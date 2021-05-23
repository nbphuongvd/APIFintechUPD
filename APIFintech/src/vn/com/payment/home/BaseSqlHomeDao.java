package vn.com.payment.home;

import static org.hibernate.criterion.Example.create;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.SessionFactoryUtils;

import vn.com.payment.config.LogType;
import vn.com.payment.entities.CompareObj;
import vn.com.payment.entities.TblImages;
import vn.com.payment.object.ObjImage;
import vn.com.payment.ultities.ClassUlt;
import vn.com.payment.ultities.FileLogger;
import vn.com.payment.ultities.GsonUltilities;

public class BaseSqlHomeDao {
	// private static final FileLogger log = new
	// FileLogger(BaseSqlHomeDao.class);

	public static void main(String[] args) throws Exception {

	}

	public Criteria createSearchCriteria(String table, ArrayList<CompareObj> compareObjs, Criteria criteria)
			throws Exception {

		try {
			for (CompareObj cobj : compareObjs) {
				Object value = cobj.getValue();

				String column = cobj.getColumn();

				switch (cobj.getType()) {
				case 0:
					criteria.add(Restrictions.eq(table + column, value));
					break;
				case 1:
					criteria.add(Restrictions.gt(table + column, value));
					break;
				case 2:
					criteria.add(Restrictions.lt(table + column, value));
					break;
				case 3:
					criteria.add(Restrictions.ge(table + column, value));

					break;
				case 4:
					criteria.add(Restrictions.le(table + column, value));
					break;
				case 5:
					criteria.add(Restrictions.like(table + column, value));
					break;
				default:
					break;
				}

			}
		} catch (Exception e) {
			throw e;
			// TODO: handle exception
		}
		return criteria;

	}

	public static Object updateObject(Object objold, Object objnew) throws Exception {
		try {
			Field[] fields = ClassUlt.getAllFields(objnew);
			for (Field field : fields) {
				if (field.get(objnew) != null) {
					field.set(objold, field.get(objnew));
				}

			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}

		return objold;
	}

	// public void persist(Object transientInstance) {
	// // log.debug("persisting TblACategories instance");
	// Session session = null;
	// Transaction trs = null;
	// try {
	// Long preProcess = System.currentTimeMillis();
	// session = HibernateUtil.getSessionFactory().openSession();
	// Long finishProcess = System.currentTimeMillis();
	// // log.info(">>>>>>>>>>>>>>>>>Total get connection="
	// // + (finishProcess - preProcess));
	// trs = session.beginTransaction();
	// session.persist(transientInstance);
	// // log.debug("persist successful");
	// trs.commit();
	// } catch (Exception re) {
	// re.printStackTrace();
	//// log.fatal("persist failed", re);
	//
	// } finally {
	// releaseSession(session);
	// }
	// }

	public int persist(Object transientInstance) {
		// log.debug("persisting TblACategories instance");
		int finish = -1;
		Session session = null;
		Transaction trs = null;
		try {
			System.currentTimeMillis();
			session = HibernateUtil.getSessionFactory().openSession();
			System.currentTimeMillis();
			// log.info(">>>>>>>>>>>>>>>>>Total get connection="
			// + (finishProcess - preProcess));
			trs = session.beginTransaction();
			session.persist(transientInstance);
			// log.debug("persist successful");
			trs.commit();
			finish = 0;
		} catch (ConstraintViolationException e) {
			e.printStackTrace();
			finish = -2;
			// TODO: handle exception
		} catch (Exception re) {
			re.printStackTrace();
			String object2Persist = GsonUltilities.toJson(transientInstance);
			FileLogger.log("persist ex" + re.getMessage(), LogType.ERROR);
			// log.fatal("persist failed[" + object2Persist + "]", re);
			// composeAlertAndSend(re);
		} finally {
			releaseSession(session);
		}
		return finish;
	}

	public void attachDirty(Object instance) {
		// log.debug("attaching dirty TblACategories instance");
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Transaction trs = session.beginTransaction();
			session.saveOrUpdate(instance);
			trs.commit();
			// log.debug("attach successful");
		} catch (Exception re) {
			// log.error("attach failed", re);

			throw re;
		} finally {
			releaseSession(session);
		}
	}

