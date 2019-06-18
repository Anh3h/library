package com.courage.library.integrationTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import com.courage.library.factory.BookFactory;
import com.courage.library.factory.JsonConverter;
import com.courage.library.factory.TopicFactory;
import com.courage.library.model.Book;
import com.courage.library.model.Topic;
import com.courage.library.model.dto.BookDTO;
import com.google.api.client.json.Json;
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
public class BookTest {

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
		baseUrl += "/api/v1/books";
	}
	@Test
	public void testCreateBook() {
		Book book = BookFactory.instance();

		ResponseEntity<String> response = Helper.createBook(port, book);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.CREATED);
		assertThat(JsonPath.parse(response.getBody()).read("title").toString())
				.isEqualTo(book.getTitle());
	}

	@Test
	public void testUpdateBook() {
		Book book = BookFactory.instance();
		ResponseEntity<String> createBookResponse = Helper.createBook(port, book);
		book.setId(JsonPath.parse(createBookResponse.getBody()).read("id").toString());
		String url = baseUrl + "/" + book.getId();
		BookDTO bookDTO = BookFactory.convertToDTO(book);
		HttpEntity entity = new HttpEntity(JsonConverter.toJSON(bookDTO), httpHeaders);

		ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.PUT,
				entity, String.class);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		assertThat(JsonPath.parse(response.getBody()).read("title").toString())
				.isEqualTo(book.getTitle());
	}

	@Test
	public void testUpdateNonExistingBook() {
		Book book = BookFactory.instance();
		String url = baseUrl + "/" + book.getId();
		BookDTO bookDTO = BookFactory.convertToDTO(book);
		HttpEntity entity = new HttpEntity(JsonConverter.toJSON(bookDTO), httpHeaders);

		ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.PUT,
				entity, String.class);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.NOT_FOUND);
	}

	@Test
	public void testInValidUpdateBook() {
		Book book = BookFactory.instance();
		String url = baseUrl + "/" + UUID.randomUUID();
		BookDTO bookDTO = BookFactory.convertToDTO(book);
		HttpEntity entity = new HttpEntity(JsonConverter.toJSON(bookDTO), httpHeaders);

		ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.PUT,
				entity, String.class);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	public void testGetBook() {
		Book book = BookFactory.instance();
		ResponseEntity<String> createBookResponse = Helper.createBook(port, book);
		book.setId(JsonPath.parse(createBookResponse.getBody()).read("id").toString());
		String url = baseUrl + "/" + book.getId();
		HttpEntity entity = new HttpEntity(null, httpHeaders);

		ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.GET,
				entity, String.class);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		assertThat(JsonPath.parse(response.getBody()).read("title").toString())
				.isEqualTo(book.getTitle());
	}

	@Test
	public void testGetBooks() {
		BookFactory.instances().forEach(book -> Helper.createBook(port, book));
		HttpEntity entity = new HttpEntity(null, httpHeaders);

		ResponseEntity<String> response = this.restTemplate.exchange(baseUrl, HttpMethod.GET,
				entity, String.class);
		String numberOfElts = JsonPath.parse(response.getBody()).read(".numberOfElements").toString();

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		assertThat(new Integer(numberOfElts.substring(1, numberOfElts.length()-1)))
			.isGreaterThan(3);
	}

	@Test
	public void testGetBooksWithValidPageParams() {
		BookFactory.instances().forEach(book -> Helper.createBook(port, book));
		HttpEntity entity = new HttpEntity(null, httpHeaders);
		String url = baseUrl + "?page=1&size=3";

		ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.GET,
				entity, String.class);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		assertThat(JsonPath.parse(response.getBody()).read(".size").toString())
			.isEqualTo("[3]");
	}

	@Test
	public void testGetBooksWithInValidPageParams() {
		BookFactory.instances().forEach(book -> Helper.createBook(port, book));
		HttpEntity entity = new HttpEntity(null, httpHeaders);
		String url = baseUrl + "?page=0&size=3";

		ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.GET,
				entity, String.class);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.BAD_REQUEST);
	}
}
