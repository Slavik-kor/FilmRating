package by.epam.karotki.film_rating.dao.exception;

public class FilmDaoException extends DaoException {

	private static final long serialVersionUID = 1L;

	public FilmDaoException() {
	}

	public FilmDaoException(String message) {
		super(message);
	}

	public FilmDaoException(String message, Exception e) {
		super(message, e);
	}
	
}
