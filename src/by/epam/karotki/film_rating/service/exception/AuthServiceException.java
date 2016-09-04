package by.epam.karotki.film_rating.service.exception;

public class AuthServiceException extends AccountServiceException {

	private static final long serialVersionUID = 1L;

	public AuthServiceException(){
		super();
	}

	public AuthServiceException(String message){
		super(message);
	}

	public AuthServiceException(String message,Exception e){
		super(message,e);
	}

}
