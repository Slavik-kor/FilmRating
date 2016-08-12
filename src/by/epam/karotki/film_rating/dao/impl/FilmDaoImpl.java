package by.epam.karotki.film_rating.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.epam.karotki.film_rating.dao.DBColumnNames;
import by.epam.karotki.film_rating.dao.FilmDao;
import by.epam.karotki.film_rating.dao.connection_pool.ConnectionPool;
import by.epam.karotki.film_rating.dao.connection_pool.exception.ConnectionPoolException;
import by.epam.karotki.film_rating.dao.exception.DaoException;
import by.epam.karotki.film_rating.entity.Film;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

public class FilmDaoImpl implements FilmDao {
	// private static final Logger LOG = LogManager.getLogger();
	private ConnectionPool conPool = ConnectionPool.getInstance();

	private static final String FILM_BY_RATING = "SELECT title, Budget, BoxOfficeCash ROUND(AVG(Rate),2) Rating "
			+ "from(select g.idFilm idFilm, coalesce(t.title,g.title) title, coalesce(t.description,g.description) description "
			+ "from (film as g left join (select * from film_lang where lang = '?')  t using(idFilm))) films "
			+ "JOIN rate ON rate.Film_id = films.idFilm GROUP BY title ORDER BY Rating Desc LIMIT ?;";

	private static final String FILM_BY_ACTOR = "SELECT Title, Budget, BoxOfficeCash FROM film "
			+ "JOIN Film_has_Authors ON Film_has_Authors.Film_id = film.idFilm "
			+ "JOIN Author ON Author.idAuthor = Film_has_Authors.Authors_idAuthors "
			+ "WHERE (AuthorFirstName = ?) AND (AuthorLastName = ?) AND (Role = 'Actor');";

	private static final String FILM_BY_DIRECTOR = "SELECT Title, Budget, BoxOfficeCash FROM film "
			+ "JOIN Film_has_Authors ON Film_has_Authors.Film_id = film.idFilm "
			+ "JOIN Author ON Author.idAuthor = Film_has_Authors.Authors_idAuthors "
			+ "WHERE (AuthorFirstName = ?) AND (AuthorLastName = ?) AND (Role = 'Director');";

	private static final String FILM_BY_SCENARIO_WRITER = "SELECT Title, Budget, BoxOfficeCash FROM film "
			+ "JOIN Film_has_Authors ON Film_has_Authors.Film_id = film.idFilm "
			+ "JOIN Author ON Author.idAuthor = Film_has_Authors.Authors_idAuthors "
			+ "WHERE (AuthorFirstName = ?) AND (AuthorLastName = ?) AND (Role = 'ScenarioWriter');";

	private static final String FILM_BY_GENRE = "SELECT Title, Budget, BoxOfficeCash FROM film "
			+ "JOIN Film_Genre ON Film_Genre.Film_id = film.idFilm "
			+ "JOIN Genre ON Genre.idGenre = Film_Genre.Genre_id " + "WHERE Name = ? ;";

	private static final String FILM_BY_BUDGET = "SELECT Title, Budget, BoxOfficeCash FROM film "
			+ "ORDER BY Budget DESC LIMIT ? ;";

	private static final String FILM_BY_BOX_OFFICE_CASH = "SELECT Title, Budget, BoxOfficeCash FROM film "
			+ "ORDER BY BoxOfficeCash DESC LIMIT ?";
	
	private static final String NEWEST_FILM = "SELECT Title, Budget, BoxOfficeCash, PremierDate FROM film "
			+ "ORDER BY PremierDate DESC LIMIT ? ;";

