package vn.com.payment.object;

import com.google.gson.Gson;

public class ObjBillRes {
	public long status;
	public String message;
	public String collection;
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

	public String getBilling_tmp_code() {
		return billing_tmp_code;
	}

	public void setBilling_tmp_code(String billing_tmp_code) {
		this.billing_tmp_code = billing_tmp_code;
	}

	public String getCollection() {
		return collection;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}
	
	
}
