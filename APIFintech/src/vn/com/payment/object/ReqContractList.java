package vn.com.payment.object;

import com.google.gson.Gson;

public class ReqContractList {
	
	public String username;
	public String token;
	public String from_date;
	public String to_date;
	public int branch_id; 				// id chi nhánh
	public int calculate_profit_type; 	// [1: du no. giam dan;2:tat toan cuoi ky]
	public int final_status; 			// trạng thái các hồ sơ muốn tìm kiếm(=-1 nếu muốn search all status)
	public String loan_code;			// ma hop dong
	public String id_number;			// cmt/ho chieu
	public String borrower_name;		// tên khách hàng

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

	

	public int getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(int branch_id) {
		this.branch_id = branch_id;
	}

	public int getCalculate_profit_type() {
		return calculate_profit_type;
	}

	public void setCalculate_profit_type(int calculate_profit_type) {
		this.calculate_profit_type = calculate_profit_type;
	}

	public int getFinal_status() {
		return final_status;
	}

	public void setFinal_status(int final_status) {
		this.final_status = final_status;
	}

	public String getLoan_code() {
		return loan_code;
	}

	public void setLoan_code(String loan_code) {
		this.loan_code = loan_code;
	}

	public String getId_number() {
		return id_number;
	}

	public void setId_number(String id_number) {
		this.id_number = id_number;
	}

	public String getBorrower_name() {
		return borrower_name;
	}

	public void setBorrower_name(String borrower_name) {
		this.borrower_name = borrower_name;
	}
	
}
