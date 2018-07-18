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

@Path("department")
@Singleton
public class DepartmentResource {
	
	private final DepartmentDAO dao;
	
	@Inject 
	public DepartmentResource(DepartmentDAO dao) {
		this.dao = dao;
	}
	
	@GET
	@Path("{departmentId}")
	@Produces(MediaType.APPLICATION_JSON)
    public Department get(@PathParam("departmentId") String id) throws Exception {
		return dao.get(id);
    }

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
    public void create(Department department) {
		dao.create(department);
    }
	
	@GET
	@Path("search")
	@Produces(MediaType.APPLICATION_JSON)
    public List<Department> search(@QueryParam("employeeName") String name) {
		return dao.search(name);
    }
}
