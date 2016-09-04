package by.epam.karotki.film_rating.service.exception;

public class CommentServiceException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public CommentServiceException(){
		super();
	}
	
	public CommentServiceException(String message){
		super(message);
	}
	
	public CommentServiceException(String message,Exception e){
		super(message,e);
	}
	
}
