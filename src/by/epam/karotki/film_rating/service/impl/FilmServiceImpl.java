package by.epam.karotki.film_rating.service.impl;

import java.io.InputStream;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Map;

import by.epam.karotki.film_rating.dao.CommentDao;
import by.epam.karotki.film_rating.dao.Criteria;
import by.epam.karotki.film_rating.dao.DBColumnName;
import by.epam.karotki.film_rating.dao.DaoFactory;
import by.epam.karotki.film_rating.dao.FilmAuthorDao;
import by.epam.karotki.film_rating.dao.FilmDao;
import by.epam.karotki.film_rating.dao.FilmGenreDao;
import by.epam.karotki.film_rating.dao.Operator;
import by.epam.karotki.film_rating.dao.exception.DaoException;
import by.epam.karotki.film_rating.entity.Comment;
import by.epam.karotki.film_rating.entity.Film;
import by.epam.karotki.film_rating.service.FilmService;
import by.epam.karotki.film_rating.service.exception.FilmServiceException;
import by.epam.karotki.film_rating.service.util.ServiceUtil;

public class FilmServiceImpl implements FilmService {
	private static final String ERROR_MESSAGE = "can't get films";
	private static final String ERROR_MESSAGE_VALIDATE = "id field equal zero";
	private static final String TITLE = "title";
	private static final String DESCRIPTION = "description";
	private static final String SITE = "site";
	private static final String BUDGET = "budget";
	private static final String BOX_OFFICE = "boxOffice";
	private static final String AUDIENCE = "audience";
	private static final String DURATION = "duration";
	private static final String RELEASE = "release";
	private static final String TEASER = "teaser";
	private static final String PROJECT_PATH = "ProjectPath";
	private static final String JPG = ".jpg";
	private static final String PATH_POSTER = "images\\poster\\film";

	@Override
	public List<Film> getFilmsByNewest(String lang) throws FilmServiceException {

		List<Film> films = null;
		DaoFactory factory = DaoFactory.getInstance();
		FilmDao fDao = factory.getFilmDao();
		try {
			Criteria criteria = factory.createCriteria();
			criteria.addOrderColumn(DBColumnName.FILM_PREMIER_DATE, false);
			films = fDao.getFilmListByCriteria(criteria, lang);
		} catch (DaoException e) {
			// log
			throw new FilmServiceException(ERROR_MESSAGE, e);
		}
		return films;
	}

	@Override
	public Film getFilmById(int id, String lang) throws FilmServiceException {

		DaoFactory factory = DaoFactory.getInstance();
		FilmDao fDao = factory.getFilmDao();
		Film film = null;
		try {
			Criteria criteria = factory.createCriteria();
			criteria.addCriterion(Operator.EQUAL, DBColumnName.FILM_ID, String.valueOf(id));
			List<Film> filmList = fDao.getFilmListByCriteria(criteria, lang);
			film = filmList.get(0);
		} catch (DaoException e) {
			// log
			throw new FilmServiceException(ERROR_MESSAGE, e);
		}
		return film;
	}

	@Override
	public List<Film> getFilmsByGenre(int idGenre, String lang) throws FilmServiceException {
		if (idGenre == 0) {
			throw new FilmServiceException(ERROR_MESSAGE_VALIDATE);
		}
		List<Film> films = null;
		DaoFactory factory = DaoFactory.getInstance();
		FilmDao fDao = factory.getFilmDao();
		FilmGenreDao fGDao = factory.getFilmGenreDao();
		try {
			Criteria criteria = factory.createCriteria();
			criteria.addCriterion(Operator.EQUAL, DBColumnName.GENRE_ID, String.valueOf(idGenre));

			List<Integer> filmIds = fGDao.getFilmsByGenre(idGenre);
			if (filmIds.size() == 0) {
				return null;
			}

			String[] filmsArray = ServiceUtil.intListToStringArray(filmIds);
			Criteria fCriteria = factory.createCriteria();
			fCriteria.addCriterion(Operator.IN, DBColumnName.FILM_ID, filmsArray);
			fCriteria.addOrderColumn(DBColumnName.FILM_PREMIER_DATE, false);
			films = fDao.getFilmListByCriteria(fCriteria, lang);
		} catch (DaoException e) {
			// log
			throw new FilmServiceException(ERROR_MESSAGE, e);
		}
		return films;
	}

