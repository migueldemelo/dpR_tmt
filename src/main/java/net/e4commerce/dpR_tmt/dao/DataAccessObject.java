package net.e4commerce.dpR_tmt.dao;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public abstract class DataAccessObject {

	protected final Store store;
	
	@Inject 
	public DataAccessObject(Store store) {
		this.store = store;
	}

}
