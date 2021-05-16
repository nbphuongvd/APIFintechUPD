/*
 * Copyright (c) by VNPT EPAY. All rights reversal
 * @author: Tung Zom
 * @date: May 19, 2011
 * @file: FileLogger.java
 */
package vn.com.payment.ultities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.thoughtworks.xstream.XStream;

import vn.com.payment.config.MainCfg;
import vn.com.payment.config.LogType;



public class FileLogger {
	private static final String LOG_DIRECTORY = MainCfg.FILE_LOGGER;
	private static String LOG_FOLDER = "ERROR";
	private static String file_name = "Error";
	
	public static String getLogDirectory() {
		return LOG_DIRECTORY;
	}

	/**
	 * Exist.
	 * 
	 * @param path
	 *            the path
	 * @return the boolean
	 */
	public static Boolean exist(String path) {
		File f = new File(path);
		Boolean tmp = f.exists();
		f = null;
		return tmp;
	}

	/**
	 * Creates the directory.
	 * 
	 * @param path
	 *            the path
	 */
	public static void createDirectory(String path) {
		if (!exist(path)) {
			(new File(path)).mkdirs();
		}
	}
	
	public static String objectToXML(Object obj) {
		try {
			XStream xstream = new XStream();
			String xmlString = xstream.toXML(obj);
			return xmlString;
		} catch (Exception e) {
			return "";
		}
	}
	
	public static Object xmlToObject(String xmlString)
	{
		XStream xstream = new XStream();
		return xstream.fromXML(xmlString);
	}
	
	public static void log(Object obj, int logType) {
		try{
			String prefix = "";
			String data = "";
			switch (logType) {
			case LogType.ERROR:			
				LOG_FOLDER = "ERROR";
				file_name = "Error";
				break;			
			case LogType.USERINFO:			
				LOG_FOLDER = "USERINFO";
				file_name = "UserInfo";
				break;
			case LogType.BUSSINESS:			
				LOG_FOLDER = "BUSSINESS";
				file_name = "Bussiness";
				break;
			default:
				break;
			}
			if (obj instanceof String) {
				data = (String)obj;
			} else {
				data = objectToXML(obj);
			}		
			log(prefix + data);
		} catch (Exception e) {
			Debug.showMessage("FileLogger:log: Exception: " + e.getMessage());
			e.printStackTrace();
		}
		
	} 


	/**
	 * Log.
	 * 
	 * @param str
	 *            the str
	 */
	private synchronized static void log(String str) {
		try {
			Date date = new Date();
			String apm = "AM";
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
			String year = formatter.format(date);
			formatter.applyPattern("MM");
			String month = formatter.format(date);
			formatter.applyPattern("dd");
			String day = formatter.format(date);
			formatter.applyPattern("HH");
			String hour = formatter.format(date);
			try {
				if(Integer.parseInt(hour) > 12) apm = "PM";
			} catch (Exception e) {
			}
			String path = LOG_DIRECTORY + File.separator + LOG_FOLDER + File.separator + year + File.separatorChar + month;			
			createDirectory(path);
			
			String fileName = file_name + "_" + day + ".txt";
			formatter.applyPattern("hh:mm:ss");
		    FileOutputStream fos = new FileOutputStream(path + File.separatorChar + fileName, true);
		    Writer out = new OutputStreamWriter(fos, "UTF8");
		    out.write(formatter.format(date) + " " + apm + "  " + str);
		    out.write("\r\n\n");
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//Debug
	public static void debug(String message){
//		if(ApplicationCfg.DEBUG_CONSOLE == 1) System.out.println(message);
	}
}
