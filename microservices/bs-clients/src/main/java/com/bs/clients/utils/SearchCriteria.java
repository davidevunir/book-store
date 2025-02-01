package com.bs.clients.utils;

import lombok.NonNull;
import java.util.List;
import java.util.LinkedList;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.domain.Specification;

public class SearchCriteria<T> implements Specification<T> {

  private final List<SearchStatement> list = new LinkedList<>();
  private static final String LIKE = "%";

  public void add(SearchStatement criteria) {
    list.add(criteria);
  }

  private String convertToString(Object value) {
    return value != null ? value.toString().toLowerCase() : "";
  }

  @Override
  public Predicate toPredicate(@NonNull Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
    var predicates = list.stream()
        .map(criteria -> switch (criteria.getOperation()) {
          case GREATER_THAN -> builder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString());
          case LESS_THAN -> builder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString());
          case GREATER_THAN_EQUAL -> builder.greaterThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString());
          case LESS_THAN_EQUAL -> builder.lessThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString());
          case NOT_EQUAL -> builder.notEqual(root.get(criteria.getKey()), criteria.getValue());
          case EQUAL -> builder.equal(root.get(criteria.getKey()), criteria.getValue());
          case MATCH -> builder.like(builder.lower(root.get(criteria.getKey())), LIKE + convertToString(criteria.getValue()) + LIKE);
          case MATCH_END -> builder.like(builder.lower(root.get(criteria.getKey())), convertToString(criteria.getValue()) + LIKE);
        }).toList();

    return builder.and(predicates.toArray(Predicate[]::new));
  }
}
