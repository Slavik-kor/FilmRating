package by.epam.karotki.film_rating.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.karotki.film_rating.command.Command;

public class Localization implements Command {
	private static final String LOCALE = "locale";
	private static final String PREV_PAGE = "prev_page";
	private static final String INDEX_PAGE = "index.jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(true);
		session.setAttribute(LOCALE, request.getParameter(LOCALE));
		String prevPage = (String) session.getAttribute(PREV_PAGE);
		if (prevPage != null) {
			response.sendRedirect(prevPage);
		} else {
			request.getRequestDispatcher(INDEX_PAGE).forward(request, response);
		}

	}

}
