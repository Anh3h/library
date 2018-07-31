package com.courage.library.service.command;

import com.courage.library.model.Book;
import com.courage.library.model.dto.BookDTO;
import org.springframework.web.multipart.MultipartFile;

public interface BookCommand {

	Book createBook(BookDTO book);
	Book uploadImage(String bookId, MultipartFile image);
	Book updateBook(BookDTO book);
	void deleteBook(String bookId);
}
