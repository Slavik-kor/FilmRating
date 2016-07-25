package by.epam.karotki.film_rating.service.impl;

import by.epam.karotki.film_rating.dao.DaoFactory;
import by.epam.karotki.film_rating.dao.IAccountDao;
import by.epam.karotki.film_rating.dao.exception.DaoException;
import by.epam.karotki.film_rating.entity.Account;
import by.epam.karotki.film_rating.service.AccountService;
import by.epam.karotki.film_rating.service.exception.ServiceAuthException;
import by.epam.karotki.film_rating.service.exception.ServiceException;

public class AccountServiceImpl implements AccountService {

	@Override
	public Account autorization(String login, String pass) throws ServiceException {
		//validation
		DaoFactory dao = DaoFactory.getInstance();
		IAccountDao aDao = dao.getAccountDao();
		
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
