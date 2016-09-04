package by.epam.karotki.film_rating.service.impl;

import java.util.List;

import by.epam.karotki.film_rating.dao.CommentDao;
import by.epam.karotki.film_rating.dao.DaoFactory;
import by.epam.karotki.film_rating.dao.exception.DaoException;
import by.epam.karotki.film_rating.dao.util.Criteria;
import by.epam.karotki.film_rating.dao.util.DBColumnNames;
import by.epam.karotki.film_rating.dao.util.Operator;
import by.epam.karotki.film_rating.entity.Comment;
import by.epam.karotki.film_rating.service.CommentService;
import by.epam.karotki.film_rating.service.exception.CommentServiceException;

public class CommentServiceImpl implements CommentService {

	@Override
	public List<Comment> getCommentsByAccount(int idAccount) throws CommentServiceException {
		List<Comment> commentList = null;
		DaoFactory factory = DaoFactory.getInstance();
		CommentDao cDao = factory.getCommentDao();
		Criteria criteria = new Criteria();
		criteria.addCriterion(Operator.EQUAL, DBColumnNames.COMMENT_ACCOUNT_ID, String.valueOf(idAccount));
		try{
			commentList = cDao.getCommentsByCriteria(criteria);
		}catch(DaoException e){
			//log
			throw new CommentServiceException("can't get comment list by account",e);
		}
		return commentList;
	}

	@Override
	public List<Comment> getCommentsByFilm(int idFilm) throws CommentServiceException {
		List<Comment> commentList = null;
		DaoFactory factory = DaoFactory.getInstance();
		CommentDao cDao = factory.getCommentDao();
		Criteria criteria = new Criteria();
		criteria.addCriterion(Operator.EQUAL, DBColumnNames.COMMENT_ACCOUNT_ID, String.valueOf(idFilm));
		try{
			commentList = cDao.getCommentsByCriteria(criteria);
		}catch(DaoException e){
			//log
			throw new CommentServiceException("can't get comment list by film",e);
		}
		return commentList;
	}

}
