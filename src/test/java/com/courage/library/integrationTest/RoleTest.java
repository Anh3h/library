package com.courage.library.integrationTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import com.courage.library.factory.JsonConverter;
import com.courage.library.factory.RoleFactory;
import com.courage.library.model.Role;
import com.jayway.jsonpath.JsonPath;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RoleTest {

	@LocalServerPort
	private Integer port;
	private String baseUrl = "http://localhost:";
	private TestRestTemplate restTemplate = new TestRestTemplate();
	private HttpHeaders httpHeaders = new HttpHeaders();

	@Before
	public void setup() {
		baseUrl += port;
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.set("Authorization", "Bearer " + Authenticate.getAccessToken(baseUrl));
		baseUrl += "/api/v1/roles";
	}

	private ResponseEntity<String> createRole(Role role) {
		HttpEntity entity = new HttpEntity(JsonConverter.toJSON(role), httpHeaders);

		 return this.restTemplate.exchange(baseUrl, HttpMethod.POST, entity, String.class);
	}

	@Test
	public void testCreateRole() {
		Role role = RoleFactory.instance();
		ResponseEntity<String> response = this.createRole(role);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.CREATED);
		assertThat(JsonPath.parse(response.getBody()).read("name")
				.toString()).isEqualTo(role.getName());
	}

	@Test
	public void testUpdateRole() {
		Role role = RoleFactory.instance();
		ResponseEntity<String> createRoleResponse = this.createRole(role);
		String roleId = JsonPath.parse(createRoleResponse.getBody()).read("id");
		String url = baseUrl + "/" + roleId;
		role.setName(RandomStringUtils.random(10, true, true));
		role.setId(roleId);
		HttpEntity entity = new HttpEntity(JsonConverter.toJSON(role), httpHeaders);

		ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.PUT,
				entity, String.class);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		assertThat(JsonPath.parse(response.getBody()).read("name").toString())
				.isEqualTo(role.getName());

	}

	@Test
	public void testInValidUpdateRole() {
		Role role = RoleFactory.instance();
		String roleId = UUID.randomUUID().toString();
		String url = baseUrl + "/" + roleId;
		HttpEntity entity = new HttpEntity(JsonConverter.toJSON(role), httpHeaders);

		ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.PUT,
				entity, String.class);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.BAD_REQUEST);

	}

	@Test
	public void testGetExistingRole() {
		Role role = RoleFactory.instance();
		ResponseEntity<String> createRoleResponse = this.createRole(role);
		String roleId = JsonPath.parse(createRoleResponse.getBody()).read("id");
		String url = baseUrl + "/" + roleId;
		HttpEntity entity = new HttpEntity(null, httpHeaders);

		ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.GET,
				entity, String.class);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		assertThat(response.getBody()).isNotBlank();
		assertThat(JsonPath.parse(response.getBody()).read("name").toString()).isEqualTo(role.getName());
	}

	@Test
	public void testGetNonExistingRole() {
		String roleId = UUID.randomUUID().toString();
		String url = baseUrl + "/" + roleId;
		HttpEntity entity = new HttpEntity(null, httpHeaders);

		ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.GET,
				entity, String.class);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.NOT_FOUND);
	}

}
