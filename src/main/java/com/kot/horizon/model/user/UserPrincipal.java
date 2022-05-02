package com.kot.horizon.model.user;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import com.kot.horizon.service.user.UserFullNameUtil;

public class UserPrincipal implements OAuth2User, UserDetails {

	private static final long serialVersionUID = 1L;
	private transient UserEntity userEntity;
	private String socialId;
	private String firstName;
	private String lastName;
	private Long id;
	private String email;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	private Map<String, Object> attributes;

	public UserPrincipal(UserEntity userEntity, String email, String socialId, String firstName, String lastName, Collection<? extends GrantedAuthority> authorities) {
		this.userEntity = userEntity;
		this.socialId = socialId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.authorities = authorities;
	}

	public UserPrincipal(Long id, String email, String password, Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}

	public static UserPrincipal create(UserEntity user) {
		List<GrantedAuthority> authorities = Collections.
				singletonList(new SimpleGrantedAuthority("ROLE_USER"));

		return new UserPrincipal(
				user,
				user.getEmail(),
				user.getSocialId(),
				user.getFirstName(),
				user.getLastName(),
				authorities
		);
	}

	public static UserPrincipal create(UserEntity user, Map<String, Object> attributes) {
		UserPrincipal userPrincipal = UserPrincipal.create(user);
		userPrincipal.setAttributes(attributes);
		return userPrincipal;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public String getSocialId() {
		return socialId;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return email != null ? email : UserFullNameUtil.getFullUserName(userEntity);
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	@Override
	public String getName() {
		return String.valueOf("socialId");
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UserPrincipal that = (UserPrincipal) o;
		return userEntity.equals(that.userEntity) &&
				socialId.equals(that.socialId) &&
				Objects.equals(firstName, that.firstName) &&
				Objects.equals(lastName, that.lastName) &&
				authorities.equals(that.authorities);
	}

	@Override
	public int hashCode() {
		return Objects.hash(userEntity, socialId, firstName, lastName, authorities);
	}
}
