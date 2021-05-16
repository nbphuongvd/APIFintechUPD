package vn.com.payment.object;

import com.google.gson.Gson;

public class ProductRes {
	public long product_type;
	public String product_brand;
	public String product_modal;
	public long total_run;
	public long product_condition;
	public long product_own_by_borrower;
	public long buy_a_new_price;
	public long loan_price;
	public long accept_loan_price;
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
	public long getProduct_type() {
		return product_type;
	}
	public void setProduct_type(long product_type) {
		this.product_type = product_type;
	}
	public String getProduct_brand() {
		return product_brand;
	}
	public void setProduct_brand(String product_brand) {
		this.product_brand = product_brand;
	}
	public String getProduct_modal() {
		return product_modal;
	}
	public void setProduct_modal(String product_modal) {
		this.product_modal = product_modal;
	}
	public long getTotal_run() {
		return total_run;
	}
	public void setTotal_run(long total_run) {
		this.total_run = total_run;
	}
	
	public long getProduct_condition() {
		return product_condition;
	}
	public void setProduct_condition(long product_condition) {
		this.product_condition = product_condition;
	}
	public long getProduct_own_by_borrower() {
		return product_own_by_borrower;
	}
	public void setProduct_own_by_borrower(long product_own_by_borrower) {
		this.product_own_by_borrower = product_own_by_borrower;
	}
	public long getBuy_a_new_price() {
		return buy_a_new_price;
	}
	public void setBuy_a_new_price(long buy_a_new_price) {
		this.buy_a_new_price = buy_a_new_price;
	}
	public long getLoan_price() {
		return loan_price;
	}
	public void setLoan_price(long loan_price) {
		this.loan_price = loan_price;
	}
	public long getAccept_loan_price() {
		return accept_loan_price;
	}
	public void setAccept_loan_price(long accept_loan_price) {
		this.accept_loan_price = accept_loan_price;
	}
	
}
