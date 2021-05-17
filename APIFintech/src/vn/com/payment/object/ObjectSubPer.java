package vn.com.payment.object;

import java.util.List;

import com.google.gson.Gson;

import vn.com.payment.entities.SubPermission;

public class ObjectSubPer {
	public String name;
	List<SubPermission> sub_permission;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<SubPermission> getSub_permission() {
		return sub_permission;
	}
	public void setSub_permission(List<SubPermission> sub_permission) {
		this.sub_permission = sub_permission;
	}
}
