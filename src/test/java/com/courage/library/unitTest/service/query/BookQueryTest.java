package com.courage.library.unitTest.service.query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.courage.library.exception.NotFoundException;
import com.courage.library.factory.BookFactory;
import com.courage.library.model.Book;
import com.courage.library.model.Topic;
import com.courage.library.repository.BookRepository;
import com.courage.library.repository.TopicRepository;
import com.courage.library.service.query.BookQuery;
import com.courage.library.service.query.implementation.BookQueryImplementation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class BookQueryTest {

	@TestConfiguration
	static class BookQueryTestConfiguration {
		@Bean
		public BookQuery bookQuery() {
			return new BookQueryImplementation();
		}
	}

	@Autowired
	private BookQuery bookQuery;

	@MockBean
	private BookRepository bookRepository;

	@MockBean
	private TopicRepository topicRepository;

	@Test
	public void getBooks_returnsAPageOfBooks() {
		Page<Book> books = new PageImpl<>(BookFactory.instances());
		given(this.bookRepository.findAll(PageRequest.of(0,2))).willReturn(books);

		Page<Book> gottenPages = this.bookQuery.getBooks(1, 2);

		assertThat(gottenPages.getContent()).isEqualTo(books.getContent());
	}

	@Test
	public void getPopularBooks_returnsAPageOfBooks() {
		Page<Book> books = new PageImpl<>(BookFactory.instances());
		given(this.bookRepository.findAll(PageRequest.of(0,2, Sort.Direction.DESC, "numOfBorrows")))
				.willReturn(books);

		Page<Book> gottenPages = this.bookQuery.getPopularBooks(1, 2);

		assertThat(gottenPages.getContent()).isEqualTo(books.getContent());
	}

	@Test
	public void getBooksByAuthor_returnsAPageOfBooks() {
		Page<Book> books = new PageImpl<>(BookFactory.instances());
		String author = books.getContent().get(0).getAuthor();
		given(this.bookRepository.findByAuthor(author, PageRequest.of(0,2)))
				.willReturn(books);

		Page<Book> gottenPages = this.bookQuery.getBooksByAuthor(author, 1, 2);

		assertThat(gottenPages.getContent()).isEqualTo(books.getContent());
	}

	@Test
	public void getBooksByTitle_returnsAPageOfBooks() {
		Page<Book> books = new PageImpl<>(BookFactory.instances());
		String title = books.getContent().get(0).getTitle();
		given(this.bookRepository.findByTitle(title, PageRequest.of(0,2)))
				.willReturn(books);

		Page<Book> gottenPages = this.bookQuery.getBooksByTitle(title, 1, 2);

		assertThat(gottenPages.getContent()).isEqualTo(books.getContent());
	}

	@Test
	public void getBooksByTopic_returnsAPageOfBooks() {
		Page<Book> books = new PageImpl<>(BookFactory.instances());;
		Topic topic = books.getContent().get(0).getTopic();
		given(this.bookRepository.findByTopic(topic, PageRequest.of(0,2)))
				.willReturn(books);
		given(this.topicRepository.getOne(topic.getId())).willReturn(topic);

		Page<Book> gottenPages = this.bookQuery.getBooksByTopic(topic.getId(), 1, 2);

		assertThat(gottenPages.getContent()).isEqualTo(books.getContent());
	}

	@Test
	public void getBook_returnsExistingBook() {
		Book book = BookFactory.instance();
		given(this.bookRepository.getOne(book.getId())).willReturn(book);

		Book gottenBook = this.bookQuery.getBookById(book.getId());

		assertThat(gottenBook).isEqualTo(book);
	}

	@Test(expected = NotFoundException.class)
	public void getNonExistingBook_throwsNotFoundException() {
		String bookId = BookFactory.instance().getId();
		given(this.bookRepository.getOne(bookId)).willReturn(null);

		this.bookQuery.getBookById(bookId);
	}
}
