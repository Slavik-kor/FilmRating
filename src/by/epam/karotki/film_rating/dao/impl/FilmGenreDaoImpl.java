package by.epam.karotki.film_rating.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.epam.karotki.film_rating.dao.DBColumnNames;
import by.epam.karotki.film_rating.dao.FilmGenreDao;
import by.epam.karotki.film_rating.dao.connection_pool.ConnectionPool;
import by.epam.karotki.film_rating.dao.connection_pool.exception.ConnectionPoolException;
import by.epam.karotki.film_rating.dao.exception.DaoException;

public class FilmGenreDaoImpl implements FilmGenreDao {
	private ConnectionPool conPool = ConnectionPool.getInstance();
	
	private static final String ADD_FILM_GENRE = "INSERT INTO Film_Genre (Film_id,Genre_id) VALUES (?,?) ";
	
	private static final String DELETE_FILM_GENRE = "DELETE FROM Film_Genre WHERE (idFilm = ?) AND (idGenre = ?)";
	
	private static final String SELECT_FILM_ID = "SELECT Film_id FROM Film_Genre WHERE Genre_id = ?";
	
	private static final String SELECT_GENRE_ID = "SELECT Genre_id FROM Film_Genre WHERE Film_id = ?";

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

	@Override
	public List<Integer> getFilmsByGenre(int idGenre) throws DaoException {
		List<Integer> filmIds = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(SELECT_FILM_ID);
			ps.setInt(1,idGenre);
			rs = ps.executeQuery();
			filmIds = new ArrayList<Integer>();
			while(rs.next()){
				filmIds.add(rs.getInt(DBColumnNames.FILM_GENRE_FILM_ID));
			}
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
		return filmIds;		
	}

	@Override
	public List<Integer> getGenresByFilm(int idFilm) throws DaoException {
		List<Integer> genreIds = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(SELECT_GENRE_ID);
			ps.setInt(1,idFilm);
			rs = ps.executeQuery();
			genreIds = new ArrayList<Integer>();
			while(rs.next()){
				genreIds.add(rs.getInt(DBColumnNames.FILM_GENRE_GENRE_ID));
			}
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
		return genreIds;		
	}

}
