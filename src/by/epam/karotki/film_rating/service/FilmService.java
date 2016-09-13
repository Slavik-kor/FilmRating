package by.epam.karotki.film_rating.service;

import java.util.List;

import by.epam.karotki.film_rating.entity.Film;
import by.epam.karotki.film_rating.service.exception.FilmServiceException;

public interface FilmService {
	
	List<Film> getFilmsByNewest(String lang) throws FilmServiceException;
	
	List<Film> getFilmsByGenre(int idGenre,String lang) throws FilmServiceException;
	
	List<Film> getFilmsByDirector(int idAuthor,String lang) throws FilmServiceException;
	
	List<Film> getFilmsByScenarioWriter(int idAuthor,String lang) throws FilmServiceException;
	
	List<Film> getFilmsByActor(int idAuthor,String lang) throws FilmServiceException;
	
	Film getFilmById(int idFilm,String lang) throws FilmServiceException;

	List<Film> getFilmsByYear(int year,String lang) throws FilmServiceException;
	
	List<Film> getFilmsByRating(String lang) throws FilmServiceException;

	List<Film> getFilmsByComments(int idAccount, String lang) throws FilmServiceException;

	List<Film> getFilmsByAccountRate(int idAccount, String lang) throws FilmServiceException;

	
}
