package net.e4commerce.dpR_tmt.dao;

import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.repository.RepositoryConnection;

/**
 * The Class DataAccessObject is an abstract class which sets the two main dependencies
 * required to interact with a store:
 * <ul>
 * <li>ValueFactory
 * <li>RepositoryConnection
 * </ul>
 *  
 * @author      Miguel de Melo
 * @version     1.0
 * @since       1.0
 */
public abstract class DataAccessObject {

	/** The value factory is used to build IRI and Literal values */
	protected final ValueFactory valueFactory;
	
	/** The connection provides read/write operations with the store */
	protected final RepositoryConnection connection;
	
	/**
	 * Instantiates a new data access object.
	 *
	 * @param valueFactory the value factory is used to build IRI and Literal values
	 * @param connection the connection provides read/write operations with the store
	 */
	public DataAccessObject(ValueFactory valueFactory, RepositoryConnection connection) {
		this.valueFactory = valueFactory;
		this.connection = connection;
	}

}
