package by.epam.karotki.film_rating.service;

import by.epam.karotki.film_rating.service.impl.AccountServiceImpl;
import by.epam.karotki.film_rating.service.impl.AuthorServiceImpl;
import by.epam.karotki.film_rating.service.impl.CountryServiceImpl;
import by.epam.karotki.film_rating.service.impl.FilmServiceImpl;
import by.epam.karotki.film_rating.service.impl.GenreServiceImpl;
import by.epam.karotki.film_rating.service.impl.InitServiceImpl;

public class ServiceFactory {
	private static final ServiceFactory instance = new ServiceFactory();
	private AccountService uService= new AccountServiceImpl();
	private FilmService fService= new FilmServiceImpl();
	private InitService iService= new InitServiceImpl();
	private CountryService cService = new CountryServiceImpl();
	private GenreService gService = new GenreServiceImpl();
	private AuthorService aService = new AuthorServiceImpl();
	
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
	
	public GenreService getGenreService(){
		return gService;
	}
	
	public AuthorService getAuthorService(){
		return aService;
	} 
}
