package vn.com.payment.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.simple.JSONArray;

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

//		ArrayList<String> cars = new ArrayList<String>();

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
		
		 JSONArray array = new JSONArray();
		
		for (int i : myNumbers) {
			Permmission permmission = permmissionHome.getPermmission(i);
//			List<SubPermission> getSubPermission = subPermissionHome.getSubPermissionid(i);
			objTest1.setName(permmission.getName());
//			objTest1.setPermission(getSubPermission);
//			cars.add(objTest1.toJSON());
			
			array.add(objTest1);
		}
//		obOjjectaaa.setName(cars.toString());
		
		
		
		
		
		
		
		
		 JSONObject jsonObject = new JSONObject();
	      //Inserting key-value pairs into the json object
	      jsonObject.put("ID", "1");
	      jsonObject.put("First_Name", "Krishna Kasyap");
	      jsonObject.put("Last_Name", "Bhagavatula");
	      jsonObject.put("Date_Of_Birth", "1989-09-26");
	      jsonObject.put("Place_Of_Birth", "Vishakhapatnam");
	      jsonObject.put("Country", "25000");
	      //Creating a json array
	     
//	      array.add("e-mail: krishna_kasyap@gmail.com");
//	      array.add("phone: 9848022338");
//	      array.add("city: Hyderabad");
//	      array.add("Area: Madapur");
//	      array.add("State: Telangana");
	      //Adding array to the json object
	      jsonObject.put("contact",array);
	    
	      System.out.println("JSON file created: "+jsonObject);
		
		
		
		
		
		
		
		
		
		
		
		
		response = response.header(Commons.ReceiveTime, getTimeNow());
		
		return response.header(Commons.ResponseTime, getTimeNow()).entity(jsonObject.toString()).build();
		
//		return response.header(Commons.ResponseTime, getTimeNow()).entity(obOjjectaaa.toJSON().replace("\\", "")).build();
	}

