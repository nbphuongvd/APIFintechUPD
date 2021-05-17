package vn.com.payment.home;
// Generated May 18, 2021 12:51:34 AM by Hibernate Tools 3.5.0.Final

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
import vn.com.payment.entities.GroupMapPer;
import vn.com.payment.entities.GroupRoles;
import vn.com.payment.entities.TblProduct;
import vn.com.payment.ultities.FileLogger;

/**
 * Home object for domain model class GroupRoles.
 * @see vn.com.payment.entities.GroupRoles
 * @author Hibernate Tools
 */
@Stateless
public class GroupRolesHome extends BaseSqlHomeDao{

	private static final Log log = LogFactory.getLog(GroupRolesHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(GroupRoles transientInstance) {
		log.debug("persisting GroupRoles instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(GroupRoles persistentInstance) {
		log.debug("removing GroupRoles instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public GroupRoles merge(GroupRoles detachedInstance) {
		log.debug("merging GroupRoles instance");
		try {
			GroupRoles result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public GroupRoles findById(Integer id) {
		log.debug("getting GroupRoles instance with id: " + id);
		try {
			GroupRoles instance = entityManager.find(GroupRoles.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	public GroupRoles getGroupRoles(int roleID) {
//		List<GroupMapPer> results = new ArrayList<>();
		GroupRoles groupRoles = null;
		Session session = null;
		try {
			session						 	= HibernateUtil.getSessionFactory().openSession();
			Criteria crtProduct 			= session.createCriteria(GroupRoles.class);
			Criterion rowId 				= Restrictions.eq("rowId", roleID);
			crtProduct.add(rowId);
			@SuppressWarnings("unchecked")
			List<GroupRoles> listGroupRoles = crtProduct.list();
			if (listGroupRoles.size() > 0) {
				groupRoles = listGroupRoles.get(0);
			}
			return groupRoles;
		} catch (Exception e) {
			FileLogger.log(" getGroupMapPer Exception "+ e, LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
	public static void main(String[] args) {
		GroupRolesHome groupRolesHome = new GroupRolesHome();
		GroupRoles getGroupRoles = groupRolesHome.getGroupRoles(9);
		System.out.println(getGroupRoles.getName());
	}
}
