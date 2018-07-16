package com.courage.library.service.query;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.courage.library.exception.NotFoundException;
import com.courage.library.model.User;
import com.courage.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (isEmail(username)) {
			User user = userRepository.findByEmail(username);
			if (user == null) {
				throw NotFoundException.create("Not Found: Email {0} does not exist", username);
			}
			List<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add(user.getRole());
			return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
					true, true, true, true, authorities);
		}
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(user.getRole());
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				true, true, true, true, authorities);
	}

	Boolean isEmail( String text ){
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
				"[a-zA-Z0-9_+&*-]+)*@" +
				"(?:[a-zA-Z0-9-]+\\.)+[a-z" +
				"A-Z]{2,7}$";

		Pattern pat = Pattern.compile(emailRegex);
		if (text == null)
			return false;
		return pat.matcher(text).matches();
	}
}
