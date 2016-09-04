package by.epam.karotki.film_rating.command.impl;

//import java.io.ByteArrayOutputStream;
//import java.io.FileOutputStream;
import java.io.IOException;
//import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.karotki.film_rating.command.Command;
import by.epam.karotki.film_rating.entity.Account;
import by.epam.karotki.film_rating.service.AccountService;
import by.epam.karotki.film_rating.service.ServiceFactory;
import by.epam.karotki.film_rating.service.exception.ServiceException;

public class Registration implements Command {
	private static final String LOGIN = "login";
	private static final String PASSWORD = "pass";
	private static final String FIRST_NAME = "first-name";
	private static final String LAST_NAME = "last-name";
	private static final String BIRTHDAY = "birthday";
	private static final String COUNTRY = "country";
	private static final String EMAIL = "email";
	private static final String PHONE_NUMBER = "phone-number";
	private static final String AVATAR = "file";
	private static final String ERROR_PAGE = "error.jsp";
	private static final String ACCOUNT = "account";
	private static final String PROFILE_PAGE = "/WEB-INF/jsp/user-profile.jsp";
	//private static final String INDEX_PAGE = "index.jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Map<String,String> reqParam = new HashMap<String,String>();
		reqParam.put(LOGIN, request.getParameter(LOGIN));
		reqParam.put(PASSWORD, request.getParameter(PASSWORD));
		reqParam.put(FIRST_NAME, request.getParameter(FIRST_NAME));
		reqParam.put(LAST_NAME, request.getParameter(LAST_NAME));
		reqParam.put(BIRTHDAY,request.getParameter(BIRTHDAY));
		reqParam.put(COUNTRY,request.getParameter(COUNTRY));
		reqParam.put(EMAIL,request.getParameter(EMAIL));
		reqParam.put(PHONE_NUMBER,request.getParameter(PHONE_NUMBER));
		reqParam.put(AVATAR,request.getParameter(AVATAR));
		
/*	InputStream is = request.getPart(AVATAR).getInputStream();
		ByteArrayOutputStream bAOS = new ByteArrayOutputStream();
		 byte buf[] = new byte[8192];
		    int qt = 0;
		    while ((qt = is.read(buf)) != -1) {
		      bAOS.write(buf, 0, qt);
		    }
		 FileOutputStream fos = new FileOutputStream("\\WebContent\\images\\avatar\\ava.jpg");
		    */
		
		ServiceFactory factory = ServiceFactory.getInstance();
		AccountService aService = factory.getAccountService();
		try {
			Account account = aService.registration(reqParam);
			HttpSession session = request.getSession(true);
			session.setAttribute(ACCOUNT, account);
			request.getRequestDispatcher(PROFILE_PAGE).forward(request, response);
		} catch (ServiceException e) {
			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
		}

	}

}
