package vn.com.payment.object;

import java.util.List;

import com.google.gson.Gson;

import vn.com.payment.entities.TblImages;
import vn.com.payment.entities.TblLoanReqDetail;
import vn.com.payment.entities.TblLoanRequestAskAns;

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
	public TblLoanReqDetail loan_req_detail;
	public List<TblImages> images;
//	public List<F> fees;
	public List<TblLoanRequestAskAns> question_and_answears;
	
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

	public List<TblLoanRequestAskAns> getQuestion_and_answears() {
		return question_and_answears;
	}

	public void setQuestion_and_answears(List<TblLoanRequestAskAns> question_and_answears) {
		this.question_and_answears = question_and_answears;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}
	
}
