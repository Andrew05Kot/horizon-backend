package com.kot.horizon.image.repository;

import org.springframework.stereotype.Repository;
import com.kot.horizon.architecture.repository.BaseCRUDRepository;
import com.kot.horizon.image.model.ImageEntity;

@Repository
public interface ImageRepository extends BaseCRUDRepository<ImageEntity> {
}
