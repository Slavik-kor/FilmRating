package by.epam.karotki.film_rating.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import by.epam.karotki.film_rating.service.InitService;
import by.epam.karotki.film_rating.service.ServiceFactory;

/**
 * Application Lifecycle Listener implementation class FilmRatingListener
 *
 */
@WebListener
public class FilmRatingListener implements ServletContextListener {
	private InitService iService;
	

	/**
	 * Default constructor.
	 */
	public FilmRatingListener() {

	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent event) {
		iService.destroy();
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent event) {
		
		//	String str = event.getServletContext().getInitParameter(LOCALE);
		
			ServiceFactory fService = ServiceFactory.getInstance();
			iService = fService.getInitService();
			iService.init();
		
	}

}
