package com.courage.library.unitTest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import com.courage.library.controller.CommentController;
import com.courage.library.factory.CommentFactory;
import com.courage.library.factory.JsonConverter;
import com.courage.library.model.Comment;
import com.courage.library.model.dto.CommentDTO;
import com.courage.library.service.command.CommentCommand;
import com.courage.library.service.query.CommentQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CommentController.class, secure = false)
public class CommentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CommentCommand commentCommand;

	@MockBean
	private CommentQuery commentQuery;

	@Test
	public void createCommentRequest_returnsHttp201AndCreatedComment() throws Exception {
		Comment comment = CommentFactory.instance();
		CommentDTO commentDTO = CommentFactory.convertToDTO(comment);
		given(this.commentCommand.createComment(any(CommentDTO.class))).willReturn(comment);

		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/comments")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(JsonConverter.toJSON(commentDTO)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("user.email").value(comment.getUser().getEmail()));
	}

	@Test
	public void updateCommentRequest_returnsHttp200AndUpdatedComment() throws Exception {
		Comment comment = CommentFactory.instance();
		CommentDTO commentDTO = CommentFactory.convertToDTO(comment);
		given(this.commentCommand.updateComment(any(CommentDTO.class))).willReturn(comment);

		this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/comments/" + comment.getId())
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(JsonConverter.toJSON(comment)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("user.email").value(comment.getUser().getEmail()));
	}

	@Test
	public void invalidUpdateCommentRequest_returnsHttp400() throws Exception {
		Comment comment = CommentFactory.instance();
		CommentDTO commentDTO = CommentFactory.convertToDTO(comment);
		String commentId = UUID.randomUUID().toString();

		this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/comments/" + commentId)
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(JsonConverter.toJSON(comment)))
				.andExpect(status().isBadRequest());
	}
}
