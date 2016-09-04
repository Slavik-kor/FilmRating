package by.epam.karotki.film_rating.dao;

import by.epam.karotki.film_rating.dao.exception.DaoException;

public interface FilmGenreDao {
	
	void addGenresToFilm(int idFilm, int idGenre) throws DaoException;
	
	void deleteGenresFromFilm(int idFilm, int idGenre) throws DaoException;

}
