package com.courage.library.service.query;

import com.courage.library.model.Transaction;
import org.springframework.data.domain.Page;

public interface TransactionQuery {

	Transaction getTransactionById(String id);
	Page<Transaction> getTransactions(Integer pageNumber, Integer pageSize);
}
