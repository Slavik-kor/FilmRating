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
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String login = request.getParameter(LOGIN);
		String password = request.getParameter(PASSWORD);
		
		AccountService aService = ServiceFactory.getInstance().getAccountService();

		try {
			Account account = aService.autorization(login, password);
			
			request.setAttribute("account", account);
			
			request.getRequestDispatcher("WEB-INF/jsp/user-profile.jsp").forward(request, response);
		} catch (ServiceAuthException e) {
			
			request.setAttribute("errorMessage", "Wrong login or password");
			
			request.getRequestDispatcher("index.jsp").forward(request, response);
			
		}  catch (ServiceException e) {
			request.getRequestDispatcher("error.jsp").forward(request, response);		
		}
	}

}
