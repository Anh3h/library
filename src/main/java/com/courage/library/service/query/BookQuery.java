package com.courage.library.service.query;

import com.courage.library.model.Book;
import org.springframework.data.domain.Page;

public interface BookQuery {

	Book getBookById(String bookId);
	Page<Book> getBooks(Integer pageNumber, Integer pageSize);
}
