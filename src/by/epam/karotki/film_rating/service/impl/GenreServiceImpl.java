package by.epam.karotki.film_rating.service.impl;

import java.util.List;

import by.epam.karotki.film_rating.dao.DaoFactory;
import by.epam.karotki.film_rating.dao.GenreDao;
import by.epam.karotki.film_rating.dao.exception.DaoException;
import by.epam.karotki.film_rating.entity.Genre;
import by.epam.karotki.film_rating.service.GenreService;
import by.epam.karotki.film_rating.service.exception.GenreServiceException;

public class GenreServiceImpl implements GenreService {

	@Override
	public List<Genre> getGenreListByFilm(int idFilm) throws GenreServiceException {
		List<Genre> genreList = null;
		DaoFactory dao = DaoFactory.getInstance();
		GenreDao gDao = dao.getGenreDao();
		try{
			genreList = gDao.getGenreListByFilm(idFilm);
		}catch(DaoException e) {
			// log
			throw new GenreServiceException("can't get film genres", e);
		}
		return genreList;
	}

}
