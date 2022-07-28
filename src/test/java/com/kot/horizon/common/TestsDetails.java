package com.kot.horizon.common;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import com.kot.horizon.security.jwt.JwtAuthenticationToken;
import com.kot.horizon.security.jwt.JwtService;
import com.kot.horizon.user.model.UserEntity;
import com.kot.horizon.user.model.UserPrincipal;
import com.kot.horizon.user.model.UserRole;
import com.kot.horizon.user.service.CurrentUserService;

@ActiveProfiles("test")
@SpringBootTest
public abstract class TestsDetails {

	@Autowired
	private JwtService jwtService;

	public final UserBuilder userBuilder = new UserBuilder();

	@Autowired
	public CurrentUserService currentUserService;

	@BeforeEach
	public void logoutUser() {
		SecurityContextHolder.clearContext();
	}

	protected UserEntity loginAsUser() {
		UserEntity user = userBuilder.setRole(UserRole.ROLE_USER).build();
		loginUser(user);

		return user;
	}

	protected UserEntity loginAsAdmin() {
		UserEntity user = userBuilder.setRole(UserRole.ROLE_ADMIN).build();
		loginUser(user);
		return user;
	}

	protected void loginUser(UserEntity userEntity) {
		UserPrincipal userPrincipal = UserPrincipal.create(userEntity);
		String token = jwtService.createToken("auth", userPrincipal.getSocialId(), 10000L);
		JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(userPrincipal, token,
				userPrincipal.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(jwtAuthenticationToken);
	}

	protected UserEntity getCurrentUser() {
		return currentUserService.getCurrentUser();
	}
}