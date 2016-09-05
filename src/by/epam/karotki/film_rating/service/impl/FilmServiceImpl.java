package by.epam.karotki.film_rating.service.impl;

import java.util.ArrayList;
import java.util.List;

import by.epam.karotki.film_rating.dao.DaoFactory;
import by.epam.karotki.film_rating.dao.FilmDao;
import by.epam.karotki.film_rating.dao.exception.DaoException;
import by.epam.karotki.film_rating.dao.util.Criteria;
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
			Criteria criteria = new Criteria();
			films = fDao.getFilmListByCriteria(criteria, lang);
		} catch (DaoException e) {
			// log
			throw new FilmServiceException("can't get newest films", e);
		}
		return films;
	}

	@Override
	public Film getFilmById(int id) throws FilmServiceException {
		DaoFactory factory = DaoFactory.getInstance();
		FilmDao fDao = factory.getFilmDao();
		Film film = null;
		try {
			film = fDao.getFilmById(id);
		} catch (DaoException e) {
			// log
			throw new FilmServiceException("can't get film by id", e);
		}
		return film;
	}

}
