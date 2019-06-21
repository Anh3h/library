package com.courage.library.service.command.implementation;

import java.util.Date;
import java.util.UUID;

import com.courage.library.exception.NotFoundException;
import com.courage.library.mapper.CommentMapper;
import com.courage.library.model.Comment;
import com.courage.library.model.Notification;
import com.courage.library.model.dto.CommentDTO;
import com.courage.library.repository.BookRepository;
import com.courage.library.repository.CommentRepository;
import com.courage.library.repository.NotificationRepository;
import com.courage.library.repository.UserRepository;
import com.courage.library.service.command.CommentCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
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

	@Autowired
	private NotificationRepository notificationRepository;


	@Override
	public Comment createComment(CommentDTO commentDTO) {
		Comment comment = new CommentMapper(this.userRepository, this.bookRepository).getComment(commentDTO);
		comment.setId(UUID.randomUUID().toString());
		Comment newComment = this.commentRepository.save(comment);
		this.createNotification(newComment);
		return newComment;
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

	@Async
	protected void createNotification(Comment comment) {
		String commentTxt = comment.getUser() + " wrote a review about " + comment.getBook();
		comment.getBook().getUsers().forEach(user -> {
			Notification notification = new Notification(UUID.randomUUID().toString(),
					"New Comment", commentTxt, new Date(), false,
					user, comment.getBook());
			this.notificationRepository.save(notification);
		});
	}
}
