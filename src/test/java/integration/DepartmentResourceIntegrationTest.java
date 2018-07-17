package integration;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.junit.Test;


public class DepartmentResourceIntegrationTest {
	
	@Test
	public void createDepartment() throws ClientProtocolException, IOException {
		HttpPut departmentRequest = new HttpPut("http://localhost:8080/resources/department");
		Header departmentHeader = new BasicHeader("Content-type", "application/json");
		departmentRequest.addHeader(departmentHeader);
		departmentRequest.setEntity(new StringEntity("{ \"departmentId\": \"1235\", \"departmentName\": \"SomeDepartment\" }", "utf-8"));
		HttpResponse departmentResponse = HttpClientBuilder.create().build().execute( departmentRequest );
		assertEquals(HttpStatus.SC_NO_CONTENT, departmentResponse.getStatusLine().getStatusCode());

		HttpGet request = new HttpGet("http://localhost:8080/resources/department/1235");
		HttpUriRequest request1 = request;
		HttpResponse response = HttpClientBuilder.create().build().execute(request1, (HttpContext) null);
		HttpEntity entity = response.getEntity();
		String responseStr = EntityUtils.toString(entity);
		assertEquals("{\"departmentId\":\"1235\",\"departmentName\":\"SomeDepartment\"}", responseStr);
		assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
	}
	
	@Test
	public void getDepartment() throws ClientProtocolException, IOException {
		HttpGet request = new HttpGet("http://localhost:8080/resources/department/1235");
		HttpUriRequest request1 = request;
		HttpResponse response = HttpClientBuilder.create().build().execute(request1, (HttpContext) null);
		HttpEntity entity = response.getEntity();
		String responseStr = EntityUtils.toString(entity);
		assertEquals("{\"departmentId\":\"1235\",\"departmentName\":\"SomeDepartment\"}", responseStr);
		assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
	}

	@Test
	public void searchDepartmentByEmployeeName() throws ClientProtocolException, IOException {
		HttpPut employeeRequest = new HttpPut("http://localhost:8080/resources/employee");
		Header employeeHeader = new BasicHeader("Content-type", "application/json");
		employeeRequest.addHeader(employeeHeader);
		employeeRequest.setEntity(new StringEntity("{ \"employeeId\": \"1235\", \"name\": \"employeeName\", \"dateOfBirth\": \"1971-10-05\", \"departmentId\": \"1235\" }", "utf-8"));
		HttpResponse employeeResponse = HttpClientBuilder.create().build().execute( employeeRequest );
		assertEquals(HttpStatus.SC_NO_CONTENT, employeeResponse.getStatusLine().getStatusCode());
		
		HttpGet request = new HttpGet("http://localhost:8080/resources/employee/1235");
		HttpUriRequest request1 = request;
		HttpResponse response = HttpClientBuilder.create().build().execute(request1, (HttpContext) null);
		HttpEntity entity = response.getEntity();
		String responseStr = EntityUtils.toString(entity);
		assertEquals("{\"dateOfBirth\":\"1971-10-05\",\"departmentId\":\"1235\",\"employeeId\":\"1235\",\"name\":\"employeeName\"}", responseStr);
		
		HttpGet searchRequest = new HttpGet("http://localhost:8080/resources/department/search?employeeName=employeeName");
		HttpUriRequest searchRequest1 = searchRequest;
		HttpResponse searchResponse = HttpClientBuilder.create().build().execute(searchRequest1, (HttpContext) null);
		HttpEntity searchEntity = searchResponse.getEntity();
		String searchResponseStr = EntityUtils.toString(searchEntity);
		assertEquals("[{\"departmentId\":\"1235\",\"departmentName\":\"SomeDepartment\"}]", searchResponseStr);
	}
}