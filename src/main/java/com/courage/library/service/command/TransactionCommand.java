package com.courage.library.service.command;

import com.courage.library.model.Transaction;
import com.courage.library.model.dto.TransactionDTO;

public interface TransactionCommand {

	Transaction createTransaction(TransactionDTO transaction);
	Transaction updateTransaction(TransactionDTO transaction);
	void  deleteTransaction(Long transId);
}
