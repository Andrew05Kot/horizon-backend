package com.kot.horizon.geodata;

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
class GeoDataServiceTest extends AbstractServiceTest<GeoDataEntity, GeoDataService, GeoDataDao> {

	@MockBean
	private GeoDataDao geoDataDao;

	@MockBean
	private UserService userService;

	@Autowired
	private GeoDataService geoDataService;

	private final UserBuilder userBuilder = new UserBuilder();
	private final GeoDataBuilder geoDataBuilder = new GeoDataBuilder();

	@BeforeEach
	public void init() {
		UserEntity owner = userBuilder.setRole(UserRole.ROLE_USER).build();
		loginUser(owner);
	}

	@Override
	protected Map<Consumer<GeoDataEntity>, String> getNonValidValuesTestParameters() {
		return new HashMap<>();
	}

	@Override
	protected GeoDataEntity getNewEntity() {
		return geoDataBuilder.build();
	}

	@Override
	protected GeoDataDao getDao() {
		return geoDataDao;
	}
}