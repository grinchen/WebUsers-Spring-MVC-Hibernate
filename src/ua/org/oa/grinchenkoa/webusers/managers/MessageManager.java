package ua.org.oa.grinchenkoa.webusers.managers;

import java.util.ResourceBundle;

/**
 * 
 * Class extract messages from file
 * 
 * @author Andrei Grinchenko
 * 
 */
public class MessageManager {
	
	/**
	 * MessageManager instance
	 */
	private static MessageManager instance;
	private ResourceBundle resourseBundle;
	
	/**
	 * Property's file
	 */
	private static final String BUNDLE_NAME = "messages";
	public static final String LOGIN_ERROR_MESSAGE = "LOGIN_ERROR_MESSAGE";
	public static final String SERVLET_EXCEPTION_ERROR_MESSAGE = "SERVLET_EXCEPTION_ERROR_MESSAGE";
	public static final String IO_EXCEPTION_ERROR_MESSAGE = "IO_EXCEPTION_ERROR_MESSAGE";
	public static final String SQL_EXCEPTION_ERROR_MESSAGE= "SQL_EXCEPTION_ERROR_MESSAGE";
	public static final String LOGIN_EXISTS_ERROR_MESSAGE = "LOGIN_EXISTS_ERROR_MESSAGE";
	public static final String EMPTY_LOGIN = "EMPTY_LOGIN";
	public static final String INVALID_LOGIN = "INVALID_LOGIN";
	public static final String EMPTY_PASSWORD = "EMPTY_PASSWORD";
	public static final String CONFIRMATION_PASSWORD_FAILED = "CONFIRMATION_PASSWORD_FAILED";
	public static final String EMPTY_EMAIL = "EMPTY_EMAIL";
	public static final String INVALID_EMAIL = "INVALID_EMAIL";
	public static final String INVALID_REQUEST = "INVALID_REQUEST";
	
	
	/**
	 * Private constructor, unable to create object outside
	 */
	private MessageManager() {}
	
	/**
	 * 
	 * @return single MessageManager instance
	 */
	public static MessageManager getInstance() {
		if (instance == null) {
			instance = new MessageManager();
			instance.resourseBundle = ResourceBundle.getBundle(BUNDLE_NAME);
		}
		return instance;
	}
	
	/**
	 * Getting message with such key
	 * 
	 * @param key
	 * @return message
	 */
	public String getMessage(String key) {
		return resourseBundle.getString(key);
	}
}
