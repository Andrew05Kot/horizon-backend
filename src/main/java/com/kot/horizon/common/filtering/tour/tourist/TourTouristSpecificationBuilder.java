package com.kot.horizon.common.filtering.tour.tourist;

import java.util.Arrays;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import com.kot.horizon.common.filtering.FilteringOperation;
import com.kot.horizon.common.filtering.SearchCriteria;
import com.kot.horizon.common.filtering.specifications.SpecificationBuilder;
import com.kot.horizon.tour.model.TourEntity;

@Component
public class TourTouristSpecificationBuilder implements SpecificationBuilder<TourEntity> {

	public static final List<FilteringOperation> SUPPORTED_OPERATORS = Arrays.asList(
			FilteringOperation.EQUAL);

	@Override
	public Specification<TourEntity> buildSpecification(SearchCriteria searchCriteria) {
		return new TourTouristSpecification(searchCriteria);
	}
}
