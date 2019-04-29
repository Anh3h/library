package com.courage.library.unitTest.service.query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;

import com.courage.library.factory.NotificationFactory;
import com.courage.library.factory.UserFactory;
import com.courage.library.model.Notification;
import com.courage.library.model.User;
import com.courage.library.repository.NotificationRepository;
import com.courage.library.repository.UserRepository;
import com.courage.library.service.query.NotificationQuery;
import com.courage.library.service.query.implementation.NotificationQueryImplementation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class NotificatiionQueryTest {

	@TestConfiguration
	static class NotificationQueryTestConfiguration {
		@Bean
		public NotificationQuery notificationQuery() {
			return new NotificationQueryImplementation();
		}
	}

	@Autowired
	private NotificationQuery notificationQuery;

	@MockBean
	private NotificationRepository notificationRepository;

	@MockBean
	private UserRepository userRepository;

	@Test
	public void getNotifications_returnsAPageOfAllNotifications() {
		User user = UserFactory.instance();
		Page<Notification> pagedNotifications = this.createPageofNotifications(user);

		given(this.userRepository.getOne(user.getId())).willReturn(user);
		given(this.notificationRepository.findByUser(user, PageRequest.of(0, 2)))
			.willReturn(pagedNotifications);

		Page<Notification> gottenNotifications = this.notificationQuery
				.getNotifications(user.getId(), 1, 2);

		assertThat(gottenNotifications.getContent()).isEqualTo(pagedNotifications.getContent());
	}

	@Test
	public void getUnReadNotifications_returnsAPageOfUnReadNotifications() {
		User user = UserFactory.instance();
		Page<Notification> pagedNotifications = this.createPageofNotifications(user);

		given(this.userRepository.getOne(user.getId())).willReturn(user);
		given(this.notificationRepository.findByUserAndDone(user, false, PageRequest.of(0, 2)))
				.willReturn(pagedNotifications);

		Page<Notification> gottenNotifications = this.notificationQuery
				.getUndoneNotifications(user.getId(), 1, 2);

		assertThat(gottenNotifications.getContent()).isEqualTo(pagedNotifications.getContent());
	}

	private Page<Notification> createPageofNotifications(User user) {
		List<Notification> notifications = new ArrayList<>();
		notifications.add(NotificationFactory.instance());
		notifications.add(NotificationFactory.instance());
		notifications.forEach(notification -> notification.setUser(user));
		return new PageImpl<>(notifications);
	}
}
