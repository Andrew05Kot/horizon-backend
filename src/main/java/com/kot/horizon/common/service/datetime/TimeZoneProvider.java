package com.kot.horizon.common.service.datetime;

import java.time.ZoneId;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import com.kot.horizon.user.model.UserEntity;

public interface TimeZoneProvider {

	ZoneId getTimeZoneId();
	Expression<Integer> getExprForDayOfYear(Root<UserEntity> root, CriteriaBuilder cb);

}
