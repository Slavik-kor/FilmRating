package by.epam.karotki.film_rating.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.karotki.film_rating.dao.CountryDao;
import by.epam.karotki.film_rating.dao.Criteria;
import by.epam.karotki.film_rating.dao.DBColumnName;
import by.epam.karotki.film_rating.dao.connection_pool.ConnectionPool;
import by.epam.karotki.film_rating.dao.connection_pool.exception.ConnectionPoolException;
import by.epam.karotki.film_rating.dao.exception.CountryDaoException;
import by.epam.karotki.film_rating.entity.Country;

public class CountryDaoImpl implements CountryDao {
	
	private static final Logger LOG = LogManager.getLogger();
	
	private ConnectionPool conPool = ConnectionPool.getInstance();
	
	private static final String ERROR_MESSAGE_QUERY = "Can't perform query";
	
	private static final String ERROR_MESSAGE_CP = "Can't get connection from ConnectionPool";

	private static final String COUNTRIES_BY_FILM = "SELECT idCountry, CountryName, CountryCode FROM Country "
			+ "JOIN FilmOriginCountry film ON Country.idCountry = film.Country_id "
			+ "WHERE film.Film_id=?";
	
	private static final String COUNTRY_BY_ID = "SELECT idCountry, CountryName, CountryCode FROM Country "
			+ "WHERE idCountry=?";
	
	private static final String COUNTRY = "SELECT idCountry, CountryName, CountryCode "
			+ "FROM (SELECT g.idCountry idCountry, coalesce(t.CountryName,g.CountryName) CountryName, g.CountryCode CountryCode "
			+ "FROM (Country as g LEFT JOIN (SELECT * FROM Country_lang WHERE lang = ?) t USING(idCountry))) Country ";
	
	private static final String ADD_COUNTRY = "INSERT INTO Country (CountryName, CountryCode) VALUES (?,?) ";

	private static final String ADD_COUNTRY_LANG = "INSERT INTO Country_lang (idCountry, lang, CountryName) VALUES (?,?,?) ";

	private static final String UPDATE_COUNTRY = "UPDATE Country SET CountryName = ?, CountryCode = ? WHERE idCountry = ?";

	private static final String UPDATE_COUNTRY_LANG = "UPDATE Country_lang SET CountryName = ? WHERE (idCountry = ?) AND (lang = ?)";
	
	private static final String DELETE_COUNTRY = "DELETE FROM Country WHERE idCountry = ?";

	private static final String DELETE_COUNTRY_LANG = "DELETE FROM Country_lang WHERE (idCountry = ?) AND (lang = ?)";

