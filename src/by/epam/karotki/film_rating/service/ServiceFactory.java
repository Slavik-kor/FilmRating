package by.epam.karotki.film_rating.service;

import by.epam.karotki.film_rating.service.impl.AccountServiceImpl;

public class ServiceFactory {
	private static final ServiceFactory instance = new ServiceFactory();
	private static AccountService uService= new AccountServiceImpl();
	
	private ServiceFactory(){
		super();
	}
	
	public static ServiceFactory getInstance(){
		return instance;
	}
	
	public AccountService getAccountService(){
		return uService;
	}
	
}
