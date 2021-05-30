package vn.com.payment.object;

import com.google.gson.Gson;

public class ObjImage {
	public String image_name;
	public String image_input_name;
	public String partner_image_id;
	public long image_type;
	public String image_byte;
	public String image_url;
	public long image_is_front;
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
	public String getImage_name() {
		return image_name;
	}
	public void setImage_name(String image_name) {
		this.image_name = image_name;
	}
	
	public String getImage_input_name() {
		return image_input_name;
	}
	public void setImage_input_name(String image_input_name) {
		this.image_input_name = image_input_name;
	}
	public String getPartner_image_id() {
		return partner_image_id;
	}
	public void setPartner_image_id(String partner_image_id) {
		this.partner_image_id = partner_image_id;
	}
	public long getImage_type() {
		return image_type;
	}
	public void setImage_type(long image_type) {
		this.image_type = image_type;
	}
	public String getImage_byte() {
		return image_byte;
	}
	public void setImage_byte(String image_byte) {
		this.image_byte = image_byte;
	}
	public String getImage_url() {
		return image_url;
	}
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	public long getImage_is_front() {
		return image_is_front;
	}
	public void setImage_is_front(long image_is_front) {
		this.image_is_front = image_is_front;
	}
	
}
