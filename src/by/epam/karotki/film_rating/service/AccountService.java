package by.epam.karotki.film_rating.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import by.epam.karotki.film_rating.entity.Account;
import by.epam.karotki.film_rating.service.exception.AccountServiceException;

public interface AccountService {
	
	Account autorization(String login,String pass) throws AccountServiceException;
	
	Account registration(Map<String,String> reqParam,InputStream is) throws AccountServiceException;
	
	Account updateAccount(Map<String,String> reqParam,InputStream is) throws AccountServiceException;
	
	List<Account> getAccountList(int value) throws AccountServiceException;
	
	Account getAccountById(int idAccount) throws AccountServiceException;
	
	void deleteAccount(int id) throws AccountServiceException;

}
