package vn.com.payment.services;

import vn.com.payment.config.LogType;
import vn.com.payment.config.MainCfg;
import vn.com.payment.entities.Account;
import vn.com.payment.entities.TblProduct;
import vn.com.payment.home.AccountHome;
import vn.com.payment.home.TblProductHome;
import vn.com.payment.object.NotifyObject;
import vn.com.payment.object.ProducResAll;
import vn.com.payment.object.ProductReq;
import vn.com.payment.object.ProductRes;
import vn.com.payment.object.ReqChangePass;
import vn.com.payment.object.ReqLogin;
import vn.com.payment.object.ResChangePass;
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
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
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
	Gson gson = new Gson();
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
				producResAll.setStatus("111");
				producResAll.setSuggest_info(productRes);
				response = response.header(Commons.ReceiveTime, getTimeNow());
				return response.header(Commons.ResponseTime, getTimeNow()).entity(producResAll.toJSON()).build();
			}
			boolean checkLG = checkLogin(productReq.getUsername(), productReq.getToken());
			if(checkLG){
				TblProduct tblProduct = tblProductHome.getProduct(String.valueOf(productReq.getProduct_type()), productReq.getProduct_brand(), productReq.getProduct_modal());
				
				if (tblProduct != null){
					producResAll.setStatus("100");
					
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
					producResAll.setStatus("111");
					producResAll.setSuggest_info(productRes);					
				}
			}else{
				FileLogger.log("getProduct: " + productReq.getUsername()+ " check login false:", LogType.BUSSINESS);
				producResAll.setStatus("111");
				producResAll.setSuggest_info(productRes);
			}
			response = response.header(Commons.ReceiveTime, getTimeNow());
			return response.header(Commons.ResponseTime, getTimeNow()).entity(producResAll.toJSON()).build();
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("----------------Ket thuc getProduct Exception "+ e.getMessage(), LogType.ERROR);
			producResAll.setStatus("111");
			producResAll.setSuggest_info(productRes);
			response = response.header(Commons.ReceiveTime, getTimeNow());
			return response.header(Commons.ResponseTime, getTimeNow()).entity(producResAll.toJSON()).build();
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
	
	public boolean sentNotify(String key, String subject, String content, String message_type, String is_html, String receive_email_expect, String receive_sms_expect, String receive_chat_id_expect, String service_code, String sub_service_code){
		boolean sent = false;
		try {
			NotifyObject notifyObject = new NotifyObject();
			notifyObject.setSubject(subject);
			notifyObject.setContent(content);
			notifyObject.setMessage_type(message_type);
			notifyObject.setIs_html(is_html);
			notifyObject.setReceive_email_expect(receive_email_expect);
			notifyObject.setReceive_sms_expect(receive_sms_expect);
			notifyObject.setReceive_chat_id_expect(receive_chat_id_expect);
			notifyObject.setService_code(service_code);
			notifyObject.setSub_service_code(sub_service_code);
			FileLogger.log("sentNotify key : " + key, LogType.BUSSINESS);
			FileLogger.log("sentNotify notifyObject : " + notifyObject.toJSON(), LogType.BUSSINESS);
			RedisBusiness redisBusiness = new RedisBusiness();
			boolean checkPush = redisBusiness.enQueueToRedis(key, notifyObject);
			FileLogger.log("sentNotify checkPush : " + checkPush, LogType.BUSSINESS);
			if(checkPush){
				sent = true;
			}else{
				sent = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sent;
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
