package com.kot.horizon.tour;

import org.springframework.stereotype.Repository;
import com.kot.horizon.architecture.repository.BaseCRUDRepository;
import com.kot.horizon.tour.TourEntity;

@Repository
public interface TourRepository extends BaseCRUDRepository<TourEntity> {
}
