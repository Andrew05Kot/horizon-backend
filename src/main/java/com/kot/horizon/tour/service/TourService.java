package com.kot.horizon.tour.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.kot.horizon.architecture.dao.AbstractDAO;
import com.kot.horizon.architecture.repository.BaseCRUDRepository;
import com.kot.horizon.architecture.service.AbstractService;
import com.kot.horizon.image.exception.UnsupportedImageTypeException;
import com.kot.horizon.image.exception.WrongImageSizeException;
import com.kot.horizon.image.model.ImageEntity;
import com.kot.horizon.image.service.ImageService;
import com.kot.horizon.tour.dao.TourDao;
import com.kot.horizon.tour.model.TourEntity;
import com.kot.horizon.user.service.CurrentUserService;

@Service
public class TourService extends AbstractService<TourEntity> {

	@Autowired
	private TourDao dao;

	@Autowired
	private ImageService imageService;

	@Autowired
	private CurrentUserService currentUserService;

	@Override
	public TourEntity create(TourEntity entity) {
		entity.setOwner(currentUserService.getCurrentUser());
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
