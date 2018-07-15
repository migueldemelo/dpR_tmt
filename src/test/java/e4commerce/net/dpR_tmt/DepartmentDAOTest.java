package e4commerce.net.dpR_tmt;

import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import net.e4commerce.dpR_tmt.dao.DepartmentDAO;
import net.e4commerce.dpR_tmt.dao.Store;
import net.e4commerce.dpR_tmt.model.Department;
import net.e4commerce.dpR_tmt.model.Employee;

@RunWith(MockitoJUnitRunner.class)
public class DepartmentDAOTest {
	
	@InjectMocks
	Store store = new Store();
	
	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testCreate() {
		
		DepartmentDAO dao = mock(DepartmentDAO.class);
		Department department = mock(Department.class);
		
		dao.create(department);
		
	}

	@Test
	public void testGet() {
		
		DepartmentDAO dao = mock(DepartmentDAO.class);
		Department department = mock(Department.class);
		
		Department dep = dao.get(department);
		
	}
}
