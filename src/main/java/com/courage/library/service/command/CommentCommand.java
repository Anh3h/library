package com.courage.library.service.command;

import com.courage.library.model.Comment;
import com.courage.library.model.dto.CommentDTO;

public interface CommentCommand {

	Comment createComment(CommentDTO comment);
	Comment updateComment(CommentDTO comment);
	void deleteComment(Long commentId);
}
