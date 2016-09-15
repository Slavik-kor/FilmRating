package by.epam.karotki.film_rating.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.karotki.film_rating.command.Command;
import by.epam.karotki.film_rating.entity.Account;
import by.epam.karotki.film_rating.entity.Film;
import by.epam.karotki.film_rating.service.FilmService;
import by.epam.karotki.film_rating.service.ServiceFactory;
import by.epam.karotki.film_rating.service.exception.ServiceException;

public class FilmFavorite implements Command {
	private static final String ACCOUNT = "account";
	private static final String LOCALE = "locale";
	private static final String RU = "ru";
	private static final String FILMS = "films";
	private static final String FILMS_PAGE = "/WEB-INF/jsp/films.jsp";
	private static final String ERROR_PAGE = "error.jsp";
	private static final String FAVOR = "Controller?command=film_favorite";
	private static final String PREV_PAGE = "prev_page";


	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(true);
		String locale = (String) session.getAttribute(LOCALE);
		if (locale == null || locale.isEmpty()) {
			locale = RU;
		}
		Account account = (Account)session.getAttribute(ACCOUNT);
		
		ServiceFactory factory = ServiceFactory.getInstance();
		FilmService fService = factory.getFilmService();
		try {
			List<Film> films = fService.getFilmsByAccountRate(account.getId(), locale);
			session.setAttribute(PREV_PAGE, FAVOR);
			request.setAttribute(FILMS, films);
			request.getRequestDispatcher(FILMS_PAGE).forward(request, response);
		} catch (ServiceException e) {
			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
		}

	}

}
