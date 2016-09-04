package by.epam.karotki.film_rating.service;

import java.util.List;

import by.epam.karotki.film_rating.entity.Country;
import by.epam.karotki.film_rating.service.exception.CountryServiceException;

public interface CountryService {
	
	List<Country> getCountriesByFilm(int idFilm) throws CountryServiceException;
	
	Country getCountryById(int idFilm) throws CountryServiceException;

}
