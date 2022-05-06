package com.kot.horizon.tour.dao;

import org.springframework.stereotype.Service;
import com.kot.horizon.architecture.dao.AbstractDAO;
import com.kot.horizon.tour.model.TourEntity;
import com.kot.horizon.tour.repository.TourRepository;

@Service
public class TourDao extends AbstractDAO<TourEntity, TourRepository> {
}
