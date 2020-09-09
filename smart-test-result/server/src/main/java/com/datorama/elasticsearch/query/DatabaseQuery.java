package com.datorama.elasticsearch.query;

import java.util.Collection;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

public class DatabaseQuery {
  private final BoolQueryBuilder boolQueryBuilder;
  private FieldSortBuilder fieldSortBuilder;
  private PageRequest pageRequest;

  public DatabaseQuery() {
    this.boolQueryBuilder = QueryBuilders.boolQuery();
  }

  public DatabaseQuery equals(String field, String text) {
    boolQueryBuilder.filter(QueryBuilders.matchQuery(field, text));
    return this;
  }

  public DatabaseQuery notEquals(String field, String text) {
    boolQueryBuilder.mustNot(QueryBuilders.matchQuery(field, text));
    return this;
  }

  public DatabaseQuery contains(String field, String text) {
    boolQueryBuilder.filter(QueryBuilders.termQuery(field, text));
    return this;
  }

  public DatabaseQuery matchAny(String field, Collection<?> collection) {
    boolQueryBuilder.filter(QueryBuilders.termsQuery(field, collection));
    return this;
  }

  public DatabaseQuery between(String field, Object from, Object to) {
    RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder(field);
    rangeQueryBuilder.from(from).to(to);
    boolQueryBuilder.filter(rangeQueryBuilder);
    return this;
  }

  public DatabaseQuery greaterThan(String field, Object value) {
    RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder(field);
    rangeQueryBuilder.gt(value);
    boolQueryBuilder.filter(rangeQueryBuilder);
    return this;
  }

  public DatabaseQuery greaterThanOrEquals(String field, Object value) {
    RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder(field);
    rangeQueryBuilder.gte(value);
    boolQueryBuilder.filter(rangeQueryBuilder);
    return this;
  }

  public DatabaseQuery lowerThan(String field, Object value) {
    RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder(field);
    rangeQueryBuilder.lt(value);
    boolQueryBuilder.filter(rangeQueryBuilder);
    return this;
  }

  public DatabaseQuery lowerThanOrEquals(String field, Object value) {
    RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder(field);
    rangeQueryBuilder.lte(value);
    boolQueryBuilder.filter(rangeQueryBuilder);
    return this;
  }

  public DatabaseQuery sort(String field, int size, SortOrder sortOrder) {
    fieldSortBuilder = SortBuilders.fieldSort(field).order(sortOrder);
    pageRequest = PageRequest.of(0, size);
    return this;
  }

  public NativeSearchQuery create() {
    NativeSearchQueryBuilder nativeSearchQueryBuilder =
        new NativeSearchQueryBuilder().withFilter(boolQueryBuilder);
    if (fieldSortBuilder != null) {
      nativeSearchQueryBuilder.withSort(fieldSortBuilder).withPageable(pageRequest);
    }
    return nativeSearchQueryBuilder.build();
  }
}
