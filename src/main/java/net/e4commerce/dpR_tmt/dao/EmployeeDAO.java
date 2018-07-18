package net.e4commerce.dpR_tmt.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Singleton;
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
import org.eclipse.rdf4j.repository.sail.SailRepository;

import net.e4commerce.dpR_tmt.model.Employee;

@Singleton
public class EmployeeDAO extends DataAccessObject implements DataAccessInterface<Employee> {
 
	@Inject
	public EmployeeDAO(Store store) {
		super(store);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void create(Employee employee) {
		
		String id = employee.getEmployeeId();
		
		IRI type = store.getValueFactory().createIRI(Store.getDefaultNs(), "Employee");
		IRI hasDepartment = store.getValueFactory().createIRI(Store.getDefaultNs(), "hasDepartment");
		IRI employeeId = store.getValueFactory().createIRI(Store.getDefaultNs(), "employeeId");
		IRI subject = store.getValueFactory().createIRI(Store.getDefaultNs(), "employee_"+id);
		
		Literal nameLiteral = store.getValueFactory().createLiteral(employee.getName());
		Literal idLiteral = store.getValueFactory().createLiteral(id);
		SailRepository repository = store.getRepository();
		try (RepositoryConnection conn = repository.getConnection()) {
			conn.add(subject, RDF.TYPE, FOAF.PERSON);
			conn.add(subject, RDF.TYPE, type);
			conn.add(subject, RDFS.LABEL, nameLiteral);
			conn.add(subject, employeeId, idLiteral);
			if (employee.getDepartmentId() != null)
			{
				conn.add(subject, hasDepartment, store.getValueFactory().createIRI(Store.getDefaultNs(), "department_"+employee.getDepartmentId()));
			}
			if (employee.getDateOfBirth() != null)
			{
				conn.add(subject, FOAF.BIRTHDAY, store.getValueFactory().createLiteral(employee.getDateOfBirth()));
			}
		}

	}

	@Override
	public void delete(String id) {
		try (RepositoryConnection conn = store.getRepository().getConnection()) {
			String queryString = 
					"PREFIX dp: <"+Store.getDefaultNs() +"> \n" + 
					"DELETE {?s ?p ?o}\n" + 
					"WHERE {\n" + 
					" ?s dp:employeeId ?id ; \n" +
					" ?p ?o ;\n" +
					" .\n" +
					"}";
	    	Update update = conn.prepareUpdate(QueryLanguage.SPARQL, queryString);
	    	update.setBinding("id", store.getValueFactory().createLiteral(id));
	    	update.execute();
		}
	}

	public Employee get(String id) throws Exception {
		Employee employee = new Employee();
		
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
	    	tupleQuery.setBinding("id", store.getValueFactory().createLiteral(id));
	    	
	    	try (TupleQueryResult result = tupleQuery.evaluate()) 
	    	{
	    		while (result.hasNext()) 
	    		{  
	    			BindingSet bindingSet = result.next();
	    			
	    			employee.setEmployeeId(bindingSet.getValue("id").stringValue());
	    			employee.setName(bindingSet.getValue("name").stringValue());
	    			employee.setDateOfBirth(bindingSet.getValue("dob").stringValue());
	    			if (bindingSet.getValue("department")  != null) 	    			
	    				employee.setDepartmentId(bindingSet.getValue("department").stringValue());
	    		}
	    	}
		}
		
		return employee;
	}

	@Override
	public void update(Employee employee) {
		if(employee.getDateOfBirth() != null) {
			updateDob(employee.getEmployeeId(), employee.getDateOfBirth());
		} else if(employee.getDepartmentId() != null) {
			updateDepartment(employee.getEmployeeId(), employee.getDepartmentId());
		}
	}
	
	private void updateDob(String employeeId, String dob) {
		try (RepositoryConnection conn = store.getRepository().getConnection()) {
			String queryString = 
					"PREFIX dp: <"+Store.getDefaultNs() +"> \n" + 
					"PREFIX foaf: <http://xmlns.com/foaf/0.1/> \n" + 
					"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n" + 
					"DELETE {?s foaf:birthday ?oldValue}\n" + 
					"INSERT {?s foaf:birthday ?newValue}\n" + 
					"WHERE {\n" + 
					" ?s a dp:Employee ; \n" +
					" dp:employeeId ?id ; \n" +
					" foaf:birthday ?oldValue ; \n" +
					" .\n" +
					"}";
	    	Update update = conn.prepareUpdate(QueryLanguage.SPARQL, queryString);
	    	update.setBinding("id", store.getValueFactory().createLiteral(employeeId));
	    	update.setBinding("newValue", store.getValueFactory().createLiteral(dob));
	    	update.execute();
		}
	}
	
	private void updateDepartment(String employeeId, String departmentId) {
		try (RepositoryConnection conn = store.getRepository().getConnection()) {
			String queryString = 
					"PREFIX dp: <"+Store.getDefaultNs() +"> \n" + 
					"PREFIX foaf: <http://xmlns.com/foaf/0.1/> \n" + 
					"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n" + 
					"INSERT {?s dp:hasDepartment ?department}\n" + 
					"WHERE {\n" + 
					" ?s a dp:Employee ; \n" +
					" dp:employeeId ?id ; \n" +
					" .\n" +
					"}";
			Update update = conn.prepareUpdate(QueryLanguage.SPARQL, queryString);
			update.setBinding("id", store.getValueFactory().createLiteral(employeeId));
			update.setBinding("department", store.getValueFactory().createIRI(Store.getDefaultNs(), "department_"+departmentId));
			update.execute();
		}
	}
	
	public List<Employee> search(String name) {
		List<Employee> employees = new ArrayList<Employee>();
		
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
	    	tupleQuery.setBinding("name", store.getValueFactory().createLiteral(name));
	    	
	    	try (TupleQueryResult result = tupleQuery.evaluate()) 
	    	{
	    		while (result.hasNext()) 
	    		{  
	    			Employee emp = new Employee();	
	    			
	    			BindingSet bindingSet = result.next();
	    			
	    			emp.setEmployeeId(bindingSet.getValue("id").stringValue());
	    			emp.setName(bindingSet.getValue("name").stringValue());
	    			emp.setDateOfBirth(bindingSet.getValue("dob").stringValue());
	    			
	    			if (bindingSet.getValue("department")  != null) 	    			
		    			emp.setDepartmentId(bindingSet.getValue("department").stringValue());
	    			
	    			employees.add(emp);
	    		}
	    	}
		}
		
		return employees;
	}
}
