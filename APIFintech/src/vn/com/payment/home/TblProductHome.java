package vn.com.payment.home;
// Generated 15-May-2021 09:07:14 by Hibernate Tools 3.5.0.Final

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
import vn.com.payment.entities.Account;
import vn.com.payment.entities.TblProduct;
import vn.com.payment.ultities.FileLogger;

/**
 * Home object for domain model class TblProduct.
 * @see vn.com.payment.entities.TblProduct
 * @author Hibernate Tools
 */
@Stateless
public class TblProductHome extends BaseSqlHomeDao{

	private static final Log log = LogFactory.getLog(TblProductHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(TblProduct transientInstance) {
		log.debug("persisting TblProduct instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TblProduct persistentInstance) {
		log.debug("removing TblProduct instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public TblProduct merge(TblProduct detachedInstance) {
		log.debug("merging TblProduct instance");
		try {
			TblProduct result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TblProduct findById(Integer id) {
		log.debug("getting TblProduct instance with id: " + id);
		try {
			TblProduct instance = entityManager.find(TblProduct.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	public TblProduct getProduct(String product_type, String product_brand, String product_modal) {
		TblProduct tblProduct = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Criteria crtProduct 	= session.createCriteria(TblProduct.class);
//			Criterion type 			= Restrictions.eq("productType", new Integer(1));
			Criterion brand 		= Restrictions.eq("productName", product_brand);
			Criterion modal			= Restrictions.eq("productCode", product_modal);
//			Criterion total 		= Restrictions.eq("total_run", total_run);
//			Criterion condition 	= Restrictions.eq("product_condition", product_condition);
//			Criterion borrower 		= Restrictions.eq("product_own_by_borrower", product_own_by_borrower);
//			Criterion serial 		= Restrictions.eq("product_serial_no", product_serial_no);
//			crtProduct.add(type);
			crtProduct.add(brand);
			crtProduct.add(modal);
//			crtProduct.add(total);
//			crtProduct.add(condition);
//			crtProduct.add(borrower);
//			crtProduct.add(serial);
			// listTrans.setMaxResults(1);
			@SuppressWarnings("unchecked")
			List<TblProduct> listProduct = crtProduct.list();
			if (listProduct.size() > 0) {
				tblProduct = listProduct.get(0);
			}
		} catch (Exception e) {
			FileLogger.log(" getProduct Exception "+ e, LogType.ERROR);
			throw e;
		} finally {
			releaseSession(session);
		}
		return tblProduct;
	}
	
	public static void main(String[] args) {
		TblProductHome tblProductHome = new TblProductHome();
		TblProduct tblProduct = tblProductHome.getProduct("1", "Jupiter", "JU 2015");
		System.out.println(tblProduct.getProductCode());
	}
}
