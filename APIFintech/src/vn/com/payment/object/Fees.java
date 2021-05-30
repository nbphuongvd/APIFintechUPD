package vn.com.payment.object;

import java.util.List;
import com.google.gson.Gson;
import vn.com.payment.entities.TblBanks;

public class Fees {
	public long fee_type;
	public double fix_fee_amount;
	public double fix_fee_percent;
	public String extra_fee_id;
	public String extra_fee_name;
	public String extra_fee_code;
	public String created_by;
	public String pay_type;
	public String created_date;

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
	
	public long getFee_type() {
		return fee_type;
	}

	public void setFee_type(long fee_type) {
		this.fee_type = fee_type;
	}

	public double getFix_fee_amount() {
		return fix_fee_amount;
	}

	public void setFix_fee_amount(double fix_fee_amount) {
		this.fix_fee_amount = fix_fee_amount;
	}

	public double getFix_fee_percent() {
		return fix_fee_percent;
	}

	public void setFix_fee_percent(double fix_fee_percent) {
		this.fix_fee_percent = fix_fee_percent;
	}

	public String getExtra_fee_id() {
		return extra_fee_id;
	}

	public void setExtra_fee_id(String extra_fee_id) {
		this.extra_fee_id = extra_fee_id;
	}

	public String getExtra_fee_name() {
		return extra_fee_name;
	}

	public void setExtra_fee_name(String extra_fee_name) {
		this.extra_fee_name = extra_fee_name;
	}

	public String getExtra_fee_code() {
		return extra_fee_code;
	}

	public void setExtra_fee_code(String extra_fee_code) {
		this.extra_fee_code = extra_fee_code;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public String getPay_type() {
		return pay_type;
	}

	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
	}

	public String getCreated_date() {
		return created_date;
	}

	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}
	
}
