package com.courage.library.service.command;

import com.courage.library.model.Role;

public interface RoleCommand {

	Role createRole(Role role);
	Role updateRole(Role role);
}
