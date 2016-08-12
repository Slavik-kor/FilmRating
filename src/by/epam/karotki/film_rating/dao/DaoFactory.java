package by.epam.karotki.film_rating.dao;

import by.epam.karotki.film_rating.dao.impl.AccountDaoImpl;
import by.epam.karotki.film_rating.dao.impl.AuthorDaoImpl;
import by.epam.karotki.film_rating.dao.impl.FilmDaoImpl;

public class DaoFactory {
	private static final DaoFactory instance = new DaoFactory();
	private AuthorDao authorDao = new AuthorDaoImpl();
	private FilmDao filmDao = new FilmDaoImpl();
	private AccountDao userDao = new AccountDaoImpl();

	private DaoFactory(){}

	public static DaoFactory getInstance(){
		return instance;
	}

	public AuthorDao getAuthorDao() {
		return authorDao;
	}

	public FilmDao getFilmDao() {
		return filmDao;
	}

	public AccountDao getAccountDao() {
		return userDao;
	}

}