	@Override
	public List<Country> getCountryByFilm(int idFilm) throws CountryDaoException {
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
			throw new CountryDaoException(ERROR_MESSAGE_CP, e);
		} catch (SQLException e) {
			throw new CountryDaoException(ERROR_MESSAGE_QUERY, e);
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				 LOG.error("Can't close ResultSet");
			}
			try {
				ps.close();
			} catch (SQLException e) {
				 LOG.error("Can't close PreparedStatement");
			}
			conPool.returnConnection(con);
		}
		return countryList;
	}

	@Override
	public Country getCountryById(int idCountry) throws CountryDaoException {
		Country country = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(COUNTRY_BY_ID);
			ps.setInt(1, idCountry);
			rs = ps.executeQuery();
			country = getCountry(rs);
		} catch (ConnectionPoolException e) {
			throw new CountryDaoException(ERROR_MESSAGE_CP, e);
		} catch (SQLException e) {
			throw new CountryDaoException(ERROR_MESSAGE_QUERY, e);
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				 LOG.error("Can't close ResultSet");
			}
			try {
				ps.close();
			} catch (SQLException e) {
				 LOG.error("Can't close PreparedStatement");
			}
			conPool.returnConnection(con);
		}
		return country;
	}
	

	@Override
	public void addCountry(Country country) throws CountryDaoException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(ADD_COUNTRY);
			ps.setString(1,country.getName());
			ps.setString(2,country.getCode());
			ps.executeUpdate();
		} catch (ConnectionPoolException e) {
			throw new CountryDaoException(ERROR_MESSAGE_CP, e);
		} catch (SQLException e) {
			throw new CountryDaoException(ERROR_MESSAGE_QUERY, e);
		} finally {
			try {
				ps.close();
				} catch (SQLException e) {
				 LOG.error("Can't close PreparedStatement");
			}
			conPool.returnConnection(con);
		}
				
	}

	@Override
	public void addCountry(Country country, String lang) throws CountryDaoException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(ADD_COUNTRY_LANG);
			ps.setInt(1,country.getId());
			ps.setString(2, lang);
			ps.setString(3, country.getName());
			ps.executeUpdate();
		} catch (ConnectionPoolException e) {
			throw new CountryDaoException(ERROR_MESSAGE_CP, e);
		} catch (SQLException e) {
			throw new CountryDaoException(ERROR_MESSAGE_QUERY, e);
		} finally {
			try {
				ps.close();
				} catch (SQLException e) {
				 LOG.error("Can't close PreparedStatement");
			}
			conPool.returnConnection(con);
		}		
	}

	@Override
	public void deleteCountryById(int idCountry) throws CountryDaoException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(DELETE_COUNTRY);
			ps.setInt(1,idCountry);
			ps.executeUpdate();
		} catch (ConnectionPoolException e) {
			throw new CountryDaoException(ERROR_MESSAGE_CP, e);
		} catch (SQLException e) {
			throw new CountryDaoException(ERROR_MESSAGE_QUERY, e);
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				 LOG.error("Can't close PreparedStatement");
			}
			conPool.returnConnection(con);
		}
		
	}

	@Override
	public void deleteCountryById(int idCountry, String lang) throws CountryDaoException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(DELETE_COUNTRY_LANG);
			ps.setInt(1,idCountry);
			ps.setString(2, lang);
			ps.executeUpdate();
		} catch (ConnectionPoolException e) {
			throw new CountryDaoException(ERROR_MESSAGE_CP, e);
		} catch (SQLException e) {
			throw new CountryDaoException(ERROR_MESSAGE_QUERY, e);
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				 LOG.error("Can't close PreparedStatement");
			}
			conPool.returnConnection(con);
		}		
		
	}

	@Override
	public void updateCountry(Country country) throws CountryDaoException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(UPDATE_COUNTRY);
			ps.setString(1, country.getName());
			ps.setString(2, country.getCode());
			ps.setInt(3, country.getId());
			ps.executeUpdate();
		} catch (ConnectionPoolException e) {
			throw new CountryDaoException(ERROR_MESSAGE_CP, e);
		} catch (SQLException e) {
			throw new CountryDaoException(ERROR_MESSAGE_QUERY, e);
		} finally {
			try {
				ps.close();
				} catch (SQLException e) {
				 LOG.error("Can't close PreparedStatement");
			}
			conPool.returnConnection(con);
		}		
	}

	@Override
	public void updateCountry(Country country, String lang) throws CountryDaoException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(UPDATE_COUNTRY_LANG);
			ps.setString(1, country.getName());
			ps.setString(2, country.getCode());
			ps.setInt(3, country.getId());
			ps.setString(4, lang);
			ps.executeUpdate();
		} catch (ConnectionPoolException e) {
			throw new CountryDaoException(ERROR_MESSAGE_CP, e);
		} catch (SQLException e) {
			throw new CountryDaoException(ERROR_MESSAGE_QUERY, e);
		} finally {
			try {
				ps.close();
				} catch (SQLException e) {
				 LOG.error("Can't close PreparedStatement");
			}
			conPool.returnConnection(con);
		}
	}

	@Override
	public List<Country> getCountryByCriteria(Criteria criteria,String lang) throws CountryDaoException {
		List<Country> countryList = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(COUNTRY+criteria.getClause());
			ps.setString(1, lang);
			
			rs = ps.executeQuery();
			
			countryList = getCountryList(rs);
		} catch (ConnectionPoolException e) {
			throw new CountryDaoException(ERROR_MESSAGE_CP, e);
		} catch (SQLException e) {
			throw new CountryDaoException(ERROR_MESSAGE_QUERY, e);
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				 LOG.error("Can't close ResultSet");
			}
			try {
				ps.close();
			} catch (SQLException e) {
				 LOG.error("Can't close PreparedStatement");
			}
			conPool.returnConnection(con);
		}
		return countryList;
	}
	
	private Country getCountry(ResultSet rs) throws SQLException {
		Country country = null;
		while (rs.next()) {
			country = new Country();
			country.setId(rs.getInt(DBColumnName.COUNTRY_ID));
			country.setName(rs.getString(DBColumnName.COUNTRY_NAME));
			country.setCode(rs.getString(DBColumnName.COUNTRY_CODE));
		}
		return country;
		}
	
	private List<Country> getCountryList(ResultSet rs) throws SQLException {
		List<Country> countryList = new ArrayList<Country>();
		while (rs.next()) {
			Country country = new Country();
			country.setId(rs.getInt(DBColumnName.COUNTRY_ID));
			country.setName(rs.getString(DBColumnName.COUNTRY_NAME));
			country.setCode(rs.getString(DBColumnName.COUNTRY_CODE));
			countryList.add(country);
		}
		return countryList;

	}

}
