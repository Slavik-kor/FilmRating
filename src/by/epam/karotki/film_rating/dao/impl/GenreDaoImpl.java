package by.epam.karotki.film_rating.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.epam.karotki.film_rating.dao.DBColumnNames;
import by.epam.karotki.film_rating.dao.GenreDao;
import by.epam.karotki.film_rating.dao.connection_pool.ConnectionPool;
import by.epam.karotki.film_rating.dao.connection_pool.exception.ConnectionPoolException;
import by.epam.karotki.film_rating.dao.exception.DaoException;
import by.epam.karotki.film_rating.entity.Genre;

public class GenreDaoImpl implements GenreDao {
	private ConnectionPool conPool = ConnectionPool.getInstance();

	private static final String GENRES_BY_FILM = "SELECT idGenre, Name, Description FROM Genre "
			+ "JOIN Film_Genre film ON Genre.idGenre = film.Genre_id WHERE film.Film_id=?";

	@Override
	public List<Genre> getGenreListByFilm(int idFilm) throws DaoException {
		List<Genre> genreList = new ArrayList<Genre>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(GENRES_BY_FILM);
			ps.setInt(1, idFilm);
			rs = ps.executeQuery();
			genreList = getGenreList(rs);
		} catch (ConnectionPoolException e) {
			throw new DaoException("Can't get connection from ConnectionPool", e);
		} catch (SQLException e) {
			throw new DaoException("Can't perform query", e);
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				// LOG.warn("Can't close ResultSet");
			}
			try {
				ps.close();
			} catch (SQLException e) {
				// LOG.warn("Can't close PreparedStatement");
			}
			conPool.returnConnection(con);
		}
		return genreList;
	}

	private List<Genre> getGenreList(ResultSet rs) throws SQLException {
		List<Genre> genreList = new ArrayList<Genre>();
		while (rs.next()) {
			Genre genre = new Genre();
			genre.setId(rs.getInt(DBColumnNames.GENRE_ID));
			genre.setName(rs.getString(DBColumnNames.GENRE_NAME));
			genre.setDescription(rs.getString(DBColumnNames.GENRE_DESCRIPTION));
			genreList.add(genre);
		}
		return genreList;
	}
}
