package by.epam.karotki.film_rating.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.karotki.film_rating.command.Command;

public class Localization implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(true);
		session.setAttribute("local", request.getParameter("local"));
		request.getRequestDispatcher("index.jsp").forward(request, response);
		//response.sendRedirect(request.getRequestURL().toString());
	}

}
