package com.courage.library.controller;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import com.courage.library.exception.BadRequestException;
import com.courage.library.model.Notification;
import com.courage.library.model.dto.PasswordDTO;
import com.courage.library.service.command.AccountCommand;
import com.courage.library.service.command.NotificationCommand;
import com.courage.library.service.query.NotificationQuery;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("api/v1")
public class AccountController {

	@Autowired
	private AccountCommand accountCommand;

	@Autowired
	private NotificationQuery notificationQuery;

	@Autowired
	private NotificationCommand notificationCommand;

	@Autowired
	private TokenStore tokenStore;

	@ApiOperation("Reset password")
	@PutMapping(
			value = "/reset-password",
			consumes = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<HttpStatus> resetPassword(HttpServletRequest request, @RequestBody PasswordDTO passwordDTO) {
		this.accountCommand.updatePassword(passwordDTO);

		//Logging user out of current session
		/*String authHeader = request.getHeader("Authorization");
		if (authHeader != null) {
			String tokenValue = authHeader.replace("Bearer", "").trim();
			OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
			tokenStore.removeAccessToken(accessToken);
		}*/

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@ApiOperation("Get logged-in user's notification")
	@GetMapping(
			value = "/users/{userId}/notifications",
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Page<Notification>> getNotifications(@PathVariable("userId") String userId, @RequestParam( value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "done", required = false) Boolean done) {
		Map<String, Integer> pageAttributes = PageValidator.validatePageAndSize(page, size);
		page = pageAttributes.get("page");
		size = pageAttributes.get("size");
		if (done != null && done == false) {
			Page<Notification> notifications = this.notificationQuery.getUndoneNotifications(userId, page, size);
			return new ResponseEntity<>(notifications, HttpStatus.OK);
		}
		Page<Notification> notifications = this.notificationQuery.getNotifications(userId, page, size);
		return new ResponseEntity<>(notifications, HttpStatus.OK);
	}

	@ApiOperation("Update logged-in user's notification")
	@PutMapping(
			value = "notifications/{notificationId}",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Notification> updateNotifications(@RequestBody Notification notification, @PathVariable("notificationId") String notificationId) {
		if (notification.getId().compareTo(notificationId) == 0) {
			Notification updatedNotification = this.notificationCommand.updateNotification(notification);
			return new ResponseEntity<>(updatedNotification, HttpStatus.OK);
		}
		throw BadRequestException.create("Bad Request: Notification id in path parameter does not match that in notification object");
	}
}
