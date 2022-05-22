package com.kot.horizon.geodata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kot.horizon.architecture.dao.AbstractDAO;
import com.kot.horizon.architecture.repository.BaseCRUDRepository;
import com.kot.horizon.architecture.service.AbstractService;

@Service
public class GeoDataService extends AbstractService<GeoDataEntity> {

	@Autowired
	private GeoDataDao dao;

	@Override
	protected AbstractDAO<GeoDataEntity, ? extends BaseCRUDRepository<GeoDataEntity>> getDAO() {
		return dao;
	}
}
