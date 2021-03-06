package by.epam.karotki.film_rating.service;

import java.util.List;

import by.epam.karotki.film_rating.service.exception.CommentServiceException;
import by.epam.karotki.film_rating.entity.Comment;

public interface CommentService {
	
	List<Comment> getCommentsByAccount(int idAccount) throws CommentServiceException;
	
	List<Comment> getCommentsByFilm(int idFilm) throws CommentServiceException;
	
	void addComment(Comment comment) throws CommentServiceException;
	
	double getAvgRateByFilm(int idFilm) throws CommentServiceException;
	
	void updateComment(Comment comment) throws CommentServiceException;
	
	void deleteComment(int idAccount,int idFilm) throws CommentServiceException;
}
