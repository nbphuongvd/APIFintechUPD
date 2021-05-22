package vn.com.payment.object;
import java.util.List;
import com.google.gson.Gson;

public class ObjReqFee {
	public String username;
	public String token;
	public long loan_amount;
	public long calculate_profit_type;
	public long loan_for_month;
	public String borrower_name;
	public String loan_expect_date;
	List<Fees> fees;
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
	public long getLoan_amount() {
		return loan_amount;
	}
	public void setLoan_amount(long loan_amount) {
		this.loan_amount = loan_amount;
	}
	public long getCalculate_profit_type() {
		return calculate_profit_type;
	}
	public void setCalculate_profit_type(long calculate_profit_type) {
		this.calculate_profit_type = calculate_profit_type;
	}
	public long getLoan_for_month() {
		return loan_for_month;
	}
	public void setLoan_for_month(long loan_for_month) {
		this.loan_for_month = loan_for_month;
	}
	public String getBorrower_name() {
		return borrower_name;
	}
	public void setBorrower_name(String borrower_name) {
		this.borrower_name = borrower_name;
	}
	public String getLoan_expect_date() {
		return loan_expect_date;
	}
	public void setLoan_expect_date(String loan_expect_date) {
		this.loan_expect_date = loan_expect_date;
	}
	public List<Fees> getFees() {
		return fees;
	}
	public void setFees(List<Fees> fees) {
		this.fees = fees;
	}
}
