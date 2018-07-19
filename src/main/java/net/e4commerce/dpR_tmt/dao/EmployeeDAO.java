package net.e4commerce.dpR_tmt.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Singleton;
import javax.inject.Inject;

import org.apache.commons.lang3.NotImplementedException;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.ValueFactory;
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

	private final IRI type;
	private final IRI hasDepartment;
	private final IRI employeeId;

	@Inject
	public EmployeeDAO(ValueFactory valueFactory, RepositoryConnection connectionection) {
		super(valueFactory, connectionection);
		type = valueFactory.createIRI(Store.getDefaultNs(), "Employee");
		hasDepartment = valueFactory.createIRI(Store.getDefaultNs(), "hasDepartment");
		employeeId = valueFactory.createIRI(Store.getDefaultNs(), "employeeId");
	}

	@Override
	public void create(Employee employee) {

		IRI subject = valueFactory.createIRI(Store.getDefaultNs(), "employee_" + employee.getEmployeeId());

		Literal nameLiteral = valueFactory.createLiteral(employee.getName());
		Literal idLiteral = valueFactory.createLiteral(employee.getEmployeeId());

		connection.add(subject, RDF.TYPE, FOAF.PERSON);
		connection.add(subject, RDF.TYPE, type);
		connection.add(subject, RDFS.LABEL, nameLiteral);
		connection.add(subject, employeeId, idLiteral);
		connection.add(subject, FOAF.BIRTHDAY, valueFactory.createLiteral(employee.getDateOfBirth()));

		if (employee.getDepartmentId() != null)
			connection.add(subject, hasDepartment,
					valueFactory.createIRI(Store.getDefaultNs(), "department_" + employee.getDepartmentId()));
	}

	@Override
	public void delete(String id) {
		String queryString = 
				  "PREFIX dp: <" + Store.getDefaultNs() + "> \n" 
				+ "DELETE {?s ?p ?o}\n" + "WHERE {\n"
				+ " ?s dp:employeeId ?id ; \n" 
				+ " ?p ?o ;\n" 
				+ " .\n" 
				+ "}";
		Update update = connection.prepareUpdate(QueryLanguage.SPARQL, queryString);
		update.setBinding("id", valueFactory.createLiteral(id));
		update.execute();
	}

	public Employee get(String id) throws Exception {
		Employee employee = new Employee();
		String queryString = 
				  "PREFIX dp: <" + Store.getDefaultNs() + "> \n"
				+ "PREFIX foaf: <http://xmlns.com/foaf/0.1/> \n"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n" 
				+ "SELECT ?id ?name ?dob ?department\n"
				+ "WHERE {\n" + " ?s a dp:Employee ; \n" 
				+ " dp:employeeId ?id ; \n" 
				+ " rdfs:label ?name ; \n"
				+ " foaf:birthday ?dob ; \n" 
				+ " .\n"
				+ " OPTIONAL{?s dp:hasDepartment/dp:departmentId ?department .} \n" 
				+ "}";
		TupleQuery tupleQuery = connection.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
		tupleQuery.setBinding("id", valueFactory.createLiteral(id));

		try (TupleQueryResult result = tupleQuery.evaluate()) {
			while (result.hasNext()) {
				BindingSet bindingSet = result.next();

				employee.setEmployeeId(bindingSet.getValue("id").stringValue());
				employee.setName(bindingSet.getValue("name").stringValue());
				employee.setDateOfBirth(bindingSet.getValue("dob").stringValue());
				if (bindingSet.getValue("department") != null)
					employee.setDepartmentId(bindingSet.getValue("department").stringValue());
			}
		}

		return employee;
	}

	@Override
	public void update(Employee employee) {
		if (employee.getDateOfBirth() != null) {
			updateDob(employee.getEmployeeId(), employee.getDateOfBirth());
		} else if (employee.getDepartmentId() != null) {
			updateDepartment(employee.getEmployeeId(), employee.getDepartmentId());
		} else {
			throw new NotImplementedException("not implemented");
		}
	}

	private void updateDob(String employeeId, String dob) {
		String queryString = 
				  "PREFIX dp: <" + Store.getDefaultNs() + "> \n"
				+ "PREFIX foaf: <http://xmlns.com/foaf/0.1/> \n"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n" 
				+ "DELETE {?s foaf:birthday ?oldValue}\n"
				+ "INSERT {?s foaf:birthday ?newValue}\n" + "WHERE {\n" 
				+ " ?s a dp:Employee ; \n"
				+ " dp:employeeId ?id ; \n" + " foaf:birthday ?oldValue ; \n" 
				+ " .\n" 
				+ "}";
		Update update = connection.prepareUpdate(QueryLanguage.SPARQL, queryString);
		update.setBinding("id", valueFactory.createLiteral(employeeId));
		update.setBinding("newValue", valueFactory.createLiteral(dob));
		update.execute();
	}

	private void updateDepartment(String employeeId, String departmentId) {
		String queryString = 
				  "PREFIX dp: <" + Store.getDefaultNs() + "> \n"
				+ "PREFIX foaf: <http://xmlns.com/foaf/0.1/> \n"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n"
				+ "INSERT {?s dp:hasDepartment ?department}\n" + "WHERE {\n" 
				+ " ?s a dp:Employee ; \n"
				+ " dp:employeeId ?id ; \n" 
				+ " .\n" 
				+ "}";
		Update update = connection.prepareUpdate(QueryLanguage.SPARQL, queryString);
		update.setBinding("id", valueFactory.createLiteral(employeeId));
		update.setBinding("department", valueFactory.createIRI(Store.getDefaultNs(), "department_" + departmentId));
		update.execute();
	}

	public List<Employee> search(String name) {
		List<Employee> employees = new ArrayList<Employee>();

		String queryString = 
				  "PREFIX dp: <" + Store.getDefaultNs() + "> \n"
				+ "PREFIX foaf: <http://xmlns.com/foaf/0.1/> \n"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n" 
				+ "SELECT ?id ?name ?dob ?department\n"
				+ "WHERE {\n" + " ?s a dp:Employee ; \n" + " dp:employeeId ?id ; \n" 
				+ " rdfs:label ?name ; \n"
				+ " foaf:birthday ?dob ; \n" 
				+ " .\n"
				+ " OPTIONAL{?s dp:hasDepartment/dp:departmentId ?department .} \n" 
				+ "}";
		TupleQuery tupleQuery = connection.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
		tupleQuery.setBinding("name", valueFactory.createLiteral(name));

		try (TupleQueryResult result = tupleQuery.evaluate()) {
			while (result.hasNext()) {
				Employee emp = new Employee();

				BindingSet bindingSet = result.next();

				emp.setEmployeeId(bindingSet.getValue("id").stringValue());
				emp.setName(bindingSet.getValue("name").stringValue());
				emp.setDateOfBirth(bindingSet.getValue("dob").stringValue());

				if (bindingSet.getValue("department") != null)
					emp.setDepartmentId(bindingSet.getValue("department").stringValue());

				employees.add(emp);
			}
		}

		return employees;
	}
}