	@Override
	public List<Film> getFilmsByDirector(int idAuthor, String lang) throws FilmServiceException {
		if (idAuthor == 0) {
			throw new FilmServiceException(ERROR_MESSAGE_VALIDATE);
		}
		List<Film> films = null;
		DaoFactory factory = DaoFactory.getInstance();
		FilmDao fDao = factory.getFilmDao();
		FilmAuthorDao fADao = factory.getFilmAuthorDao();
		try {
			Criteria criteria = factory.createCriteria();
			criteria.addCriterion(Operator.EQUAL, DBColumnName.AUTHOR_ID, String.valueOf(idAuthor));
			List<Integer> filmIds = fADao.getFilmsByAuthor(idAuthor, DBColumnName.AUTHOR_ROLE_DIRECTOR);
			if (filmIds.size() == 0) {
				return null;
			}

			String[] filmsArray = ServiceUtil.intListToStringArray(filmIds);
			Criteria fCriteria = factory.createCriteria();
			fCriteria.addCriterion(Operator.IN, DBColumnName.FILM_ID, filmsArray);
			fCriteria.addOrderColumn(DBColumnName.FILM_PREMIER_DATE, false);
			films = fDao.getFilmListByCriteria(fCriteria, lang);
		} catch (DaoException e) {
			// log
			throw new FilmServiceException(ERROR_MESSAGE, e);
		}
		return films;
	}

	@Override
	public List<Film> getFilmsByScenarioWriter(int idAuthor, String lang) throws FilmServiceException {
		if (idAuthor == 0) {
			throw new FilmServiceException(ERROR_MESSAGE_VALIDATE);
		}
		List<Film> films = null;
		DaoFactory factory = DaoFactory.getInstance();
		FilmDao fDao = factory.getFilmDao();
		FilmAuthorDao fADao = factory.getFilmAuthorDao();
		try {
			Criteria criteria = factory.createCriteria();
			criteria.addCriterion(Operator.EQUAL, DBColumnName.AUTHOR_ID, String.valueOf(idAuthor));
			List<Integer> filmIds = fADao.getFilmsByAuthor(idAuthor, DBColumnName.AUTHOR_ROLE_SCENARIOWRITER);
			if (filmIds.size() == 0) {
				return null;
			}

			String[] filmsArray = ServiceUtil.intListToStringArray(filmIds);
			Criteria fCriteria = factory.createCriteria();
			fCriteria.addCriterion(Operator.IN, DBColumnName.FILM_ID, filmsArray);
			fCriteria.addOrderColumn(DBColumnName.FILM_PREMIER_DATE, false);
			films = fDao.getFilmListByCriteria(fCriteria, lang);
		} catch (DaoException e) {
			// log
			throw new FilmServiceException(ERROR_MESSAGE, e);
		}
		return films;
	}

