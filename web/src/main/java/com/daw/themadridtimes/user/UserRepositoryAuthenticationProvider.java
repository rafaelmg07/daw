package com.daw.themadridtimes.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserRepositoryAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserRepository userService;

	@Autowired
	private UserComponent userComponent;

	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {

		User user = userService.findByEmail(auth.getName());

		if (user == null) {
			System.out.println("Login Error: User not found: "+ auth.getName());
			throw new BadCredentialsException("User not found");
		}

		String password = (String) auth.getCredentials();
		if (!new BCryptPasswordEncoder().matches(password, user.getPasswordHash())) {
			System.out.println("Login Error: Wrong password for: "+ user.getEmail());
			throw new BadCredentialsException("Wrong password");
		}

		System.out.println("Login Ok: "+ user.getName());
		userComponent.setLoggedUser(user);

		List<GrantedAuthority> roles = new ArrayList<>();
		for (String role : user.getRoles()) {
			roles.add(new SimpleGrantedAuthority(role));
		}

		return new UsernamePasswordAuthenticationToken(user.getName(), password, roles);
	}

	@Override
	public boolean supports(Class<?> authenticationObject) {
		return true;
	}
}
