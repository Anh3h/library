package com.courage.library.service.command.implementation;

import com.courage.library.exception.NotFoundException;
import com.courage.library.mapper.TransactionMapper;
import com.courage.library.model.Transaction;
import com.courage.library.model.dto.TransactionDTO;
import com.courage.library.repository.TransactionRepository;
import com.courage.library.service.command.TransactionCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TransactionCommandImplementation implements TransactionCommand {

	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	public Transaction createTransaction(TransactionDTO transactionDTO) {
		Transaction transaction = TransactionMapper.getTransaction(transactionDTO);
		return this.transactionRepository.save(transaction);
	}

	@Override
	public Transaction updateTransaction(TransactionDTO transactionDTO) {
		if (this.transactionRepository.existsById(transactionDTO.getId())) {
			Transaction transaction = TransactionMapper.getTransaction(transactionDTO);
			return this.transactionRepository.save(transaction);
		}
		throw NotFoundException.create("Not Found: Transaction does not exist");
	}

	@Override
	public void deleteTransaction(Long transId) {
		this.transactionRepository.deleteById(transId);
	}
}
