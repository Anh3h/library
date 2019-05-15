package com.courage.library.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.courage.library.model.Book;
import com.courage.library.model.Comment;
import com.courage.library.model.User;
import com.courage.library.model.dto.CommentDTO;
import org.apache.commons.lang3.RandomStringUtils;

public class CommentFactory {

	public static Comment instance() {
		String id = UUID.randomUUID().toString();
		String text = RandomStringUtils.random(20, true, true);
		User user = UserFactory.instance();
		Book book = BookFactory.instance();
		return new Comment(id, user, book, text);
	}

	public static CommentDTO convertToDTO(Comment comment) {
		return new CommentDTO(comment.getId(), comment.getUser().getId(),
				comment.getBook().getId(), comment.getText());
	}

	public static List instances() {
		List<Comment> comments = new ArrayList<>();
		comments.add(instance());
		comments.add(instance());
		comments.add(instance());
		return comments;
	}
}
