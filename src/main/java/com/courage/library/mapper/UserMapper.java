package com.courage.library.mapper;

import javax.validation.constraints.NotNull;

import com.courage.library.model.User;
import com.courage.library.model.dto.UserDTO;
import com.courage.library.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserMapper {

	@Autowired
	private static RoleRepository roleRepository;

	public static User getUser(@NotNull UserDTO userDTO) {
		User user = new User(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getUsername(), userDTO.getEmail(),
				userDTO.getPassword(), userDTO.getDob(), userDTO.getTelephone());
		if(userDTO.getId() != null)
			user.setId(userDTO.getId());
		user.setRole(roleRepository.getOne(userDTO.getRoleId()));
		user.setFavoriteBooks(userDTO.getFavoriteBooks());
		return user;
	}

}
