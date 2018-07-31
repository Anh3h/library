package com.courage.library.controller;

import javax.servlet.http.HttpServletRequest;

import java.util.Map;

import com.courage.library.model.Notification;
import com.courage.library.model.dto.PasswordDTO;
import com.courage.library.service.command.AccountCommand;
import com.courage.library.service.query.NotificationQuery;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
	private TokenStore tokenStore;

	@ApiOperation("Reset password")
	@PutMapping(
			value = "/reset-password",
			consumes = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<HttpStatus> resetPassword(HttpServletRequest request, @RequestBody PasswordDTO passwordDTO) {
		this.accountCommand.updatePassword(passwordDTO);

		//Logging user out of current session
		String authHeader = request.getHeader("Authorization");
		if (authHeader != null) {
			String tokenValue = authHeader.replace("Bearer", "").trim();
			OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
			tokenStore.removeAccessToken(accessToken);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}



	@ApiOperation("Get logged-in user's notification")
	@GetMapping(
			value = "/notifications",
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Page<Notification>> getNotifications(HttpServletRequest request, @RequestParam( value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, @RequestParam("done") Boolean done) {
		Map<String, Integer> pageAttributes = PageValidator.validatePageAndSize(page, size);
		page = pageAttributes.get("page");
		size = pageAttributes.get("size");
		System.out.println(request.getUserPrincipal().getName());
		if (done) {
			Page<Notification> notifications = this.notificationQuery.getUndoneNotifications(page, size);
			return new ResponseEntity<>(notifications, HttpStatus.OK);
		}
		Page<Notification> notifications = this.notificationQuery.getNotifications(page, size);
		return new ResponseEntity<>(notifications, HttpStatus.OK);
	}

}
