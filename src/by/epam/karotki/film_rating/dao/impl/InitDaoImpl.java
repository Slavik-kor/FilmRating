package by.epam.karotki.film_rating.dao.impl;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.karotki.film_rating.dao.InitDao;
import by.epam.karotki.film_rating.dao.connection_pool.ConnectionPool;
import by.epam.karotki.film_rating.dao.connection_pool.exception.ConnectionPoolException;

public class InitDaoImpl implements InitDao {
	
	private static final Logger LOG = LogManager.getLogger();
		
	private ConnectionPool pool;

	@Override
	public void initDao() {
		try {
			pool = ConnectionPool.getInstance();
			pool.initPoolData();
		} catch (ConnectionPoolException e) {
			LOG.error(e.getMessage());
			throw new RuntimeException(e.getMessage(), e);
		}
		LOG.debug(" DAO Layer initialization is performed");
	}

	@Override
	public void destroyDao() {
		pool.dispose();
		LOG.debug("DAO layer is destroyed");
	}

}