	public void attachClean(Object instance) {
		// log.debug("attaching clean TblACategories instance");
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.lock(instance, LockMode.NONE);
			// log.debug("attach successful");
		} catch (Exception re) {
			// log.error("attach failed", re);

			throw re;
		} finally {
			releaseSession(session);
		}
	}

	public void delete(Object persistentInstance) {
		// log.debug(String.format("deleting %s instance",
		// persistentInstance.getClass().getName()));
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			session.delete(persistentInstance);
			// log.debug("delete successful");
		} catch (Exception re) {
			// log.error("delete failed", re);

			throw re;
		} finally {
			releaseSession(session);
		}
	}

	public Object merge(Object detachedInstance) {
		// log.debug("merging TblACategories instance");
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			// session = sessionFactory.getCurrentSession();
			// session
			Transaction tx = session.beginTransaction();
			Object result = session.merge(detachedInstance);

			// log.debug("merge successful");
			tx.commit();
			return result;
		} catch (Exception re) {
			// log.error("merge failed", re);

			throw re;
		} finally {
			releaseSession(session);
		}
	}

	public void saveOrUpdate(Object detachedInstance) {
		// log.debug("merging TblACategories instance");
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			// session = sessionFactory.getCurrentSession();
			// session
			Transaction tx = session.beginTransaction();
			session.saveOrUpdate(detachedInstance);

			// log.debug("save or update successful");
			tx.commit();

		} catch (Exception re) {
			// log.error("save or update", re);

			throw re;
		} finally {
			releaseSession(session);
		}
	}

	public void updateObj(Object detachedInstance, Object id) throws Exception {
		// log.debug("merging TblACategories instance");
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			// session = sessionFactory.getCurrentSession();
			// session
			Transaction tx = session.beginTransaction();

			Object objOld = session.get(detachedInstance.getClass(), (Serializable) id);

			updateObject(objOld, detachedInstance);
			session.update(objOld);

			// log.debug("merge successful");
			tx.commit();

		} catch (Exception re) {
			// log.error("merge failed", re);

			throw re;
		} finally {
			releaseSession(session);
		}
	}

	public Serializable save(Object detachedInstance) throws Exception {
		// log.debug("merging TblACategories instance");
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			// session = sessionFactory.getCurrentSession();
			// session
			Transaction tx = session.beginTransaction();
			// session.save(detachedInstance);
			Serializable serializable = session.save(detachedInstance);
			// log.debug("save successful");
			tx.commit();
			return serializable;

		} catch (Exception re) {
			// log.error("merge failed", re);

			throw re;
		} finally {
			releaseSession(session);
		}
	}

	public boolean saveTransaction(Object obj1, Object obj2, List<TblImages> obj3) throws Exception {
		Session session = null;
		boolean result = false;
		try {
			session = HibernateUtil.getSessionFactory().openSession();

			Transaction transaction = session.beginTransaction();
			try {
				session.save(obj1);
				session.save(obj2);
				for (Object object : obj3) {
					session.save(object);
				}
				transaction.commit();
				result = true;
			} catch (Exception e) {
				session.flush();
				session.clear();
			}
			session.close();
			return result;
		} catch (Exception re) {
			re.printStackTrace();
			FileLogger.log("saveTransaction Exception " + re, LogType.ERROR);
		} finally {
			releaseSession(session);
		}
		return result;
	}

	public Object findById(Object objId, Class rootObj) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Object instance = session.get(rootObj, (Serializable) objId);
			if (instance == null) {
				// log.debug("get successful, no instance found");
			} else {
				// log.debug("get successful, instance found");
			}
			return instance;
		} catch (Exception re) {
			// log.error("get failed", re);

			throw re;
		} finally {
			try {
				session.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	public Object findByIdNoCloseSession(Object objId, Class rootObj, Session session) {
		try {
			Object instance = session.get(rootObj, (Serializable) objId);
			if (instance == null) {
				// log.debug("get successful, no instance found");
			} else {
				// log.debug("get successful, instance found");
			}
			return instance;
		} catch (Exception re) {
			// log.error("get failed", re);

			throw re;
		} finally {

		}
	}

	// public ArrayList<Object> searhWithObject(Object objsearch,
	// ArrayList<CompareObj> compareValue,
	// int limit, String oderby, boolean oderbyType)
	//
	// {
	// Session session = null;
	// try {
	// session = HibernateUtil.getSessionFactory().openSession();
	// session.beginTransaction();
	//
	// Criteria criteria = session.createCriteria(objsearch.getClass(), "o");
	// criteria.add(create(objsearch));
	// if (compareValue != null)
	// createSearchCriteria("o.", compareValue, criteria);
	// oderby = "o." + oderby;
	// criteria.setFirstResult(0).setMaxResults(limit);
	// if (oderbyType)
	// criteria.addOrder(Order.asc(oderby));
	// else
	// criteria.addOrder(Order.desc(oderby));
	//
	// List<Object> ls = criteria.list();
	//
	// return new ArrayList<>(ls);
	//
	// } catch (Exception e) {
	// log.error("searhWithObject", e);
	// } finally {
	// releaseSession(session);
	//
	// }
	// return null;
	//
	// }

	public List<Object> findByExample(Object instance) {
		// log.debug("finding TblACategories instance by example");
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();

			@SuppressWarnings("unchecked")
			List<Object> results = (List<Object>) session.createCriteria(instance.getClass().getName())
					.add(create(instance)).list();
			// log.debug("find by example successful, result size: " +
			// results.size());
			return results;
		} catch (Exception re) {
			// log.fatal("find by example failed", re);

			throw re;
		} finally {
			releaseSession(session);
		}
	}

	public List<Object> listAllObject(Object instance) {
		// log.debug("finding TblACategories instance by example");
		Session session = null;
		try {
			Long preProcess = System.currentTimeMillis();
			session = HibernateUtil.getSessionFactory().openSession();
			Long finishProcess = System.currentTimeMillis();
			// log.info(">>>>>>>>>>>>>>>>>Total get connection="
			// + (finishProcess - preProcess));
			@SuppressWarnings("unchecked")
			List<Object> results = (List<Object>) session.createCriteria(instance.getClass()).list();
			// log.debug("find by example successful, result size: " +
			// results.size());
			return results;
		} catch (Exception re) {
			// log.fatal("find by example failed", re);

			throw re;
		} finally {
			releaseSession(session);
		}
	}

	public Long getSequenceNextvalue(String sequenceName) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			String sql = "select " + sequenceName + ".nextval from dual";
			Object uniqueRs = session.createSQLQuery(sql).uniqueResult();
			if (uniqueRs != null)
				return Long.valueOf(uniqueRs.toString());
			return Long.valueOf(1);
		} catch (Exception e) {
			// TODO: handle exception
			// log.fatal(e.getLocalizedMessage(), e);

			return Long.valueOf(1);
		} finally {
			releaseSession(session);
		}
	}

	public void executeProcedure(String procedureName) {
		Session session = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			String sql = "CALL " + procedureName + "()";
			Object uniqueRs = session.createSQLQuery(sql).executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			// log.fatal(e.getLocalizedMessage(), e);

		} finally {
			releaseSession(session);
		}
	}

	// public void executeProcedure1cccc(String procedureName, String param,
	// String value) {
	// Session session = null;
	//
	// try {
	// session = HibernateUtil.getSessionFactory().openSession();
	// // String sql = "CALL " + procedureName + "()";
	//// Query query = session.createSQLQuery("call " + procedureName + " (:" +
	// param + ")").setParameter(param,value);
	// Query query = session.createSQLQuery("call").setParameter(
	// query.executeUpdate();
	// // Object uniqueRs = session.createSQLQuery(sql).executeUpdate();
	// } catch (Exception e) {
	// // TODO: handle exception
	// log.fatal(e.getLocalizedMessage(), e);
	//
	// } finally {
	// releaseSession(session);
	// }
	// }

	/**
	 * @param e
	 *            Tao canh? bao va gui cho Admin he thong
	 */

	// Cham cuu sau
	// public Long getSequenceNextvalue1(String sequenceName) {
	// Session session = null;
	// try {
	// session = HibernateUtil.getSessionFactory().openSession();
	// String sql = "select " + sequenceName + ".nextval from dual";
	//
	// Object uniqueRs = session.createSQLQuery(sql).uniqueResult();
	// if (uniqueRs != null)
	// return Long.valueOf(uniqueRs.toString());
	// return Long.valueOf(1);
	// } catch (Exception e) {
	// // TODO: handle exception
	// log.fatal(e.getLocalizedMessage(), e);
	// e.printStackTrace();
	// return Long.valueOf(1);
	// } finally {
	// releaseSession(session);
	// }
	// }

	public static void releaseSession(Session session) {
		if (session != null)
			try {
				SessionFactoryUtils.closeSession(session);
			} catch (Exception e) {
				// TODO: handle exception
			}
	}
}
