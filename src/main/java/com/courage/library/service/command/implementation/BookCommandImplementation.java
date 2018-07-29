package com.courage.library.service.command.implementation;

import java.util.UUID;

import com.courage.library.exception.ConflictException;
import com.courage.library.exception.NotFoundException;
import com.courage.library.mapper.BookMapper;
import com.courage.library.model.Book;
import com.courage.library.model.dto.BookDTO;
import com.courage.library.repository.BookRepository;
import com.courage.library.repository.TopicRepository;
import com.courage.library.service.command.BookCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BookCommandImplementation implements BookCommand {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private TopicRepository topicRepository;

	@Override
	public Book createBook(BookDTO bookDTO) {
		if (this.bookRepository.findByIsbn(bookDTO.getIsbn()) == null) {
			Book book = new BookMapper(this.topicRepository, this.bookRepository).getBook(bookDTO);
			book.setId(UUID.randomUUID().toString());
			return this.bookRepository.save(book);
		}
		throw ConflictException.create("Conflict: Book with ISBN, {0} already exist", bookDTO.getIsbn());
	}

	@Override
	public Book updateBook(BookDTO bookDTO) {
		if (this.bookRepository.existsById(bookDTO.getId())) {
			Book book = new BookMapper(this.topicRepository, this.bookRepository).getBook(bookDTO);
			return this.bookRepository.save(book);
		}
		throw NotFoundException.create("Not Found: Book does not exist");
	}

	@Override
	public void deleteBook(String bookId) {
		this.bookRepository.deleteById(bookId);
	}
}
