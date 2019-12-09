package com.courage.library.factory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.courage.library.model.Book;
import com.courage.library.model.Topic;
import com.courage.library.model.dto.BookDTO;
import org.apache.commons.lang3.RandomStringUtils;

public class BookFactory {

	public static Book instance() {
		String id = UUID.randomUUID().toString();
		String title = RandomStringUtils.random(10, true, true);
		String isbn = RandomStringUtils.random(10, false, true);
		String author = RandomStringUtils.random(10, true, true);
		Integer totalQty = Math.abs(new Random().nextInt());
		Integer availableQty = Math.abs(new Random().nextInt(totalQty));
		Integer upVotes = Math.abs(new Random().nextInt());
		Integer downVotes = Math.abs(new Random().nextInt());
		Integer numOfBorrows = Math.abs(new Random().nextInt(availableQty));
		Topic topic = TopicFactory.instance();
		Book book = new Book(id, title, isbn, author, topic, totalQty, availableQty, upVotes,
				downVotes, numOfBorrows);
		book.setUsers(new HashSet<>());

		return book;
	}

	public static BookDTO convertToDTO(Book book) {
		return new BookDTO(book.getId(), book.getTitle(), book.getIsbn(),
				book.getAuthor(), book.getTopic().getId(), book.getTotalQty(),
				book.getAvailableQty(), book.getUpVotes(), book.getDownVotes(),
				book.getNumOfBorrows());
	}

	public static List<Book> instances() {
		List<Book> books = new ArrayList<>();
		books.add(instance());
		books.add(instance());
		books.add(instance());
		return books;
	}
}
