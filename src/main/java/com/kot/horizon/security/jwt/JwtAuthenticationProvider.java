package com.kot.horizon.security.jwt;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.nimbusds.jwt.JWTClaimsSet;
import com.kot.horizon.user.model.UserPrincipal;
import com.kot.horizon.security.CustomUserDetailsService;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Override
	public Authentication authenticate(Authentication auth) {
		String token = (String) auth.getCredentials();

		JWTClaimsSet claims = jwtService.parseToken(token);
		if( new Date( System.currentTimeMillis() ).after( claims.getExpirationTime() ) ){
			throw new CredentialsExpiredException( "Token expired" );
		}
		UserPrincipal userPrincipal = userDetailsService.loadUserByUsername(claims.getSubject());

		if (userPrincipal == null) {
			throw new BadCredentialsException("Bad credentials. User not found");
		}

		return new JwtAuthenticationToken(userPrincipal, token, userPrincipal.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
	}
}
