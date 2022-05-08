package com.kot.horizon.api.v1.tour.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kot.horizon.api.v1.general.AbstractAPIService;
import com.kot.horizon.api.v1.tour.dto.TourRequest;
import com.kot.horizon.api.v1.tour.dto.TourResponse;
import com.kot.horizon.api.v1.tour.mapper.TourMapper;
import com.kot.horizon.common.filtering.EntityFilterSpecificationsBuilder;
import com.kot.horizon.tour.model.TourEntity;
import com.kot.horizon.tour.service.TourService;

@Service
public class TourAPIService extends AbstractAPIService<TourEntity, TourRequest, TourResponse, TourService> {

	@Autowired
	private TourMapper mapper;

	@Override
	protected void patch(Long id, TourRequest request) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected EntityFilterSpecificationsBuilder<TourEntity> getSpecificationBuilder() {
		return null;
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
	}

	@Override
	protected TourResponse convertToResponseBean(TourEntity entity, Optional<String> expand) {
		return mapper.toDto(entity);
	}
}
