package by.epam.karotki.film_rating.service;

import by.epam.karotki.film_rating.service.impl.AccountServiceImpl;
import by.epam.karotki.film_rating.service.impl.InitServiceImpl;

public class ServiceFactory {
	private static final ServiceFactory instance = new ServiceFactory();
	private static AccountService uService= new AccountServiceImpl();
	private static InitService iService= new InitServiceImpl();
	
	private ServiceFactory(){
		super();
	}
	
	public static ServiceFactory getInstance(){
		return instance;
	}
	
	public AccountService getAccountService(){
		return uService;
	}
	
	public InitService getInitService(){
		return iService;
	}
}
