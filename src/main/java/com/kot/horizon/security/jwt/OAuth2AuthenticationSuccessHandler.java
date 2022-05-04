package com.kot.horizon.security.jwt;

import java.io.IOException;
import java.util.Optional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import com.kot.horizon.user.model.UserPrincipal;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

	@Value("${horizon.oauth2.shortLivedMillis}")
	private Long shortLivedMillis;

	@Value("${horizon.oauth2.defaultSuccessUrl}")
	private String oauth2AuthenticationSuccessUrl;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
	                                    Authentication authentication) throws IOException{
		String targetUrl = determineTargetUrl(request, response, authentication);

		if (response.isCommitted()) {
			logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
			return;
		}

		clearAuthenticationAttributes(request, response);
		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}

	@Override
	protected String determineTargetUrl(HttpServletRequest request,
										HttpServletResponse response,
										Authentication authentication) {
		Optional<String> redirectUri = getRedirectUri(request);
		String targetUrl = redirectUri.orElse(oauth2AuthenticationSuccessUrl);
		
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		String shortLivedAuthToken = jwtService.createToken(
				JwtService.AUTH_AUDIENCE,
				userPrincipal.getSocialId(),
				shortLivedMillis);

		return UriComponentsBuilder.fromUriString(targetUrl)
				.queryParam("token", shortLivedAuthToken)
				.build().toUriString();
	}

	private Optional<String> getRedirectUri(HttpServletRequest request) {
		return CookieUtils.getCookie(request, HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME)
				.map(Cookie::getValue);
	}

	protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
		super.clearAuthenticationAttributes(request);
		httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
	}
}
