package com.kot.horizon.common.filtering;

import java.util.Arrays;
import java.util.List;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class SpecificationUtil {

	public static <T, R> Path<R> buildPath(Root<T> root, String fieldPath) {
		List<String> pathParts = Arrays.asList(fieldPath.split("\\."));
		Path<R> path = root.get(pathParts.get(0));
		for (String pathPart : pathParts.subList(1, pathParts.size())) {
			path = path.get(pathPart);
		}
		return path;
	}

	public static <T, R> Join<R, T> buildJoin(Root<T> root, String fieldPath) {
		List<String> joinParts = Arrays.asList(fieldPath.split("\\."));
		Join<R, T> join = root.joinSet(joinParts.get(0));
		for (String joinPart : joinParts.subList(1, joinParts.size())) {
			join = join.joinSet(joinPart);
		}
		return join;
	}

	public static <T> Specification<T> initEmptySpec() {
		return Specification.where(null);
	}
}
