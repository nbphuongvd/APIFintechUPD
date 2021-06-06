package vn.com.payment.home;
// Generated May 16, 2021 2:41:23 PM by Hibernate Tools 3.5.0.Final

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import vn.com.payment.config.LogType;
import vn.com.payment.entities.SeqContract;
import vn.com.payment.entities.TblImages;
import vn.com.payment.entities.TblLoanBill;
import vn.com.payment.entities.TblLoanReqDetail;
import vn.com.payment.entities.TblLoanRequest;
import vn.com.payment.entities.TblLoanRequestAskAns;
import vn.com.payment.entities.TblProduct;
import vn.com.payment.object.ObjImage;
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
	
	public boolean createLoanTrans(int insOrUpd, TblLoanRequest tblLoanRequest, TblLoanReqDetail tblLoanReqDetail, List<TblImages> imagesList, List<TblLoanBill> loanBillList, TblLoanRequestAskAns tblLoanRequestAskAns) {
		try {
			boolean checkSaveTrans = saveOrUpdateTransaction(insOrUpd, tblLoanRequest, tblLoanReqDetail, imagesList, loanBillList, tblLoanRequestAskAns);
			System.out.println("id: " + tblLoanRequest.getLoanId());
			return checkSaveTrans;
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("TblLoanRequestHome createLoanRequest Exception "+ e, LogType.ERROR);
		}
		return false;
	}
	
	public boolean createLoanTransTD(int insOrUpd, TblLoanRequest tblLoanRequest, TblLoanReqDetail tblLoanReqDetail, List<TblImages> imagesList, TblLoanRequestAskAns tblLoanRequestAskAns) {
		try {
			boolean checkSaveTrans = saveOrUpdateTransactionTD(insOrUpd, tblLoanRequest, tblLoanReqDetail, imagesList, tblLoanRequestAskAns);
			System.out.println("id: " + tblLoanRequest.getLoanId());
			return checkSaveTrans;
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("TblLoanRequestHome createLoanRequest Exception "+ e, LogType.ERROR);
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
	
	public boolean checktblLoanRequest(String loanCode) {
		TblLoanRequest tblLoanRequest = null;
		Session session = null;
		boolean result = true;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Criteria crtProduct 	= session.createCriteria(TblLoanRequest.class);
			Criterion loancode 		= Restrictions.eq("loanCode", loanCode);
			crtProduct.add(loancode);
			@SuppressWarnings("unchecked")
			List<TblLoanRequest> listLoan = crtProduct.list();
			if (listLoan.size() > 0) {
				tblLoanRequest = listLoan.get(0);
				result = false;
			}			
		} catch (Exception e) {
			FileLogger.log(" getProduct Exception "+ e, LogType.ERROR);
			throw e;
		} finally {
			releaseSession(session);
		}
		return result;
	}
	
	public BigDecimal getSeqContract() {
		Session session = null;
		BigDecimal sumAmount = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			String sqlUpd = "update SeqContract SET rowId = LAST_INSERT_ID(rowId+1)";
			String sqlSel = "select sum(row_id) from seq_contract";
			System.out.println("sql: "+ sqlUpd);
			Query updateQ = session.createQuery(sqlUpd);
			SQLQuery<BigDecimal> selectQ = session.createSQLQuery(sqlSel);
			Transaction transaction = session.beginTransaction();
			try {
				int result = updateQ.executeUpdate();
				if(result == 1){
					transaction.commit();
					sumAmount = selectQ.uniqueResult();
				}				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return sumAmount;
		} catch (Exception e) {
			e.printStackTrace();
			log.fatal("getListVaMap = " +e);
		}finally {
			releaseSession(session);
		}
		return null;
	}
	
	public boolean updateTblLoanRequest(int loan_id, Integer extendStatus) {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			String sql = "update TblLoanRequest set extendStatus =:extendStatusPD, latestUpdate=:sysDate  where loanId =:loanIDUPD";	
			tx = session.beginTransaction();
			Query query = session.createQuery(sql);
			query.setParameter("sysDate", new Date());
			query.setParameter("loanIDUPD", loan_id);
			query.setParameter("extendStatusPD", extendStatus);
			int check = query.executeUpdate();
			tx.commit();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log(">> updateTblLoanRequest err " + e,LogType.ERROR);
		}
		return result;
    }
	
	public static void main(String[] args) {
		
		TblLoanRequestHome tblLoanReqDetailHome = new TblLoanRequestHome();
//		BigInteger aa = tblLoanReqDetailHome.getIDAutoIncrement();
//		System.out.println(aa);
//		
//		TblLoanRequest tblLoanRequest = new TblLoanRequest();
//		tblLoanRequest.setLoanId(10);
//		tblLoanRequest.setCreatedDate(new Date());
//		tblLoanRequest.setEditedDate(new Date());
//		tblLoanRequest.setExpireDate(new Date());
//		tblLoanRequest.setApprovedDate(new Date());
//		tblLoanRequest.setCreatedBy("Phuongvd");
//		tblLoanRequest.setApprovedBy("Phuongvd");
//		tblLoanRequest.setFinalStatus(99);
//		tblLoanRequest.setPreviousStatus(99);
//		tblLoanRequest.setSponsorId(1);
//		tblLoanRequest.setLatestUpdate(new Date());
//		
//		
//		TblLoanReqDetail tblLoanReqDetail = new TblLoanReqDetail();
//		tblLoanReqDetail.setReqDetailId(31);
//		tblLoanReqDetail.setLoanId(aa.intValue());
//		tblLoanReqDetail.setProductId(aa.intValue());
////		tblLoanReqDetail.setProductName("d_date,edited_date,disbursement_date" + aa.intValue());
//		tblLoanReqDetail.setImportFrom(aa.intValue());
//		tblLoanReqDetail.setManufactureDate(aa.intValue());
//		tblLoanReqDetail.setExpectAmount(500000);
//		tblLoanReqDetail.setBorrowerId(0);
//		tblLoanReqDetail.setApprovedAmount(500000l);
//		tblLoanReqDetail.setCreatedDate(new Date());
//		tblLoanReqDetail.setEditedDate(new Date());
//		tblLoanReqDetail.setDisbursementDate(aa.intValue());
		
		
		BigDecimal a =  tblLoanReqDetailHome.getSeqContract();

		System.out.println(a);
	}
}
