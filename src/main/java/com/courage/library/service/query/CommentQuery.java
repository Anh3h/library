package com.courage.library.service.query;

import com.courage.library.model.Comment;
import org.springframework.data.domain.Page;

public interface CommentQuery {

	Comment getCommentById(Long commentId);
	Page<Comment> getComments(Integer pageNumber, Integer pageSize);
}
