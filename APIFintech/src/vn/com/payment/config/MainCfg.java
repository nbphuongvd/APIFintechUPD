package vn.com.payment.config;

import java.util.ArrayList;

import vn.com.payment.ultities.BaseConfig;


public class MainCfg extends BaseConfig {

	private final static String CONFIG_PATH = "config/main.cfg";
	public static String FILE_LOGGER			 =	"./logs/GW_Payment";
	public static int 	 READ_TIME_OUT 			 = 30000;
	public static int 	 CONNECTION_TIME_OUT 	 = 30000;
	public static String URL_CHECKCARD			 =	"";
	public static String URL_CALLBACK			 =	"";
	public static int 	 TIME_GETTRANS 			 = 7;
	public static int 	 MAXMINUTE_GETTRANS 	 = 7;
	public static int 	 MAX_TRANS	 			 = 100;
	public static String APIKEY					 =	"";
	public static int 	 TIME_RETRY_CHECKPENDING = 60000;
	public static String GATEWAY_ID			 	 = "";
	public static String FORMATTER_DATETIME		=	"yyyy-MM-dd HH:mm:ss";
	public static String FORMATTER_DATE			=	"yyyyMdd";
	public static String FORMATTER_DATE_OUT		=	"MM/dd/yyyy";
	
//	public static String gameCode 				= "";
//	public static String nccCode 				= "";
//	public static String accessKey 				= "";
//	public static String secretKey 				= "";
//	public static String account				= "";
//	public static int 	 type					= 1;
	public static String urlCreaterCard			= "";
	
	public static String urlCheckPending		= "";
	public static String merchantId				= "";
	public static String accessKeyCheckPending	= "";
	public static int 	 RuncheckPending;
	public static String API_KEY				= "";
	public static String KeyTripleDes			= "";
	
	public static String host					= "localhost";
	public static int port 						= 8080;
	public static String pass 					= "GX1cnNgdXfCNUbYKa3";
	public static int max_connect 				= 100;
	public static int max_idle 					= 100;
	public static int index 					= 4;
	public static int isAuth 					= 0; //0: Khong thiet lap mat khau Redis; 1: Thiet lap mat khau Redis 
	public static int min_idle					= 5;
	public static boolean testOnBorrow 			= false;
	public static int redispool_connecttimeout 	= 2000;
	public static int timeExp 					= 300;
	public static String host_name_mongo		= "";
	public static String db_name				= "";
	public static int port_mongo;
	
	

	@Override
	protected void getAllParas() {
		port_mongo					= getInt("port_mongo", 								port_mongo);	
		db_name						= properties.getProperty("db_name", 				db_name).trim();	
		host_name_mongo				= properties.getProperty("host_name_mongo", 		host_name_mongo).trim();	
		KeyTripleDes				= properties.getProperty("KeyTripleDes", 			KeyTripleDes).trim();	
		API_KEY						= properties.getProperty("API_KEY", 				API_KEY).trim();	
		URL_CALLBACK				= properties.getProperty("URL_CALLBACK", 			URL_CALLBACK).trim();			
		APIKEY						= properties.getProperty("APIKEY", 					APIKEY).trim();			
		URL_CHECKCARD				= properties.getProperty("URL_CHECKCARD", 			URL_CHECKCARD).trim();			
		FILE_LOGGER					= properties.getProperty("FILE_LOGGER", 			FILE_LOGGER).trim();			
		READ_TIME_OUT 				= getInt("read_time_out",							READ_TIME_OUT);
		CONNECTION_TIME_OUT 		= getInt("connection_time_out",						CONNECTION_TIME_OUT);
		TIME_GETTRANS 				= getInt("TIME_GETTRANS",							TIME_GETTRANS);
		MAX_TRANS	 				= getInt("MAX_TRANS",								MAX_TRANS);
		TIME_RETRY_CHECKPENDING		= getInt("TIME_RETRY_CHECKPENDING",					TIME_RETRY_CHECKPENDING);
		MAXMINUTE_GETTRANS 			= getInt("MAXMINUTE_GETTRANS",						MAXMINUTE_GETTRANS);
		RuncheckPending 			= getInt("RuncheckPending",							RuncheckPending);
		GATEWAY_ID 					= properties.getProperty("GATEWAY_ID",				GATEWAY_ID).trim();
//		gameCode 					= properties.getProperty("gameCode",				gameCode).trim();
//		nccCode 					= properties.getProperty("nccCode",					nccCode).trim();
//		accessKey 					= properties.getProperty("accessKey",				accessKey).trim();
//		secretKey 					= properties.getProperty("secretKey",				secretKey).trim();
//		account 					= properties.getProperty("account",					account).trim();
//		type		 	     		= getInt("type",									type);
		urlCreaterCard 				= properties.getProperty("urlCreaterCard",			urlCreaterCard).trim();
		urlCheckPending 			= properties.getProperty("urlCheckPending",			urlCheckPending).trim();
		merchantId		 			= properties.getProperty("merchantId",				merchantId).trim();
		accessKeyCheckPending		= properties.getProperty("accessKeyCheckPending",	accessKeyCheckPending).trim();
		
		host 						= properties.getProperty("host", host);			
		port 						= getInt("port", port);
		pass 						= properties.getProperty("pass", pass);
		max_connect 				= getInt("max_connect", max_connect);
		max_idle 					= getInt("max_idle", max_idle);
		min_idle 					= getInt("min_idle", min_idle);
		index 						= getInt("redis_index", index);
		isAuth 						= getInt("isAuth",isAuth);
		redispool_connecttimeout 	= getInt("redispool_connecttimeout", redispool_connecttimeout);
		timeExp 					= getInt("timeExp", timeExp);
	}

	public MainCfg(String configPath) {
		super(configPath);
	}

	private static MainCfg _shareInstance = null;

	public static MainCfg shareInstance() {
		if (_shareInstance == null) {
			new MainCfg(CONFIG_PATH);
			/*new Timer("ConfigProcessor", true).schedule(new TimerTask() {
				@Override
				public void run() {
					System.out.println("--------------------------------------");
					System.out.println(">> Reload config ...");
					MerchantCfg.shareInstance().loadProperties();
				}
			}, TIME_RELOAD_CONFIG * 1000, TIME_RELOAD_CONFIG * 1000);*/
		}
		return _shareInstance;
	}
	
	static {
		MainCfg.shareInstance();
	}
	
	public static void main(String[] args) {
		System.out.println("aa:"+MainCfg.RuncheckPending);
		if(MainCfg.RuncheckPending == 1){
			System.out.println("11111");
		}else{
			System.out.println("@2");
		}
	}
}
