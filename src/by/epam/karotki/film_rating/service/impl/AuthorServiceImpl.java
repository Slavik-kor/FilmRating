package by.epam.karotki.film_rating.service.impl;

import java.util.List;

import by.epam.karotki.film_rating.dao.AuthorDao;
import by.epam.karotki.film_rating.dao.Criteria;
import by.epam.karotki.film_rating.dao.DBColumnName;
import by.epam.karotki.film_rating.dao.DaoFactory;
import by.epam.karotki.film_rating.dao.FilmAuthorDao;
import by.epam.karotki.film_rating.dao.Operator;
import by.epam.karotki.film_rating.dao.exception.DaoException;
import by.epam.karotki.film_rating.entity.Author;
import by.epam.karotki.film_rating.service.AuthorService;
import by.epam.karotki.film_rating.service.exception.AuthorServiceException;
import by.epam.karotki.film_rating.service.util.ServiceUtil;

public class AuthorServiceImpl implements AuthorService {
	private static final String DIRECTOR = "Director";
	private static final String SCENARIO_WRITER = "ScenarioWriter";
	private static final String ACTOR = "Actor";
	private static final String ERROR_MESSAGE_DIR = "can't get directors by film id";
	private static final String ERROR_MESSAGE_SC = "can't get scenariowriters by film id";
	private static final String ERROR_MESSAGE_ACT = "can't get actors by film id";
	private static final String ERROR_MESSAGE_AUT = "can't get author by id";

	@Override
	public List<Author> getDirectorsByFilm(int idFilm, String lang) throws AuthorServiceException {
		List<Author> authorList = null;
		DaoFactory dao = DaoFactory.getInstance();
		AuthorDao aDao = dao.getAuthorDao();
		FilmAuthorDao fADao = dao.getFilmAuthorDao();
		try {
			List<Integer> authorIdList = fADao.getAuthorsByFilm(idFilm, DIRECTOR);
			if (authorIdList.size() == 0) {
				return null;
			}
			String[] authorArray = ServiceUtil.intListToStringArray(authorIdList);
			Criteria criteria = dao.createCriteria();
			criteria.addCriterion(Operator.EQUAL, DBColumnName.AUTHOR_ID, authorArray);
			authorList = aDao.getAuthorByCriteria(criteria, lang);
		} catch (DaoException e) {
			// log
			throw new AuthorServiceException(ERROR_MESSAGE_DIR, e);
		}
		return authorList;
	}

	@Override
	public List<Author> getScenarioWritersByFilm(int idFilm, String lang) throws AuthorServiceException {
		List<Author> authorList = null;
		DaoFactory dao = DaoFactory.getInstance();
		AuthorDao aDao = dao.getAuthorDao();
		FilmAuthorDao fADao = dao.getFilmAuthorDao();
		try {
			List<Integer> authorIdList = fADao.getAuthorsByFilm(idFilm, SCENARIO_WRITER);
			if (authorIdList.size() == 0) {
				return null;
			}
			String[] authorArray = ServiceUtil.intListToStringArray(authorIdList);
			Criteria criteria = dao.createCriteria();
			criteria.addCriterion(Operator.EQUAL, DBColumnName.AUTHOR_ID, authorArray);
			authorList = aDao.getAuthorByCriteria(criteria, lang);
		} catch (DaoException e) {
			// log
			throw new AuthorServiceException(ERROR_MESSAGE_SC, e);
		}
		return authorList;
	}

	@Override
	public List<Author> getActorByFilm(int idFilm, String lang) throws AuthorServiceException {
		List<Author> authorList = null;
		DaoFactory dao = DaoFactory.getInstance();
		AuthorDao aDao = dao.getAuthorDao();
		FilmAuthorDao fADao = dao.getFilmAuthorDao();
		try {
			List<Integer> authorIdList = fADao.getAuthorsByFilm(idFilm, ACTOR);
			if (authorIdList.size() == 0) {
				return null;
			}
			String[] authorArray = ServiceUtil.intListToStringArray(authorIdList);
			Criteria criteria = dao.createCriteria();
			criteria.addCriterion(Operator.EQUAL, DBColumnName.AUTHOR_ID, authorArray);
			authorList = aDao.getAuthorByCriteria(criteria, lang);
		} catch (DaoException e) {
			// log
			throw new AuthorServiceException(ERROR_MESSAGE_ACT, e);
		}
		return authorList;
	}

	@Override
	public Author getAuthorById(int idAuthor, String lang) throws AuthorServiceException {
		List<Author> author = null;
		DaoFactory dao = DaoFactory.getInstance();
		AuthorDao aDao = dao.getAuthorDao();
		try {
			Criteria criteria = dao.createCriteria();
			criteria.addCriterion(Operator.EQUAL, DBColumnName.AUTHOR_ID, String.valueOf(idAuthor));
			author = aDao.getAuthorByCriteria(criteria, lang);
		} catch (DaoException e) {
			// log
			throw new AuthorServiceException(ERROR_MESSAGE_AUT, e);
		}
		return author.get(0);
	}

}
