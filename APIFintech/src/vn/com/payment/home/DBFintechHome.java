package vn.com.payment.home;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.json.JSONArray;
import org.json.JSONObject;

import vn.com.payment.config.LogType;
import vn.com.payment.entities.Account;
import vn.com.payment.entities.Permmission;
import vn.com.payment.entities.TblImages;
import vn.com.payment.entities.TblLoanBill;
import vn.com.payment.entities.TblLoanExpertiseSteps;
import vn.com.payment.entities.TblLoanReqDetail;
import vn.com.payment.entities.TblLoanRequest;
import vn.com.payment.entities.TblLoanRequestAskAns;
import vn.com.payment.entities.TblRateConfig;
import vn.com.payment.object.ResContractList;
import vn.com.payment.ultities.FileLogger;
import vn.com.payment.ultities.ValidData;

public class DBFintechHome extends BaseSqlHomeDao{

	private static final Log log = LogFactory.getLog(DBFintechHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	
	public boolean createExpertiseSteps(TblLoanExpertiseSteps TblLoanExpertiseSteps) {
		try {
			save(TblLoanExpertiseSteps);
			System.out.println("id: " + TblLoanExpertiseSteps.getLoanExpertiseId());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("createExpertiseSteps Exception "+ e, LogType.ERROR);
		}
		return false;
	}
	

	public boolean updateExpertiseSteps(TblLoanExpertiseSteps TblLoanExpertiseSteps) {
		try {
			updateObj(TblLoanExpertiseSteps, TblLoanExpertiseSteps.getLoanExpertiseId());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("updateExpertiseSteps Exception "+ e, LogType.ERROR);
		}
		return false;
	}

	public List<ResContractList> listResContractList(List<Integer> branchID, List<Integer> roomID,
			String loan_code, String final_status, String id_number, 
			String borrower_name, String from_date, String to_date, String calculate_profit_type, String limit, String offSet) {
		Session session = null;
		Transaction tx = null;
		List<Object> list = null;
		List<ResContractList> lisResContractList = new ArrayList<>();		
		String time = String.valueOf(10);
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			String sql = "SELECT lr.loanCode, "
								+ "lr.loanName, ld.borrowerId, "
								+ "ld.borrowerPhone, ld.productBrand, "
								+ "ld.approvedAmount, lr.finalStatus, "
								+ "lr.createdDate, ld.borrowerFullname, "
								+ "tr.roomName, br.branchName "
								+ "FROM TblLoanReqDetail ld "
								+ "INNER JOIN TblLoanRequest lr ON lr.loanId = ld.loanId "
								+ "INNER JOIN Branch br ON br.rowId in lr.branchId "
								+ "INNER JOIN TransasctionRoom tr ON tr.rowId in lr.roomId "
								+ "Where "
								+ "lr.roomId in :listRoom and "
								+ "lr.branchId in :listbranchId ";
			if(ValidData.checkNull(loan_code) == true){
				sql = sql + "and lr.loanCode =:loan_code ";
			}
			if(ValidData.checkNull(final_status) == true){
				sql = sql + "and lr.finalStatus =:final_status ";
			}
			if(ValidData.checkNull(borrower_name) == true){
				sql = sql + "and ld.borrowerFullname =:borrower_name ";
			}
			if(ValidData.checkNull(from_date) == true){
				sql = sql + "and lr.createdDate >= :from_date ";
			}
			if(ValidData.checkNull(to_date) == true){
				sql = sql + "and lr.createdDate <= :to_date ";
			}
			if(ValidData.checkNull(calculate_profit_type) == true){
				sql = sql + "and lr.calculateProfitType =:calculate_profit_type ";
			}
			if(ValidData.checkNull(id_number) == true){
				sql = sql + "and ld.borrowerId =:id_number ";
			}
			System.out.println("sql: "+ sql);
			session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery(sql);

			if(ValidData.checkNull(loan_code) == true){
				query.setParameter("loan_code", loan_code);
			}
			if(ValidData.checkNull(final_status) == true){
				query.setParameter("final_status", Integer.parseInt(final_status));
			}
			if(ValidData.checkNull(borrower_name) == true){
				query.setParameter("borrower_name", borrower_name);
			}
			if(ValidData.checkNull(from_date) == true){
				Date fromDate = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(from_date + " 00:00:00");  
				query.setParameter("from_date", fromDate);
			}
			if(ValidData.checkNull(to_date) == true){
				Date toDate = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(to_date + " 23:59:59");  
				query.setParameter("to_date", toDate);
			}
			if(ValidData.checkNull(calculate_profit_type) == true){
				query.setParameter("calculate_profit_type", Integer.parseInt(calculate_profit_type));
			}
			if(ValidData.checkNull(id_number) == true){
				query.setParameter("id_number", id_number);
			}
			query.setParameter("listRoom", roomID);
			query.setParameter("listbranchId", branchID);
			query.setFirstResult(Integer.parseInt(offSet));
			query.setMaxResults(Integer.parseInt(limit));
			list = query.getResultList();
			
			System.out.println(list.size());
			for (int i = 0; i<  list.size(); i++){
				Object[] row = (Object[]) list.get(i);		
				ResContractList reContractList = new ResContractList();
				for (int j = 0; j < row.length; j++) {				
					reContractList.setLoan_code(row[0]+"");
					reContractList.setLoan_name(row[1]+"");
					reContractList.setId_number(Long.parseLong(row[2]+""));
					reContractList.setBorrower_phone(Long.parseLong(row[3]+""));
					reContractList.setProduct_name(row[4]+"");
					reContractList.setApproved_amount(Long.parseLong(row[5]+""));
					reContractList.setFinal_status(Long.parseLong(row[6]+""));
					reContractList.setCreated_date(row[7]+"");
					reContractList.setBorrower_fullname(row[8]+"");
					reContractList.setBranch_id(row[10]+"");
					reContractList.setRoom_code(row[9]+"");
				}
				System.out.println("----------------------------------------------");
				lisResContractList.add(reContractList);
			}
			System.out.println(list.size());
			return lisResContractList;
		} catch (Exception e) {
			FileLogger.log(">> listResContractList err " + e.getMessage(),LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
	
	public long listCountContractList(List<Integer> branchID, List<Integer> roomID,
			String loan_code, String final_status, String id_number, 
			String borrower_name, String from_date, String to_date, String calculate_profit_type, String limit, String offSet) {
		Session session = null;
		Transaction tx = null;
		List<Object> list = null;
		List<ResContractList> lisResContractList = new ArrayList<>();		
		String time = String.valueOf(10);
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			String sql = "SELECT count(lr.loanCode)"
								+ "FROM TblLoanReqDetail ld INNER JOIN TblLoanRequest lr ON lr.loanId = ld.loanId "
								+ "Where "
								+ "lr.roomId in :listRoom and "
								+ "lr.branchId in :listbranchId ";
			if(ValidData.checkNull(loan_code) == true){
				sql = sql + "and lr.loanCode =:loan_code ";
			}
			if(ValidData.checkNull(final_status) == true){
				sql = sql + "and lr.finalStatus =:final_status ";
			}
			if(ValidData.checkNull(borrower_name) == true){
				sql = sql + "and ld.borrowerFullname =:borrower_name ";
			}
			if(ValidData.checkNull(from_date) == true){
				sql = sql + "and lr.createdDate >= :from_date ";
			}
			if(ValidData.checkNull(to_date) == true){
				sql = sql + "and lr.createdDate <= :to_date ";
			}
			if(ValidData.checkNull(calculate_profit_type) == true){
				sql = sql + "and lr.calculateProfitType =:calculate_profit_type ";
			}
			if(ValidData.checkNull(id_number) == true){
				sql = sql + "and ld.borrowerId =:id_number ";
			}
			System.out.println("sql: "+ sql);
			session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery(sql);

			if(ValidData.checkNull(loan_code) == true){
				query.setParameter("loan_code", loan_code);
			}
			if(ValidData.checkNull(final_status) == true){
				query.setParameter("final_status", Integer.parseInt(final_status));
			}
			if(ValidData.checkNull(borrower_name) == true){
				query.setParameter("borrower_name", borrower_name);
			}
			if(ValidData.checkNull(from_date) == true){
				Date fromDate = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(from_date + " 00:00:00");   
				query.setParameter("from_date", fromDate);
			}
			if(ValidData.checkNull(to_date) == true){
				Date toDate = new SimpleDateFormat("yyyyMMdd HH:mm:ss").parse(to_date + " 23:59:59");
				query.setParameter("to_date", toDate);
			}
			if(ValidData.checkNull(calculate_profit_type) == true){
				query.setParameter("calculate_profit_type", Integer.parseInt(calculate_profit_type));
			}
			if(ValidData.checkNull(id_number) == true){
				query.setParameter("id_number", id_number);
			}
			query.setParameter("listRoom", roomID);
			query.setParameter("listbranchId", branchID);
//			query.setFirstResult(Integer.parseInt(offSet));
//			query.setMaxResults(Integer.parseInt(limit));
			list = query.getResultList();
			
			System.out.println(list.get(0));
//			for (int i = 0; i<  list.size(); i++){
//				Object[] row = (Object[]) list.get(i);		
//				ResContractList reContractList = new ResContractList();
//				for (int j = 0; j < row.length; j++) {				
//					reContractList.setLoan_code(row[0]+"");
//					reContractList.setLoan_name(row[1]+"");
//					reContractList.setId_number(Long.parseLong(row[2]+""));
//					reContractList.setBorrower_phone(Long.parseLong(row[3]+""));
//					reContractList.setProduct_name(row[4]+"");
//					reContractList.setApproved_amount(Long.parseLong(row[5]+""));
//					reContractList.setFinal_status(Long.parseLong(row[6]+""));
//					reContractList.setCreated_date(row[7]+"");
//					reContractList.setBorrower_fullname(row[8]+"");
//					reContractList.setBranch_id(Long.parseLong(row[10]+""));
//					reContractList.setRoom_code(Long.parseLong(row[9]+""));
//				}
//				System.out.println("----------------------------------------------");
//				lisResContractList.add(reContractList);
//			}
			System.out.println(list.size());
			return (long)list.get(0);
		} catch (Exception e) {
			FileLogger.log(">> listResContractList err " + e.getMessage(),LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return 0l;
	}
	
	public List<TblLoanExpertiseSteps> getLoanExpertiseSteps(int loan_id) {
		List<TblLoanExpertiseSteps> results = new ArrayList<>();
		Session session = null;
		try {
			session						 	= HibernateUtil.getSessionFactory().openSession();
			Criteria crtProduct 			= session.createCriteria(TblLoanExpertiseSteps.class);
			Criterion getLoan 		= Restrictions.eq("loanId", loan_id);
			crtProduct.add(getLoan);
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
	
	public boolean checkLoan(List<Integer> branchID, List<Integer> roomID, int loanID) {
		Session session = null;
		Transaction tx = null;
		List<Object> list = null;
		List<ResContractList> lisResContractList = new ArrayList<>();		
		String time = String.valueOf(10);
		boolean result = false;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			String sql = "SELECT loanCode FROM TblLoanRequest Where "
								+ "loanId =:loanID and "
								+ "roomId in :listRoom and "
								+ "branchId in :listbranchId";
			System.out.println("sql: "+ sql);
			session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery(sql);
			query.setParameter("loanID", loanID);
			query.setParameter("listRoom", roomID);
			query.setParameter("listbranchId", branchID);
			list = query.getResultList();
			if(list.size() <= 0){
				result = false;
			}else{
				result = true;
			}
			System.out.println(list.size());
			return result;
		} catch (Exception e) {
			FileLogger.log(">> checkLoan err " + e.getMessage(),LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return false;
	}
	
	
	public TblLoanRequest getLoan(List<Integer> branchID, List<Integer> roomID, int loanID) {
		Session session = null;
		Transaction tx = null;
		List<Object> list = null;
		boolean result = false;
		TblLoanRequest tblLoanRequest = new TblLoanRequest();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			String sql = "FROM TblLoanRequest Where "
								+ "loanId =:loanID and "
								+ "roomId in :listRoom and "
								+ "branchId in :listbranchId";
			System.out.println("sql: "+ sql);
			session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery(sql);
			query.setParameter("loanID", loanID);
			query.setParameter("listRoom", roomID);
			query.setParameter("listbranchId", branchID);
			list = query.getResultList();
			if(list.size() > 0){
				tblLoanRequest = (TblLoanRequest) list.get(0);
			}
			System.out.println(list.size());
			return tblLoanRequest;
		} catch (Exception e) {
			FileLogger.log(">> checkLoan err " + e.getMessage(),LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
	
	public boolean createTblLoanExpertiseSteps(TblLoanExpertiseSteps tblLoanExpertiseSteps) {
		try {
			save(tblLoanExpertiseSteps);
			System.out.println("id: " + tblLoanExpertiseSteps.getLoanExpertiseId());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("createTblLoanExpertiseSteps Exception "+ e, LogType.ERROR);
		}
		return false;
	}
	
	public TblLoanReqDetail getLoanDetail(int loan_id) {
		List<TblLoanReqDetail> results = new ArrayList<>();
		Session session = null;
		TblLoanReqDetail tblLoanReqDetail = new TblLoanReqDetail();
		try {
			session						 	= HibernateUtil.getSessionFactory().openSession();
			Criteria crtProduct 			= session.createCriteria(TblLoanReqDetail.class);
			Criterion getLoan 				= Restrictions.eq("loanId", loan_id);
			crtProduct.add(getLoan);
			results = crtProduct.list();
			if(results.size() > 0){
				tblLoanReqDetail = (TblLoanReqDetail) results.get(0);
			}
			return tblLoanReqDetail;
		} catch (Exception e) {
			FileLogger.log(" getLoanDetail Exception "+ e, LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
	public List<TblImages> getTblImages(int loan_request_detail_id) {
		List<TblImages> results = new ArrayList<>();
		Session session = null;
		TblImages tblImages = new TblImages();
		try {
			session						 	= HibernateUtil.getSessionFactory().openSession();
			Criteria crtProduct 			= session.createCriteria(TblImages.class);
			Criterion getLoan 				= Restrictions.eq("loanRequestDetailId", loan_request_detail_id);
			crtProduct.add(getLoan);
			results = crtProduct.list();
//			if(results.size() > 0){
//				tblImages = (TblImages) results.get(0);
//			}
			return results;
		} catch (Exception e) {
			FileLogger.log(" getTblImages Exception "+ e, LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
	
	public List<TblLoanRequestAskAns> getRequestAskAns(int loanID) {
		List<TblLoanRequestAskAns> results = new ArrayList<>();
		Session session = null;
		TblLoanRequestAskAns tblLoanRequestAskAns = new TblLoanRequestAskAns();
		try {
			session						 	= HibernateUtil.getSessionFactory().openSession();
			Criteria crtProduct 			= session.createCriteria(TblLoanRequestAskAns.class);
			Criterion getLoan 				= Restrictions.eq("loanId", loanID);
			crtProduct.add(getLoan);
			results = crtProduct.list();
//			if(results.size() > 0){
//				tblLoanRequestAskAns = (TblLoanRequestAskAns) results.get(0);
//			}
			return results;
		} catch (Exception e) {
			FileLogger.log(" getRequestAskAns Exception "+ e, LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
	
	public ResContractList getBranchRoom(int branchID, int roomID, int loanID) {
		Session session = null;
		Transaction tx = null;
		List<Object> list = null;
		ResContractList lisResContractList = new ResContractList();		
		boolean result = false;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			String sql = "SELECT tr.roomName, br.branchName "											
									+ "FROM TblLoanRequest lr "
									+ "INNER JOIN Branch br ON br.rowId in lr.branchId "
									+ "INNER JOIN TransasctionRoom tr ON tr.rowId in lr.roomId "
									+ "Where "
									+ "lr.loanId =:loanID and "
									+ "lr.roomId =:listRoom and "
									+ "lr.branchId =:listbranchId ";
			System.out.println("sql: "+ sql);
			session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery(sql);
			query.setParameter("loanID", loanID);
			query.setParameter("listRoom", roomID);
			query.setParameter("listbranchId", branchID);
			list = query.getResultList();
			if(list.size() > 0){
				Object[] row = (Object[]) list.get(0);		
				for (int j = 0; j < row.length; j++) {				
					lisResContractList.setBranch_id(row[1]+"");
					lisResContractList.setRoom_code(row[0]+"");
				}
				System.out.println("----------------------------------------------");
			}
			System.out.println(list.size());
			return lisResContractList;
		} catch (Exception e) {
			FileLogger.log(">> checkLoan err " + e.getMessage(),LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
	
	public static void main(String[] args) {

		 List<Integer> list1 = new ArrayList<>();
//	      list1.add(new Integer(41));
//	      list1.add(new Integer(42));
//	      
//	      List<Integer> list = new ArrayList<>();
//	      list.add(new Integer(18));
//	      list.add(new Integer(19));
		AccountHome accountHome = new AccountHome();
		DBFintechHome dbFintechHome = new DBFintechHome();
		Account acc = accountHome.getAccountUsename("dinhphuong.v@gmail.com");
		List<Integer> branchID = new ArrayList<>();
		List<Integer> roomID = new ArrayList<>();
		if (ValidData.checkNull(acc.getBranchId()) == true) {
			JSONObject isJsonObject = (JSONObject) new JSONObject(acc.getBranchId());
			Iterator<String> keys = isJsonObject.keys();
			while (keys.hasNext()) {
				String key = keys.next();
				System.out.println(key);
				JSONArray msg = (JSONArray) isJsonObject.get(key);
				branchID.add(new Integer(key.toString()));
				for (int i = 0; i < msg.length(); i++) {
					roomID.add(Integer.parseInt(msg.get(i).toString()));
				}
			}
		}
		TblLoanRequest lisResContractList = dbFintechHome.getLoan(branchID, roomID, 67);
		System.out.println(lisResContractList.getLoanName());
//		for (ResContractList resContractList : lisResContractList) {
//			System.out.println(resContractList.getLoan_code());
//		}
//		
//	      System.out.println(list);
	}
}
