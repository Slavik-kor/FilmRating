package by.epam.karotki.film_rating.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;

import by.epam.karotki.film_rating.command.Command;
import by.epam.karotki.film_rating.entity.Country;
import by.epam.karotki.film_rating.entity.Film;
import by.epam.karotki.film_rating.service.CountryService;
import by.epam.karotki.film_rating.service.FilmService;
import by.epam.karotki.film_rating.service.ServiceFactory;
import by.epam.karotki.film_rating.service.exception.ServiceException;

public class FilmCard implements Command {
	//private static final String LOCALE = "locale";
	private static final String FILM = "film";
	private static final String COUNTRY_LIST = "country_list";
	private static final String FILM_CARD_PAGE = "/WEB-INF/jsp/film-card.jsp";
	private static final String ERROR_PAGE = "error.jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		//HttpSession session = request.getSession(true);
		//String locale = (String) session.getAttribute(LOCALE);
		int idFilm = Integer.valueOf(request.getParameter(FILM));
		ServiceFactory factory = ServiceFactory.getInstance();
		FilmService fService = factory.getFilmService();
		CountryService cService = factory.getCountryService();
		try {
			Film film = fService.getFilmById(idFilm);
			request.setAttribute(FILM, film);
			List<Country> countryList = cService.getCountriesByFilm(idFilm);
			System.out.println(countryList);
			request.setAttribute(COUNTRY_LIST, countryList);
			request.getRequestDispatcher(FILM_CARD_PAGE).forward(request, response);
		} catch (ServiceException e) {
			e.printStackTrace();
			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
		}
	}

}
