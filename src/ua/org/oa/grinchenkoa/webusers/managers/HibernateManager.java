package ua.org.oa.grinchenkoa.webusers.managers;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
/**
 * 
 * @author Andrei Grinchenko
 * 
 * Util for getting Hibernate Session Factory
 *
 */

@SuppressWarnings("deprecation")
public class HibernateManager {
	
	private static SessionFactory sessionFactory;
	
	
	/**
	 * Private constructor, unable to create object outside
	 */
	private HibernateManager() {}
	
	
	/**
	 * Static initialization block, initialize sessionFactory field
	 */
	static {
		try {
			sessionFactory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable e) {
			throw new ExceptionInInitializerError(e);
		}
	}
	
	/**
	 * 
	 * @return Session Factory
	 */
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
