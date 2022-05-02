package com.kot.horizon.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import com.kot.horizon.api.filtering.FilteringOperation;
import com.kot.horizon.api.filtering.specifications.EmptyCheckSpecification;
import com.kot.horizon.api.filtering.specifications.EqualingSpecification;
import com.kot.horizon.api.searching.SearchCriteria;
import com.kot.horizon.model.Archivable;
import com.kot.horizon.model.BaseEntity;
import com.kot.horizon.model.UserFilterable;
import com.kot.horizon.model.user.UserEntity;
import com.kot.horizon.repository.BaseCRUDRepository;
import com.kot.horizon.service.user.CurrentUserService;

public abstract class AbstractDAO<Entity extends BaseEntity,
		Repository extends BaseCRUDRepository<Entity>> {

	protected Repository repository;

	@Autowired
	protected CurrentUserService currentUserService;

	@Autowired
	protected void setRepository(Repository repository) {
		this.repository = repository;
	}

	public Entity findById(Long id) {
		return findOne(getSpecification("id", id));
	}

	public Entity findOne(Specification<Entity> specification) {
		specification = setSpecificationsForReading(specification);
		return repository.findOne(specification).orElse(null);
	}

	public Entity findOneForSystem(Specification<Entity> specification) {
		// this method allowed for NotificationGroupDAO
		return findOne(specification);
	}

	public Page<Entity> findAll(Specification<Entity> specification, Pageable paging) {
		specification = setSpecificationsForReading(specification);
		return repository.findAll(specification, paging);
	}

	public List<Entity> findAll(Specification<Entity> specification) {
		specification = setSpecificationsForReading(specification);
		return repository.findAll(specification);
	}

	@Transactional
	public List<Entity> findAllForSystem(Specification<Entity> specification) {
		return repository.findAll(specification);
	}

	public Entity findByIdForModifying(Entity entity) {
		return findOneForModifying(getSpecification("id", entity.getId()));
	}

	public Entity findByIdForDeleting(Entity entity) {
		return findByIdForDeleting(entity.getId());
	}

	public Entity findByIdForDeleting(Long id) {
		return findOneForDeleting(getSpecification("id", id));
	}

	protected Entity findOneForModifying(Specification<Entity> specification) {
		specification = setSpecificationsForUpdating(specification);
		return repository.findOne(specification).orElse(null);
	}

	protected Entity findOneForDeleting(Specification<Entity> specification) {
		specification = setSpecificationsForDeleting(specification);
		return repository.findOne(specification).orElse(null);
	}

	@Transactional
	public Entity save(Entity entity) {
		System.out.println("entity >>> " + entity.getClass());
		return repository.save(entity);
	}

	public void delete(Entity entity) {
		repository.deleteById(entity.getId());
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}

	public long count(Specification<Entity> specification) {
		return repository.count(specification);
	}

	public boolean canUpdate(Entity entity) {
		return findByIdForModifying(entity) != null;
	}

	public boolean canDelete(Entity entity) {
		return findByIdForDeleting(entity) != null;
	}

	protected Specification<Entity> setSpecificationsForReading(Specification<Entity> specification) {
		return setDefaultSpecifications(specification);
	}

	protected Specification<Entity> setSpecificationsForUpdating(Specification<Entity> specification) {
		return setDefaultSpecifications(specification);
	}

	protected Specification<Entity> setSpecificationsForDeleting(Specification<Entity> specification) {
		return setDefaultSpecifications(specification);
	}

	protected Specification<Entity> setDefaultSpecifications(Specification<Entity> specification) {

		UserEntity currentUser = currentUserService.getCurrentUser();
		if (currentUser == null) {
			if (isArchivable()) {
				specification = setArchivableSpecification(specification);
			}
			return specification;
		}

		if (currentUserService.isAdministrator(currentUser)) {
			return getNegativeSpecification("id", 0).and(specification);
		}

		if (isUserFilterable()) {
			specification = getSpecification("user", currentUser).and(specification);
		}
		if (isArchivable()) {
			specification = setArchivableSpecification(specification);
		}
		return specification;
	}

	protected Specification<Entity> setArchivableSpecification(Specification<Entity> specification) {
		return getSpecification("isArchived", false).and(specification);
	}

	protected boolean isUserFilterable() {
		Class<Entity> entityTypeClasses[] = getEntityTypeClasses();
		return UserFilterable.class.isAssignableFrom(entityTypeClasses[0]);
	}

	public boolean isArchivable() {
		Class<Entity> entityTypeClasses[] = getEntityTypeClasses();
		return Archivable.class.isAssignableFrom(entityTypeClasses[0]);
	}

	protected Specification<Entity> getSpecification(String key, Object value) {
		return new EqualingSpecification<>(
				new SearchCriteria(key, FilteringOperation.EQUAL, value));
	}

	protected Specification<Entity> getEmptySpecification(String key) {
		return new EmptyCheckSpecification<>(
				new SearchCriteria(key, FilteringOperation.EMPTY, null));
	}

	protected Specification<Entity> getNegativeSpecification(String key, Object value) {
		return new EqualingSpecification<>(
				new SearchCriteria(key, FilteringOperation.NOT_EQUAL, value));
	}

	private Class<Entity>[] getEntityTypeClasses() {
		return (Class<Entity>[]) GenericTypeResolver.resolveTypeArguments(getClass(), AbstractDAO.class);
	}
}
