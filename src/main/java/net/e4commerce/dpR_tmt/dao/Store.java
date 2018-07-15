package net.e4commerce.dpR_tmt.dao;

import javax.ejb.Singleton;

import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.sail.memory.MemoryStore;

@Singleton
public class Store {
	
	private static final String DEFAULT_NS = "http://e4commerce.net/dpR_tmt/";
	private final SailRepository repository;
	private final ValueFactory valueFactory;
	
	public Store() {
		repository = new SailRepository
				(
						new MemoryStore()
//						new ForwardChainingRDFSInferencer(new MemoryStore())
						);
		repository.initialize();
		valueFactory = repository.getValueFactory();
		System.out.println("STORE");
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
	
}
