package com.kot.horizon.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kot.horizon.architecture.dao.AbstractDAO;
import com.kot.horizon.architecture.repository.BaseCRUDRepository;
import com.kot.horizon.architecture.service.AbstractService;

@Service
public class NotificationService extends AbstractService<NotificationEntity> {

	@Autowired private NotificationDao dao;

	@Override
	protected AbstractDAO<NotificationEntity, ? extends BaseCRUDRepository<NotificationEntity>> getDAO() {
		return dao;
	}
}
