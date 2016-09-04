package by.epam.karotki.film_rating.dao;

import by.epam.karotki.film_rating.dao.exception.DaoException;

public interface FilmAuthorDao {
	
    void addAuthorToFilm(int idFilm, int idAuthor,String role) throws DaoException;
	
	void deleteAuthorFromFilm(int idFilm, int idAuthor, String role) throws DaoException;
}
