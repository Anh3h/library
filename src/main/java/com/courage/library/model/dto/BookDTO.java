package com.courage.library.model.dto;

import javax.validation.constraints.NotNull;
import java.util.Date;

import com.courage.library.model.Topic;

public class BookDTO {

	private Long id;
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
	private Long topicId;
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
	private Integer numOfBorrows;

	public BookDTO(Long id, @NotNull String title, @NotNull String isbn,
			@NotNull String author, String edition, String publisher, Date publicationDate,
			@NotNull Long topicId, String shelf, @NotNull Integer totalQty,
			@NotNull Integer availableQty, @NotNull Integer upVotes,
			@NotNull Integer downVotes, @NotNull Integer numOfBorrows) {
		this.id = id;
		this.title = title;
		this.isbn = isbn;
		this.author = author;
		this.edition = edition;
		this.publisher = publisher;
		this.publicationDate = publicationDate;
		this.topicId = topicId;
		this.shelf = shelf;
		this.totalQty = totalQty;
		this.availableQty = availableQty;
		this.upVotes = upVotes;
		this.downVotes = downVotes;
		this.numOfBorrows = numOfBorrows;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Long getTopicId() {
		return topicId;
	}

	public void setTopicId(Long topicId) {
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
