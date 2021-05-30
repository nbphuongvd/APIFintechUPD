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
			FileLogger.log(" getLoanDetail Exception "+ e, LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
}
