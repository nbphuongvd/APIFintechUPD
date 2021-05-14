package vn.com.payment.object;

import com.google.gson.Gson;

public class NotifyObject {
	public String subject;
	public String content;
	public String message_type;
	public String is_html;
	public String receive_email_expect;
	public String receive_sms_expect;
	public String receive_chat_id_expect;
	public String service_code;
	public String sub_service_code;
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
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMessage_type() {
		return message_type;
	}
	public void setMessage_type(String message_type) {
		this.message_type = message_type;
	}
	public String getIs_html() {
		return is_html;
	}
	public void setIs_html(String is_html) {
		this.is_html = is_html;
	}
	public String getReceive_email_expect() {
		return receive_email_expect;
	}
	public void setReceive_email_expect(String receive_email_expect) {
		this.receive_email_expect = receive_email_expect;
	}
	public String getReceive_sms_expect() {
		return receive_sms_expect;
	}
	public void setReceive_sms_expect(String receive_sms_expect) {
		this.receive_sms_expect = receive_sms_expect;
	}
	public String getReceive_chat_id_expect() {
		return receive_chat_id_expect;
	}
	public void setReceive_chat_id_expect(String receive_chat_id_expect) {
		this.receive_chat_id_expect = receive_chat_id_expect;
	}
	public String getService_code() {
		return service_code;
	}
	public void setService_code(String service_code) {
		this.service_code = service_code;
	}
	public String getSub_service_code() {
		return sub_service_code;
	}
	public void setSub_service_code(String sub_service_code) {
		this.sub_service_code = sub_service_code;
	}
	
}
