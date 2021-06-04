package vn.com.payment.services;

import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import vn.com.payment.config.MainCfg;

@Path("/")
public class ApiController {

   @POST
   @Path("/api/login")
   @Produces({MediaType.APPLICATION_JSON})
   public Response login(@Context HttpServletRequest requestContext, @javax.ws.rs.core.Context HttpHeaders headers, String formParams) {
	   System.out.println("Vao xooong login");
	   UserInfo userInfo = new UserInfo();
   	   System.out.println(formParams);
	   return userInfo.login(formParams);
   }
   
   @POST
   @Path("/api/changePass")
   @Produces({MediaType.APPLICATION_JSON})
   public Response changePass(@Context HttpServletRequest requestContext, @javax.ws.rs.core.Context HttpHeaders headers, String formParams) {
	   System.out.println("Vao xooong changePass");
	   UserInfo userInfo = new UserInfo();
   	   System.out.println(formParams);
	   return userInfo.changePass(formParams);
   }
   
   @POST
   @Path("/api/resetPass")
   @Produces({MediaType.APPLICATION_JSON})
   public Response resetPass(@Context HttpServletRequest requestContext, @javax.ws.rs.core.Context HttpHeaders headers, String formParams) {
	   System.out.println("Vao xooong resetPass");
	   UserInfo userInfo = new UserInfo();
   	   System.out.println(formParams);
	   return userInfo.resetPass(formParams);
   }
   
   @POST
   @Path("/api/getProduct")
   @Produces({MediaType.APPLICATION_JSON})
   public Response getProduct(@Context HttpServletRequest requestContext, @javax.ws.rs.core.Context HttpHeaders headers, String formParams) {
	   System.out.println("Vao xooong getProduct");
	   Bussiness bussiness = new Bussiness();
   	   System.out.println(formParams);
	   return bussiness.getProduct(formParams);
   }
   
   @POST
   @Path("/api/getRateConfig")
   @Produces({MediaType.APPLICATION_JSON})
   public Response getRateConfig(@Context HttpServletRequest requestContext, @javax.ws.rs.core.Context HttpHeaders headers, String formParams) {
	   System.out.println("Vao xooong getProduct");
	   Bussiness bussiness = new Bussiness();
   	   System.out.println(formParams);
	   return bussiness.getRateConfig(formParams);
   }
   
   @POST
   @Path("/api/getBank")
   @Produces({MediaType.APPLICATION_JSON})
   public Response getBank(@Context HttpServletRequest requestContext, @javax.ws.rs.core.Context HttpHeaders headers, String formParams) {
	   System.out.println("Vao xooong getProduct");
	   Bussiness bussiness = new Bussiness();
   	   System.out.println(formParams);
	   return bussiness.getBank(formParams);
   }
   
   @POST
   @Path("/api/createrLoan")
   @Produces({MediaType.APPLICATION_JSON})
   public Response createrLoan(@Context HttpServletRequest requestContext, @javax.ws.rs.core.Context HttpHeaders headers, String formParams) {
	   System.out.println("Vao xooong createrLoan");
	   Bussiness bussiness = new Bussiness();
   	   System.out.println(formParams);
	   return bussiness.createrLoan(formParams);
   }
   
   @POST
   @Path("/api/getIllustration")
   @Produces({MediaType.APPLICATION_JSON})
   public Response getIllustration(@Context HttpServletRequest requestContext, @javax.ws.rs.core.Context HttpHeaders headers, String formParams) {
	   System.out.println("Vao xooong getIllustration");
	   Bussiness bussiness = new Bussiness();
   	   System.out.println(formParams);
	   return bussiness.getIllustration(formParams);
   }
   
   @POST
   @Path("/api/getContractNumber")
   @Produces({MediaType.APPLICATION_JSON})
   public Response getContractNumber(@Context HttpServletRequest requestContext, @javax.ws.rs.core.Context HttpHeaders headers, String formParams) {
	   System.out.println("Vao xooong getContractNumber");
	   Bussiness bussiness = new Bussiness();
   	   System.out.println(formParams);
	   return bussiness.getContractNumber(formParams);
   }
   
   @POST
   @Path("/api/getContractList")
   @Produces({MediaType.APPLICATION_JSON})
   public Response getContractList(@Context HttpServletRequest requestContext, @javax.ws.rs.core.Context HttpHeaders headers, String formParams) {
	   System.out.println("Vao xooong getContractList");
	   Bussiness bussiness = new Bussiness();
   	   System.out.println(formParams);
	   return bussiness.getContractList(formParams);
   }
   
