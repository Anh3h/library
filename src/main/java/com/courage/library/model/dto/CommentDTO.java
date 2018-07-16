package com.courage.library.model.dto;

import javax.validation.constraints.NotNull;

public class CommentDTO {

	private String id;
	@NotNull
	private String userId;
	@NotNull
	private String bookId;
	@NotNull
	private String text;

	public CommentDTO() {
	}

	public CommentDTO(String id, @NotNull String userId, @NotNull String bookId,
			@NotNull String text) {
		this.id = id;
		this.userId = userId;
		this.bookId = bookId;
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
