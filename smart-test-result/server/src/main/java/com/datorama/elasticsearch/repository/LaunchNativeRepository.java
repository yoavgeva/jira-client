package com.datorama.elasticsearch.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchPersistentEntity;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchEntityInformation;
import org.springframework.data.elasticsearch.repository.support.MappingElasticsearchEntityInformation;
import org.springframework.data.elasticsearch.repository.support.SimpleElasticsearchRepository;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.stereotype.Repository;

import com.datorama.elasticsearch.indicies.LaunchIndex;

@Repository
public class LaunchNativeRepository extends SimpleElasticsearchRepository<LaunchIndex, String> {
  private final ElasticsearchOperations esOps;

  public LaunchNativeRepository(
      @Qualifier("esOperations") ElasticsearchOperations elasticsearchOperations) {
    super(getElasticsearchEntityInformation(), elasticsearchOperations);
    this.esOps = elasticsearchOperations;
  }

  private static ElasticsearchEntityInformation<LaunchIndex, String>
      getElasticsearchEntityInformation() {
    ClassTypeInformation<LaunchIndex> typeInformation =
        ClassTypeInformation.from(LaunchIndex.class);
    SimpleElasticsearchPersistentEntity<LaunchIndex> entity =
        new SimpleElasticsearchPersistentEntity<>(typeInformation);
    return new MappingElasticsearchEntityInformation<>(entity);

  }

  public List<SearchHit<LaunchIndex>> search(NativeSearchQuery query) {
    SearchHits<LaunchIndex> hits = esOps.search(query, LaunchIndex.class);
    return hits.getSearchHits();
  }
}
