package ua.org.oa.grinchenkoa.webusers.services;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ua.org.oa.grinchenkoa.webusers.dao.Dao;
import ua.org.oa.grinchenkoa.webusers.dao.DaoRole;
import ua.org.oa.grinchenkoa.webusers.dao.DaoUser;
import ua.org.oa.grinchenkoa.webusers.entities.Adress;
import ua.org.oa.grinchenkoa.webusers.entities.MusicType;
import ua.org.oa.grinchenkoa.webusers.entities.User;
import ua.org.oa.grinchenkoa.webusers.managers.ConfigurationManager;
import ua.org.oa.grinchenkoa.webusers.managers.MessageManager;

/**
 * Utility for user validation
 * 
 * 
 * @author Andrei Grinchenko
 *
 */

public class Validations {
	
	/**
	 * private Constructor, unable to create instance outside
	 */
		private Validations() {}

		/**
		 * 
		 * @param login User's login
		 * @return true if login valid
		 */
		private static boolean validLogin(String login) {
			Pattern pattern = Pattern.compile("[a-zA-Z][a-zA-Z0-9_-]{5,14}");
			Matcher matcher = pattern.matcher(login.trim());
			return matcher.matches();
		}
		
		/**
		 * 
		 * @param email User's e-mail
		 * @return true if e-mail valid
		 */
		private static boolean validEmail(String email) {
			Pattern pattern = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
			Matcher matcher = pattern.matcher(email);
			return matcher.matches();
		}
		
		/**
		 * 
		 * @param user User
		 * @param conPassword inputed password confirmation
		 * @return errors' Map after validation
		 */
		private static Map<String,String> errorsMap(User user, String conPassword){
		         Map<String,String> errors = new LinkedHashMap<>();
		         if ("".equals(user.getLogin().trim())){
		             errors.put("emptyLogin", MessageManager.getInstance().getMessage(
		            		 MessageManager.EMPTY_LOGIN));
		         } else if (!validLogin(user.getLogin())) {
		        	 	errors.put("invalidLogin", MessageManager.getInstance().getMessage(
			            		 MessageManager.INVALID_LOGIN));
		         		}
		         
		         if ("".equals(user.getPassword())){
		             errors.put("emptyPassword", MessageManager.getInstance().getMessage(
		            		 MessageManager.EMPTY_PASSWORD));
		         } else if (!user.getPassword().equals(conPassword)) {
		        	 	errors.put("conPasswordFailed", MessageManager.getInstance().getMessage(
			            		 MessageManager.CONFIRMATION_PASSWORD_FAILED));
		         }
		        
		         if ("".equals(user.getEmail())){
		             errors.put("emptyEmail", MessageManager.getInstance().getMessage(
		            		 MessageManager.EMPTY_EMAIL));
		         } else if (!validEmail(user.getEmail())) {
		        	 	errors.put("invalidEmail", MessageManager.getInstance().getMessage(
			            		 MessageManager.INVALID_EMAIL));
		         }
		         return errors;
		     }
		
		/**
		 * 
		 * @param request HttpServletRequest
		 * @param response HttpServletResponse
		 * @param user Constructing user
		 * @param adress Constructing address
		 * @param errors Errors' map
		 * @param dao Dao
		 * @param daoRole DaoRole
		 * @param daoUser DaoUser
		 * @return true if valid User, false if invalid
		 * @throws ServletException
		 * @throws IOException
		 * @throws SQLException
		 */
		public static boolean validUser(HttpServletRequest request,
				HttpServletResponse response, User user, Adress adress, Map<String,String> errors,
				Dao dao, DaoRole daoRole, DaoUser daoUser) throws ServletException, IOException, SQLException {
			
			user.setRole(daoRole.read(ConfigurationManager.getInstance().getProperty(
					ConfigurationManager.USER)));
			user.setLogin(request.getParameter("login"));
			user.setPassword(request.getParameter("password"));
			user.setBirthDate(new Date(new GregorianCalendar(Integer.valueOf(request.getParameter("birthYear")),
													 Integer.valueOf(request.getParameter("birthMonth")) - 1,
													 Integer.valueOf(request.getParameter("birthDay"))).getTimeInMillis()));
			user.setEmail(request.getParameter("email"));
			Set<MusicType> checkedMusicTypes = new HashSet<>();
			for (MusicType musicType : dao.readAll(MusicType.class)) {
				if ("on".equals(request.getParameter(musicType.getMusicType()))) {
					checkedMusicTypes.add(musicType);
					
				}
			}
			user.setMusicTypes(checkedMusicTypes);
			adress.setCountry(request.getParameter("country"));
			adress.setRegion(request.getParameter("region"));
			adress.setCity(request.getParameter("city"));
			adress.setStreet(request.getParameter("street"));
			adress.setBuilding(request.getParameter("building"));
			adress.setApp(request.getParameter("app"));
			adress.setUser(user);
			
			/*if such user's login exists return false
			 * but if updating in context login could be also updated*/
			if (daoUser.contains(user.getLogin()) && 
					request.getSession().getAttribute("update") == null) {
				errors.put("Exists", MessageManager.getInstance().getMessage(
						MessageManager.LOGIN_EXISTS_ERROR_MESSAGE));
				return false;
			}
			else {
				errors.putAll(errorsMap(user, request.getParameter("conPassword"))); //filling errors' map
				/*if errors' map is empty - validating passed*/
				if (errors.isEmpty()) {
					return true;
				}
				return false;
			}
		}
}
