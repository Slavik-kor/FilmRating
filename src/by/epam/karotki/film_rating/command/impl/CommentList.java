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
import by.epam.karotki.film_rating.entity.Comment;
import by.epam.karotki.film_rating.entity.Film;
import by.epam.karotki.film_rating.service.CommentService;
import by.epam.karotki.film_rating.service.FilmService;
import by.epam.karotki.film_rating.service.ServiceFactory;
import by.epam.karotki.film_rating.service.exception.ServiceException;

public class CommentList implements Command {
	private static final String COMMENT_LIST_PAGE = "/WEB-INF/jsp/comments.jsp";
	private static final String ERROR_PAGE = "error.jsp";
	private static final String ACCOUNT = "account";
	private static final String ADMIN = "Admin";
	private static final String USER = "User";
	private static final String COMMENT_LIST = "comment_list";
	private static final String FILM_LIST = "film_list";
	private static final String LOCALE = "locale";
	private static final String RU = "ru";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(false);
		String locale = (String)session.getAttribute(LOCALE);
		if(locale == null || locale.isEmpty()){
			locale = RU;
		}
		RequestDispatcher errorDispatcher = request.getRequestDispatcher(ERROR_PAGE);
		RequestDispatcher pageDispatcher = request.getRequestDispatcher(COMMENT_LIST_PAGE);
		Account account = null;
		ServiceFactory factory = ServiceFactory.getInstance();
		CommentService cService = factory.getCommentService();
		FilmService fService = factory.getFilmService();
		if ((session != null) && ((account = (Account) session.getAttribute(ACCOUNT)) != null)) {
			String role = account.getRole();
			if ((role.equals(ADMIN)) || (role.equals(USER))) {
				List<Comment> commentList = null;
				List<Film> filmList = null;
				try {
					commentList = cService.getCommentsByAccount(account.getId());
					filmList = fService.getFilmsByComments(account.getId(), locale);
				} catch (ServiceException e) {

					errorDispatcher.forward(request, response);
				}
				request.setAttribute(FILM_LIST, filmList);
				request.setAttribute(COMMENT_LIST, commentList);
				pageDispatcher.forward(request, response);
			} else {
				errorDispatcher.forward(request, response);
			}
		} else {
			errorDispatcher.forward(request, response);
		}
	}

}
