//package vn.com.payment.home;
//// Generated May 18, 2018 3:23:47 PM by Hibernate Tools 3.4.0.CR1
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.TimeZone;
//
//import org.hibernate.Criteria;
//import org.hibernate.Session;
//import org.hibernate.criterion.Criterion;
//import org.hibernate.criterion.Order;
//import org.hibernate.criterion.Restrictions;
//
//import com.sun.tools.javac.code.Attribute.Constant;
//
//import vn.com.payment.config.LogType;
//import vn.com.payment.config.MainCfg;
//import vn.com.payment.entities.TblTransactions;
//import vn.com.payment.ultities.FileLogger;
//
//import org.hibernate.Query;
//
//
///**
// * Home object for domain model class TblTransactions.
// * 
// * @see tmp.TblTransactions
// * @author Hibernate Tools
// */
//
//public class TblTransactionsHome extends BaseSqlHomeDao {
//	SimpleDateFormat sdfClientDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////	@SuppressWarnings("unchecked")
////	public List<Object> getTransactionError() {
////
////		Session session = null;
////		try {
////			Calendar cal = Calendar.getInstance();
////			Date toDate = cal.getTime();
////
////			Calendar cal2 = Calendar.getInstance();
////			cal2.add(Calendar.MINUTE, -10);
////			Date fromDate = cal2.getTime();
////
////			session = HibernateUtil.getSessionFactory().openSession();
////			Criteria listTrans = session.createCriteria(TblTransactions.class);
////			System.out.println("GET TRANSACTION FROM:" + fromDate + " TO:" + toDate);
////			listTrans.add(Restrictions.ge("receiveDate", fromDate));
////			listTrans.add(Restrictions.le("receiveDate", toDate));
////			//listTrans.add(Restrictions.isNotNull("gatewayId"));
////
////			Criterion rest1 = Restrictions.and(Restrictions.eq("finalStatus", Constant.TRN_ERROR_EXCEPTION));
////
////			Criterion rest2 = Restrictions.and(Restrictions.eq("finalStatus", Constant.TRN_REQUEST_PENDING));
////
////			listTrans.add(Restrictions.or(rest1, rest2));
////
////			// listTrans.setMaxResults(1);
////			return listTrans.list();
////
////		} catch (Exception e) {
////			// TODO: handle exception
////			throw e;
////		} finally {
////			releaseSession(session);
////		}
////	}
////
////	public TblTransactions getTransactionById(String requetsId) {
////		TblTransactions acc = null;
////		Session session = null;
////		try {
////			session = HibernateUtil.getSessionFactory().openSession();
////			Criteria listTrans = session.createCriteria(TblTransactions.class);
////			Criterion name = Restrictions.eq("requestId", requetsId);
////			listTrans.add(name);
////			// listTrans.setMaxResults(1);
////			@SuppressWarnings("unchecked")
////			List<TblTransactions> listAcc = listTrans.list();
////			if (listAcc.size() > 0) {
////				acc = listAcc.get(0);
////			}
////		} catch (Exception e) {
////			// TODO: handle exception
////			throw e;
////		} finally {
////			releaseSession(session);
////		}
////		return acc;
////	}
////
////	public TblTransactions getTransactionByPartnerTransId(String requetsId) {
////		TblTransactions acc = null;
////		Session session = null;
////		try {
////			session = HibernateUtil.getSessionFactory().openSession();
////			Criteria listTrans = session.createCriteria(TblTransactions.class);
////			Criterion name = Restrictions.eq("requestIdTelco", requetsId);
////			listTrans.add(name);
////			// listTrans.setMaxResults(1);
////			@SuppressWarnings("unchecked")
////			List<TblTransactions> listAcc = listTrans.list();
////			if (listAcc.size() > 0) {
////				acc = listAcc.get(0);
////			}
////		} catch (Exception e) {
////			// TODO: handle exception
////			throw e;
////		} finally {
////			releaseSession(session);
////		}
////		return acc;
////	}
////
////	@SuppressWarnings("unchecked")
////	public List<Object> getTransactionPending() {
////
////		Session session = null;
////		try {
////			Calendar cal = Calendar.getInstance();
////			cal.add(Calendar.DAY_OF_MONTH, MainCfg.dayToCheckPending);
////			Date fromDate = cal.getTime();
////
////			Calendar cal2 = Calendar.getInstance();
////			cal2.add(Calendar.MINUTE, -5);
////			Date toDate = new Date();
////
////			session = HibernateUtil.getSessionFactory().openSession();
////			Criteria listTrans = session.createCriteria(TblTransactions.class);
////			System.out.println("Check PENDING FROM:" + fromDate + " TO:" + toDate);
////			listTrans.add(Restrictions.ge("receiveDate", fromDate));
////			listTrans.add(Restrictions.le("receiveDate", toDate));
////			Criterion rest1 = Restrictions.and(Restrictions.le("checkPendingTime", MainCfg.totalCheckPendingTime));
////
////			Criterion rest2 = Restrictions.and(Restrictions.isNull("checkPendingTime"));
////
////			listTrans.add(Restrictions.or(rest1, rest2));
////
////			listTrans.add(Restrictions.eq("finalStatus", Constant.TRN_REQUEST_PENDING));
////
////			// listTrans.setMaxResults(1);
////			return listTrans.list();
////
////		} catch (Exception e) {
////			// TODO: handle exception
////			throw e;
////		} finally {
////			releaseSession(session);
////		}
////	}
//
////	public List<TblTransactions> getListCard() {
////		Criteria criteria = null;
////		Session session = null;
////		List<TblTransactions> listTransaction = null;
////		try {
////			Date time = new Date();// Utils.getDateSubtractMinite(new Date(), 0);
////			session = HibernateUtil.getSessionFactory().openSession();
////			criteria = session.createCriteria(TblTransactions.class);
////			Criterion rest1= Restrictions.and(Restrictions.eq("finalStatus", "99"));
////			Criterion rest2= Restrictions.and(Restrictions.eq("status", new BigDecimal(15)));
////			criteria.setMaxResults(MainConfig.maximumPoolSizePutCard);
//////			criteria.add(Restrictions.or(rest1));
////			criteria.add(Restrictions.or(rest1, rest2));
////			listpushMerchant = criteria.list();
////			return listpushMerchant;
////		} catch (Exception e) {
//////			log.fatal("getListPushNotifyFail = " +e);
////			e.printStackTrace();
////		}finally {
////			releaseSession(session);
////		}
////		return null;
////	}
//	
//	public List<TblTransactions> getListTransactions(String finalStatus, Double timeMax, long limit) {
//		Session session = null;
//		List<TblTransactions> listPushmerchant = null;
//		try {
//			session = HibernateUtil.getSessionFactory().openSession();
//			StringBuffer sql = new StringBuffer();
//			sql.append("from tbl_transactions where final_status = :finalStatus and sysdate - receive_date <= :configTime and rownum <= :configLimit");
//			Query query = session.createQuery(sql.toString());
//			query.setParameter("finalStatus",finalStatus);
//			query.setParameter("configTime", timeMax);
//			query.setParameter("configLimit", limit);
//			listPushmerchant = query.list();
//			return listPushmerchant;
//		} catch (Exception e) {
//			e.printStackTrace();
//			FileLogger.log("getListTransactions false: "+ e.getMessage(), LogType.ERROR);
//		}finally {
//			releaseSession(session);
//		}
//		return null;
//	}
//	
//	
//	public List<TblTransactions> getListTransactionsPending() {
//		Session session = null;
////		System.out.println("Vao xoong");
//		List<TblTransactions> results = new ArrayList<>();
//		Calendar cal = Calendar.getInstance();
//		cal.add(Calendar.DATE, - MainCfg.TIME_GETTRANS);		
//
//		Calendar calMax = Calendar.getInstance();
//		calMax.add(Calendar.MINUTE, - MainCfg.MAXMINUTE_GETTRANS);		
//		TblTransactions tblTransactions = new TblTransactions();
//		try {
//			
//			String minDate = sdfClientDate.format(cal.getTime());
//			System.out.println("minDate: "+ minDate);
//			String maxDate = sdfClientDate.format(calMax.getTime());
//			System.out.println("maxDate: "+ maxDate);
//			FileLogger.log("getListTransactions minDate: "+ minDate +" maxDate: "+ maxDate, LogType.CHECKPENDING);
//			session = HibernateUtil.getSessionFactory().openSession();
//			Criteria c2 = session.createCriteria(tblTransactions.getClass());
//			c2.addOrder(Order.asc("receiveDate"));
//			c2.add(Restrictions.ge("receiveDate", sdfClientDate.parse(minDate)));
//			c2.add(Restrictions.le("receiveDate", sdfClientDate.parse(maxDate)));
//			c2.add(Restrictions.eq("finalStatus", "99"));
////			c2.add(Restrictions.in("employee_name", new String[] {MainCfg.GATEWAY_ID}));
//			c2.add(Restrictions.eq("gatewayId",   MainCfg.GATEWAY_ID));
//			c2.setMaxResults(MainCfg.MAX_TRANS);
//			System.out.println("MainCfg.MAX_TRANS: "+ MainCfg.MAX_TRANS);
//			results = c2.list();
//			return results;
//		} catch (Exception re) {
//			FileLogger.log("getListTransactions false: "+ re.getMessage(), LogType.ERROR);
//
//			re.printStackTrace();
//		} finally {
//			releaseSession(session);
//		} 
//		return null;
//	}
//
//	public List<TblTransactions> getListTransactionsBlock() {
//		Session session = null;
////		System.out.println("Vao xoong");
//		List<TblTransactions> results = new ArrayList<>();
//		Calendar cal = Calendar.getInstance();
//		cal.add(Calendar.DATE, - MainCfg.TIME_GETTRANS);		
//
//		Calendar calMax = Calendar.getInstance();
//		calMax.add(Calendar.MINUTE, - MainCfg.MAXMINUTE_GETTRANS);		
//		TblTransactions tblTransactions = new TblTransactions();
//		try {
//			
//			String minDate = sdfClientDate.format(cal.getTime());
//			System.out.println("minDate: "+ minDate);
//			String maxDate = sdfClientDate.format(calMax.getTime());
//			System.out.println("maxDate: "+ maxDate);
//			FileLogger.log("getListTransactions minDate: "+ minDate +" maxDate: "+ maxDate, LogType.CHECKPENDING);
//			session = HibernateUtil.getSessionFactory().openSession();
//			Criteria c2 = session.createCriteria(tblTransactions.getClass());
//			c2.addOrder(Order.asc("receiveDate"));
//			c2.add(Restrictions.ge("receiveDate", sdfClientDate.parse(minDate)));
//			c2.add(Restrictions.le("receiveDate", sdfClientDate.parse(maxDate)));
//			c2.add(Restrictions.eq("finalStatus", "99"));
////			c2.add(Restrictions.in("employee_name", new String[] {MainCfg.GATEWAY_ID}));
//			c2.add(Restrictions.eq("gatewayId",   MainCfg.GATEWAY_ID));
//			c2.setMaxResults(MainCfg.MAX_TRANS);
//			results = c2.list();
////			if (results.size() > 0) {
////				tblMoneyTransferRequestBl = (TblMoneyTransferRequestBl) results.get(0);
////			}	
////			System.out.println(results.size());
//			return results;
//		} catch (Exception re) {
//			FileLogger.log("getListTransactions false: "+ re.getMessage(), LogType.ERROR);
//
//			re.printStackTrace();
//		} finally {
//			releaseSession(session);
//		} 
//		return null;
//	}
//	
//	
//	public static void main(String[] args) {
////		SimpleDateFormat sdfClientDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////
////		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC+7"));
////		cal.add(Calendar.MINUTE, - MainCfg.MAXMINUTE_GETTRANS);		
////		String minDate = sdfClientDate.format(cal.getTime());
////		System.out.println("minDate: "+ minDate);
//		System.out.println(MainCfg.MAX_TRANS);
//		
////		TblTransactionsHome tblTransactionsHome = new TblTransactionsHome();
//////		double time = 7;
////		List<TblTransactions> list = tblTransactionsHome.getListTransactionsPending();
////		System.out.println("size: "+ list.size());
////		for (TblTransactions tblTransactions : list) {
////			System.out.println(tblTransactions.getCardSerial());
////		}
//		
//		// TblTransactions tblTransactions =
//		// tblTransactionsHome.getTransactionById("100_20180608080042001");
//		// System.out.println(GsonUltilities.toJson(tblTransactions));
////		tblTransactionsHome.getTransactionError();
//	}
//}
