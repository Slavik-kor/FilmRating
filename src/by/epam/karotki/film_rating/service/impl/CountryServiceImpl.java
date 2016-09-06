package by.epam.karotki.film_rating.service.impl;

import java.util.ArrayList;
import java.util.List;

import by.epam.karotki.film_rating.dao.CountryDao;
import by.epam.karotki.film_rating.dao.DaoFactory;
import by.epam.karotki.film_rating.dao.FilmDao;
import by.epam.karotki.film_rating.dao.exception.DaoException;
import by.epam.karotki.film_rating.dao.util.Criteria;
import by.epam.karotki.film_rating.dao.util.DBColumnNames;
import by.epam.karotki.film_rating.dao.util.Operator;
import by.epam.karotki.film_rating.entity.Country;
import by.epam.karotki.film_rating.entity.Film;
import by.epam.karotki.film_rating.service.CountryService;
import by.epam.karotki.film_rating.service.exception.CountryServiceException;

public class CountryServiceImpl implements CountryService {

	@Override
	public List<Country> getCountriesByFilm(int idFilm,String lang) throws CountryServiceException {
		List<Country> countryList = new ArrayList<Country>();
		DaoFactory dao = DaoFactory.getInstance();
		CountryDao cDao = dao.getCountryDao();
		FilmDao fDao = dao.getFilmDao();
		try {
			Criteria fCriteria = new Criteria();
			fCriteria.addCriterion(Operator.EQUAL, DBColumnNames.FILM_ID, String.valueOf(idFilm));
			Film film = fDao.getFilmListByCriteria(fCriteria, lang).get(0);
			int id = film.getId();
			Criteria criteria = new Criteria();
			criteria.addCriterion(Operator.EQUAL, DBColumnNames.COUNTRY_ID,String.valueOf(id));
			countryList = cDao.getCountryByCriteria(criteria, lang);
		} catch (DaoException e) {
			// log
			throw new CountryServiceException("can't get film countries", e);
		}
		return countryList;
	}

	@Override
	public Country getCountryById(int id,String lang) throws CountryServiceException {
		List<Country> country = null;
		DaoFactory dao = DaoFactory.getInstance();
		CountryDao cDao = dao.getCountryDao();
		try {
			Criteria criteria = new Criteria();
			criteria.addCriterion(Operator.EQUAL, DBColumnNames.COUNTRY_ID,String.valueOf(id));
			country = cDao.getCountryByCriteria(criteria, lang);
			
		} catch (DaoException e) {
			// log
			throw new CountryServiceException("can't get author country", e);
		}
		return country.get(0);
	}



}
