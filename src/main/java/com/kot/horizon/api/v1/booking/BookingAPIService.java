package com.kot.horizon.api.v1.booking;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kot.horizon.api.v1.general.AbstractAPIService;
import com.kot.horizon.booking.BookingEntity;
import com.kot.horizon.booking.BookingService;
import com.kot.horizon.common.filtering.EntityFilterSpecificationsBuilder;
import com.kot.horizon.common.filtering.booking.BookingSpecificationBuilder;
import com.kot.horizon.tour.TourService;
import com.kot.horizon.user.service.UserService;

@Service
public class BookingAPIService extends AbstractAPIService<BookingEntity, BookingRequest, BookingResponse, BookingService> {

	@Autowired
	private BookingMapper mapper;

	@Autowired
	private UserService userService;

	@Autowired
	private TourService tourService;

	@Override
	protected void patch(Long id, BookingRequest request) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected EntityFilterSpecificationsBuilder<BookingEntity> getSpecificationBuilder() {
		return new BookingSpecificationBuilder();
	}

	@Override
	protected BookingEntity getNewEntity(BookingRequest request) {
		return mapper.toEntity(request);
	}

	@Override
	protected void copyProperties(BookingRequest request, BookingEntity entity) {
		entity.setStatus(request.getStatus());
		entity.setTourist(userService.findById(request.getTouristId()));
		entity.setTour(tourService.findById(request.getTourId()));
		entity.setStatus(request.getStatus());
		entity.setLiked(request.getLiked());
	}

	@Override
	protected BookingResponse convertToResponseBean(BookingEntity entity, Optional<String> expand) {
		return mapper.toDto(entity);
	}
}
