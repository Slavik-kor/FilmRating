package by.epam.karotki.film_rating.service.impl;

import java.util.ArrayList;
import java.util.List;

import by.epam.karotki.film_rating.dao.DaoFactory;
import by.epam.karotki.film_rating.dao.FilmDao;
import by.epam.karotki.film_rating.dao.exception.DaoException;
import by.epam.karotki.film_rating.entity.Film;
import by.epam.karotki.film_rating.service.FilmService;
import by.epam.karotki.film_rating.service.exception.ServiceException;

public class FilmServiceImpl implements FilmService {

	@Override
	public List<Film> getFilmsByNewest(int value) throws ServiceException {
		if (value==0) {throw new ServiceException("wrong number of film list");}
		List<Film> films = new ArrayList<Film>();
		DaoFactory factory = DaoFactory.getInstance();
		FilmDao fDao = factory.getFilmDao();
		try {
			films = fDao.getNewestFilms(value);
		} catch (DaoException e) {
			//log
			throw new ServiceException("can't get newest films",e);
		}
		return films;
	}

}
