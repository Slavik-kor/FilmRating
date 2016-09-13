package by.epam.karotki.film_rating.service.impl;

import java.util.List;

import by.epam.karotki.film_rating.dao.CommentDao;
import by.epam.karotki.film_rating.dao.Criteria;
import by.epam.karotki.film_rating.dao.DBColumnName;
import by.epam.karotki.film_rating.dao.DaoFactory;
import by.epam.karotki.film_rating.dao.FilmAuthorDao;
import by.epam.karotki.film_rating.dao.FilmDao;
import by.epam.karotki.film_rating.dao.FilmGenreDao;
import by.epam.karotki.film_rating.dao.Operator;
import by.epam.karotki.film_rating.dao.exception.DaoException;
import by.epam.karotki.film_rating.entity.Comment;
import by.epam.karotki.film_rating.entity.Film;
import by.epam.karotki.film_rating.service.FilmService;
import by.epam.karotki.film_rating.service.exception.FilmServiceException;
import by.epam.karotki.film_rating.service.util.ServiceUtil;

public class FilmServiceImpl implements FilmService {
	private static final String ERROR_MESSAGE = "can't get films";
	private static final String ERROR_MESSAGE_VALIDATE = "id field equal zero";

	@Override
	public List<Film> getFilmsByNewest(String lang) throws FilmServiceException {

		List<Film> films = null;
		DaoFactory factory = DaoFactory.getInstance();
		FilmDao fDao = factory.getFilmDao();
		try {
			Criteria criteria = factory.createCriteria();
			criteria.addOrderColumn(DBColumnName.FILM_PREMIER_DATE, false);
			films = fDao.getFilmListByCriteria(criteria, lang);
		} catch (DaoException e) {
			// log
			throw new FilmServiceException(ERROR_MESSAGE, e);
		}
		return films;
	}

	@Override
	public Film getFilmById(int id, String lang) throws FilmServiceException {

		DaoFactory factory = DaoFactory.getInstance();
		FilmDao fDao = factory.getFilmDao();
		Film film = null;
		try {
			Criteria criteria = factory.createCriteria();
			criteria.addCriterion(Operator.EQUAL, DBColumnName.FILM_ID, String.valueOf(id));
			List<Film> filmList = fDao.getFilmListByCriteria(criteria, lang);
			film = filmList.get(0);
		} catch (DaoException e) {
			// log
			throw new FilmServiceException(ERROR_MESSAGE, e);
		}
		return film;
	}

	@Override
	public List<Film> getFilmsByGenre(int idGenre, String lang) throws FilmServiceException {
		if (idGenre == 0) {
			throw new FilmServiceException(ERROR_MESSAGE_VALIDATE);
		}
		List<Film> films = null;
		DaoFactory factory = DaoFactory.getInstance();
		FilmDao fDao = factory.getFilmDao();
		FilmGenreDao fGDao = factory.getFilmGenreDao();
		try {
			Criteria criteria = factory.createCriteria();
			criteria.addCriterion(Operator.EQUAL, DBColumnName.GENRE_ID, String.valueOf(idGenre));

			List<Integer> filmIds = fGDao.getFilmsByGenre(idGenre);
			if (filmIds.size() == 0) {
				return null;
			}

			String[] filmsArray = ServiceUtil.intListToStringArray(filmIds);
			Criteria fCriteria = factory.createCriteria();
			fCriteria.addCriterion(Operator.IN, DBColumnName.FILM_ID, filmsArray);
			fCriteria.addOrderColumn(DBColumnName.FILM_PREMIER_DATE, false);
			films = fDao.getFilmListByCriteria(fCriteria, lang);
		} catch (DaoException e) {
			// log
			throw new FilmServiceException(ERROR_MESSAGE, e);
		}
		return films;
	}

	@Override
	public List<Film> getFilmsByDirector(int idAuthor, String lang) throws FilmServiceException {
		if (idAuthor == 0) {
			throw new FilmServiceException(ERROR_MESSAGE_VALIDATE);
		}
		List<Film> films = null;
		DaoFactory factory = DaoFactory.getInstance();
		FilmDao fDao = factory.getFilmDao();
		FilmAuthorDao fADao = factory.getFilmAuthorDao();
		try {
			Criteria criteria = factory.createCriteria();
			criteria.addCriterion(Operator.EQUAL, DBColumnName.AUTHOR_ID, String.valueOf(idAuthor));
			List<Integer> filmIds = fADao.getFilmsByAuthor(idAuthor, DBColumnName.AUTHOR_ROLE_DIRECTOR);
			if (filmIds.size() == 0) {
				return null;
			}

			String[] filmsArray = ServiceUtil.intListToStringArray(filmIds);
			Criteria fCriteria = factory.createCriteria();
			fCriteria.addCriterion(Operator.IN, DBColumnName.FILM_ID, filmsArray);
			fCriteria.addOrderColumn(DBColumnName.FILM_PREMIER_DATE, false);
			films = fDao.getFilmListByCriteria(fCriteria, lang);
		} catch (DaoException e) {
			// log
			throw new FilmServiceException(ERROR_MESSAGE, e);
		}
		return films;
	}