//	//Tinh minh hoa khoan vay
//	public ArrayList<Document> illustration (String userName, String billID, double sotienvay, double sothangvay, double loaitrano, List<Fees> listFee){
//		ArrayList<Document> array = new ArrayList<Document>();
//		
//		try {
//			FileLogger.log("getIllustration: " + userName+ " illustrationIns:" + loaitrano, LogType.BUSSINESS);
//			double sotienconlai_a = sotienvay;		
//			double laixuatNam 					= 0;
//			double phidichvu					= 0;
//			double phituvan 					= 0;
//			double phitratruochan 				= 0;
//			double phitattoantruochan 			= 0;
//			
//			double tinhphitranotruochan_a	  	= 0;
//			double tienlaithang_a 				= 0;
//			double tinhphidichvu_a	  			= 0;
//			double tinhphituvan_a	  			= 0;
//			double tientrahangthang_a			= 0;
//			double tinhphitattoan_a	  			= 0;
//			
//			double tinhphitranotruochan	  		= 0;
//			double tienlaithang 				= 0;
//			double tinhphidichvu	  			= 0;
//			double tinhphituvan	  				= 0;
//			double tientrahangthang				= 0;
//			double tinhphitattoan	  			= 0;
//			
//			if(loaitrano == 1){
//				//Lịch trả nợ theo dư nợ giảm dần						
//				
//				for (int i = 0; i<= sothangvay ; i++) {
//					
//					if(i == 0){
//						 Document doc = new Document("idMinhhoa", billID)
//								 .append("kyTrano",getTimeOut(i))
//							     .append("gocConlai", sotienvay)
//							     .append("gocTramoiky", 0)
//							     .append("laiThang", 0)
//								 .append("traHangthang", 0)
//								 .append("phiTuvan", 0)
//								 .append("phiDichvu", 0)
//								 .append("phiTranotruochan", 0)
//								 .append("tattoanTruochan", 0);
//								 array.add(doc);
//					}else{						
//						
//						double tiengoctramoiky_a 			= sotienvay/sothangvay;;
//						
//						for (Fees fees : listFee) {
//							switch (String.valueOf(fees.getFee_type())) {
//							case "1":			
//								if(fees.getFix_fee_amount() <= 0){
//									laixuatNam 				= (double) fees.getFix_fee_percent();
//									tienlaithang_a 			= sotienconlai_a * laixuatNam * 30 / 365;
//								}else{
//									laixuatNam 				= (double) fees.getFix_fee_amount();
//									tienlaithang_a 			= laixuatNam;
//								}						
//								break;			
//							case "2":			
//								if(fees.getFix_fee_amount() <= 0){
//									phituvan 				= (double) fees.getFix_fee_percent();
//									tinhphituvan_a	  		= sotienconlai_a * phituvan * 30 / 365;
//								}else{
//									phituvan 				= (double) fees.getFix_fee_amount();
//									tinhphituvan_a	  		= phituvan;
//								}	
//								break;	
//							case "3":			
//								if(fees.getFix_fee_amount() <= 0){
//									phidichvu 				= (double) fees.getFix_fee_percent();
//									tinhphidichvu_a	  		= sotienconlai_a * phidichvu * 30 / 365;
//								}else{
//									phidichvu 				= (double) fees.getFix_fee_amount();
//									tinhphidichvu_a	  		= phidichvu;
//								}	
//								break;	
//							case "4":			
//								if(fees.getFix_fee_amount() <= 0){
//									phitratruochan 			= (double) fees.getFix_fee_percent();
//									tinhphitranotruochan_a	= sotienconlai_a * phitratruochan;
//								}else{
//									phitratruochan 			= (double) fees.getFix_fee_amount();
//									tinhphitranotruochan_a	= phitratruochan;
//								}	
//								break;
//							case "5":									
//								if(fees.getFix_fee_amount() <= 0){
//									phitattoantruochan = (double) fees.getFix_fee_percent();
//									tinhphitattoan_a	  	= sotienconlai_a + tienlaithang_a + tinhphidichvu_a + tinhphituvan_a + tinhphitranotruochan_a;
//								}else{
//									phitattoantruochan 	= (double) fees.getFix_fee_amount();
//									tinhphitattoan_a	  	= phitattoantruochan;
//								}	
//								break;
//							default:
//								break;
//							}
//						}
//
////						ObjMinhhoa objMinhhoa = new ObjMinhhoa();
////						tiengoctramoiky_a 					= sotienvay/sothangvay;
////						double tinhphitranotruochan_a	  	= sotienconlai_a * phitratruochan;						
////						double tienlaithang_a 				= sotienconlai_a * laixuatNam * 30 / 365;
////						double tinhphidichvu_a	  			= sotienconlai_a * phidichvu * 30 / 365;
////						double tinhphituvan_a	  			= sotienconlai_a * phituvan * 30 / 365;					
//						tientrahangthang_a					= tiengoctramoiky_a + tienlaithang_a + tinhphituvan_a + tinhphidichvu_a;
////						double tinhphitattoan_a	  			= sotienconlai_a + tienlaithang_a + tinhphidichvu_a + tinhphituvan_a + tinhphitranotruochan_a;
//						sotienconlai_a	  					= sotienconlai_a - tiengoctramoiky_a;
////						objMinhhoa.setKyTrano(getTimeOut(i));
////						objMinhhoa.setGocConlai(sotienconlai_a);
////						objMinhhoa.setGocTramoiky(tiengoctramoiky_a);
////						objMinhhoa.setLaiThang(tienlaithang_a);
////						objMinhhoa.setTraHangthang(tientrahangthang_a);
////						objMinhhoa.setPhiTuvan(tinhphituvan_a);
////						objMinhhoa.setPhiDichvu(tinhphidichvu_a);
////						objMinhhoa.setPhiTranotruochan(tinhphitranotruochan_a);
////						objMinhhoa.setTattoanTruochan(tinhphitattoan_a);
//	//					array.add(objMinhhoa);
//						
//						
//						 Document doc = new Document("idMinhhoa", billID)
//								 .append("kyTrano",getTimeOut(i))
//							     .append("gocConlai",  Math.floor(sotienconlai_a * 100) / 100)
//							     .append("gocTramoiky",  Math.floor(tiengoctramoiky_a * 100) / 100)
//							     .append("laiThang",  Math.floor(tienlaithang_a * 100) / 100)
//								 .append("traHangthang",  Math.floor(tientrahangthang_a * 100) / 100)
//								 .append("phiTuvan",  Math.floor(tinhphituvan_a * 100) / 100)
//								 .append("phiDichvu",  Math.floor(tinhphidichvu_a * 100) / 100)
//								 .append("phiTranotruochan",  Math.floor(tinhphitranotruochan_a * 100) / 100)
//								 .append("tattoanTruochan",  Math.floor(tinhphitattoan_a * 100) / 100);
//						 array.add(doc);
//					}
//				}			
//			}else{
//				//Lịch trả nợ gốc cuối kỳ	
//				for (int i = 0; i<= sothangvay ; i++) {
//					double sotienconlai = sotienvay;
//					if(i == 0){
//						 Document doc = new Document("idMinhhoa", billID)
//								 .append("kyTrano",getTimeOut(i))
//							     .append("gocConlai", sotienconlai)
//							     .append("gocTrakycuoi", 0)
//							     .append("laiThang", 0)
//								 .append("traHangthang", 0)
//								 .append("phiTuvan", 0)
//								 .append("phiDichvu", 0)
//								 .append("phiTranotruochan", 0)
//								 .append("tattoanTruochan", 0);
//								 array.add(doc);
//					}else{
//						
//						ObjMinhhoa objMinhhoa = new ObjMinhhoa();
//						for (Fees fees : listFee) {
//							switch (String.valueOf(fees.getFee_type())) {
//							case "1":			
//								if(fees.getFix_fee_amount() <= 0){
//									laixuatNam 				= (double) fees.getFix_fee_percent();
//									tienlaithang 			= sotienconlai * laixuatNam * 30 / 365;
//								}else{
//									laixuatNam 				= (double) fees.getFix_fee_amount();
//									tienlaithang 				= laixuatNam;
//								}						
//								break;			
//							case "2":			
//								if(fees.getFix_fee_amount() <= 0){
//									phituvan 				= (double) fees.getFix_fee_percent();
//									tinhphituvan	  		= sotienconlai * phituvan * 30 / 365;
//								}else{
//									phituvan 				= (double) fees.getFix_fee_amount();
//									tinhphituvan	  		= phituvan;
//								}	
//								break;	
//							case "3":			
//								if(fees.getFix_fee_amount() <= 0){
//									phidichvu 				= (double) fees.getFix_fee_percent();
//									tinhphidichvu	  		= sotienconlai * phidichvu * 30 / 365;
//								}else{
//									phidichvu 				= (double) fees.getFix_fee_amount();
//									tinhphidichvu	  		= phidichvu;
//								}	
//								break;	
//							case "4":			
//								if(fees.getFix_fee_amount() <= 0){
//									phitratruochan 			= (double) fees.getFix_fee_percent();
//									tinhphitranotruochan	= sotienconlai * phitratruochan;
//								}else{
//									phitratruochan 			= (double) fees.getFix_fee_amount();
//									tinhphitranotruochan	= phitratruochan;
//								}	
//								break;
//							case "5":									
//								if(fees.getFix_fee_amount() <= 0){
//									phitattoantruochan = (double) fees.getFix_fee_percent();
//									tinhphitattoan	  		= sotienconlai + tienlaithang + tinhphidichvu + tinhphituvan + tinhphitranotruochan;
//								}else{
//									phitattoantruochan 		= (double) fees.getFix_fee_amount();
//									tinhphitranotruochan_a	= phitattoantruochan;
//								}	
//								break;
//							default:
//								break;
//							}
//						}
//						tientrahangthang					= tienlaithang + tinhphituvan + tinhphidichvu;
//						double tiengoctrakycuoi 			= sotienconlai;
////						objMinhhoa.setKyTrano(getTimeOut(1));
////						objMinhhoa.setGocConlai(sotienconlai);
////						objMinhhoa.setGocTrakycuoi(tiengoctrakycuoi);
////						objMinhhoa.setLaiThang(tienlaithang);
////						objMinhhoa.setTraHangthang(tientrahangthang);
////						objMinhhoa.setPhiTuvan(tinhphituvan);
////						objMinhhoa.setPhiDichvu(tinhphidichvu);
////						objMinhhoa.setPhiTranotruochan(tinhphitranotruochan);
////						objMinhhoa.setTattoanTruochan(tinhphitattoan);
//		//				array.add(objMinhhoa);
//						
//						 Document doc = new Document("idMinhhoa", billID)
//								 .append("kyTrano",getTimeOut(i))
//							     .append("gocConlai", 		Math.floor(sotienconlai * 100) / 100)
//							     .append("gocTrakycuoi",  	Math.floor(tiengoctrakycuoi * 100) / 100)
//							     .append("laiThang",  		Math.floor(tienlaithang * 100) / 100)
//								 .append("traHangthang",  	Math.floor(tientrahangthang * 100) / 100)
//								 .append("phiTuvan",  		Math.floor(tinhphituvan * 100) / 100)
//								 .append("phiDichvu",  		Math.floor(tinhphidichvu * 100) / 100)
//								 .append("phiTranotruochan", Math.floor(tinhphitranotruochan * 100) / 100)
//								 .append("tattoanTruochan",  Math.floor(tinhphitattoan * 100) / 100);
//								 array.add(doc);
//					}
//				}
//			}
//
//			//Thì tiền tra gốc hàng tháng = số tiền vay / số tháng vay
//			//Tiền lãi hàng tháng = số tiền gốc còn lại * lãi năm * số ngày trong tháng / số ngày trong năm (mặc định là 365)
//			//Phí dịch vụ sẽ tính bằng = số tiền còn lại * phi dich vu / ngày trong năm * ngày trong tháng
//			//phí tất toán trước hạn chỉ bằng = tiền gốc còn lại + lãi trong tháng + phí dịch vụ trong tháng + phi tư vấn trong tháng + phí trả nợ trước hạn (tính theo tháng)
//			FileLogger.log("illustration: " + userName+ " illustrationIns array insert DB:" + array, LogType.BUSSINESS);
//
//			return array;
//		} catch (Exception e) {
//			FileLogger.log("illustration: " + userName+ " illustrationIns exception" + e, LogType.ERROR);
//			e.printStackTrace();
//		}
//		return null;
//	}
	public static String getTimeNow() {
		SimpleDateFormat format = new SimpleDateFormat(MainCfg.FORMATTER_DATETIME);
		return format.format(new Date());
	}
}
