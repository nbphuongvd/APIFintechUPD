package vn.com.payment.services;

import vn.com.payment.config.LogType;
import vn.com.payment.config.MainCfg;
import vn.com.payment.entities.Account;
import vn.com.payment.entities.TblBanks;
import vn.com.payment.entities.TblLoanReqDetail;
import vn.com.payment.entities.TblLoanRequest;
import vn.com.payment.entities.TblProduct;
import vn.com.payment.entities.TblRateConfig;
import vn.com.payment.home.AccountHome;
import vn.com.payment.home.TblBanksHome;
import vn.com.payment.home.TblLoanRequestHome;
import vn.com.payment.home.TblProductHome;
import vn.com.payment.home.TblRateConfigHome;
import vn.com.payment.object.BankReq;
import vn.com.payment.object.BankRes;
import vn.com.payment.object.NotifyObject;
import vn.com.payment.object.ProducResAll;
import vn.com.payment.object.ProductReq;
import vn.com.payment.object.ProductRes;
import vn.com.payment.object.RateConfigReq;
import vn.com.payment.object.RateConfigRes;
import vn.com.payment.object.ReqChangePass;
import vn.com.payment.object.ReqCreaterLoan;
import vn.com.payment.object.ReqLogin;
import vn.com.payment.object.ResChangePass;
import vn.com.payment.object.ResCreaterLoan;
import vn.com.payment.object.ResLogin;
import vn.com.payment.object.TokenRedis;
import vn.com.payment.redis.RedisBusiness;
import vn.com.payment.ultities.Commons;
import vn.com.payment.ultities.FileLogger;
import vn.com.payment.ultities.MD5;
import vn.com.payment.ultities.TripleDES;
import vn.com.payment.ultities.Utils;
import vn.com.payment.ultities.ValidData;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.util.Base64Utils;

import com.google.gson.Gson;

public class Bussiness {
	AccountHome accountHome = new AccountHome();
	TblProductHome tblProductHome = new TblProductHome();
	TblRateConfigHome tblRateConfigHome = new TblRateConfigHome();
	TblBanksHome tblBanksHome = new TblBanksHome();
	Gson gson = new Gson();
	long statusSuccess = 100l;
	long statusFale = 111l;
	long statusFaleToken = 104l;
	int statusPending = 99;
	public Response getProduct(String dataProducReq) {
		FileLogger.log("----------------Bat dau getProduct--------------------------", LogType.BUSSINESS);
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		ProducResAll producResAll = new ProducResAll();
		ProductRes productRes = new ProductRes();
		try {
			ProductReq productReq = gson.fromJson(dataProducReq, ProductReq.class);
			if (ValidData.checkNull(productReq.getUsername()) == false 
				|| ValidData.checkNull(productReq.getToken()) == false 
				|| ValidData.checkNullLong(productReq.getProduct_type()) == false 
				|| ValidData.checkNull(productReq.getProduct_brand()) == false
				|| ValidData.checkNull(productReq.getProduct_modal()) == false){
				FileLogger.log("getProduct: " + productReq.getUsername()+ " invalid : ", LogType.BUSSINESS);
				response = response.header(Commons.ReceiveTime, getTimeNow());
				producResAll.setStatus(statusFale);
				producResAll.setSuggest_info(productRes);
				response = response.header(Commons.ReceiveTime, getTimeNow());
				return response.header(Commons.ResponseTime, getTimeNow()).entity(producResAll.toJSON()).build();
			}
			boolean checkLG = checkLogin(productReq.getUsername(), productReq.getToken());
			if(checkLG){
				TblProduct tblProduct = tblProductHome.getProduct(String.valueOf(productReq.getProduct_type()), productReq.getProduct_brand(), productReq.getProduct_modal());
				
				if (tblProduct != null){
					producResAll.setStatus(statusSuccess);
					
					productRes.setProduct_type(tblProduct.getProductType());
					productRes.setProduct_brand(tblProduct.getProductName());
					productRes.setProduct_modal(tblProduct.getProductCode());
					productRes.setTotal_run(productReq.getTotal_run());
					productRes.setProduct_condition(productReq.getProduct_condition());
					productRes.setProduct_own_by_borrower(productReq.getProduct_own_by_borrower());
					productRes.setBuy_a_new_price(tblProduct.getBrandnewPrice());
					productRes.setLoan_price(tblProduct.getLoanPrice());
					//Định giá = [ Giá vay ]x [ tỷ lệ tình trạng sản phẩm ] x [ tỷ lệ KM đã đi ] x [ tỷ lệ chính chủ ]
					//accept_loan_price:	loan_price * product_condition * total_run * product_own_by_borrower
					long accept_loan_price = productRes.getLoan_price() * productRes.getProduct_condition() * productRes.getTotal_run() * productRes.getProduct_own_by_borrower();
					productRes.setAccept_loan_price(accept_loan_price);
					
					producResAll.setSuggest_info(productRes);
				}else{
					FileLogger.log("getProduct: " + productReq.getUsername()+ " tblProduct null:", LogType.BUSSINESS);
					producResAll.setStatus(statusFale);
					producResAll.setSuggest_info(productRes);					
				}
			}else{
				FileLogger.log("getProduct: " + productReq.getUsername()+ " check login false:", LogType.BUSSINESS);
				producResAll.setStatus(statusFale);
				producResAll.setSuggest_info(productRes);
			}
			FileLogger.log("getProduct: " + productReq.getUsername()+ " response to client:" + producResAll.toJSON(), LogType.BUSSINESS);
			response = response.header(Commons.ReceiveTime, getTimeNow());
			return response.header(Commons.ResponseTime, getTimeNow()).entity(producResAll.toJSON()).build();
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("----------------Ket thuc getProduct Exception "+ e.getMessage(), LogType.ERROR);
			producResAll.setStatus(statusFale);
			producResAll.setSuggest_info(productRes);
			response = response.header(Commons.ReceiveTime, getTimeNow());
			return response.header(Commons.ResponseTime, getTimeNow()).entity(producResAll.toJSON()).build();
		}
	}
	
