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
	   Bussiness bussiness = new Bussiness();
   	   System.out.println(formParams);
	   return bussiness.login(formParams);
   }
   
   @POST
   @Path("/api/changePass")
   @Produces({MediaType.APPLICATION_JSON})
   public Response changePass(@Context HttpServletRequest requestContext, @javax.ws.rs.core.Context HttpHeaders headers, String formParams) {
	   System.out.println("Vao xooong login");
	   Bussiness bussiness = new Bussiness();
   	   System.out.println(formParams);
	   return bussiness.changePass(formParams);
   }
   
   @POST
   @Path("/api/resetPass")
   @Produces({MediaType.APPLICATION_JSON})
   public Response resetPass(@Context HttpServletRequest requestContext, @javax.ws.rs.core.Context HttpHeaders headers, String formParams) {
	   System.out.println("Vao xooong login");
	   Bussiness bussiness = new Bussiness();
   	   System.out.println(formParams);
	   return bussiness.resetPass(formParams);
   }
   
   @GET
   @Path("/api/payment") //=> @Path2
   	 public String payment(
   		
   			 ){ 
   	 	return "OK";
   	 }
   
   static {
	   setTimeZone();
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