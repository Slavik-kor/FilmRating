package by.epam.karotki.film_rating.dao;

import java.util.List;

import by.epam.karotki.film_rating.dao.exception.CountryDaoException;
import by.epam.karotki.film_rating.entity.Country;

public interface CountryDao {
	List<Country> getCountryByFilm(int idFilm) throws CountryDaoException;
	
	Country getCountryById(int idCountry) throws CountryDaoException;
	
	List<Country> getCountryByCriteria(Criteria criteria,String lang) throws CountryDaoException;
	
	void addCountry(Country country) throws CountryDaoException;
	
	void addCountry(Country country,String lang) throws CountryDaoException;
	
	void deleteCountryById(int idCountry) throws CountryDaoException;
	
	void deleteCountryById(int idCountry,String lang) throws CountryDaoException;
	
	void updateCountry(Country country) throws CountryDaoException;
	
	void updateCountry(Country country,String lang) throws CountryDaoException;

}
