package by.epam.karotki.film_rating.dao.exception;

public class CountryDaoException extends DaoException {

	private static final long serialVersionUID = 1L;

	public CountryDaoException() {
	}

	public CountryDaoException(String message) {
		super(message);
	}

	public CountryDaoException(String message, Exception e) {
		super(message, e);
	}
	
}
