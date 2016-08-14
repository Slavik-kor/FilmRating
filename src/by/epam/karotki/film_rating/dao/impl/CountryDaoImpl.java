package by.epam.karotki.film_rating.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.epam.karotki.film_rating.dao.CountryDao;
import by.epam.karotki.film_rating.dao.DBColumnNames;
import by.epam.karotki.film_rating.dao.connection_pool.ConnectionPool;
import by.epam.karotki.film_rating.dao.connection_pool.exception.ConnectionPoolException;
import by.epam.karotki.film_rating.dao.exception.DaoException;
import by.epam.karotki.film_rating.entity.Country;

public class CountryDaoImpl implements CountryDao {
	private ConnectionPool conPool = ConnectionPool.getInstance();

	private static final String COUNTRIES_BY_FILM = "SELECT idCountry, CountryName, CountryCode FROM Country "
			+ "JOIN FilmOriginCountry film ON Country.idCountry = film.Country_id "
			+ "WHERE film.Film_id=?";

	@Override
	public List<Country> getCountryByFilm(int idFilm) throws DaoException {
		List<Country> countryList = new ArrayList<Country>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(COUNTRIES_BY_FILM);
			ps.setInt(1, idFilm);
			rs = ps.executeQuery();
			countryList = getCountryList(rs);
		} catch (ConnectionPoolException e) {
			throw new DaoException("Can't get connection from ConnectionPool", e);
		} catch (SQLException e) {
			throw new DaoException("Can't perform query", e);
		} finally {
			try {
				rs.close();
				ps.close();
			} catch (SQLException e) {
				// LOG.warn("Can't close PreparedStatement or ResultSet");
			}
			conPool.returnConnection(con);
		}
		return countryList;
	}

	private List<Country> getCountryList(ResultSet rs) throws SQLException {
		List<Country> countryList = new ArrayList<Country>();
		while (rs.next()) {
			Country country = new Country();
			country.setId(rs.getInt(DBColumnNames.COUNTRY_ID));
			country.setName(rs.getString(DBColumnNames.COUNTRY_NAME));
			country.setCode(rs.getString(DBColumnNames.COUNTRY_CODE));
			countryList.add(country);
		}
		return countryList;

	}

}
