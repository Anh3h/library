package com.courage.library.mapper;

import com.courage.library.model.Book;
import com.courage.library.model.dto.BookDTO;
import com.courage.library.repository.BookRepository;
import com.courage.library.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class BookMapper {

	@Autowired
	private static TopicRepository topicRepository;

	@Autowired
	private static BookRepository bookRepository;

	public static Book getBook(BookDTO bookDTO) {
		Book book = new Book(bookDTO.getTitle(), bookDTO.getIsbn(), bookDTO.getAuthor(), bookDTO.getEdition(),
				bookDTO.getPublisher(), bookDTO.getPublicationDate(), bookDTO.getShelf(), bookDTO.getTotalQty(),
				bookDTO.getAvailableQty(), bookDTO.getUpVotes(), bookDTO.getDownVotes(), bookDTO.getNumOfBorrows());
		if (bookDTO.getId() != null)
			book.setId(bookDTO.getId());
		book.setTopic(topicRepository.getOne(bookDTO.getTopicId()));
		return book;
	}

	public static Book getFromExistingBook(BookDTO bookDTO) {
		Book book = bookRepository.getOne(bookDTO.getId());
		book.genericSetter(bookDTO.getTitle(), bookDTO.getIsbn(), bookDTO.getAuthor(), bookDTO.getEdition(),
				bookDTO.getPublisher(), bookDTO.getPublicationDate(), bookDTO.getShelf(), bookDTO.getTotalQty(),
				bookDTO.getAvailableQty(), bookDTO.getUpVotes(), bookDTO.getDownVotes(), bookDTO.getNumOfBorrows());
		if (bookDTO.getId() != null);
		return book;
	}
}
