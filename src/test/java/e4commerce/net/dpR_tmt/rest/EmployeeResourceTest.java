package e4commerce.net.dpR_tmt.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import net.e4commerce.dpR_tmt.dao.EmployeeDAO;
import net.e4commerce.dpR_tmt.model.Employee;
import net.e4commerce.dpR_tmt.rest.EmployeeResource;

@RunWith(PowerMockRunner.class)
@PrepareForTest(EmployeeResource.class)
public class EmployeeResourceTest {
	
	@Mock
	private EmployeeDAO dao;
	
	@Mock
	private Employee employee;
	
	@Mock
	private ArrayList<Employee> employees;
	
	@InjectMocks
	private EmployeeResource resource;

	@Test
	public void getTest() throws Exception {
		final String id = "1235";
		when(dao.get(id)).thenReturn(employee);
		Employee emp = resource.get(id);
		assertThat(emp).isEqualTo(employee);
		verify(dao).get(id);
	}
	
	@Test
	public void createTest() throws Exception {
		doNothing().when(dao).create(employee);
		resource.create(employee);
		verify(dao).create(employee);
	}
	
	@Test
	public void deleteTest() throws Exception {
		final String id = "1235";
		doNothing().when(dao).delete(id);
		resource.delete(id);
		verify(dao).delete(id);
	}

	@Test
	public void updateDobTest() throws Exception {
		final String id = "1235";
		final String dob = "1971-11-05";
		PowerMockito.whenNew(Employee.class).withNoArguments().thenReturn(employee);
		doNothing().when(employee).setEmployeeId(id);
		doNothing().when(employee).setDateOfBirth(dob);
		doNothing().when(dao).update(employee);
		resource.updateDob(id, dob);
		verify(employee).setEmployeeId(id);
		verify(employee).setDateOfBirth(dob);
		verify(dao).update(employee);
	}
	
	@Test
	public void updateDepartmentTest() throws Exception {
		final String id = "1235";
		final String departmentId = "1235";
		PowerMockito.whenNew(Employee.class).withNoArguments().thenReturn(employee);
		doNothing().when(dao).update(employee);
		resource.addDepartment(id, departmentId);
		verify(employee).setEmployeeId(id);
		verify(employee).setDepartmentId(departmentId);
		verify(dao).update(employee);
	}

	@Test
	public void searchTest() throws Exception {
		final String name = "name";
		when(dao.search(name)).thenReturn(employees);
		ArrayList<Employee> emp = (ArrayList<Employee>) resource.search(name);
		assertThat(emp).isEqualTo(employees);
		verify(dao).search(name);
	}
}
