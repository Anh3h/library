package com.courage.library.repository;

import com.courage.library.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	Page<Transaction> findByUser(Long userId, Pageable page);
	Page<Transaction> findByBook(Long bookId, Pageable page);
}
