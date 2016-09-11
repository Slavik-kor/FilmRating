package by.epam.karotki.film_rating.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.karotki.film_rating.command.Command;
import by.epam.karotki.film_rating.entity.Account;

public class UpdateProfilePage implements Command {
	private static final String ACCOUNT = "account";
	private static final String ERROR = "error.jsp";
	private static final String ACCOUNT_UPDATE_PAGE = "/WEB-INF/jsp/registration.jsp";
	private static final String MODE = "mode";
	private static final String UPDATE = "update";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(false);
		RequestDispatcher errorDispatcher = request.getRequestDispatcher(ERROR);
		if (session == null) {
			errorDispatcher.forward(request, response);
		}
		Account account = (Account) session.getAttribute(ACCOUNT);
		if (account == null) {
			errorDispatcher.forward(request, response);
		}
		request.setAttribute(MODE, UPDATE);
		request.getRequestDispatcher(ACCOUNT_UPDATE_PAGE).forward(request, response);
	}

}
