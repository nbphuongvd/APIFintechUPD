package vn.com.payment.object;

import com.google.gson.Gson;

public class ObjBillReq {
	public long status;
	public String message;
	public String billing_tmp_code;

	public String toJSON(){
		String json	=	"";
		try {
			Gson gson = new Gson();
			json	=	gson.toJson(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	
	public static void main(String[] args) {
		
	}
}
