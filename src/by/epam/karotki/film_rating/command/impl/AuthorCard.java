package by.epam.karotki.film_rating.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epam.karotki.film_rating.command.Command;
import by.epam.karotki.film_rating.entity.Author;
import by.epam.karotki.film_rating.entity.Country;
import by.epam.karotki.film_rating.service.AuthorService;
import by.epam.karotki.film_rating.service.CountryService;
import by.epam.karotki.film_rating.service.ServiceFactory;
import by.epam.karotki.film_rating.service.exception.ServiceException;

public class AuthorCard implements Command {
	private static final String AUTHOR_ID = "author_id";
	private static final String AUTHOR = "author";
	private static final String COUNTRY = "country";
	private static final String AUTHOR_CARD_PAGE = "/WEB-INF/jsp/author-card.jsp";
	private static final String ERROR_PAGE = "error.jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		int idAuthor = Integer.valueOf(request.getParameter(AUTHOR_ID));
		ServiceFactory factory = ServiceFactory.getInstance();
		AuthorService aService = factory.getAuthorService();
		CountryService cService = factory.getCountryService();
		try {
			Author author = aService.getAuthorById(idAuthor);
			request.setAttribute(AUTHOR, author);
			
			if (author != null) {
				Country country = cService.getCountryById(author.getCountryOfBirthId());
				request.setAttribute(COUNTRY, country);
			}
			request.getRequestDispatcher(AUTHOR_CARD_PAGE).forward(request, response);
		} catch (ServiceException e) {
			e.printStackTrace();
			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
		}
	}

}
