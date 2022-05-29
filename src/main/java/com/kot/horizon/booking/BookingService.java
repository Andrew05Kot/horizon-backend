package com.kot.horizon.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kot.horizon.architecture.dao.AbstractDAO;
import com.kot.horizon.architecture.repository.BaseCRUDRepository;
import com.kot.horizon.architecture.service.AbstractService;

@Service
public class BookingService extends AbstractService<BookingEntity> {

	@Autowired
	private BookingDao dao;

	@Override
	protected AbstractDAO<BookingEntity, ? extends BaseCRUDRepository<BookingEntity>> getDAO() {
		return dao;
	}

	@Override
	public BookingEntity create(BookingEntity entity) {
		entity.setStatus(BookingStatus.PENDING);
		return super.create(entity);
	}
}
