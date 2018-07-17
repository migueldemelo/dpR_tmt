package e4commerce.net.dpR_tmt;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.repository.sail.SailRepositoryConnection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import net.e4commerce.dpR_tmt.dao.EmployeeDAO;
import net.e4commerce.dpR_tmt.dao.Store;
import net.e4commerce.dpR_tmt.model.Employee;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeDAOTest {
	
//	@InjectMocks
	@Mock
	Store store; 
//	= new Store();
	
	@Mock
	EmployeeDAO dao;
	
	@Mock
	Employee employee;
	
	@Mock
	SailRepositoryConnection connection;
	
	@Mock
	SailRepository repository;
	
	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testCreate() {
		
		doNothing().when(employee).setEmployeeId(isA(String.class));
		doNothing().when(employee).setName(isA(String.class));
		doNothing().when(employee).setDepartmentId(isA(String.class));
		doNothing().when(employee).setDateOfBirth(isA(String.class));
		
		when(store.getRepository()).thenReturn(repository);
		when(repository.getConnection()).thenReturn(connection);
		
		dao.create(employee);
		
//		verify(repository, times(1)).getConnection();
	}


	@Test
	public void testGet() {
		
		
		Employee emp = dao.get(employee);
		
	}
}
