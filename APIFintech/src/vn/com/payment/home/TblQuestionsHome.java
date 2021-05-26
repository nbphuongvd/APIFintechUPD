package vn.com.payment.home;
// Generated May 27, 2021 12:09:14 AM by Hibernate Tools 3.5.0.Final

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
import vn.com.payment.entities.TblLoanRequest;
import vn.com.payment.entities.TblQuestions;
import vn.com.payment.ultities.FileLogger;

/**
 * Home object for domain model class TblQuestions.
 * @see vn.com.payment.entities.TblQuestions
 * @author Hibernate Tools
 */
@Stateless
public class TblQuestionsHome extends BaseSqlHomeDao{

	private static final Log log = LogFactory.getLog(TblQuestionsHome.class);

	@PersistenceContext
	private EntityManager entityManager;
	
	public TblQuestions getTblQuestions(int questionsID) {
		TblQuestions tblQuestions = null;
		Session session = null;
		boolean result = true;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Criteria crtProduct 	= session.createCriteria(TblQuestions.class);
			Criterion questions		= Restrictions.eq("questionId", questionsID);
			crtProduct.add(questions);
			@SuppressWarnings("unchecked")
			List<TblQuestions> listQuestions= crtProduct.list();
			if (listQuestions.size() > 0) {
				tblQuestions = listQuestions.get(0);
			}	
			return tblQuestions;
		} catch (Exception e) {
			FileLogger.log(" getProduct Exception "+ e, LogType.ERROR);
			e.printStackTrace();
		} finally {
			releaseSession(session);
		}
		return null;
	}
}
