package by.epam.karotki.film_rating.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import by.epam.karotki.film_rating.dao.AuthorDao;
import by.epam.karotki.film_rating.dao.Criteria;
import by.epam.karotki.film_rating.dao.DBColumnName;
import by.epam.karotki.film_rating.dao.connection_pool.ConnectionPool;
import by.epam.karotki.film_rating.dao.connection_pool.exception.ConnectionPoolException;
import by.epam.karotki.film_rating.dao.exception.AuthorDaoException;
import by.epam.karotki.film_rating.entity.Author;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

public class AuthorDaoImpl implements AuthorDao {
//	private static final Logger LOG = LogManager.getLogger();
	private ConnectionPool conPool = ConnectionPool.getInstance();
	private static final String ERROR_MESSAGE_QUERY = "Can't perform query";
	private static final String ERROR_MESSAGE_CP = "Can't get connection from ConnectionPool";

	private static final String AUTHOR_BY_COUNTRY = "SELECT idAuthor, AuthorFirstName, AuthorLastName, AuthorsBirthday, Photo, CountryOfBirth_id FROM Author "
			+ "JOIN Country ON Country.idCountry = Author.CountryOfBirth_id WHERE CountryName = ? ";

	private static final String AUTHOR_BY_FILM = "SELECT idAuthor, AuthorFirstName, AuthorLastName, AuthorsBirthday, CountryOfBirth_id, Photo, Role FROM Author "
			+ "JOIN Film_has_Authors ON Film_has_Authors.Authors_idAuthors = Author.idAuthor "
			+ "JOIN Film ON film.idFilm = Film_has_Authors.Film_id WHERE Title = ? ORDER BY Role ";

	private static final String DIRECTORS_BY_FILM = "SELECT idAuthor, AuthorFirstName, AuthorLastName, AuthorsBirthday, Photo, CountryOfBirth_id FROM Author "
			+ "JOIN Film_has_Authors film ON film.Authors_idAuthors = Author.idAuthor "
			+ "WHERE (film.Film_id = ?) AND (film.Role = ?) ";
	
	private static final String AUTHOR_BY_ID = "SELECT idAuthor, AuthorFirstName, AuthorLastName, AuthorsBirthday, Photo, CountryOfBirth_id FROM Author "
			+ " WHERE idAuthor = ? ";
	
	private static final String AUTHOR_BY_NAME = "SELECT idAuthor, AuthorFirstName, AuthorLastName, AuthorsBirthday, Photo, CountryOfBirth_id FROM Author "
			+ " WHERE (AuthorFirstName = ?) AND (AuthorLastName = ?) ";
	
	private static final String ADD_AUTHOR = "INSERT INTO Author (AuthorFirstName, AuthorLastName, AuthorsBirthday, Photo, CountryOfBirth_id) "
			+ " VALUES(?,?,?,?,?) ";
	
	private static final String ADD_AUTHOR_LANG = "INSERT INTO Author_lang (AuthorFirstName, AuthorLastName, idAuthor, lang) "
			+ " VALUES(?,?,?,?) ";
	
	private static final String UPDATE_AUTHOR = "UPDATE Author SET AuthorFirstName=?, AuthorLastName=?, AuthorsBirthday=?, Photo=?, CountryOfBirth_id=? "
			+ " WHERE idAuthor = ?";
	
	private static final String UPDATE_AUTHOR_LANG = "UPDATE Author_lang SET AuthorFirstName = ?, AuthorLastName = ? "
			+ " WHERE (idAuthor = ?) AND (lang = ?)";
	
	private static final String DELETE_AUTHOR = "DELETE FROM Author WHERE idAuthor = ?";
		
	private static final String DELETE_AUTHOR_LANG = "DELETE FROM Author_lang WHERE (idAuthor = ?) AND (lang = ?)";
	
