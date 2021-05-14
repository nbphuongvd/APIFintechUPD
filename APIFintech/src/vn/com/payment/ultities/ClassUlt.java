package vn.com.payment.ultities;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;


public class ClassUlt {
	
	
	 
//	public static String convertByteToString(byte[] data) {
//		try {
//
//			return Commons.bytesToHex(data);
//			// return new String(data, "ISO-8859-1");
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		return null;
//	}
	public static String getClassShortName(Object obj) {
		String classShortName = "";
		classShortName = obj.getClass().getName();
		String[] splitName = classShortName.split("\\.");
		classShortName = splitName[splitName.length - 1];
		return classShortName;
	}

	public static String getClassShortName(Class obj) {
		String classShortName = "";
		classShortName = obj.getName();
		String[] splitName = classShortName.split("\\.");
		classShortName = splitName[splitName.length - 1];
		return classShortName;
	}

//	public static byte[] convertStringtoByte(String data) {
//		try {
//			return Commons.hexToBytes(data);
//
//		} catch (Exception e) {
//
//		}
//		return null;
//
//	}
//	
	
	public static void main(String[] args) {
 		
		byte[] data=convertOjtobytes(1);
		System.out.println(convertbytestoOj(data).getClass().getName());
	}

	public static Object convertbytestoOj(byte[] bytes) {
		Object o = null;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			ObjectInput in = null;
			try {
				in = new ObjectInputStream(bis);
				o = in.readObject();
			} finally {
				try {
					bis.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				try {
					if (in != null) {
						in.close();
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return o;
	}

	public static byte[] convertOjtobytes(Object o) {
		byte[] yourBytes = null;

		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutput out = null;
			try {
				out = new ObjectOutputStream(bos);
				out.writeObject(o);
				yourBytes = bos.toByteArray();
			} finally {
				try {
					if (out != null) {
						out.close();
					}
				} catch (IOException ex) {
					// ignore close exception
				}
				try {
					bos.close();
				} catch (IOException ex) {
					// ignore close exception
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return yourBytes;
	}

	public static Field[] getAllFields(Object obj) {
		Class<?> c = obj.getClass();
		Field[] listField = null;
		ArrayList<Field> arrayField = new ArrayList<>();
		try {
			Field[] fields = c.getDeclaredFields();
			if (c.getSuperclass() != null) {
				Class<?> supper = c.getSuperclass();
				Field[] supperClassFields = supper.getDeclaredFields();

				for (Field field : supperClassFields) {
					arrayField.add(field);
				}
			}
			for (Field field : fields) {
				arrayField.add(field);
			}
			listField = new Field[arrayField.size()];
			for (int i = 0; i < arrayField.size(); i++) {
				arrayField.get(i).setAccessible(true);

				listField[i] = arrayField.get(i);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listField;
	}

	public static Field[] getAllFields(Class c) {
		Field[] listField = null;
		ArrayList<Field> arrayField = new ArrayList<>();
		try {
			Field[] fields = c.getDeclaredFields();

			if (c.getSuperclass() != null) {
				Class<?> supper = c.getSuperclass();
				Field[] supperClassFields = supper.getDeclaredFields();

				for (Field field : supperClassFields) {
					arrayField.add(field);
				}
			}
			for (Field field : fields) {
				arrayField.add(field);
			}
			listField = new Field[arrayField.size()];
			for (int i = 0; i < arrayField.size(); i++)
				listField[i] = arrayField.get(i);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listField;
	}

	public static Field getField(Object obj, String fieldName) {
		Field field = null;
		try {
			Class<?> c = obj.getClass();
			try {
				field = c.getDeclaredField(fieldName);
			} catch (Exception e) {
				field = null;
			}
			if (field != null) {
				field.setAccessible(true);
				return field;
			}
			Class<?> supper = c.getSuperclass();
			field = supper.getDeclaredField(fieldName);
		} catch (Exception e) {
		}
		if (field != null) {
			field.setAccessible(true);
		}
		return field;
	}

	public static void setFieldNull(Object obj) {
		Field[] fields = ClassUlt.getAllFields(obj);
		try {
			for (Field field : fields) {
				field.setAccessible(true);
				if (field.get(obj) == null || field.get(obj).equals("")) {
					field.set(obj, "-1");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
