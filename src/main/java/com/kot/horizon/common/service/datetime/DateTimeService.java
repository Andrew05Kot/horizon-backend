package com.kot.horizon.common.service.datetime;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kot.horizon.user.model.UserEntity;

@Service
public class DateTimeService {

	public static final String DATE_TIME_FORMAT_FOR_REPORT = "dd-MM-yyyy HH:mm";
	public static final String DATE_TIME_FORMAT_FOR_SCREEN = "dd-MM-yyyy HH:mm:ss.SSS";
	public static final DateTimeFormatter DATE_TIME_FORMATTER_FOR_REPORT = DateTimeFormatter.ofPattern( DATE_TIME_FORMAT_FOR_REPORT );

    @Autowired
    private TimeZoneProvider timeZoneProvider;

    public ZonedDateTime getNow(){
        return ZonedDateTime.now(timeZoneProvider.getTimeZoneId());
    }

    public ZonedDateTime getNow(ZoneId zoneId){
        return ZonedDateTime.now(zoneId);
    }

    public ZonedDateTime toZonedDateTime(LocalDateTime localDateTime) {
        return toZonedDateTime(localDateTime, timeZoneProvider.getTimeZoneId());
    }

    public ZonedDateTime toZonedDateTime(LocalDateTime localDateTime, ZoneId zoneId) {
        return ZonedDateTime.of(localDateTime, zoneId);
    }

    public Expression<Integer> getExprForDayOfYear(Root<UserEntity> root, CriteriaBuilder cb) {
		return timeZoneProvider.getExprForDayOfYear(root, cb);
	}

}