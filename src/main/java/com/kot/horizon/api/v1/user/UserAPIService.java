package com.kot.horizon.api.v1.user;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kot.horizon.common.filtering.EntityFilterSpecificationsBuilder;
import com.kot.horizon.user.specification.UserSpecificationsBuilder;
import com.kot.horizon.api.v1.general.AbstractAPIService;
import com.kot.horizon.photo.model.ShortPhotoEntity;
import com.kot.horizon.user.model.UserEntity;
import com.kot.horizon.photo.service.PhotoService;
import com.kot.horizon.user.service.CurrentUserService;
import com.kot.horizon.user.service.UserService;

@Service
public class UserAPIService extends AbstractAPIService<UserEntity, User, User, UserService> {

	@Autowired
	private UserSpecificationsBuilder userSpecificationsBuilder;

	@Autowired
	private CurrentUserService currentUserService;

	@Autowired
	private UserConverter userConverter;

	@Autowired
	private PhotoService photoService;

	@Override
	public User create(User request) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void update(Long id, User request) {
		UserEntity userEntity = getValidEntityById(id);
		copyProperties(request, userEntity);
		service.update(userEntity);
	}

	public List<User> convertEntitiesListToDto(List<UserEntity> entities) {
		return entities.stream().map(userEntity -> userConverter.getResponseBean(userEntity,
				Collections.emptyList())).collect(Collectors.toList());
	}

	@Override
	protected UserEntity getNewEntity(User request) {
		throw new UnsupportedOperationException();
	}

	public User getCurrentUser() {
		UserEntity currentUser = currentUserService.getCurrentUser();
		if (currentUser != null) {
			return convertToResponseBean(currentUser, Optional.empty());
		}
		return null;
	}

	@Override
	protected void patch(Long id, User request) {
		UserEntity userEntity = getValidEntityById(id);
		boolean isNeededToUpdate = false;
		if (request.getLanguage() != null) {
			userEntity.setLanguage(request.getLanguage());
			isNeededToUpdate = true;
		}

		if (addPhotoToEntity(request, userEntity)) {
			isNeededToUpdate = true;
		}

		if (request.getRole() != null) {
			userEntity.setRole(request.getRole());
			isNeededToUpdate = true;
		}

		if (isNeededToUpdate) {
			service.update(userEntity);
		}
	}

	@Override
	protected EntityFilterSpecificationsBuilder<UserEntity> getSpecificationBuilder() {
		return userSpecificationsBuilder;
	}

	@Override
	protected void copyProperties(User request, UserEntity entity) {
		entity.setLastName(request.getLastName());
		entity.setFirstName(request.getFirstName());
		entity.setBirthDate(request.getBirthDate());
		entity.setEmail(request.getEmail());
		entity.setLanguage(request.getLanguage());
		entity.setRole(request.getRole());
		if (request.getIsPhotoToDelete()) {
		}
		else {
			addPhotoToEntity(request, entity);
		}
	}

	@Override
	protected User convertToResponseBean(UserEntity entity, Optional<String> expand) {
		return userConverter.getResponseBean(entity, parseExpandField(expand));
	}

	private boolean addPhotoToEntity(User request, UserEntity entity) {
		if (request.getPhoto() != null) {
			ShortPhotoEntity photo = photoService.getCurrentUserShortPhoto(request.getPhoto().getId());
			if (photo != null) {
				return true;
			}
		}
		return false;
	}

}
