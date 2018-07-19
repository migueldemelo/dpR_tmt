package net.e4commerce.dpR_tmt.dao;

import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.repository.RepositoryConnection;

public abstract class DataAccessObject {

//	protected final Store store;
	protected final ValueFactory valueFactory;
	protected final RepositoryConnection connection;

//	public DataAccessObject(Store store) {
//		this.store = store;
//		this.valueFactory = store.getValueFactory();
//		this.connection = store.getConnection();
//	}
	
	public DataAccessObject(ValueFactory valueFactory, RepositoryConnection connection) {
//		this.store = null;
		this.valueFactory = valueFactory;
		this.connection = connection;
	}

}
