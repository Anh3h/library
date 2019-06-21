package com.courage.library.unitTest.service.query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.courage.library.exception.NotFoundException;
import com.courage.library.factory.TransactionFactory;
import com.courage.library.factory.UserFactory;
import com.courage.library.model.Transaction;
import com.courage.library.model.User;
import com.courage.library.repository.TransactionRepository;
import com.courage.library.repository.UserRepository;
import com.courage.library.service.query.TransactionQuery;
import com.courage.library.service.query.implementation.TransactionQueryImplementation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class TransactionQueryTest {

	@TestConfiguration
	static class TransactionQueryTestConfiguration {
		@Bean
		public TransactionQuery transactionQuery() {
			return new TransactionQueryImplementation();
		}
	}

	@Autowired
	private TransactionQuery transactionQuery;

	@MockBean
	private TransactionRepository transactionRepository;

	@MockBean
	private UserRepository userRepository;

	@Test
	public void getTransactions_returnsAPageOfTransactions() {
		Page<Transaction> transactions = new PageImpl<>(TransactionFactory.getInstancesForAUser(UserFactory.instance()));
		given(this.transactionRepository.findAll(PageRequest.of(0, 2))).willReturn(transactions);

		Page<Transaction> gottenTransactions = this.transactionQuery.getTransactions(1, 2);

		assertThat(gottenTransactions.getContent()).isEqualTo(transactions.getContent());
	}

	@Test
	public void getTransactionsByUser_returnsAPageOfTransactionsPerformedByAUser() {
		User user = UserFactory.instance();
		Page<Transaction> transactions = new PageImpl<>(TransactionFactory.getInstancesForAUser(user));
		given(this.transactionRepository.findByUser(user, PageRequest.of(0, 2))).willReturn(transactions);
		given(this.userRepository.getOne(user.getId())).willReturn(user);

		Page<Transaction> gottenTransactions = this.transactionQuery.getTransactionByUser(user.getId(), 1, 2);

		assertThat(gottenTransactions.getContent()).isEqualTo(transactions.getContent());
	}

	@Test
	public void getTransaction_returnsExistingTransaction() {
		Transaction transaction = TransactionFactory.instance();
		given(this.transactionRepository.getOne(transaction.getId())).willReturn(transaction);

		Transaction gottenTransaction = this.transactionQuery.getTransactionById(transaction.getId());

		assertThat(gottenTransaction).isEqualToComparingFieldByField(transaction);
	}

	@Test(expected = NotFoundException.class)
	public void getNonExistingTransaction_throwsNotFoundException() {
		String transactionId = TransactionFactory.instance().getId();
		given(this.transactionRepository.getOne(transactionId)).willReturn(null);

		this.transactionQuery.getTransactionById(transactionId);
	}

}
