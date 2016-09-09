package by.epam.karotki.film_rating.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.karotki.film_rating.command.Command;
import by.epam.karotki.film_rating.entity.Comment;
import by.epam.karotki.film_rating.service.CommentService;
import by.epam.karotki.film_rating.service.ServiceFactory;
import by.epam.karotki.film_rating.service.exception.ServiceException;

public class AddComment implements Command {
	private static final String RATE = "rate";
	private static final String COMMENT = "comment";
	private static final String ACCOUNT_ID = "account";
	private static final String FILM_ID = "film";
	private static final String ERROR_PAGE = "error.jsp";
	private static final String PREV_PAGE = "prev_page";
	private static final String INDEX_PAGE = "index.jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Integer rate = Integer.valueOf(request.getParameter(RATE));
		String commentText = request.getParameter(COMMENT);
		Integer film_id = Integer.valueOf(request.getParameter(FILM_ID));
		Integer account_id = Integer.valueOf(request.getParameter(ACCOUNT_ID));
		Comment comment = new Comment();
		comment.setRate(rate);
		comment.setAccountId(account_id);
		comment.setComment(commentText);
		comment.setFilmId(film_id);

		ServiceFactory sFactory = ServiceFactory.getInstance();
		CommentService cService = sFactory.getCommentService();
		try {
			cService.addComment(comment);
			HttpSession session = request.getSession(false);
			String prevPage = (String) session.getAttribute(PREV_PAGE);
			if (prevPage != null) {
				response.sendRedirect(prevPage);
			} else {
				request.getRequestDispatcher(INDEX_PAGE).forward(request, response);
			}
		} catch (ServiceException e) {

			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
		}

	}

}
