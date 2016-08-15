package by.epam.karotki.film_rating.dao;

import java.util.List;

import by.epam.karotki.film_rating.dao.exception.DaoException;
import by.epam.karotki.film_rating.entity.Country;

public interface CountryDao {
	List<Country> getCountryByFilm(int idFilm) throws DaoException;
	
	Country getCountryById(int idFilm) throws DaoException;
}
