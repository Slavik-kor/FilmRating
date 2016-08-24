package by.epam.karotki.film_rating.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.karotki.film_rating.command.Command;
import by.epam.karotki.film_rating.entity.Account;

public class Profile implements Command {
	private static final String PROFILE_PAGE = "/WEB-INF/jsp/user-profile.jsp";
	private static final String ERROR_PAGE = "error.jsp";
	private static final String ACCOUNT = "account";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(false);
		if(((session!=null))&&((Account)session.getAttribute(ACCOUNT)!=null)){
		request.getRequestDispatcher(PROFILE_PAGE).forward(request, response);
		}else{
			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
		}

	}

}
