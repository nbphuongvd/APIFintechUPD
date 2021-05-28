package vn.com.payment.object;

import java.util.List;

import com.google.gson.Gson;

public class ResAllContractList {
	public long status;
	public String message;
	public List<ResContractList> contract_list;
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
	public List<ResContractList> getContract_list() {
		return contract_list;
	}
	public void setContract_list(List<ResContractList> contract_list) {
		this.contract_list = contract_list;
	}
	
}
