package com.kot.horizon.api.v1.common;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import com.kot.horizon.api.v1.ResponsePage;
import com.kot.horizon.api.v1.general.AbstractAPIService;
import com.kot.horizon.api.v1.general.AbstractRequest;
import com.kot.horizon.api.v1.general.AbstractResponse;
import com.kot.horizon.architecture.model.BaseEntity;
import com.kot.horizon.architecture.service.AbstractService;
import com.kot.horizon.common.TestsDetails;
import com.kot.horizon.common.filtering.EntityFilterSpecificationsBuilder;
import com.kot.horizon.common.filtering.SearchCriteria;
import com.kot.horizon.user.model.UserEntity;

@DirtiesContext
@AutoConfigureMockMvc
public abstract class AbstractControllerTest<
		Entity extends BaseEntity,
		Service extends AbstractService<Entity>,
		SpecificationsBuilder extends EntityFilterSpecificationsBuilder<Entity>,
		RequestBean extends AbstractRequest,
		ResponseBean extends AbstractResponse>
		extends TestsDetails {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractControllerTest.class);

	@Autowired
	protected MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;

	@Autowired
	protected SpecificationsBuilder specificationsBuilder;

	@Autowired
	protected MockMvc mockMvc;

	@SuppressWarnings("unchecked")
	@Test
	public void createItemTest() throws Exception {
		createItemTest(() -> {
			verify(getService(), times(1)).create(any());
		}, HttpStatus.CREATED);
	}

	protected void createItemTest(Runnable checkAction, HttpStatus httpStatus) {
		loginAsAdmin();
		RequestBean requestBean = getNewRequestBean();
		when(getService().create(any())).thenAnswer(invocation -> {
			Entity entity = spy((Entity) invocation.getArguments()[0]);
			fillJustCreatedEntity(entity);
			when(entity.getId()).thenReturn(new Random().nextLong());
			return entity;
		});

		try {
			ResultActions resultActions = mockMvc.perform(post(getControllerPath())
					.content(getJson(requestBean))
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
			checkAction.run();
		} catch (Exception e) {
			LOGGER.error("Failed createItemTest", e);
			fail();
		}
	}

	protected void fillJustCreatedEntity(Entity entity) {
		// cn be overried
	}

	@Test
	public void updateItemTest() throws Exception {
		loginAsAdmin();
		Entity entity = getNewEntity();
		when(getService().findById(entity.getId())).thenReturn(entity);
		RequestBean requestBean = convertToRequestBean(entity);
		mockMvc.perform(put(getControllerPath() + "/{id}", entity.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(getJson(requestBean)))
				.andExpect(status().isOk());

		verify(getService(), times(1)).findById(any(Long.class));
		verify(getService(), times(1)).update(any());
	}

	@Test
	public void nonValidValuesTest() throws Exception {
		loginAsAdmin();
		getNonValidValuesTestParameters().forEach(this::doValidationTest);
	}

	protected void doValidationTest(Consumer<RequestBean> valueSetter, String expectedExceptionText) {
		Entity entity = getNewEntity();
		RequestBean requestBean = convertToRequestBean(entity);
		valueSetter.accept(requestBean);
		try {
			mockMvc.perform(put(getControllerPath() + "/{id}", entity.getId())
							.contentType(MediaType.APPLICATION_JSON)
							.content(getJson(requestBean)))
					.andExpect(status().isBadRequest())
					.andExpect(jsonPath("$.reason").value(containsString(expectedExceptionText)));
		} catch (Exception e) {
			LOGGER.error("Failed validation test because an exception", e);
			fail();
		}
		verify(getService(), times(0)).findById(any(Long.class));
		verify(getService(), times(0)).update(any());
	}

	@Test
	public void patchValuesTest() throws Exception {
		loginAsAdmin();
		getPatchValuesTestParameters().forEach(this::doPatchTest);
	}

	private void doPatchTest(Consumer<RequestBean> valueSetter, Pair<Function<Entity, Object>, Object> valueProvider) {
		Entity entity = getNewEntity();
		RequestBean requestBean = convertToRequestBean(entity);
		valueSetter.accept(requestBean);
		when(getService().findById(entity.getId())).thenReturn(entity);
		try {
			mockMvc.perform(patch(getControllerPath() + "/{id}", entity.getId())
							.contentType(MediaType.APPLICATION_JSON)
							.content(getJson(requestBean)))
					.andDo(MockMvcResultHandlers.print())
					.andExpect(status().isOk());
		} catch (Exception e) {
			LOGGER.error("Failed patch test because an exception", e);
			fail();
		}
		Entity updatedEntity = getLastUpdatedEntity();
		assertEquals(valueProvider.getRight(), valueProvider.getLeft().apply(updatedEntity));
	}

	@Test
	public void findByIdSuccessTest() throws Exception {
		Entity entity = getNewEntity();
		when(getService().findById(entity.getId())).thenReturn(entity);
		ResponseBean expectedResponse = convertToPublicResponseBean(entity);
		mockMvc.perform(get(getControllerPath() + "/{id}", entity.getId())
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().json(getJson(expectedResponse)));
	}

	@Test
	public void findById404Test() throws Exception {
		loginAsUser();
		long missingId = 10000L;
		when(getService().findById(missingId)).thenReturn(null);
		mockMvc.perform(get(getControllerPath() + "/{id}", missingId)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.reason").value(AbstractAPIService.NON_FOUND_MESSAGE));
	}

	@Test
	public void findAllTest() throws Exception {
		List<Entity> entities = Arrays.asList(getNewEntity(), getNewEntity());
		doFindAllTest(entities, this::convertToPublicResponseBean);
	}

	@Test
	public void findAllPublicTest() throws Exception {
		findAllTest();
	}

	@Test
	public void findAllOwnerTest() throws Exception {
		UserEntity user = loginAsUser();
		List<Entity> entities = getEntitiesForOwner(user);
		doFindAllTest(entities, this::convertToOwnerResponseBean);
	}

	protected List<Entity> getEntitiesForOwner(UserEntity user) {
		return Arrays.asList(getNewEntityForUser(user), getNewEntityForUser(user));
	}

	protected abstract Entity getNewEntityForUser(UserEntity user);

	@Test
	public void findAllAdminTest() throws Exception {
		loginAsAdmin();
		List<Entity> entities = Arrays.asList(getNewEntity(), getNewEntity());
		doFindAllTest(entities, this::convertToAdminResponseBean);
	}

	protected void doFindAllTest(List<Entity> entities, Function<Entity, ResponseBean> responseBeanConverter) throws Exception {
		Page<Entity> page = getPageMock();

		when(page.getContent()).thenReturn(entities);
		when(page.getTotalElements()).thenReturn(765L);
		when(getService().findAll(isNull(), any(Pageable.class))).thenReturn(page);

		List<ResponseBean> expectedResponse = entities.stream()
				.map(responseBeanConverter).collect(Collectors.toList());

		ResponsePage<ResponseBean> responsePage = new ResponsePage<>(expectedResponse, 765L);
		mockMvc.perform(get(getControllerPath())
						.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().json(getJson(responsePage)));
	}

	@Test
	public void findAllDefaultPageValuesTest() throws Exception {
		loginAsUser();
		Page<Entity> page = getPageMock();
		when(page.getContent()).thenReturn(new ArrayList<>());
		when(getService().findAll(isNull(), any(Pageable.class))).thenReturn(page);

		mockMvc.perform(get(getControllerPath())
						.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk());

		Pageable pageable = getLastPageable();

		assertEquals(8, pageable.getPageSize());
		assertEquals(0, pageable.getPageNumber());
	}

	@Test
	public void findAllWhenPageSizeLessThanMaxPageSizeTest() throws Exception {
		loginAsUser();
		Page<Entity> page = getPageMock();
		when(page.getContent()).thenReturn(new ArrayList<>());
		when(getService().findAll(isNull(), any(Pageable.class))).thenReturn(page);

		mockMvc.perform(get(getControllerPath())
						.param("pageSize", "15")
						.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk());

		Pageable pageable = getLastPageable();

		assertEquals(15, pageable.getPageSize());
	}

	@Test
	public void findAllWhenPageSizeMoreThanMaxPageSizeTest() throws Exception {
		loginAsUser();
		Page<Entity> page = getPageMock();
		when(page.getContent()).thenReturn(new ArrayList<>());
		when(getService().findAll(isNull(), any(Pageable.class))).thenReturn(page);

		mockMvc.perform(get(getControllerPath())
						.param("pageSize", "80")
						.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk());

		Pageable pageable = getLastPageable();

		assertEquals(64, pageable.getPageSize());
	}

	@Test
	public void findAllCustomPageValuesTest() throws Exception {
		loginAsUser();
		Page<Entity> page = getPageMock();
		when(page.getContent()).thenReturn(new ArrayList<>());
		when(getService().findAll(isNull(), any(Pageable.class))).thenReturn(page);

		mockMvc.perform(get(getControllerPath())
						.param("pageNo", "2")
						.param("pageSize", "10")
						.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk());

		Pageable pageable = getLastPageable();

		assertEquals(10, pageable.getPageSize());
		assertEquals(2, pageable.getPageNumber());
	}

	@Test
	public void findAllSortingTest() {
		Page<Entity> page = getPageMock();
		when(page.getContent()).thenReturn(new ArrayList<>());
		when(getService().findAll(isNull(), any(Pageable.class))).thenReturn(page);

		Map<List<String>, Sort> sortingTestParameters = getSortingTestParameters();
		sortingTestParameters.forEach(this::doSortingTest);
	}

	protected void doSortingTest(List<String> sortingValues, Sort expectedResult) {
		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(getControllerPath());
		sortingValues.forEach(
				sortingValue -> mockHttpServletRequestBuilder.param("sort", sortingValue));

		try {
			mockMvc.perform(mockHttpServletRequestBuilder)
					.andExpect(status().isOk());
		} catch (Exception e) {
			LOGGER.error("Failed sorting test because an exception", e);
			fail();
		}

		Pageable pageable = getLastPageable();
		assertEquals(expectedResult, pageable.getSort());
	}

	@Test
	public void findAllFilteringTest() {
		loginAsUser();
		Page<Entity> page = getPageMock();
		when(page.getContent()).thenReturn(new ArrayList<>());
		when(getService().findAll(any(), any(Pageable.class))).thenReturn(page);

		Map<List<String>, List<SearchCriteria>> filteringTestParameters = getFilteringTestParameters();
		filteringTestParameters.forEach(this::doFilterTest);
	}

	protected void doFilterTest(List<String> filteringValues, List<SearchCriteria> expectedSearchCriterias) {
		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = get(getControllerPath());
		filteringValues.forEach(
				filteringValue -> mockHttpServletRequestBuilder.param("search", filteringValue));

		try {
			mockMvc.perform(mockHttpServletRequestBuilder)
					.andExpect(status().isOk());
		} catch (Exception e) {
			LOGGER.error("Failed filtering test because an exception", e);
			fail();
		}
		Specification<Entity> expectedResult = specificationsBuilder.buildSpecification(expectedSearchCriterias);
		Specification<Entity> specification = getLastCalledSpecification();
		assertEquals(expectedResult, specification);
	}

	@Test
	public void findAllFilteringWrongValueTest() throws Exception {
		loginAsUser();
		Page<Entity> page = getPageMock();
		when(page.getContent()).thenReturn(new ArrayList<>());
		when(getService().findAll(any(), any(Pageable.class))).thenReturn(page);

		mockMvc.perform(get(getControllerPath())
						.param("search", "bla bla")
				)
				.andExpect(status().isOk());
		Specification<Entity> specification = getLastCalledSpecification();
		assertNull(specification);
	}

	@Test
	public void deleteTest() throws Exception {
		loginAsAdmin();
		Entity entity = getNewEntity();
		when(getService().findById(entity.getId())).thenReturn(entity);
		mockMvc.perform(delete(getControllerPath() + "/{id}", entity.getId()))
				.andExpect(status().isNoContent());

		verify(getService(), times(1)).delete(any());
	}

	protected Pageable getLastPageable() {
		ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);
		verify(getService(), atLeastOnce()).findAll(isNull(), captor.capture());
		return captor.getValue();
	}

	protected Specification<Entity> getLastCalledSpecification() {
		ArgumentCaptor<Specification<Entity>> captor = getSpecificationArgumentCaptor();
		verify(getService(), atLeastOnce()).findAll(captor.capture(), any(Pageable.class));
		return captor.getValue();
	}

	protected Entity getLastUpdatedEntity() {
		ArgumentCaptor<Entity> captor = ArgumentCaptor.forClass(getEntityClass());
		verify(getService(), atLeastOnce()).update(captor.capture());
		return captor.getValue();
	}

	protected abstract ResponseBean convertToOwnerResponseBean(Entity entity);

	protected abstract ResponseBean convertToAdminResponseBean(Entity entity);

	@SuppressWarnings("unchecked")
	protected Page<Entity> getPageMock() {
		return mock(Page.class);
	}

	@SuppressWarnings("unchecked")
	protected ArgumentCaptor<Specification<Entity>> getSpecificationArgumentCaptor() {
		return ArgumentCaptor.forClass(Specification.class);
	}

	protected abstract Class<Entity> getEntityClass();

	protected abstract Map<List<String>, Sort> getSortingTestParameters();

	protected abstract Map<List<String>, List<SearchCriteria>> getFilteringTestParameters();

	protected abstract Map<Consumer<RequestBean>, String> getNonValidValuesTestParameters();

	protected abstract Map<Consumer<RequestBean>, Pair<Function<Entity, Object>, Object>> getPatchValuesTestParameters();

	protected abstract Service getService();

	protected abstract String getControllerPath();

	protected abstract Entity getNewEntity();

	protected abstract RequestBean getNewRequestBean();

	protected abstract RequestBean convertToRequestBean(Entity entity);

	protected abstract ResponseBean convertToPublicResponseBean(Entity entity);

	protected String getJson(Object object) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(object, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}

}
