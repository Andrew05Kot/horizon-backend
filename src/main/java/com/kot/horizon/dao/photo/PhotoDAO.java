package com.kot.horizon.dao.photo;

import org.springframework.stereotype.Service;
import com.kot.horizon.dao.AbstractDAO;
import com.kot.horizon.model.photo.PhotoEntity;
import com.kot.horizon.repository.photo.PhotoRepository;

@Service
public class PhotoDAO extends AbstractDAO<PhotoEntity, PhotoRepository> {
}
