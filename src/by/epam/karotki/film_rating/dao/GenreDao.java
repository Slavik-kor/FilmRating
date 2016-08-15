package by.epam.karotki.film_rating.dao;

import java.util.List;

import by.epam.karotki.film_rating.dao.exception.DaoException;
import by.epam.karotki.film_rating.entity.Genre;

public interface GenreDao {
	List<Genre> getGenreListByFilm(int idFilm) throws DaoException;
}
