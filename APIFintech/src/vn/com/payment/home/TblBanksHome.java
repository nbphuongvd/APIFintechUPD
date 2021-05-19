package vn.com.payment.home;
// Generated May 19, 2021 11:20:56 PM by Hibernate Tools 3.5.0.Final

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
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import vn.com.payment.config.LogType;
import vn.com.payment.entities.SubPermission;
import vn.com.payment.entities.TblBanks;
import vn.com.payment.ultities.FileLogger;

/**
 * Home object for domain model class TblBanks.
 * @see vn.com.payment.entities.TblBanks
 * @author Hibernate Tools
 */
@Stateless
public class TblBanksHome extends BaseSqlHomeDao{

	private static final Log log = LogFactory.getLog(TblBanksHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(TblBanks transientInstance) {
		log.debug("persisting TblBanks instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TblBanks persistentInstance) {
		log.debug("removing TblBanks instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public TblBanks merge(TblBanks detachedInstance) {
		log.debug("merging TblBanks instance");
		try {
			TblBanks result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TblBanks findById(Integer id) {
		log.debug("getting TblBanks instance with id: " + id);
		try {
			TblBanks instance = entityManager.find(TblBanks.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	public List<TblBanks> getTblBanks(int status, int bankSupport) {
		List<TblBanks> results = new ArrayList<>();
		Session session = null;
		try {
			session						 	= HibernateUtil.getSessionFactory().openSession();
			Criteria crtProduct 			= session.createCriteria(SubPermission.class);
			Criterion statusActive 			= Restrictions.eq("status", status);
			Criterion bankSupportFunction 	= Restrictions.in("bankSupportFunction", bankSupport);
			crtProduct.add(statusActive);
			crtProduct.add(bankSupportFunction);
//			crtProduct.addOrder(Order.asc("permissionId"));
			results = crtProduct.list();
			return results;
		} catch (Exception e) {
			FileLogger.log(" getSubPermissionid Exception "+ e, LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
}
