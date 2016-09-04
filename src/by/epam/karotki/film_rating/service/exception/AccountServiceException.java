package by.epam.karotki.film_rating.service.exception;

public class AccountServiceException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public AccountServiceException(){
		super();
	}
	
	public AccountServiceException(String message){
		super(message);
	}
	
	public AccountServiceException(String message,Exception e){
		super(message,e);
	}
	
}
