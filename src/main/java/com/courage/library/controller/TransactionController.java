package com.courage.library.controller;

import java.util.Map;

import com.courage.library.exception.BadRequestException;
import com.courage.library.model.Transaction;
import com.courage.library.model.dto.TransactionDTO;
import com.courage.library.service.command.TransactionCommand;
import com.courage.library.service.query.TransactionQuery;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("api/v1/transactions")
public class TransactionController {

	@Autowired
	private TransactionQuery transactionQuery;

	@Autowired
	private TransactionCommand transactionCommand;

	@ApiOperation("Request for a book (create a transaction)")
	@PostMapping(
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionDTO transaction) {
		System.out.println(transaction.getBookId());
		Transaction newTransaction = this.transactionCommand.createTransaction(transaction);
		System.out.println(newTransaction.getBook().getId());
		return new ResponseEntity<>(newTransaction, HttpStatus.CREATED);
	}

	@ApiOperation("Get all/some transactions")
	@GetMapping(
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Page<Transaction>> getTransactions(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "userId", required = false) String userId) {
		Map<String, Integer> pageAttributes = PageValidator.validatePageAndSize(page, size);
		page = pageAttributes.get("page");
		size = pageAttributes.get("size");
		if( userId != null ) {
			Page<Transaction> transactions =  this.transactionQuery.getTransactionByUser(userId, page, size);
			return new ResponseEntity<>(transactions, HttpStatus.OK);
		}
		Page<Transaction> transactions = this.transactionQuery.getTransactions(page, size);
		return new ResponseEntity<>(transactions, HttpStatus.OK);
	}

	@ApiOperation("Get a transaction")
	@GetMapping(
			value = "/{transId}",
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Transaction> getTransaction(@PathVariable("transId") String transId) {
		Transaction transaction = this.transactionQuery.getTransactionById(transId);
		return new ResponseEntity<>(transaction, HttpStatus.OK);
	}

	@ApiOperation("Update a transaction")
	@PutMapping(
			value = "/{transId}",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Transaction> updateTransaction(@RequestBody TransactionDTO transaction,
			@PathVariable("transId") String transId) {
		if (transaction.getId().compareTo(transId) == 0) {
			Transaction updatedTrans = this.transactionCommand.updateTransaction(transaction);
			return new ResponseEntity<>(updatedTrans, HttpStatus.OK);
		}
		throw BadRequestException.create("Bad Request: Transaction id in path parameter does not match that in transaction object");
	}
}
