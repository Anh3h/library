package com.courage.library.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Comment {

	@Id
	private String id;
	@NotNull
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	@NotNull
	@ManyToOne
	@JoinColumn(name = "book_id")
	private Book book;
	@NotNull
	@Column(nullable = false)
	private String text;

	public Comment() {
	}

	public Comment(@NotNull String text) {
		this.text = text;
		this.user = new User();
		this.book = new Book();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
