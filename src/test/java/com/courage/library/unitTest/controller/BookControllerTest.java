package com.courage.library.unitTest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import com.courage.library.controller.BookController;
import com.courage.library.factory.BookFactory;
import com.courage.library.factory.JsonConverter;
import com.courage.library.model.Book;
import com.courage.library.model.dto.BookDTO;
import com.courage.library.service.command.BookCommand;
import com.courage.library.service.query.BookQuery;
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
@WebMvcTest(value = BookController.class, secure = false)
public class BookControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BookCommand bookCommand;

	@MockBean
	private BookQuery bookQuery;

	@Test
	public void createBookRequest_returnsHttp201AndCreatedBook() throws Exception {

		Book book = BookFactory.instance();
		BookDTO bookDTO = BookFactory.convertToDTO(book);
		given(this.bookCommand.createBook(any(BookDTO.class))).willReturn(book);

		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/books")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(JsonConverter.toJSON(bookDTO)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("title").value(book.getTitle()));

	}

	@Test
	public void updateBookRequest_returnsHttp200AndUpdatedBook() throws Exception {

		Book book = BookFactory.instance();
		BookDTO bookDTO = BookFactory.convertToDTO(book);
		given(this.bookCommand.updateBook(any(BookDTO.class))).willReturn(book);

		this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/books/" + book.getId())
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(JsonConverter.toJSON(bookDTO)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("title").value(book.getTitle()));

	}

	@Test
	public void invalidUpdateBookRequest_returnsHttp400() throws Exception {

		Book book = BookFactory.instance();
		BookDTO bookDTO = BookFactory.convertToDTO(book);
		String bookId = UUID.randomUUID().toString();
		given(this.bookCommand.updateBook(any(BookDTO.class))).willReturn(book);

		this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/books/" + bookId)
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(JsonConverter.toJSON(bookDTO)))
				.andExpect(status().isBadRequest());

	}

}
