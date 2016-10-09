package by.epam.karotki.film_rating.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.karotki.film_rating.service.InitService;
import by.epam.karotki.film_rating.service.ServiceFactory;

/**
 * Application Lifecycle Listener implementation class FilmRatingListener
 *
 */
@WebListener
public class FRServletContextListener implements ServletContextListener {
	
	private InitService iService;
	
	private static final Logger LOG = LogManager.getLogger();
	

	/**
	 * Default constructor.
	 */
	public FRServletContextListener() {

	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent event) {
		iService.destroy();
		LOG.info("Servlet Context is destroyed");
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent event) {
		
			ServiceFactory fService = ServiceFactory.getInstance();
			iService = fService.getInitService();
			iService.init();
			LOG.info("Servlet Context initialization is performed");
		
	}

}
