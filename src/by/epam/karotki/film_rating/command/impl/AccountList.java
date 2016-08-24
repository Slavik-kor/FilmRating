package by.epam.karotki.film_rating.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.karotki.film_rating.command.Command;
import by.epam.karotki.film_rating.entity.Account;

public class AccountList implements Command {
	private static final String ACCOUNT_LIST_PAGE = "/WEB-INF/jsp/account-list.jsp";
	private static final String ERROR_PAGE = "error.jsp";
	private static final String ACCOUNT = "account";
	private static final String ADMIN = "admin";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(false);
		RequestDispatcher errorDispatcher = request.getRequestDispatcher(ERROR_PAGE);
		RequestDispatcher pageDispatcher = request.getRequestDispatcher(ACCOUNT_LIST_PAGE);
		Account account = null;
		if ((session != null) && ((account = (Account) session.getAttribute(ACCOUNT)) != null)) {
				String role = account.getRole();
				if (role.equals(ADMIN)) {
					pageDispatcher.forward(request, response);
				} else {
					errorDispatcher.forward(request, response);
				}
		} else {
			errorDispatcher.forward(request, response);
		}
		

	}

}
