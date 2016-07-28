package by.epam.karotki.film_rating.service.exception;

public class InitServiceException extends ServiceException{
	
	private static final long serialVersionUID = 1L;

	public InitServiceException(){
		super();
	}

	public InitServiceException(String message){
		super(message);
	}

	public InitServiceException(String message,Exception e){
		super(message,e);
	}

}
