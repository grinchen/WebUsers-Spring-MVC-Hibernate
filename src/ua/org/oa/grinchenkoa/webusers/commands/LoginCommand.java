package ua.org.oa.grinchenkoa.webusers.commands;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.org.oa.grinchenkoa.webusers.dao.Dao;
import ua.org.oa.grinchenkoa.webusers.dao.DaoUser;
import ua.org.oa.grinchenkoa.webusers.entities.User;
import ua.org.oa.grinchenkoa.webusers.managers.ConfigurationManager;
import ua.org.oa.grinchenkoa.webusers.managers.MessageManager;

/**
 * Class implements Login command
 * 
 * @author Andrei Grinchenko
 * 
 */

@Service
public class LoginCommand implements Command {

	@Autowired
	private DaoUser daoUser;

	@Autowired
	private Dao dao;
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException, SQLException {
		String page = null;
		String login = request.getParameter("login"); //getting login from the request
		String password = request.getParameter("password"); //getting password from the request
		Integer id = daoUser.readId(login, password); //getting user's id, null if doesn't exist
		/*if exists*/
		if (id != null) { 	
			User user = (User) daoUser.read(id, User.class);  //reading user from DB
			request.getSession().setAttribute("user", user); //and setting one into session
			/*if user*/
			if (user.getRole().getRoleName().equalsIgnoreCase(       	
					ConfigurationManager.getInstance().getProperty("USER"))) {
				/*user's page*/
				page = ConfigurationManager.getInstance().getProperty(			  	
						ConfigurationManager.MAIN_PAGE_PATH);
			/*if moderator*/
			} else if (user.getRole().getRoleName().equalsIgnoreCase(	
					ConfigurationManager.getInstance().getProperty("MODERATOR"))) {
				/*getting list of all information about users from DB and setting attribute into request*/
				request.setAttribute("userList", daoUser.readAllSorted());
				/*moderator's page*/
				page = ConfigurationManager.getInstance().getProperty(				
						ConfigurationManager.MAIN_MODER_PAGE_PATH);
				}
				/*if admin*/
				else if (user.getRole().getRoleName().equalsIgnoreCase(
						ConfigurationManager.getInstance().getProperty("ADMIN"))){
					/*getting list of all information about users from DB and setting attribute into request*/
					request.setAttribute("userList", daoUser.readAllSorted());
					/*admin's page*/
					page = ConfigurationManager.getInstance().getProperty(				
							ConfigurationManager.MAIN_ADMIN_PAGE_PATH);
				}
		/*if user doesn't exists*/
		} else {	
			/*setting attributes*/
			request.setAttribute("login", login);
			request.setAttribute("errorMessage", MessageManager.getInstance().getMessage(
					MessageManager.LOGIN_ERROR_MESSAGE));
			/*returning to login page*/
			page = ConfigurationManager.getInstance().getProperty(	
					ConfigurationManager.LOGIN_PAGE_PATH);
		}
		return page;
	}

}
