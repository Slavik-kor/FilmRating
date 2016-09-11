package by.epam.karotki.film_rating.dao;

import java.util.List;

import by.epam.karotki.film_rating.dao.exception.DaoException;

public interface FilmAuthorDao {
	
	List<Integer> getFilmsByAuthor(int idAuthor,String role) throws DaoException;
	
	List<Integer> getAuthorsByFilm(int idFilm, String role) throws DaoException;
	
    void addAuthorToFilm(int idFilm, int idAuthor,String role) throws DaoException;
	
	void deleteAuthorFromFilm(int idFilm, int idAuthor, String role) throws DaoException;
}
