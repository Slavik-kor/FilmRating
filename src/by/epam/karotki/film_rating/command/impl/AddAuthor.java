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
import by.epam.karotki.film_rating.entity.Author;
import by.epam.karotki.film_rating.service.AuthorService;
import by.epam.karotki.film_rating.service.ServiceFactory;
import by.epam.karotki.film_rating.service.exception.ServiceException;

public class AddAuthor implements Command {
	private static final String ERROR_PAGE = "error.jsp";
	private static final String AUTHOR_CARD_PAGE = "Controller?command=author_card&author_id=";
	private static final String ACCOUNT = "account";
	private static final String ADMIN = "Admin";
	private static final String ERROR_MESSAGE = "errorMessage";
	private static final String F_NAME_RU = "first-name-ru";
	private static final String F_NAME_EN = "first-name-en";
	private static final String F_NAME = "first-name";
	private static final String L_NAME_RU = "last-name-ru";
	private static final String L_NAME_EN = "last-name-en";
	private static final String L_NAME = "last-name";
	private static final String COUNTRY = "country";
	private static final String BIRTHDAY = "birthday";
	private static final String DIR_FILMS = "dirFilms";
	private static final String SCEN_FILMS = "scenFilms";
	private static final String ACT_FILMS = "actFilms";
	private static final String PHOTO = "authorPhoto";
	private static final String PROJECT_PATH = "ProjectPath";
	private static final String RU = "ru";
	private static final String EN = "en";
	private static final String LOCALE = "locale";

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
		reqParam.put(F_NAME, request.getParameter(F_NAME_RU));
		reqParam.put(L_NAME, request.getParameter(L_NAME_RU));
		reqParam.put(COUNTRY, request.getParameter(COUNTRY));
		reqParam.put(BIRTHDAY, request.getParameter(BIRTHDAY));
		String path = request.getServletContext().getRealPath("");
		reqParam.put(PROJECT_PATH,path);
		
		InputStream is = null;
		try{
		Part part = request.getPart(PHOTO);
		if (part!=null){
		is = part.getInputStream();
		}
		}catch(ServletException e){
			//log
		}
		
		String[] dirFilmList = request.getParameterValues(DIR_FILMS);
		String[] scenFilmList = request.getParameterValues(SCEN_FILMS);
		String[] actFilmList = request.getParameterValues(ACT_FILMS);
		
		ServiceFactory factory = ServiceFactory.getInstance();
		AuthorService aService = factory.getAuthorService();
		Author author = null;
		try{
			author = aService.addAuthor(reqParam, is);
			if(author==null){
				errorDispatcher.forward(request, response);
				return;
			}
			author.setFirstName(request.getParameter(F_NAME_RU));
			author.setLastName(request.getParameter(L_NAME_RU));
			aService.addAuthor(author, RU);
			
			author.setFirstName(request.getParameter(F_NAME_EN));
			author.setLastName(request.getParameter(L_NAME_EN));
			aService.addAuthor(author, EN);
			
			
			if(dirFilmList!=null){
				for (int i=0;i<dirFilmList.length;i++){
					aService.addDirectorToFilm(Integer.valueOf(dirFilmList[i]), author.getId());
				}
			}
			
			if(scenFilmList!=null){
				for (int i=0;i<scenFilmList.length;i++){
					aService.addScenarioToFilm(Integer.valueOf(scenFilmList[i]), author.getId());
				}
			}
			if(actFilmList!=null){
				for (int i=0;i<actFilmList.length;i++){
					aService.addActorToFilm(Integer.valueOf(actFilmList[i]),author.getId());
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
			author = aService.getAuthorById(author.getId(),locale);
		}catch(ServiceException e){
			errorDispatcher.forward(request, response);
			return;
		}
		
			request.getRequestDispatcher(AUTHOR_CARD_PAGE).forward(request, response);
			response.sendRedirect(AUTHOR_CARD_PAGE+author.getId());
	}
	

}
