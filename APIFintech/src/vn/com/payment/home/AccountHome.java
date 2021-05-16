package vn.com.payment.home;
// Generated 11-May-2021 22:31:59 by Hibernate Tools 3.5.0.Final

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
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
import vn.com.payment.ultities.FileLogger;
import vn.com.payment.ultities.MD5;

/**
 * Home object for domain model class Account.
 * @see vn.com.payment.entities.Account
 * @author Hibernate Tools
 */
@Stateless
public class AccountHome extends BaseSqlHomeDao{

	private static final Log log = LogFactory.getLog(AccountHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(Account transientInstance) {
		log.debug("persisting Account instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(Account persistentInstance) {
		log.debug("removing Account instance");
		try {
			entityManager.remove(persistentInstance);
			log.debug("remove successful");
		} catch (RuntimeException re) {
			log.error("remove failed", re);
			throw re;
		}
	}

	public Account merge(Account detachedInstance) {
		log.debug("merging Account instance");
		try {
			Account result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Account findById(Integer id) {
		log.debug("getting Account instance with id: " + id);
		try {
			Account instance = entityManager.find(Account.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	public Account getAccountLogin(String u_name, String password, int type) {
		Account acc = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Criteria listTrans = session.createCriteria(Account.class);
			Criterion name = Restrictions.eq("email", u_name);
			Criterion pwd = Restrictions.eq("password", password);
			Criterion status = Restrictions.eq("status", new Integer(1));
			Criterion typeCr = Restrictions.eq("type", type);
			listTrans.add(name);
			listTrans.add(pwd);
			listTrans.add(status);
			listTrans.add(typeCr);
			// listTrans.setMaxResults(1);
			@SuppressWarnings("unchecked")
			List<Account> listAcc = listTrans.list();
			if (listAcc.size() > 0) {
				acc = listAcc.get(0);
			}
		} catch (Exception e) {
			FileLogger.log(" getAccount Exception "+ e, LogType.ERROR);
			throw e;
		} finally {
			releaseSession(session);
		}
		return acc;
	}
	
	public Account getAccount(String u_name, String password) {
		Account acc = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Criteria listTrans = session.createCriteria(Account.class);
			Criterion name = Restrictions.eq("email", u_name);
			Criterion pwd = Restrictions.eq("password", password);
			Criterion status = Restrictions.eq("status", new Integer(1));
			listTrans.add(name);
			listTrans.add(pwd);
			listTrans.add(status);
			// listTrans.setMaxResults(1);
			@SuppressWarnings("unchecked")
			List<Account> listAcc = listTrans.list();
			if (listAcc.size() > 0) {
				acc = listAcc.get(0);
			}
		} catch (Exception e) {
			FileLogger.log(" getAccount Exception "+ e, LogType.ERROR);
			throw e;
		} finally {
			releaseSession(session);
		}
		return acc;
	}
	public Account getAccountUsename(String u_name) {
		Account acc = null;
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Criteria listTrans = session.createCriteria(Account.class);
			Criterion name = Restrictions.eq("email", u_name);
			listTrans.add(name);
			// listTrans.setMaxResults(1);
			@SuppressWarnings("unchecked")
			List<Account> listAcc = listTrans.list();
			if (listAcc.size() > 0) {
				acc = listAcc.get(0);
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			releaseSession(session);
		}
		return acc;
	}
	public boolean updateAccount(Account account) {
		try {
			updateObj(account, account.getRowId());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void main(String[] args) {
		String pass="";
		try {
			pass = MD5.hash(MD5.hash("123456"));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AccountHome accountHome = new AccountHome();
		Account acc = accountHome.getAccount("phongwm", pass);
		System.out.println(acc.getEmail());
	}
}
