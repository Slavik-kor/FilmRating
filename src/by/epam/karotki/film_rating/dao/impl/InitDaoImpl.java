package by.epam.karotki.film_rating.dao.impl;

import by.epam.karotki.film_rating.dao.InitDao;
import by.epam.karotki.film_rating.dao.connection_pool.ConnectionPool;
import by.epam.karotki.film_rating.dao.connection_pool.exception.ConnectionPoolException;

public class InitDaoImpl implements InitDao {
	private static final String ERROR_MESSAGE = "JDBC Driver error";
	
	private ConnectionPool pool;

	@Override
	public void initDao() {
		try {
			pool = ConnectionPool.getInstance();
			pool.initPoolData();
		} catch (ConnectionPoolException e) {
			throw new RuntimeException(ERROR_MESSAGE, e);
		}

	}

	@Override
	public void destroyDao() {
		pool.dispose();
	}

}
