package by.epam.karotki.film_rating.service.impl;

import java.util.List;

import by.epam.karotki.film_rating.dao.DaoFactory;
import by.epam.karotki.film_rating.dao.FilmDao;
import by.epam.karotki.film_rating.dao.GenreDao;
import by.epam.karotki.film_rating.dao.exception.DaoException;
import by.epam.karotki.film_rating.dao.util.Criteria;
import by.epam.karotki.film_rating.dao.util.DBColumnNames;
import by.epam.karotki.film_rating.dao.util.Operator;
import by.epam.karotki.film_rating.entity.Film;
import by.epam.karotki.film_rating.entity.Genre;
import by.epam.karotki.film_rating.service.GenreService;
import by.epam.karotki.film_rating.service.exception.GenreServiceException;

public class GenreServiceImpl implements GenreService {

	@Override
	public List<Genre> getGenreListByFilm(int idFilm,String lang) throws GenreServiceException {
		List<Genre> genreList = null;
		DaoFactory dao = DaoFactory.getInstance();
		GenreDao gDao = dao.getGenreDao();
		FilmDao fDao = dao.getFilmDao();
		try{
			Criteria fCriteria = new Criteria();
			fCriteria.addCriterion(Operator.EQUAL,DBColumnNames.FILM_ID, String.valueOf(idFilm));
			Film film = fDao.getFilmListByCriteria(fCriteria, lang).get(0);
			int id = film.getId();
			Criteria criteria = new Criteria();
			criteria.addCriterion(Operator.EQUAL, DBColumnNames.GENRE_ID, String.valueOf(id));
			genreList = gDao.getGenreByCriteria(criteria, lang);
		}catch(DaoException e) {
			// log
			throw new GenreServiceException("can't get film genres", e);
		}
		return genreList;
	}

}
