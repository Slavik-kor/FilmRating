package test.by.epam.karotki.film_rating.dao.impl;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import by.epam.karotki.film_rating.dao.DBColumnNames;
import by.epam.karotki.film_rating.dao.DaoFactory;
import by.epam.karotki.film_rating.dao.GenreDao;
import by.epam.karotki.film_rating.dao.Operator;
import by.epam.karotki.film_rating.dao.connection_pool.ConnectionPool;
import by.epam.karotki.film_rating.dao.connection_pool.exception.ConnectionPoolException;
import by.epam.karotki.film_rating.dao.exception.DaoException;
import by.epam.karotki.film_rating.dao.impl.CriteriaImpl;
import by.epam.karotki.film_rating.entity.Genre;

public class GenreDaoTest {
	private static DaoFactory factory;
	private static GenreDao aDao;
	private static ConnectionPool cp;
	private final static String RU_NAME = "Название жанра";
	private final static String NEW_RU_NAME = "Новое название жанра";
	private final static String EN_NAME = "Genre name";
	private final static String NEW_EN_NAME = "New genre name";
	private final static String RU_DESC = "Описание  жанра";
	private final static String NEW_RU_DESC = "Новое описание жанра";
	private final static String EN_DESC = "Genre description";
	private final static String NEW_EN_DESC = "New genre description";
	private final static String EN = "en";
	private final static String RU = "ru";

	@BeforeClass
	public static void setUpBeforeClass() {
		cp = ConnectionPool.getInstance();
		try {
			cp.initPoolData();
		} catch (ConnectionPoolException e) {
			fail("ConnectioPoolException");
		}
		factory = DaoFactory.getInstance();
		aDao = factory.getGenreDao();
		Genre ru_genre = new Genre();
		ru_genre.setName(RU_NAME);
		ru_genre.setDescription(RU_DESC);

		Genre en_genre = new Genre();
		en_genre.setName(EN_NAME);
		en_genre.setDescription(EN_DESC);

		try {
			aDao.addGenre(ru_genre);
			CriteriaImpl cr = new CriteriaImpl();
			cr.addCriterion(Operator.EQUAL, DBColumnNames.GENRE_NAME, RU_NAME);
			cr.addCriterion(Operator.EQUAL, DBColumnNames.GENRE_DESCRIPTION, RU_DESC);
			ru_genre = aDao.getGenreByCriteria(cr, RU).get(0);
			en_genre.setId(ru_genre.getId());
			aDao.addGenre(en_genre, EN);
			aDao.addGenre(ru_genre, RU);
		} catch (DaoException e) {
			fail("DaoException");
		}
	}

	@AfterClass
	public static void tearDownAfterClass() {
		Genre ru_genre = null;
		Genre en_genre = null;
		Genre genre = null;
		try {
			CriteriaImpl ru_cr = new CriteriaImpl();
			ru_cr.addCriterion(Operator.EQUAL, DBColumnNames.GENRE_NAME, RU_NAME);
			ru_genre = aDao.getGenreByCriteria(ru_cr, RU).get(0);
			aDao.deleteGenreById(ru_genre.getId(), RU);

			CriteriaImpl en_cr = new CriteriaImpl();
			en_cr.addCriterion(Operator.EQUAL, DBColumnNames.GENRE_NAME, NEW_EN_NAME);
			en_genre = aDao.getGenreByCriteria(en_cr, EN).get(0);
			aDao.deleteGenreById(en_genre.getId(), EN);

			CriteriaImpl cr = new CriteriaImpl();
			cr.addCriterion(Operator.EQUAL, DBColumnNames.GENRE_NAME, NEW_RU_NAME);
			genre = aDao.getGenreByCriteria(cr, RU).get(0);
			aDao.deleteGenreById(genre.getId());

		} catch (Exception e) {
			fail("DaoException");
		}
		cp.dispose();
	}

	@Test
	public void UpdateTest() {
		Genre genre = null;
		try {
			CriteriaImpl cr = new CriteriaImpl();
			cr.addCriterion(Operator.EQUAL, DBColumnNames.GENRE_NAME, RU_NAME);
			genre = aDao.getGenreByCriteria(cr, RU).get(0);
			
			assertNotNull(genre);
			
			genre.setName(NEW_RU_NAME);
			genre.setDescription(NEW_RU_DESC);
			
			aDao.updateGenre(genre);
			CriteriaImpl newCr = new CriteriaImpl();
			newCr.addCriterion(Operator.EQUAL, DBColumnNames.GENRE_NAME, NEW_RU_NAME);
			genre = aDao.getGenreByCriteria(newCr, RU).get(0);
			
			assertEquals(genre.getName(), NEW_RU_NAME);
			assertEquals(genre.getDescription(), NEW_RU_DESC);
		} catch (DaoException e) {
			fail("DaoException");
		}

		try {
			CriteriaImpl cr = new CriteriaImpl();
			cr.addCriterion(Operator.EQUAL, DBColumnNames.GENRE_NAME, EN_NAME);
			genre = aDao.getGenreByCriteria(cr, EN).get(0);
			
			assertNotNull(genre);
			
			genre.setName(NEW_EN_NAME);
			genre.setDescription(NEW_EN_DESC);
			
			aDao.updateGenre(genre,EN);
			CriteriaImpl newCr = new CriteriaImpl();
			newCr.addCriterion(Operator.EQUAL, DBColumnNames.GENRE_NAME, NEW_EN_NAME);
			genre = aDao.getGenreByCriteria(newCr, EN).get(0);
			
			assertEquals(genre.getName(), NEW_EN_NAME);
			assertEquals(genre.getDescription(), NEW_EN_DESC);
		} catch (DaoException e) {
			fail("DaoException");
		}

	}

}
