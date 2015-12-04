package ua.org.oa.grinchenkoa.webusers.dao;

import java.io.IOException;
import java.sql.SQLException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.org.oa.grinchenkoa.webusers.entities.Role;

/**
 * Class DaoRole extends Dao and add special operations for Role Entity
 * 
 * @see Dao
 * 
 * @author Andrei Grinchenko
 *
 */
@Repository
public class DaoRole extends Dao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	/**
	 * Getting Entity Role with Role name
	 * 
	 * @param roleName Role name
	 * @return Entity Role
	 * @throws SQLException
	 * @throws IOException
	 */
	public Role read(String roleName) throws SQLException, IOException  {
		Role role = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			role = (Role)session.createCriteria(Role.class).add(Restrictions.like("roleName", roleName)).uniqueResult();
		} catch (Exception e) {
		}
		finally {
			if ((session != null) && (session.isOpen()))
					session.close();
		}
		return role;
	}
		
	
	/**
	 * Getting Role's id with Role name
	 * 
	 * @param roleName Role name
	 * @return Entity Role's id
	 * @throws SQLException
	 * @throws IOException
	 */
	@Deprecated
	public int readId(String roleName) throws SQLException, IOException {
		Role role = null;
		Integer id = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			role = (Role)session.createCriteria(Role.class).add(Restrictions.like("roleName", roleName)).uniqueResult();
			id = role.getId();
		} finally {
			if ((session != null) && (session.isOpen()))
					session.close();
		}
		return id;
	}
}

