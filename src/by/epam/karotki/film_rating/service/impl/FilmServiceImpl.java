package by.epam.karotki.film_rating.service.impl;

import java.util.List;

import by.epam.karotki.film_rating.dao.Criteria;
import by.epam.karotki.film_rating.dao.DBColumnNames;
import by.epam.karotki.film_rating.dao.DaoFactory;
import by.epam.karotki.film_rating.dao.FilmDao;
import by.epam.karotki.film_rating.dao.FilmGenreDao;
import by.epam.karotki.film_rating.dao.Operator;
import by.epam.karotki.film_rating.dao.exception.DaoException;
import by.epam.karotki.film_rating.entity.Film;
import by.epam.karotki.film_rating.service.FilmService;
import by.epam.karotki.film_rating.service.exception.FilmServiceException;

public class FilmServiceImpl implements FilmService {
	private static final String ERROR_MESSAGE = "can't get films";
	private static final String ERROR_MESSAGE_VALIDATE = "value field equal zero of film list";

	@Override
	public List<Film> getFilmsByNewest(int value,String lang) throws FilmServiceException {
		if (value == 0) {
			throw new FilmServiceException(ERROR_MESSAGE_VALIDATE);
		}
		List<Film> films = null;
		DaoFactory factory = DaoFactory.getInstance();
		FilmDao fDao = factory.getFilmDao();
		try {
			Criteria criteria = factory.createCriteria();
			criteria.addOrderColumn(DBColumnNames.FILM_PREMIER_DATE, false);
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
			criteria.addCriterion(Operator.EQUAL, DBColumnNames.FILM_ID, String.valueOf(id));
			List<Film> filmList = fDao.getFilmListByCriteria(criteria, lang);
			film = filmList.get(0);
		} catch (DaoException e) {
			// log
			throw new FilmServiceException(ERROR_MESSAGE, e);
		}
		return film;
	}

	@Override
	public List<Film> getFilmsByGenre(int idGenre, int value, String lang) throws FilmServiceException {
		if ((idGenre == 0) || (value == 0)) {
			throw new FilmServiceException("wrong value of film list");
		}
		List<Film> films = null;
		DaoFactory factory = DaoFactory.getInstance();
		FilmDao fDao = factory.getFilmDao();
		FilmGenreDao fGDao = factory.getFilmGenreDao();
		try {
			Criteria criteria = factory.createCriteria();
			criteria.addCriterion(Operator.EQUAL, DBColumnNames.GENRE_ID, String.valueOf(idGenre));
			List<Integer> filmIds = fGDao.getFilmsByGenre(idGenre);
			String[] filmsArray = new String[filmIds.size()];
			for(int i=0;i<filmsArray.length;i++){
				filmsArray[i] = String.valueOf(filmIds.get(i));
			}
			Criteria fCriteria = factory.createCriteria();
			fCriteria.addCriterion(Operator.IN, DBColumnNames.FILM_ID, filmsArray);
			criteria.addOrderColumn(DBColumnNames.FILM_PREMIER_DATE, false);
			System.out.println(fCriteria.getClause());
			films = fDao.getFilmListByCriteria(criteria, lang);
		} catch (DaoException e) {
			// log
			throw new FilmServiceException(ERROR_MESSAGE, e);
		}
		return films;
	}


}
