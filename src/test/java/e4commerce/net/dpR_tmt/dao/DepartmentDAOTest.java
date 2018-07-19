package e4commerce.net.dpR_tmt.dao;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.awt.List;
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
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import net.e4commerce.dpR_tmt.dao.DepartmentDAO;
import net.e4commerce.dpR_tmt.model.Department;

@RunWith(MockitoJUnitRunner.class)
public class DepartmentDAOTest {

	@Mock
	private ValueFactory valueFactory;

	@Mock
	RepositoryConnection connection;

	@Mock
	private Literal literal;

	@Mock
	private Department department;

	@Mock
	private TupleQuery tupleQuery;

	@Mock
	private BindingSet bindingSet;

	@Mock
	private TupleQueryResult result;

	@Mock
	private Value value;

	@InjectMocks
	private DepartmentDAO dao;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void createTest() {
		final String departmentName = "name";
		final String departmentId = "id";

		when(department.getDepartmentName()).thenReturn(departmentName);
		when(department.getDepartmentId()).thenReturn(departmentId);

		dao.create(department);

		verify(department).getDepartmentName();
		verify(department, times(2)).getDepartmentId();

		verify(valueFactory, times(2)).createLiteral(anyString());
		verify(connection, times(4)).add(any(IRI.class), any(IRI.class), any(IRI.class));
	}

	@Test
	public void getTest() throws Exception {
		final String departmentId = "id";

		when(connection.prepareTupleQuery(any(QueryLanguage.class), anyString())).thenReturn(tupleQuery);
		when(valueFactory.createLiteral(departmentId)).thenReturn(literal);
		doNothing().when(tupleQuery).setBinding("id", literal);
		when(tupleQuery.evaluate()).thenReturn(result);

		when(result.hasNext()).thenReturn(true);
		when(result.next()).thenReturn(bindingSet);
		when(bindingSet.getValue("id")).thenReturn(value);
		when(bindingSet.getValue("name")).thenReturn(value);

		@SuppressWarnings("unused")
		Department dep = dao.get(departmentId);

		verify(connection).prepareTupleQuery(any(QueryLanguage.class), anyString());
		verify(valueFactory).createLiteral(departmentId);
		verify(tupleQuery).setBinding("id", literal);
		verify(tupleQuery).evaluate();
		verify(result).hasNext();
		verify(bindingSet).getValue("id");
		verify(bindingSet).getValue("name");
	}

	@Test
	public void getNoRecordTest() throws Exception {
		boolean thrown = false;

		final String departmentId = "id";

		when(connection.prepareTupleQuery(any(QueryLanguage.class), anyString())).thenReturn(tupleQuery);
		when(valueFactory.createLiteral(departmentId)).thenReturn(literal);
		doNothing().when(tupleQuery).setBinding("id", literal);
		when(tupleQuery.evaluate()).thenReturn(result);
		when(result.hasNext()).thenReturn(false);
		when(result.next()).thenReturn(bindingSet);
		when(bindingSet.getValue("id")).thenReturn(value);
		when(bindingSet.getValue("name")).thenReturn(value);

		try {
			@SuppressWarnings("unused")
			Department dep = dao.get(departmentId);
		} catch (Exception e) {
			thrown = true;
		}
		verify(connection).prepareTupleQuery(any(QueryLanguage.class), anyString());
		verify(valueFactory).createLiteral(departmentId);
		verify(tupleQuery).setBinding("id", literal);
		verify(tupleQuery).evaluate();
		verify(result).hasNext();

		assertTrue(thrown);
	}
	
	@Test
	public void searchTest() throws Exception {
		final String employeeName = "name";
		final String field = "employeeName";
		
		when(connection.prepareTupleQuery(any(QueryLanguage.class), anyString())).thenReturn(tupleQuery);
		when(valueFactory.createLiteral(employeeName)).thenReturn(literal);
		doNothing().when(tupleQuery).setBinding("id", literal);
		when(tupleQuery.evaluate()).thenReturn(result);

		when(result.hasNext()).thenReturn(true).thenReturn(false);
		when(result.next()).thenReturn(bindingSet);
		when(bindingSet.getValue("id")).thenReturn(value);
		when(bindingSet.getValue("name")).thenReturn(value);
		
		@SuppressWarnings("unused")
		ArrayList<Department> departments = (ArrayList<Department>) dao.searchEmployeeDepartment(employeeName);
		
		verify(connection).prepareTupleQuery(any(QueryLanguage.class), anyString());
		verify(valueFactory).createLiteral(employeeName);
		verify(tupleQuery).setBinding(field, literal);
		verify(tupleQuery).evaluate();
		verify(result, times(2)).hasNext();
		verify(bindingSet).getValue("id");
		verify(bindingSet).getValue("name");
	}
	
	@Test
	public void delete() {
		boolean thrown = false;
		try {
			dao.delete("xx");
		} catch (NotImplementedException e) {
			thrown = true;
		}
		assertTrue(thrown);
	}
	
	@Test
	public void update() {
		boolean thrown = false;
		try {
			dao.update(department);
		} catch (NotImplementedException e) {
			thrown = true;
		}
		assertTrue(thrown);
	}
}
