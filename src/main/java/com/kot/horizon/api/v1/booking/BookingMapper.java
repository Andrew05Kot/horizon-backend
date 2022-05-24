package com.kot.horizon.api.v1.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.kot.horizon.api.v1.tour.mapper.TourMapper;
import com.kot.horizon.api.v1.user.UserMapper;
import com.kot.horizon.booking.BookingEntity;
import com.kot.horizon.tour.service.TourService;
import com.kot.horizon.user.service.UserService;

@Component
public class BookingMapper {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private TourMapper tourMapper;

	@Autowired
	private TourService tourService;

	@Autowired
	private UserService userService;

	public BookingEntity toEntity(BookingRequest request) {
		BookingEntity entity = new BookingEntity();
		entity.setStatus(request.getStatus());
		entity.setTour(tourService.findById(request.getTourId()));
		entity.setTourist(userService.findById(request.getTouristId()));
		return entity;
	}

	public BookingResponse toDto(BookingEntity entity) {
		BookingResponse response = new BookingResponse();
		response.setId(entity.getId());
		response.setStatus(entity.getStatus());
		response.setTour(tourMapper.toDto(entity.getTour()));
		response.setTourist(userMapper.getPublicResponse(entity.getTourist()));
		return response;
	}
}
