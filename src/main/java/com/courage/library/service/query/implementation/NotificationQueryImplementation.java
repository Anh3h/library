package com.courage.library.service.query.implementation;

import com.courage.library.model.Notification;
import com.courage.library.model.User;
import com.courage.library.repository.NotificationRepository;
import com.courage.library.repository.UserRepository;
import com.courage.library.service.query.NotificationQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NotificationQueryImplementation implements NotificationQuery {

	@Autowired
	private NotificationRepository notificationRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public Page<Notification> getNotifications(String userId, Integer pageNumber, Integer pageSize) {
		User user = this.userRepository.getOne(userId);
		return this.notificationRepository.findByUser(user, PageRequest.of(pageNumber-1, pageSize));
	}

	@Override
	public Page<Notification> getUndoneNotifications(String userId, Integer pageNumber, Integer pageSize) {
		User user = this.userRepository.getOne(userId);
		return this.notificationRepository.findByUserAndDone(user,false, PageRequest.of(pageNumber-1, pageSize));
	}
}
