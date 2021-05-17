package vn.com.payment.services;

import java.util.List;

import com.google.gson.Gson;

import vn.com.payment.entities.GroupMapPer;
import vn.com.payment.entities.SubPermission;

public class ObjTest {
	public String name;
	List<SubPermission> permission;
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
	public List<SubPermission> getPermission() {
		return permission;
	}
	public void setPermission(List<SubPermission> permission) {
		this.permission = permission;
	}
}
