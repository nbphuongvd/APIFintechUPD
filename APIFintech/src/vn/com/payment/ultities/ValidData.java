package vn.com.payment.ultities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.corba.se.impl.transport.ReadTCPTimeoutsImpl;
import com.sun.tools.internal.xjc.model.SymbolSpace;

import vn.com.payment.config.LogType;
import vn.com.payment.home.TblLoanRequestHome;
import vn.com.payment.object.ReqAllotment;
import vn.com.payment.object.ReqAppraisal;
import vn.com.payment.object.ReqContractList;
import vn.com.payment.object.ReqContractListSponsor;
import vn.com.payment.object.ReqCreaterLoan;
import vn.com.payment.object.ReqDebtReminder;
import vn.com.payment.object.ReqDisbursement;
import vn.com.payment.object.ReqPayment;
import vn.com.payment.object.ReqSettlement;
import vn.com.payment.object.ReqStepLog;
import vn.com.payment.object.ReqUDExtendStatus;
import vn.com.payment.object.ReqUpdateStatus;
import vn.com.payment.object.ResAllContractList;
import vn.com.payment.object.ResAllotment;
import vn.com.payment.object.ResAppraisal;
import vn.com.payment.object.ResContractDetail;
import vn.com.payment.object.ResContractList;
import vn.com.payment.object.ResContractListSponsor;
import vn.com.payment.object.ResCreaterLoan;
import vn.com.payment.object.ResDebtReminder;
import vn.com.payment.object.ResDisbursement;
import vn.com.payment.object.ResPayment;
import vn.com.payment.object.ResSettlement;
import vn.com.payment.object.ResStepLog;
import vn.com.payment.object.ResUDExtendStatus;
import vn.com.payment.object.ResUpdateStatus;
import vn.com.payment.services.UserInfo;

import java.text.ParseException;

