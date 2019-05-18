package com.courage.library.unitTest.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.courage.library.factory.JsonConverter;
import com.courage.library.factory.TransactionFactory;
import com.courage.library.model.Transaction;
import com.courage.library.model.dto.TransactionDTO;
import com.courage.library.service.command.TransactionCommand;
import com.courage.library.service.query.TransactionQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(value = TransactionController.class, secure = false)
public class TransactionController {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TransactionCommand transactionCommand;

	@MockBean
	private TransactionQuery transactionQuery;

	@Test
	private void createTransactionRequest_returnsCreatedTransaction() throws Exception {
		Transaction transaction = TransactionFactory.instance();
		TransactionDTO transactionDTO = TransactionFactory.convertToDTO(transaction);
		given(this.transactionCommand.createTransaction(transactionDTO)).willReturn(transaction);

		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/transactions")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(JsonConverter.toJSON(transactionDTO)))
			.andExpect(status().isCreated());
	}

}
