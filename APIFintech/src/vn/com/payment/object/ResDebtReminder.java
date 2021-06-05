package vn.com.payment.object;

import java.util.List;

import com.google.gson.Gson;

public class ResDebtReminder {
	public long status;
	public String message;
	public List<ObjDebtReminderDetail> loan_request_details;
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
	public List<ObjDebtReminderDetail> getLoan_request_details() {
		return loan_request_details;
	}
	public void setLoan_request_details(List<ObjDebtReminderDetail> loan_request_details) {
		this.loan_request_details = loan_request_details;
	}
	
}
