package vn.com.payment.ultities;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.RandomStringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Utils {

	private static MessageDigest msgDigest = null;
	static {
		try {
			msgDigest = MessageDigest.getInstance("MD5");
		} catch (Exception ex) {
		}
	}

	public static String encryptMD5(String message) {
		String digest = null;
		try {
			byte[] hash = msgDigest.digest(message.getBytes("UTF-8"));
			StringBuilder sb = new StringBuilder(2 * hash.length);
			for (byte b : hash) {
				sb.append(String.format("%02x", b & 0xff));
			}
			digest = sb.toString();
		} catch (UnsupportedEncodingException ex) {
			ex.printStackTrace();
		}
		return digest;
	}

	public static Date getLastDateOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DATE, -1);
		calendar.set(Calendar.HOUR, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	public static Date getDateSubtractMinite(Date date, int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MINUTE, -minute);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	private static final Pattern EMAIL_PATTERN = Pattern.compile(
			"[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})",
			Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);

	public static boolean validateEmail(String email) {
		Matcher matcher = EMAIL_PATTERN.matcher(email);
		if (matcher.find())
			return true;
		return false;
	}

	public static String objectToJson(Object obj) {
		try {
			if (obj == null)
				return null;
			Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("dd-MM-yyyy HH:mm:ss").create();
			String json = gson.toJson(obj);
			return json;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static double minuteToHouse(Long time) {
		if (time == null)
			return 0d;
		return ((double) time / 24l) / 60l;
	}

	public static Date convertStringToDate(String fomat, String date) {
		SimpleDateFormat ft = new SimpleDateFormat(fomat);
		try {
			return ft.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String convertDateToString(String fomat, Date date) throws ParseException {
		SimpleDateFormat ft = new SimpleDateFormat(fomat);
		try {
			return ft.format(date);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	public static String removeTrailingZeroes(String s) {
		s = s.indexOf(".") < 0 ? s : s.replaceAll("0*$", "").replaceAll("\\.$", "");
		return s;
	}

	public static String generateAccountCode() {
		return RandomStringUtils.randomNumeric(6);
	}

	public static long getMiliseconds() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		return c.getTimeInMillis();
	}

	public static String getUnitId() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		String id = c.getTimeInMillis() + generateAccountCode();
		return id;
	}

	 public static String objectToGJson(Object obj){
		  try {
			  Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("dd-MM-yyyy HH:mm:ss").create();
			  String json = gson.toJson(obj);
			  return json;
		  } catch (Exception e) {
			  e.printStackTrace();
		  }
		  return null;
	  }
	 
	public static boolean checkNumberPhone(String number) {
		Pattern pattern = Pattern.compile("(\\+84|0)\\d{9,10}"); // (\\+84|0)\\d{9,10}
																	// ^[0-9]*$
		Matcher matcher = pattern.matcher(number);
		if (!matcher.matches()) {
			return false;
		} else if (number.length() == 10 || number.length() == 11) {
			return true;
			/*
			 * if (number.length() == 10) { if (number.substring(0,
			 * 2).equals("09")) { return true; } else { return false; } } else
			 * if (number.substring(0, 2).equals("01")) { return true; } else {
			 * return false; }
			 */
		} else {
			return false;
		}
	}

	 
	public static void main(String[] args) {
		System.out.println(minuteToHouse(5l));
	}

}
