package test.by.epam.karotki.film_rating.dao.impl;

import static org.junit.Assert.*;

import java.sql.Date;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import by.epam.karotki.film_rating.dao.AuthorDao;
import by.epam.karotki.film_rating.dao.DaoFactory;
import by.epam.karotki.film_rating.dao.connection_pool.ConnectionPool;
import by.epam.karotki.film_rating.dao.connection_pool.exception.ConnectionPoolException;
import by.epam.karotki.film_rating.dao.exception.DaoException;
import by.epam.karotki.film_rating.entity.Author;

public class AuthorDaoTest {
	private static DaoFactory factory;
	private static AuthorDao aDao;
	private static ConnectionPool cp;
	private final static String FIRST_NAME = "firstNameTest";
	private final static String LAST_NAME = "lastNameTest";
	private final static String BIRTHDAY = "1986-02-16";
	private final static int COUNTRY_OF_BIRTH = 1;
	private final static String PHOTO = "/WEB-INF/Test";
	private final static String NEW_PHOTO = "NEW/WEB-INF/Test";

	@BeforeClass
	public static void setUpBeforeClass(){
		cp = ConnectionPool.getInstance();
		try {
			cp.initPoolData();
		} catch (ConnectionPoolException e) {
			fail("ConnectioPoolException");
		}
		factory = DaoFactory.getInstance();
		aDao = factory.getAuthorDao();
		Author author = new Author();
		author.setFirstName(FIRST_NAME);
		author.setLastName(LAST_NAME);
		author.setBirthDay(Date.valueOf(BIRTHDAY));
		author.setPhoto(PHOTO);
		author.setCountryOfBirthId(COUNTRY_OF_BIRTH);
		
		try{
			aDao.addAuthor(author);
		}catch(DaoException e){
			fail("DaoException");
		}
	}

	@AfterClass
	public static void tearDownAfterClass(){
		Author author = null;
		try{
			author = aDao.getAuthorByName(FIRST_NAME, LAST_NAME);
			int id = author.getId();
			aDao.deleteAuthorById(id);
			author = aDao.getAuthorById(id);
		}catch(DaoException e){
			fail("DaoException");
		}
		assertNull(author);
		cp.dispose();
	}

	
	@Test
	public void getAuthorTest(){
		Author author = null;
		try{
			author = aDao.getAuthorByName(FIRST_NAME, LAST_NAME);
		}catch(DaoException e){
			fail("DaoException");
		}
		assertNotNull(author);
		assertEquals(author.getFirstName(),FIRST_NAME);
		assertEquals(author.getLastName(),LAST_NAME);
		assertEquals(author.getBirthDay(),Date.valueOf(BIRTHDAY));
		assertEquals(author.getPhoto(),PHOTO);
	}
	
	@Test
	public void updateAuthorTest(){
		Author author = null;
		try{
			author = aDao.getAuthorByName(FIRST_NAME, LAST_NAME);
			author.setPhoto(NEW_PHOTO);
			aDao.updateAuthor(author);
			author = aDao.getAuthorByName(FIRST_NAME, LAST_NAME);
		}catch(DaoException e){
			fail("DaoException");
		}
		assertNotNull(author);
		assertEquals(author.getPhoto(),NEW_PHOTO);
	}
}
