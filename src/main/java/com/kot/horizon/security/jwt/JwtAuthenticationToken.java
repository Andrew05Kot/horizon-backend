package com.kot.horizon.security.jwt;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = 7032335279756013130L;

	private final String JWT_TOKEN;
	private UserDetails principal;
	
	public JwtAuthenticationToken(String token) {
		super(AuthorityUtils.NO_AUTHORITIES);
		this.JWT_TOKEN = token;
	}

	public JwtAuthenticationToken(UserDetails principal, String jwtToken, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.principal = principal;
		this.JWT_TOKEN = jwtToken;
		setAuthenticated(true);
	}

	@Override
	public Object getCredentials() {
		return JWT_TOKEN;
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}
}
