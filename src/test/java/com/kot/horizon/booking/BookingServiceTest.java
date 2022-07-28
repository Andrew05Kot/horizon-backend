package com.kot.horizon.booking;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.kot.horizon.common.UserBuilder;
import com.kot.horizon.common.AbstractServiceTest;
import com.kot.horizon.user.model.UserEntity;
import com.kot.horizon.user.model.UserRole;
import com.kot.horizon.user.service.UserService;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest extends AbstractServiceTest<BookingEntity, BookingService, BookingDao> {

	@MockBean
	private BookingDao bookingDao;

	@MockBean
	private UserService userService;

	@Autowired
	private BookingService tourService;

	private final UserBuilder userBuilder = new UserBuilder();
	private final BookingBuilder bookingBuilder = new BookingBuilder();

	@BeforeEach
	public void init() {
		UserEntity owner = userBuilder.setRole(UserRole.ROLE_USER).build();
		loginUser(owner);
	}

	@Override
	protected Map<Consumer<BookingEntity>, String> getNonValidValuesTestParameters() {
		return new HashMap<>();
	}

	@Override
	protected BookingEntity getNewEntity() {
		return bookingBuilder.build();
	}

	@Override
	protected BookingDao getDao() {
		return bookingDao;
	}
}