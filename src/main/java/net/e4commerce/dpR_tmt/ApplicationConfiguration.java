package net.e4commerce.dpR_tmt;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
@ApplicationPath("/resources")
public class ApplicationConfiguration extends ResourceConfig {
	
	public ApplicationConfiguration() {
		packages("net.e4commerce.dpR_tmt");
	       register(new MyApplicationBinder());  
	    }
}