package com.courage.library.unitTest.service.query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.UUID;

import com.courage.library.exception.NotFoundException;
import com.courage.library.factory.RoleFactory;
import com.courage.library.model.Role;
import com.courage.library.repository.RoleRepository;
import com.courage.library.service.query.RoleQuery;
import com.courage.library.service.query.implementation.RoleQueryImplementation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class RoleQueryTest {

	@TestConfiguration
	static class RoleQueryTestConfiguration {
		@Bean
		public RoleQuery roleQuery() {
			return new RoleQueryImplementation();
		}
	}

	@MockBean
	private RoleRepository roleRepository;

	@Autowired
	private RoleQuery roleQuery;

	@Test
	public void getRoles_returnsAPageofRoles() {
		List<Role> roles = RoleFactory.instances();
		given(this.roleRepository.findAll()).willReturn(roles);

		List<Role> gottenRoles = this.roleQuery.getRoles();

		assertThat(gottenRoles).containsAll(roles);
	}

	@Test
	public void getRole_returnsExistingRole() {
		Role role = RoleFactory.instance();
		given(this.roleRepository.getOne(role.getId())).willReturn(role);

		Role gottenRole = this.roleQuery.getRoleById(role.getId());

		assertThat(gottenRole).isEqualToComparingFieldByField(gottenRole);
	}

	@Test(expected = NotFoundException.class)
	public void getRoleNonExistingRole_throwsNotFoundException() {
		String roleId = UUID.randomUUID().toString();
		given(this.roleRepository.getOne(roleId)).willReturn(null);

		Role gottenRole = this.roleQuery.getRoleById(roleId);

		assertThat(gottenRole).isEqualToComparingFieldByField(gottenRole);
	}

}
