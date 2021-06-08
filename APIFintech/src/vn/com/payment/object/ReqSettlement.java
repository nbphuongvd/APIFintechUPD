package vn.com.payment.object;

import java.util.List;

import com.google.gson.Gson;

public class ReqSettlement {
	public String username;
	public String token;
	public String loan_code;
	public String real_payment_date;
	public long	  pay_amount;
	public String memo;
	public String action;
	public List<ObjImage> images;
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
	public String getLoan_code() {
		return loan_code;
	}
	public void setLoan_code(String loan_code) {
		this.loan_code = loan_code;
	}
	public String getReal_payment_date() {
		return real_payment_date;
	}
	public void setReal_payment_date(String real_payment_date) {
		this.real_payment_date = real_payment_date;
	}
	public long getPay_amount() {
		return pay_amount;
	}
	public void setPay_amount(long pay_amount) {
		this.pay_amount = pay_amount;
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
	public List<ObjImage> getImages() {
		return images;
	}
	public void setImages(List<ObjImage> images) {
		this.images = images;
	}
	
}

