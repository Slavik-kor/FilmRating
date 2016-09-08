package by.epam.karotki.film_rating.service.impl;

import java.util.ArrayList;
import java.util.List;

import by.epam.karotki.film_rating.dao.CountryDao;
import by.epam.karotki.film_rating.dao.Criteria;
import by.epam.karotki.film_rating.dao.DBColumnNames;
import by.epam.karotki.film_rating.dao.DaoFactory;
import by.epam.karotki.film_rating.dao.FilmCountryDao;
import by.epam.karotki.film_rating.dao.Operator;
import by.epam.karotki.film_rating.dao.exception.DaoException;
import by.epam.karotki.film_rating.entity.Country;
import by.epam.karotki.film_rating.service.CountryService;
import by.epam.karotki.film_rating.service.exception.CountryServiceException;

public class CountryServiceImpl implements CountryService {

	@Override
	public List<Country> getCountriesByFilm(int idFilm,String lang) throws CountryServiceException {
		List<Country> countryList = new ArrayList<Country>();
		DaoFactory dao = DaoFactory.getInstance();
		CountryDao cDao = dao.getCountryDao();
		FilmCountryDao fCDao = dao.getFilmCountryDao();
		try {
			List<Integer> countryIds = fCDao.getCountriesByFilm(idFilm);
			String[] strCMas= new String[countryIds.size()];
			for(int i=0; i<strCMas.length;i++){
				strCMas[i] = String.valueOf(countryIds.get(i));
			}
			Criteria criteria = dao.createCriteria();
			criteria.addCriterion(Operator.IN, DBColumnNames.COUNTRY_ID,strCMas);
			countryList = cDao.getCountryByCriteria(criteria, lang);
		} catch (DaoException e) {
			// log
			throw new CountryServiceException("can't get film countries", e);
		}
		return countryList;
	}

	@Override
	public Country getCountryById(int id,String lang) throws CountryServiceException {
		Country country = null;
		DaoFactory dao = DaoFactory.getInstance();
		CountryDao cDao = dao.getCountryDao();
		try {
			Criteria criteria = dao.createCriteria();
			criteria.addCriterion(Operator.EQUAL, DBColumnNames.COUNTRY_ID,String.valueOf(id));
			List <Country> list = cDao.getCountryByCriteria(criteria, lang);
			country = list.get(0);
		} catch (DaoException e) {
			// log
			throw new CountryServiceException("can't get country", e);
		} catch (IndexOutOfBoundsException e){
			//log
			country = null;
		}
		return country;
	}



}
