package com.courage.library.mapper;

import com.courage.library.model.Book;
import com.courage.library.model.dto.BookDTO;
import com.courage.library.repository.BookRepository;
import com.courage.library.repository.TopicRepository;

public class BookMapper {

	private TopicRepository topicRepository;

	private BookRepository bookRepository;

	public BookMapper(TopicRepository topicRepository, BookRepository bookRepository) {
		this.topicRepository = topicRepository;
		this.bookRepository = bookRepository;
	}

	public Book getBook(BookDTO bookDTO) {
		Book book = new Book(bookDTO.getTitle(), bookDTO.getIsbn(), bookDTO.getAuthor(), bookDTO.getEdition(),
				bookDTO.getPublisher(), bookDTO.getPublicationDate(), bookDTO.getShelf(), bookDTO.getTotalQty(),
				bookDTO.getAvailableQty(), bookDTO.getUpVotes(), bookDTO.getDownVotes());
		book.setTopic(topicRepository.getOne(bookDTO.getTopicId()));
		return book;
	}

	public Book getFromExistingBook(BookDTO bookDTO) {
		Book book = bookRepository.getOne(bookDTO.getId());
		book.genericSetter(bookDTO.getTitle(), bookDTO.getIsbn(), bookDTO.getAuthor(), bookDTO.getEdition(),
				bookDTO.getPublisher(), bookDTO.getPublicationDate(), bookDTO.getShelf(), bookDTO.getTotalQty(),
				bookDTO.getAvailableQty(), bookDTO.getUpVotes(), bookDTO.getDownVotes());
		return book;
	}
}
