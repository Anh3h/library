package com.courage.library.model.dto;

import javax.validation.constraints.NotNull;

public class CommentDTO {

	private Long id;
	@NotNull
	private Long userId;
	@NotNull
	private Long bookId;
	@NotNull
	private String text;

	public CommentDTO(Long id, @NotNull Long userId, @NotNull Long bookId,
			@NotNull String text) {
		this.id = id;
		this.userId = userId;
		this.bookId = bookId;
		this.text = text;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
