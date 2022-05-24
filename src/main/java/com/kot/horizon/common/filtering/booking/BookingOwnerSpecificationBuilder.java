package com.kot.horizon.common.filtering.booking;

import java.util.Arrays;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import com.kot.horizon.booking.BookingEntity;
import com.kot.horizon.common.filtering.FilteringOperation;
import com.kot.horizon.common.filtering.SearchCriteria;
import com.kot.horizon.common.filtering.specifications.SpecificationBuilder;

@Component
public class BookingOwnerSpecificationBuilder implements SpecificationBuilder<BookingEntity> {

	public static final List<FilteringOperation> SUPPORTED_OPERATORS = Arrays.asList(
			FilteringOperation.EQUAL);

	@Override
	public Specification<BookingEntity> buildSpecification(SearchCriteria searchCriteria) {
		return new BookingOwnerSpecification(searchCriteria);
	}
}
