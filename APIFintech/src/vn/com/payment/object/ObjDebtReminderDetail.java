package vn.com.payment.object;

import java.util.Date;

import com.google.gson.Gson;

public class ObjDebtReminderDetail {

	public int loanId;
	public String loan_code;
	public String created_by;
	public int final_status;
	public String product_desc;
	public String createdDate;
	public String borrowerFullname;
	public String borrower_id;
	public String borrowerPhone;
	public long loan_amount;
	public int room_name;
	public int branch;
	public long payment_amount; // So tien thanh toan
	public String payment_date; // Ngay thanh toan
	public int bill_index; // ky thanh toan
	
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

	public int getLoanId() {
		return loanId;
	}

	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}

	public String getLoan_code() {
		return loan_code;
	}

	public void setLoan_code(String loan_code) {
		this.loan_code = loan_code;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public int getFinal_status() {
		return final_status;
	}

	public void setFinal_status(int final_status) {
		this.final_status = final_status;
	}

	public String getProduct_desc() {
		return product_desc;
	}

	public void setProduct_desc(String product_desc) {
		this.product_desc = product_desc;
	}
	

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getBorrowerFullname() {
		return borrowerFullname;
	}

	public void setBorrowerFullname(String borrowerFullname) {
		this.borrowerFullname = borrowerFullname;
	}

	public String getBorrower_id() {
		return borrower_id;
	}

	public void setBorrower_id(String borrower_id) {
		this.borrower_id = borrower_id;
	}

	public String getBorrowerPhone() {
		return borrowerPhone;
	}

	public void setBorrowerPhone(String borrowerPhone) {
		this.borrowerPhone = borrowerPhone;
	}

	public long getLoan_amount() {
		return loan_amount;
	}

	public void setLoan_amount(long loan_amount) {
		this.loan_amount = loan_amount;
	}

	public int getRoom_name() {
		return room_name;
	}

	public void setRoom_name(int room_name) {
		this.room_name = room_name;
	}

	public int getBranch() {
		return branch;
	}

	public void setBranch(int branch) {
		this.branch = branch;
	}

	public long getPayment_amount() {
		return payment_amount;
	}

	public void setPayment_amount(long payment_amount) {
		this.payment_amount = payment_amount;
	}

	public String getPayment_date() {
		return payment_date;
	}

	public void setPayment_date(String payment_date) {
		this.payment_date = payment_date;
	}

	public int getBill_index() {
		return bill_index;
	}

	public void setBill_index(int bill_index) {
		this.bill_index = bill_index;
	}

	
}
