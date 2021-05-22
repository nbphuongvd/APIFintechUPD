package vn.com.payment.object;

import java.util.List;
import com.google.gson.Gson;
import vn.com.payment.entities.TblBanks;

public class Fees {
	public long fee_type;
	public double fix_fee_amount;
	public double fix_fee_percent;
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

	
}
