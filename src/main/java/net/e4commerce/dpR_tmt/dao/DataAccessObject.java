/*
 * 
 */
package net.e4commerce.dpR_tmt.dao;

import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.repository.RepositoryConnection;

public abstract class DataAccessObject {

	protected final ValueFactory valueFactory;
	protected final RepositoryConnection connection;
	
	/**
	 * Instantiates a new data access object.
	 *
	 * @param valueFactory the value factory
	 * @param connection the connection
	 */
	public DataAccessObject(ValueFactory valueFactory, RepositoryConnection connection) {
		this.valueFactory = valueFactory;
		this.connection = connection;
	}

}
