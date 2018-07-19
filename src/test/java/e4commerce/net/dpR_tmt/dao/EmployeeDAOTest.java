package e4commerce.net.dpR_tmt.dao;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.apache.commons.lang3.NotImplementedException;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.query.Update;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import net.e4commerce.dpR_tmt.dao.EmployeeDAO;
import net.e4commerce.dpR_tmt.model.Department;
import net.e4commerce.dpR_tmt.model.Employee;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeDAOTest {
	
	@Mock
	private ValueFactory valueFactory;

	@Mock
	RepositoryConnection connection;

	@Mock
	private Literal literal;
	
	@Mock
	private IRI iri;

	@Mock
	private Employee employee;

	@Mock
	private TupleQuery tupleQuery;

	@Mock
	private BindingSet bindingSet;

	@Mock
	private TupleQueryResult result;

	@Mock
	private Value value;
	
	@Mock
	private Update update;

	@InjectMocks
	private EmployeeDAO dao;
	
	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void createTest() {
		
		final String name = "name";
		final String id = "id";
		final String dob = "dob";
		String departmentId = "departmentId";

		when(employee.getName()).thenReturn(name);
		when(employee.getEmployeeId()).thenReturn(id);
		when(employee.getDateOfBirth()).thenReturn(dob);
		when(employee.getDepartmentId()).thenReturn(departmentId);

		dao.create(employee);

		verify(employee).getName();
		verify(employee, times(2)).getEmployeeId();
		verify(employee, times(1)).getName();
		verify(employee, times(1)).getDateOfBirth();
		verify(employee, times(2)).getDepartmentId();

		verify(valueFactory, times(3)).createLiteral(anyString());
		verify(connection, times(6)).add(any(IRI.class), any(IRI.class), any(IRI.class));
	}

	@Test
	public void createNoDepartmentTest() {
		
		final String name = "name";
		final String id = "id";
		final String dob = "dob";
		
		when(employee.getName()).thenReturn(name);
		when(employee.getEmployeeId()).thenReturn(id);
		when(employee.getDateOfBirth()).thenReturn(dob);
		when(employee.getDepartmentId()).thenReturn(null);
		
		dao.create(employee);
		
		verify(employee).getName();
		verify(employee, times(2)).getEmployeeId();
		verify(employee, times(1)).getName();
		verify(employee, times(1)).getDateOfBirth();
		verify(employee, times(1)).getDepartmentId();
		
		verify(valueFactory, times(3)).createLiteral(anyString());
		verify(connection, times(5)).add(any(IRI.class), any(IRI.class), any(IRI.class));
	}


	@Test
	public void deleteTest() {
		final String id = "id";

		when(connection.prepareUpdate(any(QueryLanguage.class), anyString())).thenReturn(update);
		when(valueFactory.createLiteral(id)).thenReturn(literal);
		doNothing().when(update).setBinding("id", literal);
		doNothing().when(update).execute();
		
		dao.delete(id);

		verify(connection).prepareUpdate(any(QueryLanguage.class), anyString());
		verify(valueFactory, times(1)).createLiteral(anyString());
		verify(update).setBinding("id", literal);
		verify(update).execute();
	}
	

	@Test
	public void getTest() throws Exception {
		
		final String id = "id";

		when(connection.prepareTupleQuery(any(QueryLanguage.class), anyString())).thenReturn(tupleQuery);
		when(valueFactory.createLiteral(id)).thenReturn(literal);
		doNothing().when(tupleQuery).setBinding("id", literal);
		when(tupleQuery.evaluate()).thenReturn(result);

		when(result.hasNext()).thenReturn(true).thenReturn(false);
		when(result.next()).thenReturn(bindingSet);
		when(bindingSet.getValue("id")).thenReturn(value);
		when(bindingSet.getValue("name")).thenReturn(value);
		when(bindingSet.getValue("dob")).thenReturn(value);
		when(bindingSet.getValue("department")).thenReturn(value);

		@SuppressWarnings("unused")
		Employee dep = dao.get(id);

		verify(connection).prepareTupleQuery(any(QueryLanguage.class), anyString());
		verify(valueFactory).createLiteral(id);
		verify(tupleQuery).setBinding("id", literal);
		verify(tupleQuery).evaluate();
		verify(result, times(2)).hasNext();
		verify(bindingSet).getValue("id");
		verify(bindingSet).getValue("name");
		verify(bindingSet).getValue("dob");
		verify(bindingSet, times(2)).getValue("department");
	}
	
	@Test
	public void getNoDepartmentTest() throws Exception {
		
		final String id = "id";
		
		when(connection.prepareTupleQuery(any(QueryLanguage.class), anyString())).thenReturn(tupleQuery);
		when(valueFactory.createLiteral(id)).thenReturn(literal);
		doNothing().when(tupleQuery).setBinding("id", literal);
		when(tupleQuery.evaluate()).thenReturn(result);
		
		when(result.hasNext()).thenReturn(true).thenReturn(false);
		when(result.next()).thenReturn(bindingSet);
		when(bindingSet.getValue("id")).thenReturn(value);
		when(bindingSet.getValue("name")).thenReturn(value);
		when(bindingSet.getValue("dob")).thenReturn(value);
		when(bindingSet.getValue("department")).thenReturn(null);
		
		@SuppressWarnings("unused")
		Employee dep = dao.get(id);
		
		verify(connection).prepareTupleQuery(any(QueryLanguage.class), anyString());
		verify(valueFactory).createLiteral(id);
		verify(tupleQuery).setBinding("id", literal);
		verify(tupleQuery).evaluate();
		verify(result, times(2)).hasNext();
		verify(bindingSet).getValue("id");
		verify(bindingSet).getValue("name");
		verify(bindingSet).getValue("dob");
		verify(bindingSet).getValue("department");
	}

	@Test
	public void updateDobTest() throws Exception {
		final String dob = "dob";
		final String id = "id";
		
		when(employee.getEmployeeId()).thenReturn(id);
		when(employee.getDateOfBirth()).thenReturn(dob);
		when(connection.prepareUpdate(any(QueryLanguage.class), anyString())).thenReturn(update);
		when(valueFactory.createLiteral(id)).thenReturn(literal);
		when(valueFactory.createLiteral(dob)).thenReturn(literal);
		doNothing().when(update).setBinding("id", literal);
		doNothing().when(update).setBinding("newValue", literal);
		doNothing().when(update).execute();
		
		dao.update(employee);
		
		verify(connection).prepareUpdate(any(QueryLanguage.class), anyString());
		verify(valueFactory, times(2)).createLiteral(anyString());
		verify(update).setBinding("id", literal);
		verify(update).setBinding("newValue", literal);
		verify(update).execute();
	}
	
	@Test
	public void updateDepartmentTest() throws Exception {
		final String department = "department";
		final String id = "id";
		
		when(employee.getEmployeeId()).thenReturn(id);
		when(employee.getDepartmentId()).thenReturn(department);
		when(connection.prepareUpdate(any(QueryLanguage.class), anyString())).thenReturn(update);
		when(valueFactory.createLiteral(id)).thenReturn(literal);
		when(valueFactory.createIRI(anyString(), anyString())).thenReturn(iri);
		doNothing().when(update).setBinding("id", literal);
		doNothing().when(update).setBinding("department", iri);
		doNothing().when(update).execute();
		
		dao.update(employee);
		
		verify(connection).prepareUpdate(any(QueryLanguage.class), anyString());
		verify(valueFactory, times(1)).createLiteral(anyString());
		verify(update).setBinding("id", literal);
		verify(update).setBinding("department", iri);
		verify(update).execute();
	}
	
	@Test
	public void updateDepartmentNullTest() throws Exception {
		final String id = "id";
		boolean thrown = false;
		
		when(employee.getEmployeeId()).thenReturn(id);
		when(employee.getDepartmentId()).thenReturn(null);
		when(connection.prepareUpdate(any(QueryLanguage.class), anyString())).thenReturn(update);
		when(valueFactory.createLiteral(id)).thenReturn(literal);
		when(valueFactory.createIRI(anyString(), anyString())).thenReturn(iri);
		doNothing().when(update).setBinding("id", literal);
		doNothing().when(update).setBinding("department", iri);
		doNothing().when(update).execute();
		
		try	{
			dao.update(employee);
		} catch (NotImplementedException e) {
			thrown = true;
		}
		
		assertTrue(thrown);
	}
	
	@Test
	public void searchTest() throws Exception {
		final String employeeName = "name";
		final String field = "employeeName";
		
		when(connection.prepareTupleQuery(any(QueryLanguage.class), anyString())).thenReturn(tupleQuery);
		when(valueFactory.createLiteral(employeeName)).thenReturn(literal);
		doNothing().when(tupleQuery).setBinding("name", literal);
		when(tupleQuery.evaluate()).thenReturn(result);

		when(result.hasNext()).thenReturn(true).thenReturn(false);
		when(result.next()).thenReturn(bindingSet);
		when(bindingSet.getValue("id")).thenReturn(value);
		when(bindingSet.getValue("name")).thenReturn(value);
		when(bindingSet.getValue("dob")).thenReturn(value);
		when(bindingSet.getValue("department")).thenReturn(value);
		
		@SuppressWarnings("unused")
		ArrayList<Employee> emp = (ArrayList<Employee>) dao.search(employeeName);
		
		verify(connection).prepareTupleQuery(any(QueryLanguage.class), anyString());
		verify(valueFactory).createLiteral(employeeName);
		verify(tupleQuery).setBinding("name", literal);
		verify(tupleQuery).evaluate();
		verify(result, times(2)).hasNext();
		verify(bindingSet).getValue("id");
		verify(bindingSet).getValue("name");
		verify(bindingSet).getValue("dob");
		verify(bindingSet, times(2)).getValue("department");
	}
	
	@Test
	public void searchNoDepartmentTest() throws Exception {
		final String employeeName = "name";
		final String field = "employeeName";
		
		when(connection.prepareTupleQuery(any(QueryLanguage.class), anyString())).thenReturn(tupleQuery);
		when(valueFactory.createLiteral(employeeName)).thenReturn(literal);
		doNothing().when(tupleQuery).setBinding("name", literal);
		when(tupleQuery.evaluate()).thenReturn(result);
		
		when(result.hasNext()).thenReturn(true).thenReturn(false);
		when(result.next()).thenReturn(bindingSet);
		when(bindingSet.getValue("id")).thenReturn(value);
		when(bindingSet.getValue("name")).thenReturn(value);
		when(bindingSet.getValue("dob")).thenReturn(value);
		when(bindingSet.getValue("department")).thenReturn(null);
		
		@SuppressWarnings("unused")
		ArrayList<Employee> emp = (ArrayList<Employee>) dao.search(employeeName);
		
		verify(connection).prepareTupleQuery(any(QueryLanguage.class), anyString());
		verify(valueFactory).createLiteral(employeeName);
		verify(tupleQuery).setBinding("name", literal);
		verify(tupleQuery).evaluate();
		verify(result, times(2)).hasNext();
		verify(bindingSet).getValue("id");
		verify(bindingSet).getValue("name");
		verify(bindingSet).getValue("dob");
		verify(bindingSet).getValue("department");
	}
}
