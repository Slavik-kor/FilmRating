package by.epam.karotki.film_rating.service.exception;

public class GenreServiceException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public GenreServiceException(){
		super();
	}
	
	public GenreServiceException(String message){
		super(message);
	}
	
	public GenreServiceException(String message,Exception e){
		super(message,e);
	}
	
}
