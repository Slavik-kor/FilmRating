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
import by.epam.karotki.film_rating.service.FilmService;
import by.epam.karotki.film_rating.service.ServiceFactory;
import by.epam.karotki.film_rating.service.exception.ServiceException;

public class AddFilm implements Command {
	private static final String ERROR_PAGE = "error.jsp";
	private static final String INDEX_PAGE = "index.jsp";
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
		System.out.println(part);
		if (part!=null){
		is = part.getInputStream();
		}
		}catch(ServletException e){
			//log
		}
		
		Map<String,String> reqParamRu = new HashMap<String,String>();
		reqParamRu.put(TITLE_RU, request.getParameter(TITLE_RU));
		reqParamRu.put(DESCRIPTION_RU, request.getParameter(DESCRIPTION_RU));
		Map<String,String> reqParamEn = new HashMap<String,String>();
		reqParamEn.put(TITLE_EN, request.getParameter(TITLE_RU));
		reqParamEn.put(DESCRIPTION_EN, request.getParameter(DESCRIPTION_EN));
		
		ServiceFactory factory = ServiceFactory.getInstance();
		FilmService fService = factory.getFilmService();
		Film film = null;
		try{
			film = fService.addFilm(reqParam, is);
			fService.addFilm(reqParamRu, RU);
			fService.addFilm(reqParamEn, EN);
		}catch(ServiceException e){
			errorDispatcher.forward(request, response);

		}
		
request.getRequestDispatcher(INDEX_PAGE).forward(request, response);
}

}
