package com.kot.horizon.api.v1.tour.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.kot.horizon.api.v1.general.AbstractAPIService;
import com.kot.horizon.api.v1.tour.dto.TourRequest;
import com.kot.horizon.api.v1.tour.dto.TourResponse;
import com.kot.horizon.api.v1.tour.mapper.TourMapper;
import com.kot.horizon.common.filtering.EntityFilterSpecificationsBuilder;
import com.kot.horizon.common.filtering.tour.TourSpecificationBuilder;
import com.kot.horizon.common.service.datetime.DateTimeService;
import com.kot.horizon.image.exception.UnsupportedImageTypeException;
import com.kot.horizon.image.exception.WrongImageSizeException;
import com.kot.horizon.image.service.ImageService;
import com.kot.horizon.tour.model.TourEntity;
import com.kot.horizon.tour.service.TourService;

@Service
public class TourAPIService extends AbstractAPIService<TourEntity, TourRequest, TourResponse, TourService> {

	@Autowired
	private TourMapper mapper;

	@Autowired
	private ImageService imageService;

	@Autowired
	private TourSpecificationBuilder tourSpecificationBuilder;

	@Autowired
	private DateTimeService dateTimeService;

	public TourResponse uploadAndSaveImages(Optional<MultipartFile[]> files, Long tourId, Optional<List<Long>> imageIdsToRemove) throws UnsupportedImageTypeException, WrongImageSizeException {
		removeImagesIfNeed(imageIdsToRemove);
		TourEntity tour = files.isPresent()
				? service.createAndSaveImages(tourId, files.get())
				: service.findById(tourId);

		return mapper.toDto(tour);
	}

	@Override
	protected void patch(Long id, TourRequest request) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected EntityFilterSpecificationsBuilder<TourEntity> getSpecificationBuilder() {
		return tourSpecificationBuilder;
	}

	@Override
	protected TourEntity getNewEntity(TourRequest request) {
		return mapper.toEntity(request);
	}

	@Override
	protected void copyProperties(TourRequest request, TourEntity entity) {
		entity.setName(request.getName());
		entity.setDescription(request.getDescription());
		entity.setRate(entity.getRate());
		entity.setEventDate(dateTimeService.toZonedDateTime(request.getEventDate()));
	}

	@Override
	protected TourResponse convertToResponseBean(TourEntity entity, Optional<String> expand) {
		return mapper.toDto(entity);
	}

	private void removeImagesIfNeed(Optional<List<Long>> imageIdsToRemove) {
		if (imageIdsToRemove.isPresent()) {
			for (Long idToRemove : imageIdsToRemove.get()) {
				imageService.delete(idToRemove);
			}
		}
	}
}
