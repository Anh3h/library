package com.courage.library.model.dto;

import javax.validation.constraints.NotNull;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookDTO {

	private String id;
	@NotNull
	private String title;
	@NotNull
	private String isbn;
	@NotNull
	private String author;
	private String edition;
	private String publisher;
	private Date publicationDate;
	@NotNull
	private String topicId;
	private String shelf;
	@NotNull
	private Integer totalQty;
	@NotNull
	private Integer availableQty;
	@NotNull
	private Integer upVotes;
	@NotNull
	private Integer downVotes;
	@NotNull
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private Integer numOfBorrows;

	public BookDTO() {
	}

	public BookDTO(String id, @NotNull String title, @NotNull String isbn,
			@NotNull String author, @NotNull String topicId, @NotNull Integer totalQty,
			@NotNull Integer availableQty, @NotNull Integer upVotes,
			@NotNull Integer downVotes, @NotNull Integer numOfBorrows) {
		this.id = id;
		this.title = title;
		this.isbn = isbn;
		this.author = author;
		this.topicId = topicId;
		this.totalQty = totalQty;
		this.availableQty = availableQty;
		this.upVotes = upVotes;
		this.downVotes = downVotes;
		this.numOfBorrows = numOfBorrows;
	}

	public BookDTO(String id, @NotNull String title, @NotNull String isbn,
			@NotNull String author, String edition, String publisher, Date publicationDate,
			@NotNull String topicId, String shelf, @NotNull Integer totalQty,
			@NotNull Integer availableQty, @NotNull Integer upVotes,
			@NotNull Integer downVotes, @NotNull Integer numOfBorrows) {
		this(id, title, isbn, author, topicId, totalQty, availableQty, upVotes,
				downVotes, numOfBorrows);
		this.edition = edition;
		this.publisher = publisher;
		this.publicationDate = publicationDate;
		this.shelf = shelf;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String getTopicId() {
		return topicId;
	}

	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}

	public String getShelf() {
		return shelf;
	}

	public void setShelf(String shelf) {
		this.shelf = shelf;
	}

	public Integer getTotalQty() {
		return totalQty;
	}

	public void setTotalQty(Integer totalQty) {
		this.totalQty = totalQty;
	}

	public Integer getAvailableQty() {
		return availableQty;
	}

	public void setAvailableQty(Integer availableQty) {
		this.availableQty = availableQty;
	}

	public Integer getUpVotes() {
		return upVotes;
	}

	public void setUpVotes(Integer upVotes) {
		this.upVotes = upVotes;
	}

	public Integer getDownVotes() {
		return downVotes;
	}

	public void setDownVotes(Integer downVotes) {
		this.downVotes = downVotes;
	}

	public Integer getNumOfBorrows() {
		return numOfBorrows;
	}

	public void setNumOfBorrows(Integer numOfBorrows) {
		this.numOfBorrows = numOfBorrows;
	}
}