	public Response getRateConfig(String dataRateConfig) {
		FileLogger.log("----------------Bat dau getRateConfig--------------------------", LogType.BUSSINESS);
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		RateConfigRes rateConfigRes = new RateConfigRes();
		List<TblRateConfig> arrRateCfg = new ArrayList<>();
		try {
			RateConfigReq rateConfigReq = gson.fromJson(dataRateConfig, RateConfigReq.class);
			if (ValidData.checkNull(rateConfigReq.getUsername()) == false 
				|| ValidData.checkNull(rateConfigReq.getToken()) == false
				|| ValidData.checkNullInt(rateConfigReq.getType()) == false){
				FileLogger.log("getRateConfig: " + rateConfigReq.getUsername()+ " invalid : ", LogType.BUSSINESS);
				response = response.header(Commons.ReceiveTime, getTimeNow());
				rateConfigRes.setStatus(statusFale);
				rateConfigRes.setMessage("Lay thong tin that bai - Invalid message request");
				rateConfigRes.setRate_config(arrRateCfg);
				response = response.header(Commons.ReceiveTime, getTimeNow());
				return response.header(Commons.ResponseTime, getTimeNow()).entity(rateConfigRes.toJSON()).build();
			}
			boolean checkLG = checkLogin(rateConfigReq.getUsername(), rateConfigReq.getToken());
			if(checkLG){			
				List<TblRateConfig> results = tblRateConfigHome.getRateConfig(rateConfigReq.getType());								
				if (results != null){					
					rateConfigRes.setStatus(statusSuccess);
					rateConfigRes.setMessage("Lay thong tin thanh cong");
					rateConfigRes.setRate_config(results);
				}else{
					FileLogger.log("getProduct: " + rateConfigReq.getUsername()+ " tblProduct null:", LogType.BUSSINESS);
					rateConfigRes.setStatus(statusFale);
					rateConfigRes.setMessage("Lay thong tin that bai - Khong tim thay thong tin RateConfig");
					rateConfigRes.setRate_config(arrRateCfg);					
				}
			}else{
				FileLogger.log("getRateConfig: " + rateConfigReq.getUsername()+ " check login false:", LogType.BUSSINESS);
				rateConfigRes.setStatus(statusFale);
				rateConfigRes.setMessage("Lay thong tin that bai - Thong tin login sai");
				rateConfigRes.setRate_config(arrRateCfg);
			}
			response = response.header(Commons.ReceiveTime, getTimeNow());
			FileLogger.log("getRateConfig: " + rateConfigReq.getUsername()+ " response to client:" + rateConfigRes.toJSON(), LogType.BUSSINESS);
			return response.header(Commons.ResponseTime, getTimeNow()).entity(rateConfigRes.toJSON()).build();
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("----------------Ket thuc getRateConfig Exception "+ e.getMessage(), LogType.ERROR);
			rateConfigRes.setStatus(statusFale);
			rateConfigRes.setMessage("Lay thong tin that bai -  Da co loi xay ra");
			rateConfigRes.setRate_config(arrRateCfg);
			response = response.header(Commons.ReceiveTime, getTimeNow());
			return response.header(Commons.ResponseTime, getTimeNow()).entity(rateConfigRes.toJSON()).build();
		}
	}
	
