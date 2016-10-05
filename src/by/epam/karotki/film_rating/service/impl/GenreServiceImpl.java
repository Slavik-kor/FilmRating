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
		validate(idFilm,lang);
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
	public void addGenreToFilm(int idFilm, List<Integer> idGenre) throws GenreServiceException {
		validate(idFilm,idGenre);
		DaoFactory dao = DaoFactory.getInstance();
		FilmGenreDao fGDao = dao.getFilmGenreDao();

		try {
			List<Integer> genresDB = fGDao.getGenresByFilm(idFilm);
			for (int i = 0; i < idGenre.size(); i++) {
				Integer genre =idGenre.get(i);
				if ((genresDB!=null) && (!genresDB.contains(genre))) {
					fGDao.addGenresToFilm(idFilm, genre);
				}
			}
			
			for (int i = 0; i < genresDB.size(); i++) {
				if(!idGenre.contains(genresDB.get(i))){
					fGDao.deleteGenresFromFilm(idFilm, genresDB.get(i));
				}
			}

		} catch (DaoException e) {
			// log
			throw new GenreServiceException("can't add genre to film", e);
		}

	}

	@Override
	public List<Genre> getAllGenres(String lang) throws GenreServiceException {
		validate(lang);
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
	
	private void validate(int intValue, String stringValue) throws GenreServiceException{
		if((intValue == 0)||(stringValue == null)){
			throw new GenreServiceException("incorrect initial data");
		}
	}
	
	private void validate(int intValue, List<Integer> listValue) throws GenreServiceException{
		if((intValue == 0)||(listValue == null)){
			throw new GenreServiceException("incorrect initial data");
		}
	}
	
	private void validate(Object...value) throws GenreServiceException{
		for(Object i : value){
		if(i == null){
			throw new GenreServiceException("incorrect initial data");
		}
		}
	}

}
