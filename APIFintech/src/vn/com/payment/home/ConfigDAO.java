//package com.vnptepay.dao;
//
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.Statement;
//import java.util.Hashtable;
//import java.util.Timer;
//import java.util.TimerTask;
//
//import com.vnptepay.commons.LogType;
//import com.vnptepay.config.MainConfig;
//import com.vnptepay.db.pool.BaseDAO;
//import com.vnptepay.entities.ConfigObj;
//import com.vnptepay.utilities.FileLogger;
//
//public class ConfigDAO extends BaseDAO{
//	protected static Hashtable<String, ConfigObj> CONFIGS = new Hashtable<String, ConfigObj>();
//	private static ConfigDAO _shareInstance = null;
//
//	public static ConfigDAO shareInstance() {
//		if (_shareInstance == null) {
//			new ConfigDAO();
//			new Timer("ConfigReloader", true).schedule(new TimerTask() {
//				@Override
//				public void run() {					
//					System.out.println("--------------------------------------");
//					System.out.println(">> Reload ConfigDAO ...");
//					loadMapping();
//				}
//			}, MainConfig.START_TIME_RELOAD_CONFIG * 1000, MainConfig.TIME_RELOAD_CODES * 1000);
//		}
//		return _shareInstance;
//	}
//
//	public ConfigDAO() {
//		_shareInstance = this;
//		loadMapping();
//	}
//
//	protected static void loadMapping() {
//		
//		Hashtable<String, ConfigObj> CONFIG = new Hashtable<String, ConfigObj>();
//		Connection conn = null;
//		Statement command = null;
//		ResultSet rs = null;
//		String module = "APP";
//		try {
//			String sql = "SELECT * FROM TBL_CONFIGS WHERE MODULE = '"+module+"'";
//			conn = getConnection();
//			command = conn.prepareStatement(sql);
//			rs = command.executeQuery(sql);
//			while (rs.next()) {
//				try {
//					ConfigObj config = new ConfigObj();
//					config.setCfgKey(rs.getString("CFG_KEY"));
//					config.setCfgValue(rs.getString("CFG_VALUE"));
//					config.setCfgType(rs.getString("CFG_TYPE"));
//					config.setModule(rs.getString("MODULE"));
//					config.setDescribe(rs.getString("DESCRIBE"));
//					config.setCrtTime(rs.getString("CRT_TIME"));
//					CONFIG.put(config.getCfgKey(), config);
//				} catch (Exception e) {
//					e.printStackTrace();
//					FileLogger.log(e, LogType.ERROR);
//				}
//			}
//			if(CONFIG.size() > 0) CONFIGS =	CONFIG;
//		} catch (Exception e) {
//			e.printStackTrace();
//			FileLogger.log(e, LogType.ERROR);
//		} finally {
//			releaseConnection(conn, command, rs);	
//		}
//	}
//	public static ConfigObj getConfigObj(String cfgKey) {
//		if(CONFIGS.containsKey(cfgKey))
//			return CONFIGS.get(cfgKey);
//		else return null;
//	}
////	static {
////		ConfigDAO.shareInstance();
////	}
//}
