package com.courage.library.repository;

import com.courage.library.model.Book;
import com.courage.library.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {

	Page<Comment> findByBook(Book book, Pageable page);
}
