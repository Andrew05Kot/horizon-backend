package com.kot.horizon.api.v1.tour;

import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.kot.horizon.api.v1.geodata.GeoDataMapper;
import com.kot.horizon.api.v1.image.ImageMapper;
import com.kot.horizon.api.v1.tour.TourRequest;
import com.kot.horizon.api.v1.tour.TourResponse;
import com.kot.horizon.api.v1.user.UserMapper;
import com.kot.horizon.common.service.datetime.DateTimeService;
import com.kot.horizon.tour.TourEntity;

@Component
public class TourMapper {

	@Autowired
	private ImageMapper imageMapper;

	@Autowired
	private GeoDataMapper geoDataMapper;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private DateTimeService dateTimeService;

	public TourEntity toEntity(TourRequest request) {
		TourEntity entity = new TourEntity();
		entity.setName(request.getName());
		entity.setDescription(request.getDescription());
		entity.setRate(request.getRate());
		entity.setGeoData(geoDataMapper.toEntity(request.getGeoData()));
		entity.setEventDate(dateTimeService.toZonedDateTime(request.getEventDate()));
		entity.setPrice(request.getPrice());
		return entity;
	}

	public TourResponse toDto(TourEntity entity) {
		TourResponse response = new TourResponse();
		response.setId(entity.getId());
		response.setName(entity.getName());
		response.setDescription(entity.getDescription());
		response.setRate(entity.getRate());
		response.setEventDate(entity.getEventDate().toLocalDateTime());
		if (entity.getImages() != null) {
			response.setImages(entity.getImages().stream().map(image -> imageMapper.toDto(image)).collect(Collectors.toList()));
		}
		if (entity.getGeoData() != null) {
			response.setGeoData(geoDataMapper.toDto(entity.getGeoData()));
		}
		if (entity.getTourists() != null) {
			response.setJoinedUsers(entity.getTourists().stream().map(user -> userMapper.getResponseBean(user, null)).collect(Collectors.toList()));
		}
		response.setOwner(userMapper.getResponseBean(entity.getOwner(), null));
		response.setPrice(entity.getPrice());
		return response;
	}


}