	private static final String AUTHOR = "SELECT idAuthor, AuthorFirstName, AuthorLastName, AuthorsBirthday, Photo, CountryOfBirth_id "
			+ "FROM (SELECT g.idAuthor idAuthor, coalesce(t.AuthorFirstName,g.AuthorFirstName) AuthorFirstName, "
			+ "coalesce(t.AuthorLastName,g.AuthorLastName) AuthorLastName, AuthorsBirthday, Photo, CountryOfBirth_id "
			+ "FROM (Author g LEFT JOIN (SELECT * FROM Author_lang WHERE lang = ?) t USING(idAuthor))) Authors ";
	@Override
	public List<Author> getAuthorListByCountry(String country) throws AuthorDaoException {

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
			throw new AuthorDaoException(ERROR_MESSAGE_QUERY, e);
		} catch (ConnectionPoolException e) {
			throw new AuthorDaoException(ERROR_MESSAGE_CP, e);
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
	public List<Author> getAuthorListByFilm(String title) throws AuthorDaoException {
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
			throw new AuthorDaoException(ERROR_MESSAGE_QUERY, e);
		} catch (ConnectionPoolException e) {
			throw new AuthorDaoException(ERROR_MESSAGE_CP, e);
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
	public List<Author> getAuthorListByFilm(int idFilm,String role) throws AuthorDaoException {
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
			throw new AuthorDaoException(ERROR_MESSAGE_QUERY, e);
		} catch (ConnectionPoolException e) {
			throw new AuthorDaoException(ERROR_MESSAGE_CP, e);
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
	public Author getAuthorById(int idAuthor) throws AuthorDaoException {
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
			throw new AuthorDaoException(ERROR_MESSAGE_QUERY, e);
		} catch (ConnectionPoolException e) {
			throw new AuthorDaoException(ERROR_MESSAGE_CP, e);
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
			author.setId(rs.getInt(DBColumnName.AUTHOR_ID));
			author.setFirstName(rs.getString(DBColumnName.AUTHOR_FIRST_NAME));
			author.setLastName(rs.getString(DBColumnName.AUTHOR_LAST_NAME));
			author.setBirthDay(rs.getDate(DBColumnName.AUTHOR_BIRTHDAY));
			author.setPhoto(rs.getString(DBColumnName.AUTHOR_PHOTO));
			author.setCountryOfBirthId(rs.getInt(DBColumnName.AUTHOR_COUNTRY));
			return author;
		}
		return null;
	}
	
	private List<Author> getAuthors(ResultSet rs) throws SQLException {
		List<Author> authorList = new ArrayList<Author>();
		while (rs.next()) {
			Author author = new Author();
			author.setId(rs.getInt(DBColumnName.AUTHOR_ID));
			author.setFirstName(rs.getString(DBColumnName.AUTHOR_FIRST_NAME));
			author.setLastName(rs.getString(DBColumnName.AUTHOR_LAST_NAME));
			author.setBirthDay(rs.getDate(DBColumnName.AUTHOR_BIRTHDAY));
			author.setPhoto(rs.getString(DBColumnName.AUTHOR_PHOTO));
			author.setCountryOfBirthId(rs.getInt(DBColumnName.AUTHOR_COUNTRY));
			authorList.add(author);
		}
		return authorList;
	}

	@Override
	public void addAuthor(Author author) throws AuthorDaoException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(ADD_AUTHOR);
			ps.setString(1, author.getFirstName());
			ps.setString(2, author.getLastName());
			if(author.getBirthDay()!=null){
				ps.setDate(3,author.getBirthDay());
			}else{
				ps.setNull(3, Types.DATE);
			}
			ps.setString(4, author.getPhoto());
			if(author.getCountryOfBirthId()!=null){
			ps.setInt(5, author.getCountryOfBirthId());
			}else{
				ps.setNull(5, Types.INTEGER);
			}
			System.out.println("before");
			ps.executeUpdate();
		} catch (ConnectionPoolException e) {
			throw new AuthorDaoException(ERROR_MESSAGE_CP, e);
		} catch (SQLException e) {
			throw new AuthorDaoException(ERROR_MESSAGE_QUERY, e);
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
	public void updateAuthor(Author author) throws AuthorDaoException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(UPDATE_AUTHOR);
			ps.setString(1, author.getFirstName());
			ps.setString(2, author.getLastName());
			ps.setDate(3, author.getBirthDay());
			ps.setString(4, author.getPhoto());
			ps.setInt(5, author.getCountryOfBirthId());
			ps.setInt(6, author.getId());
			ps.executeUpdate();
		} catch (ConnectionPoolException e) {
			throw new AuthorDaoException(ERROR_MESSAGE_CP, e);
		} catch (SQLException e) {
			throw new AuthorDaoException(ERROR_MESSAGE_QUERY, e);
		} finally {
			try {
				ps.close();
				} catch (SQLException e) {
			//	 LOG.error("Can't close PreparedStatement");
			}
			conPool.returnConnection(con);
		}		
	}

