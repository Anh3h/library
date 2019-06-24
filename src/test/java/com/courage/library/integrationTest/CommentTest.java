package com.courage.library.integrationTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import com.courage.library.factory.CommentFactory;
import com.courage.library.factory.JsonConverter;
import com.courage.library.model.Comment;
import com.courage.library.model.dto.CommentDTO;
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

	private ResponseEntity<String> createComment(Comment comment) {
		ResponseEntity<String> bookResponse = Helper.createBook(port, comment.getBook());
		ResponseEntity<String> userResponse = Helper.createUser(port, comment.getUser());
		comment.getBook().setId(JsonPath.parse(bookResponse.getBody()).read("id").toString());
		comment.getUser().setId(JsonPath.parse(userResponse.getBody()).read("id").toString());

		CommentDTO commentDTO = CommentFactory.convertToDTO(comment);
		HttpEntity entity = new HttpEntity(JsonConverter.toJSON(commentDTO), httpHeaders);

		return this.restTemplate.exchange(baseUrl, HttpMethod.POST, entity,
				String.class);
	}

	@Test
	public void testCreateComment() {
		Comment comment = CommentFactory.instance();

		ResponseEntity<String> response = this.createComment(comment);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.CREATED);
		assertThat(JsonPath.parse(response.getBody()).read("text").toString())
				.isEqualTo(comment.getText());
		assertThat(JsonPath.parse(response.getBody()).read("user.id").toString())
				.isEqualTo(comment.getUser().getId());
		assertThat(JsonPath.parse(response.getBody()).read("book.id").toString())
				.isEqualTo(comment.getBook().getId());
	}

	@Test
	public void testUpdateComment() {
		Comment comment = CommentFactory.instance();
		ResponseEntity<String> createCommentRes = this.createComment(comment);
		comment.setId(JsonPath.parse(createCommentRes.getBody()).read("id").toString());
		comment.getUser().setId(JsonPath.parse(createCommentRes.getBody()).read("user.id").toString());
		comment.getBook().setId(JsonPath.parse(createCommentRes.getBody()).read("book.id").toString());
		CommentDTO commentDTO = CommentFactory.convertToDTO(comment);
		HttpEntity entity = new HttpEntity(JsonConverter.toJSON(commentDTO), httpHeaders);
		String url = baseUrl + "/" + comment.getId();

		ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.PUT, entity,
				String.class);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		assertThat(JsonPath.parse(response.getBody()).read("text").toString())
				.isEqualTo(comment.getText());
		assertThat(JsonPath.parse(response.getBody()).read("user.id").toString())
				.isEqualTo(comment.getUser().getId());
		assertThat(JsonPath.parse(response.getBody()).read("book.id").toString())
				.isEqualTo(comment.getBook().getId());
	}

	@Test
	public void testInValidUpdateComment() {
		Comment comment = CommentFactory.instance();
		HttpEntity<String> entity = new HttpEntity(JsonConverter.toJSON(comment), httpHeaders);
		String url = baseUrl + "/" + UUID.randomUUID().toString();

		ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.PUT, entity,
				String.class);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	public void testUpdateNotExistingComment() {
		Comment comment = CommentFactory.instance();
		HttpEntity<String> entity = new HttpEntity(JsonConverter.toJSON(comment), httpHeaders);
		String url = baseUrl + "/" + comment.getId();

		ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.PUT, entity,
				String.class);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.NOT_FOUND);
	}

	@Test
	public void testGetComment() {
		Comment comment = CommentFactory.instance();
		ResponseEntity<String> createCommentRes = this.createComment(comment);
		HttpEntity<String> entity = new HttpEntity(null, httpHeaders);
		String commentId = JsonPath.parse(createCommentRes.getBody()).read("id").toString();
		String url = baseUrl + "/" + commentId;

		ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.GET, entity,
				String.class);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		assertThat(JsonPath.parse(response.getBody()).read("text").toString())
				.isEqualTo(comment.getText());
		assertThat(JsonPath.parse(response.getBody()).read("user.id").toString())
				.isEqualTo(comment.getUser().getId());
		assertThat(JsonPath.parse(response.getBody()).read("book.id").toString())
				.isEqualTo(comment.getBook().getId());
	}

	@Test
	public void testGetComments() {
		CommentFactory.instances().forEach(commment -> this.createComment(commment));
		HttpEntity entity = new HttpEntity(null, httpHeaders);

		ResponseEntity<String> response = this.restTemplate.exchange(baseUrl, HttpMethod.GET, entity,
				String.class);
		String numberOfElts = JsonPath.parse(response.getBody()).read(".numberOfElements").toString();

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		assertThat(new Integer(numberOfElts.substring(1, numberOfElts.length()-1)))
				.isGreaterThan(3);
	}

	//TODO: Implement integration test /api/v1/comments?bookId=2
	/*
		Blocker Helper.createComment method assumes the books are different (new)
		but this method needs to create comments that share the same bookId account.
	*/

	@Test
	public void testGetCommentsWithValidPageParams() {
		CommentFactory.instances().forEach(commment -> this.createComment(commment));
		HttpEntity entity = new HttpEntity(null, httpHeaders);
		String url = baseUrl + "?page=1&size=3";

		ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.GET, entity,
				String.class);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		assertThat(JsonPath.parse(response.getBody()).read(".size").toString())
				.isEqualTo("[3]");
	}

	@Test
	public void testGetCommentsWithInValidPageParams() {
		HttpEntity entity = new HttpEntity(null, httpHeaders);
		String url = baseUrl + "?page=-1&size=3";

		ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.GET, entity,
				String.class);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	public void testDeleteComment() {
		Comment comment = CommentFactory.instance();
		ResponseEntity<String> createCommentRes = this.createComment(comment);
		String commentId = JsonPath.parse(createCommentRes.getBody()).read("id").toString();
		String url = baseUrl + "/" + commentId;
		HttpEntity entity = new HttpEntity(null, httpHeaders);

		ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.DELETE, entity,
				String.class);
		ResponseEntity<String> getCommentRes = this.restTemplate.exchange(url, HttpMethod.GET, entity,
				String.class);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.NO_CONTENT);
		assertThat(getCommentRes.getStatusCode()).isEqualByComparingTo(HttpStatus.NOT_FOUND);
	}
}
