package by.epam.karotki.film_rating.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSession;

import by.epam.karotki.film_rating.command.Command;
import by.epam.karotki.film_rating.entity.Author;
import by.epam.karotki.film_rating.entity.Comment;
import by.epam.karotki.film_rating.entity.Country;
import by.epam.karotki.film_rating.entity.Film;
import by.epam.karotki.film_rating.entity.Genre;
import by.epam.karotki.film_rating.service.AuthorService;
import by.epam.karotki.film_rating.service.CommentService;
import by.epam.karotki.film_rating.service.CountryService;
import by.epam.karotki.film_rating.service.FilmService;
import by.epam.karotki.film_rating.service.GenreService;
import by.epam.karotki.film_rating.service.ServiceFactory;
import by.epam.karotki.film_rating.service.exception.ServiceException;

public class FilmCard implements Command {
	private static final String FILM = "film";
	private static final String COUNTRY_LIST = "country_list";
	private static final String GENRE_LIST = "genre_list";
	private static final String DIRECTORS_LIST = "directors_list";
	private static final String SCENARIO_WRITERS_LIST = "scenarioWriters_list";
	private static final String ACTORS_LIST = "actors_list";
	private static final String COMMENT_LIST = "comment_list";
	private static final String FILM_CARD_PAGE = "/WEB-INF/jsp/film-card.jsp";
	private static final String ERROR_PAGE = "error.jsp";
	private static final String LOCALE = "locale";
	private static final String RU = "ru";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		int idFilm = Integer.valueOf(request.getParameter(FILM));
		HttpSession session = request.getSession(true);
		String locale = (String)session.getAttribute(LOCALE);
		if(locale == null || locale.isEmpty()){
			locale = RU;
		}
		
		ServiceFactory factory = ServiceFactory.getInstance();
		FilmService fService = factory.getFilmService();
		CountryService cService = factory.getCountryService();
		GenreService gService = factory.getGenreService();
		AuthorService aService = factory.getAuthorService();
		CommentService comService = factory.getCommentService();
		try {
			Film film = fService.getFilmById(idFilm,locale);
			request.setAttribute(FILM, film);
			
			List<Country> countryList = cService.getCountriesByFilm(idFilm,locale);
			request.setAttribute(COUNTRY_LIST, countryList);
			
			List<Genre> genreList = gService.getGenreListByFilm(idFilm, locale);
			request.setAttribute(GENRE_LIST, genreList);
			
			List<Author> directorList = aService.getDirectorsByFilm(idFilm,locale);
			request.setAttribute(DIRECTORS_LIST, directorList);
			
			List<Author> scenarioWritersList = aService.getScenarioWritersByFilm(idFilm, locale);
			request.setAttribute(SCENARIO_WRITERS_LIST, scenarioWritersList);
			
			List<Author> actorsList = aService.getActorByFilm(idFilm, locale);
			request.setAttribute(ACTORS_LIST, actorsList);
			
			List<Comment> commentList = comService.getCommentsByFilm(idFilm);
			request.setAttribute(COMMENT_LIST, commentList);
			
			request.getRequestDispatcher(FILM_CARD_PAGE).forward(request, response);
		} catch (ServiceException e) {
			//log
			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
		}
	}

}
