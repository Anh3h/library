package com.courage.library.repository;

import com.courage.library.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

	Page<Notification> findByUser(Long userId, Pageable page);
	Page<Notification> findByDone(Boolean done, Pageable page);

}