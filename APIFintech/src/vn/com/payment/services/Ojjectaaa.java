package vn.com.payment.services;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import vn.com.payment.entities.SubPermission;

public class Ojjectaaa {
//	ArrayList<String> nameaaa;
	public String name;
//	List<String> aaaaaaaaa;
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
	
//	public List<String> getAaaaaaaaa() {
//		return aaaaaaaaa;
//	}
//	public void setAaaaaaaaa(List<String> aaaaaaaaa) {
//		this.aaaaaaaaa = aaaaaaaaa;
//	}
	
	
//	public Object getName() {
//		return name;
//	}
//	public void setName(Object name) {
//		this.name = name;
//	}
	
	
//	public ArrayList<String> getNameaaa() {
//		return nameaaa;
//	}
//	public void setNameaaa(ArrayList<String> nameaaa) {
//		this.nameaaa = nameaaa;
//	}
	
}
