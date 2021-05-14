package vn.com.payment.home;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.SessionFactoryUtils;

public class HibernateUtil extends SessionFactoryUtils{

	private static final SessionFactory sessionFactory = buildSessionFactory();

	@SuppressWarnings("deprecation")
	private static SessionFactory buildSessionFactory() {
		try {
			// return new Configuration().configure(
			// new File("./config/hibernate.cfg.xml"))
			// .buildSessionFactory();
			// return new Configuration().configure(
			// new File("./config/hikaricp.cfg.xml"))
			// .buildSessionFactory();
			return (SessionFactory) ServicesRegister.shareInstance().context
					.getBean("hibernate5AnnotatedSessionFactory");						
			// .buildSessionFactory(serviceRegistry);
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static void releaseSession()
	{		
	}
	
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void shutdown() {
		// Close caches and connection pools
		getSessionFactory().close();
	}
}