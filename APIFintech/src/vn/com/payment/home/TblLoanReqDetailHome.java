package vn.com.payment.home;
// Generated May 16, 2021 2:41:23 PM by Hibernate Tools 3.5.0.Final

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import vn.com.payment.config.LogType;
import vn.com.payment.entities.TblLoanReqDetail;
import vn.com.payment.entities.TblLoanRequest;
import vn.com.payment.ultities.FileLogger;

/**
 * Home object for domain model class TblLoanReqDetail.
 * @see vn.com.payment.entities.TblLoanReqDetail
 * @author Hibernate Tools
 */
@Stateless
public class TblLoanReqDetailHome extends BaseSqlHomeDao{

	private static final Log log = LogFactory.getLog(TblLoanReqDetailHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public boolean createLoanReqDetail(TblLoanReqDetail tblLoanReqDetail) {
		try {
			save(tblLoanReqDetail);
			System.out.println("id: " + tblLoanReqDetail.getLoanId());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("createLoanReqDetail Exception "+ e, LogType.ERROR);
		}
		return false;
	}

	public boolean updateLoanReqDetail(TblLoanReqDetail tblLoanReqDetail) {
		try {
			updateObj(tblLoanReqDetail, tblLoanReqDetail.getLoanId());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("updateLoanReqDetail Exception "+ e, LogType.ERROR);
		}
		return false;
	}
	
	
	
	public static void main(String[] args) {
//		TblLoanReqDetailHome tblLoanReqDetailHome = new TblLoanReqDetailHome();
//		BigInteger aa = tblLoanReqDetailHome.getCountVA();
//		System.out.println(aa);
	}
}
