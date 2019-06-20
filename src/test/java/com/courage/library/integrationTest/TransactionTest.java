package com.courage.library.integrationTest;

import static org.assertj.core.api.Assertions.assertThat;

import com.courage.library.factory.TransactionFactory;
import com.courage.library.model.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
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

		assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
	}
}
