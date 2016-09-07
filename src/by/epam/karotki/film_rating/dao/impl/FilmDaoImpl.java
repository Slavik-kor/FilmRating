package by.epam.karotki.film_rating.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.epam.karotki.film_rating.dao.Criteria;
import by.epam.karotki.film_rating.dao.DBColumnNames;
import by.epam.karotki.film_rating.dao.FilmDao;
import by.epam.karotki.film_rating.dao.connection_pool.ConnectionPool;
import by.epam.karotki.film_rating.dao.connection_pool.exception.ConnectionPoolException;
import by.epam.karotki.film_rating.dao.exception.FilmDaoException;
import by.epam.karotki.film_rating.entity.Film;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

public class FilmDaoImpl implements FilmDao {
	// private static final Logger LOG = LogManager.getLogger();
	private ConnectionPool conPool = ConnectionPool.getInstance();

	private static final String FILM_BY_RATING = "SELECT idFilm, Title, Description, Budget, BoxOfficeCash, Audience, PremierDate, Duration, WebSite, Poster, Teaser, ROUND(AVG(Rate),2) Rating "
			+ "from(select g.idFilm idFilm, coalesce(t.title,g.title) title, coalesce(t.description,g.description) description "
			+ "from (film as g left join (select * from film_lang where lang = '?')  t using(idFilm))) films "
			+ "JOIN rate ON rate.Film_id = films.idFilm GROUP BY title ORDER BY Rating Desc LIMIT ?;";

	private static final String FILM_BY_ACTOR = "SELECT idFilm, Title, Description, Budget, BoxOfficeCash, Audience, PremierDate, Duration, WebSite, Poster, Teaser FROM film "
			+ "JOIN Film_has_Authors ON Film_has_Authors.Film_id = film.idFilm "
			+ "JOIN Author ON Author.idAuthor = Film_has_Authors.Authors_idAuthors "
			+ "WHERE (AuthorFirstName = ?) AND (AuthorLastName = ?) AND (Role = 'Actor');";

	private static final String FILM_BY_DIRECTOR = "SELECT idFilm, Title, Description, Budget, BoxOfficeCash, Audience, PremierDate, Duration, WebSite, Poster, Teaser FROM film "
			+ "JOIN Film_has_Authors ON Film_has_Authors.Film_id = film.idFilm "
			+ "JOIN Author ON Author.idAuthor = Film_has_Authors.Authors_idAuthors "
			+ "WHERE (AuthorFirstName = ?) AND (AuthorLastName = ?) AND (Role = 'Director');";

	private static final String FILM_BY_SCENARIO_WRITER = "SELECT idFilm, Title, Description, Budget, BoxOfficeCash, Audience, PremierDate, Duration, WebSite, Poster, Teaser FROM film "
			+ "JOIN Film_has_Authors ON Film_has_Authors.Film_id = film.idFilm "
			+ "JOIN Author ON Author.idAuthor = Film_has_Authors.Authors_idAuthors "
			+ "WHERE (AuthorFirstName = ?) AND (AuthorLastName = ?) AND (Role = 'ScenarioWriter');";

	private static final String FILM_BY_GENRE = "SELECT idFilm, Title, Description, Budget, BoxOfficeCash, Audience, PremierDate, Duration, WebSite, Poster, Teaser FROM film "
			+ "JOIN Film_Genre ON Film_Genre.Film_id = film.idFilm "
			+ "JOIN Genre ON Genre.idGenre = Film_Genre.Genre_id " + "WHERE Name = ? ;";

	private static final String FILM_BY_BUDGET = "SELECT idFilm, Title, Description, Budget, BoxOfficeCash, Audience, PremierDate, Duration, WebSite, Poster, Teaser FROM film "
			+ "ORDER BY Budget DESC LIMIT ? ;";

	private static final String FILM_BY_BOX_OFFICE_CASH = "SELECT idFilm, Title, Description, Budget, BoxOfficeCash, Audience, PremierDate, Duration, WebSite, Poster, Teaser FROM film "
			+ "ORDER BY BoxOfficeCash DESC LIMIT ?";

