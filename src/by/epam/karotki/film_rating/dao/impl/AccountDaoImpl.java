package by.epam.karotki.film_rating.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import by.epam.karotki.film_rating.dao.AccountDao;
import by.epam.karotki.film_rating.dao.Criteria;
import by.epam.karotki.film_rating.dao.DBColumnName;
import by.epam.karotki.film_rating.dao.connection_pool.ConnectionPool;
import by.epam.karotki.film_rating.dao.connection_pool.exception.ConnectionPoolException;
import by.epam.karotki.film_rating.dao.exception.AccountDaoException;
import by.epam.karotki.film_rating.entity.Account;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

public class AccountDaoImpl implements AccountDao {
	//private static final Logger LOG = LogManager.getRootLogger();
	private ConnectionPool conPool = ConnectionPool.getInstance();
	private static final String ERROR_MESSAGE_QUERY = "Can't perform query";
	private static final String ERROR_MESSAGE_CP = "Can't get connection from ConnectionPool";

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
	
	private static final String UPDATE_ACCOUNT="UPDATE Account SET AccountFirstName=?, AccountLastName=?, "
			+ "AccountBirthday=?, AccountEmail=?, AccountCreationDate=?, AccountLogin=?, AccountPassword=?, "
			+ "AccountRole=?,AccountActive=?,   Country_id=?, Phone=?, Photo=?  "
			+ " WHERE idAccount=?;";
	
	
	private static final String DELETE_ACCOUNT = "DELETE FROM Account WHERE idAccount = ?";

	private static final String ACCOUNTS = "SELECT idAccount,AccountFirstName, AccountLastName, AccountBirthday, AccountEmail,"
			+ "AccountCreationDate, AccountLogin, AccountPassword, AccountRole, AccountActive, Country_id, Phone, Photo"
			+ "  FROM Account ";
	
