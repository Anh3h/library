package com.courage.library.service.query;

import com.courage.library.model.Book;
import org.springframework.data.domain.Page;

public interface BookQuery {

	Book getBookById(String bookId);
	Page<Book> getBooks(Integer pageNumber, Integer pageSize);
	Page<Book> getPopularBooks(Integer pageNumber, Integer pageSize);
	Page<Book> getBooksByAuthor(String author, Integer pageNumber, Integer pageSize);
	Page<Book> getBooksByTitle(String title, Integer pageNumber, Integer pageSize);
	Page<Book> getBooksByTopic(String topicId, Integer pageNumber, Integer pageSize);
}
