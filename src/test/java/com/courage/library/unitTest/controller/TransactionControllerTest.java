package com.courage.library.unitTest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.courage.library.controller.TransactionController;
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
public class TransactionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TransactionCommand transactionCommand;

	@MockBean
	private TransactionQuery transactionQuery;

	@Test
	public void createTransactionRequest_returnsHttp201AndCreatedTransaction() throws Exception {
		Transaction transaction = TransactionFactory.instance();
		TransactionDTO transactionDTO = TransactionFactory.convertToDTO(transaction);
		given(this.transactionCommand.createTransaction(any(TransactionDTO.class))).willReturn(transaction);

		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/transactions")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(JsonConverter.toJSON(transactionDTO)))
			.andExpect(status().isCreated());
	}

	@Test
	public void updateTransactionRequest_returnsHttp200AndUpdatedTransaction() throws Exception {
		Transaction transaction = TransactionFactory.instance();
		TransactionDTO transactionDTO = TransactionFactory.convertToDTO(transaction);
		given(this.transactionCommand.updateTransaction(any(TransactionDTO.class))).willReturn(transaction);

		this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/transactions/" + transaction.getId())
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(JsonConverter.toJSON(transactionDTO)))
				.andExpect(status().isOk());
	}

	@Test
	public void getTransactionRequest_returnsHttp200AndExistingTransaction() throws Exception {
		Transaction transaction = TransactionFactory.instance();
		given(this.transactionQuery.getTransactionById(transaction.getId())).willReturn(transaction);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/transactions/" + transaction.getId())
				.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("id").value(transaction.getId()));
	}

}
