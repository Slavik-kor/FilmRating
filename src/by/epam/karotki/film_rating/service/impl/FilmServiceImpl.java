package by.epam.karotki.film_rating.service.impl;

import java.util.ArrayList;
import java.util.List;

import by.epam.karotki.film_rating.dao.DBColumnNames;
import by.epam.karotki.film_rating.dao.DaoFactory;
import by.epam.karotki.film_rating.dao.FilmDao;
import by.epam.karotki.film_rating.dao.Operator;
import by.epam.karotki.film_rating.dao.exception.DaoException;
import by.epam.karotki.film_rating.dao.impl.CriteriaImpl;
import by.epam.karotki.film_rating.entity.Film;
import by.epam.karotki.film_rating.service.FilmService;
import by.epam.karotki.film_rating.service.exception.FilmServiceException;

public class FilmServiceImpl implements FilmService {

	@Override
	public List<Film> getFilmsByNewest(int value,String lang) throws FilmServiceException {
		if (value == 0) {
			throw new FilmServiceException("wrong value of film list");
		}
		List<Film> films = new ArrayList<Film>();
		DaoFactory factory = DaoFactory.getInstance();
		FilmDao fDao = factory.getFilmDao();
		try {
			CriteriaImpl criteria = new CriteriaImpl();
			films = fDao.getFilmListByCriteria(criteria, lang);
		} catch (DaoException e) {
			// log
			throw new FilmServiceException("can't get newest films", e);
		}
		return films;
	}

	@Override
	public Film getFilmById(int id, String lang) throws FilmServiceException {
		DaoFactory factory = DaoFactory.getInstance();
		FilmDao fDao = factory.getFilmDao();
		Film film = null;
		try {
			CriteriaImpl criteria = new CriteriaImpl();
			criteria.addCriterion(Operator.EQUAL, DBColumnNames.FILM_ID, String.valueOf(id));
			List<Film> filmList = fDao.getFilmListByCriteria(criteria, lang);
			film = filmList.get(0);
		} catch (DaoException e) {
			// log
			throw new FilmServiceException("can't get film by id", e);
		}
		return film;
	}

}
