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
import vn.com.payment.entities.Permmission;
import vn.com.payment.entities.SubPermission;
import vn.com.payment.home.AccountHome;
import vn.com.payment.home.GroupMapPerHome;
import vn.com.payment.home.PermmissionHome;
import vn.com.payment.home.SubPermissionHome;
import vn.com.payment.ultities.Commons;

public class Test {
	public Response getAAA() {
		ResponseBuilder response = Response.status(Status.OK).entity("x");

		GroupMapPerHome groupMapPerHome = new GroupMapPerHome();
		SubPermissionHome subPermissionHome = new SubPermissionHome();

		Ojjectaaa obOjjectaaa = new Ojjectaaa();

		ArrayList<String> cars = new ArrayList<String>();

		List<GroupMapPer> results = groupMapPerHome.getGroupMapPer(9);
		ArrayList<Integer> myNumbers = new ArrayList<Integer>();
		for (GroupMapPer groupMapPer : results) {
			List<SubPermission> getSubPermission = subPermissionHome.getSubPermission(groupMapPer.getSubPermissionId());

			System.out.println(getSubPermission.size());

			for (SubPermission subPermission : getSubPermission) {
				if (!myNumbers.contains(subPermission.getPermissionId())) {
					myNumbers.add(subPermission.getPermissionId());
				}
			}
		}
		System.out.println(myNumbers);

		// [1, 3, 5, 2, 4, 6]
		PermmissionHome permmissionHome = new PermmissionHome();
		ObjTest objTest1 = new ObjTest();
		for (int i : myNumbers) {
			Permmission permmission = permmissionHome.getPermmission(i);
			List<SubPermission> getSubPermission = subPermissionHome.getSubPermissionid(i);
			objTest1.setName(permmission.getName());
			objTest1.setPermission(getSubPermission);
			cars.add(objTest1.toJSON());
		}
		obOjjectaaa.setName(cars.toString());
		response = response.header(Commons.ReceiveTime, getTimeNow());
		return response.header(Commons.ResponseTime, getTimeNow()).entity(obOjjectaaa.toJSON().replace("\\", "")).build();
	}

	public static String getTimeNow() {
		SimpleDateFormat format = new SimpleDateFormat(MainCfg.FORMATTER_DATETIME);
		return format.format(new Date());
	}
}
