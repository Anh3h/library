package com.courage.library.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Transaction {

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
	private Date checkOut;
	@NotNull
	@Column(nullable = false)
	private Date checkIn;
	@NotNull
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Status checkOutStatus;
	@NotNull
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Status checkInStatus;

	public Transaction() {
	}

	public Transaction(@NotNull Date checkOut, @NotNull Date checkIn, @NotNull Status checkOutStatus,
			@NotNull Status checkInStatus) {
		this.user = new User();
		this.book = new Book();
		this.checkOut = checkOut;
		this.checkIn = checkIn;
		this.checkOutStatus = checkOutStatus;
		this.checkInStatus = checkInStatus;
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

	public Date getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(Date checkOut) {
		this.checkOut = checkOut;
	}

	public Date getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(Date checkIn) {
		this.checkIn = checkIn;
	}

	public Status getCheckOutStatus() {
		return checkOutStatus;
	}

	public void setCheckOutStatus(Status checkOutStatus) {
		this.checkOutStatus = checkOutStatus;
	}

	public Status getCheckInStatus() {
		return checkInStatus;
	}

	public void setCheckInStatus(Status checkInStatus) {
		this.checkInStatus = checkInStatus;
	}
}
