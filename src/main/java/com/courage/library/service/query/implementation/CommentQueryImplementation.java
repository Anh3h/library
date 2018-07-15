package com.courage.library.service.query.implementation;

import com.courage.library.exception.NotFoundException;
import com.courage.library.model.Comment;
import com.courage.library.repository.CommentRepository;
import com.courage.library.service.query.CommentQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommentQueryImplementation implements CommentQuery {

	@Autowired
	private CommentRepository commentRepository;

	@Override
	public Comment getCommentById(Long commentId) {
		Comment comment = this.commentRepository.getOne(commentId);
		if (comment == null) {
			throw NotFoundException.create("Not Found: Comment does not exist");
		}
		return comment;
	}

	@Override
	public Page<Comment> getComments(Integer pageNumber, Integer pageSize) {
		return this.commentRepository.findAll(PageRequest.of(pageNumber-1, pageSize));
	}
}