	@Override
	public List<Film> getFilmsByActor(int idAuthor, String lang) throws FilmServiceException {
		if (idAuthor == 0) {
			throw new FilmServiceException(ERROR_MESSAGE_VALIDATE);
		}
		List<Film> films = null;
		DaoFactory factory = DaoFactory.getInstance();
		FilmDao fDao = factory.getFilmDao();
		FilmAuthorDao fADao = factory.getFilmAuthorDao();
		try {
			Criteria criteria = factory.createCriteria();
			criteria.addCriterion(Operator.EQUAL, DBColumnName.AUTHOR_ID, String.valueOf(idAuthor));
			List<Integer> filmIds = fADao.getFilmsByAuthor(idAuthor, DBColumnName.AUTHOR_ROLE_ACTOR);
			if (filmIds.size() == 0) {
				return null;
			}

			String[] filmsArray = ServiceUtil.intListToStringArray(filmIds);
			Criteria fCriteria = factory.createCriteria();
			fCriteria.addCriterion(Operator.IN, DBColumnName.FILM_ID, filmsArray);
			fCriteria.addOrderColumn(DBColumnName.FILM_PREMIER_DATE, false);
			films = fDao.getFilmListByCriteria(fCriteria, lang);
		} catch (DaoException e) {
			// log
			throw new FilmServiceException(ERROR_MESSAGE, e);
		}
		return films;
	}

	@Override
	public List<Film> getFilmsByYear(int year, String lang) throws FilmServiceException {
		List<Film> films = null;
		DaoFactory factory = DaoFactory.getInstance();
		FilmDao fDao = factory.getFilmDao();
		Criteria criteria = factory.createCriteria();
		criteria.addCriterion(Operator.BETWEEN, DBColumnName.FILM_PREMIER_DATE, "" + year + "-01-01",
				"" + year + "-12-31");
		try {
			films = fDao.getFilmListByCriteria(criteria, lang);
		} catch (DaoException e) {
			throw new FilmServiceException(ERROR_MESSAGE, e);
		}

		return films;
	}

	@Override
	public List<Film> getFilmsByRating(String lang) throws FilmServiceException {
		List<Film> films = null;
		DaoFactory factory = DaoFactory.getInstance();
		FilmDao fDao = factory.getFilmDao();
		try {
			films = fDao.getTopFilmsByRating(lang);
		} catch (DaoException e) {
			throw new FilmServiceException(ERROR_MESSAGE, e);
		}

		return films;
	}

	@Override
	public List<Film> getFilmsByComments(int idAccount, String lang) throws FilmServiceException {
		List<Film> films = null;
		DaoFactory factory = DaoFactory.getInstance();
		FilmDao fDao = factory.getFilmDao();
		CommentDao cDao = factory.getCommentDao();
		try {
			Criteria criteria = factory.createCriteria();
			criteria.addCriterion(Operator.EQUAL, DBColumnName.COMMENT_ACCOUNT_ID, String.valueOf(idAccount));

			List<Comment> commentList = cDao.getCommentsByCriteria(criteria);
			if (commentList == null) {
				return null;
			}
			String[] idFilmsArray = new String[commentList.size()];
			for (int i = 0; i < commentList.size(); i++) {
				idFilmsArray[i] = String.valueOf(commentList.get(i).getFilmId());
			}
			Criteria fCriteria = factory.createCriteria();
			fCriteria.addCriterion(Operator.IN, DBColumnName.FILM_ID, idFilmsArray);
			films = fDao.getFilmListByCriteria(fCriteria, lang);
		} catch (DaoException e) {
			throw new FilmServiceException(ERROR_MESSAGE, e);
		}

		return films;
	}

	@Override
	public List<Film> getFilmsByAccountRate(int idAccount, String lang) throws FilmServiceException {
		List<Film> films = null;
		DaoFactory factory = DaoFactory.getInstance();
		FilmDao fDao = factory.getFilmDao();
		CommentDao cDao = factory.getCommentDao();
		try {
			Criteria criteria = factory.createCriteria();
			criteria.addCriterion(Operator.EQUAL, DBColumnName.COMMENT_ACCOUNT_ID, String.valueOf(idAccount));
			criteria.addOrderColumn(DBColumnName.COMMENT_RATE, false);
			List<Comment> commentList = cDao.getCommentsByCriteria(criteria);
			if (commentList == null) {
				return null;
			}
			String[] idFilmsArray = new String[commentList.size()];
			for (int i = 0; i < commentList.size(); i++) {
				idFilmsArray[i] = String.valueOf(commentList.get(i).getFilmId());
			}
			Criteria fCriteria = factory.createCriteria();
			fCriteria.addCriterion(Operator.IN, DBColumnName.FILM_ID, idFilmsArray);
			films = fDao.getFilmListByCriteria(fCriteria, lang);
		} catch (DaoException e) {
			throw new FilmServiceException(ERROR_MESSAGE, e);
		}
		return films;
	}

