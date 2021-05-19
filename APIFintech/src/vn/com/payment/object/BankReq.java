package vn.com.payment.object;

import java.util.List;

import com.google.gson.Gson;

import vn.com.payment.entities.SubPermission;

public class BankReq {
	public String username;
	public String token;
	public int bank_support_function;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public int getBank_support_function() {
		return bank_support_function;
	}
	public void setBank_support_function(int bank_support_function) {
		this.bank_support_function = bank_support_function;
	}
	
}
