package by.epam.karotki.film_rating.service.impl;

import java.util.ArrayList;
import java.util.List;

import by.epam.karotki.film_rating.dao.CountryDao;
import by.epam.karotki.film_rating.dao.Criteria;
import by.epam.karotki.film_rating.dao.DBColumnName;
import by.epam.karotki.film_rating.dao.DaoFactory;
import by.epam.karotki.film_rating.dao.FilmCountryDao;
import by.epam.karotki.film_rating.dao.Operator;
import by.epam.karotki.film_rating.dao.exception.CountryDaoException;
import by.epam.karotki.film_rating.dao.exception.DaoException;
import by.epam.karotki.film_rating.entity.Country;
import by.epam.karotki.film_rating.service.CountryService;
import by.epam.karotki.film_rating.service.exception.CountryServiceException;

public class CountryServiceImpl implements CountryService {
	
	private static final String ERROR_MESSAGE = "can't get countries";

	@Override
	public List<Country> getCountriesByFilm(int idFilm,String lang) throws CountryServiceException {
		List<Country> countryList = new ArrayList<Country>();
		DaoFactory dao = DaoFactory.getInstance();
		CountryDao cDao = dao.getCountryDao();
		FilmCountryDao fCDao = dao.getFilmCountryDao();
		try {
			List<Integer> countryIds = fCDao.getCountriesByFilm(idFilm);
			if(countryIds.size()==0){return null;}
			String[] strCMas= new String[countryIds.size()];
			for(int i=0; i<strCMas.length;i++){
				strCMas[i] = String.valueOf(countryIds.get(i));
			}
			
			Criteria criteria = dao.createCriteria();
			criteria.addCriterion(Operator.IN, DBColumnName.COUNTRY_ID,strCMas);
			countryList = cDao.getCountryByCriteria(criteria, lang);
		} catch (DaoException e) {
			// log
			throw new CountryServiceException(ERROR_MESSAGE, e);
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
			criteria.addCriterion(Operator.EQUAL, DBColumnName.COUNTRY_ID,String.valueOf(id));
			List <Country> list = cDao.getCountryByCriteria(criteria, lang);
			country = list.get(0);
		} catch (DaoException e) {
			// log
			throw new CountryServiceException(ERROR_MESSAGE, e);
		} catch (IndexOutOfBoundsException e){
			//log
			country = null;
		}
		return country;
	}

	@Override
	public List<Country> getAllCountries(String lang) throws CountryServiceException {
		List<Country> countryList = new ArrayList<Country>();
		DaoFactory dao = DaoFactory.getInstance();
		CountryDao cDao = dao.getCountryDao();
		try{
			Criteria criteria = dao.createCriteria();
			countryList = cDao.getCountryByCriteria(criteria, lang);
		}catch(CountryDaoException e){
			new CountryServiceException(ERROR_MESSAGE, e);
		}
		return countryList;
	}

	@Override
	public void addCountryToFilm(int idFilm, List<Integer> idCountry) throws CountryServiceException {
		DaoFactory dao = DaoFactory.getInstance();
		FilmCountryDao fGDao = dao.getFilmCountryDao();
		try {
			List<Integer> countiesDB = fGDao.getCountriesByFilm(idFilm);
			for (int i = 0; i < idCountry.size(); i++) {
				Integer country =idCountry.get(i);
				if (!countiesDB.contains(country)) {
					fGDao.addCountriesToFilm(idFilm, country);
				}
			}
			
			for (int i = 0; i < countiesDB.size(); i++) {
				if(!idCountry.contains(countiesDB.get(i))){
					fGDao.deleteCountriesFromFilm(idFilm, countiesDB.get(i));
				}
			}

		} catch (DaoException e) {
			// log
			throw new CountryServiceException("can't add country List to film", e);
		}
	}



}
