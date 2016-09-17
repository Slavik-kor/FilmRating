package by.epam.karotki.film_rating.service.impl;

import java.io.InputStream;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import by.epam.karotki.film_rating.dao.AuthorDao;
import by.epam.karotki.film_rating.dao.Criteria;
import by.epam.karotki.film_rating.dao.DBColumnName;
import by.epam.karotki.film_rating.dao.DaoFactory;
import by.epam.karotki.film_rating.dao.FilmAuthorDao;
import by.epam.karotki.film_rating.dao.Operator;
import by.epam.karotki.film_rating.dao.exception.DaoException;
import by.epam.karotki.film_rating.entity.Author;
import by.epam.karotki.film_rating.service.AuthorService;
import by.epam.karotki.film_rating.service.exception.AuthorServiceException;
import by.epam.karotki.film_rating.service.util.ServiceUtil;

public class AuthorServiceImpl implements AuthorService {
	private static final String DIRECTOR = "Director";
	private static final String SCENARIO_WRITER = "ScenarioWriter";
	private static final String ACTOR = "Actor";
	private static final String ERROR_MESSAGE_DIR = "can't get directors by film id";
	private static final String ERROR_MESSAGE_SC = "can't get scenariowriters by film id";
	private static final String ERROR_MESSAGE_ACT = "can't get actors by film id";
	private static final String ERROR_MESSAGE_AUT = "can't get author by id";
	private static final String F_NAME = "first-name";
	private static final String L_NAME = "last-name";
	private static final String COUNTRY = "country";
	private static final String BIRTHDAY = "birthday";
	private static final String PROJECT_PATH = "ProjectPath";
	private static final String PATH_PHOTO = "images\\author\\author";
	private static final String JPG = ".jpg";
	@Override
	public List<Author> getDirectorsByFilm(int idFilm, String lang) throws AuthorServiceException {
		List<Author> authorList = null;
		DaoFactory dao = DaoFactory.getInstance();
		AuthorDao aDao = dao.getAuthorDao();
		FilmAuthorDao fADao = dao.getFilmAuthorDao();
		try {
			List<Integer> authorIdList = fADao.getAuthorsByFilm(idFilm, DIRECTOR);
			if (authorIdList.size() == 0) {
				return null;
			}
			String[] authorArray = ServiceUtil.intListToStringArray(authorIdList);
			Criteria criteria = dao.createCriteria();
			criteria.addCriterion(Operator.IN, DBColumnName.AUTHOR_ID, authorArray);
			authorList = aDao.getAuthorByCriteria(criteria, lang);
		} catch (DaoException e) {
			// log
			throw new AuthorServiceException(ERROR_MESSAGE_DIR, e);
		}
		return authorList;
	}

	@Override
	public List<Author> getScenarioWritersByFilm(int idFilm, String lang) throws AuthorServiceException {
		List<Author> authorList = null;
		DaoFactory dao = DaoFactory.getInstance();
		AuthorDao aDao = dao.getAuthorDao();
		FilmAuthorDao fADao = dao.getFilmAuthorDao();
		try {
			List<Integer> authorIdList = fADao.getAuthorsByFilm(idFilm, SCENARIO_WRITER);
			if (authorIdList.size() == 0) {
				return null;
			}
			String[] authorArray = ServiceUtil.intListToStringArray(authorIdList);
			Criteria criteria = dao.createCriteria();
			criteria.addCriterion(Operator.IN, DBColumnName.AUTHOR_ID, authorArray);
			authorList = aDao.getAuthorByCriteria(criteria, lang);
		} catch (DaoException e) {
			// log
			throw new AuthorServiceException(ERROR_MESSAGE_SC, e);
		}
		return authorList;
	}

	@Override
	public List<Author> getActorByFilm(int idFilm, String lang) throws AuthorServiceException {
		List<Author> authorList = null;
		DaoFactory dao = DaoFactory.getInstance();
		AuthorDao aDao = dao.getAuthorDao();
		FilmAuthorDao fADao = dao.getFilmAuthorDao();
		try {
			List<Integer> authorIdList = fADao.getAuthorsByFilm(idFilm, ACTOR);
			if (authorIdList.size() == 0) {
				return null;
			}
			String[] authorArray = ServiceUtil.intListToStringArray(authorIdList);
			Criteria criteria = dao.createCriteria();
			criteria.addCriterion(Operator.IN, DBColumnName.AUTHOR_ID, authorArray);
			authorList = aDao.getAuthorByCriteria(criteria, lang);
		} catch (DaoException e) {
			// log
			throw new AuthorServiceException(ERROR_MESSAGE_ACT, e);
		}
		return authorList;
	}

