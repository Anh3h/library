package com.courage.library.factory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.courage.library.model.Book;
import com.courage.library.model.Notification;
import com.courage.library.model.User;
import org.apache.commons.lang3.RandomStringUtils;

public class NotificationFactory {

	public static Notification instance() {
		String id = UUID.randomUUID().toString();
		String action = RandomStringUtils.random(10, true, false);
		String content = RandomStringUtils.random(30, true, false);
		User user = UserFactory.instance();
		Book book = BookFactory.instance();
		return new Notification(id, action, content, new Date(), false, user, book);
	}

	public static List instances() {
		List<Notification> notifications = new ArrayList<>();
		notifications.add(instance());
		notifications.add(instance());
		notifications.add(instance());
		return notifications;
	}
}
