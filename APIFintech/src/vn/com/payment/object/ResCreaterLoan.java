package vn.com.payment.object;

import com.google.gson.Gson;

public class ResCreaterLoan {
	public long status;
	public String message;
	public String request_code;
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
	public long getStatus() {
		return status;
	}
	public void setStatus(long status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getRequest_code() {
		return request_code;
	}
	public void setRequest_code(String request_code) {
		this.request_code = request_code;
	}
	
}
