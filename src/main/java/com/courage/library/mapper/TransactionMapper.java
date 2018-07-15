package com.courage.library.mapper;

import com.courage.library.model.Transaction;
import com.courage.library.model.dto.TransactionDTO;
import com.courage.library.repository.BookRepository;
import com.courage.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class TransactionMapper {

	@Autowired
	private static BookRepository bookRepository;

	@Autowired
	private static UserRepository userRepository;

	public static Transaction getTransaction(TransactionDTO transactionDTO) {
		Transaction transaction = new Transaction(transactionDTO.getCheckOut(), transactionDTO.getCheckIn(),
			transactionDTO.getCheckOutStatus(), transactionDTO.getCheckInStatus());
		if (transactionDTO.getId() != null)
			transaction.setId(transactionDTO.getId());
		transaction.setBook(bookRepository.getOne(transactionDTO.getBookId()));
		transaction.setUser(userRepository.getOne(transactionDTO.getUserId()));
		return transaction;
	}

}
