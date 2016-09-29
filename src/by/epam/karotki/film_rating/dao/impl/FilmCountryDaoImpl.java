package by.epam.karotki.film_rating.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.epam.karotki.film_rating.dao.DBColumnName;
import by.epam.karotki.film_rating.dao.FilmCountryDao;
import by.epam.karotki.film_rating.dao.connection_pool.ConnectionPool;
import by.epam.karotki.film_rating.dao.connection_pool.exception.ConnectionPoolException;
import by.epam.karotki.film_rating.dao.exception.DaoException;

public class FilmCountryDaoImpl implements FilmCountryDao {

	private ConnectionPool conPool = ConnectionPool.getInstance();

	private static final String ERROR_MESSAGE_QUERY = "Can't perform query";

	private static final String ERROR_MESSAGE_CP = "Can't get connection from ConnectionPool";

	private static final String ADD_FILM_COUNTRY = "INSERT INTO FilmOriginCountry (Film_id,Country_id) VALUES (?,?) ";

	private static final String DELETE_FILM_COUNTRY = "DELETE FROM FilmOriginCountry WHERE (Film_id = ?) AND (Country_id = ?)";

	private static final String SELECT_FILM_ID = "SELECT Film_id FROM FilmOriginCountry WHERE Country_id = ?";

	private static final String SELECT_COUNTRY_ID = "SELECT Country_id FROM FilmOriginCountry WHERE Film_id = ?";

	@Override
	public void addCountriesToFilm(int idFilm, int idCountry) throws DaoException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(ADD_FILM_COUNTRY);
			ps.setInt(1, idFilm);
			ps.setInt(2, idCountry);
			ps.executeUpdate();
		} catch (ConnectionPoolException e) {
			throw new DaoException(ERROR_MESSAGE_CP, e);
		} catch (SQLException e) {
			throw new DaoException(ERROR_MESSAGE_QUERY, e);
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				// LOG.error("Can't close PreparedStatement");
			}
			conPool.returnConnection(con);
		}
	}

	@Override
	public void deleteCountriesFromFilm(int idFilm, int idCountry) throws DaoException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(DELETE_FILM_COUNTRY);
			ps.setInt(1, idFilm);
			ps.setInt(2, idCountry);
			ps.executeUpdate();
		} catch (ConnectionPoolException e) {
			throw new DaoException(ERROR_MESSAGE_CP, e);
		} catch (SQLException e) {
			throw new DaoException(ERROR_MESSAGE_QUERY, e);
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				// LOG.error("Can't close PreparedStatement");
			}
			conPool.returnConnection(con);
		}

	}

	@Override
	public List<Integer> getFilmsByCountry(int idCountry) throws DaoException {
		List<Integer> filmIds = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(SELECT_FILM_ID);
			ps.setInt(1, idCountry);
			rs = ps.executeQuery();
			filmIds = new ArrayList<Integer>();
			while (rs.next()) {
				filmIds.add(rs.getInt(DBColumnName.FILM_COUNTRY_FILM_ID));
			}
		} catch (ConnectionPoolException e) {
			throw new DaoException(ERROR_MESSAGE_CP, e);
		} catch (SQLException e) {
			throw new DaoException(ERROR_MESSAGE_QUERY, e);
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				// LOG.error("Can't close PreparedStatement");
			}
			conPool.returnConnection(con);
		}
		return filmIds;
	}

	@Override
	public List<Integer> getCountriesByFilm(int idFilm) throws DaoException {
		List<Integer> countryIds = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(SELECT_COUNTRY_ID);
			ps.setInt(1, idFilm);
			rs = ps.executeQuery();
			countryIds = new ArrayList<Integer>();
			while (rs.next()) {
				countryIds.add(rs.getInt(DBColumnName.FILM_COUNTRY_COUNTRY_ID));
			}
		} catch (ConnectionPoolException e) {
			throw new DaoException(ERROR_MESSAGE_CP, e);
		} catch (SQLException e) {
			throw new DaoException(ERROR_MESSAGE_QUERY, e);
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				// LOG.error("Can't close PreparedStatement");
			}
			conPool.returnConnection(con);
		}
		return countryIds;
	}

}
