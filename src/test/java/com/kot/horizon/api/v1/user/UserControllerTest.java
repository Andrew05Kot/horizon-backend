package com.kot.horizon.api.v1.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import com.kot.horizon.api.v1.common.AbstractControllerTest;
import com.kot.horizon.common.filtering.FilteringOperation;
import com.kot.horizon.common.filtering.SearchCriteria;
import com.kot.horizon.user.model.Language;
import com.kot.horizon.user.model.UserEntity;
import com.kot.horizon.user.service.UserService;
import com.kot.horizon.user.specification.UserSpecificationsBuilder;

public class UserControllerTest extends AbstractControllerTest <
		UserEntity,
		UserService,
		UserSpecificationsBuilder,
		User,
		User> {

	@MockBean
	private UserService userService;

	@Override
	@Test
	public void createItemTest() throws Exception {
		loginAsUser();
		User requestBean = getNewRequestBean();
		mockMvc.perform( post( getControllerPath() )
						.content( getJson( requestBean ) )
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON) )
				.andExpect( status().isServiceUnavailable() )
				.andExpect( jsonPath("$.reason").value("The operation is not supported"));
		verify(getService(), times(0)).create( any(UserEntity.class) );
	}

	@Override
	@Test
	public void deleteTest() throws Exception {
		loginAsAdmin();
		UserEntity entity = getNewEntity();
		when( getService().findById( entity.getId() ) ).thenReturn( entity );
		mockMvc.perform( delete( getControllerPath() + "/{id}", entity.getId() ) )
				.andExpect( status().isServiceUnavailable() )
				.andExpect( jsonPath("$.reason").value("The operation is not supported"));

		verify(getService(), times(0)).delete(any(Long.class));
	}

	@Override
	protected UserEntity getNewEntityForUser(UserEntity user) {
		return user;
	}

	@Override
	protected User convertToOwnerResponseBean(UserEntity userEntity) {
		User user = new User();
		user.setId(userEntity.getId());
		user.setLastName(userEntity.getLastName());
		user.setFirstName(userEntity.getFirstName());
		user.setBirthDate(userEntity.getBirthDate());
		user.setRate(userEntity.getRate());
		user.setEmail(userEntity.getEmail());
		user.setLanguage(userEntity.getLanguage());
		user.setRole(userEntity.getRole());
		user.setSocialType(userEntity.getSocialType());
		user.setAboutMe(userEntity.getAboutMe());
		return user;
	}

	@Override
	protected User convertToAdminResponseBean(UserEntity entity) {
		return convertToOwnerResponseBean(entity);
	}

	@Override
	protected Class<UserEntity> getEntityClass() {
		return UserEntity.class;
	}

	@Override
	protected Map<List<String>, Sort> getSortingTestParameters() {
		Map< List< String >, Sort> sortingTestParameters = new HashMap<>();

		sortingTestParameters.put(
				Arrays.asList( "email" ),
				Sort.by(Sort.Direction.ASC, "email").and(Sort.by(Sort.Direction.ASC, "id")));

		sortingTestParameters.put(
				Arrays.asList( "email,DESC" ),
				Sort.by(Sort.Direction.DESC, "email").and(Sort.by(Sort.Direction.ASC, "id")));

		sortingTestParameters.put(
				Arrays.asList( "email,DESC", "lastName,ASC" ),
				Sort.by(Sort.Direction.DESC, "email")
						.and(Sort.by(Sort.Direction.ASC, "lastName"))
						.and(Sort.by(Sort.Direction.ASC, "id")));

		return sortingTestParameters;
	}

	@Override
	protected Map<List<String>, List<SearchCriteria>> getFilteringTestParameters() {
		Map<List<String>, List<SearchCriteria>>  filteringTestParameters = new HashMap<>();

		filteringTestParameters.put(
				Arrays.asList( "email:syas" ),
				Arrays.asList( new SearchCriteria("email", FilteringOperation.CONTAIN, "syas") ) );

		filteringTestParameters.put(
				Arrays.asList( "fullName:B" ),
				Arrays.asList( new SearchCriteria("fullName", FilteringOperation.CONTAIN, "B") ) );

		filteringTestParameters.put(
				Arrays.asList( "fullName:Yas" ),
				Arrays.asList( new SearchCriteria("fullName", FilteringOperation.CONTAIN, "Yas") ) );

		return filteringTestParameters;
	}

	@Override
	protected Map<Consumer<User>, String> getNonValidValuesTestParameters() {
		Map< Consumer< User >, String >  parameters = new HashMap<>();
		parameters.put( item -> item.setLastName(null), "'lastName' must not be blank");
		parameters.put( item -> item.setEmail("bla bla"), "'email' must be a well-formed email address");
		return parameters;
	}

	@Override
	protected Map<Consumer<User>, Pair<Function<UserEntity, Object>, Object>> getPatchValuesTestParameters() {
		Map< Consumer< User >, Pair< Function< UserEntity, Object >, Object > > parameters = new HashMap<>();
		parameters.put( item -> item.setLanguage(Language.EN), Pair.of( UserEntity :: getLanguage, Language.EN) );
		parameters.put( item -> item.setLanguage(Language.UK), Pair.of( UserEntity :: getLanguage, Language.UK) );
		return parameters;
	}

	@Override
	protected UserService getService() {
		return userService;
	}

	@Override
	protected String getControllerPath() {
		return UserController.BASE_URL;
	}

	@Override
	protected UserEntity getNewEntity() {
		return userBuilder.build();
	}

	@Override
	protected User getNewRequestBean() {
		return convertToPublicResponseBean( getNewEntity() );
	}

	@Override
	protected User convertToRequestBean(UserEntity entity) {
		return convertToPublicResponseBean(entity);
	}

	@Override
	protected User convertToPublicResponseBean(UserEntity entity) {
		User user = new User();
		user.setId(entity.getId());
		user.setLastName(entity.getLastName());
		user.setFirstName(entity.getFirstName());
		user.setRate(entity.getRate());
		user.setAboutMe(entity.getAboutMe());
//		user.setPhoneNumber(entity.getPhoneNumber());
		return user;
	}
}
