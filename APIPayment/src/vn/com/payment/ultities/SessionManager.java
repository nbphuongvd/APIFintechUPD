//package vn.com.vnptepay.utilities;
//
//import java.util.Hashtable;
//import java.util.Random;
//
//import com.google.gson.Gson;
//
////import memcache.Memcache;
//import vn.com.vnptepay.config.Config;
//import vn.com.vnptepay.entities.Session;
//import vn.com.vnptepay.entities.User;
//import vn.com.vnptepay.network.ChannelManager;
//import vn.com.vnptepay.redis.BaseRedis;
//import vn.com.vnptepay.redis.SessionRedis;
//import vn.com.vnptepay.redis.UserRedis;
//import vn.com.vnptepay.securities.MD5;
//
//public class SessionManager {
//	static FileLogger logger = new FileLogger(ChannelManager.class);
//	public SessionManager() {
//		super();
//		// sessionList = new Hashtable<String, Vector>();
//		// TODO Auto-generated constructor stub
//		System.out.println("Dang luu tru session bang type: " + Config.session_store_type);
//	}
//
//	static Hashtable<String, Session> sessionList = new Hashtable<String, Session>();
//	private String date_format = "ddMMyyyyHHmmss";
//	static String hexa = "123456789abcdef";
//	static final Gson gsonParse = new Gson();
//	static final String session_key = "S_";
//
//	/**
//	 * Method get an username Session String which is store in System memory
//	 * 
//	 * @return String
//	 */
//	public static Session getSessionId(String username) throws SSException {
//		username = session_key + username.toUpperCase();
//		Session eSession = null;
//		String token = null;
//		if (Config.session_store_type == 1) {
//			try {
//				if (sessionList.containsKey(username)) {
//
//					eSession = sessionList.get(username);
//					return eSession;
//				}
//				return null;
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				logger.error("getSessionId ex" + "\n" + e);
//				//LogException.writeSystemEx("getSessionId", e);
//				SSException gwex = new SSException(e);
//				gwex.setCode(Config.SQLException);
//				throw gwex;
//			}
//		} else {
//			try {
//				//eSession = (Session) Memcache.shareInstance().get(username);
//				//if (token_string!= null && token_string.length() > 0)
//					//token = gsonParse.fromJson(token_string, Session.class);
//					//BaseRedis.set(token_key, gsonParse.toJson(tblUser));
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				//LogException.writeSystemEx("getSessionId", e);
//				if (sessionList.containsKey(username)) {
//					eSession = sessionList.get(username);
//					return eSession;
//				}
//				return null;
//			}
//			if (eSession != null && eSession.getSessionid() != null) {
//				return eSession;
//			} else if (sessionList.containsKey(username)) {
//				eSession = sessionList.get(username);
//				return eSession;
//			} else
//				return null;
//		}
//	}
//
//	/**
//	 * Method get a username Session String in case they make a new login, if
//	 * they were login return old Session String
//	 */
//	public static Session getSessionIDLogin(String username, Integer expiretime) throws Exception {
//		username = session_key + username.toUpperCase();
//		Session eSession = null;
//		SessionRedis sessionRedis = new SessionRedis();
//		if (Config.session_store_type == 1) {//dung RAM
//			if (sessionList.containsKey(username)) {
//
//				eSession = sessionList.get(username);
//				// if(check_time_session(username, sessionID))
//				// removeSession(username);
//				// //return sessionID;
//				// sessionID = getNewSession(username);
//				addSession(username, eSession);
//				return eSession;
//			}
//			return getNewSession(username);
//		} else {//dung memcache
//			try {
//				
//				eSession = sessionRedis.getSessionRedis(username);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				logger.error("getSessionId ex" + "\n" + e);
//				//LogException.writeSystemEx("getSessionIDLogin", e);
//
//				if (sessionList.containsKey(username)) {
//					eSession = sessionList.get(username);
//					// sessionID = getNewSession(username);
//					addSession(username, eSession);
//					return eSession;
//				}
//				eSession = getNewSession(username);
//				if (eSession != null)
//					sessionRedis.pushToRedis(username, eSession, expiretime);
//				return eSession;
//			}
//			if (eSession != null && eSession.getSessionid() != null) {
//				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>TIM THAY SESSSION");
//				sessionRedis.pushToRedis(username, eSession, expiretime);
//				//addSessionMemcache(username, eSession);
//				addSession(username, eSession);
//				return eSession;
//			} else {
//				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>KO TIM THAY SESSSION");
//				// Kiem tra truoc khi phat sinh session moi
//				eSession = sessionList.get(username);
//				if (eSession == null) {
//					eSession = getNewSession(username);
//					sessionRedis.pushToRedis(username, eSession, expiretime);
//					//addSessionMemcache(username, eSession);
//				} else {
//					sessionRedis.pushToRedis(username, eSession, expiretime);
//					//addSessionMemcache(username, eSession);
//					addSession(username, eSession);
//				}
//				// addSession(username, eSession);
//				return eSession;
//			}
//		}
//	}
//
//	/**
//	 * Remove Session String according to it's username
//	 */
//	public static void removeSession(String username) throws SSException {
//		username = session_key + username.toUpperCase();
//		if (Config.session_store_type == 1) {
//			if (sessionList.containsKey(username)) {
//				sessionList.remove(username);
//
//			}
//		} else {
//			// Session eSession = new Session();
//			// try {
//			// Memcache.shareInstance().set(username, eSession, 1);
//			// } catch (Exception e) {
//			// // TODO Auto-generated catch block
//			// // GWException gwex = new GWException(e);
//			// // gwex.setCode(Error.SQLException);
//			// // throw gwex;
//			// LogException.writeSystemEx("removeSession ", e);
//			// }
//			deleteKeyMemcache(username);
//			if (sessionList != null) {
//				if (sessionList.containsKey(username)) {
//					synchronized (sessionList) {
//						sessionList.remove(username);
//					}
//				}
//			}
//		}
//	}
//
//	/**
//	 * Method generate new Session in hex, leng = 48 and mark it with an
//	 * username
//	 * 
//	 * @return String
//	 */
//	public static Session getNewSession(String username) {
//		String session = "";
//		Session eSession = new Session();
//		try {
//			// current_time = Utilities.getCurrentDate(date_format);
//			session = generateSessionID(48);// username + md5password +
//			eSession.setSessionid(session);
//			addSession(username, eSession);
//			return eSession;
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			//Debug.showException(e);
//			//LogException.writeSystemEx("getNewSession", e);
//			return null;
//		}
//
//	}
//
//	/**
//	 * Method add an Session String into an username
//	 */
//	private static void addSession(String username, Session eSession) throws Exception {
//		// username = username.toUpperCase();
//		eSession.setCreatedtime(System.currentTimeMillis());
//		if (sessionList != null) {
//			synchronized (sessionList) {
//				sessionList.put(username, eSession);
//			}
//			// sessionList.notifyAll();
//		}
//		// sessionList.
//	}
//
//	/**
//	 * Method add session object into memcache
//	 */
//	private static void addSessionMemcache(String username, Session eSession) {
//		// username = username.toUpperCase();
//		// deleteKeyMemcache(username);
//		try {
//			eSession.setCreatedtime(System.currentTimeMillis());
//			long timebfset = System.currentTimeMillis();
//			//Memcache.shareInstance().set(username, eSession, Config.Session_time_life + 60);
//			long timeafset = System.currentTimeMillis();
//			//TransactionDump.writeTransaction2File("total time set obj to memcache: ", timeafset - timebfset);
//			logger.error("=>>>>>>>>>>>>>>>>>>>>>>>Add session to memcache success");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			logger.error("addSessionMemcache ex" + "\n" + e);
//			//LogException.writeSystemEx("addSessionMemcache ", e);
//			// GWException gwex = new GWException(e);
//			// gwex.setCode(Error.SQLException);
//			// logger.error("=>>>>>>>>>>>>>>>>>>>>>>>>Add session to memcache
//			// fail");
//		}
//	}
//
//	/**
//	 * Method delete a memcache key value
//	 */
//	private static void deleteKeyMemcache(String key) {
//		key = key.toUpperCase();
//		try {
//			//Memcache.shareInstance().delete(key);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			//Debug.showException(e);
//			//LogException.writeSystemEx("deleteKeyMemcache ", e);
//		}
//	}
//
//	/**
//	 * Method check user and Session login system
//	 * 
//	 * @return Boolean
//	 */
//	private static boolean check_user_session(String username, Session eSession, String md5session) throws Exception {
//		username = username.toUpperCase();
//
//		if (Config.session_store_type == 1) {
//			if (sessionList.containsKey(username)) {
//				try {
//					String sysStoreSession = MD5.hash(eSession.getSessionid());
//					if (md5session.equalsIgnoreCase(sysStoreSession))
//						return true;
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					throw e;
//				}
//			}
//			return false;
//		} else {
//			if (md5session.equalsIgnoreCase(MD5.hash(eSession.getSessionid())))
//				return true;
//			return false;
//		}
//	}
//
//	/**
//	 * Update 12/05/2011 cap nhat them nem ra GWException voi truong hop session
//	 * ko hop le
//	 * 
//	 * @return Boolean
//	 */
//	private static boolean check_time_session(String username, Session eSession, String session) throws SSException {
//		username = username.toUpperCase();
//		long result;
//
//		if (Config.session_store_type == 1) {
//			if (sessionList.containsKey(username)) {
//				long createTime = eSession.getCreatedtime();
//				long current = System.currentTimeMillis();
//				result = (current - createTime) / 1000;
//				if (result > Config.Session_time_life) {
//					sessionList.remove(username);
//					return false;
//				}
//				return true;
//			}
//			return false;
//		} else {
//			if (eSession != null && eSession.getSessionid() != null) {
//				long currentime = System.currentTimeMillis();
//				long sessiontime = eSession.getCreatedtime();
//				if ((currentime - sessiontime) / 1000 > Config.Session_time_life) {
////					removeSession(username);
//					return false;
//				}
//				return true;
//			}
//			return false;
//		}
//	}
//
//	/**
//	 * Cap nhat them throw GWException voi truong hop session ko hop le Method
//	 * check valid session and username login
//	 */
//	public static int check_valid_session(String username, String md5session, Session eSession) {
//		username = session_key + username.toUpperCase();
//		// Output rtObj = new Output();
//		try {
//			if (check_user_session(username, eSession, md5session) == false) {
//				return Config.invalid_session;
//			}
//			if (check_time_session(username, eSession, md5session) == false) {
//				return Config.session_time_out;
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			//LogException.writeSystemEx("check_valid_session", e);
//			// GWException gwex = new GWException(e);
//			// gwex.setCode(Error.SQLException);
//			// throw gwex;
//			return Config.SQLException;
//		}
//
//		return Config.session_success;
//	}
//
//	/**
//	 * Method gennerate new Session from param length
//	 * 
//	 * @return String
//	 */
//	private static String generateSessionID(int length) {
//		char[] text = new char[length];
//		Random rng = new Random();
//		for (int i = 0; i < length; i++) {
//			text[i] = hexa.charAt(rng.nextInt(hexa.length()));
//		}
//		return new String(text);
//	}
//
//	// public static void main(String[] args) {
//	// // String key = generateSessionID(48);
//	// // System.out.println(key);
//	// // Long current = System.currentTimeMillis();
//	// // try {
//	// // Thread.sleep(1000);
//	// // } catch (InterruptedException e) {
//	// // // TODO Auto-generated catch block
//	// // e.printStackTrace();
//	// // }
//	// // Long current1 = System.currentTimeMillis();
//	// //
//	// // System.out.println(current1 - current);
//	// for (int i = 0; i < 10; i++) {
//	// String session = generateSessionID(48);
//	// String key = "abc";
//	// entities.Session eSession = new Session();
//	// eSession.setSessionid(session);
//	// addSession(key, eSession);
//	//
//	// try {
//	// entities.Session test = getSessionId(key);
//	// System.out.println(test.getSessionid());
//	// } catch (GWException e) {
//	// // TODO Auto-generated catch block
//	// e.printStackTrace();
//	// }
//	// }
//	// }
//
//	public static void main(String[] args) {
//		// System.out.println(generateSessionID(48));
//		// ArrayList<String> tmp = new ArrayList<String>();
//		for (int i = 0; i < 101; i++) {
//			new Thread(new Runnable() {
//
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//					process();
//					// try {
//					// Thread.sleep(1000);
//					// } catch (InterruptedException e) {
//					// // TODO Auto-generated catch block
//					// e.printStackTrace();
//					// }
//				}
//
//				void process() {
//					try {
//						Session esession = getSessionIDLogin("anhphuong", 10);
//						System.out.println("SESSION=" + esession.getSessionid());
//						// tmp.add(esession.getSessionid());
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			}).start();
//		}
//	}
//}
