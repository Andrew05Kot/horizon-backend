package com.kot.horizon.tour;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import com.kot.horizon.TestsDetails;
import com.kot.horizon.UserBuilder;
import com.kot.horizon.user.model.UserEntity;
import com.kot.horizon.user.model.UserRole;
import com.kot.horizon.user.service.UserService;

@DirtiesContext
@ExtendWith(MockitoExtension.class)
class TourServiceTest extends TestsDetails {

	@MockBean
	private TourDao tourDao;

	@MockBean
	private UserService userService;

	@Autowired
	private TourService tourService;

	private final UserBuilder userBuilder = new UserBuilder();
	private final TourBuilder tourBuilder = new TourBuilder();

	@Test
	void itemCreationTest() {
		UserEntity owner = userBuilder.setRole(UserRole.ROLE_USER).build();
		loginUser(owner);
		TourEntity entity = getNewEntity();
		entity.setOwner(owner);
		when(userService.update(any())).thenAnswer(invocation -> invocation.getArguments()[0]);
		when(tourDao.save(any())).thenAnswer(invocation -> invocation.getArguments()[0]);
		TourEntity actualEntity = tourService.create(entity);
		assertEquals(entity, actualEntity);
	}

	protected TourEntity getNewEntity() {
		return tourBuilder.build();
	}
}