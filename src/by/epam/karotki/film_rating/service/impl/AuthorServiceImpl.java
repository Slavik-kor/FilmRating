package by.epam.karotki.film_rating.service.impl;

import java.util.List;

import by.epam.karotki.film_rating.dao.AuthorDao;
import by.epam.karotki.film_rating.dao.DaoFactory;
import by.epam.karotki.film_rating.dao.exception.DaoException;
import by.epam.karotki.film_rating.entity.Author;
import by.epam.karotki.film_rating.service.AuthorService;
import by.epam.karotki.film_rating.service.exception.ServiceException;

public class AuthorServiceImpl implements AuthorService {
	private static final String DIRECTOR = "Director";
	private static final String SCENARIO_WRITER = "ScenarioWriter";
	private static final String ACTOR = "Actor";

	@Override
	public List<Author> getDirectorsByFilm(int idFilm) throws ServiceException {
		List<Author> authorList = null;
		DaoFactory dao = DaoFactory.getInstance();
		AuthorDao aDao = dao.getAuthorDao();
		try {
			authorList = aDao.getAuthorListByFilm(idFilm,	DIRECTOR);
		} catch (DaoException e) {
			// log
			throw new ServiceException("can't get directors by film id", e);
		}
		return authorList;
	}

	@Override
	public List<Author> getScenarioWritersByFilm(int idFilm) throws ServiceException {
		List<Author> authorList = null;
		DaoFactory dao = DaoFactory.getInstance();
		AuthorDao aDao = dao.getAuthorDao();
		try {
			authorList = aDao.getAuthorListByFilm(idFilm,	SCENARIO_WRITER);
		} catch (DaoException e) {
			// log
			throw new ServiceException("can't get scenariowriters by film id", e);
		}
		return authorList;
	}

	@Override
	public List<Author> getActorByFilm(int idFilm) throws ServiceException {
		List<Author> authorList = null;
		DaoFactory dao = DaoFactory.getInstance();
		AuthorDao aDao = dao.getAuthorDao();
		try {
			authorList = aDao.getAuthorListByFilm(idFilm,	ACTOR);
		} catch (DaoException e) {
			// log
			throw new ServiceException("can't get actors by film id", e);
		}
		return authorList;
	}

	@Override
	public Author getAuthorById(int idAuthor) throws ServiceException {
		Author author = null;
		DaoFactory dao = DaoFactory.getInstance();
		AuthorDao aDao = dao.getAuthorDao();
		try{
			author = aDao.getAuthorById(idAuthor);
		}catch(DaoException e){
			// log
			throw new ServiceException("can't get author by id", e);
		}
		return author;
	}

}
