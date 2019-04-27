package com.courage.library.unitTest.service.command;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.courage.library.exception.ConflictException;
import com.courage.library.exception.NotFoundException;
import com.courage.library.factory.BookFactory;
import com.courage.library.factory.NotificationFactory;
import com.courage.library.model.Book;
import com.courage.library.model.Notification;
import com.courage.library.model.dto.BookDTO;
import com.courage.library.repository.BookRepository;
import com.courage.library.repository.NotificationRepository;
import com.courage.library.repository.TopicRepository;
import com.courage.library.service.command.BookCommand;
import com.courage.library.service.command.implementation.BookCommandImplementation;
import com.google.cloud.storage.Storage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class BookCommandTest {

	@TestConfiguration
	static class BookCommandTestConfiguration {
		@Bean
		public BookCommand bookCommand() {
			return new BookCommandImplementation();
		}
	}

	@MockBean
	private BookRepository bookRepository;

	@MockBean
	private TopicRepository topicRepository;

	@MockBean
	private NotificationRepository notificationRepository;

	@MockBean
	private Storage storage;

	@Autowired
	private BookCommand bookCommand;

	@Test
	public void createBook_returnsCreatedBook() {
		Book book = BookFactory.instance();
		BookDTO bookDTO = new BookDTO(book.getId(), book.getTitle(), book.getIsbn(),
				book.getAuthor(), book.getTopic().getId(), book.getTotalQty(),
				book.getAvailableQty(), book.getUpVotes(), book.getDownVotes(),
				book.getNumOfBorrows());
		given(bookRepository.save(any(Book.class))).willReturn(book);
		given(bookRepository.findByIsbn(book.getIsbn())).willReturn(null);

		Book createdBook = this.bookCommand.createBook(bookDTO);

		assertThat(createdBook).isEqualToComparingFieldByField(book);
	}

	@Test(expected = ConflictException.class)
	public void createBookWithExistingISBN_throwsConflict() {
		Book book = BookFactory.instance();
		BookDTO bookDTO = new BookDTO(book.getId(), book.getTitle(), book.getIsbn(),
				book.getAuthor(), book.getTopic().getId(), book.getTotalQty(),
				book.getAvailableQty(), book.getUpVotes(), book.getDownVotes(),
				book.getNumOfBorrows());
		given(bookRepository.findByIsbn(book.getIsbn())).willReturn(book);

		this.bookCommand.createBook(bookDTO);
	}

	@Test
	public void updateBook_returnsUpdatedBook() {
		Book book = BookFactory.instance();
		BookDTO bookDTO = new BookDTO(book.getId(), book.getTitle(), book.getIsbn(),
				book.getAuthor(), book.getTopic().getId(), book.getTotalQty(),
				book.getAvailableQty(), book.getUpVotes(), book.getDownVotes(),
				book.getNumOfBorrows());
		Notification notification = NotificationFactory.instance();
		given(this.bookRepository.save(any(Book.class))).willReturn(book);
		given(this.notificationRepository.save(any(Notification.class))).willReturn(notification);
		given(this.bookRepository.existsById(book.getId())).willReturn(true);
		given(this.bookRepository.getOne(book.getId())).willReturn(book);

		Book updatedBook = this.bookCommand.updateBook(bookDTO);

		assertThat(updatedBook).isEqualToComparingFieldByField(book);

	}

	@Test(expected = NotFoundException.class)
	public void updateNonExistingBook_throwsNotFoundException() {
		Book book = BookFactory.instance();
		BookDTO bookDTO = new BookDTO(book.getId(), book.getTitle(), book.getIsbn(),
				book.getAuthor(), book.getTopic().getId(), book.getTotalQty(),
				book.getAvailableQty(), book.getUpVotes(), book.getDownVotes(),
				book.getNumOfBorrows());
		given(this.bookRepository.existsById(book.getId())).willReturn(false);

		this.bookCommand.updateBook(bookDTO);
	}

}
