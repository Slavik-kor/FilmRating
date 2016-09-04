package by.epam.karotki.film_rating.dao.exception;

public class CommentDaoException extends DaoException {

	private static final long serialVersionUID = 1L;

	public CommentDaoException() {
	}

	public CommentDaoException(String message) {
		super(message);
	}

	public CommentDaoException(String message, Exception e) {
		super(message, e);
	}
}
