package by.epam.karotki.film_rating.service;


import java.util.List;

import by.epam.karotki.film_rating.entity.Author;
import by.epam.karotki.film_rating.service.exception.AuthorServiceException;


public interface AuthorService {
	List<Author> getDirectorsByFilm(int idFilm, String lang) throws AuthorServiceException;
	
	List<Author> getScenarioWritersByFilm(int idFilm,String lang) throws AuthorServiceException;
	
	List<Author> getActorByFilm(int idFilm,String lang) throws AuthorServiceException;
	
	Author getAuthorById(int idAuthor,String lang) throws AuthorServiceException;
	
  //  Author addAuthor(Map<String,String> reqParam,InputStream is) throws  AuthorServiceException;
	
	//Author updateAuthor(Map<String,String> reqParam,InputStream is) throws  AuthorServiceException;
	
	//void deleteAuthor(int id) throws  AuthorServiceException;
}
