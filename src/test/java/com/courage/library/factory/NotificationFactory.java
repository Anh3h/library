package com.courage.library.factory;

import java.util.Date;
import java.util.UUID;

import com.courage.library.model.Notification;
import org.apache.commons.lang3.RandomStringUtils;

public class NotificationFactory {

	public static Notification instance() {
		String id = UUID.randomUUID().toString();
		String action = RandomStringUtils.random(10, true, false);
		String content = RandomStringUtils.random(30, true, false);
		return new Notification(id, action, content, new Date(), false);
	}
}
