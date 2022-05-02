package com.kot.horizon.security.jwt;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String TOKEN_REQUEST_HEADER_NAME = "Authorization";

	private final AuthenticationManager authenticationManager;

	public JwtTokenAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	protected boolean tokenPresent(HttpServletRequest request) {
		String header = request.getHeader(TOKEN_REQUEST_HEADER_NAME);
		return header != null && header.startsWith(TOKEN_PREFIX);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		if (tokenPresent(request)) {
			String token = request.getHeader(TOKEN_REQUEST_HEADER_NAME).substring(7);
			JwtAuthenticationToken authRequest = new JwtAuthenticationToken(token);
			try {
				Authentication auth = authenticationManager.authenticate(authRequest);
				SecurityContextHolder.getContext().setAuthentication(auth);
			} catch (CredentialsExpiredException ex) {
				response.sendError(426);
				return;
			} catch (Exception ex) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
						"Authentication Failed: " + ex.getMessage());
				return;
			}
		}

		filterChain.doFilter(request, response);
	}
}
