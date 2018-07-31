package com.courage.library.service.command.implementation;

import java.util.UUID;

import com.courage.library.model.Notification;
import com.courage.library.repository.NotificationRepository;
import com.courage.library.service.command.NotificationCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class NotificationCommandImplementation implements NotificationCommand {

	@Autowired
	private NotificationRepository notificationRepository;

	@Override
	public Notification createNotification(Notification notification) {
		notification.setId(UUID.randomUUID().toString());
		return notificationRepository.save(notification);
	}

	@Override
	public Notification updateNotification(Notification notification) {
		return notificationRepository.save(notification);
	}
}
