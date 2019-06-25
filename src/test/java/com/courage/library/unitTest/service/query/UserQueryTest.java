package com.courage.library.unitTest.service.query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.courage.library.exception.NotFoundException;
import com.courage.library.factory.UserFactory;
import com.courage.library.model.User;
import com.courage.library.repository.UserRepository;
import com.courage.library.service.query.UserQuery;
import com.courage.library.service.query.implementation.UserQueryImplementation;
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
public class UserQueryTest {

	@TestConfiguration
	static class UserQueryTestConfiguration {
		@Bean
		public UserQuery userQuery() {
			return new UserQueryImplementation();
		}
	}

	@MockBean
	private UserRepository userRepository;

	@Autowired
	private UserQuery userQuery;

	@Test
	public void getUsers_returnsAPageUser() {
		List<User> users = new ArrayList<>();
		users.add(UserFactory.instance());
		Page<User> pagedUsers = new PageImpl<>(users);
		given(this.userRepository.findAll(PageRequest.of(0,2)))
				.willReturn(pagedUsers);

		Page<User> gottenUsers = this.userQuery.getUsers(1,2);

		assertThat(gottenUsers.getContent()).isEqualTo(pagedUsers.getContent());
	}

	@Test
	public void getUser_returnsExistingUser() {
		User user = UserFactory.instance();
		given(this.userRepository.findById(user.getId())).willReturn(Optional.of(user));

		User gottenUser = this.userQuery.getUserById(user.getId());

		assertThat(gottenUser).isEqualToComparingFieldByField(user);
	}

	@Test(expected = NotFoundException.class)
	public void getNonExistingUser_throwsNotFoundException() {
		String userId = UUID.randomUUID().toString();
		given(this.userRepository.findById(userId)).willReturn(Optional.empty());

		this.userQuery.getUserById(userId);
	}
}
