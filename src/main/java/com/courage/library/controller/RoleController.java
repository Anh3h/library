package com.courage.library.controller;

import java.util.List;

import com.courage.library.exception.BadRequestException;
import com.courage.library.model.Role;
import com.courage.library.service.command.RoleCommand;
import com.courage.library.service.query.RoleQuery;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/v1/roles")
public class RoleController {

	@Autowired
	private RoleQuery roleQuery;

	@Autowired
	private RoleCommand roleCommand;

	@ApiOperation(value="Create new role")
	@PostMapping(
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Role> createRole(@RequestBody Role role) {
		Role newRole = this.roleCommand.createRole(role);
		return new ResponseEntity<>(role, HttpStatus.CREATED);
	}

	@ApiOperation(value="Get all roles")
	@GetMapping(
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<List<Role>> getRoles() {
		List<Role> roles = this.roleQuery.getRoles();
		return new ResponseEntity<>(roles, HttpStatus.OK);
	}

	@ApiOperation(value="Get a role")
	@GetMapping(
			value = "/{roleId}",
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Role> getRole(@PathVariable("roleId") String roleId) {
		Role role = this.roleQuery.getRoleById(roleId);
		return new ResponseEntity<>(role, HttpStatus.OK);
	}

	@ApiOperation(value="Update a role")
	@PutMapping(
			value = "/{roleId}",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Role> updateRole(@RequestBody Role role, @PathVariable("roleId") String roleId) {
		if (role.getId().compareTo(roleId) == 0) {
			Role updateRole = this.roleCommand.updateRole(role);
			return new ResponseEntity<>(updateRole, HttpStatus.OK);
		}
		throw BadRequestException.create("Bad Request: Role id in path param does not match that in role object");
	}
}
