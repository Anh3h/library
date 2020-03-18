package com.courage.library.unitTest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import com.courage.library.controller.AccountController;
import com.courage.library.factory.JsonConverter;
import com.courage.library.factory.NotificationFactory;
import com.courage.library.model.Notification;
import com.courage.library.service.command.AccountCommand;
import com.courage.library.service.command.NotificationCommand;
import com.courage.library.service.query.NotificationQuery;
import com.courage.library.service.query.UserQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(value = AccountController.class, secure = false)
public class AccountControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AccountCommand accountCommand;

	@MockBean
	private UserQuery userQuery;

	@MockBean
	private NotificationCommand notificationCommand;

	@MockBean
	private NotificationQuery notificationQuery;

	@MockBean
	private TokenStore tokenStore;

	@Test
	public void getUserNotificationsRequest_returnsHttp200AndAPageOfNotifications() throws Exception {
		/*Page<Notification> notifications = new PageImpl<>( NotificationFactory.instances() );
		String userId = notifications.getContent().get(0).getId();
		notifications.getContent().forEach(notification -> notification.getUser().setId(userId));
		given(this.notificationQuery.getNotifications(userId, 1, 20)).willReturn(notifications);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/" + userId + "/notifications")
				.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("content").isArray())
			.andExpect(jsonPath("number").value(0));*/
	}

	/*@Test
	public void getUserNotificationsWithPageParamRequest_returnsHttp200AndAPageOfNotifications() throws Exception {
		Page<Notification> notifications = new PageImpl<>( NotificationFactory.instances() );
		String userId = notifications.getContent().get(0).getUser().getId();
		notifications.getContent().forEach(notification -> notification.getUser().setId(userId));
		given(this.notificationQuery.getNotifications(userId, 1, 2)).willReturn(notifications);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/" + userId + "/notifications?page=1&size=2")
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("content").isArray())
				.andExpect(jsonPath("number").value(0));
	}

	@Test
	public void getUserUnReadNotificationsRequest_returnsHttp200AndAPageOfUnReadNotifications() throws Exception {
		Page<Notification> notifications = new PageImpl<>( NotificationFactory.instances() );
		String userId = notifications.getContent().get(0).getId();
		notifications.getContent().forEach(notification -> notification.getUser().setId(userId));
		given(this.notificationQuery.getUndoneNotifications(userId, 1, 20)).willReturn(notifications);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/" + userId + "/notifications?done=false")
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("content.[0].done").value(false))
				.andExpect(jsonPath("content").isArray())
				.andExpect(jsonPath("number").value(0));
	}

	@Test
	public void updateUserRequest_returnsHttp200AndAnUpdatedNotification() throws Exception {
		Notification notification = NotificationFactory.instance();
		given(this.notificationCommand.updateNotification(notification.getId())).willReturn(notification);

		this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/notifications/" + notification.getId())
				.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("id").value(notification.getId()));
	}

	@Test
	public void invalidUpdateUserRequest_returnsHttp400() throws Exception {
		String notificationId = UUID.randomUUID().toString();
		Notification notification = NotificationFactory.instance();

		this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/notifications/" + notificationId)
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(JsonConverter.toJSON(notification)))
				.andExpect(status().isBadRequest());
	}*/
}
