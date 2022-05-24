package com.kot.horizon.booking;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.kot.horizon.architecture.dao.AbstractDAO;
import com.kot.horizon.common.filtering.FilteringOperation;
import com.kot.horizon.common.filtering.SearchCriteria;
import com.kot.horizon.common.filtering.booking.BookingOwnerSpecification;
import com.kot.horizon.user.model.UserEntity;

@Service
public class BookingDao extends AbstractDAO<BookingEntity, BookingRepository> {

	@Override
	protected Specification<BookingEntity> setSpecificationsForReading(Specification<BookingEntity> specification) {
		UserEntity currentUser = currentUserService.getCurrentUser();
		if (!currentUserService.isAdministrator()) {
			return new BookingOwnerSpecification(new SearchCriteria("owner", FilteringOperation.EQUAL, currentUser.getId()));
		}
		return super.setSpecificationsForReading(specification);
	}
}
