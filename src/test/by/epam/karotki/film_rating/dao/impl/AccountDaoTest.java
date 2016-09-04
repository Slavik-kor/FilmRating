package test.by.epam.karotki.film_rating.dao.impl;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import by.epam.karotki.film_rating.dao.AccountDao;
import by.epam.karotki.film_rating.dao.DaoFactory;
import by.epam.karotki.film_rating.dao.connection_pool.ConnectionPool;
import by.epam.karotki.film_rating.dao.connection_pool.exception.ConnectionPoolException;
import by.epam.karotki.film_rating.dao.exception.DaoException;
import by.epam.karotki.film_rating.entity.Account;

public class AccountDaoTest {
	private static DaoFactory factory;
	private static AccountDao aDao;
	private static ConnectionPool cp;
	private final static String FIRST_NAME = "firstNameTest";
	private final static String LAST_NAME = "lastNameTest";
	private final static String LOGIN = "loginTest";
	private final static String PASSWORD = "passwordTest";
	private final static String PHONE = "123123Test";
	private final static String PHOTO = "/WEB-INF/Test";
	private final static String ADMIN = "Admin";


	@BeforeClass
	public static void setUpBeforeClass() {
		cp = ConnectionPool.getInstance();
		try {
			cp.initPoolData();
		} catch (ConnectionPoolException e) {
			fail("ConnectioPoolException");
		}
		factory = DaoFactory.getInstance();
		aDao = factory.getAccountDao();
	}

	@AfterClass
	public static void dropDownAfterClass() {
		Account accountTest = null;
		try{
			accountTest = aDao.getAccountByLogin(LOGIN);
		}catch(DaoException e){
			fail("DaoException");
		}
		int id = accountTest.getId();
		assertNotEquals(id,0);
		try{
			aDao.deleteAccountById(id);
		}catch(DaoException e){
			fail("DaoException");
		}
		
		try{
			accountTest = aDao.getAccountByLogin(LOGIN);
		}catch(DaoException e){
			fail("DaoException");
		}
		assertNull(accountTest);
	
		
		
		cp.dispose();
	}

	@Test
	public void authorizarion() {
		Account account = null;
		try {
			account = aDao.authorization("Admin", "Admin");
		} catch (DaoException e) {
			fail("DaoException");
		}
		assertEquals("Вячеслав", account.getFirstName());
		assertEquals("Короткий", account.getLastName());
		assertEquals("Admin", account.getLogin());
		assertEquals("Admin", account.getRole());

	}
	
	@Test
	public void addAccount(){
		Account account = new Account();
		account.setFirstName(FIRST_NAME);
		account.setLastName(LAST_NAME);
		account.setLogin(LOGIN);
		account.setPassword(PASSWORD);
		account.setPhone(PHONE);
		account.setPhoto(PHOTO);
		account.setCountryId(1);
		try{
			aDao.addAccount(account);
		}catch(DaoException e){
			fail("DaoException");
		}
		
	}
	
	@Test
	public void getAccountByLogin(){
		Account accountTest = null;
		try{
			accountTest = aDao.getAccountByLogin(LOGIN);
		}catch(DaoException e){
			fail("DaoException");
		}
		assertNotNull(accountTest);
		assertEquals(accountTest.getFirstName(),FIRST_NAME);
		assertEquals(accountTest.getLastName(),LAST_NAME);
		assertEquals(accountTest.getLogin(),LOGIN);
		assertEquals(accountTest.getPassword(),PASSWORD);
		assertEquals(accountTest.getPhone(),PHONE);
		assertEquals(accountTest.getPhoto(),PHOTO);
	} 
	
	@Test
	public void updateAccount(){
		Account accountTest = null;
		try{
			accountTest = aDao.getAccountByLogin(LOGIN);
			assertNotNull(accountTest);
			accountTest.setActive(false);
			accountTest.setRole(ADMIN);
			aDao.updateAccount(accountTest);
			accountTest = aDao.getAccountByLogin(LOGIN);
		}catch(DaoException e){
			fail("DaoException");
		}
		assertNotNull(accountTest);
		assertEquals(accountTest.isActive(),false);
		assertEquals(accountTest.getRole(),ADMIN);

	}
}
