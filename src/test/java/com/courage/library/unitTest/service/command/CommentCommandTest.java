package com.courage.library.unitTest.service.command;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.HashSet;
import java.util.Set;

import com.courage.library.exception.NotFoundException;
import com.courage.library.factory.CommentFactory;
import com.courage.library.factory.NotificationFactory;
import com.courage.library.factory.UserFactory;
import com.courage.library.model.Comment;
import com.courage.library.model.Notification;
import com.courage.library.model.User;
import com.courage.library.model.dto.CommentDTO;
import com.courage.library.repository.BookRepository;
import com.courage.library.repository.CommentRepository;
import com.courage.library.repository.NotificationRepository;
import com.courage.library.repository.UserRepository;
import com.courage.library.service.command.CommentCommand;
import com.courage.library.service.command.implementation.CommentCommandImplementation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class CommentCommandTest {

	@TestConfiguration
	static class CommentCommandTestConfiguration {
		@Bean
		public CommentCommand commentCommand() {
			return new CommentCommandImplementation();
		}
	}

	@Autowired
	private CommentCommand commentCommand;

	@MockBean
	private CommentRepository commentRepository;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private BookRepository bookRepository;

	@MockBean
	private NotificationRepository notificationRepository;

	@Test
	public void createComment_returnsCreatedComment() {
		Comment comment = CommentFactory.instance();
		CommentDTO commentDTO = CommentFactory.convertToDTO(comment);
		given(this.commentRepository.save(any(Comment.class))).willReturn(comment);
		given(this.userRepository.getOne(comment.getUser().getId())).willReturn(comment.getUser());
		given(this.bookRepository.getOne(comment.getBook().getId())).willReturn(comment.getBook());

		Comment createdComment = this.commentCommand.createComment(commentDTO);

		assertThat(createdComment).isEqualToComparingFieldByField(comment);

	}

	//This test is suppose to fail but for some reason it passes. I need to figure out
	//How to test  what goes into my JPA repository. Same thing does for all my create and update test.
	@Test
	public void createCommentForFavoriteBook_returnsCreatedComment() {
		Comment comment = CommentFactory.instance();
		Notification notification = NotificationFactory.instance();
		Set<User> bookFavUsers = new HashSet<>();
		bookFavUsers.add(UserFactory.instance());
		comment.getBook().setUsers(bookFavUsers);
		CommentDTO commentDTO = CommentFactory.convertToDTO(comment);
		given(this.commentRepository.save(any(Comment.class))).willReturn(comment);
		given(this.notificationRepository.save(any(Notification.class))).willReturn(notification);

		Comment createdComment = this.commentCommand.createComment(commentDTO);

		assertThat(createdComment).isEqualToComparingFieldByField(comment);

	}

	@Test
	public void updateExistingComment_returnsUpdatedComment() {
		Comment comment = CommentFactory.instance();
		CommentDTO commentDTO = CommentFactory.convertToDTO(comment);
		given(this.commentRepository.save(any(Comment.class))).willReturn(comment);
		given(this.commentRepository.existsById(comment.getId())).willReturn(true);

		Comment updatedComment = this.commentCommand.updateComment(commentDTO);

		assertThat(updatedComment).isEqualToComparingFieldByField(comment);

	}

	@Test(expected = NotFoundException.class)
	public void updateNonExistingComment_throwsNotFoundException() {
		Comment comment = CommentFactory.instance();
		CommentDTO commentDTO = CommentFactory.convertToDTO(comment);
		given(this.commentRepository.existsById(comment.getId())).willReturn(false);

		this.commentCommand.updateComment(commentDTO);

	}

}
