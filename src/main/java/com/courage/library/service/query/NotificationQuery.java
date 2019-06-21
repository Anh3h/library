package com.courage.library.service.query;

import com.courage.library.model.Notification;
import org.springframework.data.domain.Page;

public interface NotificationQuery {

	Page<Notification> getNotifications(String userId, Integer pageNumber, Integer pageSize);
	Page<Notification> getUndoneNotifications(String userId, Integer pageNumber, Integer pageSize);

}
