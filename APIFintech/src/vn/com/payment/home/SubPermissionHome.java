package vn.com.payment.home;
// Generated May 17, 2021 11:02:56 PM by Hibernate Tools 3.5.0.Final

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
import vn.com.payment.entities.GroupMapPer;
import vn.com.payment.entities.SubPermission;
import vn.com.payment.entities.TblProduct;
import vn.com.payment.ultities.FileLogger;

/**
 * Home object for domain model class SubPermission.
 * @see vn.com.payment.entities.SubPermission
 * @author Hibernate Tools
 */
@Stateless
public class SubPermissionHome extends BaseSqlHomeDao{

	private static final Log log = LogFactory.getLog(SubPermissionHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	
	public List<SubPermission> getSubPermission(int subPermission) {
		List<SubPermission> results = new ArrayList<>();
		Session session = null;
		try {
			session						 	= HibernateUtil.getSessionFactory().openSession();
			Criteria crtProduct 			= session.createCriteria(SubPermission.class);
			Criterion rowId 				= Restrictions.eq("rowId", subPermission);
			crtProduct.add(rowId);
			results = crtProduct.list();
			return results;
		} catch (Exception e) {
			FileLogger.log(" getSubPermission Exception "+ e, LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
	
	public List<SubPermission> getSubPermissionid(int perID, ArrayList<Integer> a) {
		List<SubPermission> results = new ArrayList<>();
		Session session = null;
		try {
			session						 	= HibernateUtil.getSessionFactory().openSession();
			Criteria crtProduct 			= session.createCriteria(SubPermission.class);
			Criterion permissionId 			= Restrictions.eq("permissionId", perID);
			Criterion permissionId1 		= Restrictions.in("rowId", a);
			crtProduct.add(permissionId1);
			crtProduct.add(permissionId);
			crtProduct.addOrder(Order.asc("permissionId"));
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
	
	public SubPermission getSubPermissionRowID (int rowId) {
		Session session = null;
		List<SubPermission> listSubPermission = new ArrayList<>();
		SubPermission subPermission = new SubPermission();
		try {
			session						 	= HibernateUtil.getSessionFactory().openSession();
			Criteria crtProduct 			= session.createCriteria(SubPermission.class);
			Criterion rowIdDB 				= Restrictions.eq("rowId", rowId);
			crtProduct.add(rowIdDB);		
			crtProduct.addOrder(Order.asc("orderMenu"));
			listSubPermission = crtProduct.list();
			
			if (listSubPermission.size() > 0) {
				subPermission = listSubPermission.get(0);
			}
			return subPermission;
		} catch (Exception e) {
			FileLogger.log(" getSubPermissionid Exception "+ e, LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
}
