package com.kot.horizon.booking;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kot.horizon.architecture.dao.AbstractDAO;
import com.kot.horizon.architecture.repository.BaseCRUDRepository;
import com.kot.horizon.architecture.service.AbstractService;
import com.kot.horizon.tour.TourEntity;
import com.kot.horizon.tour.TourService;

@Service
public class BookingService extends AbstractService<BookingEntity> {

	@Autowired
	private BookingDao dao;

	@Autowired
	private TourService tourService;

	@Override
	protected AbstractDAO<BookingEntity, ? extends BaseCRUDRepository<BookingEntity>> getDAO() {
		return dao;
	}

	@Override
	protected void beforeCreate(BookingEntity entity) {
		entity.setStatus(BookingStatus.PENDING);
	}

	@Override
	@Transactional
	protected void beforeUpdate(BookingEntity entity) {
		if (Boolean.TRUE.equals(entity.getLiked())) {
			updateRate(entity, 3);
		} else if (entity.getLiked() != null && !entity.getLiked()) {
			updateRate(entity, -3);
		}
	}

	private void updateRate(BookingEntity entity, int count) {
		if (BookingStatus.DONE.equals(entity.getStatus())
				&& entity.getTour().getRate() < 100) {
			TourEntity tourEntity = entity.getTour();
			tourEntity.setRate(tourEntity.getRate() + count);
			this.tourService.update(entity.getTour());
		}
	}
}
