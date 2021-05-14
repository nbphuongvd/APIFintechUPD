package vn.com.payment.ultities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateConvert {
	public static String dateToString(Date date, String formatString) {
		DateFormat dateFormat = new SimpleDateFormat(formatString);// "yyyy/MM/dd
																	// HH:mm:ss");
		return dateFormat.format(date);
	}

	public static String dateToString(Date date) {
		try {
			DateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");// "yyyy/MM/dd
																			// HH:mm:ss");
			return dateFormat.format(date);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";

	}

	public static Date longToDate(long date)throws Exception {
		try {
			return new Date(date);
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
 	}

	public static long dateTolong(Date date) {
		try {
			return date.getTime();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;

	}

	public static long getDateToLong() {
		try {
			Calendar cal = Calendar.getInstance();
			return cal.getTime().getTime();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}

	public static String getdateToString() {
		try {
			DateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");// "yyyy/MM/dd
																			// HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			return dateFormat.format(cal.getTime());

		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;

	}

	// public static String getdateToString(String formatString) {
	// try {
	// DateFormat dateFormat = new SimpleDateFormat(formatString);// "yyyy/MM/dd
	// HH:mm:ss");
	// Calendar cal = Calendar.getInstance();
	// return dateFormat.format(cal.getTime());
	//
	// } catch (Exception e) {
	// // TODO: handle exception
	// }
	// return null;
	//
	// }
	public static Date getdate() {
		try {
			Calendar cal = Calendar.getInstance();
			return cal.getTime();

		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;

	}

	public static Date stringToDate(String str) throws ParseException {
 		SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
		Date date = dateFormat.parse(str);
		return date;
	}

	
	public static String dateFormat(Date date) {
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");// "yyyy/MM/dd
																			// HH:mm:ss");
			return dateFormat.format(date);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";

	}
	//
	public static void main(String[] args) throws Exception {
		System.out.println(getdate());
		
	}

}
