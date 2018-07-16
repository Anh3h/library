package com.courage.library.service.command.implementation;

import java.util.UUID;

import com.courage.library.exception.NotFoundException;
import com.courage.library.mapper.TransactionMapper;
import com.courage.library.model.Transaction;
import com.courage.library.model.dto.TransactionDTO;
import com.courage.library.repository.BookRepository;
import com.courage.library.repository.TransactionRepository;
import com.courage.library.repository.UserRepository;
import com.courage.library.service.command.TransactionCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TransactionCommandImplementation implements TransactionCommand {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	public Transaction createTransaction(TransactionDTO transactionDTO) {
		Transaction transaction = new TransactionMapper(this.bookRepository, this.userRepository).getTransaction(transactionDTO);
		transaction.setId(UUID.randomUUID().toString());
		return this.transactionRepository.save(transaction);
	}

	@Override
	public Transaction updateTransaction(TransactionDTO transactionDTO) {
		if (this.transactionRepository.existsById(transactionDTO.getId())) {
			Transaction transaction = new TransactionMapper(this.bookRepository, this.userRepository).getTransaction(transactionDTO);
			return this.transactionRepository.save(transaction);
		}
		throw NotFoundException.create("Not Found: Transaction does not exist");
	}

	@Override
	public void deleteTransaction(String transId) {
		this.transactionRepository.deleteById(transId);
	}
}
