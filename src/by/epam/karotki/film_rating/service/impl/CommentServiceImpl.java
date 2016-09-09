package by.epam.karotki.film_rating.service.impl;

import java.util.List;

import by.epam.karotki.film_rating.dao.CommentDao;
import by.epam.karotki.film_rating.dao.Criteria;
import by.epam.karotki.film_rating.dao.DBColumnNames;
import by.epam.karotki.film_rating.dao.DaoFactory;
import by.epam.karotki.film_rating.dao.Operator;
import by.epam.karotki.film_rating.dao.exception.DaoException;
import by.epam.karotki.film_rating.entity.Comment;
import by.epam.karotki.film_rating.service.CommentService;
import by.epam.karotki.film_rating.service.exception.CommentServiceException;

public class CommentServiceImpl implements CommentService {
	private static final String ERROR_MESSAGE_GET = "can't get comment list ";
	private static final String ERROR_MESSAGE_ADD = "can't add comment list to DataBase ";
	
	@Override
	public List<Comment> getCommentsByAccount(int idAccount) throws CommentServiceException {
		List<Comment> commentList = null;
		DaoFactory factory = DaoFactory.getInstance();
		CommentDao cDao = factory.getCommentDao();
		Criteria criteria = factory.createCriteria();
		criteria.addCriterion(Operator.EQUAL, DBColumnNames.COMMENT_ACCOUNT_ID, String.valueOf(idAccount));
		criteria.addOrderColumn(DBColumnNames.COMMENT_DATE,false);
		
		try{
			commentList = cDao.getCommentsByCriteria(criteria);
		}catch(DaoException e){
			//log
			throw new CommentServiceException(ERROR_MESSAGE_GET,e);
		}
		return commentList;
	}

	@Override
	public List<Comment> getCommentsByFilm(int idFilm) throws CommentServiceException {
		List<Comment> commentList = null;
		DaoFactory factory = DaoFactory.getInstance();
		CommentDao cDao = factory.getCommentDao();
		Criteria criteria = factory.createCriteria();
		criteria.addCriterion(Operator.EQUAL, DBColumnNames.COMMENT_FILM_ID, String.valueOf(idFilm));
		criteria.addOrderColumn(DBColumnNames.COMMENT_DATE,false);
		try{
			commentList = cDao.getCommentsByCriteria(criteria);
		}catch(DaoException e){
			//log
			throw new CommentServiceException(ERROR_MESSAGE_GET,e);
		}
		return commentList;
	}

	@Override
	public void addComment(Comment comment) throws CommentServiceException {
		DaoFactory factory = DaoFactory.getInstance();
		CommentDao cDao = factory.getCommentDao();
		try{
			cDao.addComment(comment);
		}catch(DaoException e){
			//log
			throw new CommentServiceException(ERROR_MESSAGE_ADD,e);
		}
	}

}
