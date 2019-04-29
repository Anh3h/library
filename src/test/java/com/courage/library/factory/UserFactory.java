package com.courage.library.factory;

import java.util.Date;
import java.util.UUID;

import com.courage.library.model.Role;
import com.courage.library.model.User;
import org.apache.commons.lang3.RandomStringUtils;

public class UserFactory {

	public static User instance() {
		String id = UUID.randomUUID().toString();
		String firstName = RandomStringUtils.random(10, true, false);
		String lastName = RandomStringUtils.random(10, true, false);
		String username = RandomStringUtils.random(10, true, true);
		String password = RandomStringUtils.random(10, true, true);
		String email = RandomStringUtils.random(10, true, true) +
				"@example.com";
		Role role = RoleFactory.instance();
		return new User(id, firstName, lastName, username, email, password, new Date(),
				"", role);
	}
}