package by.epam.karotki.film_rating.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.epam.karotki.film_rating.dao.DBColumnNames;
import by.epam.karotki.film_rating.dao.AccountDao;
import by.epam.karotki.film_rating.dao.connection_pool.ConnectionPool;
import by.epam.karotki.film_rating.dao.connection_pool.exception.ConnectionPoolException;
import by.epam.karotki.film_rating.dao.exception.DaoException;
import by.epam.karotki.film_rating.entity.Account;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

public class AccountDaoImpl implements AccountDao {
	// private static final Logger LOG = LogManager.getLogger();
	private ConnectionPool conPool = ConnectionPool.getInstance();

	private static final String AUTHORIZATION = "SELECT AccountFirstName, AccountLastName, AccountBirthday, AccountEmail,"
			+ "AccountCreationDate, AccountLogin, AccountPassword, AccountRole, AccountActive, City_id "
			+ " FROM Account " + "WHERE (AccountLogin = ?) AND (AccountPassword = ?) ;";
	
	private static final String GET_ACCOUNT_BY_LOGIN = "SELECT AccountFirstName, AccountLastName, AccountBirthday, AccountEmail,"
			+ "AccountCreationDate, AccountLogin, AccountPassword, AccountRole, AccountActive, City_id "
			+ " FROM Account " + "WHERE (AccountLogin = ?);";

	private static final String ACCOUNT_BY_CITY = "SELECT AccountFirstName, AccountLastName, AccountBirthday, AccountEmail,"
			+ "AccountCreationDate, AccountLogin, AccountPassword, AccountRole, AccountActive, City_id "
			+ " FROM Account " + "JOIN City ON City.idCity = Account.City_id " + "WHERE CityName = ? ;";
	private static final String ACCOUNT_BY_COUNTRY = "SELECT AccountFirstName, AccountLastName, AccountBirthday, AccountEmail,"
			+ "AccountCreationDate, AccountLogin, AccountPassword, AccountRole, AccountActive, City_id "
			+ "FROM Account " + "JOIN City ON City.idCity = Account.City_id "
			+ "JOIN Country ON Country.idCountry = City.Country_id " + "WHERE CountryName = ? ;";
	private static final String BANNED_ACCOUNT = "SELECT AccountFirstName, AccountLastName, AccountBirthday, AccountEmail,"
			+ "AccountCreationDate, AccountLogin, AccountPassword, AccountRole, AccountActive, City_id "
			+ "FROM Account " + " WHERE AccountActive = 'false' ;";
	private static final String ACCOUNT_BY_RATE = "SELECT AccountFirstName, AccountLastName, AccountBirthday, AccountEmail,"
			+ "AccountCreationDate, AccountLogin, AccountPassword, AccountRole, AccountActive, City_id,"
			+ "COUNT(Rate) Rate FROM Account " + "JOIN Rate ON Rate.Account_id = Account.idAccount "
			+ "GROUP BY AccountFirstName " + "ORDER BY Rate DESC LIMIT ? ;";
	private static final String ACCOUNT_BY_COMMENT = "SELECT AccountFirstName, AccountLastName, AccountBirthday, AccountEmail,"
			+ "AccountCreationDate, AccountLogin, AccountPassword, AccountRole, AccountActive, City_id,"
			+ " COUNT(CommentText) Comment FROM Account " + "JOIN Comment ON Comment.Account_id = Account.idAccount "
			+ "GROUP BY AccountFirstName " + "ORDER BY Comment DESC LIMIT ? ;";

	private static final String ADD_ACCOUNT = "INSERT INTO Account (AccountFirstName, AccountLastName, AccountBirthday, AccountEmail, "
			+ "AccountCreationDate, AccountLogin, AccountPassword, AccountRole, Phone, Photo) "
			+ "VALUES (?, ?, ?, ?, NOW(), ?, ?, 'User', ?, ?);";
	
	
	private static final String UPDATE_ACCOUNT = "UPDATE INTO `FilmRate`.`Account` SET `AccountFirstName`='?', `AccountLastName`='?',"
			+ " `AccountBirthday`='?', `AccountEmail`='?', `AccountCreationDate`='?', `AccountLogin`='?', `AccountPassword`='?',"
			+ " `AccountRole`='?', `AccountActive`='?', `City_id`=?, `Phone`='?', `Photo`='?' WHERE idAccount=?;";

