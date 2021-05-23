package vn.com.payment.object;

import com.google.gson.Gson;

public class ContractObj {
	public String username;
	public String token;
	public long branch_id;
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
	public long getBranch_id() {
		return branch_id;
	}
	public void setBranch_id(long branch_id) {
		this.branch_id = branch_id;
	}
	
}
