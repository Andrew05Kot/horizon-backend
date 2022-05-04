package com.kot.horizon.common.filtering.specifications;

import java.util.Arrays;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import com.kot.horizon.common.filtering.FilteringOperation;
import com.kot.horizon.common.filtering.SearchCriteria;

public class PrefixContainingSpecificationBuilder<Entity> implements SpecificationBuilder<Entity> {

	public static final List<FilteringOperation> SUPPORTED_OPERATORS = Arrays.asList(
			FilteringOperation.CONTAIN);

	@Override
	public Specification<Entity> buildSpecification(SearchCriteria searchCriteria) {
		return new PrefixContainingSpecification<>(searchCriteria);
	}
}