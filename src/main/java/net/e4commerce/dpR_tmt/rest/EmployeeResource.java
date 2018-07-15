package net.e4commerce.dpR_tmt.rest;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import net.e4commerce.dpR_tmt.dao.EmployeeDAO;
import net.e4commerce.dpR_tmt.model.Employee;

@Path("employee")
@Singleton
//@Immediate
public class EmployeeResource {
	
	@Inject EmployeeDAO dao;
	
	@GET
	@Path("detail")
	@Produces(MediaType.APPLICATION_JSON)
    public Employee user(@QueryParam("id") String id) {
//		System.out.println("DETAIL");
		Employee employee = new Employee();
		employee.setEmployeeId(id);
		Employee emp = dao.get(employee);
        return emp;
    }

	@GET
	@Path("create")
    public void create(@QueryParam("id") String id, @QueryParam("name") String name, @QueryParam("dob") String dob, @QueryParam("departmentId") String departmentId) {
		Employee employee = new Employee();
		employee.setEmployeeId(id);
		employee.setName(name);
		employee.setDateOfBirth(dob);
		employee.setDepartmentId(departmentId);
//		System.out.println("CREATE");
		dao.create(employee);
    }

	@GET
	@Path("delete")
    public void create(@QueryParam("id") String id) {
		Employee employee = new Employee();
		employee.setEmployeeId(id);
//		System.out.println("CREATE");
		dao.delete(employee);
    }
}
