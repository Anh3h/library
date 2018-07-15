package com.courage.library.service.query;

import com.courage.library.model.User;
import org.springframework.data.domain.Page;

public interface UserQuery {

	User getUserById(String id);
	Page<User> getUsers(Integer pageNumber, Integer pageSize);
}
