package by.epam.karotki.film_rating.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.epam.karotki.film_rating.dao.Criteria;
import by.epam.karotki.film_rating.dao.DBColumnNames;
import by.epam.karotki.film_rating.dao.GenreDao;
import by.epam.karotki.film_rating.dao.connection_pool.ConnectionPool;
import by.epam.karotki.film_rating.dao.connection_pool.exception.ConnectionPoolException;
import by.epam.karotki.film_rating.dao.exception.GenreDaoException;
import by.epam.karotki.film_rating.entity.Genre;

public class GenreDaoImpl implements GenreDao {
	private ConnectionPool conPool = ConnectionPool.getInstance();
	private static final String ERROR_MESSAGE_QUERY = "Can't perform query";
	private static final String ERROR_MESSAGE_CP = "Can't get connection from ConnectionPool";
	

	private static final String GENRES_BY_FILM = "SELECT idGenre, Name, Description FROM Genre "
			+ "JOIN Film_Genre film ON Genre.idGenre = film.Genre_id WHERE film.Film_id=?";
	
	private static final String GENRES = "SELECT idGenre, Name, Description FROM (SELECT g.idGenre idGenre, coalesce(t.Name,g.Name) Name, coalesce(t.Description,g.Description) description "
			+ "FROM (Genre as g LEFT JOIN (SELECT * FROM Genre_lang WHERE lang = ?) t using(idGenre))) genres";
	
	private static final String ADD_GENRES = "INSERT INTO Genre (Name, Description) VALUES (?,?) ";

	private static final String ADD_GENRES_LANG = "INSERT INTO Genre_lang (idGenre, lang, Name, Description) VALUES (?,?,?,?) ";

	private static final String UPDATE_GENRE = "UPDATE Genre SET Name = ?, Description = ? WHERE idGenre = ?";

	private static final String UPDATE_GENRE_LANG = "UPDATE Genre_lang SET Name = ?, Description = ? WHERE (idGenre = ?) AND (lang = ?)";
	
	private static final String DELETE_GENRE = "DELETE FROM Genre WHERE idGenre = ?";

	private static final String DELETE_GENRE_LANG = "DELETE FROM Genre_lang WHERE (idGenre = ?) AND (lang = ?)";

	
	@Override
	public List<Genre> getGenreListByFilm(int idFilm) throws GenreDaoException {
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
			throw new GenreDaoException(ERROR_MESSAGE_CP, e);
		} catch (SQLException e) {
			throw new GenreDaoException(ERROR_MESSAGE_QUERY, e);
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				// LOG.error("Can't close ResultSet");
			}
			try {
				ps.close();
			} catch (SQLException e) {
				// LOG.error("Can't close PreparedStatement");
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
	
	/*	private Genre getGenre(ResultSet rs) throws SQLException {
		Genre genre = null;
		while (rs.next()) {
			genre = new Genre();
			genre.setId(rs.getInt(DBColumnNames.GENRE_ID));
			genre.setName(rs.getString(DBColumnNames.GENRE_NAME));
			genre.setDescription(rs.getString(DBColumnNames.GENRE_DESCRIPTION));
			return genre;
		}
		return genre;
	}
 */
	@Override
	public List<Genre> getGenreByCriteria(Criteria cr, String lang) throws GenreDaoException {
		List<Genre> genre = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(GENRES+cr.getClause());
			ps.setString(1, lang);
			rs = ps.executeQuery();
			genre = getGenreList(rs);
		} catch (ConnectionPoolException e) {
			throw new GenreDaoException(ERROR_MESSAGE_CP, e);
		} catch (SQLException e) {
			throw new GenreDaoException(ERROR_MESSAGE_QUERY, e);
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				// LOG.error("Can't close ResultSet");
			}
			try {
				ps.close();
			} catch (SQLException e) {
				// LOG.error("Can't close PreparedStatement");
			}
			conPool.returnConnection(con);
		}
		return genre;
	}

	@Override
	public void addGenre(Genre genre) throws GenreDaoException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(ADD_GENRES);
			ps.setString(1,genre.getName());
			ps.setString(2,genre.getDescription());
			ps.executeUpdate();
		} catch (ConnectionPoolException e) {
			throw new GenreDaoException(ERROR_MESSAGE_CP, e);
		} catch (SQLException e) {
			throw new GenreDaoException(ERROR_MESSAGE_QUERY, e);
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
	public void addGenre(Genre genre, String lang) throws GenreDaoException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(ADD_GENRES_LANG);
			ps.setInt(1,genre.getId());
			ps.setString(2, lang);
			ps.setString(3, genre.getName());
			ps.setString(4, genre.getDescription());
			ps.executeUpdate();
		} catch (ConnectionPoolException e) {
			throw new GenreDaoException(ERROR_MESSAGE_CP, e);
		} catch (SQLException e) {
			throw new GenreDaoException(ERROR_MESSAGE_QUERY, e);
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
	public void updateGenre(Genre genre) throws GenreDaoException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(UPDATE_GENRE);
			ps.setString(1, genre.getName());
			ps.setString(2, genre.getDescription());
			ps.setInt(3, genre.getId());
			ps.executeUpdate();
		} catch (ConnectionPoolException e) {
			throw new GenreDaoException(ERROR_MESSAGE_CP, e);
		} catch (SQLException e) {
			throw new GenreDaoException(ERROR_MESSAGE_QUERY, e);
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
	public void updateGenre(Genre genre, String lang) throws GenreDaoException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(UPDATE_GENRE_LANG);
			ps.setString(1, genre.getName());
			ps.setString(2, genre.getDescription());
			ps.setInt(3, genre.getId());
			ps.setString(4, lang);
			ps.executeUpdate();
		} catch (ConnectionPoolException e) {
			throw new GenreDaoException(ERROR_MESSAGE_CP, e);
		} catch (SQLException e) {
			throw new GenreDaoException(ERROR_MESSAGE_QUERY, e);
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
	public void deleteGenreById(int id) throws GenreDaoException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(DELETE_GENRE);
			ps.setInt(1,id);
			ps.executeUpdate();
		} catch (ConnectionPoolException e) {
			throw new GenreDaoException(ERROR_MESSAGE_CP, e);
		} catch (SQLException e) {
			throw new GenreDaoException(ERROR_MESSAGE_QUERY, e);
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
	public void deleteGenreById(int id, String lang) throws GenreDaoException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(DELETE_GENRE_LANG);
			ps.setInt(1,id);
			ps.setString(2, lang);
			ps.executeUpdate();
		} catch (ConnectionPoolException e) {
			throw new GenreDaoException(ERROR_MESSAGE_CP, e);
		} catch (SQLException e) {
			throw new GenreDaoException(ERROR_MESSAGE_QUERY, e);
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
