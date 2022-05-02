package com.kot.horizon.model.user;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
	ROLE_USER,
	ROLE_ADMIN,
	ROLE_SUB_ADMIN;

	@Override
	public String getAuthority() {
		return name();
	}
}