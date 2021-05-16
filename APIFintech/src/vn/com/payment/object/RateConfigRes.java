package vn.com.payment.object;

import java.util.List;

import com.google.gson.Gson;
import com.sun.tools.javac.code.Attribute.Array;

import vn.com.payment.entities.TblRateConfig;

public class RateConfigRes {
	public long status;
	public String message;
	public List<TblRateConfig> rate_config;
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
	public List<TblRateConfig> getRate_config() {
		return rate_config;
	}
	public void setRate_config(List<TblRateConfig> rate_config) {
		this.rate_config = rate_config;
	}
	
}
