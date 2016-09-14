package by.epam.karotki.film_rating.service.impl;

import java.util.List;

import by.epam.karotki.film_rating.dao.CommentDao;
import by.epam.karotki.film_rating.dao.Criteria;
import by.epam.karotki.film_rating.dao.DBColumnName;
import by.epam.karotki.film_rating.dao.DaoFactory;
import by.epam.karotki.film_rating.dao.Operator;
import by.epam.karotki.film_rating.dao.exception.DaoException;
import by.epam.karotki.film_rating.entity.Comment;
import by.epam.karotki.film_rating.service.CommentService;
import by.epam.karotki.film_rating.service.exception.CommentServiceException;
import by.epam.karotki.film_rating.service.util.ServiceUtil;

public class CommentServiceImpl implements CommentService {
	private static final String ERROR_MESSAGE_GET = "can't get comment list ";
	private static final String ERROR_MESSAGE_ADD = "can't add comment list to DataBase ";
	
	@Override
	public List<Comment> getCommentsByAccount(int idAccount) throws CommentServiceException {
		List<Comment> commentList = null;
		DaoFactory factory = DaoFactory.getInstance();
		CommentDao cDao = factory.getCommentDao();
		Criteria criteria = factory.createCriteria();
		criteria.addCriterion(Operator.EQUAL, DBColumnName.COMMENT_ACCOUNT_ID, String.valueOf(idAccount));
		criteria.addOrderColumn(DBColumnName.COMMENT_DATE,false);
		
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
		criteria.addCriterion(Operator.EQUAL, DBColumnName.COMMENT_FILM_ID, String.valueOf(idFilm));
		criteria.addOrderColumn(DBColumnName.COMMENT_DATE,true);
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

	@Override
	public double getAvgRateByFilm(int idFilm) throws CommentServiceException {
		List<Comment> commentList = null;
		DaoFactory factory = DaoFactory.getInstance();
		CommentDao cDao = factory.getCommentDao();
		Criteria criteria = factory.createCriteria();
		criteria.addCriterion(Operator.EQUAL, DBColumnName.COMMENT_FILM_ID, String.valueOf(idFilm));
		criteria.addOrderColumn(DBColumnName.COMMENT_DATE,true);
		try{
			commentList = cDao.getCommentsByCriteria(criteria);
		}catch(DaoException e){
			//log
			throw new CommentServiceException(ERROR_MESSAGE_GET,e);
		}
		Double rate = ServiceUtil.avg(commentList);
		return rate;
	}

	@Override
	public void updateComment(Comment comment) throws CommentServiceException {
		
		DaoFactory factory = DaoFactory.getInstance();
		CommentDao cDao = factory.getCommentDao();
		try{
			cDao.updateComment(comment);
		}catch(DaoException e){
			throw new CommentServiceException(ERROR_MESSAGE_GET,e);
		}
		
	}

	@Override
	public void deleteComment(int idAccount, int idFilm) throws CommentServiceException {
		DaoFactory factory = DaoFactory.getInstance();
		CommentDao cDao = factory.getCommentDao();
		try{
			cDao.deleteComment(idAccount, idFilm);
		}catch(DaoException e){
			throw new CommentServiceException(ERROR_MESSAGE_GET,e);
		}
		
	}

}