public class ValidData {
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	UserInfo userInfo = new UserInfo();
	public static boolean isNumberInt(String num) {
		try {
			int i = Integer.parseInt(num);
		 return true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}
	
	public static boolean checkNull(String str) {
		if (str != null && str != "" && str.length() != 0) {
			return true;
		}
		return false;
	}
	
	public static boolean checkNullLong(long str) {
		if (!"".equals(str)) {
			return true;
		}
		return false;
	}
	
	public static boolean checkNullInt(int str) {
		if (!"".equals(str)) {
			return true;
		}
		return false;
	}
	
	public static boolean checkNullBranch(String str) {
		if (str != null && str != "" && str.length() != 0 && !str.equals("0")) {
			return true;
		}
		return false;
	}
	
	public static boolean isNotContainSpecialChar(String inputString) {
		boolean rs = false;
		String reg	=	"[^(\'\"@#%^&*\\-_,\\.=)+{<?/>};:]{1,}";
		try{
			Pattern part = Pattern.compile(reg);
			Matcher match = part.matcher(inputString);
			rs	=	match.matches();
		}catch (Exception e) {
			rs = false;
		}
		return rs;
	}
	
	public static boolean isContent(String content) {
		boolean rs = false;
		String reg	=	"[^(\'\"@#%^&*=)+{<?>}]{1,}";
		try{
			Pattern part = Pattern.compile(reg);
			Matcher match = part.matcher(content);
			rs	=	match.matches();
		}catch (Exception e) {
			rs = false;
		}
		return rs;
	}	
	
	public static boolean isTimeFormat(String inputString) {
		boolean rs = false;
		String reg	=	"[0-9]{2}:[0-9]{2}:[0-9]{2} [0-9]{2}/[0-9]{2}/[0-9]{4}";
		// System.out.println(reg);
		try{
			Pattern part = Pattern.compile(reg);
			Matcher match = part.matcher(inputString);
			rs	=	match.matches();
		}catch (Exception e) {
			rs = false;
		}
		return rs;
	}
	
	public static boolean isAddress(String inputString) {
		boolean rs = false;
		String reg	=	"[^(\'\"@#%^&*=)+{<?>}]{1,}";
		// String reg	=	"[^\'\"@#%^&*=+{<?/>}]{1,}";
		try{
			Pattern part = Pattern.compile(reg);
			Matcher match = part.matcher(inputString);
			rs	=	match.matches();
		}catch (Exception e) {
			rs = false;
		}
		return rs;
	}
	
	public static boolean isCashierName(String cashierName) {
		boolean rs = false;
		String reg	=	"[^(\'\"@#%^&*=)+{<?>}]{1,}";
		// String reg	=	"[^\'\"@#%^&*=+{<?/>}]{1,}";
		try{
			Pattern part = Pattern.compile(reg);
			Matcher match = part.matcher(cashierName);
			rs	=	match.matches();
			if(cashierName.length() < 2 || cashierName.length() > 100) rs = false;
		}catch (Exception e) {
			rs = false;
		}
		return rs;
	}
	
	public static boolean isPhone(String inputString) {
		boolean rs = false;
		String reg	=	"^[0-9]{10,11}$";
		try{
			Pattern part = Pattern.compile(reg);
			Matcher match = part.matcher(inputString);
			rs	=	match.matches();
		}catch (Exception e) {
			rs = false;
		}
		return rs;
	}
	
	public static boolean isNumberic(String inputString) {
		boolean rs = false;
		String reg	=	"^[0-9]{1,}$";
		try{
			Pattern part = Pattern.compile(reg);
			Matcher match = part.matcher(inputString);
			rs	=	match.matches();
			if(rs == false) return false;			
			long number = Long.parseLong(inputString);
			if(number <= 0) return false;
		}catch (Exception e) {
			rs = false;
		}
		return rs;
	}
	
	public static boolean isEmail(String inputString) {
		boolean rs = false;
		String reg	=	"^\\w([\\w]*)([-\\w]?)([\\w]+)([\\.\\w]?)[\\w]*@\\w([\\w]*)([-\\w]?)([\\w]+)([\\.\\w]?)[\\w]*\\.[a-z]{2,4}$";

		try{
			Pattern part = Pattern.compile(reg);
			Matcher match = part.matcher(inputString);
			rs	=	match.matches();
			if(rs) return true;
			
		}catch (Exception e) {
			rs = false;
		}
		return rs;
	}
	
	public static boolean isTarget(String target) {
		boolean rs = false;
		String reg	=	"[\\w]*";
		try{
			Pattern part = Pattern.compile(reg);
			Matcher match = part.matcher(target);
			rs	=	match.matches();
		}catch (Exception e) {
			rs = false;
		}
		return rs;
	}
	
	public static boolean isUserName(String userName) {
		boolean rs = false;
		String reg	=	"^[\\w]{6,}";
		try{
			Pattern part = Pattern.compile(reg);
			Matcher match = part.matcher(userName);
			rs	=	match.matches();
		}catch (Exception e) {
			rs = false;
		}
		return rs;
	}
	
	public static boolean isMethodName(String methodName) {
		boolean rs = false;
		String reg	=	"^[\\w]{4,}";
		try{
			Pattern part = Pattern.compile(reg);
			Matcher match = part.matcher(methodName);
			rs	=	match.matches();
		}catch (Exception e) {
			rs = false;
		}
		return rs;
	}
	
//	public static boolean isProvider(String providerCode) {
//		boolean rs = false;
//		String reg	=	"^[A-Z]{2,10}";
//		try{
//			Pattern part = Pattern.compile(reg);
//			Matcher match = part.matcher(providerCode);
//			rs	=	match.matches();
//		}catch (Exception e) {
//			rs = false;
//		}
//		return rs;
//	}
	
	
	
	public static boolean isRequestID(String requestID) {
		boolean rs = false;
		String reg	=	"[a-zA-Z0-9_]+";
		try{
			Pattern part = Pattern.compile(reg);
			Matcher match = part.matcher(requestID);
			rs	=	match.matches();
			if(requestID.length()<6)
				rs = false;
		}catch (Exception e) {
			rs = false;
		}
		return rs;
	}
	
	public static boolean isPartnerID(String partnerID) {
		boolean rs = false;
		String reg	=	"[a-zA-Z0-9_]+";
		try{
			Pattern part = Pattern.compile(reg);
			Matcher match = part.matcher(partnerID);
			rs	=	match.matches();
			if((partnerID.length()<2)||(partnerID.length()>200))
				rs = false;
		}catch (Exception e) {
			rs = false;
		}
		return rs;
	}
	
	public static boolean isTransID(String transID) {
		boolean rs = false;
		String reg	=	"[a-zA-Z0-9_]+";
		try{
			Pattern part = Pattern.compile(reg);
			Matcher match = part.matcher(transID);
			rs	=	match.matches();
			if((transID.length()<2)||(transID.length()>200))
				rs = false;
		}catch (Exception e) {
			rs = false;
		}
		return rs;
	}
	
	public static boolean isBankNo(String bankNo) {
		boolean rs = false;
		String reg	=	"[a-zA-Z0-9_]+";
		try{
			Pattern part = Pattern.compile(reg);
			Matcher match = part.matcher(bankNo);
			rs	=	match.matches();
			if((bankNo.length()<2)||(bankNo.length()>50))
				rs = false;
		}catch (Exception e) {
			rs = false;
		}
		return rs;
	}
	
	public static boolean isReceiveName(String receiveName) {
		boolean rs = false;
		String reg	=	"[a-zA-Z ]+";
		try{
			Pattern part = Pattern.compile(reg);
			Matcher match = part.matcher(receiveName);
			rs	=	match.matches();
			if((receiveName.length()<2)||(receiveName.length()>100))
				rs = false;
		}catch (Exception e) {
			rs = false;
		}
		return rs;
	}
	
	public static boolean isBankID(String bankID) {
		boolean rs = false;
		String reg	=	"[a-zA-Z0-9]+";
		try{
			Pattern part = Pattern.compile(reg);
			Matcher match = part.matcher(bankID);
			rs	=	match.matches();
			if((bankID.length()<2)||(bankID.length() > 50))
				rs = false;
		}catch (Exception e) {
			rs = false;
		}
		return rs;
	}
		
	public static boolean isSearchTarget(String requestID) {
		boolean rs = false;
		String reg	=	"[\\w]*";
		try{
			Pattern part = Pattern.compile(reg);
			Matcher match = part.matcher(requestID);
			rs	=	match.matches();
		}catch (Exception e) {
			rs = false;
		}
		return rs;
	}
	
	public static boolean isPartnerName(String partnerName) {
		boolean rs = false;
		String reg	=	"[a-zA-Z0-9]+";
		try{
			Pattern part = Pattern.compile(reg);
			Matcher match = part.matcher(partnerName);
			rs	=	match.matches();
			if((partnerName.length() < 2) || (partnerName.length() > 100))
				rs = false;
		}catch (Exception e) {
			rs = false;
		}
		return rs;
	}
	
	public static boolean isRequestId(String requestId) {
		boolean rs = false;
		String reg	=	"[a-zA-Z0-9-]+";
		try{
			Pattern part = Pattern.compile(reg);
			Matcher match = part.matcher(requestId);
			rs	=	match.matches();
			if((requestId.length() < 6) || (requestId.length() > 200))
				rs = false;
		}catch (Exception e) {
			rs = false;
		}
		return rs;
	}
	
	public static boolean isIdNo(String idNo) {
		boolean rs = false;
		String reg	=	"[0-9]+";
		try{
			Pattern part = Pattern.compile(reg);
			Matcher match = part.matcher(idNo);
			rs	=	match.matches();
			if((idNo.length() < 6) || (idNo.length() > 50))
				rs = false;
		}catch (Exception e) {
			rs = false;
		}
		return rs;
	}
	
	public static boolean isOrdinal(String ordinal) {
		boolean rs = false;
		String reg	=	"[0-9]+";
		try{
			Pattern part = Pattern.compile(reg);
			Matcher match = part.matcher(ordinal);
			rs	=	match.matches();
			if((ordinal.length() > 3))
				rs = false;
		}catch (Exception e) {
			rs = false;
		}
		return rs;
	}
	
	public static boolean isProvider(String provider) {
		boolean rs = false;
		String reg	=	"[a-zA-Z0-9]+";
		try{
			Pattern part = Pattern.compile(reg);
			Matcher match = part.matcher(provider);
			rs	=	match.matches();
			if((provider.length() < 2) || (provider.length() > 50))
				rs = false;
		}catch (Exception e) {
			rs = false;
		}
		return rs;
	}
	
	public static boolean isAppId(String appId) {
		boolean rs = false;
		String reg	=	"[a-zA-Z0-9_]+";
		try{
			Pattern part = Pattern.compile(reg);
			Matcher match = part.matcher(appId);
			rs	=	match.matches();
			if((appId.length() < 2) || (appId.length() > 100))
				rs = false;
		}catch (Exception e) {
			rs = false;
		}
		return rs;
	}
	
	public static boolean isContractReason(String contractStatus) {
		boolean rs = false;
		String reg	=	"[a-zA-Z0-9-]+";
		try{
			Pattern part = Pattern.compile(reg);
			Matcher match = part.matcher(contractStatus);
			rs	=	match.matches();
			if((contractStatus.length() < 1) || (contractStatus.length() > 200))
				rs = false;
		}catch (Exception e) {
			rs = false;
		}
		return rs;
	}
	
	public static boolean isStoreId(String storeId) {
		boolean rs = false;
		String reg	=	"[a-zA-Z0-9_]+";
		try{
			Pattern part = Pattern.compile(reg);
			Matcher match = part.matcher(storeId);
			rs	=	match.matches();
			
			System.out.println("Length: " + storeId.length());
			
			
			if((storeId.length() < 2) || (storeId.length() > 100))
				rs = false;
		}catch (Exception e) {
			rs = false;
		}
		return rs;
	}
	
	
//	public static void main(String[] args) {
//		try {
//			System.out.println(isStoreId("TESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESTTESAB"));
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//	}
	
	public static boolean isImgLink(String imgLink) {
		boolean rs = false;
//		String reg	=	"[a-zA-Z0-9_\\w]+";
		try{
//			Pattern part = Pattern.compile(reg);
//			Matcher match = part.matcher(imgLink);
//			rs	=	match.matches();
			if((imgLink.length() < 6) || (imgLink.length() > 1000)){
				rs = false;
			}else{
				rs = true;
			}
				
		}catch (Exception e) {
			rs = false;
		}
		return rs;
	}
	
	public static boolean isFileName(String fileName) {
		boolean rs = false;
//		String reg	=	"[a-zA-Z0-9_.]+";
		try{
//			Pattern part = Pattern.compile(reg);
//			Matcher match = part.matcher(fileName);
//			rs	=	match.matches();
			
			if((fileName.length() < 6) || (fileName.length() > 1000)){
				rs = false;
			}else{
				rs = true;
			}
		}catch (Exception e) {
			rs = false;
		}
		return rs;
	}
	
	public static boolean isDocCode(String docCode) {
		boolean rs = false;
		String reg	=	"[a-zA-Z0-9_]+";
		try{
			Pattern part = Pattern.compile(reg);
			Matcher match = part.matcher(docCode);
			rs	=	match.matches();
			if((docCode.length() < 2) || (docCode.length() > 100))
				rs = false;
		}catch (Exception e) {
			rs = false;
		}
		return rs;
	}

	public static boolean isValidDate(String inDate) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	    dateFormat.setLenient(false);
	    try {
	      dateFormat.parse(inDate.trim());
	    } catch (ParseException pe) {
	      return false;
	    }
	    return true;
	  }
	

