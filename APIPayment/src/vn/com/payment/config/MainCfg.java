package vn.com.payment.config;

import java.util.ArrayList;

import vn.com.payment.ultities.BaseConfig;


public class MainCfg extends BaseConfig {

	private final static String CONFIG_PATH = "config/main.cfg";
	public static String FILE_LOGGER			 =	"./logs/GW_Payment";
	public static int 	 READ_TIME_OUT 			 = 30000;
	public static int 	 CONNECTION_TIME_OUT 	 = 30000;
//	public static String URL_CHECKCARD			 =	"";
	public static String URL_CALLBACK			 =	"";
	public static int 	 TIME_GETTRANS 			 = 7;
	public static int 	 MAXMINUTE_GETTRANS 	 = 7;
	public static int 	 MAX_TRANS	 			 = 100;
	public static String APIKEY					 =	"";
	public static int 	 TIME_RETRY_CHECKPENDING = 60000;
	public static String GATEWAY_ID			 	 = "";
	public static String FORMATTER_DATETIME		=	"yyyy-MM-dd HH:mm:ss";
	
//	public static String gameCode 				= "";
//	public static String nccCode 				= "";
//	public static String accessKey 				= "";
//	public static String secretKey 				= "";
//	public static String account				= "";
//	public static int 	 type					= 1;
	public static String urlCreaterCard			= "";
	
	public static String urlCheckPending		= "";
//	public static String merchantId				= "";
//	public static String accessKeyCheckPending	= "";
	public static int 	 RuncheckPending;
	public static String API_KEY				= "";
//	public static String KeyTripleDes			= "";

	@Override
	protected void getAllParas() {
//		KeyTripleDes				= properties.getProperty("KeyTripleDes", 			KeyTripleDes).trim();	
		API_KEY						= properties.getProperty("API_KEY", 				API_KEY).trim();
		RuncheckPending 			= getInt("RuncheckPending",							RuncheckPending);
		URL_CALLBACK				= properties.getProperty("URL_CALLBACK", 			URL_CALLBACK).trim();			
		APIKEY						= properties.getProperty("APIKEY", 					APIKEY).trim();			
//		URL_CHECKCARD				= properties.getProperty("URL_CHECKCARD", 			URL_CHECKCARD).trim();			
		FILE_LOGGER					= properties.getProperty("FILE_LOGGER", 			FILE_LOGGER).trim();			
		READ_TIME_OUT 				= getInt("read_time_out",							READ_TIME_OUT);
		CONNECTION_TIME_OUT 		= getInt("connection_time_out",						CONNECTION_TIME_OUT);
		TIME_GETTRANS 				= getInt("TIME_GETTRANS",							TIME_GETTRANS);
		MAX_TRANS	 				= getInt("MAX_TRANS",								MAX_TRANS);
		TIME_RETRY_CHECKPENDING		= getInt("TIME_RETRY_CHECKPENDING",					TIME_RETRY_CHECKPENDING);
		MAXMINUTE_GETTRANS 			= getInt("MAXMINUTE_GETTRANS",						MAXMINUTE_GETTRANS);	
		GATEWAY_ID 					= properties.getProperty("GATEWAY_ID",				GATEWAY_ID).trim();
//		gameCode 					= properties.getProperty("gameCode",				gameCode).trim();
//		nccCode 					= properties.getProperty("nccCode",					nccCode).trim();
//		accessKey 					= properties.getProperty("accessKey",				accessKey).trim();
//		secretKey 					= properties.getProperty("secretKey",				secretKey).trim();
//		account 					= properties.getProperty("account",					account).trim();
//		type		 	     		= getInt("type",									type);
		urlCreaterCard 				= properties.getProperty("urlCreaterCard",			urlCreaterCard).trim();
		urlCheckPending 			= properties.getProperty("urlCheckPending",			urlCheckPending).trim();
//		merchantId		 			= properties.getProperty("merchantId",				merchantId).trim();
//		accessKeyCheckPending		= properties.getProperty("accessKeyCheckPending",	accessKeyCheckPending).trim();
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
