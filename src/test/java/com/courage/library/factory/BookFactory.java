package com.courage.library.factory;

import java.util.HashSet;
import java.util.Random;
import java.util.UUID;

import com.courage.library.model.Book;
import com.courage.library.model.Topic;
import org.apache.commons.lang3.RandomStringUtils;

public class BookFactory {

	public static Book instance() {
		String id = UUID.randomUUID().toString();
		String title = RandomStringUtils.random(10, true, true);
		String isbn = RandomStringUtils.random(10, false, true);
		String author = RandomStringUtils.random(10, true, true);
		Integer totalQty = new Random().nextInt();
		Integer availableQty = new Random().nextInt();
		Integer upVotes = new Random().nextInt();
		Integer downVotes = new Random().nextInt();
		Integer numOfBorrows = new Random().nextInt();
		Topic topic = TopicFactory.instance();
		Book book = new Book(id, title, isbn, author, topic, totalQty, availableQty, upVotes,
				downVotes, numOfBorrows);
		book.setUsers(new HashSet<>());

		return book;
	}
}