package ua.org.oa.grinchenkoa.webusers.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.org.oa.grinchenkoa.webusers.entities.User;


/**
 *
 * Class DaoUser extends Dao and add special operations for User Entity
 * 
 * @see Dao
 * 
 * @author Andrei Grinchenko
 *
 */
@Repository
public class DaoUser extends Dao{

	@Autowired
	private SessionFactory sessionFactory;
	
	
	/**
	 * Getting sorted list of all User's objects
	 * 
	 * @return sorted list of all User's objects
	 * @throws SQLException
	 * @throws IOException
	 */
			@SuppressWarnings("unchecked")
			public List<User> readAllSorted() throws SQLException, IOException {
				List<User> list = new ArrayList<>();
				Session session = null;
				try {
					session = sessionFactory.openSession();
					list = session.createCriteria(User.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
					Collections.sort(list);
				} 
				finally {
					if ((session != null) && (session.isOpen()))
							session.close();
				} 
				return list;
			}
	
	/**
	 * Getting User's id with login and password
	 * 
	 * @param login User's login
	 * @return User's id
	 * @throws SQLException
	 * @throws IOException
	 */
	public Integer readId(String login, String password) throws SQLException, IOException  {
		User user = null;
		Integer id = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			user = (User)session.createCriteria(User.class).
			add(Restrictions.like("login", login)).
			add(Restrictions.like("password", password)).uniqueResult();
			if (user != null) id = user.getId();
		} finally {
			if ((session != null) && (session.isOpen()))
					session.close();
		}
		return id;
	}
	
	/**
	 * Checking if User exists with such login
	 * 
	 * @param login User's login
	 * @return true if exists, false if doesn't
	 * @throws SQLException
	 * @throws IOException
	 */
	public boolean contains(String login) throws SQLException, IOException  {
		User user = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			user = (User)session.createCriteria(User.class).
			add(Restrictions.like("login", login)).uniqueResult();
			if (user != null) return true;
		} 
		finally {
			if ((session != null) && (session.isOpen()))
					session.close();
		}
		return false;
	}
}
