package vn.com.payment.services;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

import vn.com.payment.config.LogType;
import vn.com.payment.config.MainCfg;
import vn.com.payment.entities.Account;
import vn.com.payment.entities.TblProduct;
import vn.com.payment.home.AccountHome;
import vn.com.payment.home.TblProductHome;
import vn.com.payment.object.NotifyObject;
import vn.com.payment.object.ProducResAll;
import vn.com.payment.object.ProductReq;
import vn.com.payment.object.ProductRes;
import vn.com.payment.object.ReqChangePass;
import vn.com.payment.object.ReqLogin;
import vn.com.payment.object.ResChangePass;
import vn.com.payment.object.ResLogin;
import vn.com.payment.object.TokenRedis;
import vn.com.payment.redis.RedisBusiness;
import vn.com.payment.ultities.Commons;
import vn.com.payment.ultities.FileLogger;
import vn.com.payment.ultities.MD5;
import vn.com.payment.ultities.ValidData;

public class UserInfo {
	public static String prefixKey = "APIFintech";
	Gson gson = new Gson();
	long statusSuccess = 100l;
	long statusFale = 111l;
	long statusFaleToken = 104l;
	public Response login(String dataLogin) {
		FileLogger.log("----------------Bat dau login--------------------------", LogType.USERINFO);
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		ResLogin resLogin = new ResLogin();
		try {
			ReqLogin reqLogin = gson.fromJson(dataLogin, ReqLogin.class);
			if (ValidData.checkNull(reqLogin.getUsername()) == false){
				FileLogger.log("login invalid : ", LogType.USERINFO);
				response = response.header(Commons.ReceiveTime, getTimeNow());
				resLogin.setStatus(statusFale);
				resLogin.setToken("");
				resLogin.setRequire_change_pass("");
				return response.header(Commons.ResponseTime, getTimeNow()).entity(resLogin.toJSON()).build();
			}
			AccountHome accountHome = new AccountHome();
			Account acc = accountHome.getAccount(reqLogin.getUsername(), MD5.hash(MD5.hash(reqLogin.getPwd())));

			if (acc != null){
				String key = prefixKey + reqLogin.getUsername();
				String tokenResponse = RedisBusiness.getValue_fromCache(key);
				if(tokenResponse == null){				
					FileLogger.log("login sinh token va push len cache : ", LogType.USERINFO);
					String token = UUID.randomUUID().toString().replace("-", "");
					TokenRedis tokenRedis = new TokenRedis();
					tokenRedis.setToken(token);
					tokenRedis.setUsername(reqLogin.getUsername());
					tokenRedis.setExpire(getTimeEXP());
					boolean checkPush = RedisBusiness.setValue_toCacheTime(key, tokenRedis.toJSON(), MainCfg.timeExp);
					if(checkPush == true){
						FileLogger.log("login Gettoken setValue_toCacheTime success------", LogType.USERINFO);
						resLogin.setStatus(statusSuccess);
						resLogin.setToken(token);
						resLogin.setRequire_change_pass(acc.getRequireChangePass()+"");
						response = response.header(Commons.ReceiveTime, getTimeNow());
					}else{
						FileLogger.log("login Gettoken setValue_toCacheTime false ------", LogType.USERINFO);
						resLogin.setStatus(statusFale);
						resLogin.setToken("");
						resLogin.setRequire_change_pass("");
						response = response.header(Commons.ReceiveTime, getTimeNow());
					}
				}else{
					TokenRedis tokenRedis = gson.fromJson(tokenResponse, TokenRedis.class);
					FileLogger.log("login Gettoken setValue_toCacheTime success------", LogType.USERINFO);
					resLogin.setStatus(statusSuccess);
					resLogin.setToken(tokenRedis.getToken());
					resLogin.setRequire_change_pass("");
					response = response.header(Commons.ReceiveTime, getTimeNow());
				}
			}else{
				FileLogger.log("login Gettoken false username or pass", LogType.USERINFO);
				resLogin.setStatus(statusFaleToken);
				resLogin.setToken("");
				resLogin.setRequire_change_pass("");
				response = response.header(Commons.ReceiveTime, getTimeNow());
			}
			return response.header(Commons.ResponseTime, getTimeNow()).entity(resLogin.toJSON()).build();
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("----------------Ket thuc login Exception "+ e.getMessage(), LogType.ERROR);
			resLogin.setStatus(statusFale);
			resLogin.setToken("");
			resLogin.setRequire_change_pass("");
			response = response.header(Commons.ReceiveTime, getTimeNow());
			return response.header(Commons.ResponseTime, getTimeNow()).entity(resLogin.toJSON()).build();
		}
	}
	
