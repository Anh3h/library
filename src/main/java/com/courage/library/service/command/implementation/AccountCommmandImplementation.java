package com.courage.library.service.command.implementation;

import com.courage.library.exception.NotFoundException;
import com.courage.library.model.User;
import com.courage.library.model.dto.PasswordDTO;
import com.courage.library.repository.UserRepository;
import com.courage.library.service.command.AccountCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountCommmandImplementation implements AccountCommand {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User updatePassword(PasswordDTO passwordDTO) {
		User user = this.userRepository.findByEmail(passwordDTO.getEmail());
		if (user != null) {
			user.setPassword(this.passwordEncoder.encode(passwordDTO.getPassword()));
			this.userRepository.save(user);
			return user;
		}
		throw NotFoundException.create("User account with the provided email doesn't exist");
	}
}