	private static final String NEWEST_FILM = "SELECT idFilm, Title, Description, Budget, BoxOfficeCash, Audience, PremierDate, Duration, WebSite, Poster, Teaser FROM film "
			+ "ORDER BY PremierDate DESC LIMIT ? ;";

	private static final String FILM_BY_ID = "SELECT idFilm, Title, Description, Budget, BoxOfficeCash, Audience, PremierDate, Duration, WebSite, Poster, Teaser FROM film "
			+ "WHERE idFilm = ?";

	private static final String FILM = "SELECT idFilm, Title, Description, Budget, BoxOfficeCash, Audience, PremierDate, Duration, WebSite, Poster, Teaser "
			+ "from(select g.idFilm idFilm, coalesce(t.title,g.title) title, coalesce(t.description,g.description) description,Budget, BoxOfficeCash, Audience, PremierDate, Duration, WebSite, Poster, Teaser "
			+ "from (film as g left join (select * from film_lang where lang = ?)  t using(idFilm))) films ";

	private static final String FILM_BY_TITLE = "SELECT idFilm, Title, Description, Budget, BoxOfficeCash, Audience, PremierDate, Duration, WebSite, Poster, Teaser FROM film "
			+ "WHERE Title = ?";

	private static final String ADD_FILM = "INSERT INTO film (Title, Description, Budget, BoxOfficeCash, Audience, PremierDate, Duration, WebSite, Poster, Teaser) "
			+ "VALUES(?,?,?,?,?,?,?,?,?,?)";
	
	private static final String ADD_FILM_LANG = "INSERT INTO film_lang (Title, Description, idFilm, lang) "
			+ "VALUES(?,?,?,?)";

	private static final String UPDATE_FILM = "UPDATE film SET Title=?, Description=?, Budget=?, BoxOfficeCash=?, Audience=?, PremierDate=?, Duration=?, WebSite=?, Poster=?, Teaser=? WHERE idFilm=? ";

	private static final String UPDATE_FILM_LANG = "UPDATE film_lang SET Title = ?, Description = ? WHERE (idFilm = ?) AND (lang = ?) ";
	
	private static final String DELETE_FILM = "DELETE FROM film WHERE idFilm = ? ";

	private static final String DELETE_FILM_LANG = "DELETE FROM film_lang WHERE (idFilm = ?) AND (lang = ?)";

	@Override
	public List<Film> getTopFilmsByRating(int value, String lang) throws FilmDaoException {
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
			throw new FilmDaoException("Can't get connection from ConnectionPool", e);
		} catch (SQLException e) {
			throw new FilmDaoException("Can't perform query", e);
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
		return filmList;
	}

	@Override
	public List<Film> getFilmsByActors(String firstName, String lastName) throws FilmDaoException {
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
			throw new FilmDaoException("Can't get connection from ConnectionPool", e);
		} catch (SQLException e) {
			throw new FilmDaoException("Can't perform query", e);
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
		return filmList;
	}

	@Override
	public List<Film> getFilmsByDirectors(String firstName, String lastName) throws FilmDaoException {
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
			throw new FilmDaoException("Can't get connection from ConnectionPool", e);
		} catch (SQLException e) {
			throw new FilmDaoException("Can't perform query", e);
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
		return filmList;
	}

	@Override
	public List<Film> getFilmsByScenarioWriters(String firstName, String lastName) throws FilmDaoException {
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
			throw new FilmDaoException("Can't get connection from ConnectionPool", e);
		} catch (SQLException e) {
			throw new FilmDaoException("Can't perform query", e);
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
		return filmList;
	}

	@Override
	public List<Film> getFilmsByGenre(String genre) throws FilmDaoException {
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
			throw new FilmDaoException("Can't get connection from ConnectionPool", e);
		} catch (SQLException e) {
			throw new FilmDaoException("Can't perform query", e);
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
		return filmList;
	}

