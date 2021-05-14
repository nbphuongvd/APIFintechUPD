package vn.com.payment.ultities;

import java.io.File;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

//import com.vnptepay.commons.LogType;


public class Commons {
	
	/*==========================================================*/
	public final static String kStandardDateFormat = "yyyy.MM.dd HH:mm:ss";
	public final static String kLogTimeFormat = "yyyy.MM.dd HH:mm:ss";
	public final static String kDateNowFullFormat = "yyMMddHHmmss";
	public final static String kFormatResponseCode = "%1$s";
	public final static String kDateForRandomFormat = "yyMMddHH";
	public final static String kDateForTelcoTransID = "yyMMddHHmm";
	public static final String ResponseTime			= "ResponseTime";	
	public static final String ReceiveTime			= "ReceiveTime";
	/*==========================================================*/
//	public static boolean recognizeAxisFaultIsFail(Exception e){
//		if (e.getMessage().toLowerCase().contains("connection refused")
//				|| e.getMessage().toLowerCase().contains("not found")
//				|| e.getMessage().toLowerCase().contains("internal server error"))
//		{
//			return true;
//		}
//		return false;
//	}
//	
//	public static boolean recognizeExceptionToResetSocket(Exception e){
//		if (e == null || e.getMessage() == null)
//			return false;
//		if (e.getMessage().toLowerCase().contains("connection refused")
//				|| e.getMessage().toLowerCase().contains("connection reset")
//				|| e.getMessage().toLowerCase().contains("software caused connection")
//				|| e.getMessage().toLowerCase().contains("connect timed out")
//				|| e.getMessage().toLowerCase().contains("socket write error"))
//			return true;
//		else return false;
//	}
	
	/**
	 * 	- Convert Timestamp to String with specific standard Date format.
	 * @param stampDate
	 * @return
	 */
	public static String getDateTimeString(Timestamp stampDate){
		return getDateTimeString(stampDate, kStandardDateFormat);
	}
	
	
	/**
	 * 	- Convert time (in Timestamp format) to String with specific Date format.
	 * @param tsTime
	 * @return
	 */
	public static String getDateTimeString(Timestamp tsTime, String strFormat){
		String strDate = "";
		try{
			if (tsTime == null || strFormat == null)
				return strDate;
			SimpleDateFormat dateFormat = new SimpleDateFormat(strFormat);
			strDate = dateFormat.format(tsTime);
		}catch (Exception e) {
			e.printStackTrace();
//			FileLogger.log(e, LogType.ERROR);
		}
		return strDate;
	}
	
	/**
	 * 	- Convert time (in long-format) to String with specific Date format.
	 * @param lTime
	 * @param strFormat
	 * @return
	 */
	public final static String getDateTimeString(long lTime, String strFormat){
		return getDateTimeString(new Timestamp(lTime), strFormat);
	}
	
	
	/**
	 * 	- Get timeNow with TimeStamp object format.
	 * @return
	 */
	public static Timestamp getTimeStampNow(){
		Date today = new Date();
		Timestamp timeNow = new Timestamp(today.getTime());
		//String logTimeStr = Commons.GetDateTimeString(logTime, Commons.kLogTimeFormat);
		return timeNow;
	}
	
	/**
	 * 	- Get timeNow as long
	 * @return
	 */
	public static long getTimeNow(){
		Date today = new Date();
		return today.getTime();
	}
	
	/**
	 * 	- Convert a number to string with specify length.
	 * @param iValue
	 * @param numericAmount
	 * @return
	 */
	public static String numberToString(int iValue, int numericAmount){
		String format = "";
		for (int i = 0; i < numericAmount; i++)
			format += "0";
		DecimalFormat formatter = new DecimalFormat(format);
		return formatter.format(iValue);
	}
	
	/**
	 * 	- Check a string is empty
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(String value){
		return (value == null || value.equals(""));
	}
	
	/**
	 *  - Create directory for a file path if unexisted.
	 * @param filePath
	 */
	public static void createDirectoryForFilePath(String filePath){
		File file = new File(filePath);
		if (!file.getParentFile().exists())
			file.getParentFile().mkdirs();
	}
	
	/**
	 * 	- Create Directory if unexisted.
	 * @param path
	 */
	public static void createDirectory(String path) {
		if (!exist(path))
			(new File(path)).mkdirs();
	}
	
	/**
	 * 	- Check a path is existed?
	 * @param path
	 * @return
	 */
	public static Boolean exist(String path) {
		File f = new File(path);
		Boolean tmp = f.exists();
		f = null;
		return tmp;
	}
	
	
	
	/**
	 * 	- Create a random Request-ID, for testing. (having 16 chars: yMMddHHmmssRRRR)
	 * @return
	 */
	public static String createRandomRequestID(){
		String timeNow = Commons.getDateTimeString(Commons.getTimeStampNow(),
				Commons.kDateForRandomFormat);
		Random rand = new Random();
		int randFuck = rand.nextInt(999);
		int randFirst = rand.nextInt(10);
		if (randFirst == 0) randFirst = 1;
		randFuck = randFirst * 1000 + randFuck;
		timeNow = timeNow + randFuck;
		return timeNow;
	}
	
	
	public static ArrayList<String> sortList(ArrayList<String> listSources, boolean asc){
		if (listSources == null || listSources.size() == 0)
			return listSources;
		
		int n = listSources.size();
		int resultComp = 0;
		String one, two;
		for (int i = 0; i < n - 1; i++)
			for (int j = i + 1; j < n; j++){
				one = listSources.get(i);
				two = listSources.get(j);
				resultComp = compareString(one, two);
				//Sap xep tang
				if (asc && resultComp > 0 ){
					listSources.remove(i); listSources.add(i, two);
					listSources.remove(j); listSources.add(j, one);
				}
				else if (!asc && resultComp < 0){
					listSources.remove(i); listSources.add(i, two);
					listSources.remove(j); listSources.add(j, one);
				}
			}
		return listSources;
	}
	
