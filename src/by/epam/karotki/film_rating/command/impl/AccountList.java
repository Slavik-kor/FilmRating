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
import by.epam.karotki.film_rating.service.AccountService;
import by.epam.karotki.film_rating.service.ServiceFactory;
import by.epam.karotki.film_rating.service.exception.ServiceException;

public class AccountList implements Command {
	private static final String ACCOUNT_LIST_PAGE = "/WEB-INF/jsp/accounts.jsp";
	private static final String ERROR_PAGE = "error.jsp";
	private static final String ACCOUNT = "account";
	private static final String ADMIN = "Admin";
	private static final String ACCOUNT_LIST = "accountList";
	private static final String ERROR_MESSAGE = "errorMessage";
	private static final String VALUE = "value";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(false);
		RequestDispatcher errorDispatcher = request.getRequestDispatcher(ERROR_PAGE);
		RequestDispatcher pageDispatcher = request.getRequestDispatcher(ACCOUNT_LIST_PAGE);
		Account account = null;
		List<Account> accountList = null;
		if ((session != null) && ((account = (Account) session.getAttribute(ACCOUNT)) != null)) {
			String role = account.getRole();
			if (role.equals(ADMIN)) {
				try {
					ServiceFactory factory = ServiceFactory.getInstance();
					AccountService aService = factory.getAccountService();
					accountList = aService.getAccountList(Integer.valueOf(request.getParameter(VALUE)));
					request.setAttribute(ACCOUNT_LIST, accountList);
					pageDispatcher.forward(request, response);
				} catch (ServiceException e) {
					request.setAttribute(ERROR_MESSAGE, "internal error. Email to support service");
					errorDispatcher.forward(request, response);
				}
				
			} else {
				request.setAttribute(ERROR_MESSAGE, "Not enough rights to perform this action");
				errorDispatcher.forward(request, response);
			}
		} else {
			request.setAttribute(ERROR_MESSAGE, "It's needed authorization");
			errorDispatcher.forward(request, response);
		}

	}

}
