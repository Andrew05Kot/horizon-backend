package com.kot.horizon.common;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;
import com.kot.horizon.security.SocialTypes;
import com.kot.horizon.user.model.Language;
import com.kot.horizon.user.model.UserEntity;
import com.kot.horizon.user.model.UserRole;

public class UserBuilder {

	private Long id;
	private String socialId;
	private String lastName;
	private String firstName;

	private String email;

	private LocalDate birthDate;
	private UserRole role;
	private Language language;
	private BigDecimal rating;
	private String socialType;

	private static int index = 0;

	public UserBuilder() {
		initData();
	}

	private void initData() {
		index++;
		Long randomLongValue = ThreadLocalRandom.current().nextLong(1, 9999999);
		this.id = randomLongValue;
		this.socialId = index + randomLongValue.toString();
		this.firstName = "FIRST-NAME" + randomLongValue;
		this.lastName = "LAST-NAME" + randomLongValue;
		this.role = UserRole.ROLE_USER;
		this.language = Language.UK;
		this.email = randomLongValue + "@gmail.com";
		this.birthDate = null;
		this.rating = BigDecimal.ZERO.setScale(2);
		this.socialType = SocialTypes.FACEBOOK.value;
	}

	public UserEntity build() {
		UserEntity result = new UserEntity();
		result.setId(id);
		result.setSocialId(socialId);
		result.setLastName(lastName);
		result.setFirstName(firstName);
		result.setEmail(email);
		result.setBirthDate(birthDate);
		result.setLanguage(language);
		result.setRole(role);
		result.setSocialType(socialType);
		initData();
		return result;
	}

	public UserEntity buildNewOne() {
		return setId(null).build();
	}

	public UserBuilder setId(Long id) {
		this.id = id;
		return this;
	}

	public UserBuilder setSocialId(String socialId) {
		this.socialId = socialId;
		return this;
	}

	public UserBuilder setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public UserBuilder setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public UserBuilder setEmail(String email) {
		this.email = email;
		return this;
	}

	public UserBuilder setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
		return this;
	}

	public UserBuilder setRole(UserRole role) {
		this.role = role;
		return this;
	}

	public UserBuilder setLanguage(Language language) {
		this.language = language;
		return this;
	}

	public UserBuilder setRating(BigDecimal rating) {
		this.rating = rating;
		return this;
	}

	public UserBuilder setSocialType(String socialType) {
		this.socialType = socialType;
		return this;
	}

}
