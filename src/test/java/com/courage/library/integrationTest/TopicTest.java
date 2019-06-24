package com.courage.library.integrationTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

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

	@Test
	public void testCreateTopic() {
		Topic topic = TopicFactory.instance();

		ResponseEntity<String> response = Helper.createTopic(port, topic);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.CREATED);
		assertThat(JsonPath.parse(response.getBody()).read("name").toString()).isEqualTo(topic.getName());
	}

	@Test
	public void testUpdateTopic() {
		Topic topic = TopicFactory.instance();
		ResponseEntity<String> createTopicResponse = Helper.createTopic(port, topic);
		topic.setId(JsonPath.parse(createTopicResponse.getBody()).read("id").toString());
		String url = baseUrl + "/" + topic.getId();
		HttpEntity entity = new HttpEntity(JsonConverter.toJSON(topic), httpHeaders);

		ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.PUT, entity,
				String.class);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		assertThat(JsonPath.parse(response.getBody()).read("name").toString()).isEqualTo(topic.getName());
	}

	@Test
	public void testUpdateNonExistingTopic() {
		Topic topic = TopicFactory.instance();
		String url = baseUrl + "/" + topic.getId();
		HttpEntity entity = new HttpEntity(JsonConverter.toJSON(topic), httpHeaders);

		ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.PUT, entity,
				String.class);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.NOT_FOUND);
	}

	@Test
	public void testInValidUpdateTopic() {
		Topic topic = TopicFactory.instance();
		String url = baseUrl + "/" + UUID.randomUUID().toString();
		HttpEntity entity = new HttpEntity(JsonConverter.toJSON(topic), httpHeaders);

		ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.PUT, entity,
				String.class);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	public void testGetTopics() {
		TopicFactory.instances().forEach(topic -> Helper.createTopic(port, topic));
		HttpEntity entity = new HttpEntity(null, httpHeaders);

		ResponseEntity<String> response = this.restTemplate.exchange(baseUrl, HttpMethod.GET, entity,
				String.class);
		String numberOfElts = JsonPath.parse(response.getBody()).read(".numberOfElements").toString();

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		assertThat(new Integer(numberOfElts.substring(1, numberOfElts.length()-1)))
			.isGreaterThan(3);
	}

	@Test
	public void testGetTopicsWithValidPageParam() {
		TopicFactory.instances().forEach(topic -> Helper.createTopic(port, topic));
		HttpEntity entity = new HttpEntity(null, httpHeaders);
		String url = baseUrl + "?page=2&size=3";

		ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.GET, entity,
				String.class);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		assertThat(JsonPath.parse(response.getBody()).read(".size").toString())
				.isEqualTo("[3]");
	}

	@Test
	public void testGetTopicsWithInValidPageParam() {
		HttpEntity entity = new HttpEntity(null, httpHeaders);
		String url = baseUrl + "?page=-2&size=3";

		ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.GET, entity,
				String.class);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	public void testGetTopic() {
		Topic topic = TopicFactory.instance();
		ResponseEntity<String> createTopicResponse = Helper.createTopic(port, topic);
		String topicId = JsonPath.parse(createTopicResponse.getBody()).read("id").toString();
		String url = baseUrl + "/" + topicId;
		HttpEntity entity = new HttpEntity(null, httpHeaders);

		ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.GET, entity,
				String.class);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		assertThat(JsonPath.parse(response.getBody()).read("name").toString()).isEqualTo(topic.getName());
	}

	@Test
	public void testDeleteTopic() {
		Topic topic = TopicFactory.instance();
		ResponseEntity<String> createTopicResponse = Helper.createTopic(port, topic);
		String topicId = JsonPath.parse(createTopicResponse.getBody()).read("id").toString();
		String url = baseUrl + "/" + topicId;
		HttpEntity entity = new HttpEntity(null, httpHeaders);

		ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.DELETE, entity,
				String.class);
		ResponseEntity<String> getTopicRes = this.restTemplate.exchange(url, HttpMethod.GET, entity,
				String.class);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.NO_CONTENT);
		assertThat(getTopicRes.getStatusCode()).isEqualByComparingTo(HttpStatus.NOT_FOUND);
	}
}
