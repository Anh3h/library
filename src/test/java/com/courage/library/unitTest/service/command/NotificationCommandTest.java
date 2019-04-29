package com.courage.library.unitTest.service.command;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.courage.library.factory.NotificationFactory;
import com.courage.library.model.Notification;
import com.courage.library.repository.NotificationRepository;
import com.courage.library.service.command.NotificationCommand;
import com.courage.library.service.command.implementation.NotificationCommandImplementation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class NotificationCommandTest {

	@TestConfiguration
	static class NotificationCommandTestConfiguration {
		@Bean
		public NotificationCommand notificationCommand() {
			return new NotificationCommandImplementation();
		}
	}

	@Autowired
	private NotificationCommand notificationCommand;

	@MockBean
	private NotificationRepository notificationRepository;

	@Test
	public void createNotification_returnsCreatedNotification() {
		Notification notification = NotificationFactory.instance();
		given(this.notificationRepository.save(notification)).willReturn(notification);

		Notification createdNotification = this.notificationCommand
				.createNotification(notification);

		assertThat(createdNotification).isEqualToComparingFieldByField(notification);
	}

}
