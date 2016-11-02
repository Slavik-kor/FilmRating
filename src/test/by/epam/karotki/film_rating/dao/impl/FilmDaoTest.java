package test.by.epam.karotki.film_rating.dao.impl;

import static org.junit.Assert.*;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import by.epam.karotki.film_rating.dao.Criteria;
import by.epam.karotki.film_rating.dao.DBColumnName;
import by.epam.karotki.film_rating.dao.DaoFactory;
import by.epam.karotki.film_rating.dao.FilmDao;
import by.epam.karotki.film_rating.dao.Operator;
import by.epam.karotki.film_rating.dao.connection_pool.ConnectionPool;
import by.epam.karotki.film_rating.dao.connection_pool.exception.ConnectionPoolException;
import by.epam.karotki.film_rating.dao.exception.DaoException;
import by.epam.karotki.film_rating.dao.impl.CriteriaImpl;
import by.epam.karotki.film_rating.entity.Film;

public class FilmDaoTest {
	private static DaoFactory factory;
	private static FilmDao aDao;
	private static ConnectionPool cp;
	private final static String TITLE = "Title Test";
	private final static String ANOTHER_DESCRIPTION = "Another description Test";
	private final static String DESCRIPTION = "Film description";
	private final static double BUDGET = 1000000;
	private final static double BOXOFFICE_CASH = 2000000;
	private final static int AUDIENCE = 300000;
	private final static String PREMIER_DATE = "2000-01-01";
	private final static String TIME = "02:20:00";
	private final static String WEB_SITE = "Web site";
	private final static String POSTER = "Poster path";
	private final static String TEASER = "Teaser path";
	private final static String EN = "en";
	private final static String TITLE_TEST = "Test";
	private final static String WRONG_TITLE_TEST = "Test111";


	@BeforeClass
	public static void setUpBeforeClass(){
		cp = ConnectionPool.getInstance();
		try {
			cp.initPoolData();
		} catch (ConnectionPoolException e) {
			fail("ConnectioPoolException");
		}
		factory = DaoFactory.getInstance();
		aDao = factory.getFilmDao();
	}

	@AfterClass
	public static void tearDownAfterClass(){
		Film film = null;
		try{
			film = aDao.getFilmByTitle(TITLE);
			int id = film.getId();
			aDao.deleteFilmById(id);
			film = aDao.getFilmByTitle(TITLE);
		}catch(DaoException e){
			fail("ConnectioPoolException");
		}
		assertNull(film);
		cp.dispose();
	}

	@Test
	public void addFilmTest() {
		Film film = new Film();
		film.setTitle(TITLE);
		film.setDescription(DESCRIPTION);
		film.setBudget(BUDGET);
		film.setBoxOfficeCash(BOXOFFICE_CASH);
		film.setAudience(AUDIENCE);
		film.setPremierDate(Date.valueOf(PREMIER_DATE));
		film.setDuration(Time.valueOf(TIME));
		film.setWebSite(WEB_SITE);
		film.setPoster(POSTER);
		film.setTeaser(TEASER);
		
		try{
			aDao.addFilm(film);
		}catch(DaoException e){
			fail("DaoException");
		}
	}
	
	@Test
	public void getFilmByTitleTest(){
		Film film = null;
		try{
			film = aDao.getFilmByTitle(TITLE);
		}catch(DaoException e){
			fail("DaoException");
		}
		assertNotNull(film);
		assertEquals(film.getPremierDate(),Date.valueOf(PREMIER_DATE));
		assertEquals(film.getDuration(),Time.valueOf(TIME));

	}
	
	@Test
	public void findFilmTest(){
		List<Film> filmList = null;
		List<Film> wrongFilmList = null;
		try{
			Criteria criteria = new CriteriaImpl();
			criteria.addCriterion(Operator.LIKE, DBColumnName.FILM_TITLE, TITLE_TEST);
			filmList = aDao.getFilmListByCriteria(criteria, EN);
			criteria = new CriteriaImpl();
			criteria.addCriterion(Operator.LIKE, DBColumnName.FILM_TITLE, WRONG_TITLE_TEST);
			wrongFilmList = aDao.getFilmListByCriteria(criteria, EN);
		}catch(DaoException e){
			fail("DaoException");
		}
		assertNotNull(filmList);
		assertEquals(filmList.get(0).getTitle(),TITLE);
		
		assertNotNull(wrongFilmList);
		assertEquals(wrongFilmList.size(),0);
	}
	
	@Test
	public void updateFilmTest(){
		Film film = null;	
		try{
			film = aDao.getFilmByTitle(TITLE);
			film.setDescription(ANOTHER_DESCRIPTION);
			int id = film.getId();
			aDao.updateFilm(film);
			film = aDao.getFilmById(id);
		}catch(DaoException e){
			fail("DaoException");
		}
		assertEquals(film.getDescription(),ANOTHER_DESCRIPTION);
	}

}
