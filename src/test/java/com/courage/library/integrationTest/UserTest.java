package com.courage.library.integrationTest;

import static org.assertj.core.api.Assertions.assertThat;

import com.courage.library.factory.JsonConverter;
import com.courage.library.factory.TopicFactory;
import com.courage.library.factory.UserFactory;
import com.courage.library.model.Topic;
import com.courage.library.model.User;
import com.jayway.jsonpath.JsonPath;
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
		baseUrl += "/api/v1/topics";
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

}