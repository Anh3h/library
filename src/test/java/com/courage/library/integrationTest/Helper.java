package com.courage.library.integrationTest;

import com.courage.library.factory.BookFactory;
import com.courage.library.factory.JsonConverter;
import com.courage.library.model.Book;
import com.courage.library.model.Topic;
import com.courage.library.model.dto.BookDTO;
import com.jayway.jsonpath.JsonPath;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class Helper {

	private static TestRestTemplate restTemplate = new TestRestTemplate();
	private static HttpHeaders httpHeaders = new HttpHeaders();

	private static void setHttpHeaders(String url) {
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.set("Authorization", "Bearer " + Authenticate.getAccessToken(url));
	}

	public static String configUrl(Integer port, String resource) {
		String url = "http://localhost:" + port;
		setHttpHeaders(url);
		url += "/api/v1/" + resource;
		return url;
	}

	public static ResponseEntity<String> createTopic(Integer port, Topic topic) {
		String url = configUrl(port, "topics");
		HttpEntity entity = new HttpEntity(JsonConverter.toJSON(topic), httpHeaders);

		return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
	}

	public static ResponseEntity<String> createBook(Integer port, Book book) {
		String url = configUrl(port, "books");
		ResponseEntity<String> res = Helper.createTopic(port, book.getTopic());
		String topicId = JsonPath.parse(res.getBody())
				.read("id").toString();
		book.getTopic().setId(topicId);
		BookDTO bookDTO = BookFactory.convertToDTO(book);
		HttpEntity entity = new HttpEntity(JsonConverter.toJSON(bookDTO), httpHeaders);

		return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
	}

}
