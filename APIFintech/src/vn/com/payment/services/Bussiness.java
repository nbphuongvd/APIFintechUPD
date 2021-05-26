package vn.com.payment.services;

import vn.com.payment.config.LogType;
import vn.com.payment.config.MainCfg;
import vn.com.payment.entities.Account;
import vn.com.payment.entities.TblBanks;
import vn.com.payment.entities.TblImages;
import vn.com.payment.entities.TblLoanBill;
import vn.com.payment.entities.TblLoanReqDetail;
import vn.com.payment.entities.TblLoanRequest;
import vn.com.payment.entities.TblLoanRequestAskAns;
import vn.com.payment.entities.TblProduct;
import vn.com.payment.entities.TblRateConfig;
import vn.com.payment.home.AccountHome;
import vn.com.payment.home.BaseMongoDB;
import vn.com.payment.home.TblBanksHome;
import vn.com.payment.home.TblLoanRequestHome;
import vn.com.payment.home.TblProductHome;
import vn.com.payment.home.TblRateConfigHome;
import vn.com.payment.object.BankReq;
import vn.com.payment.object.BankRes;
import vn.com.payment.object.ContractObj;
import vn.com.payment.object.ContractObjRes;
import vn.com.payment.object.Fees;
import vn.com.payment.object.NotifyObject;
import vn.com.payment.object.ObjBillRes;
import vn.com.payment.object.ObjImage;
import vn.com.payment.object.ObjMinhhoa;
import vn.com.payment.object.ObjQuestions;
import vn.com.payment.object.ObjReqFee;
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
import java.util.GregorianCalendar;
import java.util.Iterator;
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
import org.bson.Document;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.util.Base64Utils;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

public class Bussiness {
	AccountHome accountHome = new AccountHome();
	TblProductHome tblProductHome = new TblProductHome();
	TblRateConfigHome tblRateConfigHome = new TblRateConfigHome();
	TblLoanRequestHome tbLoanRequestHome = new TblLoanRequestHome();
	TblBanksHome tblBanksHome = new TblBanksHome();
	BaseMongoDB mongoDB = new BaseMongoDB();
	Caculator caculator= new Caculator();
	UserInfo userInfo = new UserInfo();
	ValidData validData = new ValidData();
	Gson gson = new Gson();
	long statusSuccess = 100l;
	long statusFale = 111l;
	long statusFaleToken = 104l;
	int statusPending = 999;
	int statusReject  = 110;
	
