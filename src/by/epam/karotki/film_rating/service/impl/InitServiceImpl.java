package by.epam.karotki.film_rating.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.karotki.film_rating.dao.DaoFactory;
import by.epam.karotki.film_rating.dao.InitDao;
import by.epam.karotki.film_rating.service.InitService;

public class InitServiceImpl implements InitService {
	
	private static final Logger LOG = LogManager.getLogger();

	@Override
	public void init() {
		DaoFactory dao = DaoFactory.getInstance();
		InitDao iDao = dao.getInitDao();
		iDao.initDao();
		LOG.debug("init method is performed");
	}

	public void destroy() {
		DaoFactory dao = DaoFactory.getInstance();
		InitDao iDao = dao.getInitDao();
		iDao.destroyDao();
		LOG.debug("destroy method is performed");
	}

}
