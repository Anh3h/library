package com.courage.library.unitTest.service.query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;

import com.courage.library.exception.NotFoundException;
import com.courage.library.factory.BookFactory;
import com.courage.library.factory.CommentFactory;
import com.courage.library.model.Book;
import com.courage.library.model.Comment;
import com.courage.library.repository.BookRepository;
import com.courage.library.repository.CommentRepository;
import com.courage.library.service.query.CommentQuery;
import com.courage.library.service.query.implementation.CommentQueryImplementation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class CommentQueryTest {

	@TestConfiguration
	static class CommentQueryTestConfiguration {
		@Bean
		public CommentQuery commentQuery () {
			return new CommentQueryImplementation();
		}
	}

	@Autowired
	private CommentQuery commentQuery;

	@MockBean
	private CommentRepository commentRepository;

	@MockBean
	private BookRepository bookRepository;

	@Test
	public void getComments_returnsAPageOfComments() {
		List<Comment> commentList = new ArrayList<>();
		commentList.add(CommentFactory.instance());
		Page<Comment> pagedComments = new PageImpl<>(commentList);
		given(this.commentRepository.findAll(PageRequest.of(0, 2)))
			.willReturn(pagedComments);

		Page<Comment> gottenComments = this.commentQuery.getComments(1, 2);

		assertThat(gottenComments.getContent()).isEqualTo(pagedComments.getContent());
	}

	@Test
	public void getCommentsForGivenBook_returnsAPageOfComments() {
		List<Comment> commentList = new ArrayList<>();
		Book book = BookFactory.instance();
		commentList.add(CommentFactory.instance());
		commentList.add(CommentFactory.instance());
		commentList.forEach(comment -> comment.setBook(book));
		Page<Comment> pagedComments = new PageImpl<>(commentList);

		given(this.bookRepository.getOne(book.getId())).willReturn(book);
		given(this.commentRepository.findByBook(book, PageRequest.of(0, 2)))
				.willReturn(pagedComments);

		Page<Comment> gottenComments = this.commentQuery.findCommentsByBook(book.getId(), 1, 2);

		assertThat(gottenComments.getContent()).isEqualTo(pagedComments.getContent());
	}

	@Test
	public void getComments_returnsAnExistingComments() {
		Comment comment = CommentFactory.instance();
		given(this.commentRepository.getOne(comment.getId())).willReturn(comment);

		Comment gottenComment = this.commentQuery.getCommentById(comment.getId());

		assertThat(gottenComment).isEqualToComparingFieldByField(comment);
	}

	@Test(expected = NotFoundException.class)
	public void getNonExistingComments_throwsNotFoundException() {
		String commentId = CommentFactory.instance().getId();
		given(this.commentRepository.getOne(commentId)).willReturn(null);

		this.commentQuery.getCommentById(commentId);
	}
}
