package by.epam.karotki.film_rating.service;

import by.epam.karotki.film_rating.service.exception.InitServiceException;

public interface InitService {
	
	void init() throws InitServiceException;
	
	void destroy();

}
