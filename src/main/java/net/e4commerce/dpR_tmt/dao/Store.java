/*
 * 
 */
package net.e4commerce.dpR_tmt.dao;

import javax.ejb.Singleton;

import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.sail.inferencer.fc.ForwardChainingRDFSInferencer;
import org.eclipse.rdf4j.sail.memory.MemoryStore;

// TODO: Auto-generated Javadoc
/**
 * The Class Store.
 */
@Singleton
public class Store {
	
	/** The Constant DEFAULT_NS. */
	private static final String DEFAULT_NS = "http://e4commerce.net/dpR_tmt/";
	
	/** The repository. */
	private final SailRepository repository;
	
	/** The value factory. */
	private final ValueFactory valueFactory;
	
	/** The connection. */
	private final RepositoryConnection connection;
	
	/**
	 * Instantiates a new store.
	 */
	public Store() {
		this.repository = new SailRepository(new ForwardChainingRDFSInferencer(new MemoryStore()));
		this.repository.initialize();
		this.valueFactory = repository.getValueFactory();
		this.connection = repository.getConnection();
	}

	/**
	 * Gets the repository.
	 *
	 * @return the repository
	 */
	public SailRepository getRepository() {
		return repository;
	}

	/**
	 * Gets the default ns.
	 *
	 * @return the default ns
	 */
	public static String getDefaultNs() {
		return DEFAULT_NS;
	}

	/**
	 * Gets the value factory.
	 *
	 * @return the value factory
	 */
	public ValueFactory getValueFactory() {
		return valueFactory;
	}

	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 */
	public RepositoryConnection getConnection() {
		return connection;
	}
	
}
