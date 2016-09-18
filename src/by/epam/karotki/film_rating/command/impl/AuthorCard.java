package by.epam.karotki.film_rating.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.karotki.film_rating.command.Command;
import by.epam.karotki.film_rating.entity.Author;
import by.epam.karotki.film_rating.entity.Country;
import by.epam.karotki.film_rating.entity.Film;
import by.epam.karotki.film_rating.service.AuthorService;
import by.epam.karotki.film_rating.service.CountryService;
import by.epam.karotki.film_rating.service.FilmService;
import by.epam.karotki.film_rating.service.ServiceFactory;
import by.epam.karotki.film_rating.service.exception.ServiceException;

public class AuthorCard implements Command {
	private static final String AUTHOR_ID = "author_id";
	private static final String AUTHOR = "author";
	private static final String COUNTRY = "country";
	private static final String DIRECTOR_FILMS = "directorFilms";
	private static final String SCENARIOWRITER_FILMS = "scenarioWriterFilms";
	private static final String ACTOR_FILMS = "actorFilms";
	private static final String AUTHOR_CARD_PAGE = "/WEB-INF/jsp/author-card.jsp";
	private static final String ERROR_PAGE = "error.jsp";
	private static final String LOCALE = "locale";
	private static final String RU = "ru";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		int idAuthor = 0;
		try{
		idAuthor = Integer.valueOf(request.getParameter(AUTHOR_ID));
		}catch(NumberFormatException e){
			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
		}
		HttpSession session = request.getSession(true);
		String locale = (String)session.getAttribute(LOCALE);
		if(locale == null || locale.isEmpty()){
			locale = RU;
		}
		ServiceFactory factory = ServiceFactory.getInstance();
		AuthorService aService = factory.getAuthorService();
		CountryService cService = factory.getCountryService();
		FilmService fService = factory.getFilmService();
		try {
			Author author = aService.getAuthorById(idAuthor,locale);
			request.setAttribute(AUTHOR, author);
			
			if (author != null) {
				Country country = cService.getCountryById(author.getCountryOfBirthId(),locale);
				request.setAttribute(COUNTRY, country);
				
				List<Film> directorFilmList = fService.getFilmsByDirector(idAuthor, locale);
				request.setAttribute(DIRECTOR_FILMS, directorFilmList);
				
				List<Film> scenarioWriterFilmList = fService.getFilmsByScenarioWriter(idAuthor, locale);
				request.setAttribute(SCENARIOWRITER_FILMS, scenarioWriterFilmList);
				
				List<Film> actorFilmList = fService.getFilmsByActor(idAuthor, locale);
				request.setAttribute(ACTOR_FILMS, actorFilmList);
			}
			request.getRequestDispatcher(AUTHOR_CARD_PAGE).forward(request, response);
		} catch (ServiceException e) {
			//
			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
		}
	}

}
