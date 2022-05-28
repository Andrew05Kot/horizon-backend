package com.kot.horizon.api.v1.user;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.kot.horizon.api.v1.AbstractConverter;
import com.kot.horizon.user.model.UserEntity;
import com.kot.horizon.user.service.UserService;

@Component
public class UserMapper extends AbstractConverter<UserEntity, User> {

	@Autowired
	private UserService userService;

	@Override
	public User getPublicResponse(UserEntity userEntity) {
		User user = new User();
		user.setId(userEntity.getId());
		user.setLastName(userEntity.getLastName());
		user.setFirstName(userEntity.getFirstName());
		user.setEmail(userEntity.getEmail());
		user.setPhoneNumber(userEntity.getPhoneNumber());
		user.setBirthDate(userEntity.getBirthDate());
		user.setAboutMe(userEntity.getAboutMe());
		return user;
	}

	@Override
	protected User getOwnerResponse(UserEntity userEntity) {
		User convertedUser = getPublicResponse(userEntity);
		convertedUser.setRole(userEntity.getRole());
		// TODO do we need the language field in public?
		convertedUser.setLanguage(userEntity.getLanguage());
		convertedUser.setSocialType(userEntity.getSocialType());
		return convertedUser;
	}

	@Override
	protected User getAdminResponse(UserEntity userEntity) {
		User convertedUser = getOwnerResponse(userEntity);
		return convertedUser;
	}

	@Override
	protected boolean publicCheck(UserEntity entity, UserEntity currentUser) {
		return super.publicCheck(entity, currentUser) || !ownerCheck(entity, currentUser);
	}

	@Override
	protected boolean ownerCheck(UserEntity entity, UserEntity currentUser) {
		return currentUser.equals(entity);
	}

	@Override
	protected void expandResponse(User response, UserEntity entity, List<String> entitiesToExpand) {
		// TODO nothing to do, because user doesn`t have expand entities
	}
}
