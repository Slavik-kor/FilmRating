package by.epam.karotki.film_rating.service;

import java.util.List;

import by.epam.karotki.film_rating.entity.Film;
import by.epam.karotki.film_rating.service.exception.ServiceException;

public interface FilmService {
	List<Film> getFilmsByNewest(int value) throws ServiceException;

	Film getFilmById(int value) throws ServiceException;
}
