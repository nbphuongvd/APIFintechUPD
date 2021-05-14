package vn.com.payment.ultities;

import java.util.ArrayList;

import com.google.gson.Gson;

public class GsonUltilities {
	static Gson gsonParse = new Gson();

	public static String toJson(Object object) {
		return gsonParse.toJson(object);
	}

	@SuppressWarnings("unchecked")
	public static Object fromJson(String jsonString, Class t) {
		return gsonParse.fromJson(jsonString, t);
	}

	public static ArrayList<Object> fromJson(String jsonString,
			java.lang.reflect.Type t) {
		return gsonParse.fromJson(jsonString, t);
	}
}
