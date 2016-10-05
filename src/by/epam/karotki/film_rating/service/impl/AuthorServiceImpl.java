package by.epam.karotki.film_rating.service.impl;

import java.io.File;
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
	private static final String COUNTRY = "countries";
	private static final String BIRTHDAY = "birthday";
	private static final String PROJECT_PATH = "ProjectPath";
	private static final String PATH_PHOTO = "images\\author\\author";
	private static final String JPG = ".jpg";
	private static int AUTHOR_FILE = 20;

	@Override
	public List<Author> getDirectorsByFilm(int idFilm, String lang) throws AuthorServiceException {
		validate(idFilm,lang);
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
		validate(idFilm,lang);
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
		validate(idFilm,lang);
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
		validate(idAuthor,lang);
		List<Author> authorList = null;
		Author author = null;
		DaoFactory dao = DaoFactory.getInstance();
		AuthorDao aDao = dao.getAuthorDao();
		try {
			Criteria criteria = dao.createCriteria();
			criteria.addCriterion(Operator.EQUAL, DBColumnName.AUTHOR_ID, String.valueOf(idAuthor));
			authorList = aDao.getAuthorByCriteria(criteria, lang);
			if ((authorList != null) && (authorList.size() > 0)) {
				author = authorList.get(0);
			} else {
				author = null;
			}
		} catch (DaoException e) {
			// log
			throw new AuthorServiceException(ERROR_MESSAGE_AUT, e);
		}
		return author;
	}

	@Override
	public void addDirectorToFilm(int idFilm, List<Integer> idAuthor) throws AuthorServiceException {
		validate(idFilm,idAuthor);
		DaoFactory dao = DaoFactory.getInstance();
		FilmAuthorDao fADao = dao.getFilmAuthorDao();
		try {
			List<Integer> directorsDB = fADao.getAuthorsByFilm(idFilm, DIRECTOR);
			for (int i = 0; i < idAuthor.size(); i++) {
				Integer dir = idAuthor.get(i);
				if (!directorsDB.contains(dir)) {
					fADao.addAuthorToFilm(idFilm, dir, DIRECTOR);
				}
			}
			for (int i = 0; i < directorsDB.size(); i++) {
				if (!idAuthor.contains(directorsDB.get(i))) {
					fADao.deleteAuthorFromFilm(idFilm, directorsDB.get(i),DIRECTOR);
				}
			}
		} catch (DaoException e) {
			throw new AuthorServiceException("can't add director to film", e);
		}
	}

	@Override
	public void addScenarioToFilm(int idFilm, List<Integer> idAuthor) throws AuthorServiceException {
		validate(idFilm,idAuthor);
		DaoFactory dao = DaoFactory.getInstance();
		FilmAuthorDao fADao = dao.getFilmAuthorDao();
		try {
			List<Integer> scenDB = fADao.getAuthorsByFilm(idFilm, SCENARIO_WRITER);
			for (int i = 0; i < idAuthor.size(); i++) {
				Integer scenarios = idAuthor.get(i);
				if (!scenDB.contains(scenarios)) {
					fADao.addAuthorToFilm(idFilm, scenarios, SCENARIO_WRITER);
				}
			}
			for (int i = 0; i < scenDB.size(); i++) {
				if (!idAuthor.contains(scenDB.get(i))) {
					fADao.deleteAuthorFromFilm(idFilm, scenDB.get(i),SCENARIO_WRITER);
				}
			}
		} catch (DaoException e) {
			throw new AuthorServiceException("can't add scenario writer to film", e);
		}
	}

	@Override
	public void addActorToFilm(int idFilm, List<Integer> idAuthor) throws AuthorServiceException {
		validate(idFilm,idAuthor);
		DaoFactory dao = DaoFactory.getInstance();
		FilmAuthorDao fADao = dao.getFilmAuthorDao();
		try {
			List<Integer> actorDB = fADao.getAuthorsByFilm(idFilm, ACTOR);
			for (int i = 0; i < idAuthor.size(); i++) {
				Integer actor = idAuthor.get(i);
				if (!actorDB.contains(actor)) {
					fADao.addAuthorToFilm(idFilm, actor, ACTOR);
				}
			}
			for (int i = 0; i < actorDB.size(); i++) {
				if (!idAuthor.contains(actorDB.get(i))) {
					fADao.deleteAuthorFromFilm(idFilm, actorDB.get(i),ACTOR);
				}
			}
		} catch (DaoException e) {
			throw new AuthorServiceException("can't add scenario writer to film", e);
		}
	}

	@Override
	public List<Author> getAllAuthors(String lang) throws AuthorServiceException {
		if(lang == null){
			throw new AuthorServiceException("incorrect initial data");
		}
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
		if(reqParam == null){
			throw new AuthorServiceException("incorrect initial data");
		}
		Author author = createAuthor(reqParam, is);
		DaoFactory dao = DaoFactory.getInstance();
		AuthorDao aDao = dao.getAuthorDao();
		try {
			aDao.addAuthor(author);
		} catch (DaoException e) {
			// log
			throw new AuthorServiceException("can't add author", e);
		}
		Author newAuthor = null;
		try {
			Criteria criteria = dao.createCriteria();
			criteria.addCriterion(Operator.EQUAL, DBColumnName.AUTHOR_FIRST_NAME, author.getFirstName());
			criteria.addCriterion(Operator.EQUAL, DBColumnName.AUTHOR_LAST_NAME, author.getLastName());
			newAuthor = aDao.getAuthorByCriteria(criteria, "ru").get(0);
		} catch (DaoException e) {
			throw new AuthorServiceException("can't get added auhtor", e);
		}
		return newAuthor;
	}

	@Override
	public void addAuthor(Author author, String lang) throws AuthorServiceException {
		if((author == null)||(lang == null)){
			throw new AuthorServiceException("incorrect initial data");
		}
		DaoFactory dao = DaoFactory.getInstance();
		AuthorDao aDao = dao.getAuthorDao();
		try {
			aDao.addAuthor(author, lang);
		} catch (DaoException e) {
			throw new AuthorServiceException("can't get added auhtor", e);
		}
	}


	@Override
	public void deleteAuthor(int idAuthor, String path) throws AuthorServiceException {
		validate(idAuthor,path);
		DaoFactory dao = DaoFactory.getInstance();
		AuthorDao aDao = dao.getAuthorDao();
		Author author = null;
		try {
			author = aDao.getAuthorById(idAuthor);
		} catch (DaoException e) {
			throw new AuthorServiceException("can't find author by id", e);
		}
		String photoPath = author.getPhoto();
		try {
			aDao.deleteAuthorById(idAuthor);
		} catch (DaoException e) {
			throw new AuthorServiceException("can't delete author", e);
		}

		File file = new File(path + "\\" + photoPath);
		file.delete();

	}

	@Override
	public void addFilmsToDirector(String[] idFilm, int idAuthor) throws AuthorServiceException {
		validate(idFilm,idAuthor);
		DaoFactory dao = DaoFactory.getInstance();
		FilmAuthorDao fADao = dao.getFilmAuthorDao();
		try {
			for (int i = 0; i < idFilm.length; i++) {
				fADao.addAuthorToFilm(Integer.valueOf(idFilm[i]), idAuthor, DIRECTOR);
			}
		} catch (DaoException e) {
			throw new AuthorServiceException("can't add films to director", e);
		}

	}

	@Override
	public void addFilmsToScenario(String[] idFilm, int idAuthor) throws AuthorServiceException {
		validate(idFilm,idAuthor);
		DaoFactory dao = DaoFactory.getInstance();
		FilmAuthorDao fADao = dao.getFilmAuthorDao();
		try {
			for (int i = 0; i < idFilm.length; i++) {
				fADao.addAuthorToFilm(Integer.valueOf(idFilm[i]), idAuthor, SCENARIO_WRITER);
			}
		} catch (DaoException e) {
			throw new AuthorServiceException("can't add films to scenario writer", e);
		}

	}

	@Override
	public void addFilmsToActor(String[] idFilm, int idAuthor) throws AuthorServiceException {
		validate(idFilm,idAuthor);
		DaoFactory dao = DaoFactory.getInstance();
		FilmAuthorDao fADao = dao.getFilmAuthorDao();
		try {
			for (int i = 0; i < idFilm.length; i++) {
				fADao.addAuthorToFilm(Integer.valueOf(idFilm[i]), idAuthor, ACTOR);
			}
		} catch (DaoException e) {
			throw new AuthorServiceException("can't add films to actor", e);
		}

	}
	
	private Author createAuthor(Map<String, String> reqParam, InputStream is) {
		Author author = new Author();
		author.setFirstName(reqParam.get(F_NAME));
		author.setLastName(reqParam.get(L_NAME));

		Date birthday = null;
		try {
			birthday = Date.valueOf(reqParam.get(BIRTHDAY));
		} catch (IllegalArgumentException e) {
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
		String photoPath = PATH_PHOTO + AUTHOR_FILE++ + JPG;
		String fullPhotoPath = rootPath + "\\" + photoPath;
		ServiceUtil.saveFromRequestFile(is, fullPhotoPath);
		author.setPhoto(photoPath);

		return author;
	}
	
	private void validate(int id,String lang) throws AuthorServiceException{
		if ((id==0)||(lang==null)){
			throw new AuthorServiceException("incorrect ititial data");
		}
	}

	private void validate(String[] lang,int id) throws AuthorServiceException{
		if ((id==0)||(lang==null)){
			throw new AuthorServiceException("incorrect ititial data");
		}
	}
	
	private void validate(int id,List<Integer> list) throws AuthorServiceException{
		if ((id==0)||(list==null)){
			throw new AuthorServiceException("incorrect ititial data");
		}
	}
}
