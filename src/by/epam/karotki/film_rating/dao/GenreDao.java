package by.epam.karotki.film_rating.dao;

import java.util.List;

import by.epam.karotki.film_rating.dao.exception.GenreDaoException;
import by.epam.karotki.film_rating.entity.Genre;

public interface GenreDao {
	
	List<Genre> getGenreListByFilm(int idFilm) throws GenreDaoException;
	
	List<Genre> getGenreByCriteria(Criteria cr,String lang) throws GenreDaoException;
	
	void addGenre(Genre genre) throws GenreDaoException;
	
	void addGenre(Genre genre, String lang) throws GenreDaoException;
	
	void updateGenre(Genre genre) throws GenreDaoException;
	
	void updateGenre(Genre genre,String lang) throws GenreDaoException;
	
	void deleteGenreById(int id) throws GenreDaoException;
	
	void deleteGenreById(int id,String lang) throws GenreDaoException;
}
