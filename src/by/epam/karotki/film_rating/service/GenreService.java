package by.epam.karotki.film_rating.service;

import java.util.List;

import by.epam.karotki.film_rating.entity.Genre;
import by.epam.karotki.film_rating.service.exception.ServiceException;

public interface GenreService {
	List<Genre> getGenreListByFilm(int idFilm) throws ServiceException;

}
