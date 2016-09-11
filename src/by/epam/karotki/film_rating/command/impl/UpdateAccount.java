package by.epam.karotki.film_rating.command.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import by.epam.karotki.film_rating.command.Command;
import by.epam.karotki.film_rating.entity.Account;
import by.epam.karotki.film_rating.service.AccountService;
import by.epam.karotki.film_rating.service.ServiceFactory;
import by.epam.karotki.film_rating.service.exception.AccountServiceException;

public class UpdateAccount implements Command {
	private static final String LOGIN = "login";
	private static final String PASSWORD = "pass";
	private static final String FIRST_NAME = "first-name";
	private static final String LAST_NAME = "last-name";
	private static final String BIRTHDAY = "birthday";
	private static final String COUNTRY = "country";
	private static final String EMAIL = "email";
	private static final String PHONE_NUMBER = "phone-number";
	private static final String AVATAR = "avatar";
	private static final String ERROR_PAGE = "error.jsp";
	private static final String ACCOUNT = "account";
	private static final String PROFILE_PAGE = "Controller?command=profile";
	private static final String PROJECT_PATH = "ProjectPath";
	private static final String ERROR_MESSAGE = "errorMessage";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		HttpSession session = request.getSession(false);
		if(session==null){
			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
			return;
		}
		String login = request.getParameter(LOGIN);
		String pass = request.getParameter(PASSWORD);
		
		ServiceFactory factory = ServiceFactory.getInstance();
		AccountService aService = factory.getAccountService();
		Account account = null;
		try{
			account = aService.autorization(login, pass);
		}catch(AccountServiceException e ){
			request.setAttribute(ERROR_MESSAGE, "Account by login and password isn't exist");
			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
			return;
		}
	/*	if(account==null){
	  				
	 		request.setAttribute(ERROR_MESSAGE, "Account by login and password isn't exist");
			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
			return;
		}  */
		
		Map<String, String> reqParam = new HashMap<String, String>();
		reqParam.put(LOGIN, login);
		reqParam.put(FIRST_NAME, request.getParameter(FIRST_NAME));
		reqParam.put(LAST_NAME, request.getParameter(LAST_NAME));
		reqParam.put(BIRTHDAY, request.getParameter(BIRTHDAY));
		reqParam.put(COUNTRY, request.getParameter(COUNTRY));
		reqParam.put(EMAIL, request.getParameter(EMAIL));
		reqParam.put(PHONE_NUMBER, request.getParameter(PHONE_NUMBER));
		
		InputStream is = null;

			try {
				Part part = request.getPart(AVATAR);
			//	System.out.println(part);
				if (part != null) {
					is = part.getInputStream();
					String path = request.getServletContext().getRealPath("");
					reqParam.put(PROJECT_PATH, path);
				}

			} catch (ServletException e) {
				// log
				System.out.println("exception");
			}
		
			
		System.out.println("before update");
		
		try{
			account = aService.updateAccount(reqParam, is);
		}catch(AccountServiceException e){
			request.setAttribute(ERROR_MESSAGE, "Can't get updated account");
			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
				return;
		}
		System.out.println("after update");
		System.out.println(account);
		session.setAttribute(ACCOUNT, account);
		response.sendRedirect(PROFILE_PAGE);
		
	}

}
