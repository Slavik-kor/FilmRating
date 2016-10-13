package by.epam.karotki.film_rating.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.karotki.film_rating.command.Command;
import by.epam.karotki.film_rating.entity.Account;

public class UpdateAuthorPage implements Command {
	private static final String ERROR_PAGE = "error.jsp";
	private static final String ACCOUNT = "account";
	private static final String ADMIN = "Admin";
	private static final String ERROR_MESSAGE = "errorMessage";
	private static final String LOCALE = "locale";
	private static final String RU = "ru";

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
			request.setAttribute(ERROR_MESSAGE, "Not enough access right or needed authorization");
			errorDispatcher.forward(request, response);
			return;
		}
		
		String locale = (String)session.getAttribute(LOCALE);
		if(locale == null || locale.isEmpty()){
			locale = RU;
		}
		
		
		
	}
	
	
	

}
