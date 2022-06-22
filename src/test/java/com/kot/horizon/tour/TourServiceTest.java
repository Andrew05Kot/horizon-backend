package com.kot.horizon.tour;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import com.kot.horizon.common.AbstractServiceTest;
import com.kot.horizon.UserBuilder;
import com.kot.horizon.user.model.UserEntity;
import com.kot.horizon.user.model.UserRole;
import com.kot.horizon.user.service.UserService;

@DirtiesContext
@ExtendWith(MockitoExtension.class)
class TourServiceTest extends AbstractServiceTest<TourEntity, TourService, TourDao> {

	@MockBean
	private TourDao tourDao;

	@MockBean
	private UserService userService;

	@Autowired
	private TourService tourService;

	private final UserBuilder userBuilder = new UserBuilder();
	private final TourBuilder tourBuilder = new TourBuilder();

	@BeforeEach
	public void init() {
		UserEntity owner = userBuilder.setRole(UserRole.ROLE_USER).build();
		loginUser(owner);
	}

	@Test
	void userRateIncreaseOnCreateTest() {
		UserEntity owner = userBuilder.setRole(UserRole.ROLE_USER).build();
		int expectedRate = owner.getRate() + 1;
		loginUser(owner);
		TourEntity entity = getNewEntity();
		entity.setOwner(owner);
		when(userService.update(any())).thenAnswer(invocation -> invocation.getArguments()[0]);
		when(tourDao.save(any())).thenAnswer(invocation -> invocation.getArguments()[0]);
		tourService.create(entity);
		assertEquals(expectedRate, getCurrentUser().getRate());
	}

	@Override
	protected Map<Consumer<TourEntity>, String> getNonValidValuesTestParameters() {
		Map<Consumer<TourEntity>, String> parameters = new HashMap<>();
		parameters.put(item -> item.setDescription("         "), "description");
		parameters.put(item -> item.setDescription(null), "description");
		parameters.put(item -> item.setName("         "), "name");
		parameters.put(item -> item.setName(null), "name");
		return parameters;
	}

	@Override
	protected TourEntity getNewEntity() {
		return tourBuilder.build();
	}

	@Override
	protected TourDao getDao() {
		return tourDao;
	}
}