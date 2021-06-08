package vn.com.payment.home;
// Generated 25-May-2021 21:30:25 by Hibernate Tools 3.5.0.Final

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import vn.com.payment.config.LogType;
import vn.com.payment.entities.TblLoanBill;
import vn.com.payment.entities.TblLoanReqDetail;
import vn.com.payment.entities.TblLoanRequest;
import vn.com.payment.ultities.FileLogger;

/**
 * Home object for domain model class TblLoanBill.
 * @see vn.com.payment.entities.TblLoanBill
 * @author Hibernate Tools
 */
@Stateless
public class TblLoanBillHome extends BaseSqlHomeDao{

	private static final Log log = LogFactory.getLog(TblLoanBillHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(TblLoanBill transientInstance) {
		log.debug("persisting TblLoanBill instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public List<TblLoanBill> getTblLoanBill(int loan_id) {
		List<TblLoanBill> results = new ArrayList<>();
		Session session = null;
		TblLoanBill tblLoanBill = new TblLoanBill();
		try {
			session						 	= HibernateUtil.getSessionFactory().openSession();
			Criteria crtProduct 			= session.createCriteria(TblLoanBill.class);
			Criterion getLoan 				= Restrictions.eq("loanId", loan_id);
			crtProduct.add(getLoan);
			results = crtProduct.list();
			return results;
		} catch (Exception e) {
			FileLogger.log(" getTblLoanBill Exception "+ e, LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
	
	public List<TblLoanBill> getListTblLoanBill(int loan_id, int billPaymentStatus) {
		List<TblLoanBill> results = new ArrayList<>();
		Session session = null;
		TblLoanBill tblLoanBill = new TblLoanBill();
		try {
			session						 	= HibernateUtil.getSessionFactory().openSession();
			Criteria crtProduct 			= session.createCriteria(TblLoanBill.class);
			Criterion getLoan 				= Restrictions.eq("loanId", loan_id);
			Criterion billPStatus 			= Restrictions.eq("billPaymentStatus", billPaymentStatus);
			crtProduct.add(getLoan);
			crtProduct.add(billPStatus);
			results = crtProduct.list();
			return results;
		} catch (Exception e) {
			FileLogger.log(" getTblLoanBill Exception "+ e, LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
	
	
	public TblLoanBill getTblLoanBillIndex(int loan_id, int bill_Index) {
		List<TblLoanBill> results = new ArrayList<>();
		Session session = null;
		TblLoanBill tblLoanBill = new TblLoanBill();
		try {
			session						 	= HibernateUtil.getSessionFactory().openSession();
			Criteria crtProduct 			= session.createCriteria(TblLoanBill.class);
			Criterion loanID 				= Restrictions.eq("loanId", loan_id);
			Criterion billID 				= Restrictions.eq("billIndex", bill_Index);
			crtProduct.add(loanID);
			crtProduct.add(billID);
			results = crtProduct.list();
			if(results.size() > 0){
				tblLoanBill = (TblLoanBill) results.get(0);
			}
			return tblLoanBill;
		} catch (Exception e) {
			FileLogger.log(" getTblLoanBillIndex Exception "+ e, LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
	
	public static void main(String[] args) {
		TblLoanBillHome tblLoanBillHome = new TblLoanBillHome();
		List<TblLoanBill> getListTblLoanBill = tblLoanBillHome.getListTblLoanBill(160, 0);
		for (TblLoanBill tblLoanBill : getListTblLoanBill) {
			System.out.println(tblLoanBill.getBillIndex());
		}
	}
}
