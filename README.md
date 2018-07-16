# dpR_tmt

#Requirement:

Implement a REST API​ to create. update, delete and read data for an employee and their
department. We want to store below data
For employee we want to store their employeeId, name, dateOfBirth and departmentId.
For department we want to store departmentId and departmentName.

#Your implementation should cover below use cases

1. I want to create a department with above details
2. I want to create an employee record with above details
3. I want to create new employee and later on assign a departmentId to an employee
4. I want to update deteOfBirth of an employee
5. I want to delete employee data
6. I want to search employee details by their name
7. I want to search departmentName for a given employee name


#Technical requirements:

1. Store all the data in memory
2. Data should be stored as RDF internally
3. You can use Apache Jena or RDF4J for in memory repository
4. Search should be implemented using SPARQL
5. There should be proper unit tests and integration tests
6. Keep your design simple and clean
7. Create your repo on Github and do reguler commits
8. Provide README to explain how to run your code

#Usage:
clean install jetty:run

#create a department:
curl "http://localhost:8080/resources/department/create?id=1235&name=SomeDepartment"

#view department details:
curl "http://localhost:8080/resources/department/detail?id=1235"

#create an employee:
curl "http://localhost:8080/resources/employee/create?id=1235&name=employeeName&dob=1971-10-05&departmentId=1235"

#view employee details:
curl "http://localhost:8080/resources/employee/detail?id=1235"

#delete employee:
curl "http://localhost:8080/resources/employee/delete?id=1235"

#update employee DateOfBirth (not working):
curl "http://localhost:8080/resources/employee/updateDob?id=1235&dob=1971-11-05"

#update employee department (not implemented):
curl "http://localhost:8080/resources/employee/updateDepartment?id=1235&departmentId=1235"

#search employee by name (not implemented):
curl "http://localhost:8080/resources/employee/search?name=employeeName"

#search department by employee id (not implemented):
curl "http://localhost:8080/resources/department/search?employeeId=1235"