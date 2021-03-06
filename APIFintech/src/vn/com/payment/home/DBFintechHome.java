package vn.com.payment.home;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.NativeQuery;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.core.env.SystemEnvironmentPropertySource;

import com.google.gson.Gson;

import vn.com.payment.config.LogType;
import vn.com.payment.entities.Account;
import vn.com.payment.entities.Permmission;
import vn.com.payment.entities.TblImages;
import vn.com.payment.entities.TblLoanBill;
import vn.com.payment.entities.TblLoanExpertiseSteps;
import vn.com.payment.entities.TblLoanReqDetail;
import vn.com.payment.entities.TblLoanRequest;
import vn.com.payment.entities.TblLoanRequestAskAns;
import vn.com.payment.entities.TblLoanSponsorMapp;
import vn.com.payment.entities.TblRateConfig;
import vn.com.payment.object.ObjDebtReminderDetail;
import vn.com.payment.object.ResContractList;
import vn.com.payment.object.ResContractListSponsor;
import vn.com.payment.ultities.FileLogger;
import vn.com.payment.ultities.Utils;
import vn.com.payment.ultities.ValidData;

public class DBFintechHome extends BaseSqlHomeDao{

	private static final Log log = LogFactory.getLog(DBFintechHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	Gson gson = new Gson();
	
	public boolean createExpertiseSteps(TblLoanExpertiseSteps tblLoanExpertiseSteps) {
		try {
			FileLogger.log("createExpertiseSteps "+ gson.toJson(tblLoanExpertiseSteps), LogType.BUSSINESS);
			save(tblLoanExpertiseSteps);
			FileLogger.log("createExpertiseSteps id: "+ tblLoanExpertiseSteps.getLoanExpertiseId(), LogType.BUSSINESS);
			System.out.println("id: " + tblLoanExpertiseSteps.getLoanExpertiseId());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("createExpertiseSteps Exception "+ e, LogType.ERROR);
		}
		return false;
	}
	
	public boolean createTblLoanSponsorMapp(TblLoanSponsorMapp tblLoanSponsorMapp) {
		try {
			FileLogger.log("createTblLoanSponsorMapp "+ gson.toJson(tblLoanSponsorMapp), LogType.BUSSINESS);
			save(tblLoanSponsorMapp);
			FileLogger.log("createTblLoanSponsorMapp id: "+ tblLoanSponsorMapp.getSponsorId(), LogType.BUSSINESS);
			System.out.println("id: " + tblLoanSponsorMapp.getSponsorId());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("createTblLoanSponsorMapp Exception "+ e, LogType.ERROR);
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

	public boolean updateTblLoanBill(TblLoanBill tblLoanBill) {
		try {
			updateObj(tblLoanBill, tblLoanBill.getBillId());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("updateExpertiseSteps Exception "+ e, LogType.ERROR);
		}
		return false;
	}
	
	public boolean updateTblLoanSponsorMapp(TblLoanSponsorMapp tblLoanSponsorMapp) {
		try {
			updateObj(tblLoanSponsorMapp, tblLoanSponsorMapp.getLoanSponsorMappId());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("updateExpertiseSteps Exception "+ e, LogType.ERROR);
		}
		return false;
	}
	
	public boolean updateTblLoanRequest(TblLoanRequest tblLoanRequest) {
		try {
			updateObj(tblLoanRequest, tblLoanRequest.getLoanId());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log("updateExpertiseSteps Exception "+ e, LogType.ERROR);
		}
		return false;
	}

	public List<ResContractList> listResContractList(List<Integer> branchID, List<Integer> roomID,
			String loan_code, List<Integer> final_status, String id_number, 
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
								+ "tr.roomName, br.branchName, lr.loanId "
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
			if(final_status.size() > 0){
				sql = sql + "and lr.finalStatus in :final_status ";
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
			sql = sql + "ORDER BY lr.createdDate DESC";
			System.out.println("sql: "+ sql);
			session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery(sql);

			if(ValidData.checkNull(loan_code) == true){
				query.setParameter("loan_code", loan_code);
			}
			if(final_status.size() > 0){
				query.setParameter("final_status", final_status);
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
					try {
						reContractList.setLoan_code(row[0]+"");
					} catch (Exception e) {
					}
					try {
						reContractList.setLoan_name(row[1]+"");
					} catch (Exception e) {
					}
					try {
						reContractList.setId_number(row[2]+"");
					} catch (Exception e) {
					}
					try {
						reContractList.setBorrower_phone(Long.parseLong(row[3]+""));
					} catch (Exception e) {
					}
					try {
						reContractList.setProduct_name(row[4]+"");
					} catch (Exception e) {
					}
					try {
						reContractList.setApproved_amount(Long.parseLong(row[5]+""));
					} catch (Exception e) {
					}
					try {
						reContractList.setFinal_status(Long.parseLong(row[6]+""));
					} catch (Exception e) {
					}
					try {
						reContractList.setCreated_date(row[7]+"");
					} catch (Exception e) {
					}
					try {
						reContractList.setBorrower_fullname(row[8]+"");
					} catch (Exception e) {
					}
					try {
						reContractList.setRoom_code(row[9]+"");
					} catch (Exception e) {
					}
					try {
						reContractList.setBranch_id(row[10]+"");
					} catch (Exception e) {
					}
					try {
						reContractList.setLoan_id(Long.parseLong(row[11]+""));
					} catch (Exception e) {
					}
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
			String loan_code, List<Integer> final_status, String id_number, 
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
			if(final_status.size() > 0){
				sql = sql + "and lr.finalStatus in :final_status ";
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
			if(final_status.size() > 0){
				query.setParameter("final_status", final_status);
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
			list = query.getResultList();
			
			System.out.println(list.get(0));
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
	
	public boolean checkLoan(List<Integer> branchID, List<Integer> roomID, String loanID) {
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
			query.setParameter("loanCode", loanID);
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
	
	
	public TblLoanRequest getLoan(List<Integer> branchID, List<Integer> roomID, String loanCode) {
		Session session = null;
		Transaction tx = null;
		List<Object> list = null;
		TblLoanRequest tblLoanRequest = new TblLoanRequest();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			String sql = "FROM TblLoanRequest Where "
								+ "loanCode =:loanCode and "
								+ "roomId in :listRoom and "
								+ "branchId in :listbranchId";
			System.out.println("sql: "+ sql);
			sql = sql + " ORDER BY createdDate DESC";
			session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery(sql);
			query.setParameter("loanCode", loanCode);
			query.setParameter("listRoom", roomID);
			query.setParameter("listbranchId", branchID);
			list = query.getResultList();
			if(list.size() > 0){
				tblLoanRequest = (TblLoanRequest) list.get(0);
				return tblLoanRequest;
			}
			System.out.println(list.size());
			
		} catch (Exception e) {
			FileLogger.log(">> checkLoan err " + e.getMessage(),LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
	
	
	public TblLoanRequest getLoanBranchID(List<Integer> branchID, String loanCode) {
		Session session = null;
		Transaction tx = null;
		List<Object> list = null;
		TblLoanRequest tblLoanRequest = new TblLoanRequest();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			String sql = "FROM TblLoanRequest Where "
								+ "loanCode =:loanCode and "
								+ "branchId in :listbranchId";
			System.out.println("sql: "+ sql);
			sql = sql + " ORDER BY createdDate DESC";
			session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery(sql);
			query.setParameter("loanCode", loanCode);
			query.setParameter("listbranchId", branchID);
			list = query.getResultList();
			if(list.size() > 0){
				tblLoanRequest = (TblLoanRequest) list.get(0);
				return tblLoanRequest;
			}
			System.out.println(list.size());
			
		} catch (Exception e) {
			FileLogger.log(">> checkLoan err " + e.getMessage(),LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
	public TblLoanRequest getLoanRoleNDT(String loanCode) {
		Session session = null;
		Transaction tx = null;
		List<Object> list = null;
		TblLoanRequest tblLoanRequest = new TblLoanRequest();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			String sql = "FROM TblLoanRequest Where "
								+ "loanCode =:loanCode";
			System.out.println("sql: "+ sql);
			session = HibernateUtil.getSessionFactory().openSession();
			sql = sql + " ORDER BY createdDate DESC";
			Query query = session.createQuery(sql);
			query.setParameter("loanCode", loanCode);
//			query.setParameter("listRoom", roomID);
//			query.setParameter("listbranchId", branchID);
			list = query.getResultList();
			if(list.size() > 0){
				tblLoanRequest = (TblLoanRequest) list.get(0);
				return tblLoanRequest;
			}
			System.out.println(list.size());
			
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
	
	public boolean createTblImages(TblImages tblImages) {
		try {
			save(tblImages);
			System.out.println("id: " + tblImages.getImageId());
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
				return tblLoanReqDetail;
			}
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
	
	public TblLoanRequest getTblLoanRequest(String loanCode) {
		List<TblLoanRequest> results = new ArrayList<>();
		Session session = null;
		TblLoanRequest tblLoanRequest = new TblLoanRequest();
		try {
			session						 	= HibernateUtil.getSessionFactory().openSession();
			Criteria crtProduct 			= session.createCriteria(TblLoanRequest.class);
			Criterion getLoan 				= Restrictions.eq("loanCode", loanCode);
			crtProduct.add(getLoan);
			results = crtProduct.list();
			if(results.size() > 0){
				tblLoanRequest = (TblLoanRequest) results.get(0);
			}
			return tblLoanRequest;
		} catch (Exception e) {
			FileLogger.log(" getTblLoanRequest Exception "+ e, LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
	public List<ResContractListSponsor> listListSponsor(int sponsorID, String loan_code, List<Integer> final_status, String borrower_name, String from_date, String to_date, String calculate_profit_type, String limit, String offSet) {
		Session session = null;
		Transaction tx = null;
		List<Object> list = null;
		List<ResContractListSponsor> lisResContractListSponsor = new ArrayList<>();		
		String time = String.valueOf(10);
		int disStatus = 1;
		try {
//			String s = new SimpleDateFormat("yyyyMMdd").format(new Date());
//			int dateNow = Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(new Date()));
			Date dateNowInt = new Date();
			session = HibernateUtil.getSessionFactory().openSession();
			int loan = 1;
			String sql = "SELECT lr.loanCode, "
								+ "lr.loanName, ld.productType, "
								+ "ld.approvedAmount, lr.finalStatus, "
								+ "ld.disbursementDate, ld.borrowerFullname, "
								+ "lr.loanId, lr.calculateProfitType, lr.loanForMonth, ls.disbursementStatus "
								+ "FROM TblLoanReqDetail ld "
								+ "INNER JOIN TblLoanRequest lr ON lr.loanId = ld.loanId "
								+ "INNER JOIN TblLoanSponsorMapp ls ON ls.loanId = ld.loanId "
								+ "Where ls.sponsorId =:sponsorID "
								+ "and (ls.entryExpireTime >= :dateNowInt or  ls.disbursementStatus =:disStatus) ";
			if(ValidData.checkNull(loan_code) == true){
				sql = sql + "and lr.loanCode =:loan_code ";
			}
			if(final_status.size() > 0){
				sql = sql + "and lr.finalStatus in :final_status ";
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
			sql = sql + "ORDER BY lr.createdDate DESC";
			System.out.println("sql: "+ sql);
			session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery(sql);
			query.setParameter("disStatus", disStatus);
			query.setParameter("sponsorID", sponsorID);
			query.setParameter("dateNowInt", dateNowInt);
			if(ValidData.checkNull(loan_code) == true){
				query.setParameter("loan_code", loan_code);
			}
			if(final_status.size() > 0){
				query.setParameter("final_status", final_status);
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
			query.setFirstResult(Integer.parseInt(offSet));
			query.setMaxResults(Integer.parseInt(limit));
			list = query.getResultList();
			
			System.out.println(list.size());
			for (int i = 0; i<  list.size(); i++){
				Object[] row = (Object[]) list.get(i);		
				ResContractListSponsor resContractListSponsor = new ResContractListSponsor();
				for (int j = 0; j < row.length; j++) {		
					try {
						resContractListSponsor.setLoan_code(row[0]+"");
					} catch (Exception e) {
					}
					try {
						resContractListSponsor.setLoan_name(row[1]+"");
					} catch (Exception e) {
					}
					try {
						resContractListSponsor.setProduct_name(row[2]+"");
					} catch (Exception e) {
					}
					try {
						resContractListSponsor.setApproved_amount(Long.parseLong(row[3]+""));
					} catch (Exception e) {
					}
					try {
						resContractListSponsor.setFinal_status(Long.parseLong(row[4]+""));
					} catch (Exception e) {
					}
					try {
						resContractListSponsor.setDisbursement_date(row[5]+"");
					} catch (Exception e) {
					}
					try {
						resContractListSponsor.setBorrower_fullname(row[6]+"");
					} catch (Exception e) {
					}
					try {
						resContractListSponsor.setLoan_id(Long.parseLong(row[7]+""));
					} catch (Exception e) {
					}
					try {
						resContractListSponsor.setCalculateProfitType(Long.parseLong(row[8]+""));
					} catch (Exception e) {
					}
					try {
						resContractListSponsor.setLoan_for_month(Long.parseLong(row[9]+""));
					} catch (Exception e) {
					}
					try {
						resContractListSponsor.setDisbursementStatus(Long.parseLong(row[10]+""));
					} catch (Exception e) {
					}
				}
				System.out.println("----------------------------------------------");
				lisResContractListSponsor.add(resContractListSponsor);
			}
			System.out.println(list.size());
			return lisResContractListSponsor;
		} catch (Exception e) {
			FileLogger.log(">> lisResContractListSponsor err " + e.getMessage(),LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
	
	public long listCounListSponsor(int sponsorID, String loan_code, List<Integer> final_status, String borrower_name, String from_date, String to_date, String calculate_profit_type, String limit, String offSet) {
		Session session = null;
		Transaction tx = null;
		List<Object> list = null;
		List<ResContractList> lisResContractList = new ArrayList<>();		
		String time = String.valueOf(10);
		int loan = 1;
//		int dateNow = Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(new Date()));
		Date dateNowInt = new Date();
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			String sql = "SELECT count(lr.loanCode) "
								+ "FROM TblLoanReqDetail ld "
								+ "INNER JOIN TblLoanRequest lr ON lr.loanId = ld.loanId "
								+ "INNER JOIN TblLoanSponsorMapp ls ON ls.loanId = ld.loanId "
								+ "Where ls.sponsorId =:sponsorID "
								+ "and ls.entryExpireTime >= :dateNowInt ";
			
			if(ValidData.checkNull(loan_code) == true){
				sql = sql + "and lr.loanCode =:loan_code ";
			}
			if(final_status.size() > 0){
				sql = sql + "and lr.finalStatus in :final_status ";
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
			System.out.println("sql: "+ sql);
			session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery(sql);
			query.setParameter("sponsorID", sponsorID);
			query.setParameter("dateNowInt", dateNowInt);
			if(ValidData.checkNull(loan_code) == true){
				query.setParameter("loan_code", loan_code);
			}
			if(final_status.size() > 0){
				query.setParameter("final_status", final_status);
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
			list = query.getResultList();
			
			System.out.println(list.get(0));
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
	
	public boolean deleteTblImages(int loan_request_detail_id) {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			String sql = "delete from TblImages where loanRequestDetailId =:detailId ";	
			tx = session.beginTransaction();
			Query query = session.createQuery(sql);
			query.setParameter("detailId", loan_request_detail_id);
			int check = query.executeUpdate();
			tx.commit();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log(">> deleteTblImages err " + e,LogType.ERROR);
		}
		return result;
    }
	
	public boolean deleteAskAns(int loan_id) {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			String sql = "delete from TblLoanRequestAskAns where loanId =:loan_id ";	
			tx = session.beginTransaction();
			Query query = session.createQuery(sql);
			query.setParameter("loan_id", loan_id);
			int check = query.executeUpdate();
			tx.commit();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log(">> deleteAskAns err " + e,LogType.ERROR);
		}
		return result;
    }
	
	public TblLoanSponsorMapp getLoanRequet(int loanID, int sponsorID) {
		Session session = null;
		BigDecimal sumAmount = null;
		List<Object> list = null;
		try {
			int statusDis = 5;
			int statusDisCreater = 0;
			session = HibernateUtil.getSessionFactory().openSession();
			String sqlUpd = "update TblLoanSponsorMapp set disbursementStatus =:statusDis where loanId =:loanIDUPD and sponsorId =:sponsorIDUPD and disbursementStatus =:statusDisCreater";
			String sqlSel = "from TblLoanSponsorMapp where loanId =:loanID and sponsorId =:sponsorID and disbursementStatus =:statusDisCreater";
			Query updateQ = session.createQuery(sqlUpd);
			Query query = session.createQuery(sqlSel);
			updateQ.setParameter("statusDis", statusDis);
			updateQ.setParameter("loanIDUPD", loanID);
			updateQ.setParameter("sponsorIDUPD", sponsorID);
			updateQ.setParameter("statusDisCreater", statusDisCreater);
			
			query.setParameter("statusDisCreater", statusDis);
			query.setParameter("loanID", loanID);
			query.setParameter("sponsorID", sponsorID);
			Transaction transaction = session.beginTransaction();
			try {
				int result = updateQ.executeUpdate();
				if(result == 1){					
					TblLoanSponsorMapp tbLoanSponsorMapp = null;
					list = query.getResultList();
					System.out.println(list.size());
					tbLoanSponsorMapp = (TblLoanSponsorMapp) list.get(0);
					transaction.commit();
					return tbLoanSponsorMapp;
				}				
			} catch (Exception e) {
				transaction.rollback();
				e.printStackTrace();
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			log.fatal("getListVaMap = " +e);
		}finally {
			releaseSession(session);
		}
		return null;
	}
	
	
	public List<ObjDebtReminderDetail> listDebtReminderDetail(String loan_code, List<Integer> final_status, String borrower_name, String from_date, String to_date, String id_number, String limit, String offSet) {
		Session session = null;
		Transaction tx = null;
		List<Object> list = null;
		List<ObjDebtReminderDetail> lisResDebtReminderDetail = new ArrayList<>();		
		String time = String.valueOf(10);
		int finalSTT = 116;
		int billSTT = 1161;
		try {
//			String s = new SimpleDateFormat("yyyyMMdd").format(new Date());
//			int dateNow = Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(new Date()));
			Date dateNowInt = new Date();
			session = HibernateUtil.getSessionFactory().openSession();
			int loan = 1;
			
			String sql = "SELECT lr.loanId, lr.loanCode, lr.createdBy, lr.finalStatus, ld.productDesc, "
								+ "lr.createdDate, ld.borrowerFullname, ld.borrowerId, ld.borrowerPhone, "
								+ "ld.approvedAmount, tr.roomName, br.branchName, lb.paymentAmt, "
								+ "lb.paymentDate, lb.billIndex, ld.productType, lb.dayMustPay, lb.totalOnAMonth, lb.billStatus "
								+ "FROM TblLoanReqDetail ld "
								+ "INNER JOIN TblLoanRequest lr ON lr.loanId = ld.loanId "
								+ "INNER JOIN TblLoanBill lb ON lb.loanId = ld.loanId "
								+ "INNER JOIN Branch br ON br.rowId in lr.branchId "
								+ "INNER JOIN TransasctionRoom tr ON tr.rowId in lr.roomId "
								+ "Where lr.loanId >=:loanID "
								+ "and lr.finalStatus =:finalStt "
								+ "and lb.billStatus =:billStt ";
			if(ValidData.checkNull(loan_code) == true){
				sql = sql + "and lr.loanCode =:loan_code ";
			}
//			if(final_status.size() > 0){
//				sql = sql + "and lr.finalStatus in :final_status ";
//			}
			if(ValidData.checkNull(borrower_name) == true){
				sql = sql + "and ld.borrowerFullname =:borrower_name ";
			}
			if(ValidData.checkNull(from_date) == true){
				sql = sql + "and lr.createdDate >= :from_date ";
			}
			if(ValidData.checkNull(to_date) == true){
				sql = sql + "and lr.createdDate <= :to_date ";
			}
			if(ValidData.checkNull(id_number) == true){
				sql = sql + "and ld.borrowerId =:id_number ";
			}
			sql = sql + "ORDER BY lr.createdDate DESC";
			System.out.println("sql: "+ sql);
			session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery(sql);
			query.setParameter("loanID", loan);
			if(ValidData.checkNull(loan_code) == true){
				query.setParameter("loan_code", loan_code);
			}
//			if(final_status.size() > 0){
				query.setParameter("finalStt", finalSTT);
				query.setParameter("billStt", billSTT);
//			}	
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
			if(ValidData.checkNull(id_number) == true){
				query.setParameter("id_number", id_number);
			}
			query.setFirstResult(Integer.parseInt(offSet));
			query.setMaxResults(Integer.parseInt(limit));
			list = query.getResultList();
			
			System.out.println(list.size());
			for (int i = 0; i<  list.size(); i++){
				Object[] row = (Object[]) list.get(i);		
				ObjDebtReminderDetail objDebtReminderDetail = new ObjDebtReminderDetail();
				for (int j = 0; j < row.length; j++) {	
					try {
						objDebtReminderDetail.setLoanId(Integer.parseInt(row[0]+""));
					} catch (Exception e) {
					}
					try {
						objDebtReminderDetail.setLoan_code(row[1]+"");
					} catch (Exception e) {
					}
					try {
						objDebtReminderDetail.setCreated_by(row[2]+"");
					} catch (Exception e) {
					}
					try {
						objDebtReminderDetail.setFinal_status(Integer.parseInt(row[3]+""));
					} catch (Exception e) {
					}
					try {
						objDebtReminderDetail.setProduct_desc(row[4]+"");
					} catch (Exception e) {
					}
					try {
						objDebtReminderDetail.setCreatedDate(row[5]+"");
					} catch (Exception e) {
					}
					try {
						objDebtReminderDetail.setBorrowerFullname(row[6]+"");
					} catch (Exception e) {
					}
					try {
						objDebtReminderDetail.setBorrower_id(row[7]+"");
					} catch (Exception e) {
					}
					try {
						objDebtReminderDetail.setBorrowerPhone(row[8]+"");
					} catch (Exception e) {
					}

					try {
						objDebtReminderDetail.setLoan_amount(Long.parseLong(row[9]+""));
					} catch (Exception e) {
					}
					try {
						objDebtReminderDetail.setRoom_name(row[10]+"");
					} catch (Exception e) {
					}
					try {
						objDebtReminderDetail.setBranch(row[11]+"");
					} catch (Exception e) {
					}
					try {
						objDebtReminderDetail.setPayment_amount(Long.parseLong(row[12]+""));
					} catch (Exception e) {
					}
					try {
						objDebtReminderDetail.setPayment_date(row[13]+"");
					} catch (Exception e) {
					}
					try {
						objDebtReminderDetail.setBill_index(Integer.parseInt(row[141]+""));
					} catch (Exception e) {
					}
					try {
						objDebtReminderDetail.setProduct_id(Integer.parseInt(row[15]+""));
					} catch (Exception e) {
					}
					try {
						objDebtReminderDetail.setDay_must_pay(Integer.parseInt(row[16]+""));
					} catch (Exception e) {
					}
					try {
						objDebtReminderDetail.setTotal_on_a_month(Long.parseLong(row[17]+""));
					} catch (Exception e) {
					}
					try {
						objDebtReminderDetail.setBill_status(Integer.parseInt(row[18]+""));
					} catch (Exception e) {
					}
				}
				System.out.println("----------------------------------------------");
				lisResDebtReminderDetail.add(objDebtReminderDetail);
			}
			System.out.println(list.size());
			return lisResDebtReminderDetail;
		} catch (Exception e) {
			FileLogger.log(">> lisResDebtReminderDetail err " + e.getMessage(),LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
	
	
	public long countDebtReminderDetail(String loan_code, List<Integer> final_status, String borrower_name, String from_date, String to_date, String id_number, String limit, String offSet) {
		Session session = null;
		Transaction tx = null;
		List<Object> list = null;
		List<ObjDebtReminderDetail> lisResDebtReminderDetail = new ArrayList<>();		
		String time = String.valueOf(10);
		int finalSTT = 116;
		int billSTT = 1161;
		try {
//			String s = new SimpleDateFormat("yyyyMMdd").format(new Date());
//			int dateNow = Integer.parseInt(new SimpleDateFormat("yyyyMMdd").format(new Date()));
			Date dateNowInt = new Date();
			session = HibernateUtil.getSessionFactory().openSession();
			int loan = 1;
			
			String sql = "SELECT count(lr.loanCode) "
								+ "FROM TblLoanReqDetail ld "
								+ "INNER JOIN TblLoanRequest lr ON lr.loanId = ld.loanId "
								+ "INNER JOIN TblLoanBill lb ON lb.loanId = ld.loanId "
								+ "INNER JOIN Branch br ON br.rowId in lr.branchId "
								+ "INNER JOIN TransasctionRoom tr ON tr.rowId in lr.roomId "
								+ "Where lr.loanId >=:loanID "
								+ "and lr.finalStatus =:finalStt "
								+ "and lb.billStatus =:billStt ";
			if(ValidData.checkNull(loan_code) == true){
				sql = sql + "and lr.loanCode =:loan_code ";
			}
//			if(final_status.size() > 0){
//				sql = sql + "and lr.finalStatus in :final_status ";
//			}
			if(ValidData.checkNull(borrower_name) == true){
				sql = sql + "and ld.borrowerFullname =:borrower_name ";
			}
			if(ValidData.checkNull(from_date) == true){
				sql = sql + "and lr.createdDate >= :from_date ";
			}
			if(ValidData.checkNull(to_date) == true){
				sql = sql + "and lr.createdDate <= :to_date ";
			}
			if(ValidData.checkNull(id_number) == true){
				sql = sql + "and ld.borrowerId =:id_number ";
			}
			sql = sql + "ORDER BY lr.createdDate DESC";
			System.out.println("sql: "+ sql);
			session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery(sql);
			query.setParameter("loanID", loan);
			if(ValidData.checkNull(loan_code) == true){
				query.setParameter("loan_code", loan_code);
			}
//			if(final_status.size() > 0){
				query.setParameter("finalStt", finalSTT);
				query.setParameter("billStt", billSTT);
//			}	
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
			if(ValidData.checkNull(id_number) == true){
				query.setParameter("id_number", id_number);
			}
			query.setFirstResult(Integer.parseInt(offSet));
			query.setMaxResults(Integer.parseInt(limit));
			list = query.getResultList();

			System.out.println(list.size());
			return (long)list.get(0);
		} catch (Exception e) {
			FileLogger.log(">> lisResDebtReminderDetail err " + e.getMessage(),LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return 0l;
	}
	
	public boolean updateLoanBill(int loan_id, int bill_index, Integer last_day_accept_pay) {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			String sql = "update TblLoanBill set lastDayAcceptPay =:lastDayUPD where loanId =:loanIDUPD and billIndex =:billIndexUPD";	
			tx = session.beginTransaction();
			Query query = session.createQuery(sql);
			query.setParameter("lastDayUPD", last_day_accept_pay);
			query.setParameter("loanIDUPD", loan_id);
			query.setParameter("billIndexUPD", bill_index);
			int check = query.executeUpdate();
			tx.commit();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			FileLogger.log(">> updateLoanBill err " + e,LogType.ERROR);
		}
		return result;
    }
	
	
	public int maxBillIndex(int loanID) {
		Session session = null;
		Transaction tx = null;
		List<Object> list = null;
		List<ObjDebtReminderDetail> lisResDebtReminderDetail = new ArrayList<>();		
		String time = String.valueOf(10);
		int finalSTT = 116;
		int billSTT = 1161;
		try {
			Date dateNowInt = new Date();
			session = HibernateUtil.getSessionFactory().openSession();
			String sql = "SELECT max(billIndex) FROM TblLoanBill ld where loanId =:loanID";
			session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery(sql);
			query.setParameter("loanID", loanID);
			list = query.getResultList();
			System.out.println(list.size());
			return (int)list.get(0);
		} catch (Exception e) {
			FileLogger.log(">> lisResDebtReminderDetail err " + e.getMessage(),LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return 0;
	}
	
	
	public static void main(String[] args) {
		
		DBFintechHome dbFintechHome = new DBFintechHome();
		
		int maxBillIndex = dbFintechHome.maxBillIndex(160);
		
		System.out.println(maxBillIndex);
		
//		SimpleDateFormat sm = new SimpleDateFormat("yyyyMMdd 00:00:00");
//		try {
//			AccountHome accountHome = new AccountHome();
//			DBFintechHome dbFintechHome = new DBFintechHome();
//			Account acc = accountHome.getAccountUsename("dinhphuong.v@gmail.com");
//			List<Integer> branchID = new ArrayList<>();
//			List<Integer> roomID = new ArrayList<>();
//			if (ValidData.checkNull(acc.getBranchId()) == true) {
//				JSONObject isJsonObject = (JSONObject) new JSONObject(acc.getBranchId());
//				Iterator<String> keys = isJsonObject.keys();
//				while (keys.hasNext()) {
//					String key = keys.next();
//					System.out.println(key);
//					JSONArray msg = (JSONArray) isJsonObject.get(key);
//					branchID.add(new Integer(key.toString()));
//					for (int i = 0; i < msg.length(); i++) {
//						roomID.add(Integer.parseInt(msg.get(i).toString()));
//					}
//				}
//			}
//			TblLoanSponsorMapp lisResContractList = dbFintechHome.getLoanRequet(139, 11);
//			System.out.println(lisResContractList.getLoanId());
//			System.out.println(lisResContractList.getDisbursementDate().compareTo(sm.parse(sm.format(new Date()))));
//			int aaa = lisResContractList.getDisbursementDate().compareTo(sm.parse(sm.format(new Date())));
//			if(aaa < 0){
//				System.out.println("a");
//			}else{
//				System.out.println("b");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		 List<Integer> list1 = new ArrayList<>();
//	      list1.add(new Integer(41));
//	      list1.add(new Integer(42));
//	      
//	      List<Integer> list = new ArrayList<>();
//	      list.add(new Integer(18));
//	      list.add(new Integer(19));
		
//		for (ResContractList resContractList : lisResContractList) {
//			System.out.println(resContractList.getLoan_code());
//		}
//		
//	      System.out.println(list);
//		boolean aaaa = dbFintechHome.deleteTblImages(48);
//		System.out.println("a: "+ aaaa);
	}
}