	public Response getContractNumber (String dataContract) {
		FileLogger.log("----------------Bat dau getContractNumber--------------------------", LogType.BUSSINESS);
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		ContractObjRes contractObjRes = new ContractObjRes();
		try {
			FileLogger.log("getContractNumber dataContract: " + dataContract, LogType.BUSSINESS);
			ContractObj contractObj = gson.fromJson(dataContract, ContractObj.class);
			if (ValidData.checkNull(contractObj.getUsername()) == false 
				|| ValidData.checkNull(contractObj.getToken()) == false){
				FileLogger.log("getContractNumber: " + contractObj.getUsername()+ " invalid : ", LogType.BUSSINESS);
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				contractObjRes.setStatus(statusFale);
				contractObjRes.setMessage("Lay thong tin that bai - Invalid message request");
				contractObjRes.setContract_number("");
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(contractObjRes.toJSON()).build();
			}
			
			boolean checkLG = userInfo.checkLogin(contractObj.getUsername(), contractObj.getToken());
			if(checkLG){
				//Check thong tin hop dong co trong DB chua
				BigDecimal getSeq =  tbLoanRequestHome.getSeqContract();
				FileLogger.log("getContractNumber: " + contractObj.getUsername()+ " getSeq : " + getSeq, LogType.BUSSINESS);
				if(getSeq != null){
					Account acc = accountHome.getAccountUsename(contractObj.getUsername());
					if(ValidData.checkNullBranch(acc.getBranchId()) == true){
						JSONObject isJsonObject = (JSONObject)new JSONObject(acc.getBranchId());
						Iterator<String> keys = isJsonObject.keys();
						String branchID = "";
						while(keys.hasNext()) {
							branchID = keys.next();
						}
						String genContract = MainCfg.prefixContract + "." + branchID + "." + getSeq;
						FileLogger.log("getContractNumber: " + contractObj.getUsername()+ " genContract : " + genContract, LogType.BUSSINESS);
						boolean checkContract =  tbLoanRequestHome.checktblLoanRequest(genContract);
						if(checkContract){
							FileLogger.log("getContractNumber: " + contractObj.getUsername()+ " genContract : " + genContract, LogType.BUSSINESS);
							contractObjRes.setStatus(statusSuccess);
							contractObjRes.setMessage("Lay thong tin thanh cong");
							contractObjRes.setContract_number(genContract);
						}else{
							FileLogger.log("contractObj: " + contractObj.getUsername()+ " check login false:", LogType.BUSSINESS);
							contractObjRes.setStatus(statusFale);
							contractObjRes.setMessage("Lay thong tin that bai - Co the trung ma hop dong");
							contractObjRes.setContract_number("");
						}
					}else{
						FileLogger.log("contractObj: " + contractObj.getUsername()+ " check login false:", LogType.BUSSINESS);
						contractObjRes.setStatus(statusFale);
						contractObjRes.setMessage("Lay thong tin that bai - User chua thuoc chi nhanh nao");
						contractObjRes.setContract_number("");				
					}		
				}else{
					FileLogger.log("contractObj: " + contractObj.getUsername()+ " getSeq false:", LogType.BUSSINESS);
					contractObjRes.setStatus(statusFale);
					contractObjRes.setMessage("Lay thong tin that bai - Co loi xay ra");
					contractObjRes.setContract_number("");
				}				
			}else{
				FileLogger.log("contractObj: " + contractObj.getUsername()+ " check login false:", LogType.BUSSINESS);
				contractObjRes.setStatus(statusFale);
				contractObjRes.setMessage("Lay thong tin that bai - Login false");
				contractObjRes.setContract_number("");
			}
			FileLogger.log("contractObj: " + contractObj.getUsername()+ " response to client:" + contractObjRes.toJSON(), LogType.BUSSINESS);
			FileLogger.log("----------------Ket thuc contractObj ", LogType.BUSSINESS);
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(contractObjRes.toJSON()).build();
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("----------------Ket thuc contractObj Exception "+ e.getMessage(), LogType.ERROR);
			contractObjRes.setStatus(statusFale);
			contractObjRes.setMessage("Lay thong tin that bai - Co loi xay ra");
			contractObjRes.setContract_number("");
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(contractObjRes.toJSON()).build();
		}
	}
	
	public Response getProduct(String dataProducReq) {
		FileLogger.log("----------------Bat dau getProduct--------------------------", LogType.BUSSINESS);
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		ProducResAll producResAll = new ProducResAll();
		ProductRes productRes = new ProductRes();
		try {
			FileLogger.log("getProduct dataProducReq: " + dataProducReq, LogType.BUSSINESS);
			ProductReq productReq = gson.fromJson(dataProducReq, ProductReq.class);
			if (ValidData.checkNull(productReq.getUsername()) == false 
				|| ValidData.checkNull(productReq.getToken()) == false 
				|| ValidData.checkNullLong(productReq.getProduct_type()) == false 
				|| ValidData.checkNull(productReq.getProduct_brand()) == false
				|| ValidData.checkNull(productReq.getProduct_modal()) == false){
				FileLogger.log("getProduct: " + productReq.getUsername()+ " invalid : ", LogType.BUSSINESS);
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				producResAll.setStatus(statusFale);
				producResAll.setSuggest_info(productRes);
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(producResAll.toJSON()).build();
			}
			boolean checkLG = userInfo.checkLogin(productReq.getUsername(), productReq.getToken());
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
			FileLogger.log("----------------Ket thuc getProduct ", LogType.BUSSINESS);
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(producResAll.toJSON()).build();
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("----------------Ket thuc getProduct Exception "+ e.getMessage(), LogType.ERROR);
			producResAll.setStatus(statusFale);
			producResAll.setSuggest_info(productRes);
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(producResAll.toJSON()).build();
		}
	}
	
