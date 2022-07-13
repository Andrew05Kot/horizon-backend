package com.kot.horizon.security.oauth2;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.kot.horizon.api.v1.user.AuthProvider;
import com.kot.horizon.user.model.Language;
import com.kot.horizon.user.model.UserEntity;
import com.kot.horizon.user.model.UserPrincipal;
import com.kot.horizon.user.model.UserRole;
import com.kot.horizon.user.repository.UserRepository;
import com.kot.horizon.security.user.OAuth2AuthenticationProcessingException;
import com.kot.horizon.security.user.OAuth2UserInfo;
import com.kot.horizon.security.user.OAuth2UserInfoFactory;

@Service
//my new
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

		try {
			return processOAuth2User(oAuth2UserRequest, oAuth2User);
		} catch (AuthenticationException ex) {
			throw ex;
		} catch (Exception ex) {
			// Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
			throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
		}
	}

	private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
		OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
		if(StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
			throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
		}

		Optional<UserEntity> userOptional = userRepository.findByEmail(oAuth2UserInfo.getEmail());
		UserEntity user;
		if(userOptional.isPresent()) {
			user = userOptional.get();
//			if(!user.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
//				throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
//						user.getProvider() + " account. Please use your " + user.getProvider() +
//						" account to login.");
//			}
			user = updateExistingUser(user, oAuth2UserInfo);
		} else {
			user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
		}

		return UserPrincipal.create(user, oAuth2User.getAttributes());
	}

	private UserEntity registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
		UserEntity user = new UserEntity();

//		TODO
		user.setSocialId(oAuth2UserInfo.getId());
		user.setSocialType(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()).toString());
		user.setFirstName(oAuth2UserInfo.getFirsName());
		user.setLastName(oAuth2UserInfo.getLastName());
		user.setEmail(oAuth2UserInfo.getEmail());
		user.setImageUrl(oAuth2UserInfo.getImageUrl());
		user.setLanguage(Language.UK);
		user.setRole(UserRole.ROLE_USER);
		return userRepository.save(user);
	}

	private UserEntity updateExistingUser(UserEntity existingUser, OAuth2UserInfo oAuth2UserInfo) {
		existingUser.setFirstName(oAuth2UserInfo.getFirsName());
		existingUser.setLastName(oAuth2UserInfo.getLastName());
		existingUser.setImageUrl(oAuth2UserInfo.getImageUrl());
		return userRepository.save(existingUser);
	}
}
