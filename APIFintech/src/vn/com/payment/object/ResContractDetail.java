package vn.com.payment.object;

import java.util.List;

import com.google.gson.Gson;

import vn.com.payment.entities.TblImages;
import vn.com.payment.entities.TblLoanBill;
import vn.com.payment.entities.TblLoanReqDetail;
import vn.com.payment.entities.TblLoanRequestAskAns;
import vn.com.payment.entities.TblLoanRequestAskAnsGen;

public class ResContractDetail {

	public long status;
	public String message;
	public String created_by;
	public String created_date;
	public String approved_by;
	public String approved_date;
	public String final_status;
	public String loan_code;
	public String loan_name;
	public String branch;
	public String room;
	
	public String disburse_to;
	public String borrower_type;
	public String product_brand;
	public String calculate_profit_type;
	public String loan_for_month;
	public String product_serial_no;
	public String loan_expect_date;
	
//	Nhận tiền qua  - disburse_to - tbl_loan_req_detail
//	Loại sản phẩm - borrower_type  - tbl_loan_req_detail
//	Thương hiệu - product_brand - tbl_loan_req_detail
//	Cách tính lãi - calculate_profit_type - tbl_loan_request
//	Số tháng vay - loan_for_month - tbl_loan_request
//	Serial HĐ - product_serial_no - tbl_loan_req_detail
//	Ngày vay - created_date - tbl_loan_request
//	Ngày trả - disbursement_date - tbl_loan_req_detail
	
//	Lãi suất, Phí tư vấn, Phí dịch vụ, Phạt tất toán nếu có thay đổi
	public TblLoanReqDetail loan_req_detail;
	public List<TblImages> images;
	public List<TblLoanRequestAskAnsGen> question_and_answears;
	public List<TblLoanBill> loanBill;	
	public List<Object> fees;
	public String toJSON(){
		String json	=	"";
		try {
			Gson gson = new Gson();
			json	=	gson.toJson(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json.replace("\\", "");
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

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public String getCreated_date() {
		return created_date;
	}

	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}

	public String getApproved_by() {
		return approved_by;
	}

	public void setApproved_by(String approved_by) {
		this.approved_by = approved_by;
	}

	public String getApproved_date() {
		return approved_date;
	}

	public void setApproved_date(String approved_date) {
		this.approved_date = approved_date;
	}

	public String getFinal_status() {
		return final_status;
	}

	public void setFinal_status(String final_status) {
		this.final_status = final_status;
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

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public TblLoanReqDetail getLoan_req_detail() {
		return loan_req_detail;
	}

	public void setLoan_req_detail(TblLoanReqDetail loan_req_detail) {
		this.loan_req_detail = loan_req_detail;
	}

	public List<TblImages> getImages() {
		return images;
	}

	public void setImages(List<TblImages> images) {
		this.images = images;
	}

	public List<TblLoanRequestAskAnsGen> getQuestion_and_answears() {
		return question_and_answears;
	}

	public void setQuestion_and_answears(List<TblLoanRequestAskAnsGen> question_and_answears) {
		this.question_and_answears = question_and_answears;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getDisburse_to() {
		return disburse_to;
	}

	public void setDisburse_to(String disburse_to) {
		this.disburse_to = disburse_to;
	}

	public String getBorrower_type() {
		return borrower_type;
	}

	public void setBorrower_type(String borrower_type) {
		this.borrower_type = borrower_type;
	}

	public String getProduct_brand() {
		return product_brand;
	}

	public void setProduct_brand(String product_brand) {
		this.product_brand = product_brand;
	}

	public String getCalculate_profit_type() {
		return calculate_profit_type;
	}

	public void setCalculate_profit_type(String calculate_profit_type) {
		this.calculate_profit_type = calculate_profit_type;
	}

	public String getLoan_for_month() {
		return loan_for_month;
	}

	public void setLoan_for_month(String loan_for_month) {
		this.loan_for_month = loan_for_month;
	}

	public String getProduct_serial_no() {
		return product_serial_no;
	}

	public void setProduct_serial_no(String product_serial_no) {
		this.product_serial_no = product_serial_no;
	}
	
	public String getLoan_expect_date() {
		return loan_expect_date;
	}

	public void setLoan_expect_date(String loan_expect_date) {
		this.loan_expect_date = loan_expect_date;
	}

	public List<TblLoanBill> getLoanBill() {
		return loanBill;
	}

	public void setLoanBill(List<TblLoanBill> loanBill) {
		this.loanBill = loanBill;
	}

	public List<Object> getFees() {
		return fees;
	}

	public void setFees(List<Object> fees) {
		this.fees = fees;
	}
	
}
