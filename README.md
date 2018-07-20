# dpR_tmt

dpR_tmt is a REST API implemented as an in-memory RDF Store using RDF4J. The code illustrates how to wire Inversion of Control to Data Access Objects implementing RDF4J, as well as exposing REST resources as JAX-RS as the user interface. This is a Maven project, configured to run with the Maven Jetty plugin, which also runs the integration test phase during the build. The Jetty plugin can also run the project and expose it's HTTP services on port 8080.

## Requirements:

Implement a REST API​ to create. update, delete and read data for an employee and their
department. We want to store below data
For employee we want to store their employeeId, name, dateOfBirth and departmentId.
For department we want to store departmentId and departmentName.

**Your implementation should cover below use cases**

1. I want to create a department with above details
2. I want to create an employee record with above details
3. I want to create new employee and later on assign a departmentId to an employee
4. I want to update deteOfBirth of an employee
5. I want to delete employee data
6. I want to search employee details by their name
7. I want to search departmentName for a given employee name


**Technical requirements:**

1. Store all the data in memory
2. Data should be stored as RDF internally
3. You can use Apache Jena or RDF4J for in memory repository
4. Search should be implemented using SPARQL
5. There should be proper unit tests and integration tests
6. Keep your design simple and clean
7. Create your repo on Github and do reguler commits
8. Provide README to explain how to run your code

## Running the application:
The application build framework is maven, configured to run with the Jetty Servlet Container plugin. By running
the command below, the build will compile the code, run Unit, and integration tests, and finally it will start 
Jetty on port 8080. This will allow you to run the app with your HTTP Client of choice. 
>**mvn clean install jetty:run**

*Below is the list of rest endpoints satisfying the requirements described above, you can run them manually using any standard HTTP client,
or you can [download Postman](https://www.getpostman.com/apps) and load the test collection stored in [dpR_tmt.postman_collection.json](https://github.com/migueldemelo/dpR_tmt/blob/master/src/test/resources/dpR_tmt.postman_collection.json).*

## REST Endpoints:

#### create a department:
>**PUT** http://localhost:8080/resources/department

**body**

    {
    	"departmentId": "1235",
    	"departmentName": "SomeDepartment"
    }


#### department details
>**GET** http://localhost:8080/resources/department/1235


#### create employee 1:
>**PUT** http://localhost:8080/resources/employee

**body**

    {
    	"employeeId": "1235",
    	"name": "employeeName",
    	"dateOfBirth": "1971-10-05",
    	"departmentId": "1235"
    }


#### employee 1 details:
>**GET** http://localhost:8080/resources/employee/1235


#### update employee 1 date of birth
>**POST** http://localhost:8080/resources/employee/1235/updateDob?dob=1971-11-05


#### create employee 2
>**PUT** http://localhost:8080/resources/employee

**body**

    {
    	"employeeId": "1238",
    	"name": "employeeName",
    	"dateOfBirth": "1971-10-05"
    }


#### employee 2 details
>**GET** http://localhost:8080/resources/employee/1238


#### add employee 2 to a department
>**POST** http://localhost:8080/resources/employee/1238/addDepartment?departmentId=1235


#### search employee by name:
>**GET** http://localhost:8080/resources/employee/search?name=employeeName


#### search department by employee name:
>**GET** http://localhost:8080/resources/department/search?employeeName=employeeName


#### delete employee 1:
>**DELETE** http://localhost:8080/resources/employee/1235
