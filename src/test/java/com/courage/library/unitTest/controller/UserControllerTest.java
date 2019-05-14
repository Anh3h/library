package com.courage.library.unitTest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import com.courage.library.controller.UserController;
import com.courage.library.factory.JsonConverter;
import com.courage.library.factory.UserFactory;
import com.courage.library.model.User;
import com.courage.library.model.dto.UserDTO;
import com.courage.library.service.command.UserCommand;
import com.courage.library.service.query.UserQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class, secure = false)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserCommand userCommand;

	@MockBean
	private UserQuery userQuery;

	@Test
	public void createUserRequest_returnsHttp201AndCreatedUser() throws Exception {
		User user = UserFactory.instance();
		UserDTO userDTO = new UserDTO(user.getId(), user.getFirstName(), user.getLastName(),
				user.getUsername(), user.getEmail(), user.getPassword(),
				user.getDob(), user.getTelephone(), user.getRole().getId());
		given(this.userCommand.createUser(any(UserDTO.class))).willReturn(user);

		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(JsonConverter.toJSON(userDTO)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("email").value(user.getEmail()));
	}

	@Test
	public void updateUserRequest_returnsHttp200AndUpdatedUser() throws Exception {
		User user = UserFactory.instance();
		UserDTO userDTO = new UserDTO(user.getId(), user.getFirstName(), user.getLastName(),
				user.getUsername(), user.getEmail(), user.getPassword(),
				user.getDob(), user.getTelephone(), user.getRole().getId());
		given(this.userCommand.updateUser(any(UserDTO.class))).willReturn(user);

		this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/users/" + user.getId())
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(JsonConverter.toJSON(userDTO)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("email").value(user.getEmail()));
	}

	@Test
	public void invalidUpdateUserRequest_returnsHttp400() throws Exception {
		User user = UserFactory.instance();
		String newId = UUID.randomUUID().toString();
		UserDTO userDTO = new UserDTO(user.getId(), user.getFirstName(), user.getLastName(),
				user.getUsername(), user.getEmail(), user.getPassword(),
				user.getDob(), user.getTelephone(), user.getRole().getId());

		this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/users/" + newId)
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(JsonConverter.toJSON(userDTO)))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void getUsersRequest_returnsHttp200AndAPageOfUsers() throws Exception {
		Page<User> users = new PageImpl<>( UserFactory.instances() );
		given(this.userQuery.getUsers(1, 20)).willReturn(users);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users")
				.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
				.andExpect(jsonPath("content").isArray())
				.andExpect(jsonPath("number").value(0));
	}

	@Test
	public void getsUsersWithValidPageParamsRequest_returnsHttp200AndAPageOfUsers() throws Exception {
		Page<User> users = new PageImpl<>( UserFactory.instances() );
		given(this.userQuery.getUsers(1, 2)).willReturn(users);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users?page=1&size=2")
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("content").isArray())
				.andExpect(jsonPath("number").value(0));
	}

	@Test
	public void getsUsersWithInValidPageParamsRequest_returnsHttp400() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users?page=-1&size=2")
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void getUserRequest_returnsHttp200AndExistingUser() throws Exception {
		User user = UserFactory.instance();
		given(this.userQuery.getUserById(user.getId())).willReturn(user);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/" + user.getId())
				.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("id").value(user.getId()));
	}

	@Test
	public void getUserByEmailRequest_returnsHttp200AndExistingUser() throws Exception {
		User user = UserFactory.instance();
		given(this.userQuery.getUserByEmail(user.getEmail())).willReturn(user);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/email/" + user.getEmail())
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("email").value(user.getEmail()));
	}
}
