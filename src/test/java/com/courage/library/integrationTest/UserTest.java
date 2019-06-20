package com.courage.library.integrationTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import com.courage.library.factory.JsonConverter;
import com.courage.library.factory.UserFactory;
import com.courage.library.model.User;
import com.jayway.jsonpath.JsonPath;
import org.apache.commons.lang3.RandomStringUtils;
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
public class UserTest {

	@LocalServerPort
	private Integer port;
	private String baseUrl = "http://localhost:";
	private TestRestTemplate restTemplate = new TestRestTemplate();
	private HttpHeaders httpHeaders = new HttpHeaders();

	@Before
	public void before() {
		baseUrl += port;
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.set("Authorization", "Bearer " + Authenticate.getAccessToken(baseUrl));
		baseUrl += "/api/v1/users";
	}

	private ResponseEntity<String> createUser(User user) {
		HttpEntity entity = new HttpEntity(JsonConverter.toJSON(user), httpHeaders);

		return this.restTemplate.exchange(baseUrl, HttpMethod.POST, entity, String.class);
	}

	@Test
	public void testCreateUser() {
		User user = UserFactory.instance();

		ResponseEntity<String> response = Helper.createUser(port, user);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.CREATED);
		assertThat(JsonPath.parse(response.getBody()).read("email").toString()).isEqualTo(user.getEmail());
	}

	@Test
	public void testUpdateUser() {
		User user = UserFactory.instance();
		ResponseEntity<String> createUserRes = Helper.createUser(port, user);
		String userId = JsonPath.parse(createUserRes.getBody()).read("id").toString();
		String url = baseUrl + "/" + userId;
		user.setId(userId);
		user.setFirstName(RandomStringUtils.random(10, true, false));
		HttpEntity entity = new HttpEntity(JsonConverter.toJSON(UserFactory.convertToDTO(user)), httpHeaders);

		ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.PUT, entity,
				String.class);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		assertThat(JsonPath.parse(response.getBody()).read("email").toString()).isEqualTo(user.getEmail());
	}

	@Test
	public void testInValidUpdateUser() {
		User user = UserFactory.instance();
		String url = baseUrl + "/" + UUID.randomUUID().toString();
		user.setFirstName(RandomStringUtils.random(10, true, false));
		HttpEntity entity = new HttpEntity(JsonConverter.toJSON(UserFactory.convertToDTO(user)), httpHeaders);

		ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.PUT, entity,
				String.class);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	public void testUpdateNonExistingUser() {
		User user = UserFactory.instance();
		String url = baseUrl + "/" + user.getId();
		user.setFirstName(RandomStringUtils.random(10, true, false));
		HttpEntity entity = new HttpEntity(JsonConverter.toJSON(UserFactory.convertToDTO(user)), httpHeaders);

		ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.PUT, entity,
				String.class);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.NOT_FOUND);
	}

	@Test
	public void testGetUser() {
		User user = UserFactory.instance();
		ResponseEntity<String> createUserRes = Helper.createUser(port, user);
		String userId = JsonPath.parse(createUserRes.getBody()).read("id").toString();
		String url = baseUrl + "/" + userId;
		HttpEntity entity = new HttpEntity(null, httpHeaders);

		ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.GET, entity,
				String.class);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		assertThat(JsonPath.parse(response.getBody()).read("email").toString()).isEqualTo(user.getEmail());
	}

	@Test
	public void testGetUsers() {
		UserFactory.instances().forEach(user -> Helper.createUser(port, user));
		HttpEntity entity = new HttpEntity(null, httpHeaders);

		ResponseEntity<String> response = this.restTemplate.exchange(baseUrl, HttpMethod.GET, entity,
				String.class);
		String numberOfElts = JsonPath.parse(response.getBody()).read(".numberOfElements").toString();

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		assertThat(new Integer(numberOfElts.substring(1, numberOfElts.length()-1)))
				.isGreaterThan(3);
	}

	@Test
	public void testGetUsersWithValidPageParams() {
		UserFactory.instances().forEach(user -> Helper.createUser(port, user));
		HttpEntity entity = new HttpEntity(null, httpHeaders);
		String url = baseUrl + "?page=1&size=3";

		ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.GET, entity,
				String.class);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		assertThat(JsonPath.parse(response.getBody()).read(".size").toString())
				.isEqualTo("[3]");
	}

	@Test
	public void testGetUsersWithInValidPageParams() {
		HttpEntity entity = new HttpEntity(null, httpHeaders);
		String url = baseUrl + "?page=-1&size=3";

		ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.GET, entity,
				String.class);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.BAD_REQUEST);
	}
}
