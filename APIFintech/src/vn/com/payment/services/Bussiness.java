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
import vn.com.payment.entities.TblLoanSponsorMapp;
import vn.com.payment.entities.TblProduct;
import vn.com.payment.entities.TblRateConfig;
import vn.com.payment.home.AccountHome;
import vn.com.payment.home.BaseMongoDB;
import vn.com.payment.home.DBFintechHome;
import vn.com.payment.home.TblBanksHome;
import vn.com.payment.home.TblLoanBillHome;
import vn.com.payment.home.TblLoanReqDetailHome;
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
import vn.com.payment.object.ReqAllotment;
import vn.com.payment.object.ReqAppraisal;
import vn.com.payment.object.ReqChangePass;
import vn.com.payment.object.ReqContractList;
import vn.com.payment.object.ReqContractListSponsor;
import vn.com.payment.object.ReqCreaterLoan;
import vn.com.payment.object.ReqDisbursement;
import vn.com.payment.object.ReqLogin;
import vn.com.payment.object.ReqStepLog;
import vn.com.payment.object.ReqUpdateStatus;
import vn.com.payment.object.ResAllContractList;
import vn.com.payment.object.ResAllotment;
import vn.com.payment.object.ResAppraisal;
import vn.com.payment.object.ResChangePass;
import vn.com.payment.object.ResContractDetail;
import vn.com.payment.object.ResContractList;
import vn.com.payment.object.ResContractListSponsor;
import vn.com.payment.object.ResCreaterLoan;
import vn.com.payment.object.ResDisbursement;
import vn.com.payment.object.ResLogin;
import vn.com.payment.object.ResStepLog;
import vn.com.payment.object.ResUpdateStatus;
import vn.com.payment.object.TokenRedis;
import vn.com.payment.redis.RedisBusiness;
//import vn.com.payment.thread.ThreadInsertLogStep;
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
	int statusPending = 107;
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
			ResCreaterLoan resCreaterLoanValid = validData.validCreaterLoan(reqCreaterLoan);
			if (resCreaterLoanValid != null) {
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resCreaterLoanValid.toJSON()).build();
			}

			Account acc = accountHome.getAccountUsename(reqCreaterLoan.getUsername());
			String fullName = reqCreaterLoan.getUsername();
			try {
				fullName = acc.getFirstName() + " " + acc.getLastName();
			} catch (Exception e) {
			}
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

			// 1 Nhận tiền qua - disburse_to - tbl_loan_req_detail
			// 2 Loại sản phẩm - borrower_type - tbl_loan_req_detail
			// 3 Thương hiệu - product_brand - tbl_loan_req_detail
			// 4 Cách tính lãi - calculate_profit_type - tbl_loan_request
			// 5 Số tháng vay - loan_for_month - tbl_loan_request
			// 6 Serial HĐ - product_serial_no - tbl_loan_req_detail
			// 7 Ngày vay - created_date - tbl_loan_request
			// 8 Ngày trả - disbursement_date - tbl_loan_req_detail
			
			TblLoanRequest tblLoanRequest = new TblLoanRequest();
			TblLoanReqDetail tblLoanReqDetail = new TblLoanReqDetail();
			tblLoanRequest = dbFintechHome.getTblLoanRequest(reqCreaterLoan.getLoan_code());
			int insOrUpd = 0; // 0 insert, 1 update
			if(tblLoanRequest.getLoanId() != null){
				insOrUpd = 1;
				tblLoanReqDetail = dbFintechHome.getLoanDetail(tblLoanRequest.getLoanId());
				boolean checkDelete = dbFintechHome.deleteTblImages(tblLoanReqDetail.getLoanId());
				FileLogger.log("createrLoan checkDelete IMG: " + checkDelete, LogType.BUSSINESS);				
				boolean checkDeleteAskAns = dbFintechHome.deleteAskAns(tblLoanReqDetail.getLoanId());
				FileLogger.log("createrLoan deleteAskAns checkDeleteAskAns: " + checkDeleteAskAns, LogType.BUSSINESS);

			}else{				
				tblLoanRequest = new TblLoanRequest();
				tblLoanReqDetail = new TblLoanReqDetail();
				tblLoanRequest.setLoanId(loanID.intValue());
				tblLoanReqDetail.setLoanId(loanID.intValue());
				tblLoanRequest.setFinalStatus(statusPending);
				tblLoanRequest.setPreviousStatus(statusPending);
			}
			tblLoanRequest.setCreatedDate(new Date());
			tblLoanRequest.setEditedDate(new Date());
			tblLoanRequest.setExpireDate(new Date());
			tblLoanRequest.setApprovedDate(new Date());
			tblLoanRequest.setCreatedBy(reqCreaterLoan.getUsername());
			tblLoanRequest.setApprovedBy(reqCreaterLoan.getUsername());
			tblLoanRequest.setLatestUpdate(new Date());
			tblLoanRequest.setLoanCode(reqCreaterLoan.getLoan_code());
			tblLoanRequest.setLoanName(reqCreaterLoan.getLoan_name());
			tblLoanRequest.setContractSerialNum(reqCreaterLoan.getContract_serial_num());
			tblLoanRequest.setBranchId(branch_id);
			tblLoanRequest.setRoomId(room_id);
			tblLoanRequest.setCalculateProfitType((int) reqCreaterLoan.getCalculate_profit_type());
			tblLoanRequest.setLoanForMonth((int) reqCreaterLoan.getLoan_amount());
			tblLoanRequest.setLoanForMonth((int) reqCreaterLoan.getLoan_for_month());

			tblLoanReqDetail.setDisburseTo((int) reqCreaterLoan.getDisburse_to());
			try {
				tblLoanReqDetail.setProductModal(reqCreaterLoan.getProduct_modal());
			} catch (Exception e) {
			}
			try {
				tblLoanReqDetail.setProductBrand(Integer.parseInt(reqCreaterLoan.getProduct_brand()));
			} catch (Exception e) {
			}
			try {
				tblLoanReqDetail.setTotalRun((int) reqCreaterLoan.getTotal_run());
			} catch (Exception e) {
			}
			try {
				tblLoanReqDetail.setProductCondition((int) reqCreaterLoan.getProduct_condition());		
			} catch (Exception e) {
			}
			try {
				tblLoanReqDetail.setProductOwnByBorrower((int) reqCreaterLoan.getProduct_own_by_borrower());
			} catch (Exception e) {
			}
			try {
				tblLoanReqDetail.setProductSerialNo(reqCreaterLoan.getProduct_serial_no());
			} catch (Exception e) {
			}			
			try {
				tblLoanReqDetail.setProductColor(reqCreaterLoan.getProduct_color());
			} catch (Exception e) {
			}
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
			

			tblLoanReqDetail.setCreatedDate(new Date());
			tblLoanReqDetail.setEditedDate(new Date());
			tblLoanReqDetail.setExpectAmount(reqCreaterLoan.getLoan_amount());
			tblLoanReqDetail.setApprovedAmount(reqCreaterLoan.getLoan_amount());
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
			tblLoanReqDetail.setChangeFee(Integer.parseInt(reqCreaterLoan.getChange_fee()));

			List<TblImages> imagesListSet = new ArrayList<>();
			if (reqCreaterLoan.getImages() != null) {
				List<ObjImage> imagesList = reqCreaterLoan.getImages();
				for (ObjImage objImage : imagesList) {
					TblImages tblImages = new TblImages();
					tblImages.setLoanRequestDetailId(tblLoanRequest.getLoanId());
					tblImages.setImageName(objImage.getImage_name());
					tblImages.setImageInputName(objImage.getImage_input_name());
					tblImages.setPartnerImageId(objImage.getPartner_image_id());
					tblImages.setImageType((int) objImage.getImage_type());
					tblImages.setImageByte(objImage.getImage_byte());
					tblImages.setImageUrl(objImage.getImage_url());
					tblImages.setImageIsFront((int) objImage.getImage_is_front());
					tblImages.setUploadBy(fullName);
					imagesListSet.add(tblImages);
				}
			}
			List<Fees> feesListSet = reqCreaterLoan.getFees();
			tblLoanReqDetail.setFees(gson.toJson(feesListSet));
			String billID = Utils.getTimeNowDate() + "_" + Utils.getBillid();
			double sotienvay = (double) reqCreaterLoan.getLoan_amount();
			double sothangvay = (double) reqCreaterLoan.getLoan_for_month();
			double loaitrano = (double) reqCreaterLoan.getCalculate_profit_type();
			List<TblLoanBill> illustrationNewLoanBill = caculator.illustrationNewLoanBill(reqCreaterLoan.getUsername(),
					billID, sotienvay, sothangvay, reqCreaterLoan.getLoan_expect_date(), loaitrano, feesListSet,
					loanID.intValue());

			int percentAns = 0;
			TblLoanRequestAskAns tblLoanRequestAskAns = new TblLoanRequestAskAns();
			if ((reqCreaterLoan.getQuestion_and_answears()) != null) {
				List<ObjQuestions> questionsList = reqCreaterLoan.getQuestion_and_answears();
				tblLoanRequestAskAns.setLoanId(tblLoanRequest.getLoanId());
				tblLoanRequestAskAns.setQAThamDinh1(gson.toJson(questionsList));
			}
			FileLogger.log("createrLoan: " + reqCreaterLoan.getUsername() + " percentAns: " + percentAns,
					LogType.BUSSINESS);			
			System.out.println("createrLoan: " + reqCreaterLoan.getUsername() + " tblLoanReqDetail: "
					+ gson.toJson(tblLoanReqDetail));
			FileLogger.log("createrLoan: " + reqCreaterLoan.getUsername() + " tblLoanReqDetail: "
					+ gson.toJson(tblLoanReqDetail), LogType.BUSSINESS);
			FileLogger.log(
					"createrLoan: " + reqCreaterLoan.getUsername() + " tblLoanRequest: " + gson.toJson(tblLoanRequest),
					LogType.BUSSINESS);
			FileLogger.log("createrLoan: " + reqCreaterLoan.getUsername() + " tblLoanRequestAskAns: "
					+ gson.toJson(tblLoanRequestAskAns), LogType.BUSSINESS);

			boolean checkINS = tblLoanReqDetailHome.createLoanTrans(insOrUpd, tblLoanRequest, tblLoanReqDetail, imagesListSet, illustrationNewLoanBill, tblLoanRequestAskAns);
			if (checkINS) {
				FileLogger.log("createrLoan: " + reqCreaterLoan.getUsername() + " thanh cong:", LogType.BUSSINESS);
				FileLogger.log("createrLoan: " + reqCreaterLoan.getUsername() + " percentAns:", LogType.BUSSINESS);
				// if(percentAns <= 50){
				// resCreaterLoan.setStatus(tblLoanRequest.getFinalStatus());
				// resCreaterLoan.setMessage("Khoan vay bi tu choi do thieu
				// thong tin");
				// resCreaterLoan.setRequest_code(loanID.longValue());
				// }else{
				FileLogger.log("createrLoan: " + reqCreaterLoan.getUsername() + " tblLoanRequest.getLoanId():" + tblLoanRequest.getLoanId(), LogType.BUSSINESS);
				resCreaterLoan.setStatus(statusSuccess);
				resCreaterLoan.setMessage("Yeu cau dang duoc xu ly");
				resCreaterLoan.setRequest_code(loanID.longValue());
				// }
				TblLoanExpertiseSteps tblLoanExpertiseSteps = new TblLoanExpertiseSteps();
				tblLoanExpertiseSteps.setLoanId(tblLoanRequest.getLoanId());
				tblLoanExpertiseSteps.setExpertiseUser(fullName);
				tblLoanExpertiseSteps.setExpertiseDate(Utils.getTimeStampNow());
				tblLoanExpertiseSteps.setExpertiseStatus(tblLoanRequest.getFinalStatus());
				tblLoanExpertiseSteps.setExpertiseStep(1);
				tblLoanExpertiseSteps.setExpertiseComment("");
				tblLoanExpertiseSteps.setLoanCode(tblLoanRequest.getLoanCode());
				tblLoanExpertiseSteps.setAction(reqCreaterLoan.getAction());

				FileLogger.log("createrLoan ThreadInsertLogStep", LogType.BUSSINESS);
				boolean checkINSExpertiseSteps = dbFintechHome.createExpertiseSteps(tblLoanExpertiseSteps);
				FileLogger.log("createrLoan ThreadInsertLogStep checkINS: " + checkINSExpertiseSteps, LogType.BUSSINESS);
//				Thread t = new Thread(new ThreadInsertLogStep(tblLoanExpertiseSteps));
//				t.start();

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
		FileLogger.log("----------------Bat dau getContractList--------------------------", LogType.BUSSINESS);
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		ResAllContractList resAllContractList = new ResAllContractList();
		List<ResContractList> resContractList = new ArrayList<>();
		try {
			FileLogger.log("getContractList dataGetContractList: " + dataGetContractList, LogType.BUSSINESS);
			ReqContractList reqContractList = gson.fromJson(dataGetContractList, ReqContractList.class);
			ResAllContractList resCreaterLoanValid = validData.validGetContractList(reqContractList);
			if (resCreaterLoanValid != null) {
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resCreaterLoanValid.toJSON())
						.build();
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
			List<Integer> final_statusAR = new ArrayList<>(); 
			try {
				List<String> final_status = reqContractList.getFinal_status();
				for (String string : final_status) {
					final_statusAR.add(Integer.parseInt(string));
				}
//				for (int i = 0; i < final_status.length(); i++) {
//					
//				}
			} catch (Exception e) {
			}
			String id_number = reqContractList.getId_number();
			String borrower_name = reqContractList.getBorrower_name();
			String from_date = reqContractList.getFrom_date();
			String to_date = reqContractList.getTo_date();
			String calculate_profit_type = reqContractList.getCalculate_profit_type();
			List<ResContractList> lisResContract = dbFintechHome.listResContractList(branchID, roomID, loan_code,
					final_statusAR, id_number, borrower_name, from_date, to_date, calculate_profit_type,
					reqContractList.getLimit(), reqContractList.getOffSet());
			long total = dbFintechHome.listCountContractList(branchID, roomID, loan_code, final_statusAR, id_number,
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
			FileLogger.log("----------------Ket thuc getContractList Exception " + e, LogType.ERROR);
			resAllContractList.setStatus(statusFale);
			resAllContractList.setMessage("Yeu cau that bai - Da co loi xay ra");
			resAllContractList.setContract_list(resContractList);
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resAllContractList.toJSON())
					.build();
		}
	}

	public Response getLogStepsList(String dataLogStepsList) {
		FileLogger.log("----------------Bat dau getLogStepsList--------------------------", LogType.BUSSINESS);
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
			// boolean checkLoan = dbFintechHome.checkLoan(branchID, roomID,
			// reqStepLog.getLoan_id());
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
				resStepLog.setMessage(
						"Yeu cau that bai - Khong co log cua hop dong nay - Hoac nguoi dung khong co quyen truy xuat");
			}
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			FileLogger.log(
					"getLogStepsList: " + reqStepLog.getUsername() + " response to client:" + reqStepLog.toJSON(),
					LogType.BUSSINESS);
			FileLogger.log("----------------Ket thuc getContractList: ", LogType.BUSSINESS);
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resStepLog.toJSON()).build();
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("----------------Ket thuc getLogStepsList Exception " + e, LogType.ERROR);
			resStepLog.setStatus(statusFale);
			resStepLog.setMessage("Yeu cau that bai - Da co loi xay ra");
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resStepLog.toJSON()).build();
		}
	}

	public Response getContractDetail(String dataContractDetail) {
		FileLogger.log("----------------Bat dau getContractDetail--------------------------", LogType.BUSSINESS);
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		ResContractDetail resContractDetail = new ResContractDetail();
		try {
			FileLogger.log("getContractDetail datacreaterContractList: " + dataContractDetail, LogType.BUSSINESS);
			ReqStepLog reqStepLog = gson.fromJson(dataContractDetail, ReqStepLog.class);
			ResContractDetail resContractDetail2 = validData.validgetContractDetail(reqStepLog);
			if (resContractDetail2 != null) {
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resContractDetail2.toJSON())
						.build();
			}
			List<Integer> branchID = new ArrayList<>();
			List<Integer> roomID = new ArrayList<>();
			Account acc = accountHome.getAccountUsename(reqStepLog.getUsername());
			TblLoanRequest tblLoanRequest = null;
			System.out.println(acc.getBranchId());
			if(acc.getRolesId().equals("3")){
				tblLoanRequest = dbFintechHome.getLoanRoleNDT(reqStepLog.getLoan_id());
			}else{
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
				tblLoanRequest = dbFintechHome.getLoan(branchID, roomID, reqStepLog.getLoan_id());
			}
			
			// boolean checkLoan = dbFintechHome.checkLoan(branchID, roomID,
			// Integer.parseInt(reqStepLog.getLoan_id()));
			if (tblLoanRequest != null) {
				resContractDetail.setStatus(statusSuccess);
				resContractDetail.setMessage("Yeu cau thanh cong");
				resContractDetail.setCreated_by(tblLoanRequest.getCreatedBy());
				resContractDetail.setCreated_date(Utils.convertDateToString("yyyy/MM/dd HH:mm:ss", tblLoanRequest.getCreatedDate()));
				resContractDetail.setApproved_by(tblLoanRequest.getApprovedBy());
				resContractDetail.setApproved_date(Utils.convertDateToString("yyyy/MM/dd HH:mm:ss", tblLoanRequest.getApprovedDate()));
				resContractDetail.setFinal_status(tblLoanRequest.getFinalStatus().toString());
				resContractDetail.setLoan_code(tblLoanRequest.getLoanCode());
				resContractDetail.setLoan_name(tblLoanRequest.getLoanName());
				resContractDetail.setCalculate_profit_type(tblLoanRequest.getCalculateProfitType().toString());
				resContractDetail.setLoan_for_month(tblLoanRequest.getLoanForMonth().toString());

				// Nhận tiền qua - disburse_to - tbl_loan_req_detail
				// Loại sản phẩm - borrower_type - tbl_loan_req_detail
				// Thương hiệu - product_brand - tbl_loan_req_detail
				// Cách tính lãi - calculate_profit_type - tbl_loan_request
				// Số tháng vay - loan_for_month - tbl_loan_request
				// Serial HĐ - product_serial_no - tbl_loan_req_detail
				// Ngày vay - created_date - tbl_loan_request

				TblLoanReqDetail tblLoanReqDetail = dbFintechHome.getLoanDetail(tblLoanRequest.getLoanId());
				try {
					ResContractList getBranchRoom = dbFintechHome.getBranchRoom(tblLoanRequest.getBranchId(),
							tblLoanRequest.getRoomId(), tblLoanRequest.getLoanId());
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
					List<TblLoanRequestAskAns> tblLoanRequestAskAns = dbFintechHome
							.getRequestAskAns(tblLoanRequest.getLoanId());

					List<TblLoanRequestAskAnsGen> resListLoanRequestAskAns = new ArrayList<>();
					Type listTypeAskAns1 = new TypeToken<List<Object>>() {}.getType();
					Type listTypeAskAns2 = new TypeToken<List<Object>>() {}.getType();
					for (TblLoanRequestAskAns tblAskAns : tblLoanRequestAskAns) {
						TblLoanRequestAskAnsGen tblLoanRequestAskAnsGen = new TblLoanRequestAskAnsGen();
						ArrayList<Object> lstObj1 = GsonUltilities.fromJson(tblAskAns.getQAThamDinh1(), listTypeAskAns1);
						ArrayList<Object> lstObj2 = GsonUltilities.fromJson(tblAskAns.getQAThamDinh2(), listTypeAskAns2);
						tblLoanRequestAskAnsGen.setQAId(tblAskAns.getQAId());
						tblLoanRequestAskAnsGen.setLoanId(tblAskAns.getLoanId());
						tblLoanRequestAskAnsGen.setThamDinh1Rate(tblAskAns.getThamDinh1Rate());
						tblLoanRequestAskAnsGen.setThamDinh2Rate(tblAskAns.getThamDinh2Rate());
						tblLoanRequestAskAnsGen.setQAThamDinh1(lstObj1);
						tblLoanRequestAskAnsGen.setQAThamDinh2(lstObj2);
						resListLoanRequestAskAns.add(tblLoanRequestAskAnsGen);
					}
					resContractDetail.setQuestion_and_answears(resListLoanRequestAskAns);
				} catch (Exception e) {
				}
				Type listType = new TypeToken<List<Object>>() {
				}.getType();
				ArrayList<Object> lstObj = GsonUltilities.fromJson(tblLoanReqDetail.getFees(), listType);
				resContractDetail.setFees(lstObj);
				tblLoanReqDetail.setFees("");
				resContractDetail.setLoan_req_detail(tblLoanReqDetail);
				try {
					List<TblLoanBill> listLoanBill = tblLoanBillHome.getTblLoanBill(tblLoanRequest.getLoanId());
					resContractDetail.setLoanBill(listLoanBill);
				} catch (Exception e) {
				}

			} else {
				resContractDetail.setStatus(statusFale);
				resContractDetail.setMessage(
						"Yeu cau that bai - Khong co log cua hop dong nay - Hoac nguoi dung khong co quyen truy xuat");
			}
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			FileLogger.log("getContractList: " + reqStepLog.getUsername() + " response to client:"+ resContractDetail.toJSON().replace("'\'", ""), LogType.BUSSINESS);
			FileLogger.log("----------------Ket thuc getContractDetail: ", LogType.BUSSINESS);
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resContractDetail.toJSON()).build();
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("----------------Ket thuc getContractDetail Exception " + e, LogType.ERROR);
			resContractDetail.setStatus(statusFale);
			resContractDetail.setMessage("Yeu cau that bai - Da co loi xay ra");
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resContractDetail.toJSON()).build();
		}
	}

	// Update trạng thái giao dịch
	public Response updateStatus(String dataUpdateStatus) {
		FileLogger.log("----------------Bat dau updateStatus--------------------------", LogType.BUSSINESS);
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		ResUpdateStatus resUpdateStatus = new ResUpdateStatus();
		try {
			FileLogger.log("updateStatus dataUpdateStatus: " + dataUpdateStatus, LogType.BUSSINESS);
			ReqUpdateStatus reqUpdateStatus = gson.fromJson(dataUpdateStatus, ReqUpdateStatus.class);
			ResUpdateStatus resUpdateStatus2 = validData.validUpdateStatus(reqUpdateStatus);
			if (resUpdateStatus2 != null) {
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resUpdateStatus2.toJSON()).build();
			}
			List<Integer> branchID = new ArrayList<>();
			List<Integer> roomID = new ArrayList<>();
			Account acc = accountHome.getAccountUsename(reqUpdateStatus.getUsername());
			String fullName = reqUpdateStatus.getUsername();
			try {
				fullName = acc.getFirstName() + " " + acc.getLastName();
			} catch (Exception e) {
			}
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
			System.out.println(gson.toJson(reqUpdateStatus));
			
			TblLoanRequest tblLoanRequest = dbFintechHome.getLoan(branchID, roomID, reqUpdateStatus.getLoan_code());
			
			if (tblLoanRequest != null) {
				FileLogger.log("updateStatus tblLoanRequest: " + gson.toJson(tblLoanRequest), LogType.BUSSINESS);
				FileLogger.log("updateStatus tblLoanRequest.getLoanId: " + tblLoanRequest.getLoanId(), LogType.BUSSINESS);
				tblLoanRequest.setPreviousStatus(tblLoanRequest.getFinalStatus());
				tblLoanRequest.setFinalStatus(Integer.parseInt(reqUpdateStatus.getFinal_status()));
				tblLoanRequest.setLatestUpdate(new Date());

				boolean checkUPD = tbLoanRequestHome.updateLoanRequest(tblLoanRequest);
				
				if(checkUPD){
					resUpdateStatus.setStatus(statusSuccess);
					resUpdateStatus.setMessage("Yeu cau thanh cong");
					resUpdateStatus.setLoan_code(reqUpdateStatus.getLoan_code());
					TblLoanExpertiseSteps tblLoanExpertiseSteps = new TblLoanExpertiseSteps();
					tblLoanExpertiseSteps.setLoanId(tblLoanRequest.getLoanId());
					tblLoanExpertiseSteps.setExpertiseUser(fullName);
					tblLoanExpertiseSteps.setExpertiseDate(Utils.getTimeStampNow());
					tblLoanExpertiseSteps.setExpertiseStatus(tblLoanRequest.getFinalStatus());
					tblLoanExpertiseSteps.setExpertiseStep(2);
					tblLoanExpertiseSteps.setExpertiseComment(reqUpdateStatus.getMemo());
					tblLoanExpertiseSteps.setLoanCode(tblLoanRequest.getLoanCode());
					try {
						tblLoanExpertiseSteps.setAction(reqUpdateStatus.getAction());
					} catch (Exception e) {
					}
					FileLogger.log("updateStatus ThreadInsertLogStep", LogType.BUSSINESS);
					boolean checkINSExpertiseSteps = dbFintechHome.createExpertiseSteps(tblLoanExpertiseSteps);
					FileLogger.log("updateStatus ThreadInsertLogStep checkINS: " + checkINSExpertiseSteps, LogType.BUSSINESS);
//					Thread t = new Thread(new ThreadInsertLogStep(tblLoanExpertiseSteps));
//					t.start();
					
				}else{
					resUpdateStatus.setStatus(statusFale);
					resUpdateStatus.setMessage("Yeu cau that bai -  Da co loi xay ra");
					resUpdateStatus.setLoan_code(reqUpdateStatus.getLoan_code());
				}			
			}else {
				FileLogger.log("updateStatus tblLoanRequest null: ", LogType.BUSSINESS);
				resUpdateStatus.setStatus(statusFale);
				resUpdateStatus.setMessage("Yeu cau that bai - Khong co log cua hop dong nay - Hoac nguoi dung khong co quyen truy xuat");
			}
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			FileLogger.log("updateStatus: " + reqUpdateStatus.getUsername() + " response to client:"+ resUpdateStatus.toJSON().replace("'\'", ""), LogType.BUSSINESS);
			FileLogger.log("----------------Ket thuc updateStatus: ", LogType.BUSSINESS);
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resUpdateStatus.toJSON()).build();
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("----------------Ket thuc updateStatus Exception " + e, LogType.ERROR);
			resUpdateStatus.setStatus(statusFale);
			resUpdateStatus.setMessage("Yeu cau that bai - Da co loi xay ra");
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resUpdateStatus.toJSON()).build();
		}
	}
	
	   //Phân bổ nhà đầu tư
	public Response setAllotment(String dataAllotment) {
		FileLogger.log("----------------Bat dau setAllotment--------------------------", LogType.BUSSINESS);
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		ResAllotment resAllotment = new ResAllotment();
		try {
			FileLogger.log("setAllotment dataUpdateStatus: " + dataAllotment, LogType.BUSSINESS);
			ReqAllotment reqAllotment = gson.fromJson(dataAllotment, ReqAllotment.class);
			ResAllotment resAllotment2 = validData.validSetAllotment(reqAllotment);
			if (resAllotment2 != null) {
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resAllotment2.toJSON()).build();
			}
			List<Integer> branchID = new ArrayList<>();
			List<Integer> roomID = new ArrayList<>();
			Account acc = accountHome.getAccountUsename(reqAllotment.getUsername());
			String fullName = reqAllotment.getUsername();
			try {
				fullName = acc.getFirstName() + " " + acc.getLastName();
			} catch (Exception e) {
			}
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
			System.out.println(gson.toJson(reqAllotment));
			
			TblLoanRequest tblLoanRequest = dbFintechHome.getLoan(branchID, roomID, reqAllotment.getLoan_code());
//			System.out.println(tblLoanRequest);
//			System.out.println(tblLoanRequest.getLoanId());
			if (tblLoanRequest != null) {
				TblLoanReqDetail tblLoanReqDetail = dbFintechHome.getLoanDetail(tblLoanRequest.getLoanId());
				FileLogger.log("setAllotment tblLoanRequest: " + gson.toJson(tblLoanRequest), LogType.BUSSINESS);
				FileLogger.log("setAllotment tblLoanRequest.getLoanId: " + tblLoanRequest.getLoanId(), LogType.BUSSINESS);
				tblLoanRequest.setPreviousStatus(tblLoanRequest.getFinalStatus());
				tblLoanRequest.setFinalStatus(113);  // 113 đã phân bổ
				tblLoanRequest.setLatestUpdate(new Date());

				boolean checkUPD = tbLoanRequestHome.updateLoanRequest(tblLoanRequest);
				
				if(checkUPD){
					
					List<Integer> sponsorAR = new ArrayList<>(); 
					try {
						List<String> sponsor = reqAllotment.getSponsor();
						for (String string : sponsor) {

							TblLoanSponsorMapp tblLoanSponsorMapp = new TblLoanSponsorMapp();
							tblLoanSponsorMapp.setLoanId(tblLoanRequest.getLoanId());
							tblLoanSponsorMapp.setSponsorId(Integer.parseInt(string));
							tblLoanSponsorMapp.setCreatedDate(Utils.getTimeStampNow());
							tblLoanSponsorMapp.setDisbursementDate(Utils.stringToTimestamp(tblLoanReqDetail.getDisbursementDate().toString()));
							tblLoanSponsorMapp.setDisbursementStatus(0);
							boolean checkINS = dbFintechHome.createTblLoanSponsorMapp(tblLoanSponsorMapp);
							FileLogger.log("setAllotment tblLoanRequest.getLoanId: " + tblLoanRequest.getLoanId() + "createTblLoanSponsorMapp: " + checkINS, LogType.BUSSINESS);
						}
					} catch (Exception e) {
					}
					resAllotment.setStatus(statusSuccess);
					resAllotment.setMessage("Yeu cau thanh cong");
					resAllotment.setLoan_code(reqAllotment.getLoan_code());
					
					TblLoanExpertiseSteps tblLoanExpertiseSteps = new TblLoanExpertiseSteps();
					tblLoanExpertiseSteps.setLoanId(tblLoanRequest.getLoanId());
					tblLoanExpertiseSteps.setExpertiseUser(fullName);
					tblLoanExpertiseSteps.setExpertiseDate(Utils.getTimeStampNow());
					tblLoanExpertiseSteps.setExpertiseStatus(tblLoanRequest.getFinalStatus());
					tblLoanExpertiseSteps.setExpertiseStep(2);
					tblLoanExpertiseSteps.setExpertiseComment(reqAllotment.getExpertise_comment());
					tblLoanExpertiseSteps.setLoanCode(tblLoanRequest.getLoanCode());
					try {
						tblLoanExpertiseSteps.setAction(reqAllotment.getAction());
					} catch (Exception e) {
					}
					FileLogger.log("setAllotment ThreadInsertLogStep", LogType.BUSSINESS);
					boolean checkINSExpertiseSteps = dbFintechHome.createExpertiseSteps(tblLoanExpertiseSteps);
					FileLogger.log("setAllotment ThreadInsertLogStep checkINS: " + checkINSExpertiseSteps, LogType.BUSSINESS);
//					Thread t = new Thread(new ThreadInsertLogStep(tblLoanExpertiseSteps));
//					t.start();
					
				}else{
					resAllotment.setStatus(statusFale);
					resAllotment.setMessage("Yeu cau that bai -  Da co loi xay ra");
				}			
			}else {
				FileLogger.log("setAllotment tblLoanRequest null: ", LogType.BUSSINESS);
				resAllotment.setStatus(statusFale);
				resAllotment.setMessage("Yeu cau that bai - Khong co log cua hop dong nay - Hoac nguoi dung khong co quyen truy xuat");
			}
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			FileLogger.log("setAllotment: " + reqAllotment.getUsername() + " response to client:"+ resAllotment.toJSON().replace("'\'", ""), LogType.BUSSINESS);
			FileLogger.log("----------------Ket thuc setAllotment: ", LogType.BUSSINESS);
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resAllotment.toJSON()).build();
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("----------------Ket thuc setAllotment Exception " + e, LogType.ERROR);
			resAllotment.setStatus(statusFale);
			resAllotment.setMessage("Yeu cau that bai - Da co loi xay ra");
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resAllotment.toJSON()).build();
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

	
	//Thẩm định lần 2
	public Response updateAppraisal(String dataAppraisal) {
		FileLogger.log("----------------Bat dau updateAppraisal--------------------------", LogType.BUSSINESS);
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		ResAppraisal resAppraisal = new ResAppraisal();
		try {
			FileLogger.log("updateAppraisal dataAppraisal: " + dataAppraisal, LogType.BUSSINESS);
			ReqAppraisal reqAppraisal = gson.fromJson(dataAppraisal, ReqAppraisal.class);
			ResAppraisal resAppraisal2 = validData.validUpdateAppraisal(reqAppraisal);
			if (resAppraisal2 != null) {
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resAppraisal2.toJSON()).build();
			}
			List<Integer> branchID = new ArrayList<>();
			List<Integer> roomID = new ArrayList<>();
			Account acc = accountHome.getAccountUsename(reqAppraisal.getUsername());
			String fullName = reqAppraisal.getUsername();
			try {
				fullName = acc.getFirstName() + " " + acc.getLastName();
			} catch (Exception e) {
			}
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
			TblLoanRequest tblLoanRequest = dbFintechHome.getLoan(branchID, roomID, reqAppraisal.getLoan_code());
			if (tblLoanRequest != null) {
				TblLoanReqDetail tblLoanReqDetail = dbFintechHome.getLoanDetail(tblLoanRequest.getLoanId());
				try {
					boolean checkDelete = dbFintechHome.deleteTblImages(tblLoanReqDetail.getReqDetailId());
					FileLogger.log("updateAppraisal checkDelete IMG: " + checkDelete, LogType.BUSSINESS);				
//					boolean checkDeleteAskAns = dbFintechHome.deleteAskAns(tblLoanReqDetail.getLoanId());
//					FileLogger.log("updateAppraisal deleteAskAns checkDeleteAskAns: " + checkDeleteAskAns, LogType.BUSSINESS);
				} catch (Exception e) {
				}				
				List<TblImages> imagesListSet = new ArrayList<>();
				if (reqAppraisal.getImages() != null) {
					List<ObjImage> imagesList = reqAppraisal.getImages();
					for (ObjImage objImage : imagesList) {
						TblImages tblImages = new TblImages();
						tblImages.setLoanRequestDetailId(tblLoanReqDetail.getReqDetailId());
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
				TblLoanRequestAskAns tblLoanRequestAskAns = new TblLoanRequestAskAns();
				if ((reqAppraisal.getQuestion_and_answears()) != null) {
					List<ObjQuestions> questionsList = reqAppraisal.getQuestion_and_answears();
					tblLoanRequestAskAns.setLoanId(tblLoanReqDetail.getLoanId());
//					tblLoanRequestAskAns.setQAThamDinh1(gson.toJson(questionsList));
					tblLoanRequestAskAns.setQAThamDinh2(gson.toJson(questionsList));
				}
				int insOrUpd = 0; // 0 insert, 1 update
				tblLoanRequest.setPreviousStatus(tblLoanRequest.getFinalStatus());
				tblLoanRequest.setFinalStatus(110);
				boolean checkINS = tbLoanRequestHome.createLoanTransTD(1, tblLoanRequest, tblLoanReqDetail, imagesListSet, tblLoanRequestAskAns);
				if (checkINS) {
					resAppraisal.setStatus(statusSuccess);
					resAppraisal.setMessage("Yeu cau thanh cong");
					resAppraisal.setLoan_code(tblLoanRequest.getLoanCode());
					
					TblLoanExpertiseSteps tblLoanExpertiseSteps = new TblLoanExpertiseSteps();
					tblLoanExpertiseSteps.setLoanId(tblLoanRequest.getLoanId());
					tblLoanExpertiseSteps.setExpertiseUser(fullName);
					tblLoanExpertiseSteps.setExpertiseDate(Utils.getTimeStampNow());
					tblLoanExpertiseSteps.setExpertiseStatus(tblLoanRequest.getFinalStatus());
					tblLoanExpertiseSteps.setExpertiseStep(1);
					tblLoanExpertiseSteps.setExpertiseComment(reqAppraisal.getMemo());
					tblLoanExpertiseSteps.setLoanCode(tblLoanRequest.getLoanCode());
					tblLoanExpertiseSteps.setAction(reqAppraisal.getAction());

					FileLogger.log("Bat dau ThreadInsertLogStep", LogType.BUSSINESS);
					boolean checkINSExpertiseSteps = dbFintechHome.createExpertiseSteps(tblLoanExpertiseSteps);
					FileLogger.log("ThreadInsertLogStep checkINS: " + checkINSExpertiseSteps, LogType.BUSSINESS);
//					Thread t = new Thread(new ThreadInsertLogStep(tblLoanExpertiseSteps));
//					t.start();
				}else{
					resAppraisal.setStatus(statusFale);
					resAppraisal.setMessage("Yeu cau that bai");
				}
			} else {
				resAppraisal.setStatus(statusFale);
				resAppraisal.setMessage("Yeu cau that bai - Khong co log cua hop dong nay - Hoac nguoi dung khong co quyen truy xuat");
			}
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			FileLogger.log("updateAppraisal: " + reqAppraisal.getUsername() + " response to client:" + resAppraisal.toJSON(),LogType.BUSSINESS);
			FileLogger.log("----------------Ket thuc updateAppraisal: ", LogType.BUSSINESS);
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resAppraisal.toJSON()).build();
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("----------------Ket thuc updateAppraisal Exception " + e, LogType.ERROR);
			resAppraisal.setStatus(statusFale);
			resAppraisal.setMessage("Yeu cau that bai - Da co loi xay ra");
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resAppraisal.toJSON()).build();
		}
	}
	
	// Lấy danh sach khoan vay phan bo cho nha dau tu
	public Response getContractListSponsor(String dataGetContractListSpon) {
		FileLogger.log("----------------Bat dau getContractListSponsor--------------------------", LogType.BUSSINESS);
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		ResAllContractList resAllContractList = new ResAllContractList();
		List<ResContractListSponsor> resContractListSponsors = new ArrayList<>();
		try {
			FileLogger.log("getContractListSponsor dataGetContractListSpon: " + dataGetContractListSpon, LogType.BUSSINESS);
			ReqContractListSponsor reqContractListSponsor = gson.fromJson(dataGetContractListSpon, ReqContractListSponsor.class);
			ResAllContractList resContractListSponsor2 = validData.validListSponsor(reqContractListSponsor);
			if (resContractListSponsor2 != null) {
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resContractListSponsor2.toJSON()).build();
			}
			Account acc = accountHome.getAccountUsename(reqContractListSponsor.getUsername());
			String loan_code = "";
			try {
				loan_code = reqContractListSponsor.getLoan_code();
			} catch (Exception e) {
			}
			List<Integer> final_statusAR = new ArrayList<>(); 
			try {
				List<String> final_status = reqContractListSponsor.getFinal_status();
				for (String string : final_status) {
					final_statusAR.add(Integer.parseInt(string));
				}
			} catch (Exception e) {
			}
			String borrower_name = reqContractListSponsor.getBorrower_name();
			String from_date = reqContractListSponsor.getFrom_date();
			String to_date = reqContractListSponsor.getTo_date();
			String calculate_profit_type = reqContractListSponsor.getCalculate_profit_type();
			List<ResContractListSponsor> listListSponsor = dbFintechHome.listListSponsor(acc.getRowId(), loan_code,final_statusAR, borrower_name, from_date, to_date, calculate_profit_type, reqContractListSponsor.getLimit(), reqContractListSponsor.getOffSet());
			long total = dbFintechHome.listCounListSponsor(acc.getRowId(), loan_code, final_statusAR, borrower_name, from_date, to_date, calculate_profit_type, reqContractListSponsor.getLimit(), reqContractListSponsor.getOffSet());
			if (listListSponsor != null) {
				resAllContractList.setStatus(statusSuccess);
				resAllContractList.setMessage("Yeu cau thanh cong");
				resAllContractList.setContract_list_sponsor(listListSponsor);
				resAllContractList.setTotalRecord(total);
			} else {
				resAllContractList.setStatus(statusFale);
				resAllContractList.setMessage("Yeu cau that bai - Da co loi xay ra");
				resAllContractList.setContract_list_sponsor(resContractListSponsors);
			}
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			FileLogger.log("getContractListSponsor: " + reqContractListSponsor.getUsername() + " response to client:" + resAllContractList.toJSON(), LogType.BUSSINESS);
			FileLogger.log("----------------Ket thuc getContractListSponsor: ", LogType.BUSSINESS);
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resAllContractList.toJSON()).build();
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("----------------Ket thuc getContractListSponsor Exception " + e, LogType.ERROR);
			resAllContractList.setStatus(statusFale);
			resAllContractList.setMessage("Yeu cau that bai - Da co loi xay ra");
			response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
			return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resAllContractList.toJSON()).build();
		}
	}

	// Giai ngan
		public Response disbursement(String dataDisbursement) {
			FileLogger.log("----------------Bat dau disbursement--------------------------", LogType.BUSSINESS);
			ResponseBuilder response = Response.status(Status.OK).entity("x");
			List<ResContractListSponsor> resContractListSponsors = new ArrayList<>();
			ResDisbursement resDisbursement = new ResDisbursement();
			try {
				FileLogger.log("disbursement dataDisbursement: " + dataDisbursement, LogType.BUSSINESS);
				ReqDisbursement reqDisbursement = gson.fromJson(dataDisbursement, ReqDisbursement.class);
				ResDisbursement resDisbursement2 = validData.validDisbursement(reqDisbursement);
				if (resDisbursement2 != null) {
					response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
					return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resDisbursement2.toJSON()).build();
				}
				Account acc = accountHome.getAccountUsename(reqDisbursement.getUsername());
				int accountID = acc.getRowId();
				String loan_code = "";
				try {
					loan_code = reqDisbursement.getLoan_code();
				} catch (Exception e) {
				}

				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				FileLogger.log("disbursement: " + reqDisbursement.getUsername() + " response to client:" + resDisbursement.toJSON(), LogType.BUSSINESS);
				FileLogger.log("----------------Ket thuc disbursement: ", LogType.BUSSINESS);
				return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resDisbursement.toJSON()).build();
			} catch (Exception e) {
				e.printStackTrace();
				FileLogger.log("----------------Ket thuc disbursement Exception " + e, LogType.ERROR);
				resDisbursement.setStatus(statusFale);
				resDisbursement.setMessage("Yeu cau that bai - Da co loi xay ra");
				response = response.header(Commons.ReceiveTime, Utils.getTimeNow());
				return response.header(Commons.ResponseTime, Utils.getTimeNow()).entity(resDisbursement.toJSON()).build();
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
