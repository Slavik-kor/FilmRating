package by.epam.karotki.film_rating.service;

import by.epam.karotki.film_rating.entity.Account;
import by.epam.karotki.film_rating.service.exception.ServiceException;

public interface AccountService {
	
	Account autorization(String login,String pass) throws ServiceException;

}