	@Override
	public List<Account> getUsersByCity(String city) throws DaoException {
		List<Account> userList = new ArrayList<Account>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(ACCOUNT_BY_CITY);
			ps.setString(1, city);
			rs = ps.executeQuery();
			userList = getAccounts(rs);
		} catch (ConnectionPoolException e) {
			throw new DaoException("Can't get connection from ConnectionPool", e);
		} catch (SQLException e) {
			throw new DaoException("Can't perform query", e);
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
		return userList;
	}

	@Override
	public List<Account> getUsersByCountry(String country) throws DaoException {
		List<Account> userList = new ArrayList<Account>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(ACCOUNT_BY_COUNTRY);
			ps.setString(1, country);
			rs = ps.executeQuery();
			userList = getAccounts(rs);
		} catch (ConnectionPoolException e) {
			throw new DaoException("Can't get connection from ConnectionPool", e);
		} catch (SQLException e) {
			throw new DaoException("Can't perform query", e);
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
		return userList;
	}

	@Override
	public List<Account> getBannedUsers() throws DaoException {
		List<Account> userList = new ArrayList<Account>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(BANNED_ACCOUNT);
			rs = ps.executeQuery();
			userList = getAccounts(rs);
		} catch (ConnectionPoolException e) {
			throw new DaoException("Can't get connection from ConnectionPool", e);
		} catch (SQLException e) {
			throw new DaoException("Can't perform query", e);
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
		return userList;
	}

	@Override
	public List<Account> getActiveUsersByRate(int value) throws DaoException {
		List<Account> userList = new ArrayList<Account>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(ACCOUNT_BY_RATE);
			ps.setInt(1, value);
			rs = ps.executeQuery();
			userList = getAccounts(rs);
		} catch (ConnectionPoolException e) {
			throw new DaoException("Can't get connection from ConnectionPool", e);
		} catch (SQLException e) {
			throw new DaoException("Can't perform query", e);
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
		return userList;
	}

	@Override
	public List<Account> getActiveUsersByComment(int value) throws DaoException {
		List<Account> userList = new ArrayList<Account>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(ACCOUNT_BY_COMMENT);
			ps.setInt(1, value);
			rs = ps.executeQuery();
			userList = getAccounts(rs);
		} catch (ConnectionPoolException e) {
			throw new DaoException("Can't get connection from ConnectionPool", e);
		} catch (SQLException e) {
			throw new DaoException("Can't perform query", e);
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
		return userList;
	}

	private Account getAccount(ResultSet rs) throws SQLException {
		if (rs.next()) {
			Account user = new Account();
			user.setFirstName(rs.getString(DBColumnNames.ACCOUNT_FIRST_NAME));
			user.setLastName(rs.getString(DBColumnNames.ACCOUNT_LAST_NAME));
			user.setBirthDay(rs.getDate(DBColumnNames.ACCOUNT_BIRTH_DAY));
			user.setEmail(rs.getString(DBColumnNames.ACCOUNT_EMAIL));
			user.setCreationDate(rs.getDate(DBColumnNames.ACCOUNT_CREATION_DATE));
			user.setLogin(rs.getString(DBColumnNames.ACCOUNT_LOGIN));
			user.setPassword(rs.getString(DBColumnNames.ACCOUNT_PASSWORD));
			user.setRole(rs.getString(DBColumnNames.ACCOUNT_ROLE));
			user.setActive(rs.getBoolean(DBColumnNames.ACCOUNT_IS_ACTIVE));
			user.setCityId(rs.getInt(DBColumnNames.ACCOUNT_CITY_ID));
			return user;
		}

		return null;
	}

