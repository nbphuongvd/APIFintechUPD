package vn.com.payment.object;

import com.google.gson.Gson;

public class ProductReq {

	public String product_type;
	public String product_brand;
	public String product_modal;
	public String total_run;
	public String product_condition;
	public String product_own_by_borrower;
	public String product_serial_no;
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
	public String getProduct_type() {
		return product_type;
	}
	public void setProduct_type(String product_type) {
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
	public String getTotal_run() {
		return total_run;
	}
	public void setTotal_run(String total_run) {
		this.total_run = total_run;
	}
	public String getProduct_condition() {
		return product_condition;
	}
	public void setProduct_condition(String product_condition) {
		this.product_condition = product_condition;
	}
	public String getProduct_own_by_borrower() {
		return product_own_by_borrower;
	}
	public void setProduct_own_by_borrower(String product_own_by_borrower) {
		this.product_own_by_borrower = product_own_by_borrower;
	}
	public String getProduct_serial_no() {
		return product_serial_no;
	}
	public void setProduct_serial_no(String product_serial_no) {
		this.product_serial_no = product_serial_no;
	}
	
}
