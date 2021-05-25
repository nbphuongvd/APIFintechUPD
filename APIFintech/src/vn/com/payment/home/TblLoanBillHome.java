package vn.com.payment.home;
// Generated 25-May-2021 21:30:25 by Hibernate Tools 3.5.0.Final

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import vn.com.payment.entities.TblLoanBill;

/**
 * Home object for domain model class TblLoanBill.
 * @see vn.com.payment.entities.TblLoanBill
 * @author Hibernate Tools
 */
@Stateless
public class TblLoanBillHome {

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

	public void remove(TblLoanBill persistentInstance) {
		log.debug("removing TblLoanBill instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public TblLoanBill merge(TblLoanBill detachedInstance) {
		log.debug("merging TblLoanBill instance");
		try {
			TblLoanBill result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TblLoanBill findById(Integer id) {
		log.debug("getting TblLoanBill instance with id: " + id);
		try {
			TblLoanBill instance = entityManager.find(TblLoanBill.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
}
