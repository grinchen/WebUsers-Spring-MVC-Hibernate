package ua.org.oa.grinchenkoa.webusers.commands;

import java.io.IOException;
import java.sql.SQLException;
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
import ua.org.oa.grinchenkoa.webusers.managers.MessageManager;

/**
 * Class implements EditDelete command
 * 
 * @author Andrei Grinchenko
 */
@Service
public class EditDeleteCommand implements Command {

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
		String page = null;
		/*getting user from DB with such id*/
		User editDeleteUser = (User) daoUser.read(Integer.parseInt(request.getParameter("editdeleteuserid")), User.class);
		/*if not admin(unable deleting or updating admin)*/
		if (! editDeleteUser.getRole().equals(daoRole.read(
				ConfigurationManager.getInstance().getProperty(
						ConfigurationManager.ADMIN)))) {
			Adress adress = editDeleteUser.getAdress(); //getting user's adress from DB
			/*creating record 'adress' in DB if doesn't exist*/
			if (adress == null) {
				adress = new Adress();
				adress.setUser(editDeleteUser);
				dao.create(adress);
			}
			adress.setUser(editDeleteUser);
			/*if "delete" in request*/ 
			if (request.getParameter("delete") != null) {  
				if (adress != null)
					dao.delete(adress);  //deleting user's adress from DB
				daoUser.delete(editDeleteUser);  //deleting user
				/*getting new list of all information about users from DB and setting attribute into request*/
				request.setAttribute("userList", daoUser.readAllSorted());
				/*admin's page*/
				page = ConfigurationManager.getInstance().getProperty(  
						ConfigurationManager.MAIN_ADMIN_PAGE_PATH);
			/*if edit in request*/
			} else if (request.getParameter("edit") != null) {
				/*setting editing user into context*/
				request.getSession().setAttribute("constructingUser", editDeleteUser);
				/*setting editing adress into context*/
				request.getSession().setAttribute("constructingAdress", adress);
				/*updating flag*/
				request.getSession().setAttribute("update", new Object());
				page = ConfigurationManager.getInstance().getProperty(
						ConfigurationManager.UPDATE_PAGE_PATH);
			/*if neither "delete" no "edit" in request - setting errorMessage*/
			} else {  
				/*setting error message into request*/
				request.setAttribute("errorMessage", MessageManager.getInstance().getMessage(
						MessageManager.INVALID_REQUEST));
				/*error page*/
				page = ConfigurationManager.getInstance().getProperty(
						ConfigurationManager.ERROR_PAGE_PATH);
			}
		/*unable edit or delete admin*/
		} else {
			/*getting list of all information about users from DB and setting into request*/
			request.setAttribute("userList", daoUser.readAllSorted());
			/*admin's page*/
			page = ConfigurationManager.getInstance().getProperty(
						ConfigurationManager.MAIN_ADMIN_PAGE_PATH);
		}
		return page;
	}

}
