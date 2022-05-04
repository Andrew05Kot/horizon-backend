package com.kot.horizon.photo.repository;

import org.springframework.stereotype.Repository;
import com.kot.horizon.photo.model.PhotoEntity;
import com.kot.horizon.architecture.repository.BaseCRUDRepository;

@Repository
public interface PhotoRepository extends BaseCRUDRepository<PhotoEntity> {
}
