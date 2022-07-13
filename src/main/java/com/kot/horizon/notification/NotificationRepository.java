package com.kot.horizon.notification;

import org.springframework.stereotype.Repository;
import com.kot.horizon.architecture.repository.BaseCRUDRepository;

@Repository
public interface NotificationRepository extends BaseCRUDRepository<NotificationEntity> {
}