	@Override
	public List<Film> getMostBudgetFilms(int value) throws FilmDaoException {
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
			throw new FilmDaoException("Can't get connection from ConnectionPool", e);
		} catch (SQLException e) {
			throw new FilmDaoException("Can't perform query", e);
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
		return filmList;
	}

	@Override
	public List<Film> getMostCashBoxFilms(int value) throws FilmDaoException {
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
			throw new FilmDaoException("Can't get connection from ConnectionPool", e);
		} catch (SQLException e) {
			throw new FilmDaoException("Can't perform query", e);
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
		return filmList;
	}

	@Override
	public List<Film> getNewestFilms(int value) throws FilmDaoException {
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
			throw new FilmDaoException("Can't get connection from ConnectionPool", e);
		} catch (SQLException e) {
			throw new FilmDaoException("Can't perform query", e);
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
		return filmList;

	}

	@Override
	public Film getFilmById(int id) throws FilmDaoException {
		Film film = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(FILM_BY_ID);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			film = getFilm(rs);
		} catch (ConnectionPoolException e) {
			throw new FilmDaoException("Can't get connection from ConnectionPool", e);
		} catch (SQLException e) {
			throw new FilmDaoException("Can't perform query", e);
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

		return film;
	}

	private Film getFilm(ResultSet rs) throws SQLException {
		while (rs.next()) {
			Film film = new Film();
			film.setId(rs.getInt(DBColumnNames.FILM_ID));
			film.setTitle(rs.getString(DBColumnNames.FILM_TITLE));
			film.setDescription(rs.getString(DBColumnNames.FILM_DESCRIPTION));
			film.setBudget(rs.getDouble(DBColumnNames.FILM_BUDGET));
			film.setBoxOfficeCash(rs.getDouble(DBColumnNames.FILM_BOX_OFFICE_CASH));
			film.setAudience(rs.getInt(DBColumnNames.FILM_AUDIENCE));
			film.setPremierDate(rs.getDate(DBColumnNames.FILM_PREMIER_DATE));
			film.setDuration(rs.getTime(DBColumnNames.FILM_DURATION));
			film.setWebSite(rs.getString(DBColumnNames.FILM_SITE));
			film.setPoster(rs.getString(DBColumnNames.FILM_POSTER));
			film.setTeaser(rs.getString(DBColumnNames.FILM_TEASER));
			return film;
		}
		return null;
	}

	private List<Film> getFilms(ResultSet rs) throws SQLException {
		List<Film> filmList = new ArrayList<Film>();
		while (rs.next()) {
			Film film = new Film();
			film.setId(rs.getInt(DBColumnNames.FILM_ID));
			film.setTitle(rs.getString(DBColumnNames.FILM_TITLE));
			film.setDescription(rs.getString(DBColumnNames.FILM_DESCRIPTION));
			film.setBudget(rs.getDouble(DBColumnNames.FILM_BUDGET));
			film.setBoxOfficeCash(rs.getDouble(DBColumnNames.FILM_BOX_OFFICE_CASH));
			film.setAudience(rs.getInt(DBColumnNames.FILM_AUDIENCE));
			film.setPremierDate(rs.getDate(DBColumnNames.FILM_PREMIER_DATE));
			film.setDuration(rs.getTime(DBColumnNames.FILM_DURATION));
			film.setWebSite(rs.getString(DBColumnNames.FILM_SITE));
			film.setPoster(rs.getString(DBColumnNames.FILM_POSTER));
			film.setTeaser(rs.getString(DBColumnNames.FILM_TEASER));
			filmList.add(film);
		}
		return filmList;
	}

