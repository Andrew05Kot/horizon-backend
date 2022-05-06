package com.kot.horizon.tour.repository;

import org.springframework.stereotype.Repository;
import com.kot.horizon.architecture.repository.BaseCRUDRepository;
import com.kot.horizon.tour.model.TourEntity;

@Repository
public interface TourRepository extends BaseCRUDRepository<TourEntity> {
}
