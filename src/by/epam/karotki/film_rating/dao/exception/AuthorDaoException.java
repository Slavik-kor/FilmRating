package by.epam.karotki.film_rating.dao.exception;

public class AuthorDaoException extends DaoException {

	private static final long serialVersionUID = 1L;

	public AuthorDaoException() {
	}

	public AuthorDaoException(String message) {
		super(message);
	}

	public AuthorDaoException(String message, Exception e) {
		super(message, e);
	}

}
