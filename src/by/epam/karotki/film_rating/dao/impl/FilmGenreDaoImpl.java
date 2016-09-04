package by.epam.karotki.film_rating.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import by.epam.karotki.film_rating.dao.FilmGenreDao;
import by.epam.karotki.film_rating.dao.connection_pool.ConnectionPool;
import by.epam.karotki.film_rating.dao.connection_pool.exception.ConnectionPoolException;
import by.epam.karotki.film_rating.dao.exception.DaoException;

public class FilmGenreDaoImpl implements FilmGenreDao {
	private ConnectionPool conPool = ConnectionPool.getInstance();
	
	private static final String ADD_FILM_GENRE = "INSERT INTO Film_Genre (Film_id,Genre_id) VALUES (?,?) ";
	
	private static final String DELETE_FILM_GENRE = "DELETE FROM Film_Genre WHERE (idFilm = ?) AND (idGenre = ?)";

	@Override
	public void addGenresToFilm(int idFilm, int idGenre) throws DaoException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(ADD_FILM_GENRE);
			ps.setInt(1,idFilm);
			ps.setInt(2, idGenre);
			ps.executeUpdate();
		} catch (ConnectionPoolException e) {
			throw new DaoException("Can't get connection from ConnectionPool", e);
		} catch (SQLException e) {
			throw new DaoException("Can't perform query", e);
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
	public void deleteGenresFromFilm(int idFilm, int idGenre) throws DaoException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(DELETE_FILM_GENRE);
			ps.setInt(1,idFilm);
			ps.setInt(2,idGenre);
			ps.executeUpdate();
		} catch (ConnectionPoolException e) {
			throw new DaoException("Can't get connection from ConnectionPool", e);
		} catch (SQLException e) {
			throw new DaoException("Can't perform query", e);
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				// LOG.error("Can't close PreparedStatement");
			}
			conPool.returnConnection(con);
		}
	}

}
