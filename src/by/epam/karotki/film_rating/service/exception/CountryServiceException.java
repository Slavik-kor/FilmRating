package by.epam.karotki.film_rating.service.exception;

public class CountryServiceException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public CountryServiceException(){
		super();
	}
	
	public CountryServiceException(String message){
		super(message);
	}
	
	public CountryServiceException(String message,Exception e){
		super(message,e);
	}
	
}
