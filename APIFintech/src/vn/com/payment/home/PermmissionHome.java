package vn.com.payment.home;
// Generated 18-May-2021 10:34:34 by Hibernate Tools 3.5.0.Final

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
import vn.com.payment.entities.Permmission;
import vn.com.payment.entities.SubPermission;
import vn.com.payment.entities.TblProduct;
import vn.com.payment.ultities.FileLogger;

/**
 * Home object for domain model class Permmission.
 * @see vn.com.payment.entities.Permmission
 * @author Hibernate Tools
 */
@Stateless
public class PermmissionHome extends BaseSqlHomeDao{

	private static final Log log = LogFactory.getLog(PermmissionHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(Permmission transientInstance) {
		log.debug("persisting Permmission instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(Permmission persistentInstance) {
		log.debug("removing Permmission instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Permmission merge(Permmission detachedInstance) {
		log.debug("merging Permmission instance");
		try {
			Permmission result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Permmission findById(Integer id) {
		log.debug("getting Permmission instance with id: " + id);
		try {
			Permmission instance = entityManager.find(Permmission.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	public Permmission getPermmission(int rowId) {
		List<Permmission> results = new ArrayList<>();
		Session session = null;
		Permmission permmission = null;
		try {
			session						 	= HibernateUtil.getSessionFactory().openSession();
			Criteria crtProduct 			= session.createCriteria(Permmission.class);
			Criterion rowIdDB 				= Restrictions.eq("rowId", rowId);
			crtProduct.add(rowIdDB);
			results = crtProduct.list();
			if (results.size() > 0) {
				permmission = results.get(0);
			}
			return permmission;
		} catch (Exception e) {
			FileLogger.log(" getSubPermissionid Exception "+ e, LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
}
