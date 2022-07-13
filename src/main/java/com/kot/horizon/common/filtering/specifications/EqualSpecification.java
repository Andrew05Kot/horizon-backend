package com.kot.horizon.common.filtering.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import com.kot.horizon.common.filtering.SpecificationUtil;

public class EqualSpecification<T> implements Specification<T> {

	private final String fieldPath;

	private final Object value;

	public EqualSpecification(String fieldPath, Object value) {
		this.fieldPath = fieldPath;
		this.value = value;
	}

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		Path<T> path = SpecificationUtil.buildPath(root, fieldPath);
		return cb.equal(path, value);
	}

}