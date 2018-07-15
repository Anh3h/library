package com.courage.library.repository;

import com.courage.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

	Book findByIsbn(String isbn);
}
