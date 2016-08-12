package by.epam.karotki.film_rating.dao;

import java.util.List;

import by.epam.karotki.film_rating.dao.exception.DaoException;
import by.epam.karotki.film_rating.entity.Film;


public interface FilmDao {

	List<Film> getTopFilmsByRating(int value, String lang) throws DaoException;

	List<Film> getFilmsByActors(String firstName, String lastName) throws DaoException;

	List<Film> getFilmsByDirectors(String firstName, String lastName) throws DaoException;

	List<Film> getFilmsByScenarioWriters(String firstName, String lastName) throws DaoException;

	List<Film> getFilmsByGenre(String genre) throws DaoException;

	List<Film> getMostBudgetFilms(int value) throws DaoException;

	List<Film> getMostCashBoxFilms(int value) throws DaoException;

	List<Film> getNewestFilms(int value) throws DaoException;
}
