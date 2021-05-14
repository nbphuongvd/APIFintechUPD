package vn.com.payment.ultities;

import com.thoughtworks.xstream.XStream;

public class Serilization {
	public static String objectToXML(Object obj) {
		XStream xstream = new XStream();
		String xmlString = xstream.toXML(obj);
		return xmlString;
	}
	
	public static Object xmlToObject(String xmlString)
	{
		XStream xstream = new XStream();
		return xstream.fromXML(xmlString);
	}
}
