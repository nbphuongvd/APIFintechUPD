package vn.com.payment.object;

import java.util.List;

import com.google.gson.Gson;

import vn.com.payment.entities.TblRateConfig;

public class ReqCreaterLoan {
	public String username;
	public String token;
	public String loan_code;
	public String loan_name;
	public String action;
	public String change_fee; //1 : có thay đổi  0 : không thay đổi
	public long loan_amount;
	public long calculate_profit_type;
	public long loan_for_month;
	public String borrower_name;
	public String loan_expect_date;
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
	public long product_valuation; // Giá trị sản phẩm
	public long borrower_income;   // Thu nhập người vay
	public String borrower_fullname; // Tên người vay
	public String borrower_address; // Địa chỉ người vay
	public String id_issue_at;		// Nơi cấp cmt]
	public long id_issue_date; //Ngày cap formmat: yyyyMMdd 
	public String product_desc;
	public long borrower_birthday; //Ngày sinh borrower format: yyyyMMdd
	public String product_machine_number; // So may thiet bi
	public String contract_serial_num; // So hop dong
	public String bank_branch; 
	public long status;
	
	public List<ObjImage> images;
	public List<Fees> fees;
	public List<ObjQuestions> question_and_answears;
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
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
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
	public List<Fees> getFees() {
		return fees;
	}
	public void setFees(List<Fees> fees) {
		this.fees = fees;
	}
	public List<ObjQuestions> getQuestion_and_answears() {
		return question_and_answears;
	}
	public void setQuestion_and_answears(List<ObjQuestions> question_and_answears) {
		this.question_and_answears = question_and_answears;
	}
	public long getProduct_valuation() {
		return product_valuation;
	}
	public void setProduct_valuation(long product_valuation) {
		this.product_valuation = product_valuation;
	}
	public long getBorrower_income() {
		return borrower_income;
	}
	public void setBorrower_income(long borrower_income) {
		this.borrower_income = borrower_income;
	}
	public String getBorrower_fullname() {
		return borrower_fullname;
	}
	public void setBorrower_fullname(String borrower_fullname) {
		this.borrower_fullname = borrower_fullname;
	}
	public String getBorrower_address() {
		return borrower_address;
	}
	public void setBorrower_address(String borrower_address) {
		this.borrower_address = borrower_address;
	}
	public String getId_issue_at() {
		return id_issue_at;
	}
	public void setId_issue_at(String id_issue_at) {
		this.id_issue_at = id_issue_at;
	}

	public String getProduct_desc() {
		return product_desc;
	}
	public void setProduct_desc(String product_desc) {
		this.product_desc = product_desc;
	}
	
	public long getId_issue_date() {
		return id_issue_date;
	}
	public void setId_issue_date(long id_issue_date) {
		this.id_issue_date = id_issue_date;
	}
	public long getBorrower_birthday() {
		return borrower_birthday;
	}
	public void setBorrower_birthday(long borrower_birthday) {
		this.borrower_birthday = borrower_birthday;
	}
	public String getProduct_machine_number() {
		return product_machine_number;
	}
	public void setProduct_machine_number(String product_machine_number) {
		this.product_machine_number = product_machine_number;
	}
	public String getContract_serial_num() {
		return contract_serial_num;
	}
	public void setContract_serial_num(String contract_serial_num) {
		this.contract_serial_num = contract_serial_num;
	}
	public String getBank_branch() {
		return bank_branch;
	}
	public void setBank_branch(String bank_branch) {
		this.bank_branch = bank_branch;
	}
	public String getChange_fee() {
		return change_fee;
	}
	public void setChange_fee(String change_fee) {
		this.change_fee = change_fee;
	}
}
