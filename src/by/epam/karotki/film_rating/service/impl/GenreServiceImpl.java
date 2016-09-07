package by.epam.karotki.film_rating.service.impl;

import java.util.List;

import by.epam.karotki.film_rating.dao.DBColumnNames;
import by.epam.karotki.film_rating.dao.DaoFactory;
import by.epam.karotki.film_rating.dao.FilmGenreDao;
import by.epam.karotki.film_rating.dao.GenreDao;
import by.epam.karotki.film_rating.dao.Operator;
import by.epam.karotki.film_rating.dao.exception.DaoException;
import by.epam.karotki.film_rating.dao.impl.CriteriaImpl;
import by.epam.karotki.film_rating.entity.Genre;
import by.epam.karotki.film_rating.service.GenreService;
import by.epam.karotki.film_rating.service.exception.GenreServiceException;

public class GenreServiceImpl implements GenreService {

	@Override
	public List<Genre> getGenreListByFilm(int idFilm, String lang) throws GenreServiceException {
		List<Genre> genreList = null;
		DaoFactory dao = DaoFactory.getInstance();
		GenreDao gDao = dao.getGenreDao();
		FilmGenreDao fGDao = dao.getFilmGenreDao();
		try {
			List<Integer> genreIds = fGDao.getGenresByFilm(idFilm);
			Object[] cMas = genreIds.toArray();
			String[] strGMas = new String[cMas.length];
			for (int i = 0; i < strGMas.length; i++) {
				strGMas[i] = String.valueOf(cMas[i]);
			}
			CriteriaImpl criteria = new CriteriaImpl();
			criteria.addCriterion(Operator.IN, DBColumnNames.GENRE_ID, strGMas);
			genreList = gDao.getGenreByCriteria(criteria, lang);
		} catch (DaoException e) {
			// log
			throw new GenreServiceException("can't get film genres", e);
		}
		return genreList;
	}

}
