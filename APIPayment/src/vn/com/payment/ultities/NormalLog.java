//package vn.com.vnptepay.utilities;
//
//public class NormalLog {
//
//	static FileLogger logger = new FileLogger(NormalLog.class);
//	
//	public static FileLogger Getlogger()
//	{
//		if(logger != null)
//			return logger;
//		else
//		logger = new FileLogger(NormalLog.class);
//		return logger;
//	}
//	
//	public static void Info(String info)
//	{
//		logger.info(info);
//	}
//	
//	public static void Fatal(Exception ex)
//	{
//		logger.fatal(ex.getClass().toString(), ex);
//	}	
//}
