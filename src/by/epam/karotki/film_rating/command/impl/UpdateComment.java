package by.epam.karotki.film_rating.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.karotki.film_rating.command.Command;
import by.epam.karotki.film_rating.entity.Account;
import by.epam.karotki.film_rating.entity.Comment;
import by.epam.karotki.film_rating.service.CommentService;
import by.epam.karotki.film_rating.service.ServiceFactory;
import by.epam.karotki.film_rating.service.exception.ServiceException;

public class UpdateComment implements Command {
	private static final String RATE = "rate";
	private static final String COMMENT = "comment";
	private static final String FILM_ID = "film";
	private static final String ERROR_PAGE = "error.jsp";
	private static final String PREV_PAGE = "prev_page";
	private static final String INDEX_PAGE = "index.jsp";
	private static final String ACCOUNT = "account";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(false);
		if(session==null){
			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
			return;
		}
		Account account = null;
		if((account = (Account)session.getAttribute(ACCOUNT)) == null){
			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
			return;
		}
		
		Integer rate = Integer.valueOf(request.getParameter(RATE));
		String commentText = request.getParameter(COMMENT);
		int film_id = Integer.valueOf(request.getParameter(FILM_ID));
		int account_id = account.getId();
		Comment comment = new Comment();
		comment.setRate(rate);
		comment.setAccountId(account_id);
		comment.setComment(commentText);
		comment.setFilmId(film_id);
		
		ServiceFactory factory = ServiceFactory.getInstance();
		CommentService cService = factory.getCommentService();
		try{
			cService.updateComment(comment);
			String prevPage = (String) session.getAttribute(PREV_PAGE);
			if (prevPage != null) {
				response.sendRedirect(prevPage);
			} else {
				response.sendRedirect(INDEX_PAGE);
			}
		}catch(ServiceException e){
			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
		}
			
	}

}
