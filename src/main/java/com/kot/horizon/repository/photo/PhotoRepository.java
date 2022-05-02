package com.kot.horizon.repository.photo;

import org.springframework.stereotype.Repository;
import com.kot.horizon.model.photo.PhotoEntity;
import com.kot.horizon.repository.BaseCRUDRepository;

@Repository
public interface PhotoRepository extends BaseCRUDRepository<PhotoEntity> {
}
