package by.epam.karotki.film_rating.service;

import java.util.List;

import by.epam.karotki.film_rating.entity.Country;
import by.epam.karotki.film_rating.service.exception.ServiceException;

public interface CountryService {
	
	List<Country> getCountriesByFilm(int idFilm) throws ServiceException;

}
