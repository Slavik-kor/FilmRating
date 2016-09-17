package by.epam.karotki.film_rating.service;


import java.io.InputStream;
import java.util.List;
import java.util.Map;

import by.epam.karotki.film_rating.entity.Author;
import by.epam.karotki.film_rating.service.exception.AuthorServiceException;


public interface AuthorService {
	List<Author> getDirectorsByFilm(int idFilm, String lang) throws AuthorServiceException;
	
	List<Author> getScenarioWritersByFilm(int idFilm,String lang) throws AuthorServiceException;
	
	List<Author> getActorByFilm(int idFilm,String lang) throws AuthorServiceException;
	
	Author getAuthorById(int idAuthor,String lang) throws AuthorServiceException;
	
	List<Author> getAllAuthors(String lang) throws AuthorServiceException;
	
  //  Author addAuthor(Map<String,String> reqParam,InputStream is) throws  AuthorServiceException;
	
	//Author updateAuthor(Map<String,String> reqParam,InputStream is) throws  AuthorServiceException;
	
	//void deleteAuthor(int id) throws  AuthorServiceException;
	
	void addDirectorToFilm(int idFilm, int idAuthor) throws AuthorServiceException;

	void addScenarioToFilm(int idFilm, int idAuthor) throws AuthorServiceException;

	void addActorToFilm(int idFilm, int idAuthor) throws AuthorServiceException;

	Author addAuthor(Map<String,String> reqParam,InputStream is) throws AuthorServiceException;

	void addAuthor(Author author, String lang) throws AuthorServiceException;
}
