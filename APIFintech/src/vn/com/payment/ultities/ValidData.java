package vn.com.payment.ultities;

import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.corba.se.impl.transport.ReadTCPTimeoutsImpl;

import vn.com.payment.config.LogType;
import vn.com.payment.home.TblLoanRequestHome;
import vn.com.payment.object.ReqCreaterLoan;
import vn.com.payment.object.ResCreaterLoan;

import java.text.ParseException;

public class ValidData {
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
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
	
	public static void main(String[] args) {
		long a = 0;
		
		System.out.println(checkNullLong(a));
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
			boolean checkContract =  tblLoanRequestHome.checktblLoanRequest(reqCreaterLoan.getLoan_code());
			if(!checkContract){
				String messageErr = "Valid CreaterLoan loan_code already exists ";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resCreaterLoan.setStatus(statusFale);
				resCreaterLoan.setMessage(messageErr);
				return resCreaterLoan;
			}	
			if (ValidData.checkNullLong(reqCreaterLoan.getProduct_type()) == false){
				String messageErr = "Valid CreaterLoan product_type invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resCreaterLoan.setStatus(statusFale);
				resCreaterLoan.setMessage(messageErr);
				return resCreaterLoan;
			}
			if (ValidData.checkNull(reqCreaterLoan.getProduct_brand()) == false){
				String messageErr = "Valid CreaterLoan product_brand invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resCreaterLoan.setStatus(statusFale);
				resCreaterLoan.setMessage(messageErr);
				return resCreaterLoan;
			}
			if (ValidData.checkNullLong(reqCreaterLoan.getTotal_run()) == false){
				String messageErr = "Valid CreaterLoan total_run invalid";
				FileLogger.log(messageErr, LogType.BUSSINESS);
				resCreaterLoan.setStatus(statusFale);
				resCreaterLoan.setMessage(messageErr);
				return resCreaterLoan;
			}
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
		} catch (Exception e) {
			e.printStackTrace();
			String messageErr = "Valid CreaterLoan exception: "+ e;
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
}
