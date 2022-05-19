package com.kot.horizon.security;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

import java.util.Arrays;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;
import com.kot.horizon.api.v1.auth.AuthenticationController;
import com.kot.horizon.api.v1.image.ImageController;
import com.kot.horizon.api.v1.photo.PhotoController;
import com.kot.horizon.api.v1.tour.controller.TourController;
import com.kot.horizon.api.v1.user.UserController;
import com.kot.horizon.security.jwt.HttpCookieOAuth2AuthorizationRequestRepository;
import com.kot.horizon.security.jwt.JwtAuthenticationProvider;
import com.kot.horizon.security.jwt.JwtTokenAuthenticationFilter;
import com.kot.horizon.security.jwt.OAuth2AuthenticationFailureHandler;
import com.kot.horizon.security.jwt.OAuth2AuthenticationSuccessHandler;
import com.kot.horizon.security.oauth2.CustomOAuth2AccessTokenResponseHttpMessageConverter;
import com.kot.horizon.security.oauth2.CustomOAuth2UserService;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private OAuth2AuthenticationSuccessHandler authenticationSuccessHandler;
	@Autowired
	private OAuth2AuthenticationFailureHandler authenticationFailureHandler;
	@Autowired
	private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
	@Autowired
	private CustomOAuth2UserService customOAuth2UserService;
	@Autowired
	private CustomUserDetailsService userDetailsService;
	@Autowired
	private JwtAuthenticationProvider jwtAuthenticationProvider;

	private BCryptPasswordEncoder bCryptPasswordEncoder;

	private static final String[] PUBLIC_API_POST = {
			TourController.BASE_URL + "/{id}" + "/images",
	};

	private static final String[] PUBLIC_API_GET = {
			UserController.BASE_URL,
			UserController.BASE_URL + "/{id}",
			UserController.BASE_URL + "/current",
			PhotoController.BASE_URL + "/{id}",
			PhotoController.BASE_URL,
			TourController.BASE_URL,
			ImageController.BASE_URL + "/{name}"
	};

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder())
				.and().authenticationProvider(jwtAuthenticationProvider);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and().cors()
				.and().csrf().disable()
				.oauth2Login()
				.authorizationEndpoint()
				.authorizationRequestRepository(httpCookieOAuth2AuthorizationRequestRepository)
				.and()
				.successHandler(authenticationSuccessHandler)
				.failureHandler(authenticationFailureHandler)
				.userInfoEndpoint()
				.userService(customOAuth2UserService)
				.and()
				.tokenEndpoint()
				.accessTokenResponseClient(accessTokenResponseClient());

		http.authorizeRequests()
				.antMatchers("/login", AuthenticationController.BASE_URL)
				.permitAll()
				.antMatchers(GET, PUBLIC_API_GET).permitAll()
				.antMatchers(POST, PUBLIC_API_POST).permitAll()
				.antMatchers("/v2/api-docs/**",
						"/swagger-ui.html",
						"/swagger-resources/**",
						"/webjars/**")
				.permitAll()
				.antMatchers("/v1/**").authenticated();

		http.addFilterBefore(new JwtTokenAuthenticationFilter(authenticationManagerBean()),
				UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	private OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient() {
		DefaultAuthorizationCodeTokenResponseClient client = new DefaultAuthorizationCodeTokenResponseClient();
		RestTemplate restTemplate = new RestTemplate(Arrays.asList(
				new FormHttpMessageConverter(), new CustomOAuth2AccessTokenResponseHttpMessageConverter()));
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(HttpClientBuilder.create().build()));
		restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());
		client.setRestOperations(restTemplate);
		return client;
	}

	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
}