	public Response changePass(String dataChangePass) {
		FileLogger.log("----------------Bat dau changePass--------------------------", LogType.USERINFO);
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		ResChangePass resChangePass = new ResChangePass();
		try {
			ReqChangePass reqChangePass = gson.fromJson(dataChangePass, ReqChangePass.class);
			if (ValidData.checkNull(reqChangePass.getUsername()) == false){
				FileLogger.log("changePass invalid : ", LogType.USERINFO);
				response = response.header(Commons.ReceiveTime, getTimeNow());
				resChangePass.setStatus(statusFale);
				resChangePass.setMessage("That bai - invalid message request");
				return response.header(Commons.ResponseTime, getTimeNow()).entity(resChangePass.toJSON()).build();
			}
			
			AccountHome accountHome = new AccountHome();
			Account acc = accountHome.getAccount(reqChangePass.getUsername(), MD5.hash(MD5.hash(reqChangePass.getPwd())));
			if (acc != null){
				String key = prefixKey + reqChangePass.getUsername();
				String tokenResponse = RedisBusiness.getValue_fromCache(key);
				if(tokenResponse == null){				
					FileLogger.log("changePass sinh token va push len cache : ", LogType.USERINFO);
					String token = UUID.randomUUID().toString().replace("-", "");
					TokenRedis tokenRedis = new TokenRedis();
					tokenRedis.setToken(token);
					tokenRedis.setUsername(reqChangePass.getUsername());
					tokenRedis.setExpire(getTimeEXP());
					boolean checkPush = RedisBusiness.setValue_toCacheTime(key, tokenRedis.toJSON(), MainCfg.timeExp);
					if(checkPush == true){
						FileLogger.log("changePass Gettoken setValue_toCacheTime success------", LogType.USERINFO);						
						acc.setPassword(MD5.hash(MD5.hash(reqChangePass.getNew_pwd())));
						boolean checkUPD = accountHome.updateAccount(acc);
						if(checkUPD){
							resChangePass.setStatus(statusSuccess);
							resChangePass.setMessage("Thanh cong");
						}else{
							resChangePass.setStatus(statusFale);
							resChangePass.setMessage("That bai");
						}
					}else{
						FileLogger.log("changePass Gettoken setValue_toCacheTime false ------", LogType.USERINFO);
						resChangePass.setStatus(statusFale);
						resChangePass.setMessage("That bai - Token sai hoac het han");
					}
				}else{
//					TokenRedis tokenRedis = gson.fromJson(tokenResponse, TokenRedis.class);
					FileLogger.log("changePass Gettoken setValue_toCacheTime success------", LogType.USERINFO);
					acc.setPassword(MD5.hash(MD5.hash(reqChangePass.getNew_pwd())));
					boolean checkUPD = accountHome.updateAccount(acc);
					if(checkUPD){
						resChangePass.setStatus(statusSuccess);
						resChangePass.setMessage("Thanh cong");
					}else{
						resChangePass.setStatus(statusFale);
						resChangePass.setMessage("That bai");
					}
				}
			}else{
				FileLogger.log("changePass Gettoken false username or pass", LogType.USERINFO);
				resChangePass.setStatus(statusFale);
				resChangePass.setMessage("That bai");
			}
			response = response.header(Commons.ReceiveTime, getTimeNow());
			return response.header(Commons.ResponseTime, getTimeNow()).entity(resChangePass.toJSON()).build();
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("----------------Ket thuc changePass Exception "+ e.getMessage(), LogType.ERROR);
			resChangePass.setStatus(statusFale);
			resChangePass.setMessage("That bai");
			response = response.header(Commons.ReceiveTime, getTimeNow());
			return response.header(Commons.ResponseTime, getTimeNow()).entity(resChangePass.toJSON()).build();
		}
	}
	
