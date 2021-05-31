package vn.com.payment.object;

import java.util.List;

import com.google.gson.Gson;

public class ReqAppraisal {
	public String username;
	public String token;
	public String loan_code;
	public String action;
	public String memo;
	public List<ObjImage> images;
	public List<ObjQuestions> question_and_answears;
	
	
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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

	public List<ObjImage> getImages() {
		return images;
	}

	public void setImages(List<ObjImage> images) {
		this.images = images;
	}

	public List<ObjQuestions> getQuestion_and_answears() {
		return question_and_answears;
	}

	public void setQuestion_and_answears(List<ObjQuestions> question_and_answears) {
		this.question_and_answears = question_and_answears;
	}

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

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
}
