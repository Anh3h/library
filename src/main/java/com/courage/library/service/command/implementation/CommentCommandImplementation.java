package com.courage.library.service.command.implementation;

import java.util.UUID;

import com.courage.library.exception.NotFoundException;
import com.courage.library.mapper.CommentMapper;
import com.courage.library.model.Comment;
import com.courage.library.model.dto.CommentDTO;
import com.courage.library.repository.BookRepository;
import com.courage.library.repository.CommentRepository;
import com.courage.library.repository.UserRepository;
import com.courage.library.service.command.CommentCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommentCommandImplementation implements CommentCommand {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BookRepository bookRepository;


	@Override
	public Comment createComment(CommentDTO commentDTO) {
		Comment comment = new CommentMapper(this.userRepository, this.bookRepository).getComment(commentDTO);
		comment.setId(UUID.randomUUID().toString());
		return this.commentRepository.save(comment);
	}

	@Override
	public Comment updateComment(CommentDTO commentDTO) {
		if (this.commentRepository.existsById(commentDTO.getId())) {
			Comment comment = new CommentMapper(this.userRepository, this.bookRepository).getComment(commentDTO);
			return this.commentRepository.save(comment);
		}
		throw NotFoundException.create("Not Found: Comment does not exit");
	}

	@Override
	public void deleteComment(String commentId) {
		this.commentRepository.deleteById(commentId);
	}
}
