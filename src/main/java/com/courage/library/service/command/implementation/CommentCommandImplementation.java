package com.courage.library.service.command.implementation;

import com.courage.library.exception.NotFoundException;
import com.courage.library.mapper.CommentMapper;
import com.courage.library.model.Comment;
import com.courage.library.model.dto.CommentDTO;
import com.courage.library.repository.CommentRepository;
import com.courage.library.service.command.CommentCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommentCommandImplementation implements CommentCommand {

	@Autowired
	private CommentRepository commentRepository;

	@Override
	public Comment createComment(CommentDTO commentDTO) {
		Comment comment = CommentMapper.getComment(commentDTO);
		return this.commentRepository.save(comment);
	}

	@Override
	public Comment updateComment(CommentDTO commentDTO) {
		if (this.commentRepository.existsById(commentDTO.getId())) {
			Comment comment = CommentMapper.getComment(commentDTO);
			return this.commentRepository.save(comment);
		}
		throw NotFoundException.create("Not Found: Comment does not exit");
	}

	@Override
	public void deleteComment(Long commentId) {
		this.commentRepository.deleteById(commentId);
	}
}
