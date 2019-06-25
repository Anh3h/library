package com.courage.library.service.query;

import com.courage.library.model.User;
import org.springframework.data.domain.Page;

public interface UserQuery {

	User getUserById(String id);
	User getUserByEmail(String email);
	User getUserByUsername(String username);
	Page<User> getUsers(Integer pageNumber, Integer pageSize);
}
