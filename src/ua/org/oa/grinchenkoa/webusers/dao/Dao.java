package ua.org.oa.grinchenkoa.webusers.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ua.org.oa.grinchenkoa.webusers.entities.Entity;

/**
 * Class is used for abstraction and encapsulation CRUD operations for Entity.
 * 
 * @author Andrei Grinchenko
 *
 */

@Repository
public class Dao {

	@Autowired
	private SessionFactory sessionFactory;
	

		/**
		 * Creating new record in DB
		 * 
		 * @param obj Creating Entity's object
		 * @throws SQLException
		 * @throws IOException
		 */
		public void create(Entity obj) throws SQLException, IOException {
			Session session = null;
			try {
				session = sessionFactory.openSession();
				session.beginTransaction();
				session.save(obj);
				session.getTransaction().commit();
			}
			finally {
				if ((session != null) && (session.isOpen()))
					session.close();
			}
		}

		/**
		 * 
		 * Getting Entity's object from the DB with id
		 * @param id Entity's id
		 * @param cl Class of getting object
		 * @return Entity
		 * @throws SQLException
		 * @throws IOException
		 */
		@SuppressWarnings("unchecked")
		public <T extends Entity> Entity read(int id, Class<T> cl) throws SQLException, IOException {
			Session session = null;
			T obj = null;
			try {
				session = sessionFactory.openSession();
				obj = (T)session.get(cl, id);
			} 
			finally {
				if ((session != null) && (session.isOpen()))
						session.close();
			}
			return obj;
		}
		
		/**
		 * Getting list of all Entity's objects
		 * 
		 * @param cl Class of getting objects list's
		 * @return list of all Entity's objects
		 * @throws SQLException
		 * @throws IOException
		 */
		@SuppressWarnings("unchecked")
		public <T extends Entity> List<T> readAll(Class<T> cl) throws SQLException, IOException {
			List<T> list;
			Session session = null;
			try {
				session = sessionFactory.openSession();
				list = session.createCriteria(cl).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
			} 
			finally {
				if ((session != null) && (session.isOpen()))
						session.close();
			}
			return list;
		}
		
		/**
		 * Updating Entity's object in the DB
		 * 
		 * @param obj Updating Entity's object
		 * @throws SQLException
		 * @throws IOException
		 */
		public void update(Entity obj) throws SQLException, IOException {
			Session session = null;
			try {
				session = sessionFactory.openSession();
				session.beginTransaction();
				session.update(obj);
				session.getTransaction().commit();
			} 
			finally {
				if ((session != null) && (session.isOpen()))
					session.close();
			}
		}
		
		/**
		 * 
		 * Deleting Entity's object from the DB
		 * 
		 * @param obj Deleting Entity's object
		 * @throws SQLException
		 * @throws IOException
		 */
		public void delete(Entity obj) throws SQLException, IOException {
			Session session = null;
			try {
				session = sessionFactory.openSession();
				session.beginTransaction();
				session.delete(obj);
				session.getTransaction().commit();
			} 
			finally {
				if ((session != null) && (session.isOpen()))
					session.close();
			}
		}
}
