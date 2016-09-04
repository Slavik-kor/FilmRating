package by.epam.karotki.film_rating.service.exception;

public class FilmServiceException extends ServiceException {
	
	private static final long serialVersionUID = 1L;

	public FilmServiceException(){
		super();
	}
	
	public FilmServiceException(String message){
		super(message);
	}
	
	public FilmServiceException(String message,Exception e){
		super(message,e);
	}

}
