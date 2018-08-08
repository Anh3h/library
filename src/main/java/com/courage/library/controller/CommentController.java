package com.courage.library.controller;

import java.util.Map;

import com.courage.library.exception.BadRequestException;
import com.courage.library.model.Book;
import com.courage.library.model.Comment;
import com.courage.library.model.dto.CommentDTO;
import com.courage.library.service.command.CommentCommand;
import com.courage.library.service.query.CommentQuery;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("api/v1/comments")
public class CommentController {

	@Autowired
	private CommentQuery commentQuery;

	@Autowired
	private CommentCommand commentCommand;

	@ApiOperation("Add a comment")
	@PostMapping(
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Comment> createComment(@RequestBody CommentDTO comment) {
		Comment newComment = this.commentCommand.createComment(comment);
		return new ResponseEntity<>(newComment, HttpStatus.CREATED);
	}

	@ApiOperation("Get all/some comment")
	@GetMapping(
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Page<Comment>> getComments(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "book", required = false) String book) {
		Map<String, Integer> pageAttributes = PageValidator.validatePageAndSize(page, size);
		page = pageAttributes.get("page");
		size = pageAttributes.get("size");
		if (book != null) {
			Page<Comment> comments = this.commentQuery.findCommentsByBook(book, page, size);
			return new ResponseEntity<>(comments, HttpStatus.OK);
		}
		Page<Comment> comments = this.commentQuery.getComments(page, size);
		return new ResponseEntity<>(comments, HttpStatus.OK);
	}

	@ApiOperation("Get a comment")
	@GetMapping(
			value = "/{commentId}",
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Comment> getComment(@PathVariable("commentId") String commentId) {
		Comment comment = this.commentQuery.getCommentById(commentId);
		return new ResponseEntity<>(comment, HttpStatus.OK);
	}

	@ApiOperation("Update a comment")
	@PutMapping(
			value = "/{commentId}",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Comment> updateComment(@RequestBody CommentDTO comment, @PathVariable("commentId") String commentId) {
		if (comment.getId().compareTo(commentId) == 0) {
			Comment updatedComment = this.commentCommand.updateComment(comment);
			return new ResponseEntity<>(updatedComment, HttpStatus.OK);
		}
		throw BadRequestException.create("Bad Request: Comment id in path parameter does not match that in comment object");
	}

	@ApiOperation("Delete a comment")
	@DeleteMapping(
			value = "/{commentId}"
	)
	public ResponseEntity<HttpStatus> deleteComment(@PathVariable("commentId") String commentId) {
		this.commentCommand.deleteComment(commentId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
