package vn.com.payment.object;

import com.google.gson.Gson;

public class ResContractListSponsor {
	public long loan_id;
	public String loan_code;
	public String loan_name;
	public String id_number;
	public long borrower_phone;
	public String product_name;
	public long approved_amount; 
	public long final_status; 
	public String created_date; 
	public String borrower_fullname; 
	public String branch_id; 
	public String room_code; // phong giao dich

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
