package by.epam.karotki.film_rating.service;

import java.util.List;

import by.epam.karotki.film_rating.entity.Genre;
import by.epam.karotki.film_rating.service.exception.GenreServiceException;

public interface GenreService {
	
	List<Genre> getGenreListByFilm(int idFilm, String lang) throws GenreServiceException;

	void addGenreToFilm(int idFilm, String[] idGenre) throws GenreServiceException;

	List<Genre> getAllGenres(String lang) throws GenreServiceException;

}
