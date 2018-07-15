package com.courage.library.service.command.implementation;

import com.courage.library.exception.ConflictException;
import com.courage.library.exception.NotFoundException;
import com.courage.library.model.Role;
import com.courage.library.repository.RoleRepository;
import com.courage.library.service.command.RoleCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleCommandImplementation implements RoleCommand {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Role createRole(Role role) {
		if (this.roleRepository.findByName(role.getName()) == null) {
			role.setId(null);
			return this.roleRepository.save(role);
		}
		throw ConflictException.create("Conflict: Role name already exist");
	}

	@Override
	public Role updateRole(Role role) {
		if (this.roleRepository.existsById(role.getId())) {
			return this.roleRepository.save(role);
		}
		throw NotFoundException.create("Not Found: Role does not exist");
	}
}
