package com.kot.horizon.common.filtering.booking;

import java.util.Arrays;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import com.kot.horizon.booking.BookingEntity;
import com.kot.horizon.common.filtering.EntityFilterSpecificationsBuilder;
import com.kot.horizon.common.filtering.FilterableProperty;
import com.kot.horizon.common.filtering.SearchCriteria;
import com.kot.horizon.common.filtering.booking.owner.BookingTourOwnerSpecificationBuilder;
import com.kot.horizon.common.filtering.booking.recipient.BookingTouristSpecificationBuilder;

@Component
public class BookingSpecificationBuilder implements EntityFilterSpecificationsBuilder<BookingEntity> {
	@Override
	public List<FilterableProperty<BookingEntity>> getFilterableProperties() {
		return Arrays.asList(
				new FilterableProperty<BookingEntity>("owner", Long.class,
						BookingTourOwnerSpecificationBuilder.SUPPORTED_OPERATORS, new BookingTourOwnerSpecificationBuilder()),
				new FilterableProperty<BookingEntity>("tourist", Long.class,
						BookingTouristSpecificationBuilder.SUPPORTED_OPERATORS, new BookingTouristSpecificationBuilder())
		);
	}

	@Override
	public Specification<BookingEntity> buildSpecification(List<SearchCriteria> searchCriterias) {
		return EntityFilterSpecificationsBuilder.super.buildSpecification(searchCriterias);
	}
}
