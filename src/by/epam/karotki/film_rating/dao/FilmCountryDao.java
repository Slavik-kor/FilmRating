package by.epam.karotki.film_rating.dao;

import by.epam.karotki.film_rating.dao.exception.DaoException;

public interface FilmCountryDao {
    
	void addGenresToFilm(int idFilm, int idCountry) throws DaoException;
	
	void deleteGenresFromFilm(int idFilm, int idCountry) throws DaoException;
}
