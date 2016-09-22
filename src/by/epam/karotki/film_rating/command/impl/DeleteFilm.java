package by.epam.karotki.film_rating.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.karotki.film_rating.command.Command;
import by.epam.karotki.film_rating.entity.Account;
import by.epam.karotki.film_rating.service.FilmService;
import by.epam.karotki.film_rating.service.ServiceFactory;
import by.epam.karotki.film_rating.service.exception.ServiceException;

public class DeleteFilm implements Command {
	private static final String ERROR_PAGE = "error.jsp";
	private static final String ACCOUNT = "account";
	private static final String ADMIN = "Admin";
	private static final String ERROR_MESSAGE = "errorMessage";
	private static final String FILM_ID = "idFilm";
	private static final String INDEX_PAGE = "index.jsp";
	private static final String PREV_PAGE = "prev_page";

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

		int idFilm;
		try {
			idFilm = Integer.valueOf(request.getParameter(FILM_ID));
		} catch (IllegalArgumentException e) {
			request.setAttribute(ERROR_MESSAGE, "Wrong data in request");
			errorDispatcher.forward(request, response);
			return;
		}
		
		ServiceFactory factory = ServiceFactory.getInstance();
		FilmService fService = factory.getFilmService();
		String path = request.getServletContext().getRealPath("");
		try{
			fService.deleteFilm(idFilm, path);
			String prevPage = (String) session.getAttribute(PREV_PAGE);
			if (prevPage != null) {
				response.sendRedirect(prevPage);
			} else {
				response.sendRedirect(INDEX_PAGE);
			}
		}catch(ServiceException e){
			response.sendRedirect(ERROR_PAGE);
		}
	}

}
