package com.courage.library.mapper;

import com.courage.library.model.Comment;
import com.courage.library.model.dto.CommentDTO;
import com.courage.library.repository.BookRepository;
import com.courage.library.repository.UserRepository;

public class CommentMapper {

	private UserRepository userRepository;

	private BookRepository bookRepository;

	public CommentMapper(UserRepository userRepository, BookRepository bookRepository) {
		this.userRepository = userRepository;
		this.bookRepository = bookRepository;
	}

	public Comment getComment(CommentDTO commentDTO) {
		Comment comment = new Comment(commentDTO.getText());
		if (commentDTO.getId() != null)
			comment.setId(commentDTO.getId());

		comment.setBook(bookRepository.getOne(commentDTO.getBookId()));
		comment.setUser(userRepository.getOne(commentDTO.getUserId()));
		return comment;
	}

}
