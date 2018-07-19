/*
 * 
 */
package net.e4commerce.dpR_tmt.dao;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.commons.lang3.NotImplementedException;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.RepositoryConnection;

import net.e4commerce.dpR_tmt.model.Department;

// TODO: Auto-generated Javadoc
/**
 * The Class DepartmentDAO.
 *
 * @author delirio
 */
@Singleton
public class DepartmentDAO extends DataAccessObject implements DataAccessInterface<Department> {

	/** The type. */
	private final IRI type;
	
	/** The department id. */
	private final IRI departmentId;
	
	/** The department name. */
	private final IRI departmentName;
	
	/**
	 * Instantiates a new department DAO.
	 *
	 * @param valueFactory the value factory
	 * @param connection the connection
	 */
	@Inject
	public DepartmentDAO(ValueFactory valueFactory, RepositoryConnection connection) {
		super(valueFactory, connection);
		
		this.type = valueFactory.createIRI(Store.getDefaultNs(), "Department");
		this.departmentId = valueFactory.createIRI(Store.getDefaultNs(), "departmentId");
		this.departmentName = valueFactory.createIRI(Store.getDefaultNs(), "departmentName");
	}
	
	
	/* (non-Javadoc)
	 * @see net.e4commerce.dpR_tmt.dao.DataAccessInterface#create(java.lang.Object)
	 */
	@Override
	public void create(Department department) {
		IRI subject = valueFactory.createIRI(Store.getDefaultNs(), "department_" + department.getDepartmentId());
		Literal label = valueFactory.createLiteral(department.getDepartmentName());
		Literal idLiteral = valueFactory.createLiteral(department.getDepartmentId());
		
		connection.add(subject, RDF.TYPE, type);
		connection.add(subject, RDFS.LABEL, label);
		connection.add(subject, departmentId, idLiteral);
		connection.add(subject, departmentName, label);
	}

	/* (non-Javadoc)
	 * @see net.e4commerce.dpR_tmt.dao.DataAccessInterface#delete(java.lang.String)
	 */
	@Override
	public void delete(String id) {
		throw new NotImplementedException("not implemented");
	}

	/* (non-Javadoc)
	 * @see net.e4commerce.dpR_tmt.dao.DataAccessInterface#get(java.lang.String)
	 */
	@Override
	public Department get(String id) throws Exception {
		
		String queryString = 
				"PREFIX dp: <"+Store.getDefaultNs() +"> \n" + 
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n" + 
				"SELECT ?id ?name \n" + 
				"WHERE {\n" + 
				" ?s a dp:Department ; \n" +
				" dp:departmentId ?id ; \n" +
				" dp:departmentName ?name ; \n" +
				" .\n" +
				"}";
		TupleQuery tupleQuery = connection.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
		tupleQuery.setBinding("id", valueFactory.createLiteral(id));
		
		try (TupleQueryResult result = tupleQuery.evaluate()) 
		{
			while (result.hasNext()) 
			{  
				BindingSet bindingSet = result.next();
				Department department = new Department();
				department.setDepartmentId(bindingSet.getValue("id").stringValue());
				department.setDepartmentName(bindingSet.getValue("name").stringValue());
				
				return department;
			}
		}
		
		throw new Exception();
	}

	/* (non-Javadoc)
	 * @see net.e4commerce.dpR_tmt.dao.DataAccessInterface#update(java.lang.Object)
	 */
	@Override
	public void update(Department department) {
		throw new NotImplementedException("not implemented");
	}

	/**
	 * Search employee department.
	 *
	 * @param employeeName the employee name
	 * @return the list
	 */
	public List<Department> searchEmployeeDepartment(String employeeName) {
		List<Department> departments = new ArrayList<Department>();
		
			String queryString = 
					"PREFIX dp: <"+Store.getDefaultNs() +"> \n" + 
					"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n" + 
					"SELECT DISTINCT ?id ?name \n" + 
					"WHERE {\n" + 
					" ?department a dp:Department ; \n" +
					" dp:departmentId ?id ; \n" +
					" dp:departmentName ?name ; \n" +
					" .\n"
					+ "?employee a dp:Employee ;\n"
					+ "rdfs:label ?employeeName ; \n"
					+ "dp:hasDepartment ?department ;"
					+ ". \n" +
					"}";
	    	TupleQuery tupleQuery = connection.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
	    	tupleQuery.setBinding("employeeName", valueFactory.createLiteral(employeeName));
	    	
	    	try (TupleQueryResult result = tupleQuery.evaluate()) 
	    	{
	    		while (result.hasNext()) 
	    		{  
	    			Department dep = new Department();
	    			BindingSet bindingSet = result.next();
	    			
	    			dep.setDepartmentId(bindingSet.getValue("id").stringValue());
	    			dep.setDepartmentName(bindingSet.getValue("name").stringValue());
	    			
	    			departments.add(dep);
	    		}
	    	}
		
		return departments;
	}


}
