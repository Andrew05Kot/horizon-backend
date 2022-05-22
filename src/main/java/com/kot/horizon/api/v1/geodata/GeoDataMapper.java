package com.kot.horizon.api.v1.geodata;

import org.springframework.stereotype.Component;
import com.kot.horizon.geodata.GeoDataEntity;

@Component
public class GeoDataMapper {

	public GeoDataEntity toEntity(GeoDataRequest request) {
		GeoDataEntity entity = new GeoDataEntity();
		entity.setLatitude(request.getLatitude());
		entity.setLongitude(request.getLongitude());
		entity.setAltitude(request.getAltitude());
		entity.setAddressName(request.getAddressName());
		return entity;
	}

	public GeoDataResponse toDto(GeoDataEntity entity) {
		GeoDataResponse response = new GeoDataResponse();
		response.setId(entity.getId());
		response.setLatitude(entity.getLatitude());
		response.setLongitude(entity.getLongitude());
		response.setAltitude(entity.getAltitude());
		response.setAddressName(entity.getAddressName());
		return response;
	}

}
