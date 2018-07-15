package e4commerce.net.dpR_tmt;

import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import net.e4commerce.dpR_tmt.dao.EmployeeDAO;
import net.e4commerce.dpR_tmt.dao.Store;
import net.e4commerce.dpR_tmt.model.Employee;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeDAOTest {
	
	@InjectMocks
	Store store = new Store();
	
	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testCreate() {
		
		EmployeeDAO dao = mock(EmployeeDAO.class);
		Employee employee = mock(Employee.class);
		
		dao.create(employee);
		
	}


	@Test
	public void testGet() {
		
		EmployeeDAO dao = mock(EmployeeDAO.class);
		Employee employee = mock(Employee.class);
		
		Employee emp = dao.get(employee);
		
	}
}
