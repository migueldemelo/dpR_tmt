/*
 * 
 */
package net.e4commerce.dpR_tmt.model;

// TODO: Auto-generated Javadoc
/**
 * The Class Employee.
 */
public class Employee {
	
	/** The employee id. */
	private String employeeId;
	
	/** The name. */
	private String name;
	
	/** The date of birth. */
	private String dateOfBirth;
	
	/** The department id. */
	private String departmentId;
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	
	/**
	 * Gets the employee id.
	 *
	 * @return the employee id
	 */
	public String getEmployeeId() {
		return employeeId;
	}
	
	/**
	 * Sets the employee id.
	 *
	 * @param employeeId the new employee id
	 */
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	
	/**
	 * Gets the date of birth.
	 *
	 * @return the date of birth
	 */
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	
	/**
	 * Sets the date of birth.
	 *
	 * @param dateOfBirth the new date of birth
	 */
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	/**
	 * Gets the department id.
	 *
	 * @return the department id
	 */
	public String getDepartmentId() {
		return departmentId;
	}
	
	/**
	 * Sets the department id.
	 *
	 * @param departmentId the new department id
	 */
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
}
