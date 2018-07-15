package com.courage.library.service.query.implementation;

import java.util.List;

import com.courage.library.exception.NotFoundException;
import com.courage.library.model.Role;
import com.courage.library.repository.RoleRepository;
import com.courage.library.service.query.RoleQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleQueryImplementation implements RoleQuery {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Role getRoleById(Long roleId) {
		Role role = this.roleRepository.getOne(roleId);
		if (role == null) {
			throw NotFoundException.create("Not Found: Role does not exist");
		}
		return role;
	}

	@Override
	public List<Role> getRoles() {
		return this.roleRepository.findAll();
	}
}
