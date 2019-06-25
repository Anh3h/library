package com.courage.library.controller;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;
import java.util.regex.Pattern;

import com.courage.library.exception.BadRequestException;
import com.courage.library.model.Notification;
import com.courage.library.model.User;
import com.courage.library.model.dto.PasswordDTO;
import com.courage.library.service.command.AccountCommand;
import com.courage.library.service.command.NotificationCommand;
import com.courage.library.service.query.NotificationQuery;
import com.courage.library.service.query.UserQuery;
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
	private UserQuery userQuery;

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

	//TODO get current account details
	@ApiOperation("Get logged-in user's notification")
	@GetMapping(
			value = "/me",
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<User> getLoggedInUser(Principal principal) {
		User user = this.getActiveUser(principal.getName());
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@ApiOperation("Get logged-in user's notification")
	@GetMapping(
			value = "/notifications",
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Page<Notification>> getNotifications( @RequestParam( value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "done", required = false) Boolean done, Principal principal ) {
		Map<String, Integer> pageAttributes = PageValidator.validatePageAndSize(page, size);
		page = pageAttributes.get("page");
		size = pageAttributes.get("size");
		String userId = this.getActiveUser(principal.getName()).getId();
		if (done != null && done == false) {
			Page<Notification> notifications = this.notificationQuery.getUndoneNotifications(userId, page, size);
			return new ResponseEntity<>(notifications, HttpStatus.OK);
		}
		Page<Notification> notifications = this.notificationQuery.getNotifications(userId, page, size);
		return new ResponseEntity<>(notifications, HttpStatus.OK);
	}

	@ApiOperation("Update logged-in user's notification")
	@PutMapping(
			value = "/notifications/{notificationId}",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Notification> updateNotifications(@PathVariable("notificationId") String notificationId) {
		Notification updatedNotification = this.notificationCommand.updateNotification(notificationId);
		return new ResponseEntity<>(updatedNotification, HttpStatus.OK);
	}

	private User getActiveUser(String username) {
		User user;
		if( this.isEmail(username) ) {
			user = this.userQuery.getUserByEmail(username);
		} else {
			user = this.userQuery.getUserByUsername(username);
		}
		return user;
	}

	Boolean isEmail( String text ){
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
				"[a-zA-Z0-9_+&*-]+)*@" +
				"(?:[a-zA-Z0-9-]+\\.)+[a-z" +
				"A-Z]{2,7}$";

		Pattern pat = Pattern.compile(emailRegex);
		if (text == null)
			return false;
		return pat.matcher(text).matches();
	}
}
