/*
 * 
 */
package net.e4commerce.dpR_tmt.rest;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import net.e4commerce.dpR_tmt.dao.DepartmentDAO;
import net.e4commerce.dpR_tmt.dao.EmployeeDAO;
import net.e4commerce.dpR_tmt.model.Employee;

// TODO: Auto-generated Javadoc
/**
 * The Class EmployeeResource.
 */
@Path("/employee")
@Singleton
public class EmployeeResource {
	
	/** The dao. */
	private final EmployeeDAO dao;
	
	/**
	 * Instantiates a new employee resource.
	 *
	 * @param dao the dao
	 */
	@Inject 
	public EmployeeResource(EmployeeDAO dao) {
		this.dao = dao;
	}
	
	/**
	 * Gets the.
	 *
	 * @param id the id
	 * @return the employee
	 * @throws Exception the exception
	 */
	@GET
	@Path("{employeeId}")
	@Produces(MediaType.APPLICATION_JSON)
    public Employee get(@PathParam("employeeId") String id) throws Exception {
        return dao.get(id);
    }

	/**
	 * Creates the.
	 *
	 * @param employee the employee
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
    public void create(Employee employee) {
		dao.create(employee);
    }

	/**
	 * Delete.
	 *
	 * @param id the id
	 */
	@DELETE
	@Path("{employeeId}")
    public void delete(@PathParam("employeeId") String id) {
		dao.delete(id);
    }

	/**
	 * Update dob.
	 *
	 * @param id the id
	 * @param dob the dob
	 */
	@POST
	@Path("{employeeId}/updateDob")
    public void updateDob(@PathParam("employeeId") String id, @QueryParam("dob") String dob) {
		Employee employee = new Employee();
		employee.setEmployeeId(id);
		employee.setDateOfBirth(dob);
		dao.update(employee);
    }

	/**
	 * Adds the department.
	 *
	 * @param id the id
	 * @param departmentId the department id
	 */
	@POST
	@Path("{employeeId}/addDepartment")
    public void addDepartment(@PathParam("employeeId") String id, @QueryParam("departmentId") String departmentId) {
		Employee employee = new Employee();
		employee.setEmployeeId(id);
		employee.setDepartmentId(departmentId);
		dao.update(employee);
    }
	
	/**
	 * Search.
	 *
	 * @param name the name
	 * @return the list
	 */
	@GET
	@Path("search")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Employee> search(@QueryParam("name") String name) {
		return dao.search(name);
	}
}
