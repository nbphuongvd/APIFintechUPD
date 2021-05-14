package vn.com.payment.ultities;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Convert2XML {

	/**
	 * @param args
	 */

	public static String object2Xml(Object obj) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			// Marshal the employees list in console
			jaxbMarshaller.marshal(obj, System.out);

			// Marshal the employees list in file
			// jaxbMarshaller
			// .marshal(employees, new File("c:/temp/employees.xml"));
		} catch (Exception e) {
		}
		return "";
	}

	public static Object xml2Object(String path, Class obj) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(obj);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			System.out.println("Dwuong dan den file: "+path);
			// We had written this file in marshalling example
			return jaxbUnmarshaller.unmarshal(new File(path));

			// retrun
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	

}
