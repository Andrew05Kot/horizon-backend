package com.kot.horizon.api.v1.auth;

import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import com.kot.horizon.model.user.UserEntity;
import com.kot.horizon.security.jwt.JwtService;
import com.kot.horizon.service.user.UserService;

@Service
public class AuthenticationAPIService {

	@Value("${horizon.oauth2.expirationMillis}")
	private String expirationMillis;

	@Autowired
	private UserService userService;

	@Autowired
	private JwtService jwtService;
	
	public String authenticate(String token) {
		JWTClaimsSet claims = jwtService.parseToken(token);
		UserEntity user = userService.getUserBySocialId(claims.getSubject());
		if (user == null) {
			throw new BadCredentialsException("Bad credentials. User not found.");
		}
		return jwtService.createToken(claims.getAudience().toString(), claims.getSubject(), Long.parseLong(expirationMillis));
	}
}
