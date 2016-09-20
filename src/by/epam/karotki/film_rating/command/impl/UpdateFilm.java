package by.epam.karotki.film_rating.command.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import by.epam.karotki.film_rating.command.Command;
import by.epam.karotki.film_rating.entity.Account;
import by.epam.karotki.film_rating.entity.Film;
import by.epam.karotki.film_rating.service.AuthorService;
import by.epam.karotki.film_rating.service.CountryService;
import by.epam.karotki.film_rating.service.FilmService;
import by.epam.karotki.film_rating.service.GenreService;
import by.epam.karotki.film_rating.service.ServiceFactory;
import by.epam.karotki.film_rating.service.exception.ServiceException;

public class UpdateFilm implements Command {
	private static final String ERROR_PAGE = "error.jsp";
	private static final String ACCOUNT = "account";
	private static final String ADMIN = "Admin";
	private static final String ERROR_MESSAGE = "errorMessage";
	private static final String TITLE_RU = "title_ru";
	private static final String TITLE_EN = "title_en";
	private static final String TITLE = "title";
	private static final String DESCRIPTION_RU = "description_ru";
	private static final String DESCRIPTION_EN = "description_en";
	private static final String DESCRIPTION = "description";
	private static final String SITE = "site";
	private static final String BUDGET = "budget";
	private static final String BOX_OFFICE = "boxOffice";
	private static final String AUDIENCE = "audience";
	private static final String DURATION = "duration";
	private static final String RELEASE = "release";
	private static final String TEASER = "teaser";
	private static final String POSTER = "poster";
	private static final String FILM_ID = "idFilm";
	private static final String PROJECT_PATH = "ProjectPath";
	private static final String GENRE = "genre";
	private static final String DIRECTORS = "directors";
	private static final String SCENARIOS = "scenarios";
	private static final String ACTORS = "actors";
	private static final String COUNTRIES = "countries";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(false);
		RequestDispatcher errorDispatcher = request.getRequestDispatcher(ERROR_PAGE);
		if(session == null ){
			request.setAttribute(ERROR_MESSAGE, "It's needed authorization");
			errorDispatcher.forward(request, response);
			return;
		}
		Account account = (Account) session.getAttribute(ACCOUNT);
		if((account==null)||(!account.getRole().equals(ADMIN))){
			request.setAttribute(ERROR_MESSAGE, "Not enough access right. It's needed authorization");
			errorDispatcher.forward(request, response);
			return;
		}
		
		Map<String, String> updParam = new HashMap<String, String>();
		updParam.put(FILM_ID,  request.getParameter(FILM_ID));
		updParam.put(TITLE_RU,  request.getParameter(TITLE_RU));
		updParam.put(TITLE_EN,  request.getParameter(TITLE_EN));
		updParam.put(TITLE, request.getParameter(TITLE));
		updParam.put(DESCRIPTION_RU, request.getParameter(DESCRIPTION_RU));
		updParam.put(DESCRIPTION_EN, request.getParameter(DESCRIPTION_EN));
		updParam.put(DESCRIPTION, request.getParameter(DESCRIPTION));
		updParam.put(SITE, request.getParameter(SITE));
		updParam.put(BUDGET, request.getParameter(BUDGET));
		updParam.put(BOX_OFFICE, request.getParameter(BOX_OFFICE));
		updParam.put(AUDIENCE, request.getParameter(AUDIENCE));
		updParam.put(DURATION, request.getParameter(DURATION));
		updParam.put(RELEASE, request.getParameter(RELEASE));
		updParam.put(TEASER, request.getParameter(TEASER));
		updParam.put(POSTER, request.getParameter(POSTER));

		
		String path = request.getServletContext().getRealPath("");
		updParam.put(PROJECT_PATH,path);
		
		InputStream is = null;
		try{
		Part part = request.getPart(POSTER);
		if ((part != null)&&(part.getSize()>0)){
		is = part.getInputStream();
		}
		}catch(ServletException e){
			//log
		}
		
		String[] genreList = request.getParameterValues(GENRE);
		String[] directorList = request.getParameterValues(DIRECTORS);
		String[] scenarioList = request.getParameterValues(SCENARIOS);
		String[] actorList = request.getParameterValues(ACTORS);
		String[] countryList = request.getParameterValues(COUNTRIES);
		
		ServiceFactory factory = ServiceFactory.getInstance();
		FilmService fService = factory.getFilmService();
		GenreService gService = factory.getGenreService();
		AuthorService aService = factory.getAuthorService();
		CountryService cService = factory.getCountryService();
		Film film = null;
		try{
			film = fService.updateFilm(updParam, is);
			
			
			if(genreList!=null){
					gService.addGenreToFilm(film.getId(), genreList);
			}
			
			if(directorList!=null){
					aService.addDirectorToFilm(film.getId(),directorList);
			}
			
			if(scenarioList!=null){
					aService.addScenarioToFilm(film.getId(), scenarioList);
			}
			if(actorList!=null){
					aService.addActorToFilm(film.getId(), actorList);
			}
			
			if(countryList!=null){
					cService.addCountryToFilm(film.getId(), countryList);
			}
		}catch(ServiceException e){
			errorDispatcher.forward(request, response);
			return;
		}
		
	}

}
