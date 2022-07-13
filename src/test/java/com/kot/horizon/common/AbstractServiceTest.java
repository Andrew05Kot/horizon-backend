package com.kot.horizon.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.function.Consumer;
import javax.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.annotation.DirtiesContext;
import com.kot.horizon.architecture.dao.AbstractDAO;
import com.kot.horizon.architecture.model.BaseEntity;
import com.kot.horizon.architecture.service.AbstractService;
import com.kot.horizon.common.filtering.FilteringOperation;
import com.kot.horizon.common.filtering.SearchCriteria;
import com.kot.horizon.common.filtering.specifications.EqualingSpecification;

@DirtiesContext
public abstract class AbstractServiceTest<
		Entity extends BaseEntity,
		Service extends AbstractService<Entity>,
		Dao extends AbstractDAO>
		extends TestsDetails {

	@Autowired
	protected Service service;

	@Test
	public void findItemByIdTest() {
		loginAsAdmin();
		Entity entity = getNewEntity();
		given(getDao().findById(entity.getId())).willReturn(entity);
		assertEquals(entity, service.findById(entity.getId()));
	}

	@Test
	public void nonValidValuesTest() {
		loginAsAdmin();
		getNonValidValuesTestParameters().forEach(this::doValidationTest);
	}

	@Test // expected no exception
	public void validationOK() {
		loginAsAdmin();
		Entity entity = getNewEntity();
		service.create(entity);
	}

	protected void doValidationTest(Consumer<Entity> valueSetter, String expectedExceptionText) {
		Entity entity = getNewEntity();
		valueSetter.accept(entity);
		try {
			service.create(entity);
		} catch (ConstraintViolationException e) {
			assertTrue(e.getMessage().contains(expectedExceptionText));
			verify(getDao(), times(0)).save(any());
			return;
		}
		fail();
	}

	@Test
	public void createTest() {
		loginAsAdmin();
		Entity entity = getNewEntity();
		when(getDao().save(any())).thenAnswer(invocation -> invocation.getArguments()[0]);
		Entity actualEntity = service.create(entity);
		assertEquals(entity, actualEntity);
	}

	@Test
	public void updateTest() {
		loginAsAdmin();
		Entity entity = getNewEntity();
		when(getDao().findById(any())).thenReturn(entity);
		when(getDao().findByIdForModifying(any())).thenReturn(entity);
		doReturn(true).when(getDao()).canUpdate(entity);
		service.update(entity);
		verify(getDao()).save(entity);
	}

	@Test
	public void notPermittedUpdate() {
		loginAsUser();
		Entity entity = getNewEntity();
		Exception exception = assertThrows(AccessDeniedException.class, () -> service.update(entity));

		assertTrue(exception.getMessage().contains("You don`t have permission!"));
		verify(getDao(), never()).save(any());
	}

	protected abstract Map<Consumer<Entity>, String> getNonValidValuesTestParameters();

	protected abstract Entity getNewEntity();

	protected abstract Dao getDao();

	protected Specification<Entity> getSpecification(String key, Object value) {
		return new EqualingSpecification<>(
				new SearchCriteria(key, FilteringOperation.EQUAL, value));
	}
}
