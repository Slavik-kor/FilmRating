package by.epam.karotki.film_rating.service.impl;

import java.util.ArrayList;
import java.util.List;

import by.epam.karotki.film_rating.dao.CountryDao;
import by.epam.karotki.film_rating.dao.DaoFactory;
import by.epam.karotki.film_rating.dao.exception.DaoException;
import by.epam.karotki.film_rating.entity.Country;
import by.epam.karotki.film_rating.service.CountryService;
import by.epam.karotki.film_rating.service.exception.ServiceException;

public class CountryServiceImpl implements CountryService {

	@Override
	public List<Country> getCountriesByFilm(int idFilm) throws ServiceException {
		List<Country> countryList = new ArrayList<Country>();
		DaoFactory dao = DaoFactory.getInstance();
		CountryDao cDao = dao.getCountryDao(); 
		try {
			countryList = cDao.getCountryByFilm(idFilm);
		} catch (DaoException e) {
			// log
			throw new ServiceException("can't get film countries", e);
		}
		return countryList;
	}

	@Override
	public Country getCountryById(int idFilm) throws ServiceException {
		Country country = null;
		DaoFactory dao = DaoFactory.getInstance();
		CountryDao cDao = dao.getCountryDao();
		try {
			country = cDao.getCountryById(idFilm);
		} catch (DaoException e) {
			// log
			throw new ServiceException("can't get author country", e);
		}
		return country;
	}

}
