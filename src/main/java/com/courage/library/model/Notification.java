package com.courage.library.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Notification {

	@Id
	private String id;
	@NotNull
	@Column(nullable = false)
	private String action;
	@NotNull
	@Column(nullable = false)
	private String content;
	@NotNull
	@Column(nullable = false)
	private Date createdAt;
	@NotNull
	@Column(nullable = false)
	private Boolean done;
	@NotNull
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	@NotNull
	@ManyToOne
	@JoinColumn(name = "book_id")
	private Book book;


	public Notification() {
	}

	public Notification(String id, @NotNull String action, @NotNull String content,
			@NotNull Date createdAt, @NotNull Boolean done,
			@NotNull User user, @NotNull Book book) {
		this.id = id;
		this.action = action;
		this.content = content;
		this.createdAt = createdAt;
		this.done = done;
		this.user = user;
		this.book = book;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Boolean getDone() {
		return done;
	}

	public void setDone(Boolean done) {
		this.done = done;
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
}
