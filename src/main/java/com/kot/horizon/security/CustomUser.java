package com.kot.horizon.security;

import java.util.Collection;
import java.util.Map;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import com.kot.horizon.model.user.UserEntity;

public class CustomUser extends DefaultOAuth2User {

	private static final long serialVersionUID = 1L;

	private final transient UserEntity userEntity;

    public CustomUser( UserEntity userEntity, Collection<? extends GrantedAuthority> authorities, 
					   Map<String, Object> attributes, String nameAttributeKey ) {
        super(authorities, attributes, nameAttributeKey);
        this.userEntity = userEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		CustomUser that = (CustomUser) o;

		return new EqualsBuilder()
				.appendSuper(super.equals(o))
				.append(userEntity, that.userEntity)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.appendSuper(super.hashCode())
				.append(userEntity)
				.toHashCode();
	}
}
