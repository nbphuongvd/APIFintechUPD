package vn.com.payment.home;
// Generated May 16, 2021 2:41:23 PM by Hibernate Tools 3.5.0.Final

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import vn.com.payment.config.LogType;
import vn.com.payment.entities.TblLoanReqDetail;
import vn.com.payment.entities.TblLoanRequest;
import vn.com.payment.ultities.FileLogger;

/**
 * Home object for domain model class TblLoanRequest.
 * @see vn.com.payment.entities.TblLoanRequest
 * @author Hibernate Tools
 */
@Stateless
public class TblLoanRequestHome extends BaseSqlHomeDao{

	private static final Log log = LogFactory.getLog(TblLoanRequestHome.class);

	@PersistenceContext
	private EntityManager entityManager;
	
	public boolean createLoanRequest(TblLoanRequest tblLoanRequest) {
		try {
			save(tblLoanRequest);
			System.out.println("id: " + tblLoanRequest.getLoanId());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("createLoanRequest Exception "+ e, LogType.ERROR);
		}
		return false;
	}
	
	public boolean createLoanTrans(TblLoanRequest tblLoanRequest, TblLoanReqDetail tblLoanReqDetail) {
		try {
			boolean checkSaveTrans = saveTransaction(tblLoanRequest, tblLoanReqDetail);
			System.out.println("id: " + tblLoanRequest.getLoanId());
			return checkSaveTrans;
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("createLoanRequest Exception "+ e, LogType.ERROR);
		}
		return false;
	}

	public boolean updateLoanRequest(TblLoanRequest tblLoanRequest) {
		try {
			updateObj(tblLoanRequest, tblLoanRequest.getLoanId());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("updateLoanRequest Exception "+ e, LogType.ERROR);
		}
		return false;
	}
	
	public BigInteger getIDAutoIncrement() {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			String tableName = "'tbl_loan_request'";
			String sql = "SELECT AUTO_INCREMENT FROM information_schema.tables WHERE table_name = " + tableName;
			SQLQuery<BigInteger> query = session.createSQLQuery(sql);
			BigInteger sumAmount = query.uniqueResult();
			System.err.println("sumAmount = " + sumAmount);
			System.out.println("Aaa: "+ sumAmount);
			return sumAmount;
		} catch (Exception e) {
			e.printStackTrace();
			log.fatal("getListVaMap = " +e);
		}finally {
			releaseSession(session);
		}
		return null;
	}
	
	public static void main(String[] args) {
		
		TblLoanRequestHome tblLoanReqDetailHome = new TblLoanRequestHome();
		BigInteger aa = tblLoanReqDetailHome.getIDAutoIncrement();
		System.out.println(aa);
		
		TblLoanRequest tblLoanRequest = new TblLoanRequest();
		tblLoanRequest.setLoanId(10);
		tblLoanRequest.setCreatedDate(new Date());
		tblLoanRequest.setEditedDate(new Date());
		tblLoanRequest.setExpireDate(new Date());
		tblLoanRequest.setApprovedDate(new Date());
		tblLoanRequest.setCreatedBy("Phuongvd");
		tblLoanRequest.setApprovedBy("Phuongvd");
		tblLoanRequest.setFinalStatus(99);
		tblLoanRequest.setPreviousStatus(99);
		tblLoanRequest.setSponsorId(1);
		tblLoanRequest.setLatestUpdate(new Date());
		
		
		TblLoanReqDetail tblLoanReqDetail = new TblLoanReqDetail();
		tblLoanReqDetail.setReqDetailId(31);
		tblLoanReqDetail.setLoanId(aa.intValue());
		tblLoanReqDetail.setProductId(aa.intValue());
		tblLoanReqDetail.setProductName("d_date,edited_date,disbursement_date" + aa.intValue());
		tblLoanReqDetail.setImportFrom(aa.intValue());
		tblLoanReqDetail.setManufactureDate(aa.intValue());
		tblLoanReqDetail.setExpectAmount(500000);
		tblLoanReqDetail.setBorrowerId(0);
		tblLoanReqDetail.setApprovedAmount(500000l);
		tblLoanReqDetail.setCreatedDate(new Date());
		tblLoanReqDetail.setEditedDate(new Date());
		tblLoanReqDetail.setDisbursementDate(aa.intValue());
		
		
		boolean a =  tblLoanReqDetailHome.createLoanTrans(tblLoanRequest, tblLoanReqDetail);

		System.out.println(a);
	}
}
