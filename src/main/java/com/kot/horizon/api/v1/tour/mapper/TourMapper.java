package com.kot.horizon.api.v1.tour.mapper;

import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.kot.horizon.api.v1.image.ImageController;
import com.kot.horizon.api.v1.image.ImageUtils;
import com.kot.horizon.api.v1.tour.dto.TourRequest;
import com.kot.horizon.api.v1.tour.dto.TourResponse;
import com.kot.horizon.image.model.ImageEntity;
import com.kot.horizon.tour.model.TourEntity;

@Component
public class TourMapper {

	public TourEntity toEntity(TourRequest request) {
		TourEntity entity = new TourEntity();
		entity.setName(request.getName());
		entity.setDescription(request.getDescription());
		entity.setRate(request.getRate());
		return entity;
	}

	public TourResponse toDto(TourEntity entity) {
		TourResponse response = new TourResponse();
		response.setId(entity.getId());
		response.setName(entity.getName());
		response.setDescription(entity.getDescription());
		response.setRate(entity.getRate());
		if (entity.getImages() != null)
			response.setImages(entity.getImages()
					.stream()
					.map(ImageEntity::getImageName)
					.collect(Collectors.toList()));
		return response;
	}


}
