package com.kot.horizon.common.service.datetime;

import java.time.LocalDate;
import java.time.ZoneId;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import com.kot.horizon.user.model.UserEntity;

@Service
@Profile({"h2", "h2-test"})
public class H2TimeZoneProvider implements TimeZoneProvider {

	@Override
	public ZoneId getTimeZoneId() {
		return ZoneId.systemDefault();
	}

	@Override
	public Expression<Integer> getExprForDayOfYear(Root<UserEntity> root, CriteriaBuilder cb) {
		return cb.function("DAY_OF_YEAR", Integer.class, root.<LocalDate>get("birthDate"));
	}

}
