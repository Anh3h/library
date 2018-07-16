package com.courage.library.service.command.implementation;

import java.util.UUID;

import com.courage.library.exception.ConflictException;
import com.courage.library.exception.NotFoundException;
//import com.courage.library.mapper.UserMapper;
import com.courage.library.mapper.UserMapper;
import com.courage.library.model.User;
import com.courage.library.model.dto.UserDTO;
import com.courage.library.repository.BookRepository;
import com.courage.library.repository.RoleRepository;
import com.courage.library.repository.UserRepository;
import com.courage.library.service.command.UserCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserCommandImplementation implements UserCommand {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User createUser(UserDTO userDTO) {
		if (this.userRepository.findByEmail(userDTO.getEmail()) == null) {
			User user = new UserMapper(this.roleRepository, this.bookRepository).getUser(userDTO);
			user.setId(UUID.randomUUID().toString());
			user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
			return this.userRepository.save(user);
		}
		throw ConflictException.create("Conflict: Email already exist");
	}

	@Override
	public User updateUser(UserDTO userDTO) {
		User prevUser = this.userRepository.getOne(userDTO.getId());
		if (prevUser != null) {
			User user = new UserMapper(this.roleRepository, this.bookRepository).getUser(userDTO);
			user.setPassword(prevUser.getPassword());
			return this.userRepository.save(user);
		}
		throw NotFoundException.create("Not Found: User account does not exist");
	}

	@Override
	public void deleteUser(String userId) {
		this.userRepository.deleteById(userId);
	}
}
