package com.courage.library.factory;

import java.util.UUID;

import com.courage.library.model.Role;
import org.apache.commons.lang3.RandomStringUtils;

public class RoleFactory {

	public static Role instance() {
		String id = UUID.randomUUID().toString();
		String name = RandomStringUtils.random(10, true, true);

		return new Role(id, name);
	}
}
