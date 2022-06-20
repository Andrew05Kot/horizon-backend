package com.kot.horizon.tour;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.kot.horizon.api.v1.tour.TourResponse;
import com.kot.horizon.architecture.dao.AbstractDAO;
import com.kot.horizon.architecture.repository.BaseCRUDRepository;
import com.kot.horizon.architecture.service.AbstractService;
import com.kot.horizon.image.exception.UnsupportedImageTypeException;
import com.kot.horizon.image.exception.WrongImageSizeException;
import com.kot.horizon.image.model.ImageEntity;
import com.kot.horizon.image.service.ImageService;
import com.kot.horizon.tour.TourDao;
import com.kot.horizon.tour.TourEntity;
import com.kot.horizon.user.model.UserEntity;
import com.kot.horizon.user.service.CurrentUserService;
import com.kot.horizon.user.service.UserService;

@Service
public class TourService extends AbstractService<TourEntity> {

	@Autowired
	private TourDao dao;

	@Autowired
	private ImageService imageService;

	@Autowired
	private CurrentUserService currentUserService;

	@Autowired
	private UserService userService;

	@Override
	public TourEntity create(TourEntity entity) {
		UserEntity currentUser = currentUserService.getCurrentUser();
		entity.setOwner(currentUser);
		if (currentUser.getRate() < 100) {
			currentUser.setRate(currentUser.getRate() + 1);
			userService.update(currentUser);
		}
		return super.create(entity);
	}

	public TourEntity createAndSaveImages(Long tourId, MultipartFile[] files) throws UnsupportedImageTypeException, WrongImageSizeException {
		TourEntity tour = findById(tourId);
		for (MultipartFile file: files) {
			ImageEntity image = imageService.saveImage(file);
			setImageToTour(tour, image);
		}
		return update(tour);
	}

	public TourEntity joinTourist(Long tourId, Long touristId) {
		TourEntity tour = getDAO().findById(tourId);
		UserEntity userEntity = userService.findById(touristId);

		tour.getTourists().add(userEntity);
		return getDAO().save(tour);
	}

	private void setImageToTour(TourEntity tour, ImageEntity image) {
		List<ImageEntity> images = tour.getImages();
		images.add(image);
		tour.setImages(images);
	}

	@Override
	protected AbstractDAO<TourEntity, ? extends BaseCRUDRepository<TourEntity>> getDAO() {
		return dao;
	}
}
