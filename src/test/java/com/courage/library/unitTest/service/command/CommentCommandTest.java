package com.courage.library.unitTest.service.command;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.courage.library.exception.NotFoundException;
import com.courage.library.factory.CommentFactory;
import com.courage.library.model.Comment;
import com.courage.library.model.Notification;
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
