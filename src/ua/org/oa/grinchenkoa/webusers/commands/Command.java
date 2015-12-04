package ua.org.oa.grinchenkoa.webusers.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.SQLException;

/**
 *  Interface Command define the specification of commands
 *  
 *  @author Andrei Grinchenko
 */
public interface Command {
	/**
	 * 
	 * @param request HttpServletRequest
	 * @param response  HttpServletResponse
	 * @return JSP page path for RequestDispatcher
	 * @throws ServletException
	 * @throws IOException
	 * @throws SQLException
	 */
	String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException;

}
