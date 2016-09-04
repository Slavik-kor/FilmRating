package by.epam.karotki.film_rating.dao.exception;

public class GenreDaoException extends DaoException {

	private static final long serialVersionUID = 1L;

	public GenreDaoException() {
	}

	public GenreDaoException(String message) {
		super(message);
	}

	public GenreDaoException(String message, Exception e) {
		super(message, e);
	}
	
}
