package com.kot.horizon.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.kot.horizon.model.user.UserEntity;
import com.kot.horizon.model.user.UserPrincipal;
import com.kot.horizon.repository.user.UserRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	private static final String ID = "id";

	@Autowired
	private UserRepository userRepository;


	@Override
	public UserPrincipal loadUserByUsername(String socialId) {
		UserEntity user = userRepository.findBySocialId(socialId).orElseThrow(() ->
				new UsernameNotFoundException("User with " + socialId + " social id"));
		return UserPrincipal.create(user);
	}

}
