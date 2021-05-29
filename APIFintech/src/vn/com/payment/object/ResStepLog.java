package vn.com.payment.object;

import java.util.List;

import com.google.gson.Gson;

import vn.com.payment.entities.TblLoanExpertiseSteps;

public class ResStepLog {
	public long status;
	public String message;
	public String loan_id;
	public List<TblLoanExpertiseSteps> loan_logs;
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
	public List<TblLoanExpertiseSteps> getLoan_logs() {
		return loan_logs;
	}
	public void setLoan_logs(List<TblLoanExpertiseSteps> loan_logs) {
		this.loan_logs = loan_logs;
	}
	public String getLoan_id() {
		return loan_id;
	}
	public void setLoan_id(String loan_id) {
		this.loan_id = loan_id;
	}
	
}
