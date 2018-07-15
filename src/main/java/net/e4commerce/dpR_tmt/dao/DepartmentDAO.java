package net.e4commerce.dpR_tmt.dao;

import java.util.List;
import java.util.Random;

import javax.inject.Singleton;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.RepositoryConnection;

import net.e4commerce.dpR_tmt.model.Department;

@Singleton
public class DepartmentDAO extends DataAccessObject implements DataAccessInterface<Department> {

	@Override
	public void create(Department department) {

		IRI type = store.getValueFactory().createIRI(Store.getDefaultNs(), "Department");
		IRI departmentId = store.getValueFactory().createIRI(Store.getDefaultNs(), "departmentId");
		IRI departmentName = store.getValueFactory().createIRI(Store.getDefaultNs(), "departmentName");
		
		Random rand = new Random();
		Integer  n = rand.nextInt(50) + 1;
		String id = department.getDepartmentId() != null ? department.getDepartmentId() : n.toString();
		
		IRI subject = store.getValueFactory().createIRI(Store.getDefaultNs(), "department_"+id);
		Literal label = store.getValueFactory().createLiteral(department.getDepartmentName());
		Literal idLiteral = store.getValueFactory().createLiteral(id);
		
		try (RepositoryConnection conn = store.getRepository().getConnection()) {
			conn.add(subject, RDF.TYPE, type);
			conn.add(subject, RDFS.LABEL, label);
			conn.add(subject, departmentId, idLiteral);
			conn.add(subject, departmentName, label);
		}

	}

	@Override
	public void delete(Department department) {
		// TODO Auto-generated method stub
	}

	@Override
	public Department get(Department department) {
		Department dep = new Department();
		
		try (RepositoryConnection conn = store.getRepository().getConnection()) {
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
	    	TupleQuery tupleQuery = conn.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
	    	tupleQuery.setBinding("id", store.getValueFactory().createLiteral(department.getDepartmentId()));
	    	
	    	try (TupleQueryResult result = tupleQuery.evaluate()) 
	    	{
	    		while (result.hasNext()) 
	    		{  
	    			BindingSet bindingSet = result.next();
	    			
	    			Value id = bindingSet.getValue("id");
	    			Value name = bindingSet.getValue("name");
	    			
	    			dep.setDepartmentId(id.stringValue());
	    			dep.setDepartmentName(name.stringValue());
	    			
	    		}
	    	}
		}
		
		return dep;
	}

	@Override
	public void update(Department department) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<Department> search(Department subject) {
		// TODO Auto-generated method stub
		return null;
	}

}
