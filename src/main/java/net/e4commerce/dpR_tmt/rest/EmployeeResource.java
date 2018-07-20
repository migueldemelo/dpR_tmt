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

/**
 * The Class EmployeeResource provides a REST endpoint for an Employee.
 */
@Path("/employee")
@Singleton
public class EmployeeResource {
	
	/** The data access object used to persist data to a store. */
	private final EmployeeDAO dao;
	
	/**
	 * Instantiates a new employee resource.
	 *
	 * @param dao the data access object used to persist data to a store.
	 */
	@Inject 
	public EmployeeResource(EmployeeDAO dao) {
		this.dao = dao;
	}
	
	/**
	 * Gets an Employee resource.
	 *
	 * @param id the Employee id
	 * @return an Employee resource
	 * @throws Exception is thrown when no department is found with that id 
	 * TODO: return an HTTP not found exception
	 */
	@GET
	@Path("{employeeId}")
	@Produces(MediaType.APPLICATION_JSON)
    public Employee get(@PathParam("employeeId") String id) throws Exception {
        return dao.get(id);
    }

	/**
	 * Creates an Employee resource.
	 *
	 * @param employee a new employee
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
    public void create(Employee employee) {
		dao.create(employee);
    }

	/**
	 * Delete Employee.
	 *
	 * @param id the id of the Employee to be deleted
	 */
	@DELETE
	@Path("{employeeId}")
    public void delete(@PathParam("employeeId") String id) {
		dao.delete(id);
    }

	/**
	 * Updates the date of birth of an employee by replacing the date of birth 
	 * percisted at creation time.
	 *
	 * @param id the Employee id
	 * @param dob the date of birth
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
	 * Updates the employee record with a new department.
	 *
	 * @param id the employee id
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
	 * Searches for employees by name.
	 *
	 * @param name the name
	 * @return a list of Employee
	 */
	@GET
	@Path("search")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Employee> search(@QueryParam("name") String name) {
		return dao.search(name);
	}
}
