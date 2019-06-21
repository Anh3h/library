package com.courage.library.integrationTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import com.courage.library.factory.JsonConverter;
import com.courage.library.factory.TransactionFactory;
import com.courage.library.model.Transaction;
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
public class TransactionTest {

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
		baseUrl += "/api/v1/transactions";
	}

	@Test
	public void testCreateTransaction() {
		Transaction transaction = TransactionFactory.instance();

		ResponseEntity<String> response = Helper.createTransaction(port, transaction);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.CREATED);
		assertThat(JsonPath.parse(response.getBody()).read("user.id").toString())
				.isEqualTo(transaction.getUser().getId());
		assertThat(JsonPath.parse(response.getBody()).read("book.id").toString())
				.isEqualTo(transaction.getBook().getId());
	}

	@Test
	public void testUpdateTransaction() {
		Transaction transaction = TransactionFactory.instance();
		ResponseEntity<String> createTransRes = Helper.createTransaction(port, transaction);
		String transId = JsonPath.parse(createTransRes.getBody()).read("id").toString();
		String url = baseUrl + "/" + transId;
		transaction.setId(transId);
		HttpEntity httpEntity = new HttpEntity(JsonConverter.toJSON(transaction), httpHeaders);

		ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.PUT, httpEntity,
				String.class);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		assertThat(JsonPath.parse(response.getBody()).read("user.id").toString())
				.isEqualTo(transaction.getUser().getId());
		assertThat(JsonPath.parse(response.getBody()).read("book.id").toString())
				.isEqualTo(transaction.getBook().getId());
	}

	@Test
	public void testInvaidUpdateTransaction() {
		Transaction transaction = TransactionFactory.instance();
		String url = baseUrl + "/" + UUID.randomUUID().toString();
		HttpEntity httpEntity = new HttpEntity(JsonConverter.toJSON(transaction), httpHeaders);

		ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.PUT, httpEntity,
				String.class);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	public void testUpdateNonExistingTransaction() {
		Transaction transaction = TransactionFactory.instance();
		String url = baseUrl + "/" + transaction.getId();
		HttpEntity httpEntity = new HttpEntity(JsonConverter.toJSON(transaction), httpHeaders);

		ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.PUT, httpEntity,
				String.class);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.NOT_FOUND);
	}

	@Test
	public void testGetTransaction() {
		Transaction transaction = TransactionFactory.instance();
		ResponseEntity<String> createTransRes = Helper.createTransaction(port, transaction);
		String transId = JsonPath.parse(createTransRes.getBody()).read("id").toString();
		String url = baseUrl + "/" + transId;
		HttpEntity httpEntity = new HttpEntity(JsonConverter.toJSON(null), httpHeaders);

		ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.GET, httpEntity,
				String.class);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		assertThat(JsonPath.parse(response.getBody()).read("user.id").toString())
				.isEqualTo(transaction.getUser().getId());
		assertThat(JsonPath.parse(response.getBody()).read("book.id").toString())
				.isEqualTo(transaction.getBook().getId());
	}

	@Test
	public void testGetTransactions() {
		 TransactionFactory.instances().forEach(trans -> Helper.createTransaction(port, trans));
		HttpEntity httpEntity = new HttpEntity(JsonConverter.toJSON(null), httpHeaders);

		ResponseEntity<String> response = this.restTemplate.exchange(baseUrl, HttpMethod.GET, httpEntity,
				String.class);
		String numberOfElts = JsonPath.parse(response.getBody()).read(".numberOfElements").toString();

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		assertThat(new Integer(numberOfElts.substring(1, numberOfElts.length()-1)))
				.isGreaterThan(3);
	}

	@Test
	public void testGetTransactionsWithValidPageParams() {
		TransactionFactory.instances().forEach(trans -> Helper.createTransaction(port, trans));
		HttpEntity httpEntity = new HttpEntity(JsonConverter.toJSON(null), httpHeaders);
		String url = baseUrl + "?page=1&size=3";

		ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.GET, httpEntity,
				String.class);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
		assertThat(JsonPath.parse(response.getBody()).read(".size").toString())
				.isEqualTo("[3]");
	}

	@Test
	public void testGetTransactionsWithInValidPageParams() {
		TransactionFactory.instances().forEach(trans -> Helper.createTransaction(port, trans));
		HttpEntity httpEntity = new HttpEntity(JsonConverter.toJSON(null), httpHeaders);
		String url = baseUrl + "?page=-1&size=3";

		ResponseEntity<String> response = this.restTemplate.exchange(url, HttpMethod.GET, httpEntity,
				String.class);

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.BAD_REQUEST);
	}
}
