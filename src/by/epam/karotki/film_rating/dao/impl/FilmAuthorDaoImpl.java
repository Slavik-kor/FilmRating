package by.epam.karotki.film_rating.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import by.epam.karotki.film_rating.dao.FilmAuthorDao;
import by.epam.karotki.film_rating.dao.connection_pool.ConnectionPool;
import by.epam.karotki.film_rating.dao.connection_pool.exception.ConnectionPoolException;
import by.epam.karotki.film_rating.dao.exception.DaoException;

public class FilmAuthorDaoImpl implements FilmAuthorDao {
private ConnectionPool conPool = ConnectionPool.getInstance();
private static final String ERROR_MESSAGE_QUERY = "Can't perform query";
private static final String ERROR_MESSAGE_CP = "Can't get connection from ConnectionPool";
	
	private static final String ADD_FILM_AUTHOR = "INSERT INTO Film_has_Authors (Film_id,Authors_idAuthors,Role) VALUES (?,?,?) ";
	
	private static final String DELETE_FILM_AUTHOR = "DELETE FROM Film_has_Authors WHERE (Film_id = ?) AND (Authors_idAuthors = ?) AND (Role = ?)";
	
	@Override
	public void addAuthorToFilm(int idFilm, int idAuthor, String role) throws DaoException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(ADD_FILM_AUTHOR);
			ps.setInt(1,idFilm);
			ps.setInt(2, idAuthor);
			ps.setString(3, role);
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
	public void deleteAuthorFromFilm(int idFilm, int idAuthor, String role) throws DaoException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(DELETE_FILM_AUTHOR);
			ps.setInt(1,idFilm);
			ps.setInt(2,idAuthor);
			ps.setString(3, role);
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

}
