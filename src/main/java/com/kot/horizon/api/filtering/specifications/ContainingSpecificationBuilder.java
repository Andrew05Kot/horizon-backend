package com.kot.horizon.api.filtering.specifications;

import java.util.Arrays;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import com.kot.horizon.api.filtering.FilteringOperation;
import com.kot.horizon.api.searching.SearchCriteria;

public class ContainingSpecificationBuilder<Entity> implements SpecificationBuilder<Entity> {

	public static final List<FilteringOperation> SUPPORTED_OPERATORS = Arrays.asList(
			FilteringOperation.CONTAIN);

	@Override
	public Specification<Entity> buildSpecification(SearchCriteria searchCriteria) {
		return new ContainingSpecification<>(searchCriteria);
	}
}