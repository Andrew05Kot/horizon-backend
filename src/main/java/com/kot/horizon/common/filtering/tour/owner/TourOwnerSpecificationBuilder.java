package com.kot.horizon.common.filtering.tour.owner;

import java.util.Arrays;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import com.kot.horizon.common.filtering.FilteringOperation;
import com.kot.horizon.common.filtering.SearchCriteria;
import com.kot.horizon.common.filtering.specifications.SpecificationBuilder;
import com.kot.horizon.tour.TourEntity;

@Component
public class TourOwnerSpecificationBuilder implements SpecificationBuilder<TourEntity> {

	public static final List<FilteringOperation> SUPPORTED_OPERATORS = Arrays.asList(
			FilteringOperation.EQUAL);

	@Override
	public Specification<TourEntity> buildSpecification(SearchCriteria searchCriteria) {
		return new TourOwnerSpecification(searchCriteria);
	}
}
