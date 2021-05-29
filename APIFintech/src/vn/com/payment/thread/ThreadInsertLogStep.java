package vn.com.payment.thread;

import vn.com.payment.config.LogType;
import vn.com.payment.entities.TblLoanExpertiseSteps;
import vn.com.payment.entities.TblLoanRequest;
import vn.com.payment.home.DBFintechHome;
import vn.com.payment.ultities.FileLogger;

public class ThreadInsertLogStep implements Runnable {
	TblLoanExpertiseSteps tblLoanExpertiseSteps;
	DBFintechHome dbFintechHome = new DBFintechHome();

	public ThreadInsertLogStep(TblLoanExpertiseSteps tblLoanExpertiseSteps) {
		this.tblLoanExpertiseSteps = tblLoanExpertiseSteps;
	}

	public void run() {
		FileLogger.log("Bat dau ThreadInsertLogStep", LogType.BUSSINESS);
		boolean checkINS = dbFintechHome.createExpertiseSteps(tblLoanExpertiseSteps);
		FileLogger.log("ThreadInsertLogStep checkINS: " + checkINS, LogType.BUSSINESS);
		FileLogger.log("Ket thuc ThreadInsertLogStep", LogType.BUSSINESS);
	}
}
