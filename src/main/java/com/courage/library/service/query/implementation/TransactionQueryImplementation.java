package com.courage.library.service.query.implementation;

import com.courage.library.exception.NotFoundException;
import com.courage.library.model.Transaction;
import com.courage.library.model.User;
import com.courage.library.repository.TransactionRepository;
import com.courage.library.repository.UserRepository;
import com.courage.library.service.query.TransactionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TransactionQueryImplementation implements TransactionQuery {

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public Transaction getTransactionById(String id) {
		Transaction transaction = this.transactionRepository.getOne(id);
		if (transaction == null) {
			throw NotFoundException.create("Not Found: Transaction does not exist");
		}
		return transaction;
	}

	@Override
	public Page<Transaction> getTransactionByUser(String userId, Integer pageNumber, Integer pageSize) {
		User user = this.userRepository.getOne(userId);
		if (user != null) {
			return this.transactionRepository.findByUser(user, PageRequest.of(pageNumber-1, pageSize));
		}
		throw NotFoundException.create("Not Found: user id does not exist");

	}

	@Override
	public Page<Transaction> getTransactions(Integer pageNumber, Integer pageSize) {
		return this.transactionRepository.findAll(PageRequest.of(pageNumber-1, pageSize));
	}
}
