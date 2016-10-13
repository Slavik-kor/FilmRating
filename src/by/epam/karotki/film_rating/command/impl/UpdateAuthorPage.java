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
import by.epam.karotki.film_rating.entity.Film;
import by.epam.karotki.film_rating.service.CountryService;
import by.epam.karotki.film_rating.service.FilmService;
import by.epam.karotki.film_rating.service.ServiceFactory;
import by.epam.karotki.film_rating.service.exception.ServiceException;

public class UpdateAuthorPage implements Command {
	private static final String ERROR_PAGE = "error.jsp";
	private static final String ACCOUNT = "account";
	private static final String ADMIN = "Admin";
	private static final String ERROR_MESSAGE = "errorMessage";
	private static final String LOCALE = "locale";
	private static final String RU = "ru";
	private static final String UPDATE_ACCOUNT_PAGE = "/WEB-INF/jsp/add-author.jsp";
	private static final String COUNTRIES = "countryList";
	private static final String FILMS = "filmList";
	private static final String MODE = "mode";
	private static final String UPDATE = "update";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(false);
		RequestDispatcher errorDispatcher = request.getRequestDispatcher(ERROR_PAGE);
		if (session == null) {
			request.setAttribute(ERROR_MESSAGE, "It's needed authorization");
			errorDispatcher.forward(request, response);
			return;
		}
		Account account = (Account) session.getAttribute(ACCOUNT);
		if ((account == null) || (!account.getRole().equals(ADMIN))) {
			request.setAttribute(ERROR_MESSAGE, "Not enough access right or needed authorization");
			errorDispatcher.forward(request, response);
			return;
		}
		
		String locale = (String)session.getAttribute(LOCALE);
		if(locale == null || locale.isEmpty()){
			locale = RU;
		}
		ServiceFactory factory = ServiceFactory.getInstance();
		CountryService cService = factory.getCountryService();
		FilmService fService = factory.getFilmService();
		try{
			List<Country> countryList = cService.getAllCountries(locale);
			request.setAttribute(COUNTRIES, countryList);
			
			List<Film> filmList = fService.getAllFilms(locale);
			request.setAttribute(FILMS, filmList);
		}catch(ServiceException e){
			errorDispatcher.forward(request, response);
		}
		
		request.setAttribute(MODE, UPDATE);
		request.getRequestDispatcher(UPDATE_ACCOUNT_PAGE).forward(request, response);
	} 
		
		
	}
