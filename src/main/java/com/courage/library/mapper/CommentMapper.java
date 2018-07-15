package com.courage.library.mapper;

import com.courage.library.model.Comment;
import com.courage.library.model.dto.CommentDTO;
import com.courage.library.repository.BookRepository;
import com.courage.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class CommentMapper {

	@Autowired
	private static UserRepository userRepository;

	@Autowired
	private static BookRepository bookRepository;

	public static Comment getComment(CommentDTO commentDTO) {
		Comment comment = new Comment(commentDTO.getText());
		if (commentDTO.getId() == null)
			comment.setId(commentDTO.getId());
		comment.setBook(bookRepository.getOne(commentDTO.getId()));
		comment.setUser(userRepository.getOne(commentDTO.getUserId()));
		return comment;
	}

}
