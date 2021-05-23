package vn.com.payment.object;

import java.util.List;

import com.google.gson.Gson;

import vn.com.payment.entities.TblRateConfig;

public class ReqCreaterLoan {
	public String username;
	public String token;
	public String loan_code;
	public String loan_name;
	public long product_type;
	public String product_brand;
	public String product_modal;
	public long total_run; // So km da di
	public long product_condition; // Tinh trang san pham
	public long product_own_by_borrower; // Chinh chu hay khong
	public String product_serial_no;
	public String product_color;
	public long borrower_type;
	public String borrower_phone;
	public String borrower_email;
	public String borrower_id_number;
	public long disburse_to;
	public String disburse_to_bank_no;
	public String disburse_to_bank_name;
	public String disburse_to_bank_code;
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
	public String getLoan_name() {
		return loan_name;
	}
	public void setLoan_name(String loan_name) {
		this.loan_name = loan_name;
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
	public String getProduct_serial_no() {
		return product_serial_no;
	}
	public void setProduct_serial_no(String product_serial_no) {
		this.product_serial_no = product_serial_no;
	}
	public String getProduct_color() {
		return product_color;
	}
	public void setProduct_color(String product_color) {
		this.product_color = product_color;
	}
	public long getBorrower_type() {
		return borrower_type;
	}
	public void setBorrower_type(long borrower_type) {
		this.borrower_type = borrower_type;
	}
	public String getBorrower_phone() {
		return borrower_phone;
	}
	public void setBorrower_phone(String borrower_phone) {
		this.borrower_phone = borrower_phone;
	}
	public String getBorrower_email() {
		return borrower_email;
	}
	public void setBorrower_email(String borrower_email) {
		this.borrower_email = borrower_email;
	}
	public String getBorrower_id_number() {
		return borrower_id_number;
	}
	public void setBorrower_id_number(String borrower_id_number) {
		this.borrower_id_number = borrower_id_number;
	}
	public long getDisburse_to() {
		return disburse_to;
	}
	public void setDisburse_to(long disburse_to) {
		this.disburse_to = disburse_to;
	}
	public String getDisburse_to_bank_no() {
		return disburse_to_bank_no;
	}
	public void setDisburse_to_bank_no(String disburse_to_bank_no) {
		this.disburse_to_bank_no = disburse_to_bank_no;
	}
	public String getDisburse_to_bank_name() {
		return disburse_to_bank_name;
	}
	public void setDisburse_to_bank_name(String disburse_to_bank_name) {
		this.disburse_to_bank_name = disburse_to_bank_name;
	}
	public String getDisburse_to_bank_code() {
		return disburse_to_bank_code;
	}
	public void setDisburse_to_bank_code(String disburse_to_bank_code) {
		this.disburse_to_bank_code = disburse_to_bank_code;
	}
	public List<ObjImage> getImages() {
		return images;
	}
	public void setImages(List<ObjImage> images) {
		this.images = images;
	}
	
}
