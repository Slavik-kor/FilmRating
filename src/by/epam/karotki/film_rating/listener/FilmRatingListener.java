package by.epam.karotki.film_rating.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import by.epam.karotki.film_rating.dao.connection_pool.ConnectionPool;
import by.epam.karotki.film_rating.dao.connection_pool.exception.ConnectionPoolException;

/**
 * Application Lifecycle Listener implementation class FilmRatingListener
 *
 */
@WebListener
public class FilmRatingListener implements ServletContextListener {
	private ConnectionPool pool;
    /**
     * Default constructor. 
     */
    public FilmRatingListener() {
        
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
        pool.dispose();
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
      try{
    	pool = ConnectionPool.getInstance();
         pool.initPoolData();
      } catch(ConnectionPoolException e){
    	  throw new RuntimeException("JDBC Driver error",e);
      }
    }
	
}
