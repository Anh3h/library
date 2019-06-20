package com.courage.library.integrationTest;

import com.courage.library.factory.BookFactory;
import com.courage.library.factory.JsonConverter;
import com.courage.library.factory.TransactionFactory;
import com.courage.library.factory.UserFactory;
import com.courage.library.model.Book;
import com.courage.library.model.Role;
import com.courage.library.model.Topic;
import com.courage.library.model.Transaction;
import com.courage.library.model.User;
import com.courage.library.model.dto.BookDTO;
import com.courage.library.model.dto.TransactionDTO;
import com.courage.library.model.dto.UserDTO;
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

	public static ResponseEntity<String> createRole(Integer port, Role role) {
		String url = configUrl(port, "roles");
		HttpEntity entity = new HttpEntity(JsonConverter.toJSON(role), httpHeaders);

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

	public static ResponseEntity<String> createUser(Integer port, User user) {
		String url = configUrl(port, "users");
		ResponseEntity<String> res = Helper.createRole(port, user.getRole());
		String roleId = JsonPath.parse(res.getBody())
				.read("id").toString();
		user.getRole().setId(roleId);
		UserDTO userDTO = UserFactory.convertToDTO(user);
		HttpEntity entity = new HttpEntity(JsonConverter.toJSON(userDTO), httpHeaders);

		return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
	}

	public static ResponseEntity<String> createTransaction(Integer port, Transaction transaction) {
		String url = configUrl(port, "transactions");
		ResponseEntity<String> userCreateRes = Helper.createUser(port, transaction.getUser());
		String userId = JsonPath.parse(userCreateRes.getBody()).read("id").toString();
		transaction.getUser().setId(userId);
		ResponseEntity<String> bookCreateRes = Helper.createBook(port, transaction.getBook());
		String bookId = JsonPath.parse(bookCreateRes.getBody()).read("id").toString();
		transaction.getBook().setId(bookId);
		TransactionDTO transactionDTO = TransactionFactory.convertToDTO(transaction);
		HttpEntity entity = new HttpEntity(JsonConverter.toJSON(transactionDTO), httpHeaders);

		return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
	}

}
