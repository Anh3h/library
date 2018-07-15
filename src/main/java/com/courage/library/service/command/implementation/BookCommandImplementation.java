package com.courage.library.service.command.implementation;

import com.courage.library.exception.ConflictException;
import com.courage.library.exception.NotFoundException;
import com.courage.library.mapper.BookMapper;
import com.courage.library.model.Book;
import com.courage.library.model.dto.BookDTO;
import com.courage.library.repository.BookRepository;
import com.courage.library.service.command.BookCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BookCommandImplementation implements BookCommand {

	@Autowired
	private BookRepository bookRepository;

	@Override
	public Book createBook(BookDTO bookDTO) {
		if (this.bookRepository.findByIsbn(bookDTO.getIsbn()) == null) {
			Book book = BookMapper.getBook(bookDTO);
			return this.bookRepository.save(book);
		}
		throw ConflictException.create("Conflict: Book with ISBN, {0} already exist", bookDTO.getIsbn());
	}

	@Override
	public Book updateBook(BookDTO bookDTO) {
		if (this.bookRepository.existsById(bookDTO.getId())) {

		}
		throw NotFoundException.create("Not Found: Book does not exist");
	}

	@Override
	public void deleteBook(Long bookId) {
		this.bookRepository.existsById(bookId);
	}
}
