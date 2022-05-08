package com.kot.horizon.image.dao;

import org.springframework.stereotype.Service;
import com.kot.horizon.architecture.dao.AbstractDAO;
import com.kot.horizon.image.model.ImageEntity;
import com.kot.horizon.image.repository.ImageRepository;

@Service
public class ImageDao extends AbstractDAO<ImageEntity, ImageRepository> {
}
