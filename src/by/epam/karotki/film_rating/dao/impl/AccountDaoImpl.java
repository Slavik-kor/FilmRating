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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class AccountDaoImpl implements AccountDao {
	
	private static final Logger LOG = LogManager.getRootLogger();
	
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
			+ "AccountBirthday=?, AccountEmail=?, AccountLogin=?, AccountPassword=?, "
			+ "AccountRole=?,AccountActive=?,   Country_id=?, Phone=?, Photo=?  "
			+ " WHERE idAccount=?;";
	
	private static final String DELETE_ACCOUNT = "DELETE FROM Account WHERE idAccount = ?";

	private static final String DELETE_COMMENT_LIST = "DELETE FROM Comment WHERE Account_id = ?";
	
	private static final String ACCOUNTS = "SELECT idAccount,AccountFirstName, AccountLastName, AccountBirthday, AccountEmail,"
			+ "AccountCreationDate, AccountLogin, AccountPassword, AccountRole, AccountActive, Country_id, Phone, Photo"
			+ "  FROM Account ";
	
	private static final String ACCOUNT = "SELECT idAccount,AccountFirstName, AccountLastName, AccountBirthday, AccountEmail,"
			+ "AccountCreationDate, AccountLogin, AccountPassword, AccountRole, AccountActive, Country_id, Phone, Photo"
			+ "  FROM Account ";
	

	@Override
	public List<Account> getUsersByCountry(String country) throws AccountDaoException {
		LOG.debug("Start performing method List<Account> getUsersByCountry( String country) with country = "+country);
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
				LOG.error("Can't close ResultSet");
			}
			try {
				ps.close();
			} catch (SQLException e) {
				 LOG.error("Can't close PreparedStatement");
			}
			conPool.returnConnection(con);
		}
		LOG.debug("Finish performing method List<Account> getUsersByCountry( String country) with return "+userList);
		return userList;
	}

	@Override
	public List<Account> getBannedUsers() throws AccountDaoException {
		LOG.debug("Start performing method List<Account> getBannedUsers()");
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
				 LOG.error("Can't close ResultSet");
			}
			try {
				ps.close();
			} catch (SQLException e) {
				 LOG.error("Can't close PreparedStatement");
			}
			conPool.returnConnection(con);
		}
		LOG.debug("Finish performing method List<Account> getBannedUsers() with return " + userList);
		return userList;
	}

	
	@Override
	public List<Account> getActiveUsersByComment(int value) throws AccountDaoException {
		LOG.debug("Start performing method List<Account> getActiveUsersByComment(int value) with value = "+value);
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
				 LOG.error("Can't close ResultSet");
			}
			try {
				ps.close();
			} catch (SQLException e) {
				 LOG.error("Can't close PreparedStatement");
			}
			conPool.returnConnection(con);
		}
		LOG.debug("Finish performing method List<Account> getActiveUsersByComment(int value) with return " + userList);
		return userList;
	}

	

	@Override
	public Account authorization(String login, String password) throws AccountDaoException {
		LOG.debug("Start performing method authorization(String login, String password) with login = " + login);
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
				 LOG.error("Can't close ResultSet");
			}
			try {
				ps.close();
			} catch (SQLException e) {
				 LOG.error("Can't close PreparedStatement");
			}
			conPool.returnConnection(con);
		}
		LOG.debug("Finish performing method Account authorization(String login, String password) with return "+account);
		return account;
	}

	@Override
	public void addAccount(Account account) throws AccountDaoException {
		LOG.debug("Start performing method void addAccount(Account account) with account = " + account);
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
				 LOG.error("Can't close PreparedStatement");
			}
			conPool.returnConnection(con);
		}
		LOG.debug("Finish performing method void addAccount(Account account)");
	}

	@Override
	public void updateAccount(Account account) throws AccountDaoException {
		LOG.debug("Start performing method void updateAccount(Account account) with account = " + account);
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
			
			ps.setString(5, account.getLogin());
			ps.setString(6, account.getPassword());
			ps.setString(7, account.getRole());
			ps.setBoolean(8,account.isActive());
			
			if(account.getCountryId()!=null){
			ps.setInt(9, account.getCountryId());
			}else{
				ps.setNull(9, Types.INTEGER);
			}
			
			if(account.getPhone()!=null){
			ps.setString(10, account.getPhone());
			}else{
				ps.setNull(10, Types.VARCHAR);
			}
			

			if(account.getPhoto()!=null){
			ps.setString(11, account.getPhoto());
			}else{
				ps.setNull(11, Types.VARCHAR);
			}   
			
			ps.setInt(12, account.getId());
			
			ps.executeUpdate();
			
		} catch (ConnectionPoolException e) {
			throw new AccountDaoException(ERROR_MESSAGE_CP, e);
		} catch (SQLException e) {
			throw new AccountDaoException(ERROR_MESSAGE_QUERY, e);
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				 LOG.error("Can't close PreparedStatement");
			}
			conPool.returnConnection(con);
		}
		LOG.debug("Finish performing method void updateAccount(Account account) ");
	}

	@Override
	public Account getAccountByLogin(String login) throws AccountDaoException {
		LOG.debug("Start performing method getAccountByLogin(String login) with login = " + login);
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
				 LOG.error("Can't close ResultSet");
			}
			try {
				ps.close();
			} catch (SQLException e) {
				 LOG.error("Can't close PreparedStatement");
			}
			conPool.returnConnection(con);
		}
		LOG.debug("Finish performing method Account getAccountByLogin(String login) with return "+account);
		return account;
	
	}

	@Override
	public void deleteAccountById(int id) throws AccountDaoException {
		LOG.debug("Start performing method deleteAccountById(int id) with id = " + id);
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps_comment = null;
		try {
			con = conPool.takeConnection();
			con.setAutoCommit(false);
			
			ps_comment = con.prepareStatement(DELETE_COMMENT_LIST);
			ps_comment.setInt(1, id);
			ps_comment.executeUpdate();
			
			ps = con.prepareStatement(DELETE_ACCOUNT);
			ps.setInt(1,id);
			ps.executeUpdate();
			
			con.commit();
		} catch (ConnectionPoolException e) {
			throw new AccountDaoException(ERROR_MESSAGE_CP, e);
		} catch (SQLException e) {
			try{
			con.rollback();
			}catch(SQLException ex){
				LOG.error("Can't rollback during deleting account");
			}
			throw new AccountDaoException(ERROR_MESSAGE_QUERY, e);
		} finally {
			try{
				con.setAutoCommit(true);
			}catch(SQLException e){
				LOG.error("Can't set autoCommit to connection");
			}
			try {
				ps.close();
			} catch (SQLException e) {
				LOG.error("Can't close PreparedStatement");
			}
			conPool.returnConnection(con);
		}
		LOG.debug("Finish performing method void deleteAccountById(int id) ");
	}

	@Override
	public List<Account> getAccountList() throws AccountDaoException {
		LOG.debug("Start performing method getAccountList()" );
		List<Account> accountList = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(ACCOUNTS);
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
				 LOG.error("Can't close ResultSet");
			}
			try {
				ps.close();
			} catch (SQLException e) {
				 LOG.error("Can't close PreparedStatement");
			}
			conPool.returnConnection(con);
		}
		LOG.debug("Finish performing method getAccountList() with return " + accountList);
		return accountList;
	}

	@Override
	public List<Account> getAccountByCriteria(Criteria criteria) throws AccountDaoException {
		LOG.debug("Start performing method getAccountByCriteria(Criteria criteria) with criteria = "+criteria.getClause());
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
				 LOG.error("Can't close ResultSet");
			}
		
			try {
				ps.close();
			} catch (SQLException e) {
				 LOG.error("Can't close PreparedStatement");
			}
			conPool.returnConnection(con);
		}
		LOG.debug("Finish performing method getAccountByCriteria(Criteria criteria) with return "+accountList);
		return accountList;
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
	
}
