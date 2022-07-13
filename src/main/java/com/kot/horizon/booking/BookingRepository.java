package com.kot.horizon.booking;

import org.springframework.stereotype.Repository;
import com.kot.horizon.architecture.repository.BaseCRUDRepository;

@Repository
public interface BookingRepository extends BaseCRUDRepository<BookingEntity> {
}
