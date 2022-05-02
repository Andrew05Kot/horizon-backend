package com.kot.horizon.service.photo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import com.kot.horizon.api.filtering.FilteringOperation;
import com.kot.horizon.api.filtering.specifications.EqualingSpecification;
import com.kot.horizon.api.searching.SearchCriteria;
import com.kot.horizon.dao.AbstractDAO;
import com.kot.horizon.dao.photo.PhotoDAO;
import com.kot.horizon.dao.photo.ShortPhotoDAO;
import com.kot.horizon.model.photo.PhotoEntity;
import com.kot.horizon.model.photo.ShortPhotoEntity;
import com.kot.horizon.model.user.UserEntity;
import com.kot.horizon.model.user.UserRole;
import com.kot.horizon.repository.photo.PhotoRepository;
import com.kot.horizon.service.AbstractService;
import com.kot.horizon.service.user.CurrentUserService;

@Service
@Validated
public class PhotoService extends AbstractService<PhotoEntity> {

	@Autowired
	private CurrentUserService currentUserService;

	@Autowired
	private PhotoDAO photoDAO;

	@Autowired
	private ShortPhotoDAO shortPhotoDAO;

	@Override
	protected AbstractDAO<PhotoEntity, PhotoRepository> getDAO() {
		return photoDAO;
	}

	public PhotoEntity getById(Long id) {
		return getDAO().findById(id);
	}

	public ShortPhotoEntity getCurrentUserShortPhoto(Long id){
		SearchCriteria idCriteria = new SearchCriteria("id", FilteringOperation.EQUAL, id);
		SearchCriteria userCriteria = new SearchCriteria("user",FilteringOperation.EQUAL, currentUserService.getCurrentUser().getId());
		Specification<ShortPhotoEntity> specification = new EqualingSpecification<ShortPhotoEntity>(idCriteria)
				.and(new EqualingSpecification<>(userCriteria));
		return shortPhotoDAO.findOne(specification);
	}

	@Override
	public PhotoEntity create(PhotoEntity entity) {
		return super.create(entity);
	}

	public PhotoEntity createForUser( PhotoEntity entity, UserEntity user ) {
		if( currentUserService.getCurrentUser() != null ){
			throw new AccessDeniedException( "User tried create an image for other user | userId "
					+ currentUserService.getCurrentUser().getId() );
		}
		return super.create(entity);
	}

	@Override
	public void delete(Long id){
		if( currentUserService.getCurrentUser().getRole().equals(UserRole.ROLE_USER)
				|| currentUserService.getCurrentUser().getRole().equals(UserRole.ROLE_SUB_ADMIN)){
			deleteIfExists(getCurrentUserShortPhoto(id));
			return;
		} else if (currentUserService.isAdministrator()) {
			deleteIfExists(shortPhotoDAO.findById(id));
			return;
		}

		throw new AccessDeniedException("You do not have permission to delete photo");
	}

	private void deleteIfExists(ShortPhotoEntity photoEntity) {
		if (photoEntity != null) {
			shortPhotoDAO.delete(photoEntity);
		}
	}

	public PhotoEntity saveDownloadedPicture(UserEntity userEntity, byte[] downloadedPicture) {
		PhotoEntity userPhotoEntity = new PhotoEntity();
		userPhotoEntity.setMimeType("image/png");
		userPhotoEntity.setContent(downloadedPicture);
		return createForUser(userPhotoEntity, userEntity);
	}
}
