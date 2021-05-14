package vn.com.payment.object;

import com.google.gson.Gson;

public class ResLogin {
	public String status;
	public String token;
	public String require_change_pass;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getRequire_change_pass() {
		return require_change_pass;
	}
	public void setRequire_change_pass(String require_change_pass) {
		this.require_change_pass = require_change_pass;
	}
	
}
