package com.kot.horizon.service.user;

import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import com.kot.horizon.api.filtering.FilteringOperation;
import com.kot.horizon.api.filtering.specifications.EqualingSpecification;
import com.kot.horizon.api.searching.SearchCriteria;
import com.kot.horizon.dao.AbstractDAO;
import com.kot.horizon.dao.user.UserDAO;
import com.kot.horizon.exception.LocalizedException;
import com.kot.horizon.model.photo.PhotoEntity;
import com.kot.horizon.model.photo.ShortPhotoEntity;
import com.kot.horizon.model.user.UserEntity;
import com.kot.horizon.model.user.UserPermission;
import com.kot.horizon.model.user.UserRole;
import com.kot.horizon.repository.user.UserRepository;
import com.kot.horizon.service.AbstractService;
import com.kot.horizon.service.datetime.DateTimeService;

@Service
public class UserService extends AbstractService<UserEntity> {

	private static final String PERMISSION_NOT_FIT_FOR_USER_ROLE_MESSAGE_KEY = "Permission.Not.Fit.For.UserRole";

	@Autowired
	private CurrentUserService currentUserService;

	@Autowired
	private DateTimeService dateTimeService;

	@Autowired
	private UserDAO userDAO;

	@Override
	protected AbstractDAO<UserEntity, UserRepository> getDAO() {
		return userDAO;
	}

	public UserEntity getUserBySocialId(String facebookId) {
		return userDAO.findBySocialId(facebookId);
	}

	public List<UserEntity> getByUserRole(UserRole role) {
		SearchCriteria searchCriteria = new SearchCriteria("role", FilteringOperation.EQUAL, role);
		EqualingSpecification<UserEntity> specification = new EqualingSpecification<>(searchCriteria);
		return userDAO.findAll(specification);
	}

	public List<UserEntity> getUsersWithSoonBirthday() {

		return userDAO.findAll(isUserHasBirthdayInAMonth());
	}

	@Override
	public void update(UserEntity entityForUpdate) {
		UserEntity currentUser = currentUserService.getCurrentUser();
		if (!(currentUserService.isAdministrator(currentUser) || currentUser.getId().equals(entityForUpdate.getId()))) {
			throw new AccessDeniedException("You do not have permission to update");
		}
		UserEntity oldUser = findById(entityForUpdate.getId());
		checkImportantFields(oldUser, entityForUpdate);
		super.update(entityForUpdate);
	}


	protected void checkImportantFields(UserEntity oldEntity, UserEntity userForUpdate) {
		if (!oldEntity.getRole().equals(userForUpdate.getRole())) {
			if (!currentUserService.isAdministrator()) {
				throw new AccessDeniedException("You do not have permission to update");
			}
		}
	}

	private boolean isUserRoleDowngrading(UserRole prevRole, UserRole newRole) {
		return ( prevRole.equals(UserRole.ROLE_ADMIN) && ( newRole.equals(UserRole.ROLE_USER) || newRole.equals(UserRole.ROLE_SUB_ADMIN) )
				|| ( prevRole.equals(UserRole.ROLE_SUB_ADMIN) && newRole.equals(UserRole.ROLE_USER) ) );
	}


	public void updatePhotoForNewUser(UserEntity user, PhotoEntity photo) {

		if (currentUserService.getCurrentUser() != null) {
			throw new AccessDeniedException("User tried create an image for other user | userId "
					+ currentUserService.getCurrentUser().getId());
		}

		ShortPhotoEntity shortPhotoEntity = new ShortPhotoEntity();
		shortPhotoEntity.setId(photo.getId());
		shortPhotoEntity.setMimeType(photo.getMimeType());
//		shortPhotoEntity.setUser(photo.getUser());

		userDAO.save(user);

	}

	private Specification<UserEntity> isUserHasBirthdayInAMonth() {
		return (root, query, cb) ->
		cb.equal(dateTimeService.getExprForDayOfYear(root, cb),
				dateTimeService.getNow().plusDays(30).toLocalDate().getDayOfYear());
	}

	@Override
	public void delete(Long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public UserEntity findById(Long id) {
		UserEntity user = new UserEntity();
		UserEntity userFromDB = super.findById(id);
		if (userFromDB == null) {
			return null;
		}
		BeanUtils.copyProperties(userFromDB, user);
		return user;
	}

	public List<UserEntity> findAll(Specification<UserEntity> userEntitySpecification) {
		return getDAO().findAll(userEntitySpecification);
	}

	private void checkIfCurrentUserCanUpdatePermissions(UserEntity entityForUpdate) {
		if (!currentUserService.isAdministrator()) {
			throw new AccessDeniedException("You don`t have permission!");
		}
	}

	private void checkIfPermissionFitForUserRole(UserPermission permission, UserEntity entityForUpdate) {
		if (!entityForUpdate.getRole().equals(permission.getUserRole())) {
			throw new LocalizedException("Permission not fit for this user role", PERMISSION_NOT_FIT_FOR_USER_ROLE_MESSAGE_KEY);
		}
	}

}
