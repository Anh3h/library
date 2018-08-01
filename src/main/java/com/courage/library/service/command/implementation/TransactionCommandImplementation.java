package com.courage.library.service.command.implementation;

import java.util.UUID;

import com.courage.library.exception.BadRequestException;
import com.courage.library.exception.NotFoundException;
import com.courage.library.mapper.TransactionMapper;
import com.courage.library.model.Book;
import com.courage.library.model.Status;
import com.courage.library.model.Transaction;
import com.courage.library.model.dto.TransactionDTO;
import com.courage.library.repository.BookRepository;
import com.courage.library.repository.TransactionRepository;
import com.courage.library.repository.UserRepository;
import com.courage.library.service.command.TransactionCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TransactionCommandImplementation implements TransactionCommand {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	public Transaction createTransaction(TransactionDTO transactionDTO) {
		Transaction transaction = new TransactionMapper(this.bookRepository, this.userRepository).getTransaction(transactionDTO);
		Book book = this.bookRepository.getOne(transactionDTO.getBookId());
		if (book.getAvailableQty() > 0) {
			if (transaction.getCheckOut().before(transaction.getCheckIn())) {
				transaction.setCheckInStatus(Status.PENDING);
				transaction.setCheckOutStatus(Status.PENDING);
				book.setNumOfBorrows(book.getNumOfBorrows() + 1);
				transaction.setId(UUID.randomUUID().toString());
				this.bookRepository.save(book);
				return this.transactionRepository.save(transaction);
			}
			throw BadRequestException.create("Bad Request: The checkout date for the book must be before the check-in date");
		}
		throw NotFoundException.create("Not Found: The book you are trying to reserve is not available at the moment");
	}

	@Override
	public Transaction updateTransaction(TransactionDTO transactionDTO) {
		Transaction prevTransaction = this.transactionRepository.getOne(transactionDTO.getId());
		if (prevTransaction != null) {
			if (transactionDTO.getCheckOut().before(transactionDTO.getCheckIn())) {
				prevTransaction.setCheckIn(transactionDTO.getCheckIn());
				prevTransaction.setCheckOut(transactionDTO.getCheckOut());
				if (prevTransaction.getCheckInStatus() != transactionDTO.getCheckInStatus()) {
					if (transactionDTO.getCheckInStatus().compareTo(Status.ACCEPTED) == 0){
						increaseAvailableBooks(transactionDTO.getBookId());
					}
				}
				if (prevTransaction.getCheckOutStatus() != transactionDTO.getCheckOutStatus()) {
					if (transactionDTO.getCheckOutStatus().compareTo(Status.ACCEPTED) == 0) {
						decreaseAvailableBooks(transactionDTO.getBookId());
					}
				}
				prevTransaction.setCheckInStatus(transactionDTO.getCheckInStatus());
				prevTransaction.setCheckOutStatus(transactionDTO.getCheckOutStatus());
				return this.transactionRepository.save(prevTransaction);
			}
			throw BadRequestException.create("Bad Request: The checkout date for the book must be before the check-in date");
		}
		throw NotFoundException.create("Not Found: Transaction does not exist");
	}

	private void increaseAvailableBooks(String bookId) {
		Book book = this.bookRepository.getOne(bookId);
		if (book.getAvailableQty() < book.getTotalQty()) {
			book.setAvailableQty(book.getAvailableQty() + 1);
			this.bookRepository.save(book);
			return;
		}
		throw BadRequestException.create("Bad Request: Available books can not be more than total books in the library");
	}

	private void decreaseAvailableBooks(String bookId) {
		Book book = this.bookRepository.getOne(bookId);
		if (book.getAvailableQty() > 0) {
			book.setAvailableQty(book.getAvailableQty() - 1);
			this.bookRepository.save(book);
			return;
		}
		throw BadRequestException.create("Bad Request: The are no available books");
	}

	@Override
	public void deleteTransaction(String transId) {
		this.transactionRepository.deleteById(transId);
	}
}
