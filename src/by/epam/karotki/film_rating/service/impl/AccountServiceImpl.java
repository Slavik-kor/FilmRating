package by.epam.karotki.film_rating.service.impl;

import by.epam.karotki.film_rating.dao.DaoFactory;
import by.epam.karotki.film_rating.dao.AccountDao;
import by.epam.karotki.film_rating.dao.exception.DaoException;
import by.epam.karotki.film_rating.entity.Account;
import by.epam.karotki.film_rating.service.AccountService;
import by.epam.karotki.film_rating.service.exception.ServiceAuthException;
import by.epam.karotki.film_rating.service.exception.ServiceException;

public class AccountServiceImpl implements AccountService {

	@Override
	public Account autorization(String login, String pass) throws ServiceException {
		if (login == null || login.isEmpty()) {
			throw new ServiceAuthException("Empty login field!");
		}
		if (pass == null || pass.isEmpty()) {
			throw new ServiceAuthException("Empty password field!");
		}
		DaoFactory dao = DaoFactory.getInstance();
		AccountDao aDao = dao.getAccountDao();
		
		Account account = null;
		
		try {
			account = aDao.authorization(login, pass);
			
			if(account == null){
				throw new ServiceAuthException("Wrong login or password!");
			}
			
		} catch (DaoException e) {
			
			throw new ServiceException("Error in source!", e);
			
		}
		return account;
	}

}
