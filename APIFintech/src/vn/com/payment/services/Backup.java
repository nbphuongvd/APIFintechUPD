//package vn.com.payment.services;
//
//import vn.com.payment.config.LogType;
//import vn.com.payment.config.MainCfg;
//import vn.com.payment.entities.Account;
//import vn.com.payment.entities.TblBanks;
//import vn.com.payment.entities.TblImages;
//import vn.com.payment.entities.TblLoanBill;
//import vn.com.payment.entities.TblLoanReqDetail;
//import vn.com.payment.entities.TblLoanRequest;
//import vn.com.payment.entities.TblLoanRequestAskAns;
//import vn.com.payment.entities.TblProduct;
//import vn.com.payment.entities.TblRateConfig;
//import vn.com.payment.home.AccountHome;
//import vn.com.payment.home.BaseMongoDB;
//import vn.com.payment.home.TblBanksHome;
//import vn.com.payment.home.TblLoanRequestHome;
//import vn.com.payment.home.TblProductHome;
//import vn.com.payment.home.TblRateConfigHome;
//import vn.com.payment.object.BankReq;
//import vn.com.payment.object.BankRes;
//import vn.com.payment.object.ContractObj;
//import vn.com.payment.object.ContractObjRes;
//import vn.com.payment.object.Fees;
//import vn.com.payment.object.NotifyObject;
//import vn.com.payment.object.ObjBillRes;
//import vn.com.payment.object.ObjImage;
//import vn.com.payment.object.ObjMinhhoa;
//import vn.com.payment.object.ObjQuestions;
//import vn.com.payment.object.ObjReqFee;
//import vn.com.payment.object.ProducResAll;
//import vn.com.payment.object.ProductReq;
//import vn.com.payment.object.ProductRes;
//import vn.com.payment.object.RateConfigReq;
//import vn.com.payment.object.RateConfigRes;
//import vn.com.payment.object.ReqChangePass;
//import vn.com.payment.object.ReqCreaterLoan;
//import vn.com.payment.object.ReqLogin;
//import vn.com.payment.object.ResChangePass;
//import vn.com.payment.object.ResCreaterLoan;
//import vn.com.payment.object.ResLogin;
//import vn.com.payment.object.TokenRedis;
//import vn.com.payment.redis.RedisBusiness;
//import vn.com.payment.ultities.Commons;
//import vn.com.payment.ultities.FileLogger;
//import vn.com.payment.ultities.MD5;
//import vn.com.payment.ultities.TripleDES;
//import vn.com.payment.ultities.Utils;
//import vn.com.payment.ultities.ValidData;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.math.BigInteger;
//import java.net.URLDecoder;
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//import java.text.DecimalFormat;
//import java.text.NumberFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Currency;
//import java.util.Date;
//import java.util.GregorianCalendar;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Locale;
//import java.util.Random;
//import java.util.UUID;
//
//import javax.persistence.PersistenceException;
//import javax.servlet.http.HttpServletRequest;
//import javax.ws.rs.core.MultivaluedMap;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.core.Response.ResponseBuilder;
//import javax.ws.rs.core.Response.Status;
//
//import org.apache.commons.io.IOUtils;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.params.BasicHttpParams;
//import org.apache.http.params.HttpConnectionParams;
//import org.apache.http.params.HttpParams;
//import org.bson.Document;
//import org.json.JSONObject;
//import org.json.simple.JSONArray;
//import org.json.simple.parser.JSONParser;
//import org.springframework.util.Base64Utils;
//
//import com.google.gson.Gson;
//import com.google.gson.JsonParser;
//
//public class Bussiness {
//	AccountHome accountHome = new AccountHome();
//	TblProductHome tblProductHome = new TblProductHome();
//	TblRateConfigHome tblRateConfigHome = new TblRateConfigHome();
//	TblLoanRequestHome tbLoanRequestHome = new TblLoanRequestHome();
//	TblBanksHome tblBanksHome = new TblBanksHome();
//	BaseMongoDB mongoDB = new BaseMongoDB();
//	Gson gson = new Gson();
//	long statusSuccess = 100l;
//	long statusFale = 111l;
//	long statusFaleToken = 104l;
//	int statusPending = 999;
//	
//	public Response getContractNumber (String dataContract) {
//		FileLogger.log("----------------Bat dau getContractNumber--------------------------", LogType.BUSSINESS);
//		ResponseBuilder response = Response.status(Status.OK).entity("x");
//		ContractObjRes contractObjRes = new ContractObjRes();
//		try {
//			FileLogger.log("getContractNumber dataContract: " + dataContract, LogType.BUSSINESS);
//			ContractObj contractObj = gson.fromJson(dataContract, ContractObj.class);
//			if (ValidData.checkNull(contractObj.getUsername()) == false 
//				|| ValidData.checkNull(contractObj.getToken()) == false){
//				FileLogger.log("getContractNumber: " + contractObj.getUsername()+ " invalid : ", LogType.BUSSINESS);
//				response = response.header(Commons.ReceiveTime, getTimeNow());
//				contractObjRes.setStatus(statusFale);
//				contractObjRes.setMessage("Lay thong tin that bai - Invalid message request");
//				contractObjRes.setContract_number("");
//				response = response.header(Commons.ReceiveTime, getTimeNow());
//				return response.header(Commons.ResponseTime, getTimeNow()).entity(contractObjRes.toJSON()).build();
//			}
//			boolean checkLG = checkLogin(contractObj.getUsername(), contractObj.getToken());
//			if(checkLG){
//				//Check thong tin hop dong co trong DB chua
//				BigDecimal getSeq =  tbLoanRequestHome.getSeqContract();
//				FileLogger.log("getContractNumber: " + contractObj.getUsername()+ " getSeq : " + getSeq, LogType.BUSSINESS);
//				if(getSeq != null){
//					Account acc = accountHome.getAccountUsename(contractObj.getUsername());
//					if(ValidData.checkNullBranch(acc.getBranchId()) == true){
//						JSONObject isJsonObject = (JSONObject)new JSONObject(acc.getBranchId());
//						Iterator<String> keys = isJsonObject.keys();
//						String branchID = "";
//						while(keys.hasNext()) {
//							branchID = keys.next();
//						}
//						String genContract = MainCfg.prefixContract + "." + branchID + "." + getSeq;
//						FileLogger.log("getContractNumber: " + contractObj.getUsername()+ " genContract : " + genContract, LogType.BUSSINESS);
//						boolean checkContract =  tbLoanRequestHome.checktblLoanRequest(genContract);
//						if(checkContract){
//							FileLogger.log("getContractNumber: " + contractObj.getUsername()+ " genContract : " + genContract, LogType.BUSSINESS);
//							contractObjRes.setStatus(statusSuccess);
//							contractObjRes.setMessage("Lay thong tin thanh cong");
//							contractObjRes.setContract_number(genContract);
//						}else{
//							FileLogger.log("contractObj: " + contractObj.getUsername()+ " check login false:", LogType.BUSSINESS);
//							contractObjRes.setStatus(statusFale);
//							contractObjRes.setMessage("Lay thong tin that bai - Co the trung ma hop dong");
//							contractObjRes.setContract_number("");
//						}
//					}else{
//						FileLogger.log("contractObj: " + contractObj.getUsername()+ " check login false:", LogType.BUSSINESS);
//						contractObjRes.setStatus(statusFale);
//						contractObjRes.setMessage("Lay thong tin that bai - User chua thuoc chi nhanh nao");
//						contractObjRes.setContract_number("");				
//					}		
//				}else{
//					FileLogger.log("contractObj: " + contractObj.getUsername()+ " getSeq false:", LogType.BUSSINESS);
//					contractObjRes.setStatus(statusFale);
//					contractObjRes.setMessage("Lay thong tin that bai - Co loi xay ra");
//					contractObjRes.setContract_number("");
//				}				
//			}else{
//				FileLogger.log("contractObj: " + contractObj.getUsername()+ " check login false:", LogType.BUSSINESS);
//				contractObjRes.setStatus(statusFale);
//				contractObjRes.setMessage("Lay thong tin that bai - Login false");
//				contractObjRes.setContract_number("");
//			}
//			FileLogger.log("contractObj: " + contractObj.getUsername()+ " response to client:" + contractObjRes.toJSON(), LogType.BUSSINESS);
//			FileLogger.log("----------------Ket thuc contractObj ", LogType.BUSSINESS);
//			response = response.header(Commons.ReceiveTime, getTimeNow());
//			return response.header(Commons.ResponseTime, getTimeNow()).entity(contractObjRes.toJSON()).build();
//		} catch (Exception e) {
//			e.printStackTrace();
//			FileLogger.log("----------------Ket thuc contractObj Exception "+ e.getMessage(), LogType.ERROR);
//			contractObjRes.setStatus(statusFale);
//			contractObjRes.setMessage("Lay thong tin that bai - Co loi xay ra");
//			contractObjRes.setContract_number("");
//			response = response.header(Commons.ReceiveTime, getTimeNow());
//			return response.header(Commons.ResponseTime, getTimeNow()).entity(contractObjRes.toJSON()).build();
//		}
//	}
//	
//	public Response getProduct(String dataProducReq) {
//		FileLogger.log("----------------Bat dau getProduct--------------------------", LogType.BUSSINESS);
//		ResponseBuilder response = Response.status(Status.OK).entity("x");
//		ProducResAll producResAll = new ProducResAll();
//		ProductRes productRes = new ProductRes();
//		try {
//			FileLogger.log("getProduct dataProducReq: " + dataProducReq, LogType.BUSSINESS);
//			ProductReq productReq = gson.fromJson(dataProducReq, ProductReq.class);
//			if (ValidData.checkNull(productReq.getUsername()) == false 
//				|| ValidData.checkNull(productReq.getToken()) == false 
//				|| ValidData.checkNullLong(productReq.getProduct_type()) == false 
//				|| ValidData.checkNull(productReq.getProduct_brand()) == false
//				|| ValidData.checkNull(productReq.getProduct_modal()) == false){
//				FileLogger.log("getProduct: " + productReq.getUsername()+ " invalid : ", LogType.BUSSINESS);
//				response = response.header(Commons.ReceiveTime, getTimeNow());
//				producResAll.setStatus(statusFale);
//				producResAll.setSuggest_info(productRes);
//				response = response.header(Commons.ReceiveTime, getTimeNow());
//				return response.header(Commons.ResponseTime, getTimeNow()).entity(producResAll.toJSON()).build();
//			}
//			boolean checkLG = checkLogin(productReq.getUsername(), productReq.getToken());
//			if(checkLG){
//				TblProduct tblProduct = tblProductHome.getProduct(String.valueOf(productReq.getProduct_type()), productReq.getProduct_brand(), productReq.getProduct_modal());
//				
//				if (tblProduct != null){
//					producResAll.setStatus(statusSuccess);
//					
//					productRes.setProduct_type(tblProduct.getProductType());
//					productRes.setProduct_brand(tblProduct.getProductName());
//					productRes.setProduct_modal(tblProduct.getProductCode());
//					productRes.setTotal_run(productReq.getTotal_run());
//					productRes.setProduct_condition(productReq.getProduct_condition());
//					productRes.setProduct_own_by_borrower(productReq.getProduct_own_by_borrower());
//					productRes.setBuy_a_new_price(tblProduct.getBrandnewPrice());
//					productRes.setLoan_price(tblProduct.getLoanPrice());
//					//Định giá = [ Giá vay ]x [ tỷ lệ tình trạng sản phẩm ] x [ tỷ lệ KM đã đi ] x [ tỷ lệ chính chủ ]
//					//accept_loan_price:	loan_price * product_condition * total_run * product_own_by_borrower
//					long accept_loan_price = productRes.getLoan_price() * productRes.getProduct_condition() * productRes.getTotal_run() * productRes.getProduct_own_by_borrower();
//					productRes.setAccept_loan_price(accept_loan_price);
//					
//					producResAll.setSuggest_info(productRes);
//				}else{
//					FileLogger.log("getProduct: " + productReq.getUsername()+ " tblProduct null:", LogType.BUSSINESS);
//					producResAll.setStatus(statusFale);
//					producResAll.setSuggest_info(productRes);					
//				}
//			}else{
//				FileLogger.log("getProduct: " + productReq.getUsername()+ " check login false:", LogType.BUSSINESS);
//				producResAll.setStatus(statusFale);
//				producResAll.setSuggest_info(productRes);
//			}
//			FileLogger.log("getProduct: " + productReq.getUsername()+ " response to client:" + producResAll.toJSON(), LogType.BUSSINESS);
//			FileLogger.log("----------------Ket thuc getProduct ", LogType.BUSSINESS);
//			response = response.header(Commons.ReceiveTime, getTimeNow());
//			return response.header(Commons.ResponseTime, getTimeNow()).entity(producResAll.toJSON()).build();
//		} catch (Exception e) {
//			e.printStackTrace();
//			FileLogger.log("----------------Ket thuc getProduct Exception "+ e.getMessage(), LogType.ERROR);
//			producResAll.setStatus(statusFale);
//			producResAll.setSuggest_info(productRes);
//			response = response.header(Commons.ReceiveTime, getTimeNow());
//			return response.header(Commons.ResponseTime, getTimeNow()).entity(producResAll.toJSON()).build();
//		}
//	}
//	
//	public Response getRateConfig(String dataRateConfig) {
//		FileLogger.log("----------------Bat dau getRateConfig--------------------------", LogType.BUSSINESS);
//		ResponseBuilder response = Response.status(Status.OK).entity("x");
//		RateConfigRes rateConfigRes = new RateConfigRes();
//		List<TblRateConfig> arrRateCfg = new ArrayList<>();
//		try {
//			FileLogger.log("getRateConfig dataRateConfig: " + dataRateConfig, LogType.BUSSINESS);
//			RateConfigReq rateConfigReq = gson.fromJson(dataRateConfig, RateConfigReq.class);
//			if (ValidData.checkNull(rateConfigReq.getUsername()) == false 
//				|| ValidData.checkNull(rateConfigReq.getToken()) == false
//				|| ValidData.checkNullInt(rateConfigReq.getType()) == false){
//				FileLogger.log("getRateConfig: " + rateConfigReq.getUsername()+ " invalid : ", LogType.BUSSINESS);
//				response = response.header(Commons.ReceiveTime, getTimeNow());
//				rateConfigRes.setStatus(statusFale);
//				rateConfigRes.setMessage("Lay thong tin that bai - Invalid message request");
//				rateConfigRes.setRate_config(arrRateCfg);
//				response = response.header(Commons.ReceiveTime, getTimeNow());
//				return response.header(Commons.ResponseTime, getTimeNow()).entity(rateConfigRes.toJSON()).build();
//			}
//			boolean checkLG = checkLogin(rateConfigReq.getUsername(), rateConfigReq.getToken());
//			if(checkLG){			
//				List<TblRateConfig> results = tblRateConfigHome.getRateConfig(rateConfigReq.getType());								
//				if (results != null){					
//					rateConfigRes.setStatus(statusSuccess);
//					rateConfigRes.setMessage("Lay thong tin thanh cong");
//					rateConfigRes.setRate_config(results);
//				}else{
//					FileLogger.log("getProduct: " + rateConfigReq.getUsername()+ " tblProduct null:", LogType.BUSSINESS);
//					rateConfigRes.setStatus(statusFale);
//					rateConfigRes.setMessage("Lay thong tin that bai - Khong tim thay thong tin RateConfig");
//					rateConfigRes.setRate_config(arrRateCfg);					
//				}
//			}else{
//				FileLogger.log("getRateConfig: " + rateConfigReq.getUsername()+ " check login false:", LogType.BUSSINESS);
//				rateConfigRes.setStatus(statusFale);
//				rateConfigRes.setMessage("Lay thong tin that bai - Thong tin login sai");
//				rateConfigRes.setRate_config(arrRateCfg);
//			}
//			response = response.header(Commons.ReceiveTime, getTimeNow());
//			FileLogger.log("getRateConfig: " + rateConfigReq.getUsername()+ " response to client:" + rateConfigRes.toJSON(), LogType.BUSSINESS);
//			FileLogger.log("----------------Ket thuc getRateConfig: ", LogType.BUSSINESS);
//			return response.header(Commons.ResponseTime, getTimeNow()).entity(rateConfigRes.toJSON()).build();
//		} catch (Exception e) {
//			e.printStackTrace();
//			FileLogger.log("----------------Ket thuc getRateConfig Exception "+ e.getMessage(), LogType.ERROR);
//			rateConfigRes.setStatus(statusFale);
//			rateConfigRes.setMessage("Lay thong tin that bai -  Da co loi xay ra");
//			rateConfigRes.setRate_config(arrRateCfg);
//			response = response.header(Commons.ReceiveTime, getTimeNow());
//			return response.header(Commons.ResponseTime, getTimeNow()).entity(rateConfigRes.toJSON()).build();
//		}
//	}
//	
//	public Response createrLoan (String datacreaterLoan) {
//		FileLogger.log("----------------Bat dau createrLoan--------------------------", LogType.BUSSINESS);
//		ResponseBuilder response = Response.status(Status.OK).entity("x");
//		ResCreaterLoan resCreaterLoan = new ResCreaterLoan();
//		try {
//			FileLogger.log("createrLoan datacreaterLoan: " + datacreaterLoan, LogType.BUSSINESS);
//			ReqCreaterLoan reqCreaterLoan = gson.fromJson(datacreaterLoan, ReqCreaterLoan.class);
//			if (ValidData.checkNull(reqCreaterLoan.getUsername()) == false 
//				|| ValidData.checkNull(reqCreaterLoan.getToken()) == false){
//				FileLogger.log("createrLoan: " + reqCreaterLoan.getUsername()+ " invalid : ", LogType.BUSSINESS);
//				response = response.header(Commons.ReceiveTime, getTimeNow());
//				resCreaterLoan.setStatus(statusFale);
//				resCreaterLoan.setMessage("Yeu cau that bai - Invalid message request");
////				resCreaterLoan.setRequest_code();
//				response = response.header(Commons.ReceiveTime, getTimeNow());
//				return response.header(Commons.ResponseTime, getTimeNow()).entity(resCreaterLoan.toJSON()).build();
//			}
//			boolean checkLG = checkLogin(reqCreaterLoan.getUsername(), reqCreaterLoan.getToken());
//			if(checkLG){			
//				TblLoanRequestHome tblLoanReqDetailHome = new TblLoanRequestHome();
//				BigInteger loanID = tblLoanReqDetailHome.getIDAutoIncrement();
//				System.out.println(loanID);
//				
//				TblLoanRequest tblLoanRequest = new TblLoanRequest();
//				tblLoanRequest.setLoanId(loanID.intValue());
//				tblLoanRequest.setCreatedDate(new Date());
//				tblLoanRequest.setEditedDate(new Date());
//				tblLoanRequest.setExpireDate(new Date());
//				tblLoanRequest.setApprovedDate(new Date());
//				tblLoanRequest.setCreatedBy(reqCreaterLoan.getUsername());
//				tblLoanRequest.setApprovedBy(reqCreaterLoan.getUsername());
//				tblLoanRequest.setFinalStatus(statusPending);
//				tblLoanRequest.setPreviousStatus(statusPending);
////				tblLoanRequest.setSponsorId(1);
//				tblLoanRequest.setLatestUpdate(new Date());
//				tblLoanRequest.setLoanCode(reqCreaterLoan.getLoan_code());
//				tblLoanRequest.setLoanName(reqCreaterLoan.getLoan_name());
//				
//				TblLoanReqDetail tblLoanReqDetail = new TblLoanReqDetail();
////				tblLoanReqDetail.setProductBrand(reqCreaterLoan.getProduct_brand());
//				tblLoanReqDetail.setProductModal(reqCreaterLoan.getProduct_modal());
//				tblLoanReqDetail.setTotalRun((int) reqCreaterLoan.getTotal_run());
//				tblLoanReqDetail.setProductCondition((int)reqCreaterLoan.getProduct_condition());
//				tblLoanReqDetail.setProductOwnByBorrower((int)reqCreaterLoan.getProduct_own_by_borrower());
//				tblLoanReqDetail.setProductSerialNo(reqCreaterLoan.getProduct_serial_no());
//				tblLoanReqDetail.setProductColor(reqCreaterLoan.getProduct_color());
//				tblLoanReqDetail.setBorrowerType((int)reqCreaterLoan.getBorrower_type());
//				tblLoanReqDetail.setBorrowerPhone(reqCreaterLoan.getBorrower_phone());
//				tblLoanReqDetail.setBorrowerEmail(reqCreaterLoan.getBorrower_email());
//				tblLoanReqDetail.setBorrowerId(Integer.parseInt(reqCreaterLoan.getBorrower_id_number()));
//				tblLoanReqDetail.setDisburseToBankNo(reqCreaterLoan.getDisburse_to_bank_no());
//				tblLoanReqDetail.setDisburseToBankName(reqCreaterLoan.getDisburse_to_bank_name());
//				tblLoanReqDetail.setDisburseToBankCode(reqCreaterLoan.getDisburse_to_bank_code());
////				tblLoanReqDetail.setReqDetailId(31);
//				tblLoanReqDetail.setLoanId(loanID.intValue());
////				tblLoanReqDetail.setProductId(aa.intValue());
////				tblLoanReqDetail.setProductName("d_date,edited_date,disbursement_date" + aa.intValue());
////				tblLoanReqDetail.setImportFrom(aa.intValue());
////				tblLoanReqDetail.setManufactureDate(aa.intValue());
////				tblLoanReqDetail.setExpectAmount(500000);
////				tblLoanReqDetail.setBorrowerId(0);
////				tblLoanReqDetail.setApprovedAmount(500000l);
//				tblLoanReqDetail.setCreatedDate(new Date());
//				tblLoanReqDetail.setEditedDate(new Date());
////				tblLoanReqDetail.setDisbursementDate(aa.intValue());
//				List<TblImages> imagesListSet = new ArrayList<>();
//				if(reqCreaterLoan.getImages() != null){
//					List<ObjImage> imagesList = reqCreaterLoan.getImages();					
//					for (ObjImage objImage : imagesList) {
//						TblImages tblImages = new TblImages();
//						tblImages.setLoanRequestDetailId(loanID.intValue());
//						tblImages.setImageName(objImage.getImage_name());
//						tblImages.setPartnerImageId((int) objImage.getPartner_image_id());
//						tblImages.setImageType((int)objImage.getImage_type());
//						tblImages.setImageByte(objImage.getImage_byte());
//						tblImages.setImageUrl(objImage.getImage_url());
//						tblImages.setImageIsFront((int)objImage.getImage_is_front());
//						imagesListSet.add(tblImages);
//					}
//				}
//
//				List<Fees> feesListSet = reqCreaterLoan.getFees();		
//				
//				Bussiness bussiness = new Bussiness();
//				String billID = getTimeNowDate() + "_" + getBillid();
//				double sotienvay = (double) reqCreaterLoan.getLoan_amount();
//				double sothangvay = (double) reqCreaterLoan.getLoan_for_month();
//				double loaitrano = (double) reqCreaterLoan.getCalculate_profit_type();
//				List<TblLoanBill> illustrationNewLoanBill = bussiness.illustrationNewLoanBill(reqCreaterLoan.getUsername() , billID, sotienvay, sothangvay, reqCreaterLoan.getLoan_expect_date(), loaitrano, feesListSet, loanID.intValue());
//
//				TblLoanRequestAskAns tblLoanRequestAskAns = new TblLoanRequestAskAns();
//				if((reqCreaterLoan.getQuestion_and_answears()) != null){
//					List<ObjQuestions> questionsList = reqCreaterLoan.getQuestion_and_answears();
//					String q_a_tham_dinh_1 = "";
//					for (ObjQuestions objQuestions : questionsList) {						
//						q_a_tham_dinh_1 = q_a_tham_dinh_1 + objQuestions.toJSON();
//					}
//					tblLoanRequestAskAns.setLoanId(loanID.intValue());
//					tblLoanRequestAskAns.setQAThamDinh1(q_a_tham_dinh_1);
//				}
//
//				boolean checkINS =  tblLoanReqDetailHome.createLoanTrans(tblLoanRequest, tblLoanReqDetail, imagesListSet, illustrationNewLoanBill, tblLoanRequestAskAns);
//				if(checkINS){
//					FileLogger.log("createrLoan: " + reqCreaterLoan.getUsername()+ " thanh cong:", LogType.BUSSINESS);
//					resCreaterLoan.setStatus(999l);
//					resCreaterLoan.setMessage("Yeu cau dang duoc xu ly");
//					resCreaterLoan.setRequest_code(loanID.longValue());
//				}else{
//					FileLogger.log("createrLoan: " + reqCreaterLoan.getUsername()+ " that bai:", LogType.BUSSINESS);
//					resCreaterLoan.setStatus(statusFale);
//					resCreaterLoan.setMessage("Yeu cau that bai");
////					resCreaterLoan.setRequest_code();
//				}
//			}else{
//				FileLogger.log("createrLoan: " + reqCreaterLoan.getUsername()+ " check login false:", LogType.BUSSINESS);
//				resCreaterLoan.setStatus(statusFale);
//				resCreaterLoan.setMessage("Yeu cau that bai - Thong tin login sai");
////				resCreaterLoan.setRequest_code();
//			}
//			response = response.header(Commons.ReceiveTime, getTimeNow());
//			FileLogger.log("createrLoan: " + reqCreaterLoan.getUsername()+ " response to client:" + resCreaterLoan.toJSON(), LogType.BUSSINESS);
//			FileLogger.log("----------------Ket thuc createrLoan: ", LogType.BUSSINESS);
//			return response.header(Commons.ResponseTime, getTimeNow()).entity(resCreaterLoan.toJSON()).build();
//		} catch (Exception e) {
//			e.printStackTrace();
//			FileLogger.log("----------------Ket thuc createrLoan Exception "+ e.getMessage(), LogType.ERROR);
//			resCreaterLoan.setStatus(statusFale);
//			resCreaterLoan.setMessage("Yeu cau that bai - Da co loi xay ra");
////			resCreaterLoan.setRequest_code("");
//			response = response.header(Commons.ReceiveTime, getTimeNow());
//			return response.header(Commons.ResponseTime, getTimeNow()).entity(resCreaterLoan.toJSON()).build();
//		}
//	}
//	
//	public Response getBank (String dataGetbank) {
//		FileLogger.log("----------------Bat dau getBank--------------------------", LogType.BUSSINESS);
//		ResponseBuilder response = Response.status(Status.OK).entity("x");
//		BankRes bankRes = new BankRes();
//		try {
//			FileLogger.log("getBank dataGetbank: " + dataGetbank, LogType.BUSSINESS);
//			BankReq reqBankReq = gson.fromJson(dataGetbank, BankReq.class);
//			
//			List<TblBanks> getTblBanks = tblBanksHome.getTblBanks(1, reqBankReq.getBank_support_function());
//			if(getTblBanks != null){
//				FileLogger.log("getBank: " + reqBankReq.getUsername()+ " thanh cong:", LogType.BUSSINESS);
//				bankRes.setStatus(statusSuccess);
//				bankRes.setMessage("Yeu cau thanh cong");
//				bankRes.setBanks(getTblBanks);
//			}else{
//				FileLogger.log("getBank: " + reqBankReq.getUsername()+ " that bai getTblBanks null", LogType.BUSSINESS);
//				bankRes.setStatus(statusFale);
//				bankRes.setMessage("Yeu cau that bai - Da co loi xay ra");
//			}			
//			response = response.header(Commons.ReceiveTime, getTimeNow());
//			FileLogger.log("getBank: " + reqBankReq.getUsername()+ " response to client:" + bankRes.toJSON(), LogType.BUSSINESS);
//			FileLogger.log("----------------Ket thuc getBank: ", LogType.BUSSINESS);
//			return response.header(Commons.ResponseTime, getTimeNow()).entity(bankRes.toJSON()).build();
//		} catch (Exception e) {
//			e.printStackTrace();
//			FileLogger.log("----------------Ket thuc getBank Exception "+ e.getMessage(), LogType.ERROR);
//			bankRes.setStatus(statusFale);
//			bankRes.setMessage("Yeu cau that bai - Da co loi xay ra");
//			response = response.header(Commons.ReceiveTime, getTimeNow());
//			return response.header(Commons.ResponseTime, getTimeNow()).entity(bankRes.toJSON()).build();
//		}
//	}
//	
//	public Response getIllustration (String dataIllustration) {
//		FileLogger.log("----------------Bat dau getIllustration--------------------------", LogType.BUSSINESS);
//		ResponseBuilder response = Response.status(Status.OK).entity("x");
//		ObjBillRes objBillRes = new ObjBillRes();
//		String billID = getTimeNowDate() + "_" + getBillid();
//		try {
//			FileLogger.log(" dataIllustration: " + dataIllustration, LogType.BUSSINESS);
//			ObjReqFee objReqFee = gson.fromJson(dataIllustration, ObjReqFee.class);
//			if (ValidData.checkNull(objReqFee.getUsername()) == false 
//				|| ValidData.checkNull(objReqFee.getToken()) == false){
//				FileLogger.log("getIllustration: " + objReqFee.getUsername()+ " invalid : ", LogType.BUSSINESS);
//				response = response.header(Commons.ReceiveTime, getTimeNow());
//				objBillRes.setStatus(statusFale);
//				objBillRes.setMessage("Yeu cau that bai - Invalid message request");
//				objBillRes.setBilling_tmp_code("");
//				objBillRes.setCollection("");
//				response = response.header(Commons.ReceiveTime, getTimeNow());
//				return response.header(Commons.ResponseTime, getTimeNow()).entity(objBillRes.toJSON()).build();
//			}
//			boolean checkLG = checkLogin(objReqFee.getUsername(), objReqFee.getToken());
//			if(checkLG){
//				FileLogger.log("getIllustration: " + objReqFee.getUsername()+ " checkLG:" +checkLG, LogType.BUSSINESS);
////				String billID = getTimeNowDate() + "_" + getBillid();
//				double sotienvay = (double) objReqFee.getLoan_amount();
//				double sothangvay = (double) objReqFee.getLoan_for_month();
//				double loaitrano = (double) objReqFee.getCalculate_profit_type();
//				List<Fees> listFee = objReqFee.getFees();
//				
//				//'1:Lai suat, 2:Phi tu van, 3:phi dich vu,4:phitra no trươc han,5:phi tat toan truoc han',
//				System.out.println("aaaa");
//				Bussiness bussiness = new Bussiness();
//				String loanID = "";
//				ArrayList<Document> illustrationIns = bussiness.illustrationNew(objReqFee.getUsername() , billID, sotienvay, sothangvay, objReqFee.getLoan_expect_date(), loaitrano, listFee, loanID);
//				FileLogger.log("getIllustration: " + objReqFee.getUsername()+ " illustrationIns:" + illustrationIns, LogType.BUSSINESS);
//				boolean checkInsMongo = mongoDB.insertDocument(illustrationIns, "tbl_minhhoa");
//				FileLogger.log("getIllustration: " + objReqFee.getUsername()+ " checkInsMongo: " + checkInsMongo, LogType.BUSSINESS);
//				objBillRes.setStatus(statusSuccess);
//				objBillRes.setMessage("Yeu cau thanh cong");
//				objBillRes.setBilling_tmp_code(billID);
//				objBillRes.setCollection("tbl_minhhoa");
//			}else{
//				FileLogger.log("getIllustration: " + objReqFee.getUsername()+ " check login false:", LogType.BUSSINESS);
//				objBillRes.setStatus(statusFale);
//				objBillRes.setMessage("Yeu cau that bai - Invalid message request");
//				objBillRes.setBilling_tmp_code("");
//				objBillRes.setCollection("");
//			}
//			response = response.header(Commons.ReceiveTime, getTimeNow());
//			FileLogger.log("getIllustration: " + objReqFee.getUsername()+ " response to client:" + objBillRes.toJSON(), LogType.BUSSINESS);
//			FileLogger.log("----------------Ket thuc getIllustration: ", LogType.BUSSINESS);
//			return response.header(Commons.ResponseTime, getTimeNow()).entity(objBillRes.toJSON()).build();
//		} catch (Exception e) {
//			e.printStackTrace();
//			FileLogger.log("----------------Ket thuc getIllustration Exception "+ e.getMessage(), LogType.ERROR);
//			objBillRes.setStatus(statusFale);
//			objBillRes.setMessage("Yeu cau that bai - Invalid message request");
//			objBillRes.setBilling_tmp_code("");
//			objBillRes.setCollection("");
//			response = response.header(Commons.ReceiveTime, getTimeNow());
//			return response.header(Commons.ResponseTime, getTimeNow()).entity(objBillRes.toJSON()).build();
//		}
//	}
//	
//	public boolean checkLogin(String userName, String token){
//		boolean result = false;
//		try {
//			Account acc = accountHome.getAccountUsename(userName);
//			if (acc != null){
//				String key = UserInfo.prefixKey + userName;
//				String tokenResponse = RedisBusiness.getValue_fromCache(key);
//				if(tokenResponse != null){
//					TokenRedis tokenRedis = gson.fromJson(tokenResponse, TokenRedis.class);
//					if(token.equals(tokenRedis.getToken())){
//						result = true;
//					}else{
//						FileLogger.log("checkLogin token_fromCache:" + tokenResponse + " # request_token: " + token, LogType.BUSSINESS);
//					}
//				}else{
//					FileLogger.log("checkLogin token_fromCache null ", LogType.BUSSINESS);
//				}
//			}else{
//				FileLogger.log("checkLogin getAccountUsename null ", LogType.BUSSINESS);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			FileLogger.log("checkLogin Exception "+ e, LogType.ERROR);
//		}
//		return result;
//	}
//	
//	//Tinh minh hoa khoan vay
//		public ArrayList<Document> illustrationNew (String userName, String billID, double sotienvay, double sothangvay, String ngayvay, double loaitrano, List<Fees> listFee, String loanID){
//			ArrayList<Document> array = new ArrayList<Document>();
//			List<TblLoanBill> feesListSet = new ArrayList<>();
//			try {
//				FileLogger.log("getIllustration: " + userName+ " illustrationIns:" + loaitrano, LogType.BUSSINESS);
//				double sotienconlai_a				= sotienvay;	
//				double gocconlai					= sotienvay;
//				double laixuatNam 					= 0;
//				double phidichvu					= 0;
//				double phituvan 					= 0;
//				double phitratruochan 				= 0;
//				double phitattoantruochan 			= 0;
//				
//				double tinhphitranotruochan_a	  	= 0;
//				double tienlaithang_a 				= 0;
//				double tinhphidichvu_a	  			= 0;
//				double tinhphituvan_a	  			= 0;
//				double tientrahangthang_a			= 0;
//				double tinhphitattoan_a	  			= 0;
//				
//				double tinhphitranotruochan	  		= 0;
//				double tienlaithang 				= 0;
//				double tinhphidichvu	  			= 0;
//				double tinhphituvan	  				= 0;
//				double tientrahangthang				= 0;
//				double tinhphitattoan	  			= 0;
//				
//				
////				double sumTiemlai	  			= 0;
////				double sumPhituvan	  			= 0; // phituvan = phi tu van
////				double sumPhiquanly	  			= 0; //phiquanly = phi dich vu
//				if(loaitrano == 1){
//					//Lịch trả nợ theo dư nợ giảm dần						
//					
//					int kyvay = 0;
//					for (int i = 1; i<= sothangvay ; i++) {
//						
////						if(i == 0){
////							 Document doc = new Document("idMinhhoa", billID)
////									 .append("kyTrano",getTimeOut(i))
////								     .append("gocConlai", sotienvay)
////								     .append("gocTramoiky", 0)
////								     .append("laiThang", 0)
////									 .append("traHangthang", 0)
////									 .append("phiTuvan", 0)
////									 .append("phiDichvu", 0)
////									 .append("phiTranotruochan", 0)
////									 .append("tattoanTruochan", 0);
////									 array.add(doc);
////									 sumTiemlai = 0;
////						}else{						
//							
//							double tiengoctramoiky_a 			= sotienvay/sothangvay;;
//							double tiencantt					= tienThanhtoan(userName, billID, sotienvay, sothangvay, loaitrano, listFee);
//							for (Fees fees : listFee) {
//								switch (String.valueOf(fees.getFee_type())) {
//								case "1":			
//									if(fees.getFix_fee_amount() <= 0){
//										laixuatNam 				= (double) fees.getFix_fee_percent();
//										tienlaithang_a 			= (sotienconlai_a - kyvay * sotienconlai_a / sothangvay ) * laixuatNam * 30.41666667 / 365;
//									}else{
//										laixuatNam 				= (double) fees.getFix_fee_amount();
//										tienlaithang_a 			= laixuatNam;
//									}						
//									break;			
//								case "2":			
//									if(fees.getFix_fee_amount() <= 0){
//										phituvan 				= (double) fees.getFix_fee_percent();
//										tinhphituvan_a	  		= (sotienconlai_a - kyvay * sotienconlai_a / sothangvay ) * phituvan * 30.41666667 / 365;
//									}else{
//										phituvan 				= (double) fees.getFix_fee_amount();
//										tinhphituvan_a	  		= phituvan;
//									}	
//									break;	
//								case "3":			
//									if(fees.getFix_fee_amount() <= 0){
//										phidichvu 				= (double) fees.getFix_fee_percent();
//										tinhphidichvu_a	  		= (sotienconlai_a - kyvay * sotienconlai_a / sothangvay ) * phidichvu * 30.41666667 / 365;
//									}else{
//										phidichvu 				= (double) fees.getFix_fee_amount();
//										tinhphidichvu_a	  		= phidichvu;
//									}	
//									break;	
//								case "4":			
//									if(fees.getFix_fee_amount() <= 0){
//										phitratruochan 			= (double) fees.getFix_fee_percent();
//										tinhphitranotruochan_a	= (sotienconlai_a - kyvay * sotienconlai_a / sothangvay ) * phitratruochan;
//									}else{
//										phitratruochan 			= (double) fees.getFix_fee_amount();
//										tinhphitranotruochan_a	= phitratruochan;
//									}	
//									break;
//								case "5":									
//									if(fees.getFix_fee_amount() <= 0){
//										phitattoantruochan 		= (double) fees.getFix_fee_percent();
//										tinhphitattoan_a		= (sotienconlai_a - kyvay * sotienconlai_a / sothangvay ) * phitattoantruochan;
////										tinhphitattoan_a	  	= sotienconlai_a + tienlaithang_a + tinhphidichvu_a + tinhphituvan_a + tinhphitranotruochan_a;
//									}else{
//										phitattoantruochan 		= (double) fees.getFix_fee_amount();
//										tinhphitattoan_a	  	= phitattoantruochan;
//									}	
//									break;
//								default:
//									break;
//								}
//							}
//							//'1:Lai suat, 2:Phi tu van, 3:phi dich vu,4:phi tra no qua han,5:phi tat toan qua han',
////							ObjMinhhoa objMinhhoa = new ObjMinhhoa();
////							tiengoctramoiky_a 					= sotienvay/sothangvay;
////							double tinhphitranotruochan_a	  	= sotienconlai_a * phitratruochan;						
////							double tienlaithang_a 				= sotienconlai_a * laixuatNam * 30 / 365;
////							double tinhphidichvu_a	  			= sotienconlai_a * phidichvu * 30 / 365;
////							double tinhphituvan_a	  			= sotienconlai_a * phituvan * 30 / 365;					
////							tientrahangthang_a					= tiengoctramoiky_a + tienlaithang_a + tinhphituvan_a + tinhphidichvu_a;
////							double tinhphitattoan_a	  			= sotienconlai_a + tienlaithang_a + tinhphidichvu_a + tinhphituvan_a + tinhphitranotruochan_a;
////							sotienconlai_a	  					= sotienconlai_a - tiengoctramoiky_a;
////							objMinhhoa.setKyTrano(getTimeOut(i));
////							objMinhhoa.setGocConlai(sotienconlai_a);
////							objMinhhoa.setGocTramoiky(tiengoctramoiky_a);
////							objMinhhoa.setLaiThang(tienlaithang_a);
////							objMinhhoa.setTraHangthang(tientrahangthang_a);
////							objMinhhoa.setPhiTuvan(tinhphituvan_a);
////							objMinhhoa.setPhiDichvu(tinhphidichvu_a);
////							objMinhhoa.setPhiTranotruochan(tinhphitranotruochan_a);
////							objMinhhoa.setTattoanTruochan(tinhphitattoan_a);
//		//					array.add(objMinhhoa);
//							
//							//'1:Lai suat, 2:Phi tu van, 3:phi dich vu,4:phitra no trươc han,5:phi tat toan truoc han',
//
////							 Document doc = new Document("idMinhhoa", billID)
////									 .append("kyVay",getTimeOut(i))
////									 .append("kyTrano",getTimeOut(i))
////								     .append("gocConlai",  Math.round(sotienconlai_a))
////								     .append("gocTramoiky",  Math.round(tiengoctramoiky_a)) 
////								     .append("laiThang",  Math.round(tienlaithang_a))
////									 .append("traHangthang",  Math.round(tientrahangthang_a))
////									 .append("phiTuvan",  Math.round(tinhphituvan_a))
////									 .append("phiDichvu",  Math.round(tinhphidichvu_a))
////									 .append("phiTranotruochan",  Math.round(tinhphitranotruochan_a))
////									 .append("tattoanTruochan",  Math.round(tinhphitattoan_a));
////							 array.add(doc);
//							double tiengoc = tiencantt - (tienlaithang_a + tinhphituvan_a + tinhphidichvu_a);
//							gocconlai = gocconlai - tiengoc;
//							Document doc = new Document("idMinhhoa", billID)
//									 .append("Kyvay",i)
//									 .append("Ngaythanhtoan",				getNgayvay(ngayvay))
//								     .append("Sotiencanthanhtoan",  		Math.round(tiencantt))
//								     .append("Tiengoc",  					Math.round(tiengoc) )
//								     .append("Tienlai",  					Math.round(tienlaithang_a))
//									 .append("Phituvandichvu",  			Math.round(tinhphituvan_a))
//									 .append("Phiquanly",  					Math.round(tinhphidichvu_a))
//									 .append("Gocconlaisauthanhtoanky",  	Math.round(gocconlai))
//									 .append("Phitattoan",  				Math.round(tinhphitattoan_a))
////									 .append("Phitranoquahan",  Math.round(tinhphitranotruochan_a))
//									 .append("Sotientattoantaikynay",  		Math.round(gocconlai + tinhphitattoan_a));
//							 array.add(doc);
//							 
//							 TblLoanBill tblLoanBill = new TblLoanBill();
//							 tblLoanBill.setLoanId(123);
//							 tblLoanBill.setLoanRemainAmount((new Double(gocconlai)).longValue());
//							 tblLoanBill.setCreatedDate(new Date());
//							 tblLoanBill.setAmtToDecrYourLoan(new BigDecimal(tiengoc));
//							 tblLoanBill.setMonthlyInterest(new BigDecimal(tienlaithang_a));
//							 tblLoanBill.setTotalOnAMonth(new BigDecimal(tiencantt));
//							 tblLoanBill.setAdvisoryFee(new BigDecimal(tinhphituvan_a));
//							 tblLoanBill.setServiceFee(new BigDecimal(tinhphidichvu_a));
//							 tblLoanBill.setExtFeeIfIndPayBefore(new BigDecimal(tinhphitattoan_a));
//							 tblLoanBill.setTotalPayIfSettleRequest(new BigDecimal(gocconlai + tinhphitattoan_a));
//							 feesListSet.add(tblLoanBill);
////							  `loan_id` int(11) NOT NULL COMMENT 'id khoan vay',
////							  `loan_remain_amount` decimal(10,0) NOT NULL COMMENT 'So tien con phai tra/goc',						
////							  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'ngay tao',
////							  `amt_to_decr_your_loan` decimal(20,0) DEFAULT NULL,
////							  `monthly_interest` decimal(20,0) DEFAULT NULL COMMENT 'Tien lai tra hang thang',
////							  `total_on_a_month` decimal(20,0) DEFAULT NULL COMMENT 'Tien lai thang thang',
////							  `advisory_fee` decimal(20,0) DEFAULT NULL COMMENT 'Phi tu van',
////							  `service_fee` decimal(20,0) DEFAULT NULL COMMENT 'Phi dich vu',
////							  `ext_fee_if_ind_pay_before` decimal(20,0) DEFAULT NULL COMMENT 'Phi tra no truoc han',
////							  `total_pay_if_settle_request` decimal(20,0) DEFAULT NULL COMMENT 'Tong tien phai thanh toan neu tat'' toan truoc han.',
////							  `day_must_pay` int(8) DEFAULT NULL COMMENT 'Ngay phai thanh toan: yyyyMMdd',
////							   Document doc = new Document("idMinhhoa", billID)
////									 .append("kyTrano",getTimeOut(i))  					// day_must_pay
////								     .append("gocConlai", sotienconlai)					// loan_remain_amount
////								     .append("gocTrakycuoi", tiengoctrakycuoi)
////								     .append("laiThang", tienlaithang)					// total_on_a_month
////									 .append("traHangthang", tientrahangthang)			// monthly_interest
////									 .append("phiTuvan", tinhphituvan)					// advisory_fee
////									 .append("phiDichvu", tinhphidichvu) 				// service_fee
////									 .append("phiTranotruochan", tinhphitranotruochan) 	// ext_fee_if_ind_pay_before
////									 .append("tattoanTruochan", tinhphitattoan);  		//total_pay_if_settle_request
////									 array.add(doc); 
////						}
//							 
//							 
//							 
//							 
//							 
//							 
//							 
//							 
//							 
//							 
//							 
//							 kyvay = kyvay + 1;
//							 ngayvay = getNgayvayNew(getNgayvay(ngayvay));
////						}
//					}
//				}else{
//					//Lịch trả nợ gốc cuối kỳ	
////					double sotientattoantaikynay = 0;
//					for (int i = 1; i<= sothangvay ; i++) {
//						double sotienconlai = sotienvay;
////						if(i == 0){
////							 Document doc = new Document("idMinhhoa", billID)
////									 .append("kyTrano",getTimeOut(i))
////								     .append("gocConlai", sotienconlai)
////								     .append("gocTrakycuoi", 0)
////								     .append("laiThang", 0)
////									 .append("traHangthang", 0)
////									 .append("phiTuvan", 0)
////									 .append("phiDichvu", 0)
//////								 .append("phiTranotruochan", 0)
////									 .append("tattoanquahan", 0);
////									 array.add(doc);
////						}else{
//							int check = 0;
//							ObjMinhhoa objMinhhoa = new ObjMinhhoa();
//							for (Fees fees : listFee) {
//								switch (String.valueOf(fees.getFee_type())) {
//								case "1":			
//									if(fees.getFix_fee_amount() <= 0){
//										laixuatNam 				= (double) fees.getFix_fee_percent();
//										tienlaithang 			= sotienconlai * laixuatNam * 30 / 365;
//									}else{
//										laixuatNam 				= (double) fees.getFix_fee_amount();
//										tienlaithang 			= laixuatNam;
//									}						
//									break;			
//								case "2":			
//									if(fees.getFix_fee_amount() <= 0){
//										phituvan 				= (double) fees.getFix_fee_percent();
//										tinhphituvan	  		= sotienconlai * phituvan * 30 / 365;
//									}else{
//										phituvan 				= (double) fees.getFix_fee_amount();
//										tinhphituvan	  		= phituvan;
//									}	
//									break;	
//								case "3":			
//									if(fees.getFix_fee_amount() <= 0){
//										phidichvu 				= (double) fees.getFix_fee_percent();
//										tinhphidichvu	  		= sotienconlai * phidichvu * 30 / 365;
//									}else{
//										phidichvu 				= (double) fees.getFix_fee_amount();
//										tinhphidichvu	  		= phidichvu;
//									}	
//									break;	
//								case "4":			
//									if(fees.getFix_fee_amount() <= 0){
//										phitratruochan 			= (double) fees.getFix_fee_percent();
//										tinhphitranotruochan	= sotienconlai * phitratruochan;
//									}else{
//										phitratruochan 			= (double) fees.getFix_fee_amount();
//										tinhphitranotruochan	= phitratruochan;
//									}	
//									break;
//								case "5":									
//									if(fees.getFix_fee_amount() <= 0){
//										check = 1;
//										phitattoantruochan = (double) fees.getFix_fee_percent();									
//									}else{										
//										phitattoantruochan 		= (double) fees.getFix_fee_amount();
//										tinhphitattoan			= phitattoantruochan;
//									}	
//									break;
//								default:
//									break;
//								}
//							}
//							if(check == 1){
//								tinhphitattoan	  		= sotienconlai + tienlaithang + tinhphidichvu + tinhphituvan + tinhphitranotruochan;
//							}
//							tientrahangthang					= tienlaithang + tinhphituvan + tinhphidichvu;
////							double tiengoctrakycuoi 			= sotienconlai;
////							objMinhhoa.setKyTrano(getTimeOut(1));
////							objMinhhoa.setGocConlai(sotienconlai);
////							objMinhhoa.setGocTrakycuoi(tiengoctrakycuoi);
////							objMinhhoa.setLaiThang(tienlaithang);
////							objMinhhoa.setTraHangthang(tientrahangthang);
////							objMinhhoa.setPhiTuvan(tinhphituvan);
////							objMinhhoa.setPhiDichvu(tinhphidichvu);
////							objMinhhoa.setPhiTranotruochan(tinhphitranotruochan);
////							objMinhhoa.setTattoanTruochan(tinhphitattoan);
//			//				array.add(objMinhhoa);
//							
//							Document doc = new Document("idMinhhoa", billID)
////									 .append("kyTrano",				getNgayvay(ngayvay))
////								     .append("gocConlai", 			Math.round(sotienconlai))
////								     .append("gocTrakycuoi",  		Math.round(tiengoctrakycuoi))
////								     .append("laiThang",  			Math.round(tienlaithang))
////									 .append("traHangthang",  		Math.round(tientrahangthang))
////									 .append("phiTuvan",  			Math.round(tinhphituvan))
////									 .append("phiDichvu",  			Math.round(tinhphidichvu))
//////								 .append("phiTranoquahan", 		Math.round(tinhphitranotruochan))
////									 .append("tattoanquahan",  		Math.round(tinhphitattoan));
//							
//									 .append("Kyvay",						i)
//									 .append("Ngaythanhtoan",				getNgayvay(ngayvay))
//								     .append("Sotiencanthanhtoan",  		Math.round(tientrahangthang))
//								     .append("Tiengoc",  					Math.round(sotienconlai) )
//								     .append("Tienlai",  					Math.round(tienlaithang))
//									 .append("Phituvandichvu",  			Math.round(tinhphituvan))
//									 .append("Phiquanly",  					Math.round(tinhphidichvu))
//									 .append("Gocconlaisauthanhtoanky",  	Math.round(sotienconlai))
//									 .append("Phitattoan",  				Math.round(tinhphitranotruochan))
//		//							 .append("Phitranoquahan",  			Math.round(tinhphitranotruochan_a))
//									 .append("Sotientattoantaikynay",  		Math.round(tinhphitattoan));
//									 
////									 		 Gốc còn lại khi trả trước hạn  => Gocconlaisauthanhtoanky
////											 Gốc trả cuối kỳ => Tiengoc
////											 Trả hàng tháng => Sotiencanthanhtoan
////											 Phí tư vấn => Phituvandichvu
////											 Phí dịch vụ => Phiquanly
////											 Phí trả nợ trước hạn (Nếu có) => Phitattoan
////											 Tất toán trước hạn (Nếu có) => Sotientattoantaikynay
////									 
//									 
//									 array.add(doc);
//									 
//									 TblLoanBill tblLoanBill = new TblLoanBill();
//									 tblLoanBill.setLoanId(123);
//									 tblLoanBill.setLoanRemainAmount((new Double(gocconlai)).longValue());
//									 tblLoanBill.setCreatedDate(new Date());
//									 tblLoanBill.setAmtToDecrYourLoan(new BigDecimal(sotienconlai));
//									 tblLoanBill.setMonthlyInterest(new BigDecimal(tienlaithang));
//									 tblLoanBill.setTotalOnAMonth(new BigDecimal(tientrahangthang));
//									 tblLoanBill.setAdvisoryFee(new BigDecimal(tinhphituvan));
//									 tblLoanBill.setServiceFee(new BigDecimal(tinhphidichvu));
//									 tblLoanBill.setExtFeeIfIndPayBefore(new BigDecimal(tinhphitranotruochan));
//									 tblLoanBill.setTotalPayIfSettleRequest(new BigDecimal(tinhphitattoan));
//									 feesListSet.add(tblLoanBill);
//									 
//									 ngayvay = getNgayvayNew(getNgayvay(ngayvay));
////						}
//					}
//				}
//
//				//Thì tiền tra gốc hàng tháng = số tiền vay / số tháng vay
//				//Tiền lãi hàng tháng = số tiền gốc còn lại * lãi năm * số ngày trong tháng / số ngày trong năm (mặc định là 365)
//				//Phí dịch vụ sẽ tính bằng = số tiền còn lại * phi dich vu / ngày trong năm * ngày trong tháng
//				//phí tất toán trước hạn chỉ bằng = tiền gốc còn lại + lãi trong tháng + phí dịch vụ trong tháng + phi tư vấn trong tháng + phí trả nợ trước hạn (tính theo tháng)
//				FileLogger.log("illustration: " + userName+ " illustrationIns array insert DB:" + array, LogType.BUSSINESS);
//
//				return array;
//			} catch (Exception e) {
//				FileLogger.log("illustration: " + userName+ " illustrationIns exception" + e, LogType.ERROR);
//				e.printStackTrace();
//			}
//			return null;
//		}
//		
//		
//		
//		//Tinh minh hoa khoan vay
//				public List<TblLoanBill> illustrationNewLoanBill (String userName, String billID, double sotienvay, double sothangvay, String ngayvay, double loaitrano, List<Fees> listFee, int loanID){
//					ArrayList<Document> array = new ArrayList<Document>();
//					List<TblLoanBill> feesListSet = new ArrayList<>();
//					try {
//						FileLogger.log("getIllustration: " + userName+ " illustrationIns:" + loaitrano, LogType.BUSSINESS);
//						double sotienconlai_a				= sotienvay;	
//						double gocconlai					= sotienvay;
//						double laixuatNam 					= 0;
//						double phidichvu					= 0;
//						double phituvan 					= 0;
//						double phitratruochan 				= 0;
//						double phitattoantruochan 			= 0;
//						
//						double tinhphitranotruochan_a	  	= 0;
//						double tienlaithang_a 				= 0;
//						double tinhphidichvu_a	  			= 0;
//						double tinhphituvan_a	  			= 0;
//						double tientrahangthang_a			= 0;
//						double tinhphitattoan_a	  			= 0;
//						
//						double tinhphitranotruochan	  		= 0;
//						double tienlaithang 				= 0;
//						double tinhphidichvu	  			= 0;
//						double tinhphituvan	  				= 0;
//						double tientrahangthang				= 0;
//						double tinhphitattoan	  			= 0;
//						
//						
////						double sumTiemlai	  			= 0;
////						double sumPhituvan	  			= 0; // phituvan = phi tu van
////						double sumPhiquanly	  			= 0; //phiquanly = phi dich vu
//						if(loaitrano == 1){
//							//Lịch trả nợ theo dư nợ giảm dần						
//							
//							int kyvay = 0;
//							for (int i = 1; i<= sothangvay ; i++) {
//								
////								if(i == 0){
////									 Document doc = new Document("idMinhhoa", billID)
////											 .append("kyTrano",getTimeOut(i))
////										     .append("gocConlai", sotienvay)
////										     .append("gocTramoiky", 0)
////										     .append("laiThang", 0)
////											 .append("traHangthang", 0)
////											 .append("phiTuvan", 0)
////											 .append("phiDichvu", 0)
////											 .append("phiTranotruochan", 0)
////											 .append("tattoanTruochan", 0);
////											 array.add(doc);
////											 sumTiemlai = 0;
////								}else{						
//									
//									double tiengoctramoiky_a 			= sotienvay/sothangvay;;
//									double tiencantt					= tienThanhtoan(userName, billID, sotienvay, sothangvay, loaitrano, listFee);
//									for (Fees fees : listFee) {
//										switch (String.valueOf(fees.getFee_type())) {
//										case "1":			
//											if(fees.getFix_fee_amount() <= 0){
//												laixuatNam 				= (double) fees.getFix_fee_percent();
//												tienlaithang_a 			= (sotienconlai_a - kyvay * sotienconlai_a / sothangvay ) * laixuatNam * 30.41666667 / 365;
//											}else{
//												laixuatNam 				= (double) fees.getFix_fee_amount();
//												tienlaithang_a 			= laixuatNam;
//											}						
//											break;			
//										case "2":			
//											if(fees.getFix_fee_amount() <= 0){
//												phituvan 				= (double) fees.getFix_fee_percent();
//												tinhphituvan_a	  		= (sotienconlai_a - kyvay * sotienconlai_a / sothangvay ) * phituvan * 30.41666667 / 365;
//											}else{
//												phituvan 				= (double) fees.getFix_fee_amount();
//												tinhphituvan_a	  		= phituvan;
//											}	
//											break;	
//										case "3":			
//											if(fees.getFix_fee_amount() <= 0){
//												phidichvu 				= (double) fees.getFix_fee_percent();
//												tinhphidichvu_a	  		= (sotienconlai_a - kyvay * sotienconlai_a / sothangvay ) * phidichvu * 30.41666667 / 365;
//											}else{
//												phidichvu 				= (double) fees.getFix_fee_amount();
//												tinhphidichvu_a	  		= phidichvu;
//											}	
//											break;	
//										case "4":			
//											if(fees.getFix_fee_amount() <= 0){
//												phitratruochan 			= (double) fees.getFix_fee_percent();
//												tinhphitranotruochan_a	= (sotienconlai_a - kyvay * sotienconlai_a / sothangvay ) * phitratruochan;
//											}else{
//												phitratruochan 			= (double) fees.getFix_fee_amount();
//												tinhphitranotruochan_a	= phitratruochan;
//											}	
//											break;
//										case "5":									
//											if(fees.getFix_fee_amount() <= 0){
//												phitattoantruochan 		= (double) fees.getFix_fee_percent();
//												tinhphitattoan_a		= (sotienconlai_a - kyvay * sotienconlai_a / sothangvay ) * phitattoantruochan;
////												tinhphitattoan_a	  	= sotienconlai_a + tienlaithang_a + tinhphidichvu_a + tinhphituvan_a + tinhphitranotruochan_a;
//											}else{
//												phitattoantruochan 		= (double) fees.getFix_fee_amount();
//												tinhphitattoan_a	  	= phitattoantruochan;
//											}	
//											break;
//										default:
//											break;
//										}
//									}
//									//'1:Lai suat, 2:Phi tu van, 3:phi dich vu,4:phi tra no qua han,5:phi tat toan qua han',
////									ObjMinhhoa objMinhhoa = new ObjMinhhoa();
////									tiengoctramoiky_a 					= sotienvay/sothangvay;
////									double tinhphitranotruochan_a	  	= sotienconlai_a * phitratruochan;						
////									double tienlaithang_a 				= sotienconlai_a * laixuatNam * 30 / 365;
////									double tinhphidichvu_a	  			= sotienconlai_a * phidichvu * 30 / 365;
////									double tinhphituvan_a	  			= sotienconlai_a * phituvan * 30 / 365;					
////									tientrahangthang_a					= tiengoctramoiky_a + tienlaithang_a + tinhphituvan_a + tinhphidichvu_a;
////									double tinhphitattoan_a	  			= sotienconlai_a + tienlaithang_a + tinhphidichvu_a + tinhphituvan_a + tinhphitranotruochan_a;
////									sotienconlai_a	  					= sotienconlai_a - tiengoctramoiky_a;
////									objMinhhoa.setKyTrano(getTimeOut(i));
////									objMinhhoa.setGocConlai(sotienconlai_a);
////									objMinhhoa.setGocTramoiky(tiengoctramoiky_a);
////									objMinhhoa.setLaiThang(tienlaithang_a);
////									objMinhhoa.setTraHangthang(tientrahangthang_a);
////									objMinhhoa.setPhiTuvan(tinhphituvan_a);
////									objMinhhoa.setPhiDichvu(tinhphidichvu_a);
////									objMinhhoa.setPhiTranotruochan(tinhphitranotruochan_a);
////									objMinhhoa.setTattoanTruochan(tinhphitattoan_a);
//				//					array.add(objMinhhoa);
//									
//									//'1:Lai suat, 2:Phi tu van, 3:phi dich vu,4:phitra no trươc han,5:phi tat toan truoc han',
//
////									 Document doc = new Document("idMinhhoa", billID)
////											 .append("kyVay",getTimeOut(i))
////											 .append("kyTrano",getTimeOut(i))
////										     .append("gocConlai",  Math.round(sotienconlai_a))
////										     .append("gocTramoiky",  Math.round(tiengoctramoiky_a)) 
////										     .append("laiThang",  Math.round(tienlaithang_a))
////											 .append("traHangthang",  Math.round(tientrahangthang_a))
////											 .append("phiTuvan",  Math.round(tinhphituvan_a))
////											 .append("phiDichvu",  Math.round(tinhphidichvu_a))
////											 .append("phiTranotruochan",  Math.round(tinhphitranotruochan_a))
////											 .append("tattoanTruochan",  Math.round(tinhphitattoan_a));
////									 array.add(doc);
//									double tiengoc = tiencantt - (tienlaithang_a + tinhphituvan_a + tinhphidichvu_a);
//									gocconlai = gocconlai - tiengoc;
//									Document doc = new Document("idMinhhoa", billID)
//											 .append("Kyvay",i)
//											 .append("Ngaythanhtoan",				getNgayvay(ngayvay))
//										     .append("Sotiencanthanhtoan",  		Math.round(tiencantt))
//										     .append("Tiengoc",  					Math.round(tiengoc) )
//										     .append("Tienlai",  					Math.round(tienlaithang_a))
//											 .append("Phituvandichvu",  			Math.round(tinhphituvan_a))
//											 .append("Phiquanly",  					Math.round(tinhphidichvu_a))
//											 .append("Gocconlaisauthanhtoanky",  	Math.round(gocconlai))
//											 .append("Phitattoan",  				Math.round(tinhphitattoan_a))
////											 .append("Phitranoquahan",  Math.round(tinhphitranotruochan_a))
//											 .append("Sotientattoantaikynay",  		Math.round(gocconlai + tinhphitattoan_a));
//									 array.add(doc);
//									 
//									 TblLoanBill tblLoanBill = new TblLoanBill();
//									 tblLoanBill.setLoanId(loanID);
//									 tblLoanBill.setLoanRemainAmount((new Double(gocconlai)).longValue());
//									 tblLoanBill.setCreatedDate(new Date());
//									 tblLoanBill.setAmtToDecrYourLoan(new BigDecimal(tiengoc));
//									 tblLoanBill.setMonthlyInterest(new BigDecimal(tienlaithang_a));
//									 tblLoanBill.setTotalOnAMonth(new BigDecimal(tiencantt));
//									 tblLoanBill.setAdvisoryFee(new BigDecimal(tinhphituvan_a));
//									 tblLoanBill.setServiceFee(new BigDecimal(tinhphidichvu_a));
//									 tblLoanBill.setExtFeeIfIndPayBefore(new BigDecimal(tinhphitattoan_a));
//									 tblLoanBill.setTotalPayIfSettleRequest(new BigDecimal(gocconlai + tinhphitattoan_a));
//									 feesListSet.add(tblLoanBill);
////									  `loan_id` int(11) NOT NULL COMMENT 'id khoan vay',
////									  `loan_remain_amount` decimal(10,0) NOT NULL COMMENT 'So tien con phai tra/goc',						
////									  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'ngay tao',
////									  `amt_to_decr_your_loan` decimal(20,0) DEFAULT NULL,
////									  `monthly_interest` decimal(20,0) DEFAULT NULL COMMENT 'Tien lai tra hang thang',
////									  `total_on_a_month` decimal(20,0) DEFAULT NULL COMMENT 'Tien lai thang thang',
////									  `advisory_fee` decimal(20,0) DEFAULT NULL COMMENT 'Phi tu van',
////									  `service_fee` decimal(20,0) DEFAULT NULL COMMENT 'Phi dich vu',
////									  `ext_fee_if_ind_pay_before` decimal(20,0) DEFAULT NULL COMMENT 'Phi tra no truoc han',
////									  `total_pay_if_settle_request` decimal(20,0) DEFAULT NULL COMMENT 'Tong tien phai thanh toan neu tat'' toan truoc han.',
////									  `day_must_pay` int(8) DEFAULT NULL COMMENT 'Ngay phai thanh toan: yyyyMMdd',
////									   Document doc = new Document("idMinhhoa", billID)
////											 .append("kyTrano",getTimeOut(i))  					// day_must_pay
////										     .append("gocConlai", sotienconlai)					// loan_remain_amount
////										     .append("gocTrakycuoi", tiengoctrakycuoi)
////										     .append("laiThang", tienlaithang)					// total_on_a_month
////											 .append("traHangthang", tientrahangthang)			// monthly_interest
////											 .append("phiTuvan", tinhphituvan)					// advisory_fee
////											 .append("phiDichvu", tinhphidichvu) 				// service_fee
////											 .append("phiTranotruochan", tinhphitranotruochan) 	// ext_fee_if_ind_pay_before
////											 .append("tattoanTruochan", tinhphitattoan);  		//total_pay_if_settle_request
////											 array.add(doc); 
////								}
//									 
//									 
//									 
//									 
//									 
//									 
//									 
//									 
//									 
//									 
//									 
//									 kyvay = kyvay + 1;
//									 ngayvay = getNgayvayNew(getNgayvay(ngayvay));
////								}
//							}
//						}else{
//							//Lịch trả nợ gốc cuối kỳ	
////							double sotientattoantaikynay = 0;
//							for (int i = 1; i<= sothangvay ; i++) {
//								double sotienconlai = sotienvay;
////								if(i == 0){
////									 Document doc = new Document("idMinhhoa", billID)
////											 .append("kyTrano",getTimeOut(i))
////										     .append("gocConlai", sotienconlai)
////										     .append("gocTrakycuoi", 0)
////										     .append("laiThang", 0)
////											 .append("traHangthang", 0)
////											 .append("phiTuvan", 0)
////											 .append("phiDichvu", 0)
//////										 .append("phiTranotruochan", 0)
////											 .append("tattoanquahan", 0);
////											 array.add(doc);
////								}else{
//									int check = 0;
//									ObjMinhhoa objMinhhoa = new ObjMinhhoa();
//									for (Fees fees : listFee) {
//										switch (String.valueOf(fees.getFee_type())) {
//										case "1":			
//											if(fees.getFix_fee_amount() <= 0){
//												laixuatNam 				= (double) fees.getFix_fee_percent();
//												tienlaithang 			= sotienconlai * laixuatNam * 30 / 365;
//											}else{
//												laixuatNam 				= (double) fees.getFix_fee_amount();
//												tienlaithang 			= laixuatNam;
//											}						
//											break;			
//										case "2":			
//											if(fees.getFix_fee_amount() <= 0){
//												phituvan 				= (double) fees.getFix_fee_percent();
//												tinhphituvan	  		= sotienconlai * phituvan * 30 / 365;
//											}else{
//												phituvan 				= (double) fees.getFix_fee_amount();
//												tinhphituvan	  		= phituvan;
//											}	
//											break;	
//										case "3":			
//											if(fees.getFix_fee_amount() <= 0){
//												phidichvu 				= (double) fees.getFix_fee_percent();
//												tinhphidichvu	  		= sotienconlai * phidichvu * 30 / 365;
//											}else{
//												phidichvu 				= (double) fees.getFix_fee_amount();
//												tinhphidichvu	  		= phidichvu;
//											}	
//											break;	
//										case "4":			
//											if(fees.getFix_fee_amount() <= 0){
//												phitratruochan 			= (double) fees.getFix_fee_percent();
//												tinhphitranotruochan	= sotienconlai * phitratruochan;
//											}else{
//												phitratruochan 			= (double) fees.getFix_fee_amount();
//												tinhphitranotruochan	= phitratruochan;
//											}	
//											break;
//										case "5":									
//											if(fees.getFix_fee_amount() <= 0){
//												check = 1;
//												phitattoantruochan = (double) fees.getFix_fee_percent();									
//											}else{										
//												phitattoantruochan 		= (double) fees.getFix_fee_amount();
//												tinhphitattoan			= phitattoantruochan;
//											}	
//											break;
//										default:
//											break;
//										}
//									}
//									if(check == 1){
//										tinhphitattoan	  		= sotienconlai + tienlaithang + tinhphidichvu + tinhphituvan + tinhphitranotruochan;
//									}
//									tientrahangthang					= tienlaithang + tinhphituvan + tinhphidichvu;
////									double tiengoctrakycuoi 			= sotienconlai;
////									objMinhhoa.setKyTrano(getTimeOut(1));
////									objMinhhoa.setGocConlai(sotienconlai);
////									objMinhhoa.setGocTrakycuoi(tiengoctrakycuoi);
////									objMinhhoa.setLaiThang(tienlaithang);
////									objMinhhoa.setTraHangthang(tientrahangthang);
////									objMinhhoa.setPhiTuvan(tinhphituvan);
////									objMinhhoa.setPhiDichvu(tinhphidichvu);
////									objMinhhoa.setPhiTranotruochan(tinhphitranotruochan);
////									objMinhhoa.setTattoanTruochan(tinhphitattoan);
//					//				array.add(objMinhhoa);
//									
//									Document doc = new Document("idMinhhoa", billID)
////											 .append("kyTrano",				getNgayvay(ngayvay))
////										     .append("gocConlai", 			Math.round(sotienconlai))
////										     .append("gocTrakycuoi",  		Math.round(tiengoctrakycuoi))
////										     .append("laiThang",  			Math.round(tienlaithang))
////											 .append("traHangthang",  		Math.round(tientrahangthang))
////											 .append("phiTuvan",  			Math.round(tinhphituvan))
////											 .append("phiDichvu",  			Math.round(tinhphidichvu))
//////										 .append("phiTranoquahan", 		Math.round(tinhphitranotruochan))
////											 .append("tattoanquahan",  		Math.round(tinhphitattoan));
//									
//											 .append("Kyvay",						i)
//											 .append("Ngaythanhtoan",				getNgayvay(ngayvay))
//										     .append("Sotiencanthanhtoan",  		Math.round(tientrahangthang))
//										     .append("Tiengoc",  					Math.round(sotienconlai) )
//										     .append("Tienlai",  					Math.round(tienlaithang))
//											 .append("Phituvandichvu",  			Math.round(tinhphituvan))
//											 .append("Phiquanly",  					Math.round(tinhphidichvu))
//											 .append("Gocconlaisauthanhtoanky",  	Math.round(sotienconlai))
//											 .append("Phitattoan",  				Math.round(tinhphitranotruochan))
//				//							 .append("Phitranoquahan",  			Math.round(tinhphitranotruochan_a))
//											 .append("Sotientattoantaikynay",  		Math.round(tinhphitattoan));
//											 
////											 		 Gốc còn lại khi trả trước hạn  => Gocconlaisauthanhtoanky
////													 Gốc trả cuối kỳ => Tiengoc
////													 Trả hàng tháng => Sotiencanthanhtoan
////													 Phí tư vấn => Phituvandichvu
////													 Phí dịch vụ => Phiquanly
////													 Phí trả nợ trước hạn (Nếu có) => Phitattoan
////													 Tất toán trước hạn (Nếu có) => Sotientattoantaikynay
////											 
//											 
//											 array.add(doc);
//											 
//											 TblLoanBill tblLoanBill = new TblLoanBill();
//											 tblLoanBill.setLoanId(loanID);
//											 tblLoanBill.setLoanRemainAmount((new Double(gocconlai)).longValue());
//											 tblLoanBill.setCreatedDate(new Date());
//											 tblLoanBill.setAmtToDecrYourLoan(new BigDecimal(sotienconlai));
//											 tblLoanBill.setMonthlyInterest(new BigDecimal(tienlaithang));
//											 tblLoanBill.setTotalOnAMonth(new BigDecimal(tientrahangthang));
//											 tblLoanBill.setAdvisoryFee(new BigDecimal(tinhphituvan));
//											 tblLoanBill.setServiceFee(new BigDecimal(tinhphidichvu));
//											 tblLoanBill.setExtFeeIfIndPayBefore(new BigDecimal(tinhphitranotruochan));
//											 tblLoanBill.setTotalPayIfSettleRequest(new BigDecimal(tinhphitattoan));
//											 feesListSet.add(tblLoanBill);
//											 
//											 ngayvay = getNgayvayNew(getNgayvay(ngayvay));
////								}
//							}
//						}
//
//						//Thì tiền tra gốc hàng tháng = số tiền vay / số tháng vay
//						//Tiền lãi hàng tháng = số tiền gốc còn lại * lãi năm * số ngày trong tháng / số ngày trong năm (mặc định là 365)
//						//Phí dịch vụ sẽ tính bằng = số tiền còn lại * phi dich vu / ngày trong năm * ngày trong tháng
//						//phí tất toán trước hạn chỉ bằng = tiền gốc còn lại + lãi trong tháng + phí dịch vụ trong tháng + phi tư vấn trong tháng + phí trả nợ trước hạn (tính theo tháng)
//						FileLogger.log("illustration: " + userName+ " illustrationIns array insert DB:" + array, LogType.BUSSINESS);
//
//						return feesListSet;
//					} catch (Exception e) {
//						FileLogger.log("illustration: " + userName+ " illustrationIns exception" + e, LogType.ERROR);
//						e.printStackTrace();
//					}
//					return null;
//				}
//		
//	public double tienThanhtoan(String userName, String billID, double sotienvay, double sothangvay, double loaitrano, List<Fees> listFee){
//		double laixuatNam 					= 0;
//		double phidichvu					= 0;
//		double phituvan 					= 0;
//		double sotienconlai_a 				= sotienvay;
//		double tienlaithang_a 				= 0;
//		double tinhphidichvu_a	  			= 0;
//		double tinhphituvan_a	  			= 0;
//		
//		double sumTiemlai	  				= 0;
//		double sumPhituvan	  				= 0; // phituvan = phi tu van
//		double sumPhiquanly	  				= 0; //phiquanly = phi dich vu
//		double tienTT 						= 0;
//		try {
//			int kyvay = 0;
//			for (int i = 0; i<= sothangvay ; i++) {
//				
//					double tiengoctramoiky_a 			= sotienvay/sothangvay;;
//					
//					for (Fees fees : listFee) {
//						switch (String.valueOf(fees.getFee_type())) {
//						case "1":			
//							if(fees.getFix_fee_amount() <= 0){
//								laixuatNam 				= (double) fees.getFix_fee_percent();
//								tienlaithang_a 			= (sotienconlai_a - kyvay * sotienconlai_a / sothangvay ) * laixuatNam * 30.41666667 / 365;
//							}else{
//								laixuatNam 				= (double) fees.getFix_fee_amount();
//								tienlaithang_a 			= laixuatNam;
//							}						
//							break;			
//						case "2":			
//							if(fees.getFix_fee_amount() <= 0){
//								phituvan 				= (double) fees.getFix_fee_percent();
//								tinhphituvan_a	  		= (sotienconlai_a - kyvay * sotienconlai_a / sothangvay ) * phituvan * 30.41666667 / 365;
//							}else{
//								phituvan 				= (double) fees.getFix_fee_amount();
//								tinhphituvan_a	  		= phituvan;
//							}	
//							break;	
//						case "3":			
//							if(fees.getFix_fee_amount() <= 0){
//								phidichvu 				= (double) fees.getFix_fee_percent();
//								tinhphidichvu_a	  		= (sotienconlai_a - kyvay * sotienconlai_a / sothangvay ) * phidichvu * 30.41666667 / 365;
//							}else{
//								phidichvu 				= (double) fees.getFix_fee_amount();
//								tinhphidichvu_a	  		= phidichvu;
//							}	
//							break;		
//						default:
//							break;
//						}
//					}				
//					
//					//'1:Lai suat, 2:Phi tu van, 3:phi dich vu,4:phitra no trươc han,5:phi tat toan truoc han',
//
//					 System.out.println("sumTiemlai0: " + sumTiemlai);
//					 sumTiemlai = sumTiemlai + tienlaithang_a;
//					 sumPhituvan = sumPhituvan + tinhphituvan_a;// phituvan = phi tu van
//					 sumPhiquanly = sumPhiquanly + tinhphidichvu_a;//phiquanly = phi dich vu
//					 kyvay = kyvay + 1;
//					 System.out.println("----------------");
//					 System.out.println("kyvay: " + kyvay);
//					 System.out.println("tienlaithang_a: " + tienlaithang_a);
//					 System.out.println("sumTiemlai1: " + sumTiemlai);
//				
//			}
//			System.out.println("sumTiemlai:"+ sumTiemlai);
//			System.out.println("sumPhituvan:"+ sumPhituvan);
//			System.out.println("sumPhiquanly:"+ sumPhiquanly);
//			System.out.println("sothangvay:"+ sothangvay);
//			tienTT = (sumTiemlai + sumPhituvan + sumPhiquanly + sotienvay) / sothangvay;
//			System.out.println("tienTT:"+ tienTT);
//			return tienTT;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return tienTT;
//	}
//	
//	
//	
//	public static String getTimeNow() {
//		SimpleDateFormat format = new SimpleDateFormat(MainCfg.FORMATTER_DATETIME);
//		return format.format(new Date());
//	}
//	public static String getTimeNowDate() {
//		SimpleDateFormat format = new SimpleDateFormat(MainCfg.FORMATTER_DATE);
//		return format.format(new Date());
//	}
//	public static String getTimeOut(int dateBefore) {
//		SimpleDateFormat format = new SimpleDateFormat(MainCfg.FORMATTER_DATE_OUT);
//		Date dt = new Date();
//		Calendar c = Calendar.getInstance(); 
//		c.setTime(dt); 
//		c.add(Calendar.MONTH, dateBefore);
//		dt = c.getTime();
////		System.out.println(format.format(dt));
//		return format.format(dt);
//	}
//	
//	public static String getTimeEXP() {
//		SimpleDateFormat format = new SimpleDateFormat(MainCfg.FORMATTER_DATE);
//		Date dt = new Date();
//		Calendar c = Calendar.getInstance(); 
//		c.setTime(dt); 
//		c.add(Calendar.DATE, 1);
//		dt = c.getTime();
////		System.out.println(format.format(dt));
//		return format.format(dt);
//	}
//	
//	public static String getNgayvay(String date){
//		String result = "";
//		try {
//			Date date1 = new SimpleDateFormat("yyyyMMdd").parse(date);
//			SimpleDateFormat format = new SimpleDateFormat(MainCfg.FORMATTER_DATE_OUT);
//			Calendar calendar = new GregorianCalendar(/* remember about timezone! */);
//			calendar.setTime(date1);
//			calendar.add(Calendar.DATE, 30);
//			Date dateReturn = calendar.getTime();
//			System.out.println(dateReturn);
//			result = format.format(dateReturn);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return result;
//	}
//	
//	public static String getNgayvayNew(String date){
//		String result = "";
//		try {
//			Date date1 = new SimpleDateFormat("MM/dd/yyyy").parse(date);
//			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
//			Calendar calendar = new GregorianCalendar(/* remember about timezone! */);
//			calendar.setTime(date1);
//			Date dateReturn = calendar.getTime();
//			System.out.println(dateReturn);
//			result = format.format(dateReturn);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return result;
//	}
//	
//	public static String getRandomStr(int length) {
//		String stock = "0123456789abcdefghijklmnopqrstuvwxyz";
//		String ran = "";
//		for (int i = 0; i < length; i++) {
//			ran += stock.charAt(new Random().nextInt(stock.length() - 1));
//		}
//		return ran;
//	}
//	
//	public static int getBillid() {
//		int random = (int)(Math.random()*(99999-00001+1)+00001);  
//		return random;
//	}
//	
//
//	public static void main(String[] args) {
//		try {
////			System.out.println(getRandomStr(8));
////			System.out.println(URLEncoder.encode("yQ/N3ntKC40cDV9Q0ha4J5b3X77Ws0tcE616aZJhy+E\u003d", "UTF-8"));
////			System.out.println(URLDecoder.decode("yQ%2FN3ntKC40cDV9Q0ha4J5b3X77Ws0tcE616aZJhy%2BE%3D", "UTF-8"));
////			System.out.println(getTimeEXP());
////			SimpleDateFormat format = new SimpleDateFormat(MainCfg.FORMATTER_DATE);
////			format.format(new Date());
////			Date dt = new Date();
////			Calendar c = Calendar.getInstance(); 
////			c.setTime(dt); 
////			c.add(Calendar.DATE, 1);
////			dt = c.getTime();
////			
////			long a = 10;
////			long b = 2;
////			System.out.println(a/b);
////			if(a == 10){
////				System.out.println(a);
////			}
////			System.out.println(MD5.hash(MD5.hash("123456")));
////			Bussiness bussiness = new Bussiness();			
////			BaseMongoDB mongoDB = new BaseMongoDB();
////			String billID = getTimeNowDate() + "_" + getBillid();
////			ArrayList<Document> illustration = bussiness.illustration("Phuongvd", billID, 18d, 25.2d, 8d, 8d, 30000000d, 24d, 2d, 54d);
////			System.out.println(illustration);
////			mongoDB.insertDocument(illustration, "tbl_loan_bill");
//			
//			
////			double rate = 10.2566645;
//			 
////	        System.out.println("Su dung phuong thuc Math.round()");
//	        // lam tron xuong gom 1 so thap phan, nhan va chia cho 10
////	        System.out.println((double) Math.round(rate));
//			Bussiness bussiness = new Bussiness();
//			AccountHome accountHome = new AccountHome();
//			Account acc = accountHome.getAccountUsename("dinhphuong.v@gmail.com");
//			if(ValidData.checkNull(acc.getBranchId()) == true){
////				JSONParser parser = new JSONParser();
//////				JSONArray jsonArray = (JSONArray) acc.getBranchId();
////		    	JSONObject jsonParse = (JSONObject) acc.getBranchId();
////		    	for (String key: jsonParse()) {
////		    	    jo.get(key);
////		    	}
//				JsonParser jsonParser = new JsonParser();
//				JSONObject isJsonObject = (JSONObject)new JSONObject(acc.getBranchId());
//				Iterator<String> keys = isJsonObject.keys();
//
//				while(keys.hasNext()) {
//				    String key = keys.next();
//				    System.out.println(key);
////				    if (jsonObject.get(key) instanceof JSONObject) {
////				          // do something with jsonObject here      
////				    }
//				}
//			}else{
//				System.out.println("null");
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//}
