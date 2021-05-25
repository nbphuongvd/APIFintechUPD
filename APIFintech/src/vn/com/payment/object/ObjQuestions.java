package vn.com.payment.object;

import org.json.simple.JSONArray;

import com.google.gson.Gson;

public class ObjQuestions {
	public double fix_fee_amount;
	public double fix_fee_percent;
	public JSONArray result;
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
	public JSONArray getResult() {
		return result;
	}
	public void setResult(JSONArray result) {
		this.result = result;
	}
	
}
