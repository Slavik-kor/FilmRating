package by.epam.karotki.film_rating.dao;

import by.epam.karotki.film_rating.dao.impl.AccountDaoImpl;
import by.epam.karotki.film_rating.dao.impl.AuthorDaoImpl;
import by.epam.karotki.film_rating.dao.impl.FilmDaoImpl;

public class DaoFactory {
	private static final DaoFactory instance = new DaoFactory();
	private IAuthorDao authorDao = new AuthorDaoImpl();
	private IFilmDao filmDao = new FilmDaoImpl();
	private IAccountDao userDao = new AccountDaoImpl();

	private DaoFactory(){}

	public static DaoFactory getInstance(){
		return instance;
	}

	public IAuthorDao getAuthorDao() {
		return authorDao;
	}

	public IFilmDao getFilmDao() {
		return filmDao;
	}

	public IAccountDao getAccountDao() {
		return userDao;
	}

}
