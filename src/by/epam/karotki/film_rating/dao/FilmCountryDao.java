package by.epam.karotki.film_rating.dao;

import java.util.List;

import by.epam.karotki.film_rating.dao.exception.DaoException;

public interface FilmCountryDao {
	
	List<Integer> getFilmsByCountry(int idCountry) throws DaoException;
	
	List<Integer> getCountriesByFilm(int idFilm) throws DaoException;
    
	void addCountriesToFilm(int idFilm, int idCountry) throws DaoException;
	
	void deleteCountriesFromFilm(int idFilm, int idCountry) throws DaoException;
}
