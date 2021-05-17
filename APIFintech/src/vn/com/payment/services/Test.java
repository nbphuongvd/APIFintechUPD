package vn.com.payment.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

import vn.com.payment.config.MainCfg;
import vn.com.payment.entities.Account;
import vn.com.payment.entities.GroupMapPer;
import vn.com.payment.entities.SubPermission;
import vn.com.payment.home.AccountHome;
import vn.com.payment.home.GroupMapPerHome;
import vn.com.payment.home.SubPermissionHome;
import vn.com.payment.ultities.Commons;

public class Test {
	public Response getAAA() {
		ResponseBuilder response = Response.status(Status.OK).entity("x");
		
		GroupMapPerHome groupMapPerHome = new GroupMapPerHome();
		SubPermissionHome subPermissionHome = new SubPermissionHome();
//		AccountHome accountHome = new AccountHome();
//		
//		Account acc = accountHome.getAccountUsename("admin");
				
		Ojjectaaa obOjjectaaa = new Ojjectaaa();
		
		
		ArrayList<String> cars = new ArrayList<String>();
		
		List<GroupMapPer> results = groupMapPerHome.getGroupMapPer(9);
		for (GroupMapPer groupMapPer : results) {
			List<SubPermission> getSubPermission = subPermissionHome.getSubPermission(groupMapPer.getSubPermissionId());
			ObjTest objTest1 = new ObjTest();
			objTest1.setName("aaa");
			objTest1.setPermission(getSubPermission);
			
			cars.add(objTest1.toJSON());
		}
		
		obOjjectaaa.setName(cars.toString());
//		
//		String json = new Gson().toJson(cars);
//		
//	    System.out.println(cars);
//	    Gson gson = new Gson();
//	    obOjjectaaa.setName(json);
		
		response = response.header(Commons.ReceiveTime, getTimeNow());
		return response.header(Commons.ResponseTime, getTimeNow()).entity(obOjjectaaa.toJSON().replace("\\", "")).build();
	}
	
	public static String getTimeNow() {
		SimpleDateFormat format = new SimpleDateFormat(MainCfg.FORMATTER_DATETIME);
		return format.format(new Date());
	}
}
