package com.courage.library.repository;

import com.courage.library.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String>{

	User findByEmail(String email);
	User findByUsername(String username);
}
