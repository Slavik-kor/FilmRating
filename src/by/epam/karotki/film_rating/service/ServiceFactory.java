package by.epam.karotki.film_rating.service;

import by.epam.karotki.film_rating.service.impl.AccountServiceImpl;
import by.epam.karotki.film_rating.service.impl.CountryServiceImpl;
import by.epam.karotki.film_rating.service.impl.FilmServiceImpl;
import by.epam.karotki.film_rating.service.impl.InitServiceImpl;

public class ServiceFactory {
	private static final ServiceFactory instance = new ServiceFactory();
	private static AccountService uService= new AccountServiceImpl();
	private static FilmService fService= new FilmServiceImpl();
	private static InitService iService= new InitServiceImpl();
	private static CountryService cService = new CountryServiceImpl();
	
	private ServiceFactory(){
		super();
	}
	
	public static ServiceFactory getInstance(){
		return instance;
	}
	
	public AccountService getAccountService(){
		return uService;
	}
	
	public FilmService getFilmService(){
		return fService;
	}
	
	public InitService getInitService(){
		return iService;
	}
	
	public CountryService getCountryService(){
		return cService;
	}
}
