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
	private static final String ROLE = "role";
	private static final String ACTIVE = "active";
	private static final String PHONE_NUMBER = "phone-number";
	private static final String AVATAR = "avatar";
	private static final String ERROR_PAGE = "error.jsp";
	private static final String ACCOUNT = "account";
	private static final String PROFILE_PAGE = "Controller?command=profile";
	private static final String PROJECT_PATH = "ProjectPath";
	private static final String ERROR_MESSAGE = "errorMessage";
	private static final String LOCALE = "locale";
	private static final String RU = "ru";
	private static final String PREV_PAGE = "prev_page";
	private static final String INDEX_PAGE = "index.jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(false);
		if(session==null){
			request.setAttribute(ERROR_MESSAGE, "Session wasn't found");
			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
			return;
		}
		String login = request.getParameter(LOGIN);
		Account account = (Account)session.getAttribute(ACCOUNT);
		if((!account.getLogin().equals(login))&&(!account.getRole().equals("Admin"))){
			request.setAttribute(ERROR_MESSAGE, "Not enough access");
			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
			return;
		}
		ServiceFactory factory = ServiceFactory.getInstance();
		AccountService aService = factory.getAccountService();
	/*	String login = request.getParameter(LOGIN);
		String pass = request.getParameter(PASSWORD);
		
		
		ServiceFactory factory = ServiceFactory.getInstance();
		AccountService aService = factory.getAccountService();
		Account accountCO = null;
		try{
			accountCO = aService.autorization(login, pass);
		}catch(AccountServiceException e ){
			request.setAttribute(ERROR_MESSAGE, "Account by login and password isn't exist");
			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
			return;
		}
		Account account = (Account)session.getAttribute(ACCOUNT);
		if(!accountCO.equals(account)){
	  				
	 		request.setAttribute(ERROR_MESSAGE, "wrong password");
			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
			return;
		}  
		*/
		
		Map<String, String> reqParam = new HashMap<String, String>();
		reqParam.put(LOGIN, request.getParameter(LOGIN));
		reqParam.put(PASSWORD, request.getParameter(PASSWORD));
		reqParam.put(FIRST_NAME, request.getParameter(FIRST_NAME));
		reqParam.put(LAST_NAME, request.getParameter(LAST_NAME));
		reqParam.put(BIRTHDAY, request.getParameter(BIRTHDAY));
		reqParam.put(COUNTRY, request.getParameter(COUNTRY));
		reqParam.put(EMAIL, request.getParameter(EMAIL));
		reqParam.put(ACTIVE, request.getParameter(ACTIVE));
		reqParam.put(PHONE_NUMBER, request.getParameter(PHONE_NUMBER));
		reqParam.put(ROLE, request.getParameter(ROLE));

		
		InputStream is = null;

			try {
				Part part = request.getPart(AVATAR);
			
				if ((part != null)&&(part.getSize()>0)) {
					is = part.getInputStream();
					String path = request.getServletContext().getRealPath("");
					reqParam.put(PROJECT_PATH, path);
				}

			} catch (ServletException e) {
				// log
			}
		Account updAccount = null;
		try{
			updAccount = aService.updateAccount(reqParam, is);
		}catch(AccountServiceException e){
			request.setAttribute(ERROR_MESSAGE, "Can't get updated account");
			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
				return;
		}
		if(account.getId() == updAccount.getId()){
		session.setAttribute(ACCOUNT, updAccount);
		response.sendRedirect(PROFILE_PAGE);
		}else{
			String locale = (String)session.getAttribute(LOCALE);
			if(locale == "" || locale.isEmpty()){
				locale = RU;
			}
			String prevPage = (String) session.getAttribute(PREV_PAGE);
			if (prevPage != null) {
				response.sendRedirect(prevPage);
				return;
			} else {
				request.getRequestDispatcher(INDEX_PAGE).forward(request, response);
			}
			response.sendRedirect(prevPage);
		}
	}

}