	public boolean checkLogin(String userName, String token){
		boolean result = false;
		try {
			Account acc = accountHome.getAccountUsename(userName);
			if (acc != null){
				String key = UserInfo.prefixKey + userName;
				String tokenResponse = RedisBusiness.getValue_fromCache(key);
				if(tokenResponse != null){
					TokenRedis tokenRedis = gson.fromJson(tokenResponse, TokenRedis.class);
					if(token.equals(tokenRedis.getToken())){
						result = true;
					}else{
						FileLogger.log("checkLogin token_fromCache:" + tokenResponse + " # request_token: " + token, LogType.BUSSINESS);
					}
				}else{
					FileLogger.log("checkLogin token_fromCache null ", LogType.BUSSINESS);
				}
			}else{
				FileLogger.log("checkLogin getAccountUsename null ", LogType.BUSSINESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("checkLogin Exception "+ e, LogType.ERROR);
		}
		return result;
	}
	
	public Response createrLoan (String datacreaterLoan) {
		FileLogger.log("----------------Bat dau createrLoan--------------------------", LogType.BUSSINESS);
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		ResCreaterLoan resCreaterLoan = new ResCreaterLoan();
		try {
			ReqCreaterLoan reqCreaterLoan = gson.fromJson(datacreaterLoan, ReqCreaterLoan.class);
			if (ValidData.checkNull(reqCreaterLoan.getUsername()) == false 
				|| ValidData.checkNull(reqCreaterLoan.getToken()) == false){
				FileLogger.log("createrLoan: " + reqCreaterLoan.getUsername()+ " invalid : ", LogType.BUSSINESS);
				response = response.header(Commons.ReceiveTime, getTimeNow());
				resCreaterLoan.setStatus(statusFale);
				resCreaterLoan.setMessage("Yeu cau that bai - Invalid message request");
				resCreaterLoan.setRequest_code("");
				response = response.header(Commons.ReceiveTime, getTimeNow());
				return response.header(Commons.ResponseTime, getTimeNow()).entity(resCreaterLoan.toJSON()).build();
			}
			boolean checkLG = checkLogin(reqCreaterLoan.getUsername(), reqCreaterLoan.getToken());
			if(checkLG){			
				TblLoanRequestHome tblLoanReqDetailHome = new TblLoanRequestHome();
				BigInteger aa = tblLoanReqDetailHome.getIDAutoIncrement();
				System.out.println(aa);
				
				TblLoanRequest tblLoanRequest = new TblLoanRequest();
				tblLoanRequest.setLoanId(aa.intValue());
				tblLoanRequest.setCreatedDate(new Date());
				tblLoanRequest.setEditedDate(new Date());
				tblLoanRequest.setExpireDate(new Date());
				tblLoanRequest.setApprovedDate(new Date());
				tblLoanRequest.setCreatedBy(reqCreaterLoan.getUsername());
				tblLoanRequest.setApprovedBy(reqCreaterLoan.getUsername());
				tblLoanRequest.setFinalStatus(statusPending);
				tblLoanRequest.setPreviousStatus(statusPending);
				tblLoanRequest.setSponsorId(1);
				tblLoanRequest.setLatestUpdate(new Date());				
				
				TblLoanReqDetail tblLoanReqDetail = new TblLoanReqDetail();
				tblLoanReqDetail.setReqDetailId(31);
				tblLoanReqDetail.setLoanId(aa.intValue());
				tblLoanReqDetail.setProductId(aa.intValue());
//				tblLoanReqDetail.setProductName("d_date,edited_date,disbursement_date" + aa.intValue());
				tblLoanReqDetail.setImportFrom(aa.intValue());
				tblLoanReqDetail.setManufactureDate(aa.intValue());
				tblLoanReqDetail.setExpectAmount(500000);
				tblLoanReqDetail.setBorrowerId(0);
				tblLoanReqDetail.setApprovedAmount(500000l);
				tblLoanReqDetail.setCreatedDate(new Date());
				tblLoanReqDetail.setEditedDate(new Date());
				tblLoanReqDetail.setDisbursementDate(aa.intValue());
				
				boolean checkINS =  tblLoanReqDetailHome.createLoanTrans(tblLoanRequest, tblLoanReqDetail);
				if(checkINS){
					FileLogger.log("createrLoan: " + reqCreaterLoan.getUsername()+ " thanh cong:", LogType.BUSSINESS);
					resCreaterLoan.setStatus(999l);
					resCreaterLoan.setMessage("Yeu cau dang duoc xu ly");
					resCreaterLoan.setRequest_code("");
				}else{
					FileLogger.log("createrLoan: " + reqCreaterLoan.getUsername()+ " that bai:", LogType.BUSSINESS);
					resCreaterLoan.setStatus(statusFale);
					resCreaterLoan.setMessage("Yeu cau that bai");
					resCreaterLoan.setRequest_code("");
				}
			}else{
				FileLogger.log("createrLoan: " + reqCreaterLoan.getUsername()+ " check login false:", LogType.BUSSINESS);
				resCreaterLoan.setStatus(statusFale);
				resCreaterLoan.setMessage("Yeu cau that bai - Thong tin login sai");
				resCreaterLoan.setRequest_code("");
			}
			response = response.header(Commons.ReceiveTime, getTimeNow());
			FileLogger.log("createrLoan: " + reqCreaterLoan.getUsername()+ " response to client:" + resCreaterLoan.toJSON(), LogType.BUSSINESS);
			return response.header(Commons.ResponseTime, getTimeNow()).entity(resCreaterLoan.toJSON()).build();
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("----------------Ket thuc createrLoan Exception "+ e.getMessage(), LogType.ERROR);
			resCreaterLoan.setStatus(statusFale);
			resCreaterLoan.setMessage("Yeu cau that bai - Da co loi xay ra");
			resCreaterLoan.setRequest_code("");
			response = response.header(Commons.ReceiveTime, getTimeNow());
			return response.header(Commons.ResponseTime, getTimeNow()).entity(resCreaterLoan.toJSON()).build();
		}
	}
	
	public Response getBank (String dataGetbank) {
		FileLogger.log("----------------Bat dau getBank--------------------------", LogType.BUSSINESS);
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		BankRes bankRes = new BankRes();
		try {
			BankReq reqBankReq = gson.fromJson(dataGetbank, BankReq.class);
			
			List<TblBanks> getTblBanks = tblBanksHome.getTblBanks(1, reqBankReq.getBank_support_function());
			if(getTblBanks != null){
				FileLogger.log("getBank: " + reqBankReq.getUsername()+ " thanh cong:", LogType.BUSSINESS);
				bankRes.setStatus(statusSuccess);
				bankRes.setMessage("Yeu cau thanh cong");
				bankRes.setBanks(getTblBanks);
			}else{
				FileLogger.log("getBank: " + reqBankReq.getUsername()+ " that bai getTblBanks null", LogType.BUSSINESS);
				bankRes.setStatus(statusFale);
				bankRes.setMessage("Yeu cau that bai - Da co loi xay ra");
			}			
			response = response.header(Commons.ReceiveTime, getTimeNow());
			FileLogger.log("getBank: " + reqBankReq.getUsername()+ " response to client:" + bankRes.toJSON(), LogType.BUSSINESS);
			return response.header(Commons.ResponseTime, getTimeNow()).entity(bankRes.toJSON()).build();
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("----------------Ket thuc getBank Exception "+ e.getMessage(), LogType.ERROR);
			bankRes.setStatus(statusFale);
			bankRes.setMessage("Yeu cau that bai - Da co loi xay ra");
			response = response.header(Commons.ReceiveTime, getTimeNow());
			return response.header(Commons.ResponseTime, getTimeNow()).entity(bankRes.toJSON()).build();
		}
	}
	
	public static String getTimeNow() {
		SimpleDateFormat format = new SimpleDateFormat(MainCfg.FORMATTER_DATETIME);
		return format.format(new Date());
	}
	
	public static String getTimeEXP() {
		SimpleDateFormat format = new SimpleDateFormat(MainCfg.FORMATTER_DATE);
		Date dt = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(dt); 
		c.add(Calendar.DATE, 1);
		dt = c.getTime();
		System.out.println(format.format(dt));
		return format.format(dt);
	}
	
	public static String getRandomStr(int length) {
		String stock = "0123456789abcdefghijklmnopqrstuvwxyz";
		String ran = "";
		for (int i = 0; i < length; i++) {
			ran += stock.charAt(new Random().nextInt(stock.length() - 1));
		}
		return ran;
	}
	
	
	public static void main(String[] args) {
		try {
//			System.out.println(getRandomStr(8));
//			System.out.println(URLEncoder.encode("yQ/N3ntKC40cDV9Q0ha4J5b3X77Ws0tcE616aZJhy+E\u003d", "UTF-8"));
//			System.out.println(URLDecoder.decode("yQ%2FN3ntKC40cDV9Q0ha4J5b3X77Ws0tcE616aZJhy%2BE%3D", "UTF-8"));
//			System.out.println(getTimeEXP());
//			SimpleDateFormat format = new SimpleDateFormat(MainCfg.FORMATTER_DATE);
//			format.format(new Date());
//			Date dt = new Date();
//			Calendar c = Calendar.getInstance(); 
//			c.setTime(dt); 
//			c.add(Calendar.DATE, 1);
//			dt = c.getTime();
//			
			System.out.println(MD5.hash(MD5.hash("123456")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
