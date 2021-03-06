package com.courage.library.controller;

import java.util.Map;

import com.courage.library.exception.BadRequestException;
import com.courage.library.model.Book;
import com.courage.library.model.dto.BookDTO;
import com.courage.library.service.command.BookCommand;
import com.courage.library.service.query.BookQuery;
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
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("api/v1/books")
public class BookController {

	@Autowired
	private BookQuery bookQuery;

	@Autowired
	private BookCommand bookCommand;

	@ApiOperation("Add a book")
	@PostMapping(
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Book> createBook(@RequestBody BookDTO book) {
		Book newBook = this.bookCommand.createBook(book);
		return new ResponseEntity<>(newBook, HttpStatus.CREATED);
	}

	@ApiOperation("Get all/some books")
	@GetMapping(
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Page<Book>> getBooks(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sort", required = false) Boolean sort,
			@RequestParam(value = "title", required = false) String title, @RequestParam(value = "author", required = false) String author,
			@RequestParam(value = "topicId", required = false) String topicId) {
		Map<String, Integer> pageAttributes = PageValidator.validatePageAndSize(page, size);
		page = pageAttributes.get("page");
		size = pageAttributes.get("size");
		if (sort != null && sort) {
			Page<Book> books = this.bookQuery.getPopularBooks(page, size);
			return new ResponseEntity<>(books, HttpStatus.OK);
		}
		if (title != null) {
			Page<Book> books = this.bookQuery.getBooksByTitle(title, page, size);
			return new ResponseEntity<>(books, HttpStatus.OK);
		}
		if (topicId != null) {
			Page<Book> books = this.bookQuery.getBooksByTopic(topicId, page, size);
			return new ResponseEntity<>(books, HttpStatus.OK);
		}
		if (author != null) {
			Page<Book> books = this.bookQuery.getBooksByAuthor(author, page, size);
			return new ResponseEntity<>(books, HttpStatus.OK);
		}
		Page<Book> books = this.bookQuery.getBooks(page, size);
		return new ResponseEntity<>(books, HttpStatus.OK);
	}

	@ApiOperation("Get a book")
	@GetMapping(
			value = "{bookId}",
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Book> getBook(@PathVariable("bookId") String bookId) {
		Book book = this.bookQuery.getBookById(bookId);
		return new ResponseEntity<>(book, HttpStatus.OK);
	}

	@ApiOperation("Update a book")
	@PutMapping(
			value = "/{bookId}",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Book> updateBook(@RequestBody BookDTO book, @PathVariable("bookId") String bookId) {
		if (book.getId().compareTo(bookId) == 0) {
			Book updatedBook = this.bookCommand.updateBook(book);
			return new ResponseEntity<>(updatedBook, HttpStatus.OK);
		}
		throw BadRequestException.create("Bad Request: Book id in path parameter does not match that in book object");
	}

	@ApiOperation("Upload cover image of book")
	@PostMapping(
			value = "/{bookId}"
	)
	public ResponseEntity<Book> uploadCoverImage(@PathVariable("bookId") String bookId,
			@RequestParam(value = "image", required = true) MultipartFile image) {
		Book book = this.bookCommand.uploadImage(bookId, image);
		return new ResponseEntity<>(book, HttpStatus.OK);
	}

	@ApiOperation("Delete a book")
	@DeleteMapping(
			value = "/{bookId}"
	)
	public ResponseEntity<HttpStatus> deleteBook(@PathVariable("bookId") String bookId) {
		this.bookCommand.deleteBook(bookId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
