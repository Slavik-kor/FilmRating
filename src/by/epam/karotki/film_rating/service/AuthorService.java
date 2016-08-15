package by.epam.karotki.film_rating.service;

import java.util.List;

import by.epam.karotki.film_rating.entity.Author;
import by.epam.karotki.film_rating.service.exception.ServiceException;

public interface AuthorService {
	List<Author> getDirectorsByFilm(int idFilm) throws ServiceException;
	
	List<Author> getScenarioWritersByFilm(int idFilm) throws ServiceException;
	
	List<Author> getActorByFilm(int idFilm) throws ServiceException;
	
	Author getAuthorById(int idAuthor) throws ServiceException;
}
