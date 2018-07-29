package com.courage.library.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Book {

	@Id
	private String id;
	@NotNull
	@Column(nullable = false)
	private String title;
	@NotNull
	@Column(nullable = false, unique = true)
	private String isbn;
	@NotNull
	@Column(nullable = false)
	private String author;
	private String edition;
	private String publisher;
	private Date publicationDate;
	@NotNull
	@ManyToOne
	@JoinColumn(name = "topic_id")
	private Topic topic;
	private String shelf;
	@NotNull
	@Column(nullable = false)
	private Integer totalQty;
	@NotNull
	@Column(nullable = false)
	private Integer availableQty;
	@NotNull
	@Column(nullable = false)
	private Integer upVotes;
	@NotNull
	@Column(nullable = false)
	private Integer downVotes;
	@NotNull
	@Column(nullable = false)
	private Integer numOfBorrows;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "favoriteBooks")
	private Set<User> users;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "book", cascade = CascadeType.ALL)
	private Set<Comment> comments;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "book", cascade = CascadeType.ALL)
	private Set<Transaction> transactions;

	public Book() {
	}

	public Book(@NotNull String title, @NotNull String isbn, @NotNull String author, String edition, String publisher,
			Date publicationDate, String shelf, @NotNull Integer totalQty, @NotNull Integer availableQty,
			@NotNull Integer upVotes, @NotNull Integer downVotes, @NotNull Integer numOfBorrows) {
		this.title = title;
		this.isbn = isbn;
		this.author = author;
		this.edition = edition;
		this.publisher = publisher;
		this.publicationDate = publicationDate;
		this.topic = new Topic();
		this.shelf = shelf;
		this.totalQty = totalQty;
		this.availableQty = availableQty;
		this.upVotes = upVotes;
		this.downVotes = downVotes;
		this.numOfBorrows = numOfBorrows;
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

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
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

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public void genericSetter(@NotNull String title, @NotNull String isbn, @NotNull String author, String edition, String publisher,
			Date publicationDate, String shelf, @NotNull Integer totalQty, @NotNull Integer availableQty,
			@NotNull Integer upVotes, @NotNull Integer downVotes, @NotNull Integer numOfBorrows) {
		this.title = title;
		this.isbn = isbn;
		this.author = author;
		this.edition = edition;
		this.publisher = publisher;
		this.publicationDate = publicationDate;
		this.shelf = shelf;
		this.totalQty = totalQty;
		this.availableQty = availableQty;
		this.upVotes = upVotes;
		this.downVotes = downVotes;
		this.numOfBorrows = numOfBorrows;
	}
}
