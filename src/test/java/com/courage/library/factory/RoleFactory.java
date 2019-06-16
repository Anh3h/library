package com.courage.library.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.courage.library.model.Role;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;

public class RoleFactory {

	public static Role instance() {
		String id = UUID.randomUUID().toString();
		String name = RandomStringUtils.random(10, true, true);

		return new Role(id, name);
	}

	public static List<Role> instances() {
		List<Role> roles = new ArrayList<Role>();
		roles.add(instance());
		roles.add(instance());
		roles.add(instance());
		return roles;
	}
}
