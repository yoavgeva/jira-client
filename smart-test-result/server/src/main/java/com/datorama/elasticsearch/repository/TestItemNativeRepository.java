package com.datorama.elasticsearch.repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHitsIterator;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchPersistentEntity;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchEntityInformation;
import org.springframework.data.elasticsearch.repository.support.MappingElasticsearchEntityInformation;
import org.springframework.data.elasticsearch.repository.support.SimpleElasticsearchRepository;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.stereotype.Repository;

import com.datorama.elasticsearch.indicies.TestItemIndex;

@Repository
public class TestItemNativeRepository extends SimpleElasticsearchRepository<TestItemIndex, String> {
  private final ElasticsearchOperations esOps;

  public TestItemNativeRepository(
      @Qualifier("esOperations") ElasticsearchOperations elasticsearchOperations) {
    super(getElasticsearchEntityInformation(), elasticsearchOperations);
    this.esOps = elasticsearchOperations;
  }

  private static ElasticsearchEntityInformation<TestItemIndex, String>
      getElasticsearchEntityInformation() {
    ClassTypeInformation<TestItemIndex> typeInformation =
        ClassTypeInformation.from(TestItemIndex.class);
    SimpleElasticsearchPersistentEntity<TestItemIndex> entity =
        new SimpleElasticsearchPersistentEntity<>(typeInformation);
    return new MappingElasticsearchEntityInformation<>(entity);
  }

  public List<SearchHit<TestItemIndex>> search(NativeSearchQuery query) {
    List<SearchHit<TestItemIndex>> hits = new CopyOnWriteArrayList<>();
    try( SearchHitsIterator<TestItemIndex> hitsIterator = esOps.searchForStream(query, TestItemIndex.class);){
      while(hitsIterator.hasNext()){
        hits.add(hitsIterator.next());
      }
    }
    return hits;
  }
}
