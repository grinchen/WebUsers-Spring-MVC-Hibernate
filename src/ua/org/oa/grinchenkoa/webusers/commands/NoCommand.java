package ua.org.oa.grinchenkoa.webusers.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import ua.org.oa.grinchenkoa.webusers.managers.ConfigurationManager;

/**
 * Class implements NoCommand
 * 
 * In case of the direct appeal to the controller - redirection
 * to the login page
 * 
 * @author Andrei Grinchenko
 */
@Service
public class NoCommand implements Command {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		
		return ConfigurationManager.getInstance().getProperty(ConfigurationManager.LOGIN_PAGE_PATH);
	}

}
