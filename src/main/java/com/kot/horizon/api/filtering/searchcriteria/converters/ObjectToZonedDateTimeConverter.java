package com.kot.horizon.api.filtering.searchcriteria.converters;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
public class ObjectToZonedDateTimeConverter implements Converter<Object, ZonedDateTime> {

	@Override
	public ZonedDateTime convert(Object source) {
		
		if (source instanceof ZonedDateTime) {
			return (ZonedDateTime) source;
		}
		
		return ZonedDateTime.parse(source.toString(), DateTimeFormatter.ISO_DATE_TIME);
	}
}
