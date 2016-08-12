package by.epam.karotki.film_rating.dao;

import java.util.List;

import by.epam.karotki.film_rating.dao.exception.DaoException;
import by.epam.karotki.film_rating.entity.Author;


public interface AuthorDao {

	List<Author> getAuthorListByCountry(String country) throws DaoException;

	List<Author> getAuthorListByFilm(String Title) throws DaoException;

}
