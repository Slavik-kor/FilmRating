package by.epam.karotki.film_rating.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epam.karotki.film_rating.command.Command;
import by.epam.karotki.film_rating.entity.Account;
import by.epam.karotki.film_rating.service.AccountService;
import by.epam.karotki.film_rating.service.ServiceFactory;
import by.epam.karotki.film_rating.service.exception.AccountServiceException;

public class DeleteAccount implements Command {
	private static final String ERROR_PAGE = "error.jsp";
	private static final String ACCOUNT = "account";
	private static final String INDEX_PAGE = "index.jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(false);
		
		if(session==null){
			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
			return;
		} 
		
		Account account = (Account) session.getAttribute(ACCOUNT);
		
		ServiceFactory factory = ServiceFactory.getInstance();
		AccountService aService = factory.getAccountService();
		String path = request.getServletContext().getRealPath("");
		try{
			aService.deleteAccount(account.getId(),path);
			session.setAttribute(ACCOUNT, null);
			response.sendRedirect(INDEX_PAGE);
		}catch(AccountServiceException e){
			request.getRequestDispatcher(ERROR_PAGE).forward(request, response);
		}
	}
	

}
