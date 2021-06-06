package vn.com.payment.object;

import com.google.gson.Gson;

public class ReqUDExtendStatus {
	public String username;
	public String token;
	public String extend_status; 			// trạng thái các hồ sơ cập nhật
	public String loan_code;				// ma hop dong
	public String memo;						// Mô tả thao tác
	public String action;					// Hành động
	public String bill_index;
	public String last_day_accept_pay;		// Ngày hẹn trả yyyyMMdd

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

	public String getExtend_status() {
		return extend_status;
	}

	public void setExtend_status(String extend_status) {
		this.extend_status = extend_status;
	}

	public String getLoan_code() {
		return loan_code;
	}

	public void setLoan_code(String loan_code) {
		this.loan_code = loan_code;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getBill_index() {
		return bill_index;
	}

	public void setBill_index(String bill_index) {
		this.bill_index = bill_index;
	}

	public String getLast_day_accept_pay() {
		return last_day_accept_pay;
	}

	public void setLast_day_accept_pay(String last_day_accept_pay) {
		this.last_day_accept_pay = last_day_accept_pay;
	}
	
}
