package vn.com.payment.object;

import com.google.gson.Gson;

public class ProducResAll {
	public long status;
	public ProductRes suggest_info;
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

	public ProductRes getSuggest_info() {
		return suggest_info;
	}
	public void setSuggest_info(ProductRes suggest_info) {
		this.suggest_info = suggest_info;
	}
}
