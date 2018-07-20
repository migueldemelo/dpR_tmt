package net.e4commerce.dpR_tmt.rest;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import net.e4commerce.dpR_tmt.dao.DepartmentDAO;
import net.e4commerce.dpR_tmt.model.Department;
import net.e4commerce.dpR_tmt.model.Employee;

/**
 * The Class DepartmentResource provides a REST endpoint for a Department.
 * 
 * @author      Miguel de Melo
 * @version     1.0
 * @since       1.0
 */
@Path("department")
@Singleton
public class DepartmentResource {
	
	/** The data access object used to persist data to a store. */
	private final DepartmentDAO dao;
	
	/**
	 * Instantiates a new department resource.
	 *
	 * @param dao the data access object used to persist data to a store.
	 */
	@Inject 
	public DepartmentResource(DepartmentDAO dao) {
		this.dao = dao;
	}
	
	/**
	 * Gets a Department resource.
	 *
	 * @param id the department id
	 * @return a Department resource
	 * @throws Exception is thrown when no department is found with that id 
	 * TODO: return an HTTP not found exception
	 */
	@GET
	@Path("{departmentId}")
	@Produces(MediaType.APPLICATION_JSON)
    public Department get(@PathParam("departmentId") String id) throws Exception {
		return dao.get(id);
    }

	/**
	 * Creates a Department resource.
	 *
	 * @param department a new department
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
    public void create(Department department) {
		dao.create(department);
    }
	
	/**
	 * Search for an employee's department by providing the employee's name.
	 * Produces a list of deparments an employee may belong to, or a department more than 
	 * one employee with the same name belong to.
	 *
	 * @param name the employee name
	 * @return a list of departments
	 */
	@GET
	@Path("search")
	@Produces(MediaType.APPLICATION_JSON)
    public List<Department> search(@QueryParam("employeeName") String name) {
		return dao.searchEmployeeDepartment(name);
    }
}