	private List<Account> getAccounts(ResultSet rs) throws SQLException {
		List<Account> userList = new ArrayList<Account>();
		while (rs.next()) {
			Account user = new Account();
			user.setFirstName(rs.getString(DBColumnNames.ACCOUNT_FIRST_NAME));
			user.setLastName(rs.getString(DBColumnNames.ACCOUNT_LAST_NAME));
			user.setBirthDay(rs.getDate(DBColumnNames.ACCOUNT_BIRTH_DAY));
			user.setEmail(rs.getString(DBColumnNames.ACCOUNT_EMAIL));
			user.setCreationDate(rs.getDate(DBColumnNames.ACCOUNT_CREATION_DATE));
			user.setLogin(rs.getString(DBColumnNames.ACCOUNT_LOGIN));
			user.setPassword(rs.getString(DBColumnNames.ACCOUNT_PASSWORD));
			user.setRole(rs.getString(DBColumnNames.ACCOUNT_ROLE));
			user.setActive(rs.getBoolean(DBColumnNames.ACCOUNT_IS_ACTIVE));
			user.setCityId(rs.getInt(DBColumnNames.ACCOUNT_CITY_ID));
			userList.add(user);
		}
		return userList;
	}

	@Override
	public Account authorization(String login, String password) throws DaoException {
		Account account = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(AUTHORIZATION);
			ps.setString(1, login);
			ps.setString(2, password);
			rs = ps.executeQuery();
			account = getAccount(rs);
		} catch (ConnectionPoolException e) {
			throw new DaoException("Can't get connection from ConnectionPool", e);
		} catch (SQLException e) {
			throw new DaoException("Can't perform query", e);
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
		return account;
	}

	@Override
	public void addAccount(Account account) throws DaoException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(ADD_ACCOUNT);
			System.out.println("before firstname");
			ps.setString(1, account.getFirstName());
			System.out.println("between firstname and lastname");
			ps.setString(2, account.getLastName());
			System.out.println("between lastname and birth");
			ps.setDate(3, account.getBirthDay());
			System.out.println("between  birth and mail");
			ps.setString(4, account.getEmail());
			System.out.println("between mail and login");
			ps.setString(5, account.getLogin());
			System.out.println("between login and pass");
			ps.setString(6, account.getPassword());
			System.out.println("between pass and role");
			System.out.println("between city and phone");
			ps.setString(7, account.getPhone());
			System.out.println("between phone and photo");
			ps.setString(8, account.getPhoto());
			System.out.println("before query");
			//ps.setInt(9,account.getCityId());
			ps.executeUpdate();
			System.out.println("after query");
		} catch (ConnectionPoolException e) {
			System.out.println("ConnectionPoolException");
			throw new DaoException("Can't get connection from ConnectionPool", e);
		} catch (SQLException e) {
			System.out.println("SQLException");
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
	public void updateAccount(Account account) throws DaoException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(UPDATE_ACCOUNT);
			ps.setString(1, account.getFirstName());
			ps.setString(2, account.getLastName());
			ps.setDate(3, account.getBirthDay());
			ps.setString(4, account.getEmail());
			ps.setDate(5, account.getCreationDate());
			ps.setString(6, account.getLogin());
			ps.setString(7, account.getPassword());
			ps.setString(8, account.getRole());
			ps.setInt(9, account.getCityId());
			ps.setString(10, account.getPhone());
			ps.setString(11, account.getPhoto());
			ps.setBoolean(12, account.isActive());
			ps.setInt(13, account.getId());
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
	public Account getAccountByLogin(String login) throws DaoException {
		Account account = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(GET_ACCOUNT_BY_LOGIN);
			ps.setString(1, login);
			rs = ps.executeQuery();
			account = getAccount(rs);
		} catch (ConnectionPoolException e) {
			throw new DaoException("Can't get connection from ConnectionPool", e);
		} catch (SQLException e) {
			throw new DaoException("Can't perform query", e);
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
		return account;
	
	}

}