	@Override
	public List<Film> getFilmsByScenarioWriter(int idAuthor, String lang) throws FilmServiceException {
		if (idAuthor == 0) {
			throw new FilmServiceException(ERROR_MESSAGE_VALIDATE);
		}
		List<Film> films = null;
		DaoFactory factory = DaoFactory.getInstance();
		FilmDao fDao = factory.getFilmDao();
		FilmAuthorDao fADao = factory.getFilmAuthorDao();
		try {
			Criteria criteria = factory.createCriteria();
			criteria.addCriterion(Operator.EQUAL, DBColumnName.AUTHOR_ID, String.valueOf(idAuthor));
			List<Integer> filmIds = fADao.getFilmsByAuthor(idAuthor, DBColumnName.AUTHOR_ROLE_SCENARIOWRITER);
			if (filmIds.size() == 0) {
				return null;
			}

			String[] filmsArray = ServiceUtil.intListToStringArray(filmIds);
			Criteria fCriteria = factory.createCriteria();
			fCriteria.addCriterion(Operator.IN, DBColumnName.FILM_ID, filmsArray);
			fCriteria.addOrderColumn(DBColumnName.FILM_PREMIER_DATE, false);
			films = fDao.getFilmListByCriteria(fCriteria, lang);
		} catch (DaoException e) {
			// log
			throw new FilmServiceException(ERROR_MESSAGE, e);
		}
		return films;
	}

	@Override
	public List<Film> getFilmsByActor(int idAuthor, String lang) throws FilmServiceException {
		if (idAuthor == 0) {
			throw new FilmServiceException(ERROR_MESSAGE_VALIDATE);
		}
		List<Film> films = null;
		DaoFactory factory = DaoFactory.getInstance();
		FilmDao fDao = factory.getFilmDao();
		FilmAuthorDao fADao = factory.getFilmAuthorDao();
		try {
			Criteria criteria = factory.createCriteria();
			criteria.addCriterion(Operator.EQUAL, DBColumnName.AUTHOR_ID, String.valueOf(idAuthor));
			List<Integer> filmIds = fADao.getFilmsByAuthor(idAuthor, DBColumnName.AUTHOR_ROLE_ACTOR);
			if (filmIds.size() == 0) {
				return null;
			}

			String[] filmsArray = ServiceUtil.intListToStringArray(filmIds);
			Criteria fCriteria = factory.createCriteria();
			fCriteria.addCriterion(Operator.IN, DBColumnName.FILM_ID, filmsArray);
			fCriteria.addOrderColumn(DBColumnName.FILM_PREMIER_DATE, false);
			films = fDao.getFilmListByCriteria(fCriteria, lang);
		} catch (DaoException e) {
			// log
			throw new FilmServiceException(ERROR_MESSAGE, e);
		}
		return films;
	}

	@Override
	public List<Film> getFilmsByYear(int year, String lang) throws FilmServiceException {
		List<Film> films = null;
		DaoFactory factory = DaoFactory.getInstance();
		FilmDao fDao = factory.getFilmDao();
		Criteria criteria = factory.createCriteria();
		criteria.addCriterion(Operator.BETWEEN, DBColumnName.FILM_PREMIER_DATE, ""+year+"-01-01", ""+year+"-12-31");
		try{
			films = fDao.getFilmListByCriteria(criteria, lang);
		}catch(DaoException e){
			throw new FilmServiceException(ERROR_MESSAGE, e);
		}
		
		return films;
	}

	@Override
	public List<Film> getFilmsByRating(String lang) throws FilmServiceException {
		List<Film> films = null;
		DaoFactory factory = DaoFactory.getInstance();
		FilmDao fDao = factory.getFilmDao();
		try{
			films = fDao.getTopFilmsByRating(lang);
		}catch(DaoException e){
			throw new FilmServiceException(ERROR_MESSAGE, e);
		}
		
		return films;
	}

	@Override
	public List<Film> getFilmsByComments(int idAccount, String lang) throws FilmServiceException {
		List<Film> films = null;
		DaoFactory factory = DaoFactory.getInstance();
		FilmDao fDao = factory.getFilmDao();
		CommentDao cDao = factory.getCommentDao();
		try{
			Criteria criteria = factory.createCriteria();
			criteria.addCriterion(Operator.EQUAL, DBColumnName.COMMENT_ACCOUNT_ID, String.valueOf(idAccount));
			
			List<Comment> commentList = cDao.getCommentsByCriteria(criteria);
			if(commentList==null){return null;}
			String[] idFilmsArray = new String[commentList.size()];
			for(int i=0;i<commentList.size();i++){
				idFilmsArray[i] = String.valueOf(commentList.get(i).getFilmId());
			}
			Criteria fCriteria = factory.createCriteria();
			fCriteria.addCriterion(Operator.IN, DBColumnName.FILM_ID, idFilmsArray);
			films = fDao.getFilmListByCriteria(fCriteria, lang);
		}catch(DaoException e){
			throw new FilmServiceException(ERROR_MESSAGE, e);
		}
		
		return films;
	}

	@Override
	public List<Film> getFilmsByAccountRate(int idAccount, String lang) throws FilmServiceException {
		List<Film> films = null;
		DaoFactory factory = DaoFactory.getInstance();
		FilmDao fDao = factory.getFilmDao();
		CommentDao cDao = factory.getCommentDao();
		try{
			Criteria criteria = factory.createCriteria();
			criteria.addCriterion(Operator.EQUAL, DBColumnName.COMMENT_ACCOUNT_ID, String.valueOf(idAccount));
			criteria.addOrderColumn(DBColumnName.COMMENT_RATE, false);
			List<Comment> commentList = cDao.getCommentsByCriteria(criteria);
			if(commentList==null){return null;}
			String[] idFilmsArray = new String[commentList.size()];
			for(int i=0;i<commentList.size();i++){
				idFilmsArray[i] = String.valueOf(commentList.get(i).getFilmId());
			}
			Criteria fCriteria = factory.createCriteria();
			fCriteria.addCriterion(Operator.IN, DBColumnName.FILM_ID, idFilmsArray);
			films = fDao.getFilmListByCriteria(fCriteria, lang);
		}catch(DaoException e){
			throw new FilmServiceException(ERROR_MESSAGE, e);
		}
		return films;
	}

}
