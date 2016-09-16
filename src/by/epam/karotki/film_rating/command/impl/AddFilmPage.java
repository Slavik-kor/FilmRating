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
import by.epam.karotki.film_rating.entity.Author;
import by.epam.karotki.film_rating.entity.Country;
import by.epam.karotki.film_rating.entity.Genre;
import by.epam.karotki.film_rating.service.AuthorService;
import by.epam.karotki.film_rating.service.CountryService;
import by.epam.karotki.film_rating.service.GenreService;
import by.epam.karotki.film_rating.service.ServiceFactory;
import by.epam.karotki.film_rating.service.exception.ServiceException;

public class AddFilmPage implements Command {
	private static final String ADD_FILM_PAGE = "/WEB-INF/jsp/add-film.jsp";
	private static final String ERROR_PAGE = "error.jsp";
	private static final String ACCOUNT = "account";
	private static final String ADMIN = "Admin";
	private static final String ERROR_MESSAGE = "errorMessage";
	private static final String LOCALE = "locale";
	private static final String RU = "ru";
	private static final String COUNTRIES = "countryList";
	private static final String AUTHORS = "authorList";
	private static final String GENRES = "genreList";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(true);
		RequestDispatcher errorDispatcher = request.getRequestDispatcher(ERROR_PAGE);
		RequestDispatcher pageDispatcher = request.getRequestDispatcher(ADD_FILM_PAGE);
		
		String locale = (String)session.getAttribute(LOCALE);
		
		if(locale == null || locale.isEmpty()){
			locale = RU;
		}
		Account account = null;
		if ((session != null) && ((account = (Account) session.getAttribute(ACCOUNT)) != null)) {
				String role = account.getRole();
				if (role.equals(ADMIN)) {
					ServiceFactory factory = ServiceFactory.getInstance();
					CountryService cService = factory.getCountryService();
					AuthorService aService = factory.getAuthorService();
					GenreService gService = factory.getGenreService();
					try{
						List<Country> countryList = cService.getAllCountries(locale);
						request.setAttribute(COUNTRIES, countryList);
						
						List<Author> authorList = aService.getAllAuthors(locale);
						request.setAttribute(AUTHORS, authorList);

						List<Genre> genreList = gService.getAllGenres(locale);
						request.setAttribute(GENRES, genreList);
						
					}catch(ServiceException e){
						request.setAttribute(ERROR_MESSAGE, "error");
						errorDispatcher.forward(request, response);
						
					}
					pageDispatcher.forward(request, response);
				} else {
					request.setAttribute(ERROR_MESSAGE, "Not enough access right");
					errorDispatcher.forward(request, response);
				}
		} else {
			request.setAttribute(ERROR_MESSAGE, "Not enough access right. It's needed authorization");
			errorDispatcher.forward(request, response);
		}

	}

}