	@Override
	public List<Film> getTopFilmsByRating(int value, String lang) throws DaoException {
		List<Film> filmList = new ArrayList<Film>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(FILM_BY_RATING);
			ps.setString(1, lang);
			ps.setInt(2, value);
			rs = ps.executeQuery();
			filmList = getFilms(rs);
		} catch (ConnectionPoolException e) {
			throw new DaoException("Can't get connection from ConnectionPool", e);
		} catch (SQLException e) {
			throw new DaoException("Can't perform query", e);
		} finally {
			try {
				rs.close();
				ps.close();
			} catch (SQLException e) {
				// LOG.warn("Can't close PreparedStatement or ResultSet");
			}
			conPool.returnConnection(con);
		}
		return filmList;
	}

	@Override
	public List<Film> getFilmsByActors(String firstName, String lastName) throws DaoException {
		List<Film> filmList = new ArrayList<Film>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(FILM_BY_ACTOR);
			ps.setString(1, firstName);
			ps.setString(2, lastName);
			rs = ps.executeQuery();
			filmList = getFilms(rs);
		} catch (ConnectionPoolException e) {
			throw new DaoException("Can't get connection from ConnectionPool", e);
		} catch (SQLException e) {
			throw new DaoException("Can't perform query", e);
		} finally {
			try {
				rs.close();
				ps.close();
			} catch (SQLException e) {
				// LOG.warn("Can't close PreparedStatement or ResultSet");
			}
			conPool.returnConnection(con);
		}
		return filmList;
	}

	@Override
	public List<Film> getFilmsByDirectors(String firstName, String lastName) throws DaoException {
		List<Film> filmList = new ArrayList<Film>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(FILM_BY_DIRECTOR);
			ps.setString(1, firstName);
			ps.setString(2, lastName);
			rs = ps.executeQuery();
			filmList = getFilms(rs);
		} catch (ConnectionPoolException e) {
			throw new DaoException("Can't get connection from ConnectionPool", e);
		} catch (SQLException e) {
			throw new DaoException("Can't perform query", e);
		} finally {
			try {
				rs.close();
				ps.close();
			} catch (SQLException e) {
				// LOG.warn("Can't close PreparedStatement or ResultSet");
			}
			conPool.returnConnection(con);
		}
		return filmList;
	}

	@Override
	public List<Film> getFilmsByScenarioWriters(String firstName, String lastName) throws DaoException {
		List<Film> filmList = new ArrayList<Film>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(FILM_BY_SCENARIO_WRITER);
			ps.setString(1, firstName);
			ps.setString(2, lastName);
			rs = ps.executeQuery();
			filmList = getFilms(rs);
		} catch (ConnectionPoolException e) {
			throw new DaoException("Can't get connection from ConnectionPool", e);
		} catch (SQLException e) {
			throw new DaoException("Can't perform query", e);
		} finally {
			try {
				rs.close();
				ps.close();
			} catch (SQLException e) {
				// LOG.warn("Can't close PreparedStatement or ResultSet");
			}
			conPool.returnConnection(con);
		}
		return filmList;
	}

	@Override
	public List<Film> getFilmsByGenre(String genre) throws DaoException {
		List<Film> filmList = new ArrayList<Film>();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(FILM_BY_GENRE);
			ps.setString(1, genre);
			rs = ps.executeQuery();
			filmList = getFilms(rs);
		} catch (ConnectionPoolException e) {
			throw new DaoException("Can't get connection from ConnectionPool", e);
		} catch (SQLException e) {
			throw new DaoException("Can't perform query", e);
		} finally {
			try {
				rs.close();
				ps.close();
				conPool.returnConnection(con);
			} catch (SQLException e) {
				// LOG.warn("Can't close PreparedStatement or ResultSet");
			}
		}
		return filmList;
	}

	@Override
	public List<Film> getMostBudgetFilms(int value) throws DaoException {
		List<Film> filmList = new ArrayList<Film>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(FILM_BY_BUDGET);
			ps.setInt(1, value);
			rs = ps.executeQuery();
			filmList = getFilms(rs);
		} catch (ConnectionPoolException e) {
			throw new DaoException("Can't get connection from ConnectionPool", e);
		} catch (SQLException e) {
			throw new DaoException("Can't perform query", e);
		} finally {
			try {
				rs.close();
				ps.close();
			} catch (SQLException e) {
				// LOG.warn("Can't close PreparedStatement or ResultSet");
			}
		}
		conPool.returnConnection(con);
		return filmList;
	}

	@Override
	public List<Film> getMostCashBoxFilms(int value) throws DaoException {
		List<Film> filmList = new ArrayList<Film>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(FILM_BY_BOX_OFFICE_CASH);
			ps.setInt(1, value);
			rs = ps.executeQuery();
			filmList = getFilms(rs);
		} catch (ConnectionPoolException e) {
			throw new DaoException("Can't get connection from ConnectionPool", e);
		} catch (SQLException e) {
			throw new DaoException("Can't perform query", e);
		} finally {
			try {
				rs.close();
				ps.close();
			} catch (SQLException e) {
				// LOG.warn("Can't close PreparedStatement or ResultSet");
			}
			conPool.returnConnection(con);
		}
		return filmList;
	}
	
	@Override
	public List<Film> getNewestFilms(int value) throws DaoException {
		List<Film> filmList = new ArrayList<Film>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(NEWEST_FILM);
			ps.setInt(1, value);
			rs = ps.executeQuery();
			filmList = getFilms(rs);
		} catch (ConnectionPoolException e) {
			throw new DaoException("Can't get connection from ConnectionPool", e);
		} catch (SQLException e) {
			throw new DaoException("Can't perform query", e);
		} finally {
			try {
				rs.close();
				ps.close();
			} catch (SQLException e) {
				// LOG.warn("Can't close PreparedStatement or ResultSet");
			}
			conPool.returnConnection(con);
		}
		return filmList;
		
	}

	private List<Film> getFilms(ResultSet rs) throws SQLException {
		List<Film> filmList = new ArrayList<Film>();
		while (rs.next()) {
			Film film = new Film();
			film.setTitle(rs.getString(DBColumnNames.FILM_TITLE));
			film.setBudget(rs.getDouble(DBColumnNames.FILM_BUDGET));
			film.setBoxOfficeCash(rs.getDouble(DBColumnNames.FILM_BOX_OFFICE_CASH));
			filmList.add(film);
		}
		return filmList;
	}

	

}
