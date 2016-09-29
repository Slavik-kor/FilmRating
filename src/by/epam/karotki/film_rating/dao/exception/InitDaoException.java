package by.epam.karotki.film_rating.dao.exception;

public class InitDaoException extends DaoException {
	
	private static final long serialVersionUID = 1L;

	public InitDaoException(){
		super();
	}
	
	public InitDaoException(String message){
		super(message);
	}
	
	public InitDaoException(String message,Exception e){
		super(message,e);
	}

}
