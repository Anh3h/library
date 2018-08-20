package com.courage.library.service.query.implementation;

import com.courage.library.exception.NotFoundException;
import com.courage.library.model.User;
import com.courage.library.repository.UserRepository;
import com.courage.library.service.query.UserQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserQueryImplementation implements UserQuery {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User getUserById(String id) {
		User user = this.userRepository.getOne(id);
		if (user == null) {
			throw NotFoundException.create("Not Found: User does not exist");
		}
		return user;
	}

	@Override
	public User getUserByEmail(String email) {
		User user = this.userRepository.findByEmail(email);
		if (user == null) {
			throw NotFoundException.create("Not Found: User does not exist");
		}
		return user;
	}

	@Override
	public Page<User> getUsers(Integer pageNumber, Integer pageSize) {
		return this.userRepository.findAll(PageRequest.of(pageNumber-1, pageSize));
	}
}