	public static boolean isValidDateStr(String inDate) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	    dateFormat.setLenient(false);
	    try {
	      if((inDate.length() != 8)){
	    	  return false;
		  }
	      dateFormat.parse(inDate.trim());
	      System.out.println(dateFormat.getDateTimeInstance());
	    } catch (ParseException pe) {
	      return false;
	    }
	    return true;
	  }
	long statusFale = 111l;
	
	public ResCreaterLoan validCreaterLoan(ReqCreaterLoan reqCreaterLoan){
		ResCreaterLoan resCreaterLoan = new ResCreaterLoan();
		TblLoanRequestHome tblLoanRequestHome = new TblLoanRequestHome();
		try {
			if (ValidData.checkNull(reqCreaterLoan.getUsername()) == false){
				String messageErr = "Valid CreaterLoan Username invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resCreaterLoan.setStatus(statusFale);
				resCreaterLoan.setMessage(messageErr);
				return resCreaterLoan;
			}
			if (ValidData.checkNull(reqCreaterLoan.getToken()) == false){
				String messageErr = "Valid CreaterLoan token invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resCreaterLoan.setStatus(statusFale);
				resCreaterLoan.setMessage(messageErr);
				return resCreaterLoan;
			}
			if (ValidData.checkNull(reqCreaterLoan.getLoan_code()) == false){
				String messageErr = "Valid CreaterLoan loan_code invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resCreaterLoan.setStatus(statusFale);
				resCreaterLoan.setMessage(messageErr);
				return resCreaterLoan;
			}
//			boolean checkContract =  tblLoanRequestHome.checktblLoanRequest(reqCreaterLoan.getLoan_code());
//			if(!checkContract){
//				String messageErr = "Valid CreaterLoan loan_code already exists ";
//				FileLogger.log(messageErr, LogType.BUSSINESS);
//				resCreaterLoan.setStatus(statusFale);
//				resCreaterLoan.setMessage(messageErr);
//				return resCreaterLoan;
//			}	
			if (ValidData.checkNullLong(reqCreaterLoan.getProduct_type()) == false){
				String messageErr = "Valid CreaterLoan product_type invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resCreaterLoan.setStatus(statusFale);
				resCreaterLoan.setMessage(messageErr);
				return resCreaterLoan;
			}
//			if (ValidData.checkNull(reqCreaterLoan.getProduct_brand()) == false){
//				String messageErr = "Valid CreaterLoan product_brand invalid";
//				FileLogger.log(messageErr, LogType.BUSSINESS);
//				resCreaterLoan.setStatus(statusFale);
//				resCreaterLoan.setMessage(messageErr);
//				return resCreaterLoan;
//			}
//			if (ValidData.checkNullLong(reqCreaterLoan.getTotal_run()) == false){
//				String messageErr = "Valid CreaterLoan total_run invalid";
//				FileLogger.log(messageErr, LogType.BUSSINESS);
//				resCreaterLoan.setStatus(statusFale);
//				resCreaterLoan.setMessage(messageErr);
//				return resCreaterLoan;
//			}
//			if (ValidData.checkNull(reqCreaterLoan.getBorrower_phone()) == false){
//				String messageErr = "Valid CreaterLoan borrower_phone invalid";
//				FileLogger.log(messageErr, LogType.BUSSINESS);
//				resCreaterLoan.setStatus(statusFale);
//				resCreaterLoan.setMessage(messageErr);
//				return resCreaterLoan;
//			}
//			if (ValidData.checkNull(reqCreaterLoan.getBorrower_email()) == false){
//				String messageErr = "Valid CreaterLoan borrower_email invalid";
//				FileLogger.log(messageErr, LogType.BUSSINESS);
//				resCreaterLoan.setStatus(statusFale);
//				resCreaterLoan.setMessage(messageErr);
//				return resCreaterLoan;
//			}
			if (ValidData.checkNull(reqCreaterLoan.getBorrower_id_number()) == false){
				String messageErr = "Valid CreaterLoan borrower_id_number invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resCreaterLoan.setStatus(statusFale);
				resCreaterLoan.setMessage(messageErr);
				return resCreaterLoan;
			}
			if (ValidData.checkNullLong(reqCreaterLoan.getLoan_amount()) == false){
				String messageErr = "Valid CreaterLoan loan_amount invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resCreaterLoan.setStatus(statusFale);
				resCreaterLoan.setMessage(messageErr);
				return resCreaterLoan;
			}
			
			if (ValidData.isNumberic(String.valueOf(reqCreaterLoan.getLoan_amount())) == false){
				String messageErr = "Valid CreaterLoan loan_amount invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resCreaterLoan.setStatus(statusFale);
				resCreaterLoan.setMessage(messageErr);
				return resCreaterLoan;
			}
			
			if (ValidData.checkNullLong(reqCreaterLoan.getProduct_valuation()) == false){
				String messageErr = "Valid CreaterLoan product_valuation invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resCreaterLoan.setStatus(statusFale);
				resCreaterLoan.setMessage(messageErr);
				return resCreaterLoan;
			}
			if (ValidData.checkNull(reqCreaterLoan.getBorrower_fullname()) == false){
				String messageErr = "Valid CreaterLoan borrower_fullname invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resCreaterLoan.setStatus(statusFale);
				resCreaterLoan.setMessage(messageErr);
				return resCreaterLoan;
			}if (ValidData.checkNull(reqCreaterLoan.getBorrower_address()) == false){
				String messageErr = "Valid CreaterLoan borrower_address invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resCreaterLoan.setStatus(statusFale);
				resCreaterLoan.setMessage(messageErr);
				return resCreaterLoan;
			}
			if (ValidData.checkNull(reqCreaterLoan.getBorrower_id_number()) == false){
				String messageErr = "Valid CreaterLoan borrower_id_number invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resCreaterLoan.setStatus(statusFale);
				resCreaterLoan.setMessage(messageErr);
				return resCreaterLoan;
			}					
			boolean checkLG = userInfo.checkLogin(reqCreaterLoan.getUsername(), reqCreaterLoan.getToken());
			if(!checkLG){
				FileLogger.log("createrLoan: " + reqCreaterLoan.getUsername()+ " check login false:", LogType.BUSSINESS);
				String messageErr = "Yeu cau that bai - Thong tin login sai";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resCreaterLoan.setStatus(statusFale);
				resCreaterLoan.setMessage(messageErr);
				return resCreaterLoan;
			}
		} catch (Exception e) {
			e.printStackTrace();
			String messageErr = "Valid CreaterLoan exception: "+ e;
			FileLogger.log(messageErr, LogType.ERROR);
		}
		return null;
	}

	public ResAllContractList validGetContractList(ReqContractList reqContractList){
		ResAllContractList resAllContractList = new ResAllContractList();
		List<ResContractList> resContractList = new ArrayList<>();
		try {
			if (ValidData.checkNull(reqContractList.getUsername()) == false){
				String messageErr = "Valid etContractList Username invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resAllContractList.setStatus(statusFale);
				resAllContractList.setMessage(messageErr);
				resAllContractList.setContract_list(resContractList);
				return resAllContractList;
			}
			if (ValidData.checkNull(reqContractList.getToken()) == false){
				String messageErr = "Valid etContractList token invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resAllContractList.setStatus(statusFale);
				resAllContractList.setMessage(messageErr);
				resAllContractList.setContract_list(resContractList);
				return resAllContractList;
			}
			if (ValidData.checkNull(reqContractList.getFrom_date()) == true){
				if(ValidData.isValidDateStr(reqContractList.getFrom_date()) == false){
					String messageErr = "Valid From_date invalid yyyyMMdd";
					FileLogger.log(messageErr, LogType.BUSSINESS);
					resAllContractList.setStatus(statusFale);
					resAllContractList.setMessage(messageErr);
					resAllContractList.setContract_list(resContractList);
					return resAllContractList;
				}
			}
			if (ValidData.checkNull(reqContractList.getTo_date()) == true){
				if(ValidData.isValidDateStr(reqContractList.getFrom_date()) == false){
					String messageErr = "Valid To_date invalid yyyyMMdd";
					FileLogger.log(messageErr, LogType.BUSSINESS);
					resAllContractList.setStatus(statusFale);
					resAllContractList.setMessage(messageErr);
					resAllContractList.setContract_list(resContractList);
					return resAllContractList;
				}
			}

			boolean checkLG = userInfo.checkLogin(reqContractList.getUsername(), reqContractList.getToken());
			if(!checkLG){
				FileLogger.log("etContractList: " + reqContractList.getUsername()+ " check login false:", LogType.BUSSINESS);
				String messageErr = "Yeu cau that bai - Thong tin login sai";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resAllContractList.setStatus(statusFale);
				resAllContractList.setMessage(messageErr);
				resAllContractList.setContract_list(resContractList);
				return resAllContractList;
			}
		} catch (Exception e) {
			e.printStackTrace();
			String messageErr = "Valid resContractList exception: "+ e;
			FileLogger.log(messageErr, LogType.ERROR);
		}
		return null;
	}
	
	public ResStepLog validGetLogStepsList(ReqStepLog reqStepLog){
		ResStepLog resStepLog = new ResStepLog();
		List<ResContractList> resContractList = new ArrayList<>();
		try {
			if (ValidData.checkNull(reqStepLog.getUsername()) == false){
				String messageErr = "Valid GetLogStepsList Username invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resStepLog.setStatus(statusFale);
				resStepLog.setMessage(messageErr);
				return resStepLog;
			}
			if (ValidData.checkNull(reqStepLog.getToken()) == false){
				String messageErr = "Valid GetLogStepsList token invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resStepLog.setStatus(statusFale);
				resStepLog.setMessage(messageErr);
				return resStepLog;
			}
			if (ValidData.checkNull(reqStepLog.getLoan_id()) == false){
				String messageErr = "Valid GetLogStepsList loan_id invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resStepLog.setStatus(statusFale);
				resStepLog.setMessage(messageErr);
				return resStepLog;
			}
			boolean checkLG = userInfo.checkLogin(reqStepLog.getUsername(), reqStepLog.getToken());
			if(!checkLG){
				FileLogger.log("GetLogStepsList: " + reqStepLog.getUsername()+ " check login false:", LogType.BUSSINESS);
				String messageErr = "Yeu cau that bai - Thong tin login sai";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resStepLog.setStatus(statusFale);
				resStepLog.setMessage(messageErr);
				return resStepLog;
			}
		} catch (Exception e) {
			e.printStackTrace();
			String messageErr = "Valid resContractList exception: "+ e;
			FileLogger.log(messageErr, LogType.ERROR);
		}
		return null;
	}
	
	
	public ResContractDetail validgetContractDetail(ReqStepLog reqStepLog){
		ResContractDetail resStepLog = new ResContractDetail();
		List<ResContractList> resContractList = new ArrayList<>();
		try {
			if (ValidData.checkNull(reqStepLog.getUsername()) == false){
				String messageErr = "Valid validgetContractDetail Username invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resStepLog.setStatus(statusFale);
				resStepLog.setMessage(messageErr);
				return resStepLog;
			}
			if (ValidData.checkNull(reqStepLog.getToken()) == false){
				String messageErr = "Valid validgetContractDetail token invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resStepLog.setStatus(statusFale);
				resStepLog.setMessage(messageErr);
				return resStepLog;
			}
			if (ValidData.checkNull(reqStepLog.getLoan_id()) == false){
				String messageErr = "Valid validgetContractDetail loan_id invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resStepLog.setStatus(statusFale);
				resStepLog.setMessage(messageErr);
				return resStepLog;
			}
			boolean checkLG = userInfo.checkLogin(reqStepLog.getUsername(), reqStepLog.getToken());
			if(!checkLG){
				FileLogger.log("validgetContractDetail: " + reqStepLog.getUsername()+ " check login false:", LogType.BUSSINESS);
				String messageErr = "Yeu cau that bai - Thong tin login sai";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resStepLog.setStatus(statusFale);
				resStepLog.setMessage(messageErr);
				return resStepLog;
			}
		} catch (Exception e) {
			e.printStackTrace();
			String messageErr = "Valid validgetContractDetail exception: "+ e;
			FileLogger.log(messageErr, LogType.ERROR);
		}
		return null;
	}
	
	
	public ResUpdateStatus validUpdateStatus(ReqUpdateStatus reqUpdateStatus){
		ResUpdateStatus resUpdateStatus = new ResUpdateStatus();
		try {
			if (ValidData.checkNull(reqUpdateStatus.getUsername()) == false){
				String messageErr = "Valid validUpdateStatus Username invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resUpdateStatus.setStatus(statusFale);
				resUpdateStatus.setMessage(messageErr);
				return resUpdateStatus;
			}
			if (ValidData.checkNull(reqUpdateStatus.getToken()) == false){
				String messageErr = "Valid validUpdateStatus token invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resUpdateStatus.setStatus(statusFale);
				resUpdateStatus.setMessage(messageErr);
				return resUpdateStatus;
			}
			if (ValidData.checkNull(reqUpdateStatus.getLoan_code()) == false){
				String messageErr = "Valid validUpdateStatus loan_id invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resUpdateStatus.setStatus(statusFale);
				resUpdateStatus.setMessage(messageErr);
				return resUpdateStatus;
			}
			boolean checkLG = userInfo.checkLogin(reqUpdateStatus.getUsername(), reqUpdateStatus.getToken());
			if(!checkLG){
				FileLogger.log("validUpdateStatus: " + reqUpdateStatus.getUsername()+ " check login false:", LogType.BUSSINESS);
				String messageErr = "Yeu cau that bai - Thong tin login sai";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resUpdateStatus.setStatus(statusFale);
				resUpdateStatus.setMessage(messageErr);
				return resUpdateStatus;
			}
		} catch (Exception e) {
			e.printStackTrace();
			String messageErr = "Valid validUpdateStatus exception: "+ e;
			FileLogger.log(messageErr, LogType.ERROR);
		}
		return null;
	}
	
	public ResUDExtendStatus updateExtendStatus(ReqUDExtendStatus reqUDExtendStatus){
		ResUDExtendStatus resUDExtendStatus = new ResUDExtendStatus();
		try {
			if (ValidData.checkNull(reqUDExtendStatus.getUsername()) == false){
				String messageErr = "Valid updateExtendStatus Username invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resUDExtendStatus.setStatus(statusFale);
				resUDExtendStatus.setMessage(messageErr);
				return resUDExtendStatus;
			}
			if (ValidData.checkNull(reqUDExtendStatus.getToken()) == false){
				String messageErr = "Valid updateExtendStatus token invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resUDExtendStatus.setStatus(statusFale);
				resUDExtendStatus.setMessage(messageErr);
				return resUDExtendStatus;
			}
			if (ValidData.checkNull(reqUDExtendStatus.getLoan_code()) == false){
				String messageErr = "Valid updateExtendStatus Loan_code invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resUDExtendStatus.setStatus(statusFale);
				resUDExtendStatus.setMessage(messageErr);
				return resUDExtendStatus;
			}
			if (ValidData.checkNull(reqUDExtendStatus.getExtend_status()) == false){
				String messageErr = "Valid updateExtendStatus Extend_status invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resUDExtendStatus.setStatus(statusFale);
				resUDExtendStatus.setMessage(messageErr);
				return resUDExtendStatus;
			}
			if (!reqUDExtendStatus.getExtend_status().equals("1163") && !reqUDExtendStatus.getExtend_status().equals("1164")){
				String messageErr = "Valid updateExtendStatus Extend_status is incorrect";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resUDExtendStatus.setStatus(statusFale);
				resUDExtendStatus.setMessage(messageErr);
				return resUDExtendStatus;
			}
			if (ValidData.checkNull(reqUDExtendStatus.getBill_index()) == false){
				String messageErr = "Valid updateExtendStatus Bill_index invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resUDExtendStatus.setStatus(statusFale);
				resUDExtendStatus.setMessage(messageErr);
				return resUDExtendStatus;
			}
			boolean checkLG = userInfo.checkLogin(reqUDExtendStatus.getUsername(), reqUDExtendStatus.getToken());
			if(!checkLG){
				FileLogger.log("updateExtendStatus: " + reqUDExtendStatus.getUsername()+ " check login false:", LogType.BUSSINESS);
				String messageErr = "Yeu cau that bai - Thong tin login sai";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resUDExtendStatus.setStatus(statusFale);
				resUDExtendStatus.setMessage(messageErr);
				return resUDExtendStatus;
			}
		} catch (Exception e) {
			e.printStackTrace();
			String messageErr = "Valid updateExtendStatus exception: "+ e;
			FileLogger.log(messageErr, LogType.ERROR);
		}
		return null;
	}
	
	public ResAppraisal validUpdateAppraisal(ReqAppraisal reqAppraisal){
		ResAppraisal resAppraisal = new ResAppraisal();
		try {
			if (ValidData.checkNull(reqAppraisal.getUsername()) == false){
				String messageErr = "Valid validUpdateAppraisal Username invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resAppraisal.setStatus(statusFale);
				resAppraisal.setMessage(messageErr);
				return resAppraisal;
			}
			if (ValidData.checkNull(reqAppraisal.getToken()) == false){
				String messageErr = "Valid validUpdateAppraisal token invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resAppraisal.setStatus(statusFale);
				resAppraisal.setMessage(messageErr);
				return resAppraisal;
			}
			if (ValidData.checkNull(reqAppraisal.getLoan_code()) == false){
				String messageErr = "Valid validUpdateAppraisal loan_id invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resAppraisal.setStatus(statusFale);
				resAppraisal.setMessage(messageErr);
				return resAppraisal;
			}
			boolean checkLG = userInfo.checkLogin(reqAppraisal.getUsername(), reqAppraisal.getToken());
			if(!checkLG){
				FileLogger.log("validUpdateAppraisal: " + reqAppraisal.getUsername()+ " check login false:", LogType.BUSSINESS);
				String messageErr = "Yeu cau that bai - Thong tin login sai";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resAppraisal.setStatus(statusFale);
				resAppraisal.setMessage(messageErr);
				return resAppraisal;
			}
		} catch (Exception e) {
			e.printStackTrace();
			String messageErr = "Valid validUpdateAppraisal exception: "+ e;
			FileLogger.log(messageErr, LogType.ERROR);
		}
		return null;
	}
	
	public ResAllotment validSetAllotment(ReqAllotment reqAllotment){
		ResAllotment resAllotment = new ResAllotment();
		try {
			if (ValidData.checkNull(reqAllotment.getUsername()) == false){
				String messageErr = "Valid validSetAllotment Username invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resAllotment.setStatus(statusFale);
				resAllotment.setMessage(messageErr);
				return resAllotment;
			}
			if (ValidData.checkNull(reqAllotment.getToken()) == false){
				String messageErr = "Valid validSetAllotment token invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resAllotment.setStatus(statusFale);
				resAllotment.setMessage(messageErr);
				return resAllotment;
			}
			if (ValidData.checkNull(reqAllotment.getLoan_code()) == false){
				String messageErr = "Valid validSetAllotment loan_id invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resAllotment.setStatus(statusFale);
				resAllotment.setMessage(messageErr);
				return resAllotment;
			}
			boolean checkLG = userInfo.checkLogin(reqAllotment.getUsername(), reqAllotment.getToken());
			if(!checkLG){
				FileLogger.log("validSetAllotment: " + reqAllotment.getUsername()+ " check login false:", LogType.BUSSINESS);
				String messageErr = "Yeu cau that bai - Thong tin login sai";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resAllotment.setStatus(statusFale);
				resAllotment.setMessage(messageErr);
				return resAllotment;
			}
		} catch (Exception e) {
			e.printStackTrace();
			String messageErr = "Valid validSetAllotment exception: "+ e;
			FileLogger.log(messageErr, LogType.ERROR);
		}
		return null;
	}
	
	public ResDebtReminder validGetdebtReminder(ReqDebtReminder reqDebtReminder){
		ResDebtReminder resDebtReminder = new ResDebtReminder();
		try {
			if (ValidData.checkNull(reqDebtReminder.getUsername()) == false){
				String messageErr = "Valid validGetdebtReminder Username invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resDebtReminder.setStatus(statusFale);
				resDebtReminder.setMessage(messageErr);
				return resDebtReminder;
			}
			if (ValidData.checkNull(reqDebtReminder.getToken()) == false){
				String messageErr = "Valid validGetdebtReminder token invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resDebtReminder.setStatus(statusFale);
				resDebtReminder.setMessage(messageErr);
				return resDebtReminder;
			}
			boolean checkLG = userInfo.checkLogin(reqDebtReminder.getUsername(), reqDebtReminder.getToken());
			if(!checkLG){
				FileLogger.log("GetdebtReminder : " + reqDebtReminder.getUsername()+ " check login false:", LogType.BUSSINESS);
				String messageErr = "Yeu cau that bai - Thong tin login sai";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resDebtReminder.setStatus(statusFale);
				resDebtReminder.setMessage(messageErr);
				return resDebtReminder;
			}
		} catch (Exception e) {
			e.printStackTrace();
			String messageErr = "Valid validGetdebtReminder exception: "+ e;
			FileLogger.log(messageErr, LogType.ERROR);
		}
		return null;
	}
	
	public ResAllContractList validListSponsor(ReqContractListSponsor reqContractListSponsor){
		ResAllContractList resContractListSponsor = new ResAllContractList();
		try {
			if (ValidData.checkNull(reqContractListSponsor.getUsername()) == false){
				String messageErr = "Valid ListSponsor Username invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resContractListSponsor.setStatus(statusFale);
				resContractListSponsor.setMessage(messageErr);
				return resContractListSponsor;
			}
			if (ValidData.checkNull(reqContractListSponsor.getToken()) == false){
				String messageErr = "Valid ListSponsor token invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resContractListSponsor.setStatus(statusFale);
				resContractListSponsor.setMessage(messageErr);
				return resContractListSponsor;
			}
			
//			if (ValidData.checkNull(reqContractListSponsor.getSponsor_id()) == false){
//				String messageErr = "Valid ListSponsor Sponsor_id invalid";
//				FileLogger.log(messageErr, LogType.BUSSINESS);
//				resContractListSponsor.setStatus(statusFale);
//				resContractListSponsor.setMessage(messageErr);
//				return resContractListSponsor;
//			}
			
			boolean checkLG = userInfo.checkLogin(reqContractListSponsor.getUsername(), reqContractListSponsor.getToken());
			if(!checkLG){
				FileLogger.log("ListSponsor : " + reqContractListSponsor.getUsername()+ " check login false:", LogType.BUSSINESS);
				String messageErr = "Yeu cau that bai - Thong tin login sai";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resContractListSponsor.setStatus(statusFale);
				resContractListSponsor.setMessage(messageErr);
				return resContractListSponsor;
			}
		} catch (Exception e) {
			e.printStackTrace();
			String messageErr = "Valid resContractList exception: "+ e;
			FileLogger.log(messageErr, LogType.ERROR);
		}
		return null;
	}
	
	public ResDisbursement validDisbursement(ReqDisbursement reqDisbursement){
		ResDisbursement resDisbursement = new ResDisbursement();
		try {
			if (ValidData.checkNull(reqDisbursement.getUsername()) == false){
				String messageErr = "Valid validDisbursement Username invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resDisbursement.setStatus(statusFale);
				resDisbursement.setMessage(messageErr);
				return resDisbursement;
			}
			if (ValidData.checkNull(reqDisbursement.getToken()) == false){
				String messageErr = "Valid validDisbursement token invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resDisbursement.setStatus(statusFale);
				resDisbursement.setMessage(messageErr);
				return resDisbursement;
			}
			
			if (ValidData.checkNull(reqDisbursement.getLoan_code()) == false){
				String messageErr = "Valid validDisbursement Loan_code invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resDisbursement.setStatus(statusFale);
				resDisbursement.setMessage(messageErr);
				return resDisbursement;
			}
			
			if (ValidData.checkNullLong(reqDisbursement.getExpertise_status()) == false){
				String messageErr = "Valid validDisbursement Expertise_status invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resDisbursement.setStatus(statusFale);
				resDisbursement.setMessage(messageErr);
				return resDisbursement;
			}
			
			boolean checkLG = userInfo.checkLogin(reqDisbursement.getUsername(), reqDisbursement.getToken());
			if(!checkLG){
				FileLogger.log("validDisbursement : " + reqDisbursement.getUsername()+ " check login false:", LogType.BUSSINESS);
				String messageErr = "Yeu cau that bai - Thong tin login sai";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resDisbursement.setStatus(statusFale);
				resDisbursement.setMessage(messageErr);
				return resDisbursement;
			}
		} catch (Exception e) {
			e.printStackTrace();
			String messageErr = "Valid validDisbursement exception: "+ e;
			FileLogger.log(messageErr, LogType.ERROR);
		}
		return null;
	}
	
	
	public ResPayment validDataPaymentLoan(ReqPayment reqPayment){
		ResPayment resPayment = new ResPayment();
		try {
			if (ValidData.checkNull(reqPayment.getUsername()) == false){
				String messageErr = "Valid validDataPaymentLoan Username invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resPayment.setStatus(statusFale);
				resPayment.setMessage(messageErr);
				return resPayment;
			}
			if (ValidData.checkNull(reqPayment.getToken()) == false){
				String messageErr = "Valid validDataPaymentLoan token invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resPayment.setStatus(statusFale);
				resPayment.setMessage(messageErr);
				return resPayment;
			}
			
			if (ValidData.checkNull(reqPayment.getLoan_code()) == false){
				String messageErr = "Valid validDataPaymentLoan Loan_code invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resPayment.setStatus(statusFale);
				resPayment.setMessage(messageErr);
				return resPayment;
			}
			
			if (ValidData.checkNull(reqPayment.getBill_index()) == false){
				String messageErr = "Valid validDataPaymentLoan bill_index invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resPayment.setStatus(statusFale);
				resPayment.setMessage(messageErr);
				return resPayment;
			}
			if (ValidData.isNumberInt(reqPayment.getBill_index()) == false){
				String messageErr = "Valid validDataPaymentLoan bill_index is incorrect";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resPayment.setStatus(statusFale);
				resPayment.setMessage(messageErr);
				return resPayment;
			}
			
			if (ValidData.checkNullLong(reqPayment.getPay_amount()) == false){
				String messageErr = "Valid validDataPaymentLoanPay_amount invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resPayment.setStatus(statusFale);
				resPayment.setMessage(messageErr);
				return resPayment;
			}
			
			if (ValidData.isNumberInt(Long.toString(reqPayment.getPay_amount())) == false){
				String messageErr = "Valid validDataPaymentLoan Pay_amount is incorrect";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resPayment.setStatus(statusFale);
				resPayment.setMessage(messageErr);
				return resPayment;
			}
			
			if (ValidData.checkNull(reqPayment.getIs_a_special_payment()) == false){
				String messageErr = "Valid validDataPaymentLoan Is_a_special_payment invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resPayment.setStatus(statusFale);
				resPayment.setMessage(messageErr);
				return resPayment;
			}
			
			boolean checkLG = userInfo.checkLogin(reqPayment.getUsername(), reqPayment.getToken());
			if(!checkLG){
				FileLogger.log("validDataPaymentLoan : " + reqPayment.getUsername()+ " check login false:", LogType.BUSSINESS);
				String messageErr = "Yeu cau that bai - Thong tin login sai";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resPayment.setStatus(statusFale);
				resPayment.setMessage(messageErr);
				return resPayment;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			String messageErr = "Valid validDataPaymentLoan exception: "+ e;
			FileLogger.log(messageErr, LogType.ERROR);
		}
		return null;
	}
	
	
	public ResSettlement validSettlement(ReqSettlement reqSettlement){
		ResSettlement resSettlement = new ResSettlement();
		try {
			if (ValidData.checkNull(reqSettlement.getUsername()) == false){
				String messageErr = "Valid validSettlement Username invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resSettlement.setStatus(statusFale);
				resSettlement.setMessage(messageErr);
				return resSettlement;
			}
			if (ValidData.checkNull(reqSettlement.getToken()) == false){
				String messageErr = "Valid validSettlement token invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resSettlement.setStatus(statusFale);
				resSettlement.setMessage(messageErr);
				return resSettlement;
			}
			
			if (ValidData.checkNull(reqSettlement.getLoan_code()) == false){
				String messageErr = "Valid validSettlement Loan_code invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resSettlement.setStatus(statusFale);
				resSettlement.setMessage(messageErr);
				return resSettlement;
			}
			
			if (ValidData.checkNullLong(reqSettlement.getPay_amount()) == false){
				String messageErr = "Valid validSettlement invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resSettlement.setStatus(statusFale);
				resSettlement.setMessage(messageErr);
				return resSettlement;
			}
			
			if (ValidData.isNumberInt(Long.toString(reqSettlement.getPay_amount())) == false){
				String messageErr = "Valid validSettlement Pay_amount is incorrect";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resSettlement.setStatus(statusFale);
				resSettlement.setMessage(messageErr);
				return resSettlement;
			}
			
			boolean checkLG = userInfo.checkLogin(reqSettlement.getUsername(), reqSettlement.getToken());
			if(!checkLG){
				FileLogger.log("validSettlement : " + reqSettlement.getUsername()+ " check login false:", LogType.BUSSINESS);
				String messageErr = "Yeu cau that bai - Thong tin login sai";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resSettlement.setStatus(statusFale);
				resSettlement.setMessage(messageErr);
				return resSettlement;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			String messageErr = "Valid validSettlement exception: "+ e;
			FileLogger.log(messageErr, LogType.ERROR);
		}
		return null;
	}
	
//	100	Thành công
//	101	Sai token
//	102	Token hết hạn hoặc không tồn tại
//	103	User không tồn tại
//	104	Mật khẩu hoặc user không đúng
//	105	Không thấy thông tin dữ liệu trong database
//	999	Yêu cầu đang được xử lý
//	106	Không thấy thông tin khoản vay
//	107	Khoản vay đã được duyệt
//	108	Khoản vay bị từ chối do user trong Blacklist
//	109	Khoản vay bị từ chối do phương tiện không đáp ứng
//	110	Khoản vại bị từ chôi do thiếu thông tin
	
	public static void main(String[] args) {
//		if(ValidData.isValidDateStr("20200528q") == false){
		try {
//			 SimpleDateFormat formatter6=new SimpleDateFormat("yyyyMMdd");  
//			    Date date1=formatter6.parse("a30200528qd");  
//			    System.out.println(date1);
//				System.out.println(ValidData.isValidDateStr("aa30200528qd"));
				String date = "aa30200528qd";
				if((date.length() != 6)){
					System.out.println("aa");
				}else{
					System.out.println("bb");
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		}
	}
}
