package com.kot.horizon.photo.dao;

import org.springframework.stereotype.Service;
import com.kot.horizon.architecture.dao.AbstractDAO;
import com.kot.horizon.photo.model.PhotoEntity;
import com.kot.horizon.photo.repository.PhotoRepository;

@Service
public class PhotoDAO extends AbstractDAO<PhotoEntity, PhotoRepository> {
}
