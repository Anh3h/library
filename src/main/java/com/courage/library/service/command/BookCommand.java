package com.courage.library.service.command;

import com.courage.library.model.Book;
import com.courage.library.model.dto.BookDTO;

public interface BookCommand {

	Book createBook(BookDTO book);
	Book updateBook(BookDTO book);
	void deleteBook(Long bookId);
}
