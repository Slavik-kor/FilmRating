package by.epam.karotki.film_rating.dao;

import java.util.List;

import by.epam.karotki.film_rating.dao.exception.DaoException;
import by.epam.karotki.film_rating.entity.Account;


public interface AccountDao {

	List<Account> getUsersByCity(String city) throws DaoException;

	List<Account> getUsersByCountry(String country) throws DaoException;

	List<Account> getBannedUsers() throws DaoException;

	List<Account> getActiveUsersByRate(int value) throws DaoException;

	List<Account> getActiveUsersByComment(int value) throws DaoException;

	Account authorization(String login, String password) throws DaoException;
	
	void addAccount(Account account) throws DaoException;
	
	void updateAccount(Account account) throws DaoException;

	Account getAccountByLogin(String login) throws DaoException;
}
