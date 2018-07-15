package com.courage.library.controller;

import javax.websocket.server.PathParam;
import java.util.Map;

import com.courage.library.exception.BadRequestException;
import com.courage.library.model.User;
import com.courage.library.model.dto.UserDTO;
import com.courage.library.service.command.UserCommand;
import com.courage.library.service.query.UserQuery;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("api/v1/users")
public class UserController {

	@Autowired
	private UserQuery userQuery;

	@Autowired
	private UserCommand userCommand;

	@ApiOperation("Create new user account")
	@PostMapping(
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<User> createUser(@RequestBody UserDTO user) {
		User newUser = this.userCommand.createUser(user);
		return new ResponseEntity<>(newUser, HttpStatus.CREATED);
	}

	@ApiOperation("Get all/some user accounts")
	@GetMapping(
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Page<User>> getUsers(@RequestParam( value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size) {
		Map<String, Integer> pageAttributes = PageValidator.validatePageAndSize(page, size);
		page = pageAttributes.get("page");
		size = pageAttributes.get("size");
		Page<User> users = this.userQuery.getUsers(page, size);
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@ApiOperation("Get a user account")
	@GetMapping(
			value = "/userId",
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<User> getUser(@PathVariable("userId") String userId) {
		User user = this.userQuery.getUserById(userId);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@ApiOperation("Update a user account")
	@PutMapping(
			value = "/userId",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<User> updateUser(@RequestBody UserDTO user, @PathVariable("userId") String userId) {
		if (user.getId() == userId) {
			User updatedUser = this.userCommand.updateUser(user);
			return new ResponseEntity<>(updatedUser, HttpStatus.OK);
		}
		throw BadRequestException.create("Bad Request: User id in path param does not match that in user object");
	}

	@ApiOperation("Delete a user account")
	@DeleteMapping(
			value = "/userId"
	)
	public ResponseEntity<HttpStatus> deleteUser(@PathVariable("userId") String userId) {
		this.userCommand.deleteUser(userId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
