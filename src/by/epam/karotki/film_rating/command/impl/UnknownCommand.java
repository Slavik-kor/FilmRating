package by.epam.karotki.film_rating.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.karotki.film_rating.command.Command;

public class UnknownCommand implements Command {
	private static final String ERROR_PAGE = "error.jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.getRequestDispatcher(ERROR_PAGE).forward(request, response);

	}

}