	@Override
	public void addFilm(Film film) throws FilmDaoException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(ADD_FILM);
			ps.setString(1, film.getTitle());
			ps.setString(2, film.getDescription());
			ps.setDouble(3, film.getBudget());
			ps.setDouble(4, film.getBoxOfficeCash());
			ps.setInt(5, film.getAudience());
			ps.setDate(6, film.getPremierDate());
			ps.setTime(7, film.getDuration());
			ps.setString(8, film.getWebSite());
			ps.setString(9, film.getPoster());
			ps.setString(10, film.getTeaser());
			ps.executeUpdate();
		} catch (ConnectionPoolException e) {
			throw new FilmDaoException("Can't get connection from ConnectionPool", e);
		} catch (SQLException e) {
			throw new FilmDaoException("Can't perform query", e);
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
	public void updateFilm(Film film) throws FilmDaoException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(UPDATE_FILM);
			ps.setString(1, film.getTitle());
			ps.setString(2, film.getDescription());
			ps.setDouble(3, film.getBudget());
			ps.setDouble(4, film.getBoxOfficeCash());
			ps.setInt(5, film.getAudience());
			ps.setDate(6, film.getPremierDate());
			ps.setTime(7, film.getDuration());
			ps.setString(8, film.getWebSite());
			ps.setString(9, film.getPoster());
			ps.setString(10, film.getTeaser());
			ps.setInt(11, film.getId());
			ps.executeUpdate();

		} catch (ConnectionPoolException e) {
			throw new FilmDaoException("Can't get connection from ConnectionPool", e);
		} catch (SQLException e) {
			throw new FilmDaoException("Can't perform query", e);
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
	public void deleteFilmById(int id) throws FilmDaoException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(DELETE_FILM);
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (ConnectionPoolException e) {
			throw new FilmDaoException("Can't get connection from ConnectionPool", e);
		} catch (SQLException e) {
			throw new FilmDaoException("Can't perform query", e);
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
	public Film getFilmByTitle(String title) throws FilmDaoException {
		Film film = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(FILM_BY_TITLE);
			ps.setString(1, title);
			rs = ps.executeQuery();
			film = getFilm(rs);
		} catch (ConnectionPoolException e) {
			throw new FilmDaoException("Can't get connection from ConnectionPool", e);
		} catch (SQLException e) {
			throw new FilmDaoException("Can't perform query", e);
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

		return film;
	}

	@Override
	public List<Film> getFilmListByCriteria(Criteria criteria, String lang) throws FilmDaoException {
		List<Film> filmList = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(FILM + criteria.getClause());
			ps.setString(1, lang);
			rs = ps.executeQuery();
			filmList = getFilms(rs);
		} catch (ConnectionPoolException e) {
			throw new FilmDaoException("Can't get connection from ConnectionPool", e);
		} catch (SQLException e) {
			throw new FilmDaoException("Can't perform query", e);
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
		return filmList;
	}

	@Override
	public void addFilm(Film film, String lang) throws FilmDaoException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(ADD_FILM_LANG);
			ps.setString(1, film.getTitle());
			ps.setString(2, film.getDescription());
			ps.setInt(3, film.getId());
			ps.setString(4,lang);
			ps.executeUpdate();
		} catch (ConnectionPoolException e) {
			throw new FilmDaoException("Can't get connection from ConnectionPool", e);
		} catch (SQLException e) {
			throw new FilmDaoException("Can't perform query", e);
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
	public void updateFilm(Film film, String lang) throws FilmDaoException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(UPDATE_FILM_LANG);
			ps.setString(1, film.getTitle());
			ps.setString(2, film.getDescription());
			ps.setInt(3, film.getId());
			ps.setString(4, lang);
			ps.executeUpdate();

		} catch (ConnectionPoolException e) {
			throw new FilmDaoException("Can't get connection from ConnectionPool", e);
		} catch (SQLException e) {
			throw new FilmDaoException("Can't perform query", e);
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
	public void deleteFilmById(int id, String lang) throws FilmDaoException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(DELETE_FILM_LANG);
			ps.setInt(1, id);
			ps.setString(2, lang);
			ps.executeUpdate();
		} catch (ConnectionPoolException e) {
			throw new FilmDaoException("Can't get connection from ConnectionPool", e);
		} catch (SQLException e) {
			throw new FilmDaoException("Can't perform query", e);
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
