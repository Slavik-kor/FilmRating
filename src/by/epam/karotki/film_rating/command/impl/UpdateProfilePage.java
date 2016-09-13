package by.epam.karotki.film_rating.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
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

public class UpdateProfilePage implements Command {
	private static final String ACCOUNT = "account";
	private static final String ERROR = "error.jsp";
	private static final String ACCOUNT_UPDATE_PAGE = "/WEB-INF/jsp/registration.jsp";
	private static final String MODE = "mode";
	private static final String UPDATE = "update";
	private static final String COUNTRY_LIST = "countryList";
	private static final String LOCALE = "locale";
	private static final String RU = "ru";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(false);
		RequestDispatcher errorDispatcher = request.getRequestDispatcher(ERROR);
		if (session == null) {
			errorDispatcher.forward(request, response);
		}
		Account account = (Account) session.getAttribute(ACCOUNT);
		if (account == null) {
			errorDispatcher.forward(request, response);
		}
		String locale = (String)session.getAttribute(LOCALE);
		if(locale == null || locale.isEmpty()){
			locale = RU;
		}
		ServiceFactory sFactory = ServiceFactory.getInstance();
		CountryService cService = sFactory.getCountryService();
		List<Country> countryList = null;
		try{
			countryList = cService.getAllCountries(locale);
		}catch(ServiceException e){
			//
		}
		
		request.setAttribute(COUNTRY_LIST, countryList);
		request.setAttribute(MODE, UPDATE);
		request.getRequestDispatcher(ACCOUNT_UPDATE_PAGE).forward(request, response);
	}

}
