package com.courage.library.service.command;

import com.courage.library.model.Notification;

public interface NotificationCommand {

	Notification createNotification(Notification notification);
	Notification updateNotification(String notification);
}
