package vn.com.payment.ultities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class GenerateTransactionId {

	static GenerateTransactionId instance = null;
	static AtomicInteger atomicVal = new AtomicInteger(0);

	public static GenerateTransactionId GetInstance() {
		if (instance == null)
			new GenerateTransactionId();
		return instance;
	}

	public GenerateTransactionId() {
		// TODO Auto-generated constructor stub
		instance = this;
	}

	/**
	 * Ham sinh ra transactionID 
	 * 
	 * @author VietTung
	 * @param input neu input != null chuoi sinh ra se co prefix la input
	 * @return String
	 */
	public String GeneratePartnerTransactionId(String input) {
		String strDateFormat = GenerateTransactionId.dateToString(new Date(),
				"YYYYMMddHHmmss");
		if (input != null)
			return input + strDateFormat
					+ String.format("%05d", GetIncreaseNumber());
		else
			return strDateFormat + String.format("%05d", GetIncreaseNumber());
	}

	/**
	 * Ham sinh ra transaction ID gui sang FTP
	 * 
	 * @author VietTung
	 * @param merchantId
	 * @return String
	 */
	public String GeneratePartnerTransactionId() {
		String strDateFormat = GenerateTransactionId.dateToString(new Date(),
				"YYYYMMddHHmmss");
		return strDateFormat + String.format("%05d", GetIncreaseNumber());
	}

	private int GetIncreaseNumber() {
		synchronized (atomicVal) {
			int value = atomicVal.getAndIncrement();
			if (value == 999)
				atomicVal = new AtomicInteger(0);
			return value;
		}
	}

	public static String dateToString(Date date, String formatString) {
		DateFormat dateFormat = new SimpleDateFormat(formatString);// "yyyy/MM/dd HH:mm:ss");
		return dateFormat.format(date);
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10000; i++) {
			System.err.println(GenerateTransactionId.GetInstance()
					.GeneratePartnerTransactionId());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
