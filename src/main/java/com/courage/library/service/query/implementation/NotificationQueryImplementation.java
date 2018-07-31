package com.courage.library.service.query.implementation;

import com.courage.library.model.Notification;
import com.courage.library.repository.NotificationRepository;
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

	@Override
	public Page<Notification> getNotifications(Integer pageNumber, Integer pageSize) {
		return this.notificationRepository.findAll(PageRequest.of(pageNumber-1, pageSize));
	}

	@Override
	public Page<Notification> getUndoneNotifications(Integer pageNumber, Integer pageSize) {
		return this.notificationRepository.findByDone(false, PageRequest.of(pageNumber-1, pageSize));
	}
}
