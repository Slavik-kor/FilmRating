package by.epam.karotki.film_rating.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import by.epam.karotki.film_rating.dao.CommentDao;
import by.epam.karotki.film_rating.dao.Criteria;
import by.epam.karotki.film_rating.dao.DBColumnNames;
import by.epam.karotki.film_rating.dao.connection_pool.ConnectionPool;
import by.epam.karotki.film_rating.dao.connection_pool.exception.ConnectionPoolException;
import by.epam.karotki.film_rating.dao.exception.CommentDaoException;
import by.epam.karotki.film_rating.entity.Comment;

public class CommentDaoImpl implements CommentDao {
	private ConnectionPool conPool = ConnectionPool.getInstance();
	private static final String ERROR_MESSAGE_QUERY = "Can't perform query";
	private static final String ERROR_MESSAGE_CP = "Can't get connection from ConnectionPool";
	
	private static final String GET_COMMENT = "SELECT Account_id, Film_id, CommentText, CommentDate, Rate FROM Comment";
	
	private static final String ADD_COMMENT = "INSERT INTO Comment (Account_id, Film_id, CommentText, CommentDate, Rate) VALUES (?,?,?,NOW(),?) ";

	private static final String UPDATE_COMMENT = "UPDATE Comment SET CommentText = ?,  Rate = ? WHERE (Account_id = ?) AND (Film_id = ?)";

	private static final String DELETE_COMMENT = "DELETE FROM Country WHERE (Account_id = ?) AND (Film_id = ?)";

	@Override
	public List<Comment> getCommentsByCriteria(Criteria criteria) throws CommentDaoException {
		List<Comment> commentList = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(GET_COMMENT+criteria.getClause());
			rs = ps.executeQuery();
			commentList = getCommentList(rs);
		} catch (ConnectionPoolException e) {
			throw new CommentDaoException(ERROR_MESSAGE_CP, e);
		} catch (SQLException e) {
			throw new CommentDaoException(ERROR_MESSAGE_QUERY, e);
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				// LOG.error("Can't close ResultSet");
			}
			try {
				ps.close();
			} catch (SQLException e) {
				// LOG.error("Can't close PreparedStatement");
			}
			conPool.returnConnection(con);
		}
		return commentList;
	}

	@Override
	public void addComment(Comment comment) throws CommentDaoException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(ADD_COMMENT);
			ps.setInt(1,comment.getAccountId());
			ps.setInt(2,comment.getFilmId());
			ps.setString(3,comment.getComment());
			if(comment.getRate() != 0){
				ps.setInt(4,comment.getRate());
			}else{
				ps.setNull(4, Types.INTEGER);
			}
			ps.executeUpdate();
		} catch (ConnectionPoolException e) {
			throw new CommentDaoException(ERROR_MESSAGE_CP, e);
		} catch (SQLException e) {
			throw new CommentDaoException(ERROR_MESSAGE_QUERY, e);
		} finally {
			try {
				ps.close();
				} catch (SQLException e) {
				// LOG.error("Can't close PreparedStatement");
			}
			conPool.returnConnection(con);
		}		
	}

	@Override
	public void deleteComment(int idAccount, int idFilm) throws CommentDaoException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(DELETE_COMMENT);
			ps.setInt(1,idAccount);
			ps.setInt(2, idFilm);
			ps.executeUpdate();
		} catch (ConnectionPoolException e) {
			throw new CommentDaoException(ERROR_MESSAGE_CP, e);
		} catch (SQLException e) {
			throw new CommentDaoException(ERROR_MESSAGE_QUERY, e);
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				// LOG.error("Can't close PreparedStatement");
			}
			conPool.returnConnection(con);
		}		
	}

	@Override
	public void updateComment(Comment comment) throws CommentDaoException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = conPool.takeConnection();
			ps = con.prepareStatement(UPDATE_COMMENT);
			ps.setString(1, comment.getComment());
			ps.setInt(2, comment.getRate());
			ps.setInt(3, comment.getAccountId());
			ps.setInt(4, comment.getFilmId());
			ps.executeUpdate();
		} catch (ConnectionPoolException e) {
			throw new CommentDaoException(ERROR_MESSAGE_CP, e);
		} catch (SQLException e) {
			throw new CommentDaoException(ERROR_MESSAGE_QUERY, e);
		} finally {
			try {
				ps.close();
				} catch (SQLException e) {
				// LOG.error("Can't close PreparedStatement");
			}
			conPool.returnConnection(con);
		}		
	}
	
	private List<Comment> getCommentList(ResultSet rs) throws SQLException {
		List<Comment> commentList = new ArrayList<Comment>();
		while (rs.next()) {
			Comment comment = new Comment();
			comment.setAccountId(rs.getInt(DBColumnNames.COMMENT_ACCOUNT_ID));
			comment.setFilmId(rs.getInt(DBColumnNames.COMMENT_FILM_ID));
			comment.setComment(rs.getString(DBColumnNames.COMMENT_TEXT));
			comment.setCommentDate(rs.getDate(DBColumnNames.COMMENT_DATE));
			comment.setRate(rs.getInt(DBColumnNames.COMMENT_RATE));
			commentList.add(comment);
		}
		return commentList;
	}

}
