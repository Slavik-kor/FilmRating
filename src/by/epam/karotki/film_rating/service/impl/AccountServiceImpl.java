package by.epam.karotki.film_rating.service.impl;

import by.epam.karotki.film_rating.dao.DaoFactory;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import by.epam.karotki.film_rating.dao.AccountDao;
import by.epam.karotki.film_rating.dao.exception.DaoException;
import by.epam.karotki.film_rating.entity.Account;
import by.epam.karotki.film_rating.service.AccountService;
import by.epam.karotki.film_rating.service.exception.AccountServiceException;
import by.epam.karotki.film_rating.service.exception.AuthServiceException;

public class AccountServiceImpl implements AccountService {
	private static final String LOGIN = "login";
	private static final String PASSWORD = "pass";
	private static final String FIRST_NAME = "first-name";
	private static final String LAST_NAME = "last-name";
	private static final String BIRTHDAY = "birthday";
	private static final String COUNTRY = "country";
	private static final String EMAIL = "email";
	private static final String PHONE_NUMBER = "phone-number";
//	private static final String AVATAR = "file";
	private static final String USER = "User";
	private static final String PATH_AVATAR = "images/avatar/";

	@Override
	public Account autorization(String login, String pass) throws AccountServiceException {
		validateLogPas(login,pass);
		DaoFactory dao = DaoFactory.getInstance();
		AccountDao aDao = dao.getAccountDao();
		
		Account account = null;
		
		try {
			account = aDao.authorization(login, pass);
			
			if(account == null){
				throw new AuthServiceException("Wrong login or password!");
			}
			
		} catch (DaoException e) {
			
			throw new AccountServiceException("Error in source!", e);
			
		}
		return account;
	}

	@Override
	public Account registration(Map<String,String> reqParam) throws AccountServiceException {
		validateParam(reqParam);
		Account account = createAccount(reqParam);
		DaoFactory dao = DaoFactory.getInstance();
		AccountDao aDao = dao.getAccountDao();
		Account newAccount = null;
			try{
				System.out.println("Before add");
			aDao.addAccount(account);
			System.out.println("After add");
			
			}catch(DaoException e){
				System.out.println("in DaoException");
				throw new AccountServiceException("error in source during registration",e);
			}
			
			try{
			newAccount = aDao.getAccountByLogin(reqParam.get(LOGIN));
			System.out.println("After getAccountByLogin");
			}catch(DaoException e){
				System.out.println("in DaoException");
				throw new AccountServiceException("error in source during registration",e);
			}
			System.out.println(newAccount);
			return newAccount;
	}
	
	private void validateParam(Map<String,String> reqParam) throws AccountServiceException{
		validateLogPas(reqParam.get(LOGIN),reqParam.get(PASSWORD));
		DaoFactory dao = DaoFactory.getInstance();
		AccountDao aDao = dao.getAccountDao();
		Account existAccount = null;
		try{
		existAccount = aDao.getAccountByLogin(reqParam.get(LOGIN));
		}catch(DaoException e){ 
			throw new AccountServiceException("error in source during validating login");
		}
			if(existAccount != null){
				throw new AccountServiceException("login already exist");
			}
	}

	private void validateLogPas(String login, String pass) throws AuthServiceException{
		if (login == null || login.isEmpty()) {
			throw new AuthServiceException("Empty login field!");
		}
		if (pass == null || pass.isEmpty()) {
			throw new AuthServiceException("Empty password field!");
		}
	}
	
	private Account createAccount(Map<String,String> reqParam){
		Account account = new Account();
		account.setLogin(reqParam.get(LOGIN));
		account.setPassword(reqParam.get(PASSWORD));
		account.setFirstName(reqParam.get(FIRST_NAME));
		account.setLastName(reqParam.get(LAST_NAME));
		account.setEmail(reqParam.get(EMAIL));
		account.setPhone(reqParam.get(PHONE_NUMBER));
		account.setRole(USER);
		
		Date birthday = null;
		try{
		birthday = Date.valueOf(reqParam.get(BIRTHDAY));
		}catch(IllegalArgumentException e){
			birthday = null;
		}
		account.setBirthDay(birthday);
		
		Integer countryId = null;
		try{
		countryId = Integer.valueOf(reqParam.get(COUNTRY));
		}catch(IllegalArgumentException|NullPointerException e){
			countryId = null;
		}
		account.setCountryId(countryId);
		
		String avatar = PATH_AVATAR+"avatar"+account.getLogin()+".jpg";
	
		account.setPhoto(avatar);
		System.out.println(account);
		return account;
	}

	@Override
	public List<Account> getAccountList(int value) throws AccountServiceException {
		List<Account> accountList = null;
		DaoFactory dao = DaoFactory.getInstance();
		AccountDao aDao = dao.getAccountDao();
		try {
			accountList = aDao.getAccountList(value);
		} catch (DaoException e) {
			
			throw new AccountServiceException("Error in source!", e);
			
		}
		return accountList;
	}
	
}
