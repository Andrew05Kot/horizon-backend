package com.kot.horizon.architecture.service;

import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import com.kot.horizon.architecture.dao.AbstractDAO;
import com.kot.horizon.architecture.model.Archivable;
import com.kot.horizon.architecture.model.BaseEntity;
import com.kot.horizon.architecture.repository.BaseCRUDRepository;

public abstract class AbstractService<Entity extends BaseEntity> {

	protected static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

	protected abstract AbstractDAO<Entity, ? extends BaseCRUDRepository<Entity>> getDAO();

	public Entity create(Entity entity) {
		beforeCreate(entity);
		validate(entity);
		return getDAO().save(entity);
	}

	public Entity update(Entity entity) {
		beforeUpdate(entity);
		validate(entity);
		if( getDAO().findByIdForModifying( entity ) == null ){
			throw new AccessDeniedException("You don`t have permission!");
		}
		return getDAO().save(entity);
	}

	public void delete(Long id) {
		Entity toDeleting = getDAO().findByIdForDeleting(id);
		if( toDeleting == null ){
			return;
		}
		if( getDAO().isArchivable() ){
			Archivable archivable = ( Archivable ) toDeleting;
			archivable.setArchived();
			getDAO().save( toDeleting );
			return;
		}
		getDAO().delete(id);
	}

	public Page<Entity> findAll(Specification<Entity> filteringSpecification, Pageable paging) {
		return getDAO().findAll(filteringSpecification, paging);
	}

	public List<Entity> findAll(Specification<Entity> filteringSpecification, Sort sort) {
		return getDAO().findAll(filteringSpecification, sort);
	}

	public Entity findById(Long id) {
		return getDAO().findById(id);
	}

	public Entity findOne(Specification<Entity> filter) {
		return getDAO().findOne(filter);
	}

	protected void validate(Entity entity) {
		Set<ConstraintViolation<Entity>> violations = VALIDATOR.validate(entity);
		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(violations);
		}
	}

	protected <T> T cloneItem( T newItem, T oldItem ) {
		BeanUtils.copyProperties(oldItem, newItem);
		return newItem;
	}

	protected void beforeUpdate(Entity entity) {

	}

	protected void beforeCreate(Entity entity) {

	}

}
