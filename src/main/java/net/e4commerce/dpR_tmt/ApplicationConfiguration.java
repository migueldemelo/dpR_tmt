package net.e4commerce.dpR_tmt;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * The Class ApplicationConfiguration initiates a new application configuration 
 * and registers the Inversion control binder for net.e4commerce.dpR_tmt package.
 *  
 * @author      Miguel de Melo
 * @version     1.0
 * @since       1.0
 */
@ApplicationPath("/resources")
public class ApplicationConfiguration extends ResourceConfig {
	

	/**
	 * Instantiates a new application configuration.
	 */
	public ApplicationConfiguration() {
		packages("net.e4commerce.dpR_tmt");
	       register(new MyApplicationBinder());  
	    }
}