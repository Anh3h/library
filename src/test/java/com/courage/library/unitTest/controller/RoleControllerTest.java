package com.courage.library.unitTest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.UUID;

import com.courage.library.controller.RoleController;
import com.courage.library.factory.JsonConverter;
import com.courage.library.factory.RoleFactory;
import com.courage.library.model.Role;
import com.courage.library.service.command.RoleCommand;
import com.courage.library.service.query.RoleQuery;
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
@WebMvcTest(value = RoleController.class, secure = false)
public class RoleControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RoleQuery roleQuery;

	@MockBean
	private RoleCommand roleCommand;

	@Test
	public void createRoleRequest_returnsHttp201AndCreatedRole() throws Exception {
		Role role = RoleFactory.instance();
		given(this.roleCommand.createRole(any(Role.class))).willReturn(role);

		this.mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/roles")
				.content(JsonConverter.toJSON(role))
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("name").value(role.getName()));
	}

	@Test
	public void updateRoleRequest_returnsHttp200AndUpdatedRole() throws Exception {
		Role role = RoleFactory.instance();
		given(this.roleCommand.updateRole(any(Role.class))).willReturn(role);

		this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/roles/" + role.getId())
				.content(JsonConverter.toJSON(role))
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
			.andExpect(jsonPath("name").value(role.getName()));
	}

	@Test
	public void invalidUpdateRoleRequest_returnsHttp400() throws Exception {
		Role role = RoleFactory.instance();
		String id = UUID.randomUUID().toString();

		this.mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/roles/" + id)
				.content(JsonConverter.toJSON(role))
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isBadRequest());
	}

	@Test
	public void getRolesRequest_returnsHttp200AndAListOfExistingRoles() throws Exception {
		List<Role> roles = RoleFactory.instances();
		given(this.roleQuery.getRoles()).willReturn(roles);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/roles")
				.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$").isArray());
	}

	@Test
	public void getRoleRequest_returnsHttp200AndAnExistingRole() throws Exception {
		Role role = RoleFactory.instance();
		given(this.roleQuery.getRoleById(role.getId())).willReturn(role);

		this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/roles/" + role.getId())
				.accept(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(jsonPath("name").value(role.getName()));
	}
}
