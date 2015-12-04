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

@Service
public class UpdateCommand implements Command {

	@Autowired
	private DaoUser daoUser;
	
	@Autowired
	private DaoRole daoRole;
	
	@Autowired
	private Dao dao;
	
	
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			SQLException {
		/*getting updating user from the context*/
		User updatingUser = (User)request.getSession().getAttribute("constructingUser");
		/*getting updating adress from the context*/
		Adress updatingAdress = (Adress)request.getSession().getAttribute("constructingAdress");
		String page = null;
		Map<String,String> errors = new LinkedHashMap<>();  //errors' Map(key - reason, value - message)
		/*if user could be updated(validation OK)*/
		if (Validations.validUser(request, response, 
				updatingUser, updatingAdress, errors, dao, daoRole, daoUser)) {
			daoUser.update(updatingUser);  //updating user in database
			dao.update(updatingAdress);  //updating adress in database
			/*getting new list of all information about users from DB and setting attribute into context*/
			request.setAttribute("userList", daoUser.readAllSorted());
			/*admin's page*/
			page = ConfigurationManager.getInstance().getProperty(  
					ConfigurationManager.MAIN_ADMIN_PAGE_PATH);
		} 
		/*if validation failed - setting attributes for JSP*/
		else { 
			request.setAttribute("constructingUser", updatingUser);
			request.setAttribute("constructingAdress", updatingAdress);
			request.setAttribute("errors", errors);
			/*updating page*/
			page = ConfigurationManager.getInstance().getProperty(
						ConfigurationManager.UPDATE_PAGE_PATH);
		}
		return page;
		
	}
}
