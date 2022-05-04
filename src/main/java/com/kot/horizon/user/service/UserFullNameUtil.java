package com.kot.horizon.user.service;

import com.kot.horizon.user.model.UserEntity;

public class UserFullNameUtil {

	private static final String NO_USER = "no user";

	public static String getFullUserName(UserEntity userEntity) {
		if (userEntity == null) {
			return NO_USER;
		}
		return userEntity.getFirstName() + " " + userEntity.getLastName();
	}

	public static String getFullUserNameAndId(UserEntity userEntity) {
		if (userEntity == null) {
			return NO_USER;
		}
		return userEntity.getFirstName() + " " + userEntity.getLastName() + " (id:"+userEntity.getId()+")";
	}
}
