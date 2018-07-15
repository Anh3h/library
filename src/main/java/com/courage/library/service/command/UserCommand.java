package com.courage.library.service.command;

import com.courage.library.model.User;
import com.courage.library.model.dto.UserDTO;

public interface UserCommand {

	User createUser(UserDTO user);
	User updateUser(UserDTO user);
	void deleteUser(Long userId);
}
