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
//	private static final Logger LOG = LogManager.getLogger();
	private ConnectionPool conPool = ConnectionPool.getInstance();

	private static final String AUTHORIZATION = "SELECT AccountFirstName, AccountLastName, AccountBirthday, AccountEmail,"
			+ "AccountCreationDate, AccountLogin, AccountPassword, AccountRole, AccountActive, City_id "
			+ " FROM Account " 
			+ "WHERE (AccountLogin = ?) AND (AccountPassword = ?) ;";
	
	private static final String ACCOUNT_BY_CITY = "SELECT AccountFirstName, AccountLastName, AccountBirthday, AccountEmail,"
			+ "AccountCreationDate, AccountLogin, AccountPassword, AccountRole, AccountActive, City_id "
			+ " FROM Account " 
			+ "JOIN City ON City.idCity = Account.City_id "
			+ "WHERE CityName = ? ;";
	private static final String ACCOUNT_BY_COUNTRY = "SELECT AccountFirstName, AccountLastName, AccountBirthday, AccountEmail,"
			+ "AccountCreationDate, AccountLogin, AccountPassword, AccountRole, AccountActive, City_id "
			+ "FROM Account "
			+ "JOIN City ON City.idCity = Account.City_id "
			+ "JOIN Country ON Country.idCountry = City.Country_id "
			+ "WHERE CountryName = ? ;";
	private static final String BANNED_ACCOUNT = "SELECT AccountFirstName, AccountLastName, AccountBirthday, AccountEmail,"
			+ "AccountCreationDate, AccountLogin, AccountPassword, AccountRole, AccountActive, City_id "
			+ "FROM Account "
			+ " WHERE AccountActive = 'false' ;";
	private static final String ACCOUNT_BY_RATE = "SELECT AccountFirstName, AccountLastName, AccountBirthday, AccountEmail,"
			+ "AccountCreationDate, AccountLogin, AccountPassword, AccountRole, AccountActive, City_id,"
			+ "COUNT(Rate) Rate FROM Account "
			+ "JOIN Rate ON Rate.Account_id = Account.idAccount "
			+ "GROUP BY AccountFirstName "
			+ "ORDER BY Rate DESC LIMIT ? ;";
	private static final String ACCOUNT_BY_COMMENT = "SELECT AccountFirstName, AccountLastName, AccountBirthday, AccountEmail,"
			+ "AccountCreationDate, AccountLogin, AccountPassword, AccountRole, AccountActive, City_id,"
			+ " COUNT(CommentText) Comment FROM Account "
			+ "JOIN Comment ON Comment.Account_id = Account.idAccount "
			+ "GROUP BY AccountFirstName "
			+ "ORDER BY Comment DESC LIMIT ? ;";


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
				ps.close();
			} catch (SQLException e) {
			//	LOG.warn("Can't close PreparedStatement or ResultSet");
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
				ps.close();
			} catch (SQLException e) {
	//			LOG.warn("Can't close PreparedStatement or ResultSet");
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
				ps.close();
			} catch (SQLException e) {
	//			LOG.warn("Can't close PreparedStatement or ResultSet");
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
				ps.close();
			} catch (SQLException e) {
	//			LOG.warn("Can't close PreparedStatement or ResultSet");
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
				ps.close();
			} catch (SQLException e) {
	//			LOG.warn("Can't close PreparedStatement or ResultSet");
			}
			conPool.returnConnection(con);
		}
		return userList;
	}

	private Account getAccount(ResultSet rs) throws SQLException {
		if(rs.next()){	
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
		//validation
		Account account = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(AUTHORIZATION);
			ps.setString(1,login);
			ps.setString(2,password);
			rs = ps.executeQuery();
			account = getAccount(rs);
		} catch (ConnectionPoolException e) {
			throw new DaoException("Can't get connection from ConnectionPool", e);
		} catch (SQLException e) {
			throw new DaoException("Can't perform query", e);
		} finally {
			try {
				rs.close();
				ps.close();
			} catch (SQLException e) {
	//			LOG.warn("Can't close PreparedStatement or ResultSet");
			} catch (Exception e){
				
			}
			conPool.returnConnection(con);
		}
		return account;
	}

}
