package ua.org.oa.grinchenkoa.webusers.springController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.org.oa.grinchenkoa.webusers.commands.Command;
import ua.org.oa.grinchenkoa.webusers.commands.EditDeleteCommand;
import ua.org.oa.grinchenkoa.webusers.commands.LoginCommand;
import ua.org.oa.grinchenkoa.webusers.commands.NoCommand;
import ua.org.oa.grinchenkoa.webusers.commands.RegCommand;
import ua.org.oa.grinchenkoa.webusers.commands.UpdateCommand;
import ua.org.oa.grinchenkoa.webusers.managers.ConfigurationManager;
import ua.org.oa.grinchenkoa.webusers.managers.MessageManager;

/**
 * Spring Controller
 * 
 * 
 * @author Andrei Grinchenko
 *
 */

@Controller
public class SpringController {

	
	private LoginCommand loginCommand;
	private RegCommand regCommand;
	private EditDeleteCommand editDeleteCommand;
	private UpdateCommand updateCommand;
	private NoCommand noCommand;
	private HashMap<String, Command> commands = new HashMap<>();
	
	private void fillingCommandsMap() {
		commands.put("login", loginCommand);
		commands.put("registration", regCommand);
		commands.put("editdelete", editDeleteCommand);
		commands.put("updating", updateCommand);
	}
	@Autowired
	public SpringController(LoginCommand loginCommand, RegCommand regCommand,
			EditDeleteCommand editDeleteCommand, UpdateCommand updateCommand, 
			NoCommand noCommand) {
		this.loginCommand = loginCommand;
		this.regCommand = regCommand;
		this.editDeleteCommand = editDeleteCommand;
		this.updateCommand = updateCommand;
		this.noCommand = noCommand;
		fillingCommandsMap();
	}

	@RequestMapping(value="/Controller", method={RequestMethod.POST, RequestMethod.GET})
		public ModelAndView authentication(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		String page = null;
		try {
			Command command = commands.get(request.getParameter("command"));
			if (command == null) {
				command = noCommand;
			}
			page = command.execute(request, response);  //getting the page for response
		} catch (SQLException e) {
			e.printStackTrace();
			/*setting SQL Error Message into request*/
			request.setAttribute("errorMessage", MessageManager.getInstance().getMessage(
					MessageManager.SQL_EXCEPTION_ERROR_MESSAGE));
			page = ConfigurationManager.getInstance().getProperty(
					ConfigurationManager.ERROR_PAGE_PATH);
		} catch (ServletException e) {
			e.printStackTrace();
			/*setting Servlet Error Message into request*/
			request.setAttribute("errorMessage", MessageManager.getInstance().getMessage(
					MessageManager.SERVLET_EXCEPTION_ERROR_MESSAGE));
			page = ConfigurationManager.getInstance().getProperty(
					ConfigurationManager.ERROR_PAGE_PATH);
		} catch (IOException e) {
			e.printStackTrace();
			/*setting IO Error Message into request*/
			request.setAttribute("errorMessage", MessageManager.getInstance().getMessage(
							MessageManager.IO_EXCEPTION_ERROR_MESSAGE));
			page = ConfigurationManager.getInstance().getProperty(
					ConfigurationManager.ERROR_PAGE_PATH);
		}
		mav.setViewName(page);
		return mav;
	}
}
