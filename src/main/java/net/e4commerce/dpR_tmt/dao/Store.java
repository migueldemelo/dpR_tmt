package net.e4commerce.dpR_tmt.dao;

import javax.ejb.Singleton;

import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.sail.inferencer.fc.ForwardChainingRDFSInferencer;
import org.eclipse.rdf4j.sail.memory.MemoryStore;

@Singleton
public class Store {
	
	private static final String DEFAULT_NS = "http://e4commerce.net/dpR_tmt/";
	private final SailRepository repository;
	private final ValueFactory valueFactory;
	private final RepositoryConnection connection;
	
	public Store() {
		this.repository = new SailRepository(new ForwardChainingRDFSInferencer(new MemoryStore()));
		this.repository.initialize();
		this.valueFactory = repository.getValueFactory();
		this.connection = repository.getConnection();
	}

	public SailRepository getRepository() {
		return repository;
	}

	public static String getDefaultNs() {
		return DEFAULT_NS;
	}

	public ValueFactory getValueFactory() {
		return valueFactory;
	}

	/**
	 * @return the connection
	 */
	public RepositoryConnection getConnection() {
		return connection;
	}
	
}
