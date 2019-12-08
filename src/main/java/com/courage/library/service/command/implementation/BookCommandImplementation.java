package com.courage.library.service.command.implementation;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import com.courage.library.exception.BadRequestException;
import com.courage.library.exception.ConflictException;
import com.courage.library.exception.NotFoundException;
import com.courage.library.mapper.BookMapper;
import com.courage.library.model.Book;
import com.courage.library.model.Notification;
import com.courage.library.model.dto.BookDTO;
import com.courage.library.repository.BookRepository;
import com.courage.library.repository.NotificationRepository;
import com.courage.library.repository.TopicRepository;
import com.courage.library.service.command.BookCommand;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class BookCommandImplementation implements BookCommand {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private TopicRepository topicRepository;

	@Autowired
	private NotificationRepository notificationRepository;

	private Bucket bucket;

	public BookCommandImplementation() {
		Storage storage = StorageOptions.getDefaultInstance().getService();
		bucket = storage.get("e-library-1");
	}

	public BookCommandImplementation(Bucket bucket) {
		bucket = bucket;
	}

	@Override
	public Book createBook(BookDTO bookDTO) {
		if (this.bookRepository.findByIsbn(bookDTO.getIsbn()) == null) {
			Book book = new BookMapper(this.topicRepository, this.bookRepository).getBook(bookDTO);
			book.setId(UUID.randomUUID().toString());
			book.setAvailableQty(book.getTotalQty());
			return this.bookRepository.save(book);
		}
		throw ConflictException.create("Conflict: Book with ISBN, {0} already exist", bookDTO.getIsbn());
	}

	@Override
	public Book uploadImage(String bookId, MultipartFile image) {
		Book book = this.bookRepository.getOne(bookId);
		this.validateFile(image);
		String imageName = image.getOriginalFilename();
		final String extension = (imageName.substring(imageName.lastIndexOf('.') + 1));
		DateTimeFormatter dtf = DateTimeFormat.forPattern("-YYYY-MM-dd-HHmmssSSS");
		String dtString = DateTime.now(DateTimeZone.UTC).toString(dtf);
		final String updatedFileName = StringUtils.cleanPath(image.getOriginalFilename()).substring(0, imageName.indexOf('.'))
				+ dtString + "." + extension;
		try {
			BlobInfo blobInfo = bucket.create(updatedFileName, image.getInputStream());
			book.setCoverImage(blobInfo.getMediaLink());
			this.bookRepository.save(book);
			return book;
		} catch (IOException ex) {
			throw BadRequestException.create(ex.getMessage());
		}
	}

	@Override
	public Book updateBook(BookDTO bookDTO) {
		if (this.bookRepository.existsById(bookDTO.getId())) {
			Book book = new BookMapper(this.topicRepository, this.bookRepository).getFromExistingBook(bookDTO);
			Book newBook = this.bookRepository.save(book);
			newBook.getUsers().forEach(user -> {
				Notification notification = new Notification(UUID.randomUUID().toString(),
						"Update book", "There updates on your favorite book", new Date(), false,
						user, book);
				this.notificationRepository.save(notification);
			});
			return newBook;
		}
		throw NotFoundException.create("Not Found: Book does not exist");
	}

	@Override
	public void deleteBook(String bookId) {
		this.bookRepository.deleteById(bookId);
	}

	private void validateFile(MultipartFile file) {
		if (!file.isEmpty() || !StringUtils.cleanPath(file.getOriginalFilename()).contains(".")) {
			if (!file.getContentType().contains("image"))
				throw BadRequestException.create("Incorrect file format {0}", file.getContentType());
		}
	}
}
