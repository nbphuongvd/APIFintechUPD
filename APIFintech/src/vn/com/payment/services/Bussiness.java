package vn.com.payment.services;

import vn.com.payment.config.LogType;
import vn.com.payment.config.MainCfg;
import vn.com.payment.entities.Account;
import vn.com.payment.entities.TblBanks;
import vn.com.payment.entities.TblImages;
import vn.com.payment.entities.TblLoanBill;
import vn.com.payment.entities.TblLoanExpertiseSteps;
import vn.com.payment.entities.TblLoanReqDetail;
import vn.com.payment.entities.TblLoanRequest;
import vn.com.payment.entities.TblLoanRequestAskAns;
import vn.com.payment.entities.TblLoanRequestAskAnsGen;
import vn.com.payment.entities.TblProduct;
import vn.com.payment.entities.TblRateConfig;
import vn.com.payment.home.AccountHome;
import vn.com.payment.home.BaseMongoDB;
import vn.com.payment.home.DBFintechHome;
import vn.com.payment.home.TblBanksHome;
import vn.com.payment.home.TblLoanBillHome;
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
import vn.com.payment.object.ReqContractList;
import vn.com.payment.object.ReqCreaterLoan;
import vn.com.payment.object.ReqLogin;
import vn.com.payment.object.ReqStepLog;
import vn.com.payment.object.ResAllContractList;
import vn.com.payment.object.ResChangePass;
import vn.com.payment.object.ResContractDetail;
import vn.com.payment.object.ResContractList;
import vn.com.payment.object.ResCreaterLoan;
import vn.com.payment.object.ResLogin;
import vn.com.payment.object.ResStepLog;
import vn.com.payment.object.TokenRedis;
import vn.com.payment.redis.RedisBusiness;
import vn.com.payment.thread.ThreadInsertLogStep;
import vn.com.payment.ultities.Commons;
import vn.com.payment.ultities.FileLogger;
import vn.com.payment.ultities.GsonUltilities;
import vn.com.payment.ultities.MD5;
import vn.com.payment.ultities.TripleDES;
import vn.com.payment.ultities.Utils;
import vn.com.payment.ultities.ValidData;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.json.JSONArray;
import org.json.simple.parser.JSONParser;
import org.omg.PortableServer.ServantLocatorOperations;
import org.springframework.util.Base64Utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class Bussiness {
	AccountHome accountHome = new AccountHome();
	TblProductHome tblProductHome = new TblProductHome();
	TblRateConfigHome tblRateConfigHome = new TblRateConfigHome();
	TblLoanRequestHome tbLoanRequestHome = new TblLoanRequestHome();
	TblLoanBillHome tblLoanBillHome = new TblLoanBillHome();
	TblBanksHome tblBanksHome = new TblBanksHome();
	BaseMongoDB mongoDB = new BaseMongoDB();
	Caculator caculator = new Caculator();
	UserInfo userInfo = new UserInfo();
	ValidData validData = new ValidData();
	DBFintechHome dbFintechHome = new DBFintechHome();
	Gson gson = new Gson();
	long statusSuccess = 100l;
	long statusFale = 111l;
	long statusFaleToken = 104l;
	int statusPending = 999;
	int statusReject = 110;

	public Response getContractNumber(String dataContract) {
		FileLogger.log("----------------Bat dau getContractNumber--------------------------", LogType.BUSSINESS);
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		ContractObjRes contractObjRes = new ContractObjRes();
		try {
			FileLogger.log("getContractNumber dataContract: " + dataContract, LogType.BUSSINESS);
			ContractObj contractObj = gson.fromJson(dataContract, ContractObj.class);
			if (ValidData.checkNull(contractObj.getUsername()) == false
					|| ValidData.checkNull(contractObj.getToken()) == false) {
				FileLogger.log("getContractNumber: " + contractObj.getUsername() + " invalid : ", LogType.BUSSINESS);
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				contractObjRes.setStatus(statusFale);
				contractObjRes.setMessage("Lay thong tin that bai - Invalid message request");
				contractObjRes.setContract_number("");
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(contractObjRes.toJSON())
						.build();
			}

			boolean checkLG = userInfo.checkLogin(contractObj.getUsername(), contractObj.getToken());
			if (checkLG) {
				// Check thong tin hop dong co trong DB chua
				BigDecimal getSeq = tbLoanRequestHome.getSeqContract();
				FileLogger.log("getContractNumber: " + contractObj.getUsername() + " getSeq : " + getSeq,
						LogType.BUSSINESS);
				if (getSeq != null) {
					Account acc = accountHome.getAccountUsename(contractObj.getUsername());
					if (ValidData.checkNullBranch(acc.getBranchId()) == true) {
						JSONObject isJsonObject = (JSONObject) new JSONObject(acc.getBranchId());
						Iterator<String> keys = isJsonObject.keys();
						String branchID = "";
						while (keys.hasNext()) {
							branchID = keys.next();
						}
						String genContract = MainCfg.prefixContract + "." + branchID + "." + getSeq;
						FileLogger.log(
								"getContractNumber: " + contractObj.getUsername() + " genContract : " + genContract,
								LogType.BUSSINESS);
						boolean checkContract = tbLoanRequestHome.checktblLoanRequest(genContract);
						if (checkContract) {
							FileLogger.log(
									"getContractNumber: " + contractObj.getUsername() + " genContract : " + genContract,
									LogType.BUSSINESS);
							contractObjRes.setStatus(statusSuccess);
							contractObjRes.setMessage("Lay thong tin thanh cong");
							contractObjRes.setContract_number(genContract);
						} else {
							FileLogger.log("contractObj: " + contractObj.getUsername() + " check login false:",
									LogType.BUSSINESS);
							contractObjRes.setStatus(statusFale);
							contractObjRes.setMessage("Lay thong tin that bai - Co the trung ma hop dong");
							contractObjRes.setContract_number("");
						}
					} else {
						FileLogger.log("contractObj: " + contractObj.getUsername() + " check login false:",
								LogType.BUSSINESS);
						contractObjRes.setStatus(statusFale);
						contractObjRes.setMessage("Lay thong tin that bai - User chua thuoc chi nhanh nao");
						contractObjRes.setContract_number("");
					}
				} else {
					FileLogger.log("contractObj: " + contractObj.getUsername() + " getSeq false:", LogType.BUSSINESS);
					contractObjRes.setStatus(statusFale);
					contractObjRes.setMessage("Lay thong tin that bai - Co loi xay ra");
					contractObjRes.setContract_number("");
				}
			} else {
				FileLogger.log("contractObj: " + contractObj.getUsername() + " check login false:", LogType.BUSSINESS);
				contractObjRes.setStatus(statusFale);
				contractObjRes.setMessage("Lay thong tin that bai - Login false");
				contractObjRes.setContract_number("");
			}
			FileLogger.log(
					"contractObj: " + contractObj.getUsername() + " response to client:" + contractObjRes.toJSON(),
					LogType.BUSSINESS);
			FileLogger.log("----------------Ket thuc contractObj ", LogType.BUSSINESS);
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(contractObjRes.toJSON()).build();
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("----------------Ket thuc contractObj Exception " + e.getMessage(), LogType.ERROR);
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
					|| ValidData.checkNull(productReq.getProduct_modal()) == false) {
				FileLogger.log("getProduct: " + productReq.getUsername() + " invalid : ", LogType.BUSSINESS);
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				producResAll.setStatus(statusFale);
				producResAll.setSuggest_info(productRes);
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(producResAll.toJSON()).build();
			}
			boolean checkLG = userInfo.checkLogin(productReq.getUsername(), productReq.getToken());
			if (checkLG) {
				TblProduct tblProduct = tblProductHome.getProduct(String.valueOf(productReq.getProduct_type()),
						productReq.getProduct_brand(), productReq.getProduct_modal());

				if (tblProduct != null) {
					producResAll.setStatus(statusSuccess);

					productRes.setProduct_type(tblProduct.getProductType());
					productRes.setProduct_brand(tblProduct.getProductName());
					productRes.setProduct_modal(tblProduct.getProductCode());
					productRes.setTotal_run(productReq.getTotal_run());
					productRes.setProduct_condition(productReq.getProduct_condition());
					productRes.setProduct_own_by_borrower(productReq.getProduct_own_by_borrower());
					productRes.setBuy_a_new_price(tblProduct.getBrandnewPrice());
					productRes.setLoan_price(tblProduct.getLoanPrice());
					// Định giá = [ Giá vay ]x [ tỷ lệ tình trạng sản phẩm ] x [
					// tỷ lệ KM đã đi ] x [ tỷ lệ chính chủ ]
					// accept_loan_price: loan_price * product_condition *
					// total_run * product_own_by_borrower
					long accept_loan_price = productRes.getLoan_price() * productRes.getProduct_condition()
							* productRes.getTotal_run() * productRes.getProduct_own_by_borrower();
					productRes.setAccept_loan_price(accept_loan_price);

					producResAll.setSuggest_info(productRes);
				} else {
					FileLogger.log("getProduct: " + productReq.getUsername() + " tblProduct null:", LogType.BUSSINESS);
					producResAll.setStatus(statusFale);
					producResAll.setSuggest_info(productRes);
				}
			} else {
				FileLogger.log("getProduct: " + productReq.getUsername() + " check login false:", LogType.BUSSINESS);
				producResAll.setStatus(statusFale);
				producResAll.setSuggest_info(productRes);
			}
			FileLogger.log("getProduct: " + productReq.getUsername() + " response to client:" + producResAll.toJSON(),
					LogType.BUSSINESS);
			FileLogger.log("----------------Ket thuc getProduct ", LogType.BUSSINESS);
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(producResAll.toJSON()).build();
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("----------------Ket thuc getProduct Exception " + e.getMessage(), LogType.ERROR);
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
					|| ValidData.checkNullInt(rateConfigReq.getType()) == false) {
				FileLogger.log("getRateConfig: " + rateConfigReq.getUsername() + " invalid : ", LogType.BUSSINESS);
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				rateConfigRes.setStatus(statusFale);
				rateConfigRes.setMessage("Lay thong tin that bai - Invalid message request");
				rateConfigRes.setRate_config(arrRateCfg);
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(rateConfigRes.toJSON()).build();
			}
			boolean checkLG = userInfo.checkLogin(rateConfigReq.getUsername(), rateConfigReq.getToken());
			if (checkLG) {
				List<TblRateConfig> results = tblRateConfigHome.getRateConfig(rateConfigReq.getType());
				if (results != null) {
					rateConfigRes.setStatus(statusSuccess);
					rateConfigRes.setMessage("Lay thong tin thanh cong");
					rateConfigRes.setRate_config(results);
				} else {
					FileLogger.log("getProduct: " + rateConfigReq.getUsername() + " tblProduct null:",
							LogType.BUSSINESS);
					rateConfigRes.setStatus(statusFale);
					rateConfigRes.setMessage("Lay thong tin that bai - Khong tim thay thong tin RateConfig");
					rateConfigRes.setRate_config(arrRateCfg);
				}
			} else {
				FileLogger.log("getRateConfig: " + rateConfigReq.getUsername() + " check login false:",
						LogType.BUSSINESS);
				rateConfigRes.setStatus(statusFale);
				rateConfigRes.setMessage("Lay thong tin that bai - Thong tin login sai");
				rateConfigRes.setRate_config(arrRateCfg);
			}
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			FileLogger.log(
					"getRateConfig: " + rateConfigReq.getUsername() + " response to client:" + rateConfigRes.toJSON(),
					LogType.BUSSINESS);
			FileLogger.log("----------------Ket thuc getRateConfig: ", LogType.BUSSINESS);
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(rateConfigRes.toJSON()).build();
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("----------------Ket thuc getRateConfig Exception " + e.getMessage(), LogType.ERROR);
			rateConfigRes.setStatus(statusFale);
			rateConfigRes.setMessage("Lay thong tin that bai -  Da co loi xay ra");
			rateConfigRes.setRate_config(arrRateCfg);
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(rateConfigRes.toJSON()).build();
		}
	}

	public Response createrLoan(String datacreaterLoan) {
		FileLogger.log("----------------Bat dau createrLoan--------------------------", LogType.BUSSINESS);
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		ResCreaterLoan resCreaterLoan = new ResCreaterLoan();
		try {
			FileLogger.log("createrLoan datacreaterLoan: " + datacreaterLoan, LogType.BUSSINESS);
			ReqCreaterLoan reqCreaterLoan = gson.fromJson(datacreaterLoan, ReqCreaterLoan.class);
			// if (ValidData.checkNull(reqCreaterLoan.getUsername()) == false ||
			// ValidData.checkNull(reqCreaterLoan.getToken()) == false){
			// FileLogger.log("createrLoan: " + reqCreaterLoan.getUsername()+ "
			// invalid : ", LogType.BUSSINESS);
			// resCreaterLoan.setStatus(statusFale);
			// resCreaterLoan.setMessage("Yeu cau that bai - Invalid message
			// request");
			// resCreaterLoan.setRequest_code();
			// }

			ResCreaterLoan resCreaterLoanValid = validData.validCreaterLoan(reqCreaterLoan);
			if (resCreaterLoanValid != null) {
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resCreaterLoanValid.toJSON())
						.build();
			}

			Account acc = accountHome.getAccountUsename(reqCreaterLoan.getUsername());
			int branch_id = 0;
			int room_id = 0;
			if (ValidData.checkNull(acc.getBranchId()) == true) {
				JSONObject isJsonObject = (JSONObject) new JSONObject(acc.getBranchId());
				Iterator<String> keys = isJsonObject.keys();
				while (keys.hasNext()) {
					String key = keys.next();
					System.out.println(key);
					JSONArray msg = (JSONArray) isJsonObject.get(key);
					System.out.println(msg);
					System.out.println(msg.get(0));
					branch_id = Integer.parseInt(key);
					room_id = Integer.parseInt(msg.get(0).toString());
				}
			}
			System.out.println("aaa");
			TblLoanRequestHome tblLoanReqDetailHome = new TblLoanRequestHome();
			BigInteger loanID = tblLoanReqDetailHome.getIDAutoIncrement();
			System.out.println(loanID);
			
//		1	Nhận tiền qua  - disburse_to - tbl_loan_req_detail
//		2	Loại sản phẩm - borrower_type  - tbl_loan_req_detail
//		3	Thương hiệu - product_brand - tbl_loan_req_detail
//		4	Cách tính lãi - calculate_profit_type - tbl_loan_request
//		5	Số tháng vay - loan_for_month - tbl_loan_request
//		6	Serial HĐ - product_serial_no - tbl_loan_req_detail
//		7	Ngày vay - created_date - tbl_loan_request
//		8	Ngày trả - disbursement_date - tbl_loan_req_detail

			TblLoanRequest tblLoanRequest = new TblLoanRequest();
			tblLoanRequest.setLoanId(loanID.intValue());
			tblLoanRequest.setCreatedDate(new Date());
			tblLoanRequest.setEditedDate(new Date());
			tblLoanRequest.setExpireDate(new Date());
			tblLoanRequest.setApprovedDate(new Date());
			tblLoanRequest.setCreatedBy(reqCreaterLoan.getUsername());
			tblLoanRequest.setApprovedBy(reqCreaterLoan.getUsername());
			// tblLoanRequest.setSponsorId(1);
			tblLoanRequest.setLatestUpdate(new Date());
			tblLoanRequest.setLoanCode(reqCreaterLoan.getLoan_code());
			tblLoanRequest.setLoanName(reqCreaterLoan.getLoan_name());
			tblLoanRequest.setContractSerialNum(reqCreaterLoan.getContract_serial_num());
			tblLoanRequest.setBranchId(branch_id);
			tblLoanRequest.setRoomId(room_id);
			tblLoanRequest.setCalculateProfitType((int) reqCreaterLoan.getCalculate_profit_type());
			tblLoanRequest.setLoanForMonth((int) reqCreaterLoan.getLoan_amount());
			tblLoanRequest.setLoanForMonth((int)reqCreaterLoan.getLoan_for_month());

			TblLoanReqDetail tblLoanReqDetail = new TblLoanReqDetail();
			tblLoanReqDetail.setDisburseTo((int)reqCreaterLoan.getDisburse_to());
			tblLoanReqDetail.setProductModal(reqCreaterLoan.getProduct_modal());
			tblLoanReqDetail.setProductBrand(Integer.parseInt(reqCreaterLoan.getProduct_brand()));
			tblLoanReqDetail.setTotalRun((int) reqCreaterLoan.getTotal_run());
			tblLoanReqDetail.setProductCondition((int) reqCreaterLoan.getProduct_condition());
			tblLoanReqDetail.setProductOwnByBorrower((int) reqCreaterLoan.getProduct_own_by_borrower());
			tblLoanReqDetail.setProductSerialNo(reqCreaterLoan.getProduct_serial_no());
			tblLoanReqDetail.setProductColor(reqCreaterLoan.getProduct_color());
			tblLoanReqDetail.setBorrowerType((int) reqCreaterLoan.getBorrower_type());
			tblLoanReqDetail.setDisbursementDate(Integer.parseInt(reqCreaterLoan.getLoan_expect_date()));
			try {
				tblLoanReqDetail.setBorrowerPhone(reqCreaterLoan.getBorrower_phone());
				tblLoanReqDetail.setBorrowerEmail(reqCreaterLoan.getBorrower_email());
			} catch (Exception e) {
			}
			tblLoanReqDetail.setBorrowerId(reqCreaterLoan.getBorrower_id_number());
			tblLoanReqDetail.setDisburseToBankNo(reqCreaterLoan.getDisburse_to_bank_no());
			tblLoanReqDetail.setDisburseToBankName(reqCreaterLoan.getDisburse_to_bank_name());
			tblLoanReqDetail.setDisburseToBankCode(reqCreaterLoan.getDisburse_to_bank_code());
			tblLoanReqDetail.setLoanId(loanID.intValue());

			tblLoanReqDetail.setCreatedDate(new Date());
			tblLoanReqDetail.setEditedDate(new Date());
			tblLoanReqDetail.setExpectAmount(reqCreaterLoan.getLoan_amount());
			tblLoanReqDetail.setApprovedAmount(reqCreaterLoan.getLoan_amount());
			// tblLoanReqDetail.setDisbursementDate(aa.intValue());
			tblLoanReqDetail.setProductValuation(reqCreaterLoan.getProduct_valuation());
			tblLoanReqDetail.setBorrowerIncome(reqCreaterLoan.getBorrower_income());
			tblLoanReqDetail.setBorrowerFullname(reqCreaterLoan.getBorrower_fullname());
			tblLoanReqDetail.setBorrowerAddress(reqCreaterLoan.getBorrower_address());
			tblLoanReqDetail.setIdIssueAt(reqCreaterLoan.getId_issue_at());
			tblLoanReqDetail.setIdIssueDate((int) reqCreaterLoan.getId_issue_date());
			tblLoanReqDetail.setProductDesc(reqCreaterLoan.getProduct_desc());
			tblLoanReqDetail.setBorrowerBirthday((int) reqCreaterLoan.getBorrower_birthday());
			tblLoanReqDetail.setProductMachineNumber(reqCreaterLoan.getProduct_machine_number());
			tblLoanReqDetail.setBankBranch(reqCreaterLoan.getBank_branch());
			tblLoanReqDetail.setDisburseTo((int) reqCreaterLoan.getDisburse_to());
//			tblLoanReqDetail.setFees(reqCreaterLoan.getFees().toString());
			tblLoanReqDetail.setChangeFee(Integer.parseInt(reqCreaterLoan.getChange_fee()));
			
			

			List<TblImages> imagesListSet = new ArrayList<>();
			if (reqCreaterLoan.getImages() != null) {
				List<ObjImage> imagesList = reqCreaterLoan.getImages();
				for (ObjImage objImage : imagesList) {
					TblImages tblImages = new TblImages();
					tblImages.setLoanRequestDetailId(loanID.intValue());
					tblImages.setImageName(objImage.getImage_name());
					tblImages.setImageInputName(objImage.getImage_input_name());
					tblImages.setPartnerImageId(objImage.getPartner_image_id());
					tblImages.setImageType((int) objImage.getImage_type());
					tblImages.setImageByte(objImage.getImage_byte());
					tblImages.setImageUrl(objImage.getImage_url());
					tblImages.setImageIsFront((int) objImage.getImage_is_front());
					imagesListSet.add(tblImages);
				}
			}

			String feeStr = "";
			List<Fees> feesListSet = reqCreaterLoan.getFees();
//			for (Fees fees : feesListSet) {
//				if(feeStr.equals("")){
////					feeStr = feeStr + "[" + fees.toJSON();
//					feeStr = feeStr + fees.toJSON();
//				}else{
//					feeStr = feeStr + "," + fees.toJSON();
//				}
//			}
//			JSONArray jArray = new JSONArray(feeStr);
			tblLoanReqDetail.setFees(gson.toJson(feesListSet));
//			tblLoanReqDetail.setQAThamDinh1(gson.toJson(questionsList));
			// Bussiness bussiness = new Bussiness();
			String billID = Utils.getTimeNowDate() + "_" + Utils.getBillid();
			double sotienvay = (double) reqCreaterLoan.getLoan_amount();
			double sothangvay = (double) reqCreaterLoan.getLoan_for_month();
			double loaitrano = (double) reqCreaterLoan.getCalculate_profit_type();
			List<TblLoanBill> illustrationNewLoanBill = caculator.illustrationNewLoanBill(reqCreaterLoan.getUsername(), billID, sotienvay, sothangvay, reqCreaterLoan.getLoan_expect_date(), loaitrano, feesListSet,loanID.intValue());

			int percentAns = 0;
			TblLoanRequestAskAns tblLoanRequestAskAns = new TblLoanRequestAskAns();
			if ((reqCreaterLoan.getQuestion_and_answears()) != null) {
				List<ObjQuestions> questionsList = reqCreaterLoan.getQuestion_and_answears();
//				List<ObjQuestions> questionsList1 = new ArrayList<>();
//				String q_a_tham_dinh_1 = "";
//				int totalQ = 0;
//				int totalTrus = 0;
//				
//				Gson gson = new Gson();
//				for (ObjQuestions objQuestions : questionsList) {
////					q_a_tham_dinh_1 = q_a_tham_dinh_1 + objQuestions.toJSON();
//					questionsList1.add(objQuestions);
//				}
				tblLoanRequestAskAns.setLoanId(loanID.intValue());
//				tblLoanRequestAskAns.setQAThamDinh1(q_a_tham_dinh_1);
				tblLoanRequestAskAns.setQAThamDinh1(gson.toJson(questionsList));
				// percentAns =
				// caculator.questionPercent(reqCreaterLoan.getUsername(),
				// questionsList);
			}
			FileLogger.log("createrLoan: " + reqCreaterLoan.getUsername() + " percentAns: " + percentAns,
					LogType.BUSSINESS);
			// if (percentAns <= 50) {
			// tblLoanRequest.setFinalStatus(statusReject);
			// tblLoanRequest.setPreviousStatus(statusReject);
			// } else {
			tblLoanRequest.setFinalStatus(statusPending);
			tblLoanRequest.setPreviousStatus(statusPending);
			// }
			System.out.println("createrLoan: " + reqCreaterLoan.getUsername() + " tblLoanReqDetail: "
					+ gson.toJson(tblLoanReqDetail));
			FileLogger.log("createrLoan: " + reqCreaterLoan.getUsername() + " tblLoanReqDetail: "
					+ gson.toJson(tblLoanReqDetail), LogType.BUSSINESS);
			FileLogger.log(
					"createrLoan: " + reqCreaterLoan.getUsername() + " tblLoanRequest: " + gson.toJson(tblLoanRequest),
					LogType.BUSSINESS);
			FileLogger.log("createrLoan: " + reqCreaterLoan.getUsername() + " tblLoanRequestAskAns: "
					+ gson.toJson(tblLoanRequestAskAns), LogType.BUSSINESS);

			boolean checkINS = tblLoanReqDetailHome.createLoanTrans(tblLoanRequest, tblLoanReqDetail, imagesListSet,
					illustrationNewLoanBill, tblLoanRequestAskAns);
			if (checkINS) {
				FileLogger.log("createrLoan: " + reqCreaterLoan.getUsername() + " thanh cong:", LogType.BUSSINESS);
				FileLogger.log("createrLoan: " + reqCreaterLoan.getUsername() + " percentAns:", LogType.BUSSINESS);
				// if(percentAns <= 50){
				// resCreaterLoan.setStatus(tblLoanRequest.getFinalStatus());
				// resCreaterLoan.setMessage("Khoan vay bi tu choi do thieu
				// thong tin");
				// resCreaterLoan.setRequest_code(loanID.longValue());
				// }else{
				resCreaterLoan.setStatus(statusSuccess);
				resCreaterLoan.setMessage("Yeu cau dang duoc xu ly");
				resCreaterLoan.setRequest_code(loanID.longValue());
				// }
				TblLoanExpertiseSteps tblLoanExpertiseSteps = new TblLoanExpertiseSteps();
				tblLoanExpertiseSteps.setLoanId(tblLoanRequest.getLoanId());
				tblLoanExpertiseSteps.setExpertiseUser(tblLoanRequest.getApprovedBy());
				tblLoanExpertiseSteps.setExpertiseDate(Utils.getTimeStampNow());
				tblLoanExpertiseSteps.setExpertiseStatus(tblLoanRequest.getFinalStatus());
				tblLoanExpertiseSteps.setExpertiseStep(1);
				tblLoanExpertiseSteps.setExpertiseComment("");
				tblLoanExpertiseSteps.setLoanCode(tblLoanRequest.getLoanCode());
				tblLoanExpertiseSteps.setAction(reqCreaterLoan.getAction());

				Thread t = new Thread(new ThreadInsertLogStep(tblLoanExpertiseSteps));
				t.start();

			} else {
				FileLogger.log("createrLoan: " + reqCreaterLoan.getUsername() + " that bai:", LogType.BUSSINESS);
				resCreaterLoan.setStatus(statusFale);
				resCreaterLoan.setMessage("Yeu cau that bai");
				// resCreaterLoan.setRequest_code();
			}
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			FileLogger.log(
					"createrLoan: " + reqCreaterLoan.getUsername() + " response to client:" + resCreaterLoan.toJSON(),
					LogType.BUSSINESS);
			FileLogger.log("----------------Ket thuc createrLoan: ", LogType.BUSSINESS);
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resCreaterLoan.toJSON()).build();
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("----------------Ket thuc createrLoan Exception " + e, LogType.ERROR);
			resCreaterLoan.setStatus(statusFale);
			resCreaterLoan.setMessage("Yeu cau that bai - Da co loi xay ra");
			// resCreaterLoan.setRequest_code("");
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resCreaterLoan.toJSON()).build();
		}
	}

	public Response getContractList(String dataGetContractList) {
		FileLogger.log("----------------Bat dau createrLoan--------------------------", LogType.BUSSINESS);
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		ResAllContractList resAllContractList = new ResAllContractList();
		List<ResContractList> resContractList = new ArrayList<>();
		try {
			FileLogger.log("getContractList dataGetContractList: " + dataGetContractList, LogType.BUSSINESS);
			ReqContractList reqContractList = gson.fromJson(dataGetContractList, ReqContractList.class);
			ResAllContractList resCreaterLoanValid = validData.validGetContractList(reqContractList);
			if (resCreaterLoanValid != null) {
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resCreaterLoanValid.toJSON()).build();
			}
			Account acc = accountHome.getAccountUsename(reqContractList.getUsername());
			List<Integer> branchID = new ArrayList<>();
			List<Integer> roomID = new ArrayList<>();
			if (ValidData.checkNull(acc.getBranchId()) == true) {
				JSONObject isJsonObject = (JSONObject) new JSONObject(acc.getBranchId());
				Iterator<String> keys = isJsonObject.keys();
				while (keys.hasNext()) {
					String key = keys.next();
					System.out.println(key);
					JSONArray msg = (JSONArray) isJsonObject.get(key);
					branchID.add(new Integer(key.toString()));
					for (int i = 0; i < msg.length(); i++) {
						roomID.add(Integer.parseInt(msg.get(i).toString()));
					}
				}
			}
			System.out.println("branchID: " + branchID);
			System.out.println("roomID: " + roomID);
			String loan_code = "";
			try {
				loan_code = reqContractList.getLoan_code();
			} catch (Exception e) {
			}
			String final_status = reqContractList.getFinal_status();
			String id_number = reqContractList.getId_number();
			String borrower_name = reqContractList.getBorrower_name();
			String from_date = reqContractList.getFrom_date();
			String to_date = reqContractList.getTo_date();
			String calculate_profit_type = reqContractList.getCalculate_profit_type();
			List<ResContractList> lisResContract = dbFintechHome.listResContractList(branchID, roomID, loan_code,
					final_status, id_number, borrower_name, from_date, to_date, calculate_profit_type,
					reqContractList.getLimit(), reqContractList.getOffSet());
			long total = dbFintechHome.listCountContractList(branchID, roomID, loan_code, final_status, id_number,
					borrower_name, from_date, to_date, calculate_profit_type, reqContractList.getLimit(),
					reqContractList.getOffSet());
			if (lisResContract != null) {
				resAllContractList.setStatus(statusSuccess);
				resAllContractList.setMessage("Yeu cau thanh cong");
				resAllContractList.setContract_list(lisResContract);
				resAllContractList.setTotalRecord(total);
			} else {
				resAllContractList.setStatus(statusFale);
				resAllContractList.setMessage("Yeu cau that bai - Da co loi xay ra");
				resAllContractList.setContract_list(resContractList);
			}
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			FileLogger.log("getContractList: " + reqContractList.getUsername() + " response to client:"
					+ resAllContractList.toJSON(), LogType.BUSSINESS);
			FileLogger.log("----------------Ket thuc getContractList: ", LogType.BUSSINESS);
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resAllContractList.toJSON())
					.build();
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("----------------Ket thuc createrLoan Exception " + e, LogType.ERROR);
			resAllContractList.setStatus(statusFale);
			resAllContractList.setMessage("Yeu cau that bai - Da co loi xay ra");
			resAllContractList.setContract_list(resContractList);
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resAllContractList.toJSON())
					.build();
		}
	}

	public Response getLogStepsList(String dataLogStepsList) {
		FileLogger.log("----------------Bat dau createrLoan--------------------------", LogType.BUSSINESS);
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		ResStepLog resStepLog = new ResStepLog();
		try {
			FileLogger.log("getLogStepsList dataLogStepsList: " + dataLogStepsList, LogType.BUSSINESS);
			ReqStepLog reqStepLog = gson.fromJson(dataLogStepsList, ReqStepLog.class);
			ResStepLog resStepLog2 = validData.validGetLogStepsList(reqStepLog);
			if (resStepLog2 != null) {
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resStepLog2.toJSON()).build();
			}
			List<Integer> branchID = new ArrayList<>();
			List<Integer> roomID = new ArrayList<>();
			Account acc = accountHome.getAccountUsename(reqStepLog.getUsername());
			if (ValidData.checkNull(acc.getBranchId()) == true) {
				JSONObject isJsonObject = (JSONObject) new JSONObject(acc.getBranchId());
				Iterator<String> keys = isJsonObject.keys();
				while (keys.hasNext()) {
					String key = keys.next();
					System.out.println(key);
					JSONArray msg = (JSONArray) isJsonObject.get(key);
					branchID.add(new Integer(key.toString()));
					for (int i = 0; i < msg.length(); i++) {
						roomID.add(Integer.parseInt(msg.get(i).toString()));
					}
				}
			}
//			boolean checkLoan = dbFintechHome.checkLoan(branchID, roomID, reqStepLog.getLoan_id());
			TblLoanRequest tblLoanRequest = dbFintechHome.getLoan(branchID, roomID, reqStepLog.getLoan_id());
			if (tblLoanRequest != null) {
				List<TblLoanExpertiseSteps> getLoanExpertiseSteps = dbFintechHome.getLoanExpertiseSteps(tblLoanRequest.getLoanId());
				if (getLoanExpertiseSteps != null) {
					resStepLog.setStatus(statusSuccess);
					resStepLog.setMessage("Yeu cau thanh cong");
					resStepLog.setLoan_id(reqStepLog.getLoan_id());
					resStepLog.setLoan_logs(getLoanExpertiseSteps);
				} else {
					resStepLog.setStatus(statusSuccess);
					resStepLog.setMessage("Yeu cau thanh cong - Khong co log cua hop dong nay");
					resStepLog.setLoan_id(reqStepLog.getLoan_id());
				}
			} else {
				resStepLog.setStatus(statusFale);
				resStepLog.setMessage("Yeu cau that bai - Khong co log cua hop dong nay - Hoac nguoi dung khong co quyen truy xuat");
			}
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			FileLogger.log(
					"getContractList: " + reqStepLog.getUsername() + " response to client:" + reqStepLog.toJSON(),
					LogType.BUSSINESS);
			FileLogger.log("----------------Ket thuc getContractList: ", LogType.BUSSINESS);
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resStepLog.toJSON()).build();
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("----------------Ket thuc createrLoan Exception " + e, LogType.ERROR);
			resStepLog.setStatus(statusFale);
			resStepLog.setMessage("Yeu cau that bai - Da co loi xay ra");
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resStepLog.toJSON()).build();
		}
	}

	public Response getContractDetail(String dataContractDetail) {
		FileLogger.log("----------------Bat dau createrLoan--------------------------", LogType.BUSSINESS);
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		ResContractDetail resContractDetail = new ResContractDetail();
		try {
			FileLogger.log("getContractDetail datacreaterContractList: " + dataContractDetail, LogType.BUSSINESS);
			ReqStepLog reqStepLog = gson.fromJson(dataContractDetail, ReqStepLog.class);
			ResContractDetail resContractDetail2 = validData.validgetContractDetail(reqStepLog);
			if (resContractDetail2 != null) {
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resContractDetail2.toJSON()).build();
			}
			List<Integer> branchID = new ArrayList<>();
			List<Integer> roomID = new ArrayList<>();
			Account acc = accountHome.getAccountUsename(reqStepLog.getUsername());
			if (ValidData.checkNull(acc.getBranchId()) == true) {
				JSONObject isJsonObject = (JSONObject) new JSONObject(acc.getBranchId());
				Iterator<String> keys = isJsonObject.keys();
				while (keys.hasNext()) {
					String key = keys.next();
					System.out.println(key);
					JSONArray msg = (JSONArray) isJsonObject.get(key);
					branchID.add(new Integer(key.toString()));
					for (int i = 0; i < msg.length(); i++) {
						roomID.add(Integer.parseInt(msg.get(i).toString()));
					}
				}
			}
			TblLoanRequest tblLoanRequest = dbFintechHome.getLoan(branchID, roomID, reqStepLog.getLoan_id());
//			boolean checkLoan = dbFintechHome.checkLoan(branchID, roomID, Integer.parseInt(reqStepLog.getLoan_id()));
			if (tblLoanRequest != null) {			
				resContractDetail.setStatus(statusSuccess);
				resContractDetail.setMessage("Yeu cau thanh cong");
				resContractDetail.setCreated_by(tblLoanRequest.getCreatedBy());
				resContractDetail.setCreated_date(tblLoanRequest.getCreatedDate().toString());
				resContractDetail.setApproved_by(tblLoanRequest.getApprovedBy());
				resContractDetail.setApproved_date(tblLoanRequest.getApprovedDate().toString());
				resContractDetail.setFinal_status(tblLoanRequest.getFinalStatus().toString());
				resContractDetail.setLoan_code(tblLoanRequest.getLoanCode());
				resContractDetail.setLoan_name(tblLoanRequest.getLoanName());
				resContractDetail.setCalculate_profit_type(tblLoanRequest.getCalculateProfitType().toString());
				resContractDetail.setLoan_for_month(tblLoanRequest.getLoanForMonth().toString());	
				
//				Nhận tiền qua  	- disburse_to 			- tbl_loan_req_detail
//				Loại sản phẩm 	- borrower_type  		- tbl_loan_req_detail
//				Thương hiệu 	- product_brand 		- tbl_loan_req_detail
//				Cách tính lãi	- calculate_profit_type - tbl_loan_request
//				Số tháng vay 	- loan_for_month 		- tbl_loan_request
//				Serial HĐ 		- product_serial_no 	- tbl_loan_req_detail
//				Ngày vay 		- created_date 			- tbl_loan_request

				TblLoanReqDetail tblLoanReqDetail = dbFintechHome.getLoanDetail(tblLoanRequest.getLoanId());
				try {
					ResContractList getBranchRoom = dbFintechHome.getBranchRoom(tblLoanRequest.getBranchId(), tblLoanRequest.getRoomId(), tblLoanRequest.getLoanId());
					resContractDetail.setBranch(getBranchRoom.getBranch_id());
					resContractDetail.setRoom(getBranchRoom.getRoom_code());
				} catch (Exception e) {
				}
				try {
					List<TblImages> tblImages = dbFintechHome.getTblImages(tblLoanRequest.getLoanId());
					resContractDetail.setImages(tblImages);
				} catch (Exception e) {
				}
				try {
					List<TblLoanRequestAskAns> tblLoanRequestAskAns = dbFintechHome.getRequestAskAns(tblLoanRequest.getLoanId());
					
					List<TblLoanRequestAskAnsGen> resListLoanRequestAskAns = new ArrayList<>();
					Type listTypeAskAns = new TypeToken<List<Object>>() {}.getType();
					for (TblLoanRequestAskAns tblAskAns : tblLoanRequestAskAns) {
						TblLoanRequestAskAnsGen tblLoanRequestAskAnsGen = new TblLoanRequestAskAnsGen();
						ArrayList<Object> lstObj = GsonUltilities.fromJson(tblAskAns.getQAThamDinh1(), listTypeAskAns);
						tblLoanRequestAskAnsGen.setQAId(tblAskAns.getQAId());
						tblLoanRequestAskAnsGen.setLoanId(tblAskAns.getLoanId());
						tblLoanRequestAskAnsGen.setQAThamDinh2(tblAskAns.getQAThamDinh2());
						tblLoanRequestAskAnsGen.setThamDinh1Rate(tblAskAns.getThamDinh1Rate());
						tblLoanRequestAskAnsGen.setThamDinh2Rate(tblAskAns.getThamDinh2Rate());
						tblLoanRequestAskAnsGen.setQAThamDinh1(lstObj);
						resListLoanRequestAskAns.add(tblLoanRequestAskAnsGen);
					}
					resContractDetail.setQuestion_and_answears(resListLoanRequestAskAns);
				} catch (Exception e) {
				}
				Type listType = new TypeToken<List<Object>>() {}.getType();
				ArrayList<Object> lstObj = GsonUltilities.fromJson(tblLoanReqDetail.getFees(), listType);
				resContractDetail.setFees(lstObj);
				tblLoanReqDetail.setFees("");
				resContractDetail.setLoan_req_detail(tblLoanReqDetail);
				try {
					List<TblLoanBill> listLoanBill = tblLoanBillHome.getTblLoanBill(tblLoanRequest.getLoanId());
					resContractDetail.setLoanBill(listLoanBill);
				} catch (Exception e) {
				}
				
			}else{
				resContractDetail.setStatus(statusFale);
				resContractDetail.setMessage("Yeu cau that bai - Khong co log cua hop dong nay - Hoac nguoi dung khong co quyen truy xuat");
			}
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			FileLogger.log("getContractList: " + reqStepLog.getUsername() + " response to client:" + resContractDetail.toJSON().replace("'\'", ""), LogType.BUSSINESS);
			FileLogger.log("----------------Ket thuc getContractList: ", LogType.BUSSINESS);
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resContractDetail.toJSON()).build();
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("----------------Ket thuc createrLoan Exception " + e, LogType.ERROR);
			resContractDetail.setStatus(statusFale);
			resContractDetail.setMessage("Yeu cau that bai - Da co loi xay ra");
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resContractDetail.toJSON()).build();
		}
	}

	// public Response getContractList(String datacreaterContractList) {
	// FileLogger.log("----------------Bat dau
	// createrLoan--------------------------", LogType.BUSSINESS);
	// ResponseBuilder response = Response.status(Status.OK).entity("x");
	// ResContractList resContractList = new ResContractList();
	// try {
	// ReqContractList reqContractList = gson.fromJson(datacreaterContractList,
	// ReqContractList.class);
	// ResContractList resCreaterLoanValid =
	// validData.validGetContractList(reqContractList);
	// if (resCreaterLoanValid != null) {
	// response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
	// return response.header(Commons.ResponseTime,
	// Utils.getTimeNow()).entity(resCreaterLoanValid.toJSON()).build();
	// }
	// response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
	// FileLogger.log("getContractList: " + reqContractList.getUsername() + "
	// response to client:" + resContractList.toJSON(), LogType.BUSSINESS);
	// FileLogger.log("----------------Ket thuc getContractList: ",
	// LogType.BUSSINESS);
	// return response.header(Commons.ResponseTime,
	// Utils.getTimeNow()).entity(resContractList.toJSON()).build();
	// } catch (Exception e) {
	// e.printStackTrace();
	// FileLogger.log("----------------Ket thuc createrLoan Exception " + e,
	// LogType.ERROR);
	// resContractList.setStatus(statusFale);
	// resContractList.setMessage("Yeu cau that bai - Da co loi xay ra");
	// response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
	// return response.header(Commons.ResponseTime,
	// Utils.getTimeNow()).entity(resContractList.toJSON()).build();
	// }
	// }

	public Response getBank(String dataGetbank) {
		FileLogger.log("----------------Bat dau getBank--------------------------", LogType.BUSSINESS);
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		BankRes bankRes = new BankRes();
		try {
			FileLogger.log("getBank dataGetbank: " + dataGetbank, LogType.BUSSINESS);
			BankReq reqBankReq = gson.fromJson(dataGetbank, BankReq.class);

			List<TblBanks> getTblBanks = tblBanksHome.getTblBanks(1, reqBankReq.getBank_support_function());
			if (getTblBanks != null) {
				FileLogger.log("getBank: " + reqBankReq.getUsername() + " thanh cong:", LogType.BUSSINESS);
				bankRes.setStatus(statusSuccess);
				bankRes.setMessage("Yeu cau thanh cong");
				bankRes.setBanks(getTblBanks);
			} else {
				FileLogger.log("getBank: " + reqBankReq.getUsername() + " that bai getTblBanks null",
						LogType.BUSSINESS);
				bankRes.setStatus(statusFale);
				bankRes.setMessage("Yeu cau that bai - Da co loi xay ra");
			}
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			FileLogger.log("getBank: " + reqBankReq.getUsername() + " response to client:" + bankRes.toJSON(),
					LogType.BUSSINESS);
			FileLogger.log("----------------Ket thuc getBank: ", LogType.BUSSINESS);
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(bankRes.toJSON()).build();
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("----------------Ket thuc getBank Exception " + e.getMessage(), LogType.ERROR);
			bankRes.setStatus(statusFale);
			bankRes.setMessage("Yeu cau that bai - Da co loi xay ra");
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(bankRes.toJSON()).build();
		}
	}

	public Response getIllustration(String dataIllustration) {
		FileLogger.log("----------------Bat dau getIllustration--------------------------", LogType.BUSSINESS);
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		ObjBillRes objBillRes = new ObjBillRes();
		String billID = Utils.getTimeNowDate() + "_" + Utils.getBillid();
		try {
			FileLogger.log(" dataIllustration: " + dataIllustration, LogType.BUSSINESS);
			ObjReqFee objReqFee = gson.fromJson(dataIllustration, ObjReqFee.class);
			if (ValidData.checkNull(objReqFee.getUsername()) == false
					|| ValidData.checkNull(objReqFee.getToken()) == false) {
				FileLogger.log("getIllustration: " + objReqFee.getUsername() + " invalid : ", LogType.BUSSINESS);
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				objBillRes.setStatus(statusFale);
				objBillRes.setMessage("Yeu cau that bai - Invalid message request");
				objBillRes.setBilling_tmp_code("");
				objBillRes.setCollection("");
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(objBillRes.toJSON()).build();
			}
			boolean checkLG = userInfo.checkLogin(objReqFee.getUsername(), objReqFee.getToken());
			if (checkLG) {
				FileLogger.log("getIllustration: " + objReqFee.getUsername() + " checkLG:" + checkLG,
						LogType.BUSSINESS);
				// String billID = getTimeNowDate() + "_" + getBillid();
				double sotienvay = (double) objReqFee.getLoan_amount();
				double sothangvay = (double) objReqFee.getLoan_for_month();
				double loaitrano = (double) objReqFee.getCalculate_profit_type();
				List<Fees> listFee = objReqFee.getFees();

				// '1:Lai suat, 2:Phi tu van, 3:phi dich vu,4:phitra no trươc
				// han,5:phi tat toan truoc han',
				System.out.println("aaaa");
				Bussiness bussiness = new Bussiness();
				String loanID = "";
				ArrayList<Document> illustrationIns = caculator.illustrationNew(objReqFee.getUsername(), billID,
						sotienvay, sothangvay, objReqFee.getLoan_expect_date(), loaitrano, listFee, loanID);
				FileLogger.log("getIllustration: " + objReqFee.getUsername() + " illustrationIns:" + illustrationIns,
						LogType.BUSSINESS);
				boolean checkInsMongo = mongoDB.insertDocument(illustrationIns, "tbl_minhhoa");
				FileLogger.log("getIllustration: " + objReqFee.getUsername() + " checkInsMongo: " + checkInsMongo,
						LogType.BUSSINESS);
				objBillRes.setStatus(statusSuccess);
				objBillRes.setMessage("Yeu cau thanh cong");
				objBillRes.setBilling_tmp_code(billID);
				objBillRes.setCollection("tbl_minhhoa");
			} else {
				FileLogger.log("getIllustration: " + objReqFee.getUsername() + " check login false:",
						LogType.BUSSINESS);
				objBillRes.setStatus(statusFale);
				objBillRes.setMessage("Yeu cau that bai - Invalid message request");
				objBillRes.setBilling_tmp_code("");
				objBillRes.setCollection("");
			}
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			FileLogger.log("getIllustration: " + objReqFee.getUsername() + " response to client:" + objBillRes.toJSON(),
					LogType.BUSSINESS);
			FileLogger.log("----------------Ket thuc getIllustration: ", LogType.BUSSINESS);
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(objBillRes.toJSON()).build();
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("----------------Ket thuc getIllustration Exception " + e.getMessage(), LogType.ERROR);
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
			List<Integer> branchID = new ArrayList<>();
			List<Integer> roomID = new ArrayList<>();
			if (ValidData.checkNull(acc.getBranchId()) == true) {
				JSONObject isJsonObject = (JSONObject) new JSONObject(acc.getBranchId());
				Iterator<String> keys = isJsonObject.keys();
				while (keys.hasNext()) {
					String key = keys.next();
					System.out.println(key);
					JSONArray msg = (JSONArray) isJsonObject.get(key);
					branchID.add(new Integer(key.toString()));
					for (int i = 0; i < msg.length(); i++) {
						roomID.add(Integer.parseInt(msg.get(i).toString()));
					}
				}
			} else {
				System.out.println("null");
			}
			System.out.println("aa: " + branchID);
			System.out.println("aa: " + roomID);
			String aaa = "";
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
