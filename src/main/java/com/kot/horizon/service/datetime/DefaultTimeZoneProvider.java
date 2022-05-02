package com.kot.horizon.service.datetime;

import java.time.LocalDate;
import java.time.ZoneId;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import com.kot.horizon.model.user.UserEntity;

@Service
@Profile({"!h2 & !h2-test"})
public class DefaultTimeZoneProvider implements TimeZoneProvider {

	@Override
	public ZoneId getTimeZoneId() {
		return ZoneId.of("UTC");
	}

	@Override
	public Expression<Integer> getExprForDayOfYear(Root<UserEntity> root, CriteriaBuilder cb) {
		return cb.function("DATE_PART", Integer.class, cb.literal("doy"), root.<LocalDate>get("birthDate"));
	}

}