	@Override
	public Film addFilm(Map<String, String> reqParam, InputStream is) throws FilmServiceException {
		validateParam(reqParam);
		Film film = createFilm(reqParam, is);
		DaoFactory dao = DaoFactory.getInstance();
		FilmDao fDao = dao.getFilmDao();
		try {
			fDao.addFilm(film);
		} catch (DaoException e) {
			// log
			throw new FilmServiceException(ERROR_MESSAGE, e);
		}
		Film newFilm = null;
		try {
			newFilm = fDao.getFilmByTitle(film.getTitle());
		} catch (DaoException e) {
			throw new FilmServiceException(ERROR_MESSAGE, e);
		}

		return newFilm;
	}

	private Film createFilm(Map<String, String> reqParam, InputStream is) {
		Film film = new Film();
		film.setTitle(reqParam.get(TITLE));
		film.setDescription(reqParam.get(DESCRIPTION));
		int audience;
		try {
			audience = Integer.valueOf(reqParam.get(AUDIENCE));
		} catch (NumberFormatException e) {
			audience = 0;
		}
		film.setAudience(audience);

		int budget;
		try {
			budget = Integer.valueOf(reqParam.get(BUDGET));
		} catch (NumberFormatException e) {
			budget = 0;
		}
		film.setBudget(budget);

		double boxOffice;
		try {
			boxOffice = Double.valueOf(BOX_OFFICE);
		} catch (NumberFormatException e) {
			boxOffice = 0;
		}
		film.setBoxOfficeCash(boxOffice);
		film.setWebSite(reqParam.get(SITE));
		film.setTeaser(reqParam.get(TEASER));

		Time duration = null;
		try {
			duration = Time.valueOf(reqParam.get(DURATION));
		} catch (IllegalArgumentException e) {
			duration = null;
		}
		film.setDuration(duration);

		Date premierDate = null;
		try {
			premierDate = Date.valueOf(reqParam.get(RELEASE));
		} catch (IllegalArgumentException e) {
			premierDate = null;
		}
		film.setPremierDate(premierDate);

		if (is != null) {
			String rootPath = reqParam.get(PROJECT_PATH);
			String photoPath = PATH_POSTER + film.getTitle() + JPG;
			String fullPhotoPath = rootPath + "\\" + photoPath;
			ServiceUtil.saveFromRequestFile(is, fullPhotoPath);
			film.setPoster(photoPath);
		}
		
		return film;
	}

	private void validateParam(Map<String, String> reqParam) throws FilmServiceException {

	}

	@Override
	public void addFilm(Film film, String lang) throws FilmServiceException {
		DaoFactory dao = DaoFactory.getInstance();
		FilmDao fDao = dao.getFilmDao();
		try {
			fDao.addFilm(film, lang);
		} catch (DaoException e) {
			// log
			throw new FilmServiceException(ERROR_MESSAGE, e);
		}

	}

	@Override
	public List<Film> getAllFilms(String lang) throws FilmServiceException {
		List<Film> filmList = null;
		DaoFactory dao = DaoFactory.getInstance();
		FilmDao fDao = dao.getFilmDao();
		try {
			Criteria criteria = dao.createCriteria();
			filmList = fDao.getFilmListByCriteria(criteria, lang);
		} catch (DaoException e) {
			throw new FilmServiceException(ERROR_MESSAGE, e);
		}
		return filmList;
	}

	@Override
	public Film updateFilm(Map<String, String> updParam, InputStream is) throws FilmServiceException {
		// TODO Auto-generated method stub
		return null;
	}

}
