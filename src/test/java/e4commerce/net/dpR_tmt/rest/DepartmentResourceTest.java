package e4commerce.net.dpR_tmt.rest;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.awt.List;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import net.e4commerce.dpR_tmt.dao.DepartmentDAO;
import net.e4commerce.dpR_tmt.model.Department;
import net.e4commerce.dpR_tmt.model.Employee;
import net.e4commerce.dpR_tmt.rest.DepartmentResource;

@RunWith(MockitoJUnitRunner.class)
public class DepartmentResourceTest {

	@Mock
	private DepartmentDAO dao;
	
	@Mock
	private Department department;
	
	@Mock
	private ArrayList<Department> departments;
	
	@Mock
	private Employee employee;
	
	@InjectMocks
	private DepartmentResource resource;
	
	@Test
	public void getTest() throws Exception {
		final String id = "1235";
		when(dao.get(id)).thenReturn(department);
		Department dep = resource.get(id);
		assertThat(dep).isEqualTo(department);
		verify(dao).get(id);
	}
	
	@Test
	public void createTest() throws Exception {
		doNothing().when(dao).create(department);
		resource.create(department);
		verify(dao).create(department);
	}
	
	@Test
	public void searchTest() throws Exception {
		final String name = "name";
		when(dao.search(name)).thenReturn(departments);
		ArrayList<Department> dep = (ArrayList<Department>) resource.search(name);
		assertThat(dep).isEqualTo(departments);
		verify(dao).search(name);
	}
}
