package by.epam.karotki.film_rating.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.karotki.film_rating.command.Command;
import by.epam.karotki.film_rating.entity.Account;
import by.epam.karotki.film_rating.service.AccountService;
import by.epam.karotki.film_rating.service.ServiceFactory;
import by.epam.karotki.film_rating.service.exception.ServiceAuthException;
import by.epam.karotki.film_rating.service.exception.ServiceException;

public class Authorization implements Command {
	private static final String LOGIN = "login";
	private static final String PASSWORD = "password";
	private static final String ACCOUNT = "account";
	private static final String USER_PROFILE_PAGE = "WEB-INF/jsp/user-profile.jsp";
	private static final String ERROR_MESSAGE = "errorMessage";
	private static final String INDEX_PAGE = "index.jsp";
	private static final String ERROR_PAGE = "error.jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String login = request.getParameter(LOGIN);
		String password = request.getParameter(PASSWORD);

		AccountService aService = ServiceFactory.getInstance().getAccountService();

		try {
			Account account = aService.autorization(login, password);

			request.setAttribute(ACCOUNT, account);

			request.getRequestDispatcher(USER_PROFILE_PAGE).forward(request, response);
		} catch (ServiceAuthException e) {

			request.setAttribute(ERROR_MESSAGE, "Wrong login or password");

			request.getRequestDispatcher(INDEX_PAGE).forward(request, response);

		} catch (ServiceException e) {
			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
		}
	}

}
