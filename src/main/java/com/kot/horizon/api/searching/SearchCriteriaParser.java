package com.kot.horizon.api.searching;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import com.kot.horizon.api.filtering.FilterableProperty;
import com.kot.horizon.api.filtering.FilteringOperation;
import com.kot.horizon.api.filtering.IllegalFilteringOperationException;

@Service
public class SearchCriteriaParser {

    private static final Logger LOG = LoggerFactory.getLogger(SearchCriteriaParser.class);

    private ConversionService conversionService;

    @Autowired
    public SearchCriteriaParser(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    public <T> List<SearchCriteria> parseSearchCriteria(String searchQuery, List<FilterableProperty<T>> filterableProperties) {
        String[] searchParams = searchQuery.split(",");
        List<SearchCriteria> searchCriterias = new ArrayList<>();

        for (String searchParameter : searchParams) {
            Pattern pattern = Pattern.compile( "([A-Za-z0-9.]+?)(:|[!<>_]=?|=)(.*)" );
            Matcher matcher = pattern.matcher(searchParameter);
            while (matcher.find()) {
                String key = matcher.group(1);
                String operationStr = matcher.group(2);
                FilteringOperation operation = FilteringOperation.fromString(operationStr);
                String value = matcher.group(3);

                Optional<FilterableProperty<T>> filterableProperty = filterableProperties.stream().filter(
                        property -> property.getPropertyName().equals(key)
                ).findFirst();

                if (filterableProperty.isPresent()) {
                    Object convertedValue;
                    if ("null".equals(value) || StringUtils.isEmpty(value)) {
                        convertedValue = null;
                    } else {
                    	value = decodeFilteringValue(  value );
                        convertedValue = conversionService.convert(value, filterableProperty.get().getExpectedType());
                    }
                    // check if a FilterableOperation is supported
                    if (!filterableProperty.get().getOperators().contains(operation)) {
                        throw new IllegalFilteringOperationException("Operation '" + operation + "' is not supported for property " + key);
                    }
                    searchCriterias.add(new SearchCriteria(key, operation, convertedValue));
                } else {
                    LOG.warn("Filtering on property '{}' has been skipped because it's absent in filterableProperties", key);
                }
            }
        }
        return searchCriterias;
    }

	private String decodeFilteringValue(String value) {
		try {
			value = java.net.URLDecoder.decode(value, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			LOG.error( "UnsupportedEncodingException value: {}", value, e);
		}
		return value;
	}
}