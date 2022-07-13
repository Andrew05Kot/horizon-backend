package com.kot.horizon.api.v1.geodata;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kot.horizon.api.v1.general.AbstractAPIService;
import com.kot.horizon.common.filtering.EntityFilterSpecificationsBuilder;
import com.kot.horizon.geodata.GeoDataEntity;
import com.kot.horizon.geodata.GeoDataService;

@Service
public class GeoDataAPIService extends AbstractAPIService<GeoDataEntity, GeoDataRequest, GeoDataResponse, GeoDataService> {

	@Autowired
	private GeoDataMapper mapper;

	@Override
	protected void patch(Long id, GeoDataRequest request) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected EntityFilterSpecificationsBuilder<GeoDataEntity> getSpecificationBuilder() {
		return null;
	}

	@Override
	protected GeoDataEntity getNewEntity(GeoDataRequest request) {
		return mapper.toEntity(request);
	}

	@Override
	protected void copyProperties(GeoDataRequest request, GeoDataEntity entity) {
		entity.setLatitude(request.getLatitude());
		entity.setLongitude(request.getLongitude());
		entity.setAltitude(request.getAltitude());
		entity.setAddressName(request.getAddressName());
	}

	@Override
	protected GeoDataResponse convertToResponseBean(GeoDataEntity entity, Optional<String> expand) {
		return mapper.toDto(entity);
	}
}