	@Override
	public void deleteAuthorById(int id) throws AuthorDaoException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(DELETE_AUTHOR);
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (ConnectionPoolException e) {
			throw new AuthorDaoException(ERROR_MESSAGE_CP, e);
		} catch (SQLException e) {
			throw new AuthorDaoException(ERROR_MESSAGE_QUERY, e);
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
	public Author getAuthorByName(String firstName, String lastName) throws AuthorDaoException {
		Author author = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(AUTHOR_BY_NAME);
			ps.setString(1, firstName);
			ps.setString(2, lastName);
			rs = ps.executeQuery();
			author = getAuthor(rs);
		} catch (SQLException e) {
			throw new AuthorDaoException(ERROR_MESSAGE_QUERY, e);
		} catch (ConnectionPoolException e) {
			throw new AuthorDaoException(ERROR_MESSAGE_CP, e);
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

	@Override
	public List<Author> getAuthorByCriteria(Criteria criteria, String lang) throws AuthorDaoException {
		List<Author> authorList = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(AUTHOR+criteria.getClause());
			ps.setString(1, lang);
			rs = ps.executeQuery();
			authorList = getAuthors(rs);
		} catch (ConnectionPoolException e) {
			throw new AuthorDaoException(ERROR_MESSAGE_CP, e);
		} catch (SQLException e) {
			throw new AuthorDaoException(ERROR_MESSAGE_QUERY, e);
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
	public void addAuthor(Author author, String lang) throws AuthorDaoException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(ADD_AUTHOR_LANG);
			ps.setString(1, author.getFirstName());
			ps.setString(2, author.getLastName());
			ps.setInt(3, author.getId());
			ps.setString(4, lang);
			ps.executeUpdate();
		} catch (ConnectionPoolException e) {
			throw new AuthorDaoException(ERROR_MESSAGE_CP, e);
		} catch (SQLException e) {
			throw new AuthorDaoException(ERROR_MESSAGE_QUERY, e);
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
	public void updateAuthor(Author author, String lang) throws AuthorDaoException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(UPDATE_AUTHOR_LANG);
			ps.setString(1, author.getFirstName());
			ps.setString(2, author.getLastName());
			ps.setInt(3, author.getId());
			ps.setString(4, lang);
			ps.executeUpdate();
		} catch (ConnectionPoolException e) {
			throw new AuthorDaoException(ERROR_MESSAGE_CP, e);
		} catch (SQLException e) {
			throw new AuthorDaoException(ERROR_MESSAGE_QUERY, e);
		} finally {
			try {
				ps.close();
				} catch (SQLException e) {
			//	 LOG.error("Can't close PreparedStatement");
			}
			conPool.returnConnection(con);
		}				
	}

	@Override
	public void deleteAuthorById(int id, String lang) throws AuthorDaoException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(DELETE_AUTHOR_LANG);
			ps.setInt(1, id);
			ps.setString(2, lang);
			ps.executeUpdate();
		} catch (ConnectionPoolException e) {
			throw new AuthorDaoException(ERROR_MESSAGE_CP, e);
		} catch (SQLException e) {
			throw new AuthorDaoException(ERROR_MESSAGE_QUERY, e);
		} finally {
			try {
				ps.close();
				} catch (SQLException e) {
			//	 LOG.error("Can't close PreparedStatement");
			}
			conPool.returnConnection(con);
		}				
		
	}

	

}
