package net.e4commerce.dpR_tmt.rest;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import net.e4commerce.dpR_tmt.dao.EmployeeDAO;
import net.e4commerce.dpR_tmt.model.Employee;

@Path("/employee")
@Singleton
public class EmployeeResource {
	
	@Inject EmployeeDAO dao;
	
	@GET
	@Path("{employeeId}")
	@Produces(MediaType.APPLICATION_JSON)
    public Employee get(@PathParam("employeeId") String id) {
		Employee employee = new Employee();
		employee.setEmployeeId(id);
		Employee emp = dao.get(employee);
        return emp;
    }

	@PUT
    public void set(@QueryParam("id") String id, @QueryParam("name") String name, @QueryParam("dob") String dob, @QueryParam("departmentId") String departmentId) {
		Employee employee = new Employee();
		employee.setEmployeeId(id);
		employee.setName(name);
		employee.setDateOfBirth(dob);
		employee.setDepartmentId(departmentId);
		dao.create(employee);
    }

	@DELETE
	@Path("{employeeId}")
    public void delete(@PathParam("employeeId") String id) {
		Employee employee = new Employee();
		employee.setEmployeeId(id);
		dao.delete(employee);
    }

	@POST
	@Path("{employeeId}/updateDob")
    public void updateDob(@PathParam("employeeId") String id, @QueryParam("dob") String dob) {
		Employee employee = new Employee();
		employee.setEmployeeId(id);
		employee.setDateOfBirth(dob);
		dao.update(employee);
    }

	@POST
	@Path("{employeeId}/addDepartment")
    public void updateDepartment(@PathParam("employeeId") String id, @QueryParam("departmentId") String departmentId) {
		Employee employee = new Employee();
		employee.setEmployeeId(id);
		employee.setDepartmentId(departmentId);
		dao.update(employee);
    }
	
	@GET
	@Path("search")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Employee> search(@QueryParam("name") String name) {
		Employee employee = new Employee();
		employee.setName(name);
		return dao.search(employee);
	}
}
