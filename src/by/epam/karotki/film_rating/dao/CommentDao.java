package by.epam.karotki.film_rating.dao;

import java.util.List;

import by.epam.karotki.film_rating.dao.exception.CommentDaoException;
import by.epam.karotki.film_rating.entity.Comment;

public interface CommentDao {
	
    List<Comment> getCommentsByCriteria(Criteria criteria) throws CommentDaoException;
	
	void addComment(Comment comment) throws CommentDaoException;
	
	void deleteComment(int idAccount,int idFilm) throws CommentDaoException;
	
	void updateComment(Comment comment) throws CommentDaoException;
}
