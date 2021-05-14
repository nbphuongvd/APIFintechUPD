//package vn.com.payment.home;
//// Generated Nov 22, 2018 11:42:48 AM by Hibernate Tools 3.4.0.CR1
//
//import java.util.List;
//
//import org.hibernate.Criteria;
//import org.hibernate.Session;
//import org.hibernate.criterion.Criterion;
//import org.hibernate.criterion.Restrictions;
//
//import vn.com.payment.config.LogType;
//import vn.com.payment.entities.TblGwTransactions;
//import vn.com.payment.ultities.FileLogger;
//
///**
// * Home object for domain model class TblGwTransactions.
// * 
// * @see TblGwTransactions_bk.TblGwTransactions
// * @author Hibernate Tools
// */
//
//public class TblGwTransactionsHome extends BaseSqlHomeDao {
//	public TblGwTransactions getGwTransactionsById(String requetsId) {
//		TblGwTransactions acc = null;
//		Session session = null;
//		try {
//			session = HibernateUtil.getSessionFactory().openSession();
//			Criteria listTrans = session.createCriteria(TblGwTransactions.class);
//			Criterion name = Restrictions.eq("requestId", requetsId);
//			listTrans.add(name);
//			// listTrans.setMaxResults(1);
//			@SuppressWarnings("unchecked")
//			List<TblGwTransactions> listAcc = listTrans.list();
//			if (listAcc.size() > 0) {
//				acc = listAcc.get(0);
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			throw e;
//		} finally {
//			releaseSession(session);
//		}
//		return acc;
//	}
//	
//	public TblGwTransactions getGwTransactionsByGWTranID(String gwTransId) {
//		TblGwTransactions acc = null;
//		Session session = null;
//		try {
//			session = HibernateUtil.getSessionFactory().openSession();
//			Criteria listTrans = session.createCriteria(TblGwTransactions.class);
//			Criterion name = Restrictions.eq("gwTransId", gwTransId);
//			listTrans.add(name);
//			// listTrans.setMaxResults(1);
//			@SuppressWarnings("unchecked")
//			List<TblGwTransactions> listAcc = listTrans.list();
//			if (listAcc.size() > 0) {
//				acc = listAcc.get(0);
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			FileLogger.log("Bussiness Exception  getGwTransactionsByGWTranID: " + e.getMessage(), LogType.ERROR);
//			throw e;
//		} finally {
//			releaseSession(session);
//		}
//		return acc;
//	}
//	
//	public static void main(String[] args) {
//		TblGwTransactionsHome tblGwTransactionsHome = new TblGwTransactionsHome();
//		TblGwTransactions tblGwTransactions = new TblGwTransactions();
//		tblGwTransactions = tblGwTransactionsHome.getGwTransactionsByGWTranID("2f4b73ff43a9e48f4255a3e1ec5ec787");
//		System.out.println(tblGwTransactions.getCardPin());
//		if (tblGwTransactions != null && tblGwTransactions.getCardSerial() != null) {
//			System.out.println("aa");
//		}else{
//			System.out.println("bb");
//		}
//	}
//}
