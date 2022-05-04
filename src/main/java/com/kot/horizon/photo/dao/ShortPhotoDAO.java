package com.kot.horizon.photo.dao;

import org.springframework.stereotype.Service;
import com.kot.horizon.architecture.dao.AbstractDAO;
import com.kot.horizon.photo.model.ShortPhotoEntity;
import com.kot.horizon.photo.repository.ShortPhotoRepository;

@Service
public class ShortPhotoDAO extends AbstractDAO<ShortPhotoEntity, ShortPhotoRepository> {
}
