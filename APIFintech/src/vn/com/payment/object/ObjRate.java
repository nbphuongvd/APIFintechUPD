package vn.com.payment.object;

import com.google.gson.Gson;

public class ObjRate {
	public long rate_id;
	public String rate_name;
	public String rate_type;
	public String rate_value;
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
	public long getRate_id() {
		return rate_id;
	}
	public void setRate_id(long rate_id) {
		this.rate_id = rate_id;
	}
	public String getRate_name() {
		return rate_name;
	}
	public void setRate_name(String rate_name) {
		this.rate_name = rate_name;
	}
	public String getRate_type() {
		return rate_type;
	}
	public void setRate_type(String rate_type) {
		this.rate_type = rate_type;
	}
	public String getRate_value() {
		return rate_value;
	}
	public void setRate_value(String rate_value) {
		this.rate_value = rate_value;
	}
	
}
