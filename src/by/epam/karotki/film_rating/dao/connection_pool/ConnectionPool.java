package by.epam.karotki.film_rating.dao.connection_pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import by.epam.karotki.film_rating.dao.connection_pool.exception.ConnectionPoolException;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

public final class ConnectionPool {
//	private static final Logger LOG = LogManager.getLogger(ConnectionPool.class);

	private BlockingQueue<Connection> connectionQueue;
	private BlockingQueue<Connection> givenAwayConQueue;

	private String driverName;
	private String url;
	private String user;
	private String password;
	private int poolSize;
	private static final ConnectionPool instance = new ConnectionPool();

	private ConnectionPool() {
		DBResourceManager dbResourceManager = DBResourceManager.getInstance();
		this.driverName = dbResourceManager.getValue(DBParameter.DB_DRIVER);
		this.url = dbResourceManager.getValue(DBParameter.DB_URL);
		this.user = dbResourceManager.getValue(DBParameter.DB_USER);
		this.password = dbResourceManager.getValue(DBParameter.DB_PASSWORD);

		try {
			this.poolSize = Integer.parseInt(dbResourceManager.getValue(DBParameter.DB_POLL_SIZE));
		} catch (NumberFormatException e) {
			poolSize = 5;
		}

	}

	public static ConnectionPool getInstance() {
		return instance;
	}

	public void initPoolData() throws ConnectionPoolException {

		try {
			Class.forName(driverName);
			givenAwayConQueue = new ArrayBlockingQueue<Connection>(poolSize);
			connectionQueue = new ArrayBlockingQueue<Connection>(poolSize);
			for (int i = 0; i < poolSize; i++) {
				Connection connection = DriverManager.getConnection(url, user, password);
				connectionQueue.add(connection);
			}
		} catch (SQLException e) {
			throw new ConnectionPoolException("Can't get and add new connection in pool.", e);

		} catch (ClassNotFoundException e) {
			throw new ConnectionPoolException("Can't find database driver class.", e);
		}

	}

	public synchronized Connection takeConnection() throws ConnectionPoolException {
		Connection con = null;
		try {
				con = connectionQueue.take();
				givenAwayConQueue.add(con);
			
		} catch (InterruptedException e) {
			throw new ConnectionPoolException("Error connecting to the data source.", e);
		} 
		return con;
	}

	public synchronized void returnConnection(Connection con) {
		if (givenAwayConQueue.remove(con)) {
			connectionQueue.offer(con);
		} else {
			try {
				con.close();
			} catch (SQLException e) {
	//			LOG.warn("Can't close connection!");
			}
		}
	}

	public void dispose() {
		closeConnectionsQueue(givenAwayConQueue);
		closeConnectionsQueue(connectionQueue);
	}

	private void closeConnectionsQueue(BlockingQueue<Connection> queue) {
		Connection con;
		while ((con = queue.poll()) != null) {
			try {
				con.close();
			} catch (SQLException e) {
//				LOG.warn("Can't close connection!");
			}
		}
	}

}
