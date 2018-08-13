package com.courage.library.repository;

import com.courage.library.model.Notification;
import com.courage.library.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

	Page<Notification> findByUser(User user, Pageable page);
	Page<Notification> findByUserAndDone(User user, Boolean done, Pageable page);

}
