package by.epam.karotki.film_rating.dao.exception;

public class AccountDaoException extends DaoException {

	private static final long serialVersionUID = 1L;

	public AccountDaoException() {
	}

	public AccountDaoException(String message) {
		super(message);
	}

	public AccountDaoException(String message, Exception e) {
		super(message, e);
	}

}