	public static ArrayList<String> toUpcaseListStrings(ArrayList<String> listSources){
		if (listSources == null || listSources.size() == 0)
			return listSources;
		for (int i = 0; i < listSources.size(); i++){
			String strUpCase = listSources.get(i);
			if (!Commons.isEmpty(strUpCase)){
				listSources.remove(i);
				listSources.add(i, strUpCase.toUpperCase());
			}
		}
		return listSources;
	}
	
	public static int compareString(String one, String two){
		if (one == null){
			if (two == null)
				return 0;
			else
				return -1;
		}
		if (two == null)
			return 1;
		if (one.length() > two.length())
			return 1;
		else if (one.length() < two.length())
			return -1;
		else {
			return one.compareTo(two);
		}
	}
	
	
	public static String getAmountString(double amountOnCash){
		DecimalFormat formatter = new DecimalFormat("0.00");
		String strAmount = formatter.format(amountOnCash); //Double.toString(amountOnCash);
		//Debug.Info(strAmount);
		return strAmount;
	}
	
	/**
	 * 	- Convert a number in string type to a string with fixed-length.
	 * @param strValue
	 * @param numericAmount
	 * @return
	 */
	public static String numberToString(String strValue, int numericAmount){
		if (strValue == null)
			strValue = "";
		int length = strValue.length();
		String prefix = "";
		for (int i = length; i < numericAmount; i++)
			prefix += "0";
		return prefix + strValue;
	}
	
	/**
	 * 	- Cat String lay 1 doan dau voi do dai co dinh (them dau ... o cuoi)
	 * @param input
	 * @param lengthLimit
	 * @return
	 */
	public static String summaryString(String input, int lengthLimit){
		String output = "";
		if (Commons.isEmpty(input))
			return output;
		if (input.length() <= lengthLimit)
			output = input;
		else{
			if (lengthLimit <= 0)
				output = "";
			else{
				if (lengthLimit > 3){
					output = input.substring(0, lengthLimit - 3) + "...";
				}
				else
					output = input.substring(0, lengthLimit);
			}
		}
		return output;
	}
	
	/**
	 * 	- Dinh dang sinh: XyyMMddHHmm9999
	 * 	blacknight@NOTE: de tranh trung voi phien ban Beeline truoc cua Mr. Viet
	 * 	=> Co them ky tu X o dau >= 4
	 * @return
	 */
	public static String createTelcoTransID(){
		String timeNow = Commons.getDateTimeString(Commons.getTimeStampNow(),
				Commons.kDateForTelcoTransID);
		//De tranh trung voi aViet truoc, sinh ra ky tu dau tien
		Random rand = new Random();
		int randFuck = rand.nextInt(9999);
		int randFirst = 4 + rand.nextInt(5); //int randFirst = 4;
		String telcoTransID = randFirst + timeNow + Commons.numberToString(randFuck, 4);
		return telcoTransID;
	}
	static Random rand = new Random();
	public synchronized static String createRequestID(int lengthMax){
		String st = String.valueOf(new Date().getTime()) + Math.abs(rand.nextInt());
		if(st.length() > lengthMax){
			st = st.substring(st.length() - lengthMax);
		}
		return st;
	}
	
//	public static String createTelcoTransID(){
//		String timeNow = Commons.getDateTimeString(Commons.getTimeStampNow(),
//				Commons.kDateForTelcoTransID);
//		
////		Random rand = new Random();
////		int randFuck = rand.nextInt(999);
////		String telcoTransID = timeNow + Commons.numberToString(randFuck, 3);
////		return telcoTransID;
//		
//		//De tranh trung voi aViet truoc, sinh ra ky tu dau tien
//		Random rand = new Random();
//		int randFuck = rand.nextInt(99);
//		int randFirst = 4 + rand.nextInt(5);
//		//int randFirst = 4;
//		String telcoTransID = randFirst + timeNow + Commons.numberToString(randFuck, 2);
//		return telcoTransID;
//	}
	
	public static String bytesToHex(final byte[] bytes) {
		final StringBuilder buf = new StringBuilder(bytes.length * 2);
		for (final byte b : bytes) {
			final String hex = Integer.toHexString(0xff & b);
			if (hex.length() == 1) {
				buf.append("0");
			}
			buf.append(hex);
		}
		return buf.toString();
	}
	
	public static String getLocalIP() {
	    try {   
	        return InetAddress.getLocalHost().getHostAddress();	        
	      } catch (Exception e) {
	          e.printStackTrace();
	          return "127.0.0.1";
	      }
	}
	
	public static void main(String[] args) {
		getLocalIP();
	}
}
