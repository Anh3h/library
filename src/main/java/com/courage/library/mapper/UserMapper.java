package com.courage.library.mapper;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

import com.courage.library.model.Book;
import com.courage.library.model.User;
import com.courage.library.model.dto.UserDTO;
import com.courage.library.repository.BookRepository;
import com.courage.library.repository.RoleRepository;

public class UserMapper {

	private RoleRepository roleRepository;

	private BookRepository bookRepository;

	public UserMapper(RoleRepository roleRepository, BookRepository bookRepository) {
		this.roleRepository = roleRepository;
		this.bookRepository = bookRepository;
	}

	public User getUser(@NotNull UserDTO userDTO) {
		User user = new User(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(), userDTO.getUsername(),
				userDTO.getDob(), userDTO.getTelephone());
		if(userDTO.getId() != null)
			user.setId(userDTO.getId());
		if(userDTO.getPassword() != null)
			user.setPassword(user.getPassword());
		user.setRole(roleRepository.findById(userDTO.getRoleId()).get());
		Set<Book> books = new HashSet<>();
		if (userDTO.getFavoriteBookIds() != null) {
			userDTO.getFavoriteBookIds().forEach(bookId -> {
				books.add(this.bookRepository.findById(bookId).get());
			});
		}
		user.setFavoriteBooks(books);
		return user;
	}
}
