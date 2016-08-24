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
		cp.dispose();
	}

	@Test
	public void test() {
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

}
