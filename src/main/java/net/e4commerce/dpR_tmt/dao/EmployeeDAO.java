package net.e4commerce.dpR_tmt.dao;

import java.util.Random;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.vocabulary.FOAF;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.query.Update;
import org.eclipse.rdf4j.repository.RepositoryConnection;

import net.e4commerce.dpR_tmt.model.Employee;

@ApplicationScoped
@Startup
@Singleton
public class EmployeeDAO extends DataAccessObject implements DataAccessInterface<Employee> {

	@Override
	public void create(Employee employee) {
		
		IRI type = store.getValueFactory().createIRI(Store.getDefaultNs(), "Employee");
		IRI hasDepartment = store.getValueFactory().createIRI(Store.getDefaultNs(), "hasDepartment");
		IRI employeeId = store.getValueFactory().createIRI(Store.getDefaultNs(), "employeeId");
		
		Random rand = new Random();
		Integer  n = rand.nextInt(50) + 1;
		String id = employee.getEmployeeId() != null ? employee.getEmployeeId() : n.toString();
		
		IRI subject = store.getValueFactory().createIRI(Store.getDefaultNs(), id);
		Literal label = store.getValueFactory().createLiteral(employee.getName());
		Literal idLiteral = store.getValueFactory().createLiteral(id);
		
		try (RepositoryConnection conn = store.getRepository().getConnection()) {
			conn.add(subject, RDF.TYPE, FOAF.PERSON);
			conn.add(subject, RDF.TYPE, type);
			conn.add(subject, RDFS.LABEL, label);
			conn.add(subject, employeeId, idLiteral);
			if (employee.getDepartmentId() != null)
			{
				conn.add(subject, hasDepartment, store.getValueFactory().createIRI(Store.getDefaultNs(), employee.getDepartmentId()));
			}
			if (employee.getDateOfBirth() != null)
			{
				conn.add(subject, FOAF.BIRTHDAY, store.getValueFactory().createLiteral(employee.getDateOfBirth()));
			}
		}

	}

	@Override
	public void delete(Employee employee) {
		try (RepositoryConnection conn = store.getRepository().getConnection()) {
			String queryString = 
					"PREFIX dp: <"+Store.getDefaultNs() +"> \n" + 
					"delete {?s ?p ?o}\n" + 
					"WHERE {\n" + 
					" ?s dp:employeeId ?id ; \n" +
					" ?p ?o ;\n" +
					" .\n" +
					"}";
	    	Update update = conn.prepareUpdate(QueryLanguage.SPARQL, queryString);
	    	update.setBinding("id", store.getValueFactory().createLiteral(employee.getEmployeeId()));
	    	update.execute();
		}
	}

	public Employee get(Employee employee) {
		Employee emp = new Employee();
		
		try (RepositoryConnection conn = store.getRepository().getConnection()) {
			String queryString = 
					"PREFIX dp: <"+Store.getDefaultNs() +"> \n" + 
					"PREFIX foaf: <http://xmlns.com/foaf/0.1/> \n" + 
					"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n" + 
					"SELECT ?id ?name ?dob ?department\n" + 
					"WHERE {\n" + 
					" ?s a dp:Employee ; \n" +
					" dp:employeeId ?id ; \n" +
					" rdfs:label ?name ; \n" +
					" foaf:birthday ?dob ; \n" +
					" .\n" +
					" OPTIONAL{?s dp:hasDepartment/dp:departmentId ?department .} \n" +
					"}";
	    	TupleQuery tupleQuery = conn.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
	    	tupleQuery.setBinding("id", store.getValueFactory().createLiteral(employee.getEmployeeId()));
	    	
	    	try (TupleQueryResult result = tupleQuery.evaluate()) 
	    	{
	    		while (result.hasNext()) 
	    		{  
	    			
	    			BindingSet bindingSet = result.next();
	    			
	    			Value id = bindingSet.getValue("id");
	    			Value name = bindingSet.getValue("name");
	    			Value dob = bindingSet.getValue("dob");
	    			Value department = bindingSet.getValue("department");
	    			
	    			emp.setEmployeeId(id.stringValue());
	    			emp.setName(name.stringValue());
	    			emp.setDateOfBirth(dob.stringValue());
	    			
	    			if (department  != null) 	    			
		    			emp.setDepartmentId(department.stringValue());
	    		}
	    	}
		}
		
		return emp;
	}

	@Override
	public void update(Employee employee) {
		// TODO Auto-generated method stub

	}

	@Override
	public Employee search(Employee subject) {
		// TODO Auto-generated method stub
		return null;
	}


}