	private static final String ACCOUNT = "SELECT idAccount,AccountFirstName, AccountLastName, AccountBirthday, AccountEmail,"
			+ "AccountCreationDate, AccountLogin, AccountPassword, AccountRole, AccountActive, Country_id, Phone, Photo"
			+ "  FROM Account ";

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
			throw new AccountDaoException(ERROR_MESSAGE_CP, e);
		} catch (SQLException e) {
			throw new AccountDaoException(ERROR_MESSAGE_QUERY, e);
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				//LOG.error("Can't close ResultSet");
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
			throw new AccountDaoException(ERROR_MESSAGE_CP, e);
		} catch (SQLException e) {
			throw new AccountDaoException(ERROR_MESSAGE_QUERY, e);
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
			throw new AccountDaoException(ERROR_MESSAGE_CP, e);
		} catch (SQLException e) {
			throw new AccountDaoException(ERROR_MESSAGE_QUERY, e);
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
			user.setId(rs.getInt(DBColumnName.ACCOUNT_ID));
			user.setFirstName(rs.getString(DBColumnName.ACCOUNT_FIRST_NAME));
			user.setLastName(rs.getString(DBColumnName.ACCOUNT_LAST_NAME));
			user.setBirthDay(rs.getDate(DBColumnName.ACCOUNT_BIRTH_DAY));
			user.setEmail(rs.getString(DBColumnName.ACCOUNT_EMAIL));
			user.setCreationDate(rs.getDate(DBColumnName.ACCOUNT_CREATION_DATE));
			user.setLogin(rs.getString(DBColumnName.ACCOUNT_LOGIN));
			user.setPassword(rs.getString(DBColumnName.ACCOUNT_PASSWORD));
			user.setRole(rs.getString(DBColumnName.ACCOUNT_ROLE));
			user.setActive(rs.getBoolean(DBColumnName.ACCOUNT_IS_ACTIVE));
			user.setPhone(rs.getString(DBColumnName.ACCOUNT_PHONE));
			user.setPhoto(rs.getString(DBColumnName.ACCOUNT_PHOTO));
			user.setCountryId(rs.getInt(DBColumnName.ACCOUNT_COUNTRY_ID));
			return user;
		}

		return null;
	}

	private List<Account> getAccounts(ResultSet rs) throws SQLException {
		List<Account> userList = new ArrayList<Account>();
		while (rs.next()) {
			Account user = new Account();
			user.setId(rs.getInt(DBColumnName.ACCOUNT_ID));
			user.setFirstName(rs.getString(DBColumnName.ACCOUNT_FIRST_NAME));
			user.setLastName(rs.getString(DBColumnName.ACCOUNT_LAST_NAME));
			user.setBirthDay(rs.getDate(DBColumnName.ACCOUNT_BIRTH_DAY));
			user.setEmail(rs.getString(DBColumnName.ACCOUNT_EMAIL));
			user.setCreationDate(rs.getDate(DBColumnName.ACCOUNT_CREATION_DATE));
			user.setLogin(rs.getString(DBColumnName.ACCOUNT_LOGIN));
			user.setPassword(rs.getString(DBColumnName.ACCOUNT_PASSWORD));
			user.setRole(rs.getString(DBColumnName.ACCOUNT_ROLE));
			user.setActive(rs.getBoolean(DBColumnName.ACCOUNT_IS_ACTIVE));
			user.setPhone(rs.getString(DBColumnName.ACCOUNT_PHONE));
			user.setPhoto(rs.getString(DBColumnName.ACCOUNT_PHOTO));
			user.setCountryId(rs.getInt(DBColumnName.ACCOUNT_COUNTRY_ID));
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
			throw new AccountDaoException(ERROR_MESSAGE_CP, e);
		} catch (SQLException e) {
			throw new AccountDaoException(ERROR_MESSAGE_QUERY, e);
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
			throw new AccountDaoException(ERROR_MESSAGE_CP, e);
		} catch (SQLException e) {
			throw new AccountDaoException(ERROR_MESSAGE_QUERY, e);
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
			if(account.getFirstName()!=null){
			ps.setString(1, account.getFirstName());
			}else{
				ps.setNull(1, Types.VARCHAR);
			}
			if(account.getLastName()!=null){
			ps.setString(2, account.getLastName());
			}else{
				ps.setNull(2, Types.VARCHAR);
			}

			if(account.getBirthDay()!=null){
			ps.setDate(3, account.getBirthDay());
			}else{
				ps.setNull(3, Types.DATE);
			}
			if(account.getEmail()!=null){
			ps.setString(4, account.getEmail());
			}else{
				ps.setNull(4, Types.VARCHAR);
			}
			
			ps.setDate(5, account.getCreationDate());
			ps.setString(6, account.getLogin());
			ps.setString(7, account.getPassword());
			ps.setString(8, account.getRole());
			ps.setBoolean(9,account.isActive());
			if(account.getCountryId()!=0){
			ps.setInt(10, account.getCountryId());
			}else{
				ps.setNull(10, Types.INTEGER);
			}
			if(account.getPhone()!=null){
			ps.setString(11, account.getPhone());
			}else{
				ps.setNull(11, Types.VARCHAR);
			}
			if(account.getPhoto()!=null){
			ps.setString(12, account.getPhoto());
			}else{
				ps.setNull(12, Types.VARCHAR);
			}   
			ps.setInt(13, account.getId());
			
			ps.executeUpdate();
			
		} catch (ConnectionPoolException e) {
			throw new AccountDaoException(ERROR_MESSAGE_CP, e);
		} catch (SQLException e) {
			throw new AccountDaoException(ERROR_MESSAGE_QUERY, e);
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
			rs = ps.executeQuery();
			account = getAccount(rs);
		} catch (ConnectionPoolException e) {
			throw new AccountDaoException(ERROR_MESSAGE_CP, e);
		} catch (SQLException e) {
			throw new AccountDaoException(ERROR_MESSAGE_QUERY, e);
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
			throw new AccountDaoException(ERROR_MESSAGE_CP, e);
		} catch (SQLException e) {
			throw new AccountDaoException(ERROR_MESSAGE_QUERY, e);
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
	public List<Account> getAccountList() throws AccountDaoException {
		List<Account> accountList = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(ACCOUNTS);
			//ps.setInt(1, value);
			rs = ps.executeQuery();
			accountList = getAccounts(rs);
		} catch (ConnectionPoolException e) {
			throw new AccountDaoException(ERROR_MESSAGE_CP, e);
		} catch (SQLException e) {
			throw new AccountDaoException(ERROR_MESSAGE_QUERY, e);
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

	@Override
	public List<Account> getAccountByCriteria(Criteria criteria) throws AccountDaoException {
		List<Account> accountList = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(ACCOUNT + criteria.getClause());
			rs = ps.executeQuery();
			accountList = getAccounts(rs);
		} catch (ConnectionPoolException e) {
			throw new AccountDaoException(ERROR_MESSAGE_CP, e);
		} catch (SQLException e) {
			throw new AccountDaoException(ERROR_MESSAGE_QUERY, e);
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