   @POST
   @Path("/api/getLogStepsList")
   @Produces({MediaType.APPLICATION_JSON})
   public Response getLogStepsList(@Context HttpServletRequest requestContext, @javax.ws.rs.core.Context HttpHeaders headers, String formParams) {
	   System.out.println("Vao xooong getContractList");
	   Bussiness bussiness = new Bussiness();
   	   System.out.println(formParams);
	   return bussiness.getLogStepsList(formParams);
   }
   
   @POST
   @Path("/api/getContractDetail")
   @Produces({MediaType.APPLICATION_JSON})
   public Response getContractDetail(@Context HttpServletRequest requestContext, @javax.ws.rs.core.Context HttpHeaders headers, String formParams) {
	   System.out.println("Vao xooong getContractList");
	   Bussiness bussiness = new Bussiness();
   	   System.out.println(formParams);
	   return bussiness.getContractDetail(formParams);
   }
   @POST
   @Path("/api/updateStatus")
   @Produces({MediaType.APPLICATION_JSON})
   public Response updateStatus(@Context HttpServletRequest requestContext, @javax.ws.rs.core.Context HttpHeaders headers, String formParams) {
	   System.out.println("Vao xooong getContractList");
	   Bussiness bussiness = new Bussiness();
   	   System.out.println(formParams);
	   return bussiness.updateStatus(formParams);
   }
   
	//Thẩm định lần 2
   @POST
   @Path("/api/updateAppraisal")
   @Produces({MediaType.APPLICATION_JSON})
   public Response updateAppraisal(@Context HttpServletRequest requestContext, @javax.ws.rs.core.Context HttpHeaders headers, String formParams) {
	   System.out.println("Vao xooong getContractList");
	   Bussiness bussiness = new Bussiness();
   	   System.out.println(formParams);
	   return bussiness.updateAppraisal(formParams);
   }
   
   //Phân bổ nhà đầu tư
   @POST
   @Path("/api/setAllotment")
   @Produces({MediaType.APPLICATION_JSON})
   public Response setAllotment(@Context HttpServletRequest requestContext, @javax.ws.rs.core.Context HttpHeaders headers, String formParams) {
	   System.out.println("Vao xooong setAllotment");
	   Bussiness bussiness = new Bussiness();
   	   System.out.println(formParams);
	   return bussiness.setAllotment(formParams);
   }
   
   
   @POST
   @Path("/api/getContractListSponsor")
   @Produces({MediaType.APPLICATION_JSON})
   public Response getContractListSponsor(@Context HttpServletRequest requestContext, @javax.ws.rs.core.Context HttpHeaders headers, String formParams) {
	   System.out.println("Vao xooong setAllotment");
	   Bussiness bussiness = new Bussiness();
   	   System.out.println(formParams);
	   return bussiness.getContractListSponsor(formParams);
   }
   
   //Giai ngan
   @POST
   @Path("/api/disbursement")
   @Produces({MediaType.APPLICATION_JSON})
   public Response disbursement(@Context HttpServletRequest requestContext, @javax.ws.rs.core.Context HttpHeaders headers, String formParams) {
	   System.out.println("Vao xooong setAllotment");
	   Bussiness bussiness = new Bussiness();
   	   System.out.println(formParams);
	   return bussiness.disbursement(formParams);
   }
   
   
//   @POST
//   @Path("/api/test")
//   @Produces({MediaType.APPLICATION_JSON})
//   public Response test(@Context HttpServletRequest requestContext, @javax.ws.rs.core.Context HttpHeaders headers, String formParams) {
//	   System.out.println("Vao xooong getProduct");
//	   Test test = new Test();
//   	   System.out.println(formParams);
//	   return test.getAAA();
//   }
   
   @GET
   @Path("/api/payment") //=> @Path2
   	 public String payment(
   		
   			 ){ 
   	 	return "OK";
   	 }
   
   static {
	   setTimeZone();
//	   ConfigDAO.shareInstance();
	}

   private static void setTimeZone() {
		String defaultTimeZone = "Asia/Bangkok";
		String timezoneId = defaultTimeZone;
		TimeZone tzone = TimeZone.getTimeZone(timezoneId);
		System.out.println("Current TZ:" + tzone);
		tzone.setDefault(tzone);
		System.out.println("Default time zone:" + tzone);
	}
}