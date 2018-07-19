/*
 * 
 */
package net.e4commerce.dpR_tmt;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
// TODO: Auto-generated Javadoc

/**
 * The Class ApplicationConfiguration.
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