	public Response resetPass(String dataResetPass) {
		FileLogger.log("----------------Bat dau resetPass--------------------------", LogType.USERINFO);
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		ResChangePass resChangePass = new ResChangePass();
		try {
			ReqChangePass reqChangePass = gson.fromJson(dataResetPass, ReqChangePass.class);
			if (ValidData.checkNull(reqChangePass.getUsername()) == false){
				FileLogger.log("changePass invalid : ", LogType.USERINFO);
				response = response.header(Commons.ReceiveTime, getTimeNow());
				resChangePass.setStatus(statusFale);
				resChangePass.setMessage("That bai");
				return response.header(Commons.ResponseTime, getTimeNow()).entity(resChangePass.toJSON()).build();
			}
			String newPass = getRandomStr(8);
			AccountHome accountHome = new AccountHome();
			Account acc = accountHome.getAccountUsename(reqChangePass.getUsername());
			if (acc != null){
				acc.setPassword(MD5.hash(MD5.hash(newPass)));
				boolean checkUPD = accountHome.updateAccount(acc);
				FileLogger.log("changePass username: " + reqChangePass.getUsername() + " checkUPD newPass:" + checkUPD, LogType.USERINFO);
				if(checkUPD){
					resChangePass.setStatus(statusSuccess);
					resChangePass.setMessage("Thanh cong");
					response = response.header(Commons.ReceiveTime, getTimeNow());
					
					//Sent email
					String key = prefixKey + "_NOTIFY";
					String subject = "Thong bao thay doi mat khau";
					String content = "Mat khau moi cua ban la: " + newPass;
					String message = "1";
					String isHtml  = "true";
					String receiveEmail = acc.getEmail();
					String receiveSMS = "";
					String receiveChat = "";
					String serviceCode = "API";
					String subService = "APIFintech";
					boolean sentNoti = sentNotify(key, subject, content, message, isHtml, receiveEmail, receiveSMS, receiveChat, serviceCode, subService);
					FileLogger.log("changePass sentNoti : " + sentNoti, LogType.USERINFO);
				}else{
					resChangePass.setStatus(statusFale);
					resChangePass.setMessage("That bai");
					response = response.header(Commons.ReceiveTime, getTimeNow());
				}
			}else{
				FileLogger.log("changePass username: " + reqChangePass.getUsername() + " false getAccountUsename null:", LogType.USERINFO);
				resChangePass.setStatus(statusFale);
				resChangePass.setMessage("That bai");
				response = response.header(Commons.ReceiveTime, getTimeNow());
			}
			return response.header(Commons.ResponseTime, getTimeNow()).entity(resChangePass.toJSON()).build();
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("----------------Ket thuc resetPass Exception "+ e.getMessage(), LogType.ERROR);
			resChangePass.setStatus(statusFale);
			resChangePass.setMessage("That bai");
			response = response.header(Commons.ReceiveTime, getTimeNow());
			return response.header(Commons.ResponseTime, getTimeNow()).entity(resChangePass.toJSON()).build();
		}
	}
	
	
	
	public static String getTimeNow() {
		SimpleDateFormat format = new SimpleDateFormat(MainCfg.FORMATTER_DATETIME);
		return format.format(new Date());
	}
	
	public static String getTimeEXP() {
		SimpleDateFormat format = new SimpleDateFormat(MainCfg.FORMATTER_DATE);
		Date dt = new Date();
		Calendar c = Calendar.getInstance(); 
		c.setTime(dt); 
		c.add(Calendar.DATE, 1);
		dt = c.getTime();
		System.out.println(format.format(dt));
		return format.format(dt);
	}
	
	public static String getRandomStr(int length) {
		String stock = "0123456789abcdefghijklmnopqrstuvwxyz";
		String ran = "";
		for (int i = 0; i < length; i++) {
			ran += stock.charAt(new Random().nextInt(stock.length() - 1));
		}
		return ran;
	}
	
	public boolean sentNotify(String key, String subject, String content, String message_type, String is_html, String receive_email_expect, String receive_sms_expect, String receive_chat_id_expect, String service_code, String sub_service_code){
		boolean sent = false;
		try {
			NotifyObject notifyObject = new NotifyObject();
			notifyObject.setSubject(subject);
			notifyObject.setContent(content);
			notifyObject.setMessage_type(message_type);
			notifyObject.setIs_html(is_html);
			notifyObject.setReceive_email_expect(receive_email_expect);
			notifyObject.setReceive_sms_expect(receive_sms_expect);
			notifyObject.setReceive_chat_id_expect(receive_chat_id_expect);
			notifyObject.setService_code(service_code);
			notifyObject.setSub_service_code(sub_service_code);
			FileLogger.log("sentNotify key : " + key, LogType.USERINFO);
			FileLogger.log("sentNotify notifyObject : " + notifyObject.toJSON(), LogType.USERINFO);
			RedisBusiness redisBusiness = new RedisBusiness();
			boolean checkPush = redisBusiness.enQueueToRedis(key, notifyObject);
			FileLogger.log("sentNotify checkPush : " + checkPush, LogType.USERINFO);
			if(checkPush){
				sent = true;
			}else{
				sent = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sent;
	}
	
	public static void main(String[] args) {
		try {
//			System.out.println(getRandomStr(8));
//			System.out.println(URLEncoder.encode("yQ/N3ntKC40cDV9Q0ha4J5b3X77Ws0tcE616aZJhy+E\u003d", "UTF-8"));
//			System.out.println(URLDecoder.decode("yQ%2FN3ntKC40cDV9Q0ha4J5b3X77Ws0tcE616aZJhy%2BE%3D", "UTF-8"));
//			System.out.println(getTimeEXP());
//			SimpleDateFormat format = new SimpleDateFormat(MainCfg.FORMATTER_DATE);
//			format.format(new Date());
//			Date dt = new Date();
//			Calendar c = Calendar.getInstance(); 
//			c.setTime(dt); 
//			c.add(Calendar.DATE, 1);
//			dt = c.getTime();
//			
			System.out.println(MD5.hash(MD5.hash("123456")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