	@Override
	public Author getAuthorById(int idAuthor, String lang) throws AuthorServiceException {
		List<Author> author = null;
		DaoFactory dao = DaoFactory.getInstance();
		AuthorDao aDao = dao.getAuthorDao();
		try {
			Criteria criteria = dao.createCriteria();
			criteria.addCriterion(Operator.EQUAL, DBColumnName.AUTHOR_ID, String.valueOf(idAuthor));
			author = aDao.getAuthorByCriteria(criteria, lang);
		} catch (DaoException e) {
			// log
			throw new AuthorServiceException(ERROR_MESSAGE_AUT, e);
		}
		return author.get(0);
	}

	@Override
	public void addDirectorToFilm(int idFilm, int idAuthor) throws AuthorServiceException {
		DaoFactory dao = DaoFactory.getInstance();
		FilmAuthorDao fADao = dao.getFilmAuthorDao();
		try{
			fADao.addAuthorToFilm(idFilm, idAuthor, DIRECTOR);
		}catch(DaoException e){
			throw new AuthorServiceException("can't add director to film", e);
		}
	}

	@Override
	public void addScenarioToFilm(int idFilm, int idAuthor) throws AuthorServiceException {
		DaoFactory dao = DaoFactory.getInstance();
		FilmAuthorDao fADao = dao.getFilmAuthorDao();
		try{
			fADao.addAuthorToFilm(idFilm, idAuthor,SCENARIO_WRITER);
		}catch(DaoException e){
			throw new AuthorServiceException("can't add director to film", e);
		}
		
	}

	@Override
	public void addActorToFilm(int idFilm, int idAuthor) throws AuthorServiceException {
		DaoFactory dao = DaoFactory.getInstance();
		FilmAuthorDao fADao = dao.getFilmAuthorDao();
		try{
			fADao.addAuthorToFilm(idFilm, idAuthor, ACTOR);
		}catch(DaoException e){
			throw new AuthorServiceException("can't add director to film", e);
		}
		
	}

	@Override
	public List<Author> getAllAuthors(String lang) throws AuthorServiceException {
		List<Author> authorList = null;
		DaoFactory dao = DaoFactory.getInstance();
		AuthorDao aDao = dao.getAuthorDao();
		try {
			Criteria criteria = dao.createCriteria();
			authorList = aDao.getAuthorByCriteria(criteria, lang);
		} catch (DaoException e) {
			// log
			throw new AuthorServiceException("can't get all authors", e);
		}
		return authorList;
	}

	@Override
	public Author addAuthor(Map<String, String> reqParam, InputStream is) throws AuthorServiceException {
		Author author = createAuthor(reqParam,is);
		DaoFactory dao = DaoFactory.getInstance();
		AuthorDao aDao = dao.getAuthorDao();
		try {
			aDao.addAuthor(author);
		} catch (DaoException e) {
			// log
			throw new AuthorServiceException("can't add author", e);
		}
		Author newAuthor = null;
		try{
			newAuthor = aDao.getAuthorById(author.getId());
		}catch(DaoException e){
			throw new AuthorServiceException("can't get added auhtor",e);
		}
		return newAuthor;
	}


	@Override
	public void addAuthor(Author author, String lang) throws AuthorServiceException {
		DaoFactory dao = DaoFactory.getInstance();
		AuthorDao aDao = dao.getAuthorDao();
		try{
			aDao.addAuthor(author, lang);
		}catch(DaoException e){
			throw new AuthorServiceException("can't get added auhtor",e);
		}
	}
	
	private Author createAuthor(Map<String, String> reqParam, InputStream is) {
		Author author = new Author();
		author.setFirstName(reqParam.get(F_NAME));
		author.setLastName(reqParam.get(L_NAME));
		
		Date birthday = null;
		try{
			birthday = Date.valueOf(reqParam.get(BIRTHDAY));
		}catch(IllegalArgumentException e){
			birthday = null;
		}
		author.setBirthDay(birthday);
		
		Integer countryId = null;
		try {
			countryId = Integer.valueOf(reqParam.get(COUNTRY));
		} catch (IllegalArgumentException | NullPointerException e) {
			countryId = null;
		}
		author.setCountryOfBirthId(countryId);

		String rootPath = reqParam.get(PROJECT_PATH);
		String photoPath = PATH_PHOTO + author.getLastName()+author.getId() + JPG;
		String fullPhotoPath = rootPath + "\\" + photoPath;
		ServiceUtil.saveFromRequestFile(is, fullPhotoPath);
		author.setPhoto(photoPath);
		
		return author;
	}
	

}
