package by.epam.karotki.film_rating.service.impl;

import by.epam.karotki.film_rating.dao.connection_pool.ConnectionPool;
import by.epam.karotki.film_rating.dao.connection_pool.exception.ConnectionPoolException;
import by.epam.karotki.film_rating.service.InitService;
import by.epam.karotki.film_rating.service.exception.InitServiceException;

public class InitServiceImpl implements InitService {
	private ConnectionPool pool;

	@Override
	public void init() throws InitServiceException {
		try {
			pool = ConnectionPool.getInstance();
			pool.initPoolData();
		} catch (ConnectionPoolException e) {
			throw new RuntimeException("JDBC Driver error", e);
		}

	}
	
	public void destroy() {
	pool.dispose();

	}

}
