package com.datorama.elasticsearch.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.datorama.elasticsearch.indicies.TestItemIndex;

@Repository
public interface TestItemCRUDRepository extends ElasticsearchRepository<TestItemIndex,String> {
}
