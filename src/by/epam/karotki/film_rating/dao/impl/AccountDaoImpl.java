package by.epam.karotki.film_rating.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import by.epam.karotki.film_rating.dao.AccountDao;
import by.epam.karotki.film_rating.dao.DBColumnNames;
import by.epam.karotki.film_rating.dao.connection_pool.ConnectionPool;
import by.epam.karotki.film_rating.dao.connection_pool.exception.ConnectionPoolException;
import by.epam.karotki.film_rating.dao.exception.AccountDaoException;
import by.epam.karotki.film_rating.entity.Account;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

public class AccountDaoImpl implements AccountDao {
	// private static final Logger LOG = LogManager.getLogger();
	private ConnectionPool conPool = ConnectionPool.getInstance();

	private static final String AUTHORIZATION = "SELECT idAccount, AccountFirstName, AccountLastName, AccountBirthday, AccountEmail,"
			+ "AccountCreationDate, AccountLogin, AccountPassword, AccountRole, AccountActive, Country_id, Phone, Photo "
			+ " FROM Account " + "WHERE (AccountLogin = ?) AND (AccountPassword = ?) ;";
	
	private static final String GET_ACCOUNT_BY_LOGIN = "SELECT idAccount,AccountFirstName, AccountLastName, AccountBirthday, AccountEmail,"
			+ "AccountCreationDate, AccountLogin, AccountPassword, AccountRole, AccountActive, Country_id, Phone, Photo "
			+ " FROM Account " + "WHERE (AccountLogin = ?)";

	private static final String ACCOUNT_BY_COUNTRY = "SELECT idAccount,AccountFirstName, AccountLastName, AccountBirthday, AccountEmail,"
			+ "AccountCreationDate, AccountLogin, AccountPassword, AccountRole, AccountActive, Country_id, Phone, Photo "
			+ " FROM Account " + "JOIN Country ON Country.idCountry = Account.Country_id " + "WHERE CountryName = ? ;";
	
	private static final String BANNED_ACCOUNT = "SELECT idAccount,AccountFirstName, AccountLastName, AccountBirthday, AccountEmail,"
			+ "AccountCreationDate, AccountLogin, AccountPassword, AccountRole, AccountActive, Country_id, Phone, Photo "
			+ "FROM Account " + " WHERE AccountActive = 'false' ;";
	
	
	private static final String ACCOUNT_BY_COMMENT = "SELECT idAccount,AccountFirstName, AccountLastName, AccountBirthday, AccountEmail,"
			+ "AccountCreationDate, AccountLogin, AccountPassword, AccountRole, AccountActive, Country_id, Phone, Photo"
			+ " COUNT(CommentText) Comment FROM Account " + "JOIN Comment ON Comment.Account_id = Account.idAccount "
			+ "GROUP BY AccountFirstName " + "ORDER BY Comment DESC LIMIT ? ;";

	private static final String ADD_ACCOUNT = "INSERT INTO Account (AccountFirstName, AccountLastName, AccountBirthday, AccountEmail, "
			+ "AccountCreationDate, AccountLogin, AccountPassword, AccountRole, Phone, Photo, Country_id) "
			+ "VALUES (?, ?, ?, ?, NOW(), ?, ?, 'User', ?, ?, ?);";
	
	private static final String UPDATE_ACCOUNT = "UPDATE Account SET AccountFirstName=?, AccountLastName=?, "
			+ "AccountBirthday=?, AccountEmail=?, AccountCreationDate=?, AccountLogin=?, AccountPassword=?, "
			+ "AccountRole=?, AccountActive=?, Country_id=?, Phone=?, Photo=?  "
			+ " WHERE idAccount=?;";
	
	private static final String DELETE_ACCOUNT = "DELETE FROM Account WHERE idAccount=?;";

	private static final String ACCOUNTS = "SELECT idAccount,AccountFirstName, AccountLastName, AccountBirthday, AccountEmail,"
			+ "AccountCreationDate, AccountLogin, AccountPassword, AccountRole, AccountActive, Country_id, Phone, Photo"
			+ "  FROM Account LIMIT ? ";

