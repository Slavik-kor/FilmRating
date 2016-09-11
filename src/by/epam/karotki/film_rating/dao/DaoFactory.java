package by.epam.karotki.film_rating.dao;

import by.epam.karotki.film_rating.dao.impl.AccountDaoImpl;
import by.epam.karotki.film_rating.dao.impl.AuthorDaoImpl;
import by.epam.karotki.film_rating.dao.impl.CommentDaoImpl;
import by.epam.karotki.film_rating.dao.impl.CountryDaoImpl;
import by.epam.karotki.film_rating.dao.impl.CriteriaImpl;
import by.epam.karotki.film_rating.dao.impl.FilmAuthorDaoImpl;
import by.epam.karotki.film_rating.dao.impl.FilmCountryDaoImpl;
import by.epam.karotki.film_rating.dao.impl.FilmDaoImpl;
import by.epam.karotki.film_rating.dao.impl.FilmGenreDaoImpl;
import by.epam.karotki.film_rating.dao.impl.GenreDaoImpl;
import by.epam.karotki.film_rating.dao.impl.InitDaoImpl;

public class DaoFactory {
	private static final DaoFactory instance = new DaoFactory();
	private AuthorDao authorDao = new AuthorDaoImpl();
	private FilmDao filmDao = new FilmDaoImpl();
	private AccountDao userDao = new AccountDaoImpl();
	private CountryDao countryDao = new CountryDaoImpl();
	private GenreDao genreDao = new GenreDaoImpl();
	private CommentDao commentDao = new CommentDaoImpl();
	private FilmGenreDao filmGenreDao = new FilmGenreDaoImpl();
	private FilmCountryDao filmCountryDao = new FilmCountryDaoImpl();
	private FilmAuthorDao filmAuthorDao = new FilmAuthorDaoImpl();
	private InitDao initDao = new InitDaoImpl();

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
	
	public FilmCountryDao getFilmCountryDao(){
		return filmCountryDao;
	}
	
	public FilmAuthorDao getFilmAuthorDao(){
		return filmAuthorDao;
	}
	
	public InitDao getInitDao(){
		return initDao;
	}
	
	public Criteria createCriteria(){
		return new CriteriaImpl();
	}

}
