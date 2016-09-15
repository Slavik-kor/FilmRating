package by.epam.karotki.film_rating.dao;

import java.util.List;

import by.epam.karotki.film_rating.dao.exception.AccountDaoException;
import by.epam.karotki.film_rating.entity.Account;


public interface AccountDao {

	List<Account> getUsersByCountry(String country) throws AccountDaoException;

	List<Account> getBannedUsers() throws AccountDaoException;

	List<Account> getActiveUsersByComment(int value) throws AccountDaoException;
	
	List<Account> getAccountList() throws AccountDaoException;	
	
	List<Account> getAccountByCriteria(Criteria criteria) throws AccountDaoException;	

	Account authorization(String login, String password) throws AccountDaoException;
	
	void addAccount(Account account) throws AccountDaoException;
	
	void updateAccount(Account account) throws AccountDaoException;

	Account getAccountByLogin(String login) throws AccountDaoException;
	
	void deleteAccountById(int id) throws AccountDaoException;
}
