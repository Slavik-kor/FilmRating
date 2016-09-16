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

public class AddFilm implements Command {
	private static final String ERROR_PAGE = "error.jsp";
	//private static final String FILM = "film";
	private static final String FILM_CARD_PAGE = "Controller?command=film_Card&film=";
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
	private static final String PROJECT_PATH = "ProjectPath";
	private static final String RU = "ru";
	private static final String EN = "en";
	private static final String LOCALE = "locale";
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
			errorDispatcher.forward(request, response);
			return;
		}
		Account account = (Account) session.getAttribute(ACCOUNT);
		if((account==null)||(!account.getRole().equals(ADMIN))){
			request.setAttribute(ERROR_MESSAGE, "Not enough access right. It's needed authorization");
			errorDispatcher.forward(request, response);
			return;
		}
		
		Map<String,String> reqParam = new HashMap<String,String>();
		reqParam.put(TITLE, request.getParameter(TITLE_RU));
		reqParam.put(DESCRIPTION, request.getParameter(DESCRIPTION_RU));
		reqParam.put(SITE, request.getParameter(SITE));
		reqParam.put(BUDGET, request.getParameter(BUDGET));
		reqParam.put(BOX_OFFICE,request.getParameter(BOX_OFFICE));
		reqParam.put(AUDIENCE,request.getParameter(AUDIENCE));
		reqParam.put(DURATION,request.getParameter(DURATION));
		reqParam.put(RELEASE,request.getParameter(RELEASE));
		reqParam.put(TEASER,request.getParameter(TEASER));
		String path = request.getServletContext().getRealPath("");
		reqParam.put(PROJECT_PATH,path);
		
		InputStream is = null;
		try{
		Part part = request.getPart(POSTER);
		if (part!=null){
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
			film = fService.addFilm(reqParam, is);
			film.setDescription(request.getParameter(DESCRIPTION_RU));
			film.setTitle(request.getParameter(TITLE_RU));
			fService.addFilm(film, RU);
			
			film.setDescription(request.getParameter(DESCRIPTION_EN));
			film.setTitle(request.getParameter(TITLE_EN));
			fService.addFilm(film, EN);
			
			if(genreList!=null){
				for (int i=0;i<genreList.length;i++){
					gService.addGenreToFilm(film.getId(), Integer.valueOf(genreList[i]));
				}
			}
			if(directorList!=null){
				for (int i=0;i<directorList.length;i++){
					aService.addDirectorToFilm(film.getId(), Integer.valueOf(directorList[i]));
				}
			}
			
			if(scenarioList!=null){
				for (int i=0;i<scenarioList.length;i++){
					aService.addScenarioToFilm(film.getId(), Integer.valueOf(scenarioList[i]));
				}
			}
			if(actorList!=null){
				for (int i=0;i<actorList.length;i++){
					aService.addActorToFilm(film.getId(), Integer.valueOf(actorList[i]));
				}
			}
			
			if(countryList!=null){
				for (int i=0;i<countryList.length;i++){
					cService.addCountryToFilm(film.getId(), Integer.valueOf(countryList[i]));
				}
			}
		}catch(ServiceException e){
			errorDispatcher.forward(request, response);
			return;
		}
		String locale = (String)session.getAttribute(LOCALE);
		if(locale == null || locale.isEmpty()){
			locale = RU;
		}
		
		try{
			film = fService.getFilmById(film.getId(), locale);
		}catch(ServiceException e){
			errorDispatcher.forward(request, response);
			return;
		}
		
       //request.getRequestDispatcher(FILM_CARD_PAGE).forward(request, response);
			response.sendRedirect(FILM_CARD_PAGE+film.getId());
	}

}
