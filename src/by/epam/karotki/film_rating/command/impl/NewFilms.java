package by.epam.karotki.film_rating.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.karotki.film_rating.command.Command;
import by.epam.karotki.film_rating.entity.Film;
import by.epam.karotki.film_rating.service.FilmService;
import by.epam.karotki.film_rating.service.ServiceFactory;
import by.epam.karotki.film_rating.service.exception.ServiceException;

public class NewFilms implements Command {
	private static final String VALUE = "value";
	private static final String LOCALE = "locale";
	private static final String RU = "ru";
	private static final String FILMS = "films";
	private static final String FILMS_PAGE = "/WEB-INF/jsp/films.jsp";
	private static final String ERROR_PAGE = "error.jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		int value = Integer.valueOf(request.getParameter(VALUE));
		HttpSession session = request.getSession(true);
		String locale = (String)session.getAttribute(LOCALE);
		if(locale == null || locale.isEmpty()){
			locale = RU;
		}
		ServiceFactory factory = ServiceFactory.getInstance();
		FilmService fService = factory.getFilmService();
		try {
			List<Film> films = fService.getFilmsByNewest(value,locale);
			request.setAttribute(FILMS, films);
			request.getRequestDispatcher(FILMS_PAGE).forward(request, response);
		} catch (ServiceException e) {
			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
		}
	}

}
