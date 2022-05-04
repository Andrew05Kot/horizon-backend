package com.kot.horizon.common.service.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.kot.horizon.user.model.UserEntity;
import com.kot.horizon.user.model.UserPrincipal;
import com.kot.horizon.user.model.UserRole;
import com.kot.horizon.security.jwt.JwtAuthenticationToken;
import com.kot.horizon.security.jwt.JwtService;
import com.kot.horizon.user.service.UserFullNameUtil;

@Service
public class SystemAdministratorUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(SystemAdministratorUtil.class);

	@Autowired
	private JwtService jwtService;

	public void loginSystemAdministrator() {
		UserEntity defaultSystemAdministrator = getDefaultSystemAdministrator();
		UserPrincipal userPrincipal = UserPrincipal.create(defaultSystemAdministrator);
		String token = jwtService.createToken("auth", userPrincipal.getSocialId(), 100000L);
		JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(userPrincipal, token, userPrincipal.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(jwtAuthenticationToken);
		LOGGER.info("Logged in as: {}", UserFullNameUtil.getFullUserNameAndId(defaultSystemAdministrator));
	}

	public UserEntity getDefaultSystemAdministrator() {
		UserEntity admin = new UserEntity();
		admin.setId(Long.MAX_VALUE);
		admin.setFirstName("System");
		admin.setLastName("Administrator");
		admin.setRole(UserRole.ROLE_ADMIN);
		return admin;
	}
}
