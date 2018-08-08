package com.courage.library.service.query;

import com.courage.library.model.Book;
import com.courage.library.model.Comment;
import org.springframework.data.domain.Page;

public interface CommentQuery {

	Comment getCommentById(String commentId);
	Page<Comment> getComments(Integer pageNumber, Integer pageSize);
	Page<Comment> findCommentsByBook(String bookId, Integer pageNumber, Integer pageSize);
}
