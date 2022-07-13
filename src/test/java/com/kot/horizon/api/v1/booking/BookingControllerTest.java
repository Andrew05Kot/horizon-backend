package com.kot.horizon.api.v1.booking;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import com.kot.horizon.api.v1.common.AbstractControllerTest;
import com.kot.horizon.api.v1.tour.TourMapper;
import com.kot.horizon.api.v1.user.UserMapper;
import com.kot.horizon.booking.BookingBuilder;
import com.kot.horizon.booking.BookingEntity;
import com.kot.horizon.booking.BookingService;
import com.kot.horizon.common.filtering.SearchCriteria;
import com.kot.horizon.common.filtering.booking.BookingSpecificationBuilder;
import com.kot.horizon.user.model.UserEntity;
import com.kot.horizon.user.model.UserRole;

public class BookingControllerTest extends AbstractControllerTest<
		BookingEntity,
		BookingService,
		BookingSpecificationBuilder,
		BookingRequest,
		BookingResponse> {

	@MockBean
	private BookingService bookingService;

	@Autowired
	private TourMapper tourMapper;

	@Autowired
	private UserMapper userMapper;

	private final BookingBuilder bookingBuilder = new BookingBuilder();

	@BeforeEach
	public void init() {
		UserEntity owner = userBuilder.setRole(UserRole.ROLE_USER).build();
		loginUser(owner);
	}

	@Override
	protected BookingEntity getNewEntityForUser(UserEntity user) {
		return bookingBuilder.setTourist(user).build();
	}

	@Override
	protected BookingResponse convertToOwnerResponseBean(BookingEntity entity) {
		return convertToPublicResponseBean(entity);
	}

	@Override
	protected BookingResponse convertToAdminResponseBean(BookingEntity entity) {
		return convertToOwnerResponseBean(entity);
	}

	@Override
	protected Class<BookingEntity> getEntityClass() {
		return BookingEntity.class;
	}

	@Override
	protected Map<List<String>, Sort> getSortingTestParameters() {
		return new HashMap<>();
	}

	@Override
	protected Map<List<String>, List<SearchCriteria>> getFilteringTestParameters() {
		return new HashMap<>();
	}

	@Override
	protected Map<Consumer<BookingRequest>, String> getNonValidValuesTestParameters() {
		return new HashMap<>();
	}

	@Override
	protected Map<Consumer<BookingRequest>, Pair<Function<BookingEntity, Object>, Object>> getPatchValuesTestParameters() {
		return new HashMap<>();
	}

	@Override
	protected BookingService getService() {
		return bookingService;
	}

	@Override
	protected String getControllerPath() {
		return BookingController.BASE_URL;
	}

	@Override
	protected BookingEntity getNewEntity() {
		return bookingBuilder.build();
	}

	@Override
	protected BookingRequest getNewRequestBean() {
		return convertToRequestBean(bookingBuilder.build());
	}

	@Override
	protected BookingRequest convertToRequestBean(BookingEntity entity) {
		BookingRequest request = new BookingRequest();
		request.setStatus(entity.getStatus());
		request.setTourId(entity.getTourist().getId());
		request.setTourId(entity.getTour().getId());
		request.setLiked(entity.getLiked());
		return request;
	}

	@Override
	protected BookingResponse convertToPublicResponseBean(BookingEntity entity) {
		BookingResponse response = new BookingResponse();
		response.setId(entity.getId());
		response.setStatus(entity.getStatus());
		response.setTour(tourMapper.toDto(entity.getTour()));
		response.setTourist(userMapper.getPublicResponse(entity.getTourist()));
		response.setLiked(entity.getLiked());
		return response;
	}
}
