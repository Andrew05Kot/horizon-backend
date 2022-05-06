package com.kot.horizon.api.v1.tour.mapper;

import org.springframework.stereotype.Component;
import com.kot.horizon.api.v1.tour.dto.TourRequest;
import com.kot.horizon.api.v1.tour.dto.TourResponse;
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
		return response;
	}

}
