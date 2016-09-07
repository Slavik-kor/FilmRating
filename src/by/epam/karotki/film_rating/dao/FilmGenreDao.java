package by.epam.karotki.film_rating.dao;

import java.util.List;

import by.epam.karotki.film_rating.dao.exception.DaoException;

public interface FilmGenreDao {
	
	List<Integer> getFilmsByGenre(int idGenre) throws DaoException;
	
	List<Integer> getGenresByFilm(int idFilm) throws DaoException;
	
	void addGenresToFilm(int idFilm, int idGenre) throws DaoException;
	
	void deleteGenresFromFilm(int idFilm, int idGenre) throws DaoException;

}
