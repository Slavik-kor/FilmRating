package by.epam.karotki.film_rating.dao;

import by.epam.karotki.film_rating.dao.impl.AccountDaoImpl;
import by.epam.karotki.film_rating.dao.impl.AuthorDaoImpl;
import by.epam.karotki.film_rating.dao.impl.CommentDaoImpl;
import by.epam.karotki.film_rating.dao.impl.CountryDaoImpl;
import by.epam.karotki.film_rating.dao.impl.FilmDaoImpl;
import by.epam.karotki.film_rating.dao.impl.FilmGenreDaoImpl;
import by.epam.karotki.film_rating.dao.impl.GenreDaoImpl;

public class DaoFactory {
	private static final DaoFactory instance = new DaoFactory();
	private AuthorDao authorDao = new AuthorDaoImpl();
	private FilmDao filmDao = new FilmDaoImpl();
	private AccountDao userDao = new AccountDaoImpl();
	private CountryDao countryDao = new CountryDaoImpl();
	private GenreDao genreDao = new GenreDaoImpl();
	private CommentDao commentDao = new CommentDaoImpl();
	private FilmGenreDao filmGenreDao = new FilmGenreDaoImpl();

	private DaoFactory(){
		
	}

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
	
	public CountryDao getCountryDao(){
		return countryDao;
	}
	
	public GenreDao getGenreDao(){
		return genreDao;
	}
	
	public CommentDao getCommentDao(){
		return commentDao;
	}
	
	public FilmGenreDao getFilmGenreDao(){
		return filmGenreDao;
	}

}