	public Response getRateConfig(String dataRateConfig) {
		FileLogger.log("----------------Bat dau getRateConfig--------------------------", LogType.BUSSINESS);
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		RateConfigRes rateConfigRes = new RateConfigRes();
		List<TblRateConfig> arrRateCfg = new ArrayList<>();
		try {
			FileLogger.log("getRateConfig dataRateConfig: " + dataRateConfig, LogType.BUSSINESS);
			RateConfigReq rateConfigReq = gson.fromJson(dataRateConfig, RateConfigReq.class);
			if (ValidData.checkNull(rateConfigReq.getUsername()) == false 
				|| ValidData.checkNull(rateConfigReq.getToken()) == false
				|| ValidData.checkNullInt(rateConfigReq.getType()) == false){
				FileLogger.log("getRateConfig: " + rateConfigReq.getUsername()+ " invalid : ", LogType.BUSSINESS);
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				rateConfigRes.setStatus(statusFale);
				rateConfigRes.setMessage("Lay thong tin that bai - Invalid message request");
				rateConfigRes.setRate_config(arrRateCfg);
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(rateConfigRes.toJSON()).build();
			}
			boolean checkLG = userInfo.checkLogin(rateConfigReq.getUsername(), rateConfigReq.getToken());
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
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			FileLogger.log("getRateConfig: " + rateConfigReq.getUsername()+ " response to client:" + rateConfigRes.toJSON(), LogType.BUSSINESS);
			FileLogger.log("----------------Ket thuc getRateConfig: ", LogType.BUSSINESS);
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(rateConfigRes.toJSON()).build();
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("----------------Ket thuc getRateConfig Exception "+ e.getMessage(), LogType.ERROR);
			rateConfigRes.setStatus(statusFale);
			rateConfigRes.setMessage("Lay thong tin that bai -  Da co loi xay ra");
			rateConfigRes.setRate_config(arrRateCfg);
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(rateConfigRes.toJSON()).build();
		}
	}
	
