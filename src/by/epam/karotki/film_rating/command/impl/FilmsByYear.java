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

public class FilmsByYear implements Command {
	private static final String YEAR = "year";
	private static final String LOCALE = "locale";
	private static final String RU = "ru";
	private static final String ERROR_PAGE = "error.jsp";
	private static final String FILMS = "films";
	private static final String FILMS_PAGE = "/WEB-INF/jsp/films.jsp";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		int year = Integer.valueOf(request.getParameter(YEAR));
		HttpSession session = request.getSession(true);
		String locale = (String)session.getAttribute(LOCALE);
		if(locale == null || locale.isEmpty()){
			locale = RU;
		}
	
	
	ServiceFactory factory = ServiceFactory.getInstance();
	FilmService fService = factory.getFilmService();
	List<Film> filmList = null;
	try{
		filmList = fService.getFilmsByYear(year, locale);
		request.setAttribute(FILMS, filmList);
		request.getRequestDispatcher(FILMS_PAGE).forward(request, response);
	}catch(ServiceException e){
		request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
	}
	}
}
