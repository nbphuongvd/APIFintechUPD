package vn.com.payment.object;

import java.util.List;

import com.google.gson.Gson;

public class ReqDisbursement {
	public String username;
	public String token;
	public String loan_code;
	public String action;
	public List<ObjImage> images; 
	public long expertise_status;
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
	public List<ObjImage> getImages() {
		return images;
	}
	public void setImages(List<ObjImage> images) {
		this.images = images;
	}
	public long getExpertise_status() {
		return expertise_status;
	}
	public void setExpertise_status(long expertise_status) {
		this.expertise_status = expertise_status;
	}
	
}
