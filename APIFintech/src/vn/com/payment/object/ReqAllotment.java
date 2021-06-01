package vn.com.payment.object;

import java.util.List;

import com.google.gson.Gson;

public class ReqAllotment {
	public String username;
	public String token;
	public String loan_code;
	public String action;
	public String expertise_comment;
	public List<String> sponsor;
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
	public String getLoan_code() {
		return loan_code;
	}
	public void setLoan_code(String loan_code) {
		this.loan_code = loan_code;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getExpertise_comment() {
		return expertise_comment;
	}
	public void setExpertise_comment(String expertise_comment) {
		this.expertise_comment = expertise_comment;
	}
	public List<String> getSponsor() {
		return sponsor;
	}
	public void setSponsor(List<String> sponsor) {
		this.sponsor = sponsor;
	}
	
}
