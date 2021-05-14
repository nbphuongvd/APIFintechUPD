package vn.com.payment.ultities;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class SSException extends Exception {
	/**
	 * 
	 */
	public SSException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public SSException(String arg0, Throwable arg1) {
		super(arg0, arg1);

		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public SSException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public SSException(Throwable arg0) {
		super(arg0);
		Writer result = new StringWriter();
		PrintWriter printWriter = new PrintWriter(result);
		arg0.printStackTrace(printWriter);
		eMessage = result.toString();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int code;
	private String eMessage;
	
	private String logData = "";

	public String getLogData() {
		return logData;
	}

	public void setLogData(String logData) {
		this.logData = logData;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param eMessage
	 *            the eMessage to set
	 */
	public void seteMessage(String eMessage) {
		this.eMessage = eMessage;
	}

	/**
	 * @return the eMessage
	 */
	public String geteMessage() {
		return eMessage;
	}
}
