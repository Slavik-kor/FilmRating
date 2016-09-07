package by.epam.karotki.film_rating.service.impl;

import by.epam.karotki.film_rating.dao.DaoFactory;
import by.epam.karotki.film_rating.dao.InitDao;
import by.epam.karotki.film_rating.service.InitService;

public class InitServiceImpl implements InitService {

	@Override
	public void init() {
		DaoFactory dao = DaoFactory.getInstance();
		InitDao iDao = dao.getInitDao();
		iDao.initDao();
	}

	public void destroy() {
		DaoFactory dao = DaoFactory.getInstance();
		InitDao iDao = dao.getInitDao();
		iDao.destroyDao();

	}

}
