package by.epam.karotki.film_rating.command.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.karotki.film_rating.command.Command;
import by.epam.karotki.film_rating.entity.Account;
import by.epam.karotki.film_rating.entity.Author;
import by.epam.karotki.film_rating.entity.Country;
import by.epam.karotki.film_rating.entity.Film;
import by.epam.karotki.film_rating.entity.Genre;
import by.epam.karotki.film_rating.service.AuthorService;
import by.epam.karotki.film_rating.service.CountryService;
import by.epam.karotki.film_rating.service.FilmService;
import by.epam.karotki.film_rating.service.GenreService;
import by.epam.karotki.film_rating.service.ServiceFactory;
import by.epam.karotki.film_rating.service.exception.ServiceException;

public class UpdateFilmPage implements Command {
	private static final String ERROR_PAGE = "error.jsp";
	private static final String ACCOUNT = "account";
	private static final String ADMIN = "Admin";
	private static final String ERROR_MESSAGE = "errorMessage";
	private static final String LOCALE = "locale";
	private static final String RU = "ru";
	private static final String EN = "en";
	private static final String FILM = "idFilm";
	private static final String FILM_RU = "filmRu";
	private static final String FILM_EN = "filmEn";
	private static final String COUNTRIES = "countryList";
	private static final String AUTHORS = "authorList";
	private static final String GENRES = "genreList";
	private static final String DIRECTORS = "directors";
	private static final String SCENARIOS = "scenarios";
	private static final String ACTORS = "actors";
	private static final String MODE = "mode";
	private static final String UPDATE = "update";
	private static final String CURRENT_COUNTRIES = "currentCountries";
	private static final String CURRENT_GENRES = "currentGenres";
	private static final String FILM_UPDATE_PAGE = "/WEB-INF/jsp/add-film.jsp";

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
			request.setAttribute(ERROR_MESSAGE, "Not enough access right. It's needed authorization");
			errorDispatcher.forward(request, response);
			return;
		}

		String locale = (String) session.getAttribute(LOCALE);
		if (locale == null || locale.isEmpty()) {
			locale = RU;
		}

		Integer idFilm = Integer.valueOf(request.getParameter(FILM));

		ServiceFactory sFactory = ServiceFactory.getInstance();
		FilmService fService = sFactory.getFilmService();
		CountryService cService = sFactory.getCountryService();
		AuthorService aService = sFactory.getAuthorService();
		GenreService gService = sFactory.getGenreService();
		List<Country> countryList = null;
		List<Author> authorList = null;
		List<Genre> genreList = null;
		try {
			Film film_ru = fService.getFilmById(idFilm, RU);
			request.setAttribute(FILM_RU, film_ru);

			Film film_en = fService.getFilmById(idFilm, EN);
			request.setAttribute(FILM_EN, film_en);

			countryList = cService.getAllCountries(locale);
			request.setAttribute(COUNTRIES, countryList);

			List<Integer> countryListId = new ArrayList<Integer>();
			List<Country> cList = cService.getCountriesByFilm(idFilm, locale);
			if (cList != null) {
				for (int i = 0; i < cList.size(); i++) {
					Country country = cList.get(i);
					countryListId.add(Integer.valueOf(country.getId()));
				}
				request.setAttribute(CURRENT_COUNTRIES, countryListId);
			}

			genreList = gService.getAllGenres(locale);
			request.setAttribute(GENRES, genreList);

			List<Integer> genreListId = new ArrayList<Integer>();
			List<Genre> gList = gService.getGenreListByFilm(idFilm, locale);
			if (gList != null) {
				for (int i = 0; i < gList.size(); i++) {
					Genre genre = gList.get(i);
					genreListId.add(Integer.valueOf(genre.getId()));
				}
				request.setAttribute(CURRENT_GENRES, genreListId);
			}

			authorList = aService.getAllAuthors(locale);
			request.setAttribute(AUTHORS, authorList);

			List<Integer> directorListId = new ArrayList<Integer>();
			List<Author> directorList = aService.getDirectorsByFilm(idFilm, locale);
			if (directorList != null) {
				for (int i = 0; i < directorList.size(); i++) {
					Author director = directorList.get(i);
					directorListId.add(director.getId());
				}
				request.setAttribute(DIRECTORS, directorListId);
			}

			List<Integer> scenListId = new ArrayList<Integer>();
			List<Author> scenList = aService.getScenarioWritersByFilm(idFilm, locale);
			if (scenList != null) {
				for (int i = 0; i < scenList.size(); i++) {
					Author scen = scenList.get(i);
					scenListId.add(scen.getId());
				}
				request.setAttribute(SCENARIOS, scenListId);
			}

			List<Integer> actorListId = new ArrayList<Integer>();
			List<Author> actorList = aService.getActorByFilm(idFilm, locale);
			if (actorList != null) {
				for (int i = 0; i < actorList.size(); i++) {
					Author actor = actorList.get(i);
					actorListId.add(actor.getId());
				}
				request.setAttribute(ACTORS, actorListId);
			}

		} catch (ServiceException e) {
			request.setAttribute(ERROR_MESSAGE, "Can't get some data from DataBase");
			errorDispatcher.forward(request, response);
		}

		request.setAttribute(MODE, UPDATE);
		request.getRequestDispatcher(FILM_UPDATE_PAGE).forward(request, response);
	}

}