	public Response createrLoan (String datacreaterLoan) {
		FileLogger.log("----------------Bat dau createrLoan--------------------------", LogType.BUSSINESS);
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		ResCreaterLoan resCreaterLoan = new ResCreaterLoan();
		try {
			FileLogger.log("createrLoan datacreaterLoan: " + datacreaterLoan, LogType.BUSSINESS);
			ReqCreaterLoan reqCreaterLoan = gson.fromJson(datacreaterLoan, ReqCreaterLoan.class);
//			if (ValidData.checkNull(reqCreaterLoan.getUsername()) == false || ValidData.checkNull(reqCreaterLoan.getToken()) == false){
//				FileLogger.log("createrLoan: " + reqCreaterLoan.getUsername()+ " invalid : ", LogType.BUSSINESS);
//				resCreaterLoan.setStatus(statusFale);
//				resCreaterLoan.setMessage("Yeu cau that bai - Invalid message request");
//  			resCreaterLoan.setRequest_code();
//			}
			
			ResCreaterLoan resCreaterLoanValid = validData.validCreaterLoan(reqCreaterLoan);
			if(resCreaterLoanValid != null){
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resCreaterLoanValid.toJSON()).build();
			}
			System.out.println("aaa");
			boolean checkLG = userInfo.checkLogin(reqCreaterLoan.getUsername(), reqCreaterLoan.getToken());
			if(checkLG){			
				TblLoanRequestHome tblLoanReqDetailHome = new TblLoanRequestHome();
				BigInteger loanID = tblLoanReqDetailHome.getIDAutoIncrement();
				System.out.println(loanID);
				
				TblLoanRequest tblLoanRequest = new TblLoanRequest();
				tblLoanRequest.setLoanId(loanID.intValue());
				tblLoanRequest.setCreatedDate(new Date());
				tblLoanRequest.setEditedDate(new Date());
				tblLoanRequest.setExpireDate(new Date());
				tblLoanRequest.setApprovedDate(new Date());
				tblLoanRequest.setCreatedBy(reqCreaterLoan.getUsername());
				tblLoanRequest.setApprovedBy(reqCreaterLoan.getUsername());				
//				tblLoanRequest.setSponsorId(1);
				tblLoanRequest.setLatestUpdate(new Date());
				tblLoanRequest.setLoanCode(reqCreaterLoan.getLoan_code());
				tblLoanRequest.setLoanName(reqCreaterLoan.getLoan_name());
				tblLoanRequest.setContractSerialNum(reqCreaterLoan.getContract_serial_num());
												
				TblLoanReqDetail tblLoanReqDetail = new TblLoanReqDetail();
//				tblLoanReqDetail.setProductBrand(reqCreaterLoan.getProduct_brand());
				tblLoanReqDetail.setProductModal(reqCreaterLoan.getProduct_modal());
				tblLoanReqDetail.setTotalRun((int) reqCreaterLoan.getTotal_run());
				tblLoanReqDetail.setProductCondition((int)reqCreaterLoan.getProduct_condition());
				tblLoanReqDetail.setProductOwnByBorrower((int)reqCreaterLoan.getProduct_own_by_borrower());
				tblLoanReqDetail.setProductSerialNo(reqCreaterLoan.getProduct_serial_no());
				tblLoanReqDetail.setProductColor(reqCreaterLoan.getProduct_color());
				tblLoanReqDetail.setBorrowerType((int)reqCreaterLoan.getBorrower_type());
				tblLoanReqDetail.setBorrowerPhone(reqCreaterLoan.getBorrower_phone());
				tblLoanReqDetail.setBorrowerEmail(reqCreaterLoan.getBorrower_email());
				tblLoanReqDetail.setBorrowerId(Integer.parseInt(reqCreaterLoan.getBorrower_id_number()));
				tblLoanReqDetail.setDisburseToBankNo(reqCreaterLoan.getDisburse_to_bank_no());
				tblLoanReqDetail.setDisburseToBankName(reqCreaterLoan.getDisburse_to_bank_name());
				tblLoanReqDetail.setDisburseToBankCode(reqCreaterLoan.getDisburse_to_bank_code());
//				tblLoanReqDetail.setReqDetailId(31);
				tblLoanReqDetail.setLoanId(loanID.intValue());
//				tblLoanReqDetail.setProductId(aa.intValue());
//				tblLoanReqDetail.setProductName("d_date,edited_date,disbursement_date" + aa.intValue());
//				tblLoanReqDetail.setImportFrom(aa.intValue());
//				tblLoanReqDetail.setManufactureDate(aa.intValue());
//				tblLoanReqDetail.setExpectAmount(500000);
//				tblLoanReqDetail.setBorrowerId(0);
//				tblLoanReqDetail.setApprovedAmount(500000l);
				tblLoanReqDetail.setCreatedDate(new Date());
				tblLoanReqDetail.setEditedDate(new Date());
//				tblLoanReqDetail.setDisbursementDate(aa.intValue());
				tblLoanReqDetail.setProductValuation(reqCreaterLoan.getProduct_valuation());
				tblLoanReqDetail.setBorrowerIncome(reqCreaterLoan.getBorrower_income());
				tblLoanReqDetail.setBorrowerFullname(reqCreaterLoan.getBorrower_fullname());
				tblLoanReqDetail.setBorrowerAddress(reqCreaterLoan.getBorrower_address());
				tblLoanReqDetail.setIdIssueAt(reqCreaterLoan.getId_issue_at());
				tblLoanReqDetail.setIdIssueDate((int)reqCreaterLoan.getId_issue_date());
				tblLoanReqDetail.setProductDesc(reqCreaterLoan.getProduct_desc());
				tblLoanReqDetail.setBorrowerBirthday((int)reqCreaterLoan.getBorrower_birthday());
				tblLoanReqDetail.setProductMachineNumber(reqCreaterLoan.getProduct_machine_number());
				
				List<TblImages> imagesListSet = new ArrayList<>();
				if(reqCreaterLoan.getImages() != null){
					List<ObjImage> imagesList = reqCreaterLoan.getImages();					
					for (ObjImage objImage : imagesList) {
						TblImages tblImages = new TblImages();
						tblImages.setLoanRequestDetailId(loanID.intValue());
						tblImages.setImageName(objImage.getImage_name());
						tblImages.setPartnerImageId((int) objImage.getPartner_image_id());
						tblImages.setImageType((int)objImage.getImage_type());
						tblImages.setImageByte(objImage.getImage_byte());
						tblImages.setImageUrl(objImage.getImage_url());
						tblImages.setImageIsFront((int)objImage.getImage_is_front());
						imagesListSet.add(tblImages);
					}
				}

				List<Fees> feesListSet = reqCreaterLoan.getFees();		
				
				Bussiness bussiness = new Bussiness();
				String billID = Utils.getTimeNowDate() + "_" + Utils.getBillid();
				double sotienvay = (double) reqCreaterLoan.getLoan_amount();
				double sothangvay = (double) reqCreaterLoan.getLoan_for_month();
				double loaitrano = (double) reqCreaterLoan.getCalculate_profit_type();
				List<TblLoanBill> illustrationNewLoanBill = caculator.illustrationNewLoanBill(reqCreaterLoan.getUsername() , billID, sotienvay, sothangvay, reqCreaterLoan.getLoan_expect_date(), loaitrano, feesListSet, loanID.intValue());

				int percentAns = 0;
				TblLoanRequestAskAns tblLoanRequestAskAns = new TblLoanRequestAskAns();
				if((reqCreaterLoan.getQuestion_and_answears()) != null){
					List<ObjQuestions> questionsList = reqCreaterLoan.getQuestion_and_answears();
					String q_a_tham_dinh_1 = "";
					int totalQ = 0;
					int totalTrus = 0;
					for (ObjQuestions objQuestions : questionsList) {						
						q_a_tham_dinh_1 = q_a_tham_dinh_1 + objQuestions.toJSON();
					}
					tblLoanRequestAskAns.setLoanId(loanID.intValue());
					tblLoanRequestAskAns.setQAThamDinh1(q_a_tham_dinh_1);
//					percentAns = caculator.questionPercent(reqCreaterLoan.getUsername(), questionsList);
				}
				FileLogger.log("createrLoan: " + reqCreaterLoan.getUsername()+ " percentAns: " + percentAns, LogType.BUSSINESS);
				if(percentAns <= 50){
					tblLoanRequest.setFinalStatus(statusReject);
					tblLoanRequest.setPreviousStatus(statusReject);
				}else{
					tblLoanRequest.setFinalStatus(statusPending);
					tblLoanRequest.setPreviousStatus(statusPending);
				}
				FileLogger.log("createrLoan: " + reqCreaterLoan.getUsername()+ " tblLoanReqDetail: " + gson.toJson(tblLoanReqDetail), LogType.BUSSINESS);
				FileLogger.log("createrLoan: " + reqCreaterLoan.getUsername()+ " tblLoanRequest: " + gson.toJson(tblLoanRequest), LogType.BUSSINESS);
				FileLogger.log("createrLoan: " + reqCreaterLoan.getUsername()+ " tblLoanRequestAskAns: " + gson.toJson(tblLoanRequestAskAns), LogType.BUSSINESS);

				boolean checkINS =  tblLoanReqDetailHome.createLoanTrans(tblLoanRequest, tblLoanReqDetail, imagesListSet, illustrationNewLoanBill, tblLoanRequestAskAns);
				if(checkINS){
					FileLogger.log("createrLoan: " + reqCreaterLoan.getUsername()+ " thanh cong:", LogType.BUSSINESS);
					
					if(percentAns <= 50){
						resCreaterLoan.setStatus(tblLoanRequest.getFinalStatus());
						resCreaterLoan.setMessage("Khoan vay bi tu choi do thieu thong tin");
						resCreaterLoan.setRequest_code(loanID.longValue());
					}else{
						resCreaterLoan.setStatus(tblLoanRequest.getFinalStatus());
						resCreaterLoan.setMessage("Yeu cau dang duoc xu ly");
						resCreaterLoan.setRequest_code(loanID.longValue());
					}
				}else{
					FileLogger.log("createrLoan: " + reqCreaterLoan.getUsername()+ " that bai:", LogType.BUSSINESS);
					resCreaterLoan.setStatus(statusFale);
					resCreaterLoan.setMessage("Yeu cau that bai");
//					resCreaterLoan.setRequest_code();
				}
			}else{
				FileLogger.log("createrLoan: " + reqCreaterLoan.getUsername()+ " check login false:", LogType.BUSSINESS);
				resCreaterLoan.setStatus(statusFale);
				resCreaterLoan.setMessage("Yeu cau that bai - Thong tin login sai");
//				resCreaterLoan.setRequest_code();
			}
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			FileLogger.log("createrLoan: " + reqCreaterLoan.getUsername()+ " response to client:" + resCreaterLoan.toJSON(), LogType.BUSSINESS);
			FileLogger.log("----------------Ket thuc createrLoan: ", LogType.BUSSINESS);
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resCreaterLoan.toJSON()).build();
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("----------------Ket thuc createrLoan Exception "+ e, LogType.ERROR);
			resCreaterLoan.setStatus(statusFale);
			resCreaterLoan.setMessage("Yeu cau that bai - Da co loi xay ra");
//			resCreaterLoan.setRequest_code("");
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resCreaterLoan.toJSON()).build();
		}
	}
	
	public Response getBank (String dataGetbank) {
		FileLogger.log("----------------Bat dau getBank--------------------------", LogType.BUSSINESS);
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		BankRes bankRes = new BankRes();
		try {
			FileLogger.log("getBank dataGetbank: " + dataGetbank, LogType.BUSSINESS);
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
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			FileLogger.log("getBank: " + reqBankReq.getUsername()+ " response to client:" + bankRes.toJSON(), LogType.BUSSINESS);
			FileLogger.log("----------------Ket thuc getBank: ", LogType.BUSSINESS);
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(bankRes.toJSON()).build();
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("----------------Ket thuc getBank Exception "+ e.getMessage(), LogType.ERROR);
			bankRes.setStatus(statusFale);
			bankRes.setMessage("Yeu cau that bai - Da co loi xay ra");
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(bankRes.toJSON()).build();
		}
	}
	
	public Response getIllustration (String dataIllustration) {
		FileLogger.log("----------------Bat dau getIllustration--------------------------", LogType.BUSSINESS);
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		ObjBillRes objBillRes = new ObjBillRes();
		String billID = Utils.getTimeNowDate() + "_" + Utils.getBillid();
		try {
			FileLogger.log(" dataIllustration: " + dataIllustration, LogType.BUSSINESS);
			ObjReqFee objReqFee = gson.fromJson(dataIllustration, ObjReqFee.class);
			if (ValidData.checkNull(objReqFee.getUsername()) == false 
				|| ValidData.checkNull(objReqFee.getToken()) == false){
				FileLogger.log("getIllustration: " + objReqFee.getUsername()+ " invalid : ", LogType.BUSSINESS);
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				objBillRes.setStatus(statusFale);
				objBillRes.setMessage("Yeu cau that bai - Invalid message request");
				objBillRes.setBilling_tmp_code("");
				objBillRes.setCollection("");
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(objBillRes.toJSON()).build();
			}
			boolean checkLG = userInfo.checkLogin(objReqFee.getUsername(), objReqFee.getToken());
			if(checkLG){
				FileLogger.log("getIllustration: " + objReqFee.getUsername()+ " checkLG:" +checkLG, LogType.BUSSINESS);
//				String billID = getTimeNowDate() + "_" + getBillid();
				double sotienvay = (double) objReqFee.getLoan_amount();
				double sothangvay = (double) objReqFee.getLoan_for_month();
				double loaitrano = (double) objReqFee.getCalculate_profit_type();
				List<Fees> listFee = objReqFee.getFees();
				
				//'1:Lai suat, 2:Phi tu van, 3:phi dich vu,4:phitra no trươc han,5:phi tat toan truoc han',
				System.out.println("aaaa");
				Bussiness bussiness = new Bussiness();
				String loanID = "";
				ArrayList<Document> illustrationIns = caculator.illustrationNew(objReqFee.getUsername() , billID, sotienvay, sothangvay, objReqFee.getLoan_expect_date(), loaitrano, listFee, loanID);
				FileLogger.log("getIllustration: " + objReqFee.getUsername()+ " illustrationIns:" + illustrationIns, LogType.BUSSINESS);
				boolean checkInsMongo = mongoDB.insertDocument(illustrationIns, "tbl_minhhoa");
				FileLogger.log("getIllustration: " + objReqFee.getUsername()+ " checkInsMongo: " + checkInsMongo, LogType.BUSSINESS);
				objBillRes.setStatus(statusSuccess);
				objBillRes.setMessage("Yeu cau thanh cong");
				objBillRes.setBilling_tmp_code(billID);
				objBillRes.setCollection("tbl_minhhoa");
			}else{
				FileLogger.log("getIllustration: " + objReqFee.getUsername()+ " check login false:", LogType.BUSSINESS);
				objBillRes.setStatus(statusFale);
				objBillRes.setMessage("Yeu cau that bai - Invalid message request");
				objBillRes.setBilling_tmp_code("");
				objBillRes.setCollection("");
			}
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			FileLogger.log("getIllustration: " + objReqFee.getUsername()+ " response to client:" + objBillRes.toJSON(), LogType.BUSSINESS);
			FileLogger.log("----------------Ket thuc getIllustration: ", LogType.BUSSINESS);
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(objBillRes.toJSON()).build();
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("----------------Ket thuc getIllustration Exception "+ e.getMessage(), LogType.ERROR);
			objBillRes.setStatus(statusFale);
			objBillRes.setMessage("Yeu cau that bai - Invalid message request");
			objBillRes.setBilling_tmp_code("");
			objBillRes.setCollection("");
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(objBillRes.toJSON()).build();
		}
	}
	
	
	

	public static void main(String[] args) {
		try {
			Bussiness bussiness = new Bussiness();
			AccountHome accountHome = new AccountHome();
			Account acc = accountHome.getAccountUsename("dinhphuong.v@gmail.com");
			if(ValidData.checkNull(acc.getBranchId()) == true){
//				JSONParser parser = new JSONParser();
////				JSONArray jsonArray = (JSONArray) acc.getBranchId();
//		    	JSONObject jsonParse = (JSONObject) acc.getBranchId();
//		    	for (String key: jsonParse()) {
//		    	    jo.get(key);
//		    	}
				JsonParser jsonParser = new JsonParser();
				JSONObject isJsonObject = (JSONObject)new JSONObject(acc.getBranchId());
				Iterator<String> keys = isJsonObject.keys();

				while(keys.hasNext()) {
				    String key = keys.next();
				    System.out.println(key);
//				    if (jsonObject.get(key) instanceof JSONObject) {
//				          // do something with jsonObject here      
//				    }
				}
			}else{
				System.out.println("null");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
