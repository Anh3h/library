package com.courage.library.factory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.courage.library.model.Book;
import com.courage.library.model.Status;
import com.courage.library.model.Transaction;
import com.courage.library.model.User;
import com.courage.library.model.dto.TransactionDTO;
import org.springframework.data.domain.Page;

public class TransactionFactory {

	public static Transaction instance() {
		String id = UUID.randomUUID().toString();
		User user = UserFactory.instance();
		Book book = BookFactory.instance();
		Date checkOut = new Date();

		Calendar c = Calendar.getInstance();
		c.setTime(checkOut);
		c.add(Calendar.DATE, 1);
		Date checkIn = c.getTime();

		Status checkOutStatus = Status.PENDING;
		Status checkInStatus = Status.PENDING;

		return new Transaction(id, user, book, checkOut, checkIn, checkOutStatus, checkInStatus);
	}

	public static List<Transaction> getInstances(User user) {
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(instance());
		transactions.add(instance());
		transactions.add(instance());

		transactions.forEach(transaction -> transaction.setUser(user));
		return transactions;
	}

	public static TransactionDTO convertToDTO(Transaction transaction) {
		return new TransactionDTO(transaction.getId(), transaction.getUser().getId(),
				transaction.getBook().getId(), transaction.getCheckOut(), transaction.getCheckIn()
				, transaction.getCheckOutStatus(), transaction.getCheckInStatus());
	}
}
