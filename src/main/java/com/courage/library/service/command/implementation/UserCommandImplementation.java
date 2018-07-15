package com.courage.library.service.command.implementation;

import com.courage.library.exception.ConflictException;
import com.courage.library.exception.NotFoundException;
import com.courage.library.mapper.UserMapper;
import com.courage.library.model.User;
import com.courage.library.model.dto.UserDTO;
import com.courage.library.repository.UserRepository;
import com.courage.library.service.command.UserCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserCommandImplementation implements UserCommand {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User createUser(UserDTO userDTO) {
		if (this.userRepository.findByEmail(userDTO.getEmail()) == null) {
			User user = UserMapper.getUser(userDTO);
			return this.userRepository.save(user);
		}
		throw ConflictException.create("Conflict: Email already exist");
	}

	@Override
	public User updateUser(UserDTO userDTO) {
		if (this.userRepository.existsById(userDTO.getId())) {
			User user = UserMapper.getUser(userDTO);
			return this.userRepository.save(user);
		}
		throw NotFoundException.create("Not Found: User account does not exist");
	}

	@Override
	public void deleteUser(Long userId) {
		this.userRepository.deleteById(userId);
	}
}
