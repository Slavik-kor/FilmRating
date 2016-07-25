package by.epam.karotki.film_rating.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.epam.karotki.film_rating.connection_pool.ConnectionPool;
import by.epam.karotki.film_rating.connection_pool.exception.ConnectionPoolException;
import by.epam.karotki.film_rating.dao.DBColumnNames;
import by.epam.karotki.film_rating.dao.IAuthorDao;
import by.epam.karotki.film_rating.dao.exception.DaoException;
import by.epam.karotki.film_rating.entity.Author;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

public class AuthorDaoImpl implements IAuthorDao {
//	private static final Logger LOG = LogManager.getLogger();
	private ConnectionPool conPool = ConnectionPool.getInstance();

	private static final String AUTHOR_BY_COUNTRY = "SELECT AuthorFirstName, AuthorLastName FROM Author "
			+ "JOIN Country ON Country.idCountry = Author.CountryOfBirth_id " + "WHERE CountryName = ? ;";

	private static final String AUTHOR_BY_FILM = "SELECT AuthorFirstName, AuthorLastName, Role FROM Author "
			+ "JOIN Film_has_Authors ON Film_has_Authors.Authors_idAuthors = Author.idAuthor "
			+ "JOIN Film ON film.idFilm = Film_has_Authors.Film_id " + "WHERE Title = ? ORDER BY Role ;";


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
				ps.close();
			} catch (SQLException e) {
		//		LOG.warn("Can't close PreparedStatement or ResultSet");
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
				ps.close();
			} catch (SQLException e) {
		//		LOG.warn("Can't close PreparedStatement or ResultSet");
			}
			conPool.returnConnection(con);
		}
		return authorList;
	}
	
	private List<Author> getAuthors(ResultSet rs) throws SQLException {
		List<Author> authorList = new ArrayList<Author>();
		while (rs.next()) {
			Author author = new Author();
			author.setFirstName(rs.getString(DBColumnNames.AUTHOR_FIRST_NAME));
			author.setLastName(rs.getString(DBColumnNames.AUTHOR_LAST_NAME));
			authorList.add(author);
		}
		return authorList;
	}

}
