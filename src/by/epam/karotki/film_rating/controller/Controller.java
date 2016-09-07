package by.epam.karotki.film_rating.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.karotki.film_rating.command.Command;

/**
 * Servlet implementation class Controller
 */
@WebServlet("/Controller")
@MultipartConfig
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String COMMAND = "command";
	private static final String INDEX_PAGE = "index.jsp";
	

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Controller() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String commandName = request.getParameter(COMMAND);
		if (commandName != null) {
			Command command = CommandHelper.getInstance().getCommand(commandName);
				command.execute(request, response);
		} else {
			request.getRequestDispatcher(INDEX_PAGE).forward(request, response);
		}
	}

}
