package vn.com.payment.object;

import java.util.List;

import com.google.gson.Gson;

public class ReqContractListSponsor {
	public String username;
	public String token;
	public String from_date;
	public String to_date;
	public String calculate_profit_type; 	// [1: du no. giam dan;2:tat toan cuoi ky]
	public List<String> final_status;		// trạng thái các hồ sơ muốn tìm kiếm(=-1 nếu muốn search all status)
	public String loan_code;				// ma hop dong
	public String sponsor_id;				// tên khách hàng
	public String limit;
	public String offSet;

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

	public String getFrom_date() {
		return from_date;
	}

	public void setFrom_date(String from_date) {
		this.from_date = from_date;
	}

	public String getTo_date() {
		return to_date;
	}

	public void setTo_date(String to_date) {
		this.to_date = to_date;
	}

	public String getCalculate_profit_type() {
		return calculate_profit_type;
	}

	public void setCalculate_profit_type(String calculate_profit_type) {
		this.calculate_profit_type = calculate_profit_type;
	}

	public List<String> getFinal_status() {
		return final_status;
	}

	public void setFinal_status(List<String> final_status) {
		this.final_status = final_status;
	}

	public String getLoan_code() {
		return loan_code;
	}

	public void setLoan_code(String loan_code) {
		this.loan_code = loan_code;
	}

	public String getSponsor_id() {
		return sponsor_id;
	}

	public void setSponsor_id(String sponsor_id) {
		this.sponsor_id = sponsor_id;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getOffSet() {
		return offSet;
	}

	public void setOffSet(String offSet) {
		this.offSet = offSet;
	}
	
}
