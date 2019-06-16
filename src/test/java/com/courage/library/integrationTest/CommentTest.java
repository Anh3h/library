package com.courage.library.integrationTest;

import static org.assertj.core.api.Assertions.assertThat;

import com.courage.library.factory.CommentFactory;
import com.courage.library.factory.JsonConverter;
import com.courage.library.model.Comment;
import com.courage.library.model.dto.CommentDTO;
import com.jayway.jsonpath.JsonPath;
import org.apache.http.HttpResponse;
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
public class CommentTest {

	@LocalServerPort
	private Integer port;
	private String baseUrl = "http://localhost:";
	private HttpHeaders httpHeaders = new HttpHeaders();
	private TestRestTemplate restTemplate = new TestRestTemplate();

	@Before
	public void before() {
		baseUrl += port;
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.set("Authorization", "Bearer " + Authenticate.getAccessToken(baseUrl));
		baseUrl += "/api/v1/comments";
	}

	@Test
	public void testCreateComment() {
		Comment comment = CommentFactory.instance();
		ResponseEntity<String> bookResponse = Helper.createBook(port, comment.getBook());
		ResponseEntity<String> userResponse = Helper.createUser(port, comment.getUser());
		comment.getBook().setId(JsonPath.parse(bookResponse.getBody()).read("id").toString());
		comment.getUser().setId(JsonPath.parse(userResponse.getBody()).read("id").toString());

		CommentDTO commentDTO = CommentFactory.convertToDTO(comment);
		HttpEntity entity = new HttpEntity(JsonConverter.toJSON(commentDTO), httpHeaders);

		ResponseEntity<String> response = this.restTemplate.exchange(baseUrl, HttpMethod.POST, entity,
				String.class);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.CREATED);
	}

}
