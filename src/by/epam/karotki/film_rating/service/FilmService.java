package by.epam.karotki.film_rating.service;

import java.util.List;

import by.epam.karotki.film_rating.entity.Film;
import by.epam.karotki.film_rating.service.exception.FilmServiceException;

public interface FilmService {
	
	List<Film> getFilmsByNewest(int value,String lang) throws FilmServiceException;
	
	List<Film> getFilmsByGenre(int idGenre, int value,String lang) throws FilmServiceException;
	
	Film getFilmById(int value,String lang) throws FilmServiceException;
}
