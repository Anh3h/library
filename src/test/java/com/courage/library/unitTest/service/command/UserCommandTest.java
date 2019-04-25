package com.courage.library.unitTest.service.command;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.courage.library.exception.ConflictException;
import com.courage.library.exception.NotFoundException;
import com.courage.library.factory.UserFactory;
import com.courage.library.mapper.UserMapper;
import com.courage.library.model.User;
import com.courage.library.model.dto.UserDTO;
import com.courage.library.repository.BookRepository;
import com.courage.library.repository.RoleRepository;
import com.courage.library.repository.UserRepository;
import com.courage.library.service.command.UserCommand;
import com.courage.library.service.command.implementation.UserCommandImplementation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class UserCommandTest {

	@TestConfiguration
	static class UserCommandTestConfiguration {
		@Bean
		public UserCommand userCommand() {
			return new UserCommandImplementation();
		}
	}

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private RoleRepository roleRepository;

	@MockBean
	private BookRepository bookRepository;

	@MockBean
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserCommand userCommand;

	@Test
	public void createUser_returnsCreatedUser() {
		User user = UserFactory.instance();
		UserDTO userDTO = new UserDTO(user.getId(), user.getFirstName(), user.getLastName(),
				user.getUsername(), user.getEmail(), user.getPassword(),
				user.getDob(), user.getTelephone(), user.getRole().getId());
		given(this.userRepository.save(any(User.class))).willReturn(user);
		given(this.userRepository.findByEmail(user.getEmail())).willReturn(null);
		given(this.roleRepository.getOne(userDTO.getRoleId())).willReturn(user.getRole());
		given(this.passwordEncoder.encode(user.getPassword())).willReturn(user.getPassword());

		User createdUser = this.userCommand.createUser(userDTO);

		assertThat(createdUser).isEqualToComparingFieldByField(user);
	}

	@Test(expected = ConflictException.class)
	public void createExistingUser_throwsConflictException() {
		User user = UserFactory.instance();
		UserDTO userDTO = new UserDTO(user.getId(), user.getFirstName(), user.getLastName(),
				user.getUsername(), user.getEmail(), user.getPassword(),
				user.getDob(), user.getTelephone(), user.getRole().getId());
		given(this.userRepository.findByEmail(user.getEmail())).willReturn(user);

		this.userCommand.createUser(userDTO);
	}

	@Test
	public void updateExistingUser_returnsUpdatedUser() {
		User user = UserFactory.instance();
		UserDTO userDTO = new UserDTO(user.getId(), user.getFirstName(), user.getLastName(),
				user.getUsername(), user.getEmail(), user.getPassword(),
				user.getDob(), user.getTelephone(), user.getRole().getId());
		given(this.userRepository.getOne(user.getId())).willReturn(user);
		given(this.userRepository.save(any(User.class))).willReturn(user);

		User updatedUser = this.userCommand.updateUser(userDTO);

		assertThat(updatedUser).isEqualToComparingFieldByField(user);
	}

	@Test(expected = NotFoundException.class)
	public void updateNonExistingUser_throwsNonFoundException() {
		User user = UserFactory.instance();
		UserDTO userDTO = new UserDTO(user.getId(), user.getFirstName(), user.getLastName(),
				user.getUsername(), user.getEmail(), user.getPassword(),
				user.getDob(), user.getTelephone(), user.getRole().getId());
		given(this.userRepository.getOne(user.getId())).willReturn(null);

		this.userCommand.updateUser(userDTO);
	}
}
