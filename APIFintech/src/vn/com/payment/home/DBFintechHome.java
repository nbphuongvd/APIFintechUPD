package vn.com.payment.home;

import java.util.ArrayList;
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

import vn.com.payment.config.LogType;
import vn.com.payment.entities.Permmission;
import vn.com.payment.entities.TblImages;
import vn.com.payment.entities.TblLoanBill;
import vn.com.payment.entities.TblLoanExpertiseSteps;
import vn.com.payment.entities.TblLoanReqDetail;
import vn.com.payment.entities.TblLoanRequest;
import vn.com.payment.entities.TblLoanRequestAskAns;
import vn.com.payment.object.ResContractList;
import vn.com.payment.ultities.FileLogger;

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
	
	public List<ResContractList> listResContractList(List<Integer> branchID, List<Integer> roomID) {
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
								+ "lr.roomId, lr.branchId "
								+ "FROM TblLoanReqDetail ld INNER JOIN TblLoanRequest lr ON lr.loanId = ld.loanId "
								+ "Where "
								+ "lr.roomId in :listRoom and "
								+ "lr.branchId in :listbranchId";
//								+ "lr.loanCode =:loanCode and ld.borrowerId =:borrowerId and "
//								+ "lr.branchId =: branchID and "
//								+ "lr.roomId in listRoom"; 
			System.out.println("sql: "+ sql);
			session = HibernateUtil.getSessionFactory().openSession();
			Query query = session.createQuery(sql);
			query.setParameter("listbranchId", branchID);
			query.setParameter("listRoom", roomID);
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
					reContractList.setBranch_id(Long.parseLong(row[9]+""));
					reContractList.setRoom_code(Long.parseLong(row[10]+""));
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
	
	public static void main(String[] args) {

		 List<Integer> list1 = new ArrayList<>();
	      list1.add(new Integer(41));
	      list1.add(new Integer(42));
	      
	      List<Integer> list = new ArrayList<>();
	      list.add(new Integer(18));
	      list.add(new Integer(19));
		DBFintechHome dbFintechHome = new DBFintechHome();
//		String a = "'18','19'";
		List<ResContractList> lisResContractList = dbFintechHome.listResContractList(list1, list);
		for (ResContractList resContractList : lisResContractList) {
			System.out.println(resContractList.getLoan_code());
		}
		
	      System.out.println(list);
	}
}
