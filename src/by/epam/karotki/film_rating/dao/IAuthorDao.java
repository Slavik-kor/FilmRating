package by.epam.karotki.film_rating.dao;

import java.util.List;

import by.epam.karotki.film_rating.dao.exception.DaoException;
import by.epam.karotki.film_rating.entity.Author;


public interface IAuthorDao {

	List<Author> getAuthorListByCountry(String country) throws DaoException;

	List<Author> getAuthorListByFilm(String Title) throws DaoException;

}
