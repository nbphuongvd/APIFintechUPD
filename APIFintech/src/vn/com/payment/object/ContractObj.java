package vn.com.payment.object;

import com.google.gson.Gson;

public class ContractObj {
	public String username;
	public String token;
	public String product_type;
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
}
