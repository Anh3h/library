package com.courage.library.model.dto;

import javax.validation.constraints.NotNull;
import java.util.Date;

import com.courage.library.model.Status;

public class TransactionDTO {

	private Long id;
	@NotNull
	private Long userId;
	@NotNull
	private Long bookId;
	@NotNull
	private Date checkOut;
	@NotNull
	private Date checkIn;
	@NotNull
	private Status checkOutStatus;
	@NotNull
	private Status checkInStatus;

	public TransactionDTO(Long id, @NotNull Long userId, @NotNull Long bookId,
			@NotNull Date checkOut, @NotNull Date checkIn,
			@NotNull Status checkOutStatus, @NotNull Status checkInStatus) {
		this.id = id;
		this.userId = userId;
		this.bookId = bookId;
		this.checkOut = checkOut;
		this.checkIn = checkIn;
		this.checkOutStatus = checkOutStatus;
		this.checkInStatus = checkInStatus;
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
