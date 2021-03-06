package by.epam.karotki.film_rating.service.impl;

import java.io.File;
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
	private static final String ERROR_MESSAGE_VALIDATE = "incorrect initial data";
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
	private static final String TITLE_RU = "title_ru";
	private static final String TITLE_EN = "title_en";
	private static final String DESCRIPTION_RU = "description_ru";
	private static final String DESCRIPTION_EN = "description_en";
	private static final String FILM_ID = "idFilm";
	private static final String RU = "ru";
	private static final String EN = "en";

	@Override
	public List<Film> getFilmsByNewest(String lang) throws FilmServiceException {
		validate(lang);
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
		validate(id,lang);
		DaoFactory factory = DaoFactory.getInstance();
		FilmDao fDao = factory.getFilmDao();
		Film film = null;
		try {
			Criteria criteria = factory.createCriteria();
			criteria.addCriterion(Operator.EQUAL, DBColumnName.FILM_ID, String.valueOf(id));
			List<Film> filmList = fDao.getFilmListByCriteria(criteria, lang);
			if ((filmList != null) && (filmList.size() > 0)) {
				film = filmList.get(0);
			}
		} catch (DaoException e) {
			// log
			throw new FilmServiceException(ERROR_MESSAGE, e);
		}
		return film;
	}

	@Override
	public List<Film> getFilmsByGenre(int idGenre, String lang) throws FilmServiceException {
		validate(idGenre,lang);
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
		validate(idAuthor,lang);
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
		validate(idAuthor,lang);
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
		validate(idAuthor,lang);
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
		validate(year,lang);
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
		validate(lang);
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
		validate(idAccount,lang);
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
		validate(idAccount,lang);
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

	

	@Override
	public List<Film> getAllFilms(String lang) throws FilmServiceException {
		validate(lang);
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
		validateParam(updParam);
		int idFilm = Integer.valueOf(updParam.get(FILM_ID));
		DaoFactory dao = DaoFactory.getInstance();
		FilmDao fDao = dao.getFilmDao();
		Film film = null;
		try{
			film = fDao.getFilmById(idFilm);
		}catch (DaoException e) {
			throw new FilmServiceException(ERROR_MESSAGE, e);
		}
		
		if(film == null){
			throw new FilmServiceException(ERROR_MESSAGE);
		}
		
		
			String title = updParam.get(TITLE);
			if(title!=null){
				film.setTitle(title);
			}

		String desc = updParam.get(DESCRIPTION);
		if(desc!=null){
			film.setDescription(desc);
		}
		
		try{
		Double budget = Double.valueOf(updParam.get(BUDGET));
		if(budget!=null){
			film.setBudget(budget);
			}
		}catch(IllegalArgumentException | NullPointerException e){
				//log
			}
		
		
		try{
		Double boxOffice = Double.valueOf(updParam.get(BOX_OFFICE));
		if(boxOffice!=null){
			film.setBoxOfficeCash(boxOffice);
		}
		}catch(IllegalArgumentException | NullPointerException e){
			//log
		}
		
		
		try{
		Integer audience = Integer.valueOf(updParam.get(AUDIENCE));
		if(audience!=null){
			film.setAudience(audience);
		}
		}catch(IllegalArgumentException | NullPointerException e){
			//log
		}
		
		try{
		Date release = Date.valueOf(updParam.get(RELEASE));
		if(release!=null){
			film.setPremierDate(release);
		}
		}catch(IllegalArgumentException | NullPointerException e){
			//log
		}
		
		String site = updParam.get(SITE);
		if(site!=null){
			film.setWebSite(site);
		}
		
		try{
		Time duration = Time.valueOf(updParam.get(DURATION));
		if(duration!=null){
			film.setDuration(duration);
		}
		}catch(IllegalArgumentException | NullPointerException e){
			//log
		}
		
		String teaser = updParam.get(TEASER);
		if(teaser!=null){
			film.setTeaser(teaser);
		}
		
		if (is != null) {
			String rootPath = updParam.get(PROJECT_PATH);
			String photoPath = PATH_POSTER + film.getId() + JPG;
			String fullPhotoPath = rootPath + "\\" + photoPath;
			ServiceUtil.saveFromRequestFile(is, fullPhotoPath);
			film.setPoster(photoPath);
		}
		
		try{
			fDao.updateFilm(film);
		}catch (DaoException e) {
			throw new FilmServiceException(ERROR_MESSAGE, e);
		}
		
		String titleRU = updParam.get(TITLE_RU);
		if(title!=null){
			film.setTitle(titleRU);
		}
		
		String descRU = updParam.get(DESCRIPTION_RU);
		if(desc!=null){
			film.setDescription(descRU);
		}
		
		try{
			fDao.updateFilm(film, RU);
		}catch (DaoException e) {
			throw new FilmServiceException(ERROR_MESSAGE, e);
		}

		String titleEN = updParam.get(TITLE_EN);
		if(title!=null){
			film.setTitle(titleEN);
		}
		
		String descEN = updParam.get(DESCRIPTION_EN);
		if(desc!=null){
			film.setDescription(descEN);
		}
		
		try{
			fDao.updateFilm(film, EN);
		}catch (DaoException e) {
			throw new FilmServiceException(ERROR_MESSAGE, e);
		}
		
		try{
			film = fDao.getFilmById(idFilm);
		}catch (DaoException e) {
			throw new FilmServiceException(ERROR_MESSAGE, e);
		}
			
		return film;
	}

	@Override
	public void deleteFilm(int id, String path) throws FilmServiceException {
		validate(id,path);
		DaoFactory dao = DaoFactory.getInstance();
		FilmDao fDao = dao.getFilmDao();
		Film film = null;
		try {
			film = fDao.getFilmById(id);
		} catch (DaoException e) {
			throw new FilmServiceException("can't find film by id", e);
		}
		String posterPath = film.getPoster();
		try {
			fDao.deleteFilmById(id);
			;
		} catch (DaoException e) {
			throw new FilmServiceException("can't delete film", e);
		}

		File file = new File(path + "\\" + posterPath);
		file.delete();
	}
	
	
	
	@Override
	public void addFilm(Film film, String lang) throws FilmServiceException {
		validate(film,lang);
		DaoFactory dao = DaoFactory.getInstance();
		FilmDao fDao = dao.getFilmDao();
		try {
			fDao.addFilm(film, lang);
		} catch (DaoException e) {
			// log
			throw new FilmServiceException(ERROR_MESSAGE, e);
		}

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

	
	private void validate(int intValue, String stringValue) throws FilmServiceException{
		if((intValue == 0)||(stringValue == null)){
			throw new FilmServiceException(ERROR_MESSAGE_VALIDATE);
		}
	}

	private void validate(Object... stringValue) throws FilmServiceException{
		for (Object i : stringValue){
		if(i == null){
			throw new FilmServiceException(ERROR_MESSAGE_VALIDATE);
		}
		}
	}
	
	private void validateParam(Map<String, String> reqParam) throws FilmServiceException {
		if(reqParam == null){
			throw new FilmServiceException(ERROR_MESSAGE_VALIDATE);
		}
	}

	
}
