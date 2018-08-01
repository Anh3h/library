package com.courage.library.service.query.implementation;

import com.courage.library.exception.NotFoundException;
import com.courage.library.model.Book;
import com.courage.library.repository.BookRepository;
import com.courage.library.service.query.BookQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BookQueryImplementation implements BookQuery {

	@Autowired
	private BookRepository bookRepository;

	@Override
	public Book getBookById(String bookId) {
		Book book = this.bookRepository.getOne(bookId);
		if (book == null) {
			throw NotFoundException.create("Not Found: Book does not exist");
		}
		return book;
	}

	@Override
	public Page<Book> getBooks(Integer pageNumber, Integer pageSize) {
		return this.bookRepository.findAll(PageRequest.of(pageNumber-1, pageSize));
	}

	@Override
	public Page<Book> getPopularBooks(Integer pageNumber, Integer pageSize) {
		return this.bookRepository.findAll(PageRequest.of(pageNumber-1, pageSize, Sort.Direction.DESC, "numOfBorrows"));
	}
}
