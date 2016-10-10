package by.epam.karotki.film_rating.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.karotki.film_rating.command.Command;
import by.epam.karotki.film_rating.entity.Account;
import by.epam.karotki.film_rating.service.AccountService;
import by.epam.karotki.film_rating.service.ServiceFactory;
import by.epam.karotki.film_rating.service.exception.AuthServiceException;
import by.epam.karotki.film_rating.service.exception.ServiceException;

public class Authorization implements Command {
	private static final String LOGIN = "login";
	private static final String PASSWORD = "password";
	private static final String ACCOUNT = "account";
	private static final String PREV_PAGE = "prev_page";
	private static final String ERROR_MESSAGE = "errorMessage";
	// private static final String INDEX_PAGE = "index.jsp";
	private static final String ERROR_PAGE = "error.jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String login = request.getParameter(LOGIN);
		String password = request.getParameter(PASSWORD);

		AccountService aService = ServiceFactory.getInstance().getAccountService();
		RequestDispatcher errorDispatcher = request.getRequestDispatcher(ERROR_PAGE);
		try {
			Account account = aService.autorization(login, password);
			HttpSession session = request.getSession(true);
			session.setAttribute(ACCOUNT, account);
			String prev_page = (String) session.getAttribute(PREV_PAGE);
			if (prev_page != null) {
				response.sendRedirect(prev_page);
			} else {
				request.setAttribute(ERROR_MESSAGE, "attribute \"prev_page\" not found");
				errorDispatcher.forward(request, response);
			}
		} catch (AuthServiceException e) {

			request.setAttribute(ERROR_MESSAGE, "Wrong login or password");
			errorDispatcher.forward(request, response);

		} catch (ServiceException e) {
			request.setAttribute(ERROR_MESSAGE, "Error in Service");
			errorDispatcher.forward(request, response);
		}
	}

}
