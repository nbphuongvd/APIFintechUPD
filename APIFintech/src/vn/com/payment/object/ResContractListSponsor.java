package vn.com.payment.object;

import com.google.gson.Gson;

public class ResContractListSponsor {
	public long loan_id;
	public String loan_code;
	public String loan_name;
	public String product_name;
	public long approved_amount; 
	public long final_status; 
	public String disbursement_date; 
	public String borrower_fullname; 
	public long loan_for_month;
	public long calculateProfitType;
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

	public long getLoan_id() {
		return loan_id;
	}
	public void setLoan_id(long loan_id) {
		this.loan_id = loan_id;
	}
	public String getLoan_code() {
		return loan_code;
	}
	public void setLoan_code(String loan_code) {
		this.loan_code = loan_code;
	}
	public String getLoan_name() {
		return loan_name;
	}
	public void setLoan_name(String loan_name) {
		this.loan_name = loan_name;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public long getApproved_amount() {
		return approved_amount;
	}
	public void setApproved_amount(long approved_amount) {
		this.approved_amount = approved_amount;
	}
	public long getFinal_status() {
		return final_status;
	}
	public void setFinal_status(long final_status) {
		this.final_status = final_status;
	}
	public String getDisbursement_date() {
		return disbursement_date;
	}
	public void setDisbursement_date(String disbursement_date) {
		this.disbursement_date = disbursement_date;
	}
	public String getBorrower_fullname() {
		return borrower_fullname;
	}
	public void setBorrower_fullname(String borrower_fullname) {
		this.borrower_fullname = borrower_fullname;
	}
	public long getLoan_for_month() {
		return loan_for_month;
	}
	public void setLoan_for_month(long loan_for_month) {
		this.loan_for_month = loan_for_month;
	}

	public long getCalculateProfitType() {
		return calculateProfitType;
	}

	public void setCalculateProfitType(long calculateProfitType) {
		this.calculateProfitType = calculateProfitType;
	}
	
}
