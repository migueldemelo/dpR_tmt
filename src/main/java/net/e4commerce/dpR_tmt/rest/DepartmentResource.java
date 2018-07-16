package net.e4commerce.dpR_tmt.rest;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import net.e4commerce.dpR_tmt.dao.DepartmentDAO;
import net.e4commerce.dpR_tmt.model.Department;
import net.e4commerce.dpR_tmt.model.Employee;

@Path("department")
@Singleton
public class DepartmentResource {
	
	@Inject DepartmentDAO dao;
	
	@GET
	@Path("detail")
	@Produces(MediaType.APPLICATION_JSON)
    public Department user(@QueryParam("id") String id) {
		Department department = new Department();
		department.setDepartmentId(id);
		Department dep = dao.get(department);
        return dep;
    }

	@GET
	@Path("create")
    public void create(@QueryParam("id") String id, @QueryParam("name") String name) {
		Department department = new Department();
		department.setDepartmentId(id);
		department.setDepartmentName(name);
		dao.create(department);
    }
	
	@GET
	@Path("search")
	@Produces(MediaType.APPLICATION_JSON)
    public List<Department> search(@QueryParam("employeeName") String name) {
		Employee employee = new Employee();
		employee.setName(name);
		return dao.search(employee);
    }
}
