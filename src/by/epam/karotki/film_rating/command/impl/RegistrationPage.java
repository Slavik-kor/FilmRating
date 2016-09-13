package by.epam.karotki.film_rating.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.karotki.film_rating.command.Command;
import by.epam.karotki.film_rating.entity.Country;
import by.epam.karotki.film_rating.service.CountryService;
import by.epam.karotki.film_rating.service.ServiceFactory;
import by.epam.karotki.film_rating.service.exception.ServiceException;

public class RegistrationPage implements Command {
	private static final String REG_PAGE = "/WEB-INF/jsp/registration.jsp";
	private static final String LOCALE = "locale";
	private static final String RU = "ru";
	private static final String COUNTRY_LIST = "countryList";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
			HttpSession session = request.getSession(true);
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
			request.getRequestDispatcher(REG_PAGE).forward(request, response);
	}

}
