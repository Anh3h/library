package com.courage.library.mapper;

import com.courage.library.model.Transaction;
import com.courage.library.model.dto.TransactionDTO;
import com.courage.library.repository.BookRepository;
import com.courage.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class TransactionMapper {

	private BookRepository bookRepository;

	private UserRepository userRepository;

	public TransactionMapper(BookRepository bookRepository, UserRepository userRepository) {
		this.bookRepository = bookRepository;
		this.userRepository = userRepository;
	}

	public Transaction getTransaction(TransactionDTO transactionDTO) {
		Transaction transaction = new Transaction(transactionDTO.getCheckOut(), transactionDTO.getCheckIn(),
			transactionDTO.getCheckOutStatus(), transactionDTO.getCheckInStatus());
		if (transactionDTO.getId() != null)
			transaction.setId(transactionDTO.getId());
		transaction.setBook(bookRepository.getOne(transactionDTO.getBookId()));
		transaction.setUser(userRepository.getOne(transactionDTO.getUserId()));
		return transaction;
	}

}
