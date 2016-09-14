package by.epam.karotki.film_rating.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.karotki.film_rating.command.Command;
import by.epam.karotki.film_rating.entity.Account;
import by.epam.karotki.film_rating.service.CommentService;
import by.epam.karotki.film_rating.service.ServiceFactory;
import by.epam.karotki.film_rating.service.exception.CommentServiceException;

public class DeleteComment implements Command {
	private static final String ERROR_PAGE = "error.jsp";
	private static final String ACCOUNT = "account";
	private static final String PREV_PAGE = "prev_page";
	private static final String FILM = "film";
	private static final String INDEX_PAGE = "index.jsp";


	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(false);
		if(session==null){
			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
			return;
		} 
		
		Account account = (Account) session.getAttribute(ACCOUNT);
		
		if(account==null){
			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
			return;
		}
		int idFilm = Integer.valueOf(request.getParameter(FILM));
		ServiceFactory factory = ServiceFactory.getInstance();
		CommentService cService = factory.getCommentService();
		try{
			cService.deleteComment(account.getId(),idFilm);
			String prevPage = (String) session.getAttribute(PREV_PAGE);
			if (prevPage != null) {
				response.sendRedirect(prevPage);
			} else {
				request.getRequestDispatcher(INDEX_PAGE).forward(request, response);
			}
		}catch(CommentServiceException e){
			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
		}
	}

}
