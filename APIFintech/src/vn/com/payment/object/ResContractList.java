package vn.com.payment.object;

import com.google.gson.Gson;

public class ResContractList {
	public String loan_code;
	public String loan_name;
	public long id_number;
	public long borrower_phone;
	public String product_name;
	public long approved_amount; 
	public long final_status; 
	public String created_date; 
	public String borrower_fullname; 
	public String borrower_fullname1; // phong giao dich

	
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
}
//"loan_code":			tbl_loan_request
//"loan_code":			tbl_loan_request
//"id_number": 			tbl_borrower
//"borrower_phone":  	tbl_loan_req_detail
//"product_name":   -   	branch_id tbl_brand
//"approved_amount":		tbl_loan_req_detail
//"final_status":		tbl_loan_req_detail
//"created_date":		tbl_loan_req_detail
//"borrower_fullname":	tbl_loan_req_detail
//phong giao dichj nao