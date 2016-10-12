package by.epam.karotki.film_rating.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.karotki.film_rating.command.Command;
import by.epam.karotki.film_rating.entity.Account;
import by.epam.karotki.film_rating.entity.Country;
import by.epam.karotki.film_rating.service.CountryService;
import by.epam.karotki.film_rating.service.ServiceFactory;
import by.epam.karotki.film_rating.service.exception.ServiceException;

public class Profile implements Command {
	private static final String PROFILE_PAGE = "/WEB-INF/jsp/user-profile.jsp";
	private static final String ERROR_PAGE = "error.jsp";
	private static final String ACCOUNT = "account";
	private static final String LOCALE = "locale";
	private static final String RU = "ru";
	private static final String COUNTRY = "country";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(false);
		if(session == null){
			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
		}
		
		Account account = (Account)session.getAttribute(ACCOUNT);
		
		if(account == null){
			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
		}
		
			String locale = (String)session.getAttribute(LOCALE);
			if(locale == null || locale.isEmpty()){
				locale = RU;
			}
			
		ServiceFactory sFactory = ServiceFactory.getInstance();	
		CountryService cService = sFactory.getCountryService();
			Country country = null;
		try{
			country = cService.getCountryById(account.getCountryId(), locale);
		} catch (ServiceException e){
			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
		}
		 
		request.setAttribute(COUNTRY, country);	
		request.getRequestDispatcher(PROFILE_PAGE).forward(request, response);

	}

}
