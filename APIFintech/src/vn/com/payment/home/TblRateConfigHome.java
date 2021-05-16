package vn.com.payment.home;
// Generated May 16, 2021 10:01:24 AM by Hibernate Tools 3.5.0.Final

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.sun.xml.internal.rngom.parse.host.Base;

import vn.com.payment.config.LogType;
import vn.com.payment.entities.TblProduct;
import vn.com.payment.entities.TblRateConfig;
import vn.com.payment.ultities.FileLogger;

/**
 * Home object for domain model class TblRateConfig.
 * @see vn.com.payment.entities.TblRateConfig
 * @author Hibernate Tools
 */
@Stateless
public class TblRateConfigHome extends BaseSqlHomeDao{

	private static final Log log = LogFactory.getLog(TblRateConfigHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(TblRateConfig transientInstance) {
		log.debug("persisting TblRateConfig instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TblRateConfig persistentInstance) {
		log.debug("removing TblRateConfig instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public TblRateConfig merge(TblRateConfig detachedInstance) {
		log.debug("merging TblRateConfig instance");
		try {
			TblRateConfig result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TblRateConfig findById(int id) {
		log.debug("getting TblRateConfig instance with id: " + id);
		try {
			TblRateConfig instance = entityManager.find(TblRateConfig.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<TblRateConfig> getRateConfig() {
		List<TblRateConfig> results = new ArrayList<>();
		Session session = null;
		try {
			session						 	= HibernateUtil.getSessionFactory().openSession();
			Criteria crtProduct 			= session.createCriteria(TblRateConfig.class);
			Criterion active_status 		= Restrictions.eq("activeStatus", new Integer(1));
			crtProduct.add(active_status);
			results = crtProduct.list();
			return results;
		} catch (Exception e) {
			FileLogger.log(" getProduct Exception "+ e, LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
	
	public static void main(String[] args) {
		TblRateConfigHome tblRateConfigHome = new TblRateConfigHome();
		List<TblRateConfig> results = tblRateConfigHome.getRateConfig();
		System.out.println(results);
		for (TblRateConfig tblRateConfig : results) {
			System.out.println(tblRateConfig.getRateName());
		}
		
//		List<AllAppModel> lstExtrextData = new ArrayList<>();
	}
}
