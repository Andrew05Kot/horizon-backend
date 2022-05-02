package com.kot.horizon.api.filtering.specifications;

import org.springframework.data.jpa.domain.Specification;
import com.kot.horizon.api.searching.SearchCriteria;

public interface SpecificationBuilder<T> {

     Specification <T> buildSpecification(SearchCriteria searchCriteria);

}