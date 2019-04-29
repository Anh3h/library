package com.courage.library.unitTest.service.command;

import com.courage.library.repository.UserRepository;
import com.courage.library.service.command.AccountCommand;
import com.courage.library.service.command.implementation.AccountCommmandImplementation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class AccountCommandTest {

	@TestConfiguration
	static class AccountCommandTestConfiguration {
		@Bean
		public AccountCommand accountCommand() {
			return new AccountCommmandImplementation();
		}
	}

	@Autowired
	private AccountCommand accountCommand;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private PasswordEncoder passwordEncoder;

	@Test
	public void updatePassword_ReturnUserAccount() {

	}

}
