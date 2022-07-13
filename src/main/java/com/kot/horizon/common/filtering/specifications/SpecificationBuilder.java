package com.kot.horizon.common.filtering.specifications;

import org.springframework.data.jpa.domain.Specification;
import com.kot.horizon.common.filtering.SearchCriteria;

public interface SpecificationBuilder<T> {

     Specification <T> buildSpecification(SearchCriteria searchCriteria);

}