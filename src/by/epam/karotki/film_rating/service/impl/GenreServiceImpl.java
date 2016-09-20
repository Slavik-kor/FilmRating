package by.epam.karotki.film_rating.service.impl;

import java.util.List;

import by.epam.karotki.film_rating.dao.Criteria;
import by.epam.karotki.film_rating.dao.DBColumnName;
import by.epam.karotki.film_rating.dao.DaoFactory;
import by.epam.karotki.film_rating.dao.FilmGenreDao;
import by.epam.karotki.film_rating.dao.GenreDao;
import by.epam.karotki.film_rating.dao.Operator;
import by.epam.karotki.film_rating.dao.exception.DaoException;
import by.epam.karotki.film_rating.entity.Genre;
import by.epam.karotki.film_rating.service.GenreService;
import by.epam.karotki.film_rating.service.exception.GenreServiceException;
import by.epam.karotki.film_rating.service.util.ServiceUtil;

public class GenreServiceImpl implements GenreService {
	private static final String ERROR_MESSAGE = "can't get film genres";

	@Override
	public List<Genre> getGenreListByFilm(int idFilm, String lang) throws GenreServiceException {
		List<Genre> genreList = null;
		DaoFactory dao = DaoFactory.getInstance();
		GenreDao gDao = dao.getGenreDao();
		FilmGenreDao fGDao = dao.getFilmGenreDao();
		try {
			List<Integer> genreIds = fGDao.getGenresByFilm(idFilm);
			if (genreIds.size() == 0) {
				return null;
			}
			String[] strGMas = ServiceUtil.intListToStringArray(genreIds);

			Criteria criteria = dao.createCriteria();
			criteria.addCriterion(Operator.IN, DBColumnName.GENRE_ID, strGMas);
			genreList = gDao.getGenreByCriteria(criteria, lang);
		} catch (DaoException e) {
			// log
			throw new GenreServiceException(ERROR_MESSAGE, e);
		}
		return genreList;
	}

	@Override
	public void addGenreToFilm(int idFilm, String[] idGenre) throws GenreServiceException {
		
		DaoFactory dao = DaoFactory.getInstance();
		FilmGenreDao fGDao = dao.getFilmGenreDao();
		
		try {
			
			for (int i = 0; i < idGenre.length; i++) {
				fGDao.addGenresToFilm(idFilm, Integer.valueOf(idGenre[i]));
			}
		} catch (DaoException e) {
			// log
			throw new GenreServiceException("can't add genre to film", e);
		}

	}

	@Override
	public List<Genre> getAllGenres(String lang) throws GenreServiceException {
		List<Genre> genreList = null;
		DaoFactory dao = DaoFactory.getInstance();
		GenreDao gDao = dao.getGenreDao();
		try {
			Criteria criteria = dao.createCriteria();
			genreList = gDao.getGenreByCriteria(criteria, lang);
		} catch (DaoException e) {
			// log
			throw new GenreServiceException(ERROR_MESSAGE, e);
		}
		return genreList;
	}

}
