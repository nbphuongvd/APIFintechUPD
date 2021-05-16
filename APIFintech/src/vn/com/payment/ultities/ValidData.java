package vn.com.payment.ultities;

public class ValidData {

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
	
	public static void main(String[] args) {
		long a = 0;
		
		System.out.println(checkNullLong(a));
	}
}
