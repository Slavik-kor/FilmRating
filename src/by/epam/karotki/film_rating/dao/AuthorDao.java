package by.epam.karotki.film_rating.dao;

import java.util.List;

import by.epam.karotki.film_rating.dao.exception.AuthorDaoException;
import by.epam.karotki.film_rating.entity.Author;


public interface AuthorDao {

	List<Author> getAuthorListByCountry(String country) throws AuthorDaoException;

	List<Author> getAuthorListByFilm(String Title) throws AuthorDaoException;
	
	List<Author> getAuthorListByFilm(int idFilm,String role) throws AuthorDaoException;
	
	Author getAuthorById(int idAuthor) throws AuthorDaoException;
	
	Author getAuthorByName(String firstName,String lastName) throws AuthorDaoException;
	
	List<Author> getAuthorByCriteria(Criteria criteria,String lang) throws AuthorDaoException;
	
    void addAuthor(Author author) throws AuthorDaoException;
    
    void addAuthor(Author author,String lang) throws AuthorDaoException;
	
	void updateAuthor(Author author) throws AuthorDaoException;
	
	void updateAuthor(Author author,String lang) throws AuthorDaoException;

	void deleteAuthorById(int id) throws AuthorDaoException;
	
	void deleteAuthorById(int id,String lang) throws AuthorDaoException;
}
