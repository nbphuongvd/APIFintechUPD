package vn.com.payment.home;
// Generated May 19, 2021 11:20:56 PM by Hibernate Tools 3.5.0.Final

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import vn.com.payment.entities.TblImages;

/**
 * Home object for domain model class TblImages.
 * @see vn.com.payment.entities.TblImages
 * @author Hibernate Tools
 */
@Stateless
public class TblImagesHome extends BaseSqlHomeDao{

	private static final Log log = LogFactory.getLog(TblImagesHome.class);

	@PersistenceContext
	private EntityManager entityManager;

	public void persist(TblImages transientInstance) {
		log.debug("persisting TblImages instance");
		try {
			entityManager.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void remove(TblImages persistentInstance) {
		log.debug("removing TblImages instance");
		try {
			delete(persistentInstance);
			log.debug("remove successful");
		} catch (Exception re) {
			System.out.println("xxx");
			log.error("remove failed", re);
			re.printStackTrace();
		}
	}

	public TblImages merge(TblImages detachedInstance) {
		log.debug("merging TblImages instance");
		try {
			TblImages result = entityManager.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TblImages findById(Long id) {
		log.debug("getting TblImages instance with id: " + id);
		try {
			TblImages instance = entityManager.find(TblImages.class, id);
			log.debug("get successful");
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	public static void main(String[] args) {
		TblImagesHome tblImagesHome = new TblImagesHome();
		TblImages tblImages = new TblImages();
		tblImages.setImageId(5l);
		tblImages.setLoanRequestDetailId(46);
		System.out.println(tblImages);
		tblImagesHome.remove(tblImages);
	}
}
