package by.epam.karotki.film_rating.dao;

import java.util.List;

import by.epam.karotki.film_rating.dao.exception.FilmDaoException;
import by.epam.karotki.film_rating.entity.Film;


public interface FilmDao {

	List<Film> getTopFilmsByRating(int value, String lang) throws FilmDaoException;

	List<Film> getFilmsByActors(String firstName, String lastName) throws FilmDaoException;

	List<Film> getFilmsByDirectors(String firstName, String lastName) throws FilmDaoException;

	List<Film> getFilmsByScenarioWriters(String firstName, String lastName) throws FilmDaoException;

	List<Film> getFilmsByGenre(String genre) throws FilmDaoException;

	List<Film> getMostBudgetFilms(int value) throws FilmDaoException;

	List<Film> getMostCashBoxFilms(int value) throws FilmDaoException;

	List<Film> getNewestFilms(int value) throws FilmDaoException;
	
	Film getFilmById(int id) throws FilmDaoException;
	
	Film getFilmByTitle(String title) throws FilmDaoException;
	
	List<Film> getFilmListByCriteria(Criteria criteria,String lang) throws FilmDaoException;
	
    void addFilm(Film film) throws FilmDaoException;
    
    void addFilm(Film film,String lang) throws FilmDaoException;
	
	void updateFilm(Film film) throws FilmDaoException;
	
	void updateFilm(Film film,String lang) throws FilmDaoException;
	
	void deleteFilmById(int id) throws FilmDaoException;
	
	void deleteFilmById(int id, String lang) throws FilmDaoException;

}
