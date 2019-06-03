package com.courage.library.integrationTest;

import static org.assertj.core.api.Assertions.assertThat;

import com.courage.library.factory.JsonConverter;
import com.courage.library.factory.TopicFactory;
import com.courage.library.model.Topic;
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
public class TopicTest {

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

	private ResponseEntity<String> createTopic(Topic topic) {
		HttpEntity entity = new HttpEntity(JsonConverter.toJSON(topic), httpHeaders);

		return this.restTemplate.exchange(baseUrl, HttpMethod.POST, entity, String.class);
	}

	@Test
	public void testCreateTopic() {
		Topic topic = TopicFactory.instance();

		ResponseEntity<String> response = this.createTopic(topic);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.CREATED);
		assertThat(JsonPath.parse(response.getBody()).read("name").toString()).isEqualTo(topic.getName());
	}

	@Test
	public void testUpdateTopic() {
		Topic topic = TopicFactory.instance();
		ResponseEntity<String> createTopicResponse = this.createTopic(topic);
		topic.setId(JsonPath.parse(createTopicResponse.getBody()).read("id").toString());
		String url = baseUrl + "/" + topic.getId();
		HttpEntity entity = new HttpEntity(JsonConverter.toJSON(topic), httpHeaders);

		ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.PUT, entity,
				String.class);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		assertThat(JsonPath.parse(response.getBody()).read("name").toString()).isEqualTo(topic.getName());
	}

	@Test
	public void testInValidUpdateTopic() {
		Topic topic = TopicFactory.instance();
		String url = baseUrl + "/" + topic.getId();
		HttpEntity entity = new HttpEntity(JsonConverter.toJSON(topic), httpHeaders);

		ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.NOT_FOUND);
	}
}
