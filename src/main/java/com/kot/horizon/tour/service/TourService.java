package com.kot.horizon.tour.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kot.horizon.architecture.dao.AbstractDAO;
import com.kot.horizon.architecture.repository.BaseCRUDRepository;
import com.kot.horizon.architecture.service.AbstractService;
import com.kot.horizon.tour.dao.TourDao;
import com.kot.horizon.tour.model.TourEntity;

@Service
public class TourService extends AbstractService<TourEntity> {

	@Autowired
	private TourDao dao;

	@Override
	protected AbstractDAO<TourEntity, ? extends BaseCRUDRepository<TourEntity>> getDAO() {
		return dao;
	}
}