	@Override
	public List<Account> getUsersByCountry(String country) throws AccountDaoException {
		List<Account> userList = null;
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
			throw new AccountDaoException("Can't get connection from ConnectionPool", e);
		} catch (SQLException e) {
			throw new AccountDaoException("Can't perform query", e);
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
	public List<Account> getBannedUsers() throws AccountDaoException {
		List<Account> userList = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(BANNED_ACCOUNT);
			rs = ps.executeQuery();
			userList = getAccounts(rs);
		} catch (ConnectionPoolException e) {
			throw new AccountDaoException("Can't get connection from ConnectionPool", e);
		} catch (SQLException e) {
			throw new AccountDaoException("Can't perform query", e);
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
	public List<Account> getActiveUsersByComment(int value) throws AccountDaoException {
		List<Account> userList = null;
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
			throw new AccountDaoException("Can't get connection from ConnectionPool", e);
		} catch (SQLException e) {
			throw new AccountDaoException("Can't perform query", e);
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
			user.setId(rs.getInt(DBColumnNames.ACCOUNT_ID));
			user.setFirstName(rs.getString(DBColumnNames.ACCOUNT_FIRST_NAME));
			user.setLastName(rs.getString(DBColumnNames.ACCOUNT_LAST_NAME));
			user.setBirthDay(rs.getDate(DBColumnNames.ACCOUNT_BIRTH_DAY));
			user.setEmail(rs.getString(DBColumnNames.ACCOUNT_EMAIL));
			user.setCreationDate(rs.getDate(DBColumnNames.ACCOUNT_CREATION_DATE));
			user.setLogin(rs.getString(DBColumnNames.ACCOUNT_LOGIN));
			user.setPassword(rs.getString(DBColumnNames.ACCOUNT_PASSWORD));
			user.setRole(rs.getString(DBColumnNames.ACCOUNT_ROLE));
			user.setActive(rs.getBoolean(DBColumnNames.ACCOUNT_IS_ACTIVE));
			user.setPhone(rs.getString(DBColumnNames.ACCOUNT_PHONE));
			user.setPhoto(rs.getString(DBColumnNames.ACCOUNT_PHOTO));
			user.setCountryId(rs.getInt(DBColumnNames.ACCOUNT_COUNTRY_ID));
			return user;
		}

		return null;
	}

	private List<Account> getAccounts(ResultSet rs) throws SQLException {
		List<Account> userList = new ArrayList<Account>();
		while (rs.next()) {
			Account user = new Account();
			user.setId(rs.getInt(DBColumnNames.ACCOUNT_ID));
			user.setFirstName(rs.getString(DBColumnNames.ACCOUNT_FIRST_NAME));
			user.setLastName(rs.getString(DBColumnNames.ACCOUNT_LAST_NAME));
			user.setBirthDay(rs.getDate(DBColumnNames.ACCOUNT_BIRTH_DAY));
			user.setEmail(rs.getString(DBColumnNames.ACCOUNT_EMAIL));
			user.setCreationDate(rs.getDate(DBColumnNames.ACCOUNT_CREATION_DATE));
			user.setLogin(rs.getString(DBColumnNames.ACCOUNT_LOGIN));
			user.setPassword(rs.getString(DBColumnNames.ACCOUNT_PASSWORD));
			user.setRole(rs.getString(DBColumnNames.ACCOUNT_ROLE));
			user.setActive(rs.getBoolean(DBColumnNames.ACCOUNT_IS_ACTIVE));
			user.setPhone(rs.getString(DBColumnNames.ACCOUNT_PHONE));
			user.setPhoto(rs.getString(DBColumnNames.ACCOUNT_PHOTO));
			user.setCountryId(rs.getInt(DBColumnNames.ACCOUNT_COUNTRY_ID));
			userList.add(user);
		}
		return userList;
	}

	@Override
	public Account authorization(String login, String password) throws AccountDaoException {
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
			throw new AccountDaoException("Can't get connection from ConnectionPool", e);
		} catch (SQLException e) {
			throw new AccountDaoException("Can't perform query", e);
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
	public void addAccount(Account account) throws AccountDaoException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(ADD_ACCOUNT);
			ps.setString(1, account.getFirstName());
			ps.setString(2, account.getLastName());
			ps.setDate(3, account.getBirthDay());
			ps.setString(4, account.getEmail());
			ps.setString(5, account.getLogin());
			ps.setString(6, account.getPassword());
			ps.setString(7, account.getPhone());
			ps.setString(8, account.getPhoto());
			if(account.getCountryId()!=null){
				ps.setInt(9,account.getCountryId());
			}else{
				ps.setNull(9, Types.INTEGER);
			}
			ps.executeUpdate();
		} catch (ConnectionPoolException e) {
			throw new AccountDaoException("Can't get connection from ConnectionPool", e);
		} catch (SQLException e) {
			throw new AccountDaoException("Can't perform query", e);
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
	public void updateAccount(Account account) throws AccountDaoException {
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
			ps.setBoolean(9, account.isActive());
			if(account.getCountryId()!=null){
			ps.setInt(10, account.getCountryId());
			}else{
				ps.setNull(10, Types.INTEGER);
			}
			ps.setString(11, account.getPhone());
			ps.setString(12, account.getPhoto());
			ps.setInt(13, account.getId());
			ps.executeUpdate();
		} catch (ConnectionPoolException e) {
			throw new AccountDaoException("Can't get connection from ConnectionPool", e);
		} catch (SQLException e) {
			throw new AccountDaoException("Can't perform query", e);
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
	public Account getAccountByLogin(String login) throws AccountDaoException {
		Account account = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(GET_ACCOUNT_BY_LOGIN);
			ps.setString(1, login);
			System.out.println("before performing query");
			rs = ps.executeQuery();
			System.out.println("after performing query");
			account = getAccount(rs);
		} catch (ConnectionPoolException e) {
			throw new AccountDaoException("Can't get connection from ConnectionPool", e);
		} catch (SQLException e) {
			throw new AccountDaoException("Can't perform query", e);
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
	public void deleteAccountById(int id) throws AccountDaoException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(DELETE_ACCOUNT);
			ps.setInt(1,id);
			ps.executeUpdate();
		} catch (ConnectionPoolException e) {
			throw new AccountDaoException("Can't get connection from ConnectionPool", e);
		} catch (SQLException e) {
			throw new AccountDaoException("Can't perform query", e);
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
	public List<Account> getAccountList(int value) throws AccountDaoException {
		List<Account> accountList = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(ACCOUNTS);
			ps.setInt(1, value);
			rs = ps.executeQuery();
			accountList = getAccounts(rs);
		} catch (ConnectionPoolException e) {
			throw new AccountDaoException("Can't get connection from ConnectionPool", e);
		} catch (SQLException e) {
			throw new AccountDaoException("Can't perform query", e);
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
		return accountList;
	}

}
