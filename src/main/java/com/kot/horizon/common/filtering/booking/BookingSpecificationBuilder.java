package com.kot.horizon.common.filtering.booking;

import java.util.Arrays;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import com.kot.horizon.booking.BookingEntity;
import com.kot.horizon.common.filtering.EntityFilterSpecificationsBuilder;
import com.kot.horizon.common.filtering.FilterableProperty;
import com.kot.horizon.common.filtering.SearchCriteria;
import com.kot.horizon.common.filtering.tour.TourNameOrDescriptionSpecificationBuilder;
import com.kot.horizon.tour.model.TourEntity;

@Component
public class BookingSpecificationBuilder implements EntityFilterSpecificationsBuilder<BookingEntity> {
	@Override
	public List<FilterableProperty<BookingEntity>> getFilterableProperties() {
		return Arrays.asList(
				new FilterableProperty<BookingEntity>("owner", Long.class,
						BookingOwnerSpecificationBuilder.SUPPORTED_OPERATORS, new BookingOwnerSpecificationBuilder())
		);
	}

	@Override
	public Specification<BookingEntity> buildSpecification(List<SearchCriteria> searchCriterias) {
		return EntityFilterSpecificationsBuilder.super.buildSpecification(searchCriterias);
	}
}
