package com.courage.library.repository;

import com.courage.library.model.Book;
import com.courage.library.model.Transaction;
import com.courage.library.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

	Page<Transaction> findByUser(User user, Pageable page);
	Page<Transaction> findByBook(Book book, Pageable page);
}
