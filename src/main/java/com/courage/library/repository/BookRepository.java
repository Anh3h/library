package com.courage.library.repository;

import com.courage.library.model.Book;
import com.courage.library.model.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {

	Book findByIsbn(String isbn);
	Page<Book> findByAuthor(String author, Pageable page);
	Page<Book> findByTitle(String title, Pageable page);
	Page<Book> findByTopic(Topic topic, Pageable page);
}
