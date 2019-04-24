package com.courage.library.unitTest.service.command;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.courage.library.exception.ConflictException;
import com.courage.library.exception.NotFoundException;
import com.courage.library.factory.RoleFactory;
import com.courage.library.model.Role;
import com.courage.library.repository.RoleRepository;
import com.courage.library.service.command.RoleCommand;
import com.courage.library.service.command.implementation.RoleCommandImplementation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class RoleCommandTest {

	@TestConfiguration
	static class RoleCommandTestConfiguration {
		@Bean
		public RoleCommand roleCommand() {
			return new RoleCommandImplementation();
		}
	}

	@MockBean
	private RoleRepository roleRepository;

	@Autowired
	private RoleCommand roleCommand;

	@Test
	public void createRole_returnsCreatedRole() {
		Role role = RoleFactory.instance();
		given(this.roleRepository.save(role)).willReturn(role);

		Role newRole = this.roleCommand.createRole(role);

		assertThat(newRole).isEqualToComparingFieldByField(role);
	}

	@Test(expected = ConflictException.class)
	public void createExistingRole_throwConflictException() {
		Role role = RoleFactory.instance();
		given(this.roleRepository.findByName(role.getName())).willReturn(role);

		this.roleCommand.createRole(role);
	}

	@Test
	public void updateRole_returnsUpdatedRole() {
		Role role = RoleFactory.instance();
		given(this.roleRepository.save(role)).willReturn(role);
		given(this.roleRepository.existsById(role.getId())).willReturn(true);

		Role updatedRole = this.roleCommand.updateRole(role);

		assertThat(updatedRole).isEqualToComparingFieldByField(role);
	}

	@Test(expected = NotFoundException.class)
	public void updateNonExistingRole_throwsNotFound() {
		Role role = RoleFactory.instance();
		given(this.roleRepository.existsById(role.getId())).willReturn(false);

		this.roleCommand.updateRole(role);
	}
}
