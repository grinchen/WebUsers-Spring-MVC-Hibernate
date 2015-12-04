package ua.org.oa.grinchenkoa.webusers.commands;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.org.oa.grinchenkoa.webusers.dao.Dao;
import ua.org.oa.grinchenkoa.webusers.dao.DaoRole;
import ua.org.oa.grinchenkoa.webusers.dao.DaoUser;
import ua.org.oa.grinchenkoa.webusers.entities.Adress;
import ua.org.oa.grinchenkoa.webusers.entities.User;
import ua.org.oa.grinchenkoa.webusers.managers.ConfigurationManager;
import ua.org.oa.grinchenkoa.webusers.services.Validations;

/**
 * Class implements Registration command
 * 
 * @author Andrei Grinchenko
 * 
 */
@Service
public class RegCommand implements Command {
	
	@Autowired
	private DaoUser daoUser;
	
	@Autowired
	private DaoRole daoRole;
	
	@Autowired
	private Dao dao;
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException, SQLException {
		User constructingUser = new User();
		Adress constructingAdress = new Adress();
		String page = null;
		Map<String,String> errors = new LinkedHashMap<>();  //errors' Map(key - reason, value - message)
		/*if user could be created(validation OK and no such login in DB)*/
		if (Validations.validUser(request, response, 
				constructingUser, constructingAdress, errors, dao, daoRole, daoUser)) {
			daoUser.create(constructingUser);  //creating user in database
			dao.create(constructingAdress);  //creating adress in database
			request.getSession().setAttribute("user", constructingUser);  //setting user into context
			/*user's page*/
			page = ConfigurationManager.getInstance().getProperty(
					ConfigurationManager.MAIN_PAGE_PATH);
		} 
		/*if validation failed - setting attributes for JSP*/
		else { 
			request.setAttribute("constructingUser", constructingUser);
			request.setAttribute("constructingAdress", constructingAdress);
			request.setAttribute("errors", errors);
			/*registration page*/
			page = ConfigurationManager.getInstance().getProperty(
						ConfigurationManager.REG_PAGE_PATH);
		}
		return page;
	}
}
