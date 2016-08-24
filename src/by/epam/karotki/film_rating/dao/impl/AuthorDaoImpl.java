package by.epam.karotki.film_rating.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.epam.karotki.film_rating.dao.DBColumnNames;
import by.epam.karotki.film_rating.dao.AuthorDao;
import by.epam.karotki.film_rating.dao.connection_pool.ConnectionPool;
import by.epam.karotki.film_rating.dao.connection_pool.exception.ConnectionPoolException;
import by.epam.karotki.film_rating.dao.exception.DaoException;
import by.epam.karotki.film_rating.entity.Author;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

public class AuthorDaoImpl implements AuthorDao {
	// private static final Logger LOG = LogManager.getLogger();
	private ConnectionPool conPool = ConnectionPool.getInstance();

	private static final String AUTHOR_BY_COUNTRY = "SELECT idAuthor, AuthorFirstName, AuthorLastName, AuthorsBirthday, Photo, CountryOfBirth_id FROM Author "
			+ "JOIN Country ON Country.idCountry = Author.CountryOfBirth_id WHERE CountryName = ? ;";

	private static final String AUTHOR_BY_FILM = "SELECT idAuthor, AuthorFirstName, AuthorLastName, AuthorsBirthday, CountryOfBirth_id, Photo, Role FROM Author "
			+ "JOIN Film_has_Authors ON Film_has_Authors.Authors_idAuthors = Author.idAuthor "
			+ "JOIN Film ON film.idFilm = Film_has_Authors.Film_id WHERE Title = ? ORDER BY Role ;";

	private static final String DIRECTORS_BY_FILM = "SELECT idAuthor, AuthorFirstName, AuthorLastName, AuthorsBirthday, Photo, CountryOfBirth_id FROM Author "
			+ "JOIN Film_has_Authors film ON film.Authors_idAuthors = Author.idAuthor "
			+ "WHERE (film.Film_id = ?) AND (film.Role = ?) ;";
	
	private static final String AUTHOR_BY_ID = "SELECT idAuthor, AuthorFirstName, AuthorLastName, AuthorsBirthday, Photo, CountryOfBirth_id FROM Author "
			+ " WHERE idAuthor = ? ;";

	@Override
	public List<Author> getAuthorListByCountry(String country) throws DaoException {

		List<Author> authorList = new ArrayList<Author>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(AUTHOR_BY_COUNTRY);
			ps.setString(1, country);
			rs = ps.executeQuery();
			authorList = getAuthors(rs);
		} catch (SQLException e) {
			throw new DaoException("Can't perform query", e);
		} catch (ConnectionPoolException e) {
			throw new DaoException("Can't get connection from ConnectionPool", e);
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
		return authorList;
	}

	@Override
	public List<Author> getAuthorListByFilm(String title) throws DaoException {
		List<Author> authorList = new ArrayList<Author>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(AUTHOR_BY_FILM);
			ps.setString(1, title);
			rs = ps.executeQuery();
			authorList = getAuthors(rs);
		} catch (SQLException e) {
			throw new DaoException("Can't perform query", e);
		} catch (ConnectionPoolException e) {
			throw new DaoException("Can't get connection from ConnectionPool", e);
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
		return authorList;
	}

	@Override
	public List<Author> getAuthorListByFilm(int idFilm,String role) throws DaoException {
		List<Author> authorList = new ArrayList<Author>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(DIRECTORS_BY_FILM);
			ps.setInt(1, idFilm);
			ps.setString(2, role);
			rs = ps.executeQuery();
			authorList = getAuthors(rs);
		} catch (SQLException e) {
			throw new DaoException("Can't perform query", e);
		} catch (ConnectionPoolException e) {
			throw new DaoException("Can't get connection from ConnectionPool", e);
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
		return authorList;
	}
	
	@Override
	public Author getAuthorById(int idAuthor) throws DaoException {
		Author author = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(AUTHOR_BY_ID);
			ps.setInt(1, idAuthor);
			rs = ps.executeQuery();
			author = getAuthor(rs);
		} catch (SQLException e) {
			throw new DaoException("Can't perform query", e);
		} catch (ConnectionPoolException e) {
			throw new DaoException("Can't get connection from ConnectionPool", e);
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
		return author;
	}

	private Author getAuthor(ResultSet rs) throws SQLException {
		while (rs.next()) {
			Author author = new Author();
			author.setId(rs.getInt(DBColumnNames.AUTHOR_ID));
			author.setFirstName(rs.getString(DBColumnNames.AUTHOR_FIRST_NAME));
			author.setLastName(rs.getString(DBColumnNames.AUTHOR_LAST_NAME));
			author.setBirthDay(rs.getDate(DBColumnNames.AUTHOR_BIRTHDAY));
			author.setPhoto(rs.getString(DBColumnNames.AUTHOR_PHOTO));
			author.setCountryOfBirthId(rs.getInt(DBColumnNames.AUTHOR_COUNTRY));
			return author;
		}
		return null;
	}
	
	private List<Author> getAuthors(ResultSet rs) throws SQLException {
		List<Author> authorList = new ArrayList<Author>();
		while (rs.next()) {
			Author author = new Author();
			author.setId(rs.getInt(DBColumnNames.AUTHOR_ID));
			author.setFirstName(rs.getString(DBColumnNames.AUTHOR_FIRST_NAME));
			author.setLastName(rs.getString(DBColumnNames.AUTHOR_LAST_NAME));
			author.setBirthDay(rs.getDate(DBColumnNames.AUTHOR_BIRTHDAY));
			author.setPhoto(rs.getString(DBColumnNames.AUTHOR_PHOTO));
			author.setCountryOfBirthId(rs.getInt(DBColumnNames.AUTHOR_COUNTRY));
			authorList.add(author);
		}
		return authorList;
	}

	

}
