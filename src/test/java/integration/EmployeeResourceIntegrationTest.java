package integration;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EmployeeResourceIntegrationTest {
	private HttpResponse employee1Response;
	
	@Before
	public void setUp() throws Exception {
		HttpPut departmentRequest = new HttpPut("http://localhost:8080/resources/department");
		Header departmentHeader = new BasicHeader("Content-type", "application/json");
		departmentRequest.addHeader(departmentHeader);
		departmentRequest.setEntity(new StringEntity("{ \"departmentId\": \"1235\", \"departmentName\": \"SomeDepartment\" }", "utf-8"));
		HttpResponse departmentResponse = HttpClientBuilder.create().build().execute( departmentRequest );
		assertEquals(HttpStatus.SC_NO_CONTENT, departmentResponse.getStatusLine().getStatusCode());
		
		HttpPut employeeRequest = new HttpPut("http://localhost:8080/resources/employee");
		Header employeeHeader = new BasicHeader("Content-type", "application/json");
		employeeRequest.addHeader(employeeHeader);
		employeeRequest.setEntity(new StringEntity("{ \"employeeId\": \"1235\", \"name\": \"employeeName\", \"dateOfBirth\": \"1971-10-05\", \"departmentId\": \"1235\" }", "utf-8"));
		employee1Response = HttpClientBuilder.create().build().execute( employeeRequest );
	}
	
	@Test
	public void createEmployee() throws ClientProtocolException, IOException {
		assertEquals(HttpStatus.SC_NO_CONTENT, employee1Response.getStatusLine().getStatusCode());
		
		HttpGet request = new HttpGet("http://localhost:8080/resources/employee/1235");
		HttpUriRequest request1 = request;
		HttpResponse response = HttpClientBuilder.create().build().execute(request1, (HttpContext) null);
		HttpEntity entity = response.getEntity();
		String responseStr = EntityUtils.toString(entity);
		assertEquals("{\"dateOfBirth\":\"1971-10-05\",\"departmentId\":\"1235\",\"employeeId\":\"1235\",\"name\":\"employeeName\"}", responseStr);
	}
	
	@Test
	public void getEmployee() throws ClientProtocolException, IOException {
		HttpGet request = new HttpGet("http://localhost:8080/resources/employee/1235");
		HttpUriRequest request1 = request;
		HttpResponse response = HttpClientBuilder.create().build().execute(request1, (HttpContext) null);
		HttpEntity entity = response.getEntity();
		String responseStr = EntityUtils.toString(entity);
		assertEquals("{\"dateOfBirth\":\"1971-10-05\",\"departmentId\":\"1235\",\"employeeId\":\"1235\",\"name\":\"employeeName\"}", responseStr);
		assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
	}

	@Test
	public void updateEmployeeDateOfBirth() throws ClientProtocolException, IOException {
		HttpPost request = new HttpPost("http://localhost:8080/resources/employee/1235/updateDob?dob=1971-11-05");
		HttpResponse response = HttpClientBuilder.create().build().execute( request );
		assertEquals(HttpStatus.SC_NO_CONTENT, response.getStatusLine().getStatusCode());
		
		HttpGet getRequest = new HttpGet("http://localhost:8080/resources/employee/1235");
		HttpUriRequest request1 = getRequest;
		HttpResponse getResponse = HttpClientBuilder.create().build().execute(request1, (HttpContext) null);
		HttpEntity entity = getResponse.getEntity();
		String responseStr = EntityUtils.toString(entity);
		assertEquals("{\"dateOfBirth\":\"1971-11-05\",\"departmentId\":\"1235\",\"employeeId\":\"1235\",\"name\":\"employeeName\"}", responseStr);
		assertEquals(HttpStatus.SC_OK, getResponse.getStatusLine().getStatusCode());
	}
	
	@Test
	public void addDepartmentToEmployee() throws ClientProtocolException, IOException {
		HttpPut employeeRequest = new HttpPut("http://localhost:8080/resources/employee");
		Header employeeHeader = new BasicHeader("Content-type", "application/json");
		employeeRequest.addHeader(employeeHeader);
		employeeRequest.setEntity(new StringEntity("{ \"employeeId\": \"1238\", \"name\": \"employeeName\", \"dateOfBirth\": \"1971-10-05\" }", "utf-8"));
		employee1Response = HttpClientBuilder.create().build().execute( employeeRequest );
		assertEquals(HttpStatus.SC_NO_CONTENT, employee1Response.getStatusLine().getStatusCode());
		
		HttpGet getRequest = new HttpGet("http://localhost:8080/resources/employee/1238");
		HttpUriRequest request1 = getRequest;
		HttpResponse getResponse = HttpClientBuilder.create().build().execute(request1, (HttpContext) null);
		HttpEntity entity = getResponse.getEntity();
		String responseStr = EntityUtils.toString(entity);
		assertEquals("{\"dateOfBirth\":\"1971-10-05\",\"employeeId\":\"1238\",\"name\":\"employeeName\"}", responseStr);
		assertEquals(HttpStatus.SC_OK, getResponse.getStatusLine().getStatusCode());
		
		HttpPost request = new HttpPost("http://localhost:8080/resources/employee/1238/addDepartment?departmentId=1235");
		HttpResponse response = HttpClientBuilder.create().build().execute( request );
		assertEquals(HttpStatus.SC_NO_CONTENT, response.getStatusLine().getStatusCode());

		HttpGet finalRequest = new HttpGet("http://localhost:8080/resources/employee/1238");
		HttpUriRequest finalRequest1 = finalRequest;
		HttpResponse finalGetResponse = HttpClientBuilder.create().build().execute(finalRequest1, (HttpContext) null);
		HttpEntity finalEntity = finalGetResponse.getEntity();
		String finalResponseStr = EntityUtils.toString(finalEntity);
		assertEquals("{\"dateOfBirth\":\"1971-10-05\",\"departmentId\":\"1235\",\"employeeId\":\"1238\",\"name\":\"employeeName\"}", finalResponseStr);
		assertEquals(HttpStatus.SC_OK, finalGetResponse.getStatusLine().getStatusCode());
	}
	
	@Test
	public void searchEmployeeByName() throws ClientProtocolException, IOException {
		HttpGet getRequest = new HttpGet("http://localhost:8080/resources/employee/search?name=employeeName");
		HttpUriRequest request1 = getRequest;
		HttpResponse getResponse = HttpClientBuilder.create().build().execute(request1, (HttpContext) null);
		HttpEntity entity = getResponse.getEntity();
		String responseStr = EntityUtils.toString(entity);
		assertEquals("[{\"dateOfBirth\":\"1971-10-05\",\"departmentId\":\"1235\",\"employeeId\":\"1235\",\"name\":\"employeeName\"}]", responseStr);
		assertEquals(HttpStatus.SC_OK, getResponse.getStatusLine().getStatusCode());
	}
	
	@Test
	public void deleteEmployee() throws ClientProtocolException, IOException {
		HttpDelete request = new HttpDelete("http://localhost:8080/resources/employee/1235");
		HttpResponse response = HttpClientBuilder.create().build().execute( request );
		assertEquals(HttpStatus.SC_NO_CONTENT, response.getStatusLine().getStatusCode());
		
		HttpUriRequest getRequest = new HttpGet("http://localhost:8080/resources/employee/1235");
		HttpUriRequest request1 = getRequest;
		HttpResponse getResponse = HttpClientBuilder.create().build().execute(request1, (HttpContext) null);
		HttpEntity entity = getResponse.getEntity();
		String responseStr = EntityUtils.toString(entity);
		assertEquals("{}", responseStr);
		assertEquals(HttpStatus.SC_OK, getResponse.getStatusLine().getStatusCode());
	}
	
	@After
    public final void tearDown() throws ClientProtocolException, IOException 
	{ 
		HttpDelete request = new HttpDelete("http://localhost:8080/resources/employee/1235");
		HttpClientBuilder.create().build().execute(request, (HttpContext) null);
		
		HttpDelete request2 = new HttpDelete("http://localhost:8080/resources/employee/1238");
		HttpClientBuilder.create().build().execute(request2, (HttpContext) null);
	}
}