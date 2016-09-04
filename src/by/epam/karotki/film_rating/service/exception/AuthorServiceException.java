package by.epam.karotki.film_rating.service.exception;

public class AuthorServiceException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public AuthorServiceException(){
		super();
	}
	
	public AuthorServiceException(String message){
		super(message);
	}
	
	public AuthorServiceException(String message,Exception e){
		super(message,e);
	}
	
}
