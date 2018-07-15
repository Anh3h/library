package com.courage.library.service.query;

import java.util.List;

import com.courage.library.model.Role;

public interface RoleQuery {

	Role getRoleById(Long roleId);
	List<Role> getRoles();
}
