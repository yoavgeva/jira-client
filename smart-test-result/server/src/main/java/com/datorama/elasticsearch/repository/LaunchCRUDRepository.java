package com.datorama.elasticsearch.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.datorama.elasticsearch.indicies.LaunchIndex;

@Repository
public interface LaunchCRUDRepository extends ElasticsearchRepository<LaunchIndex,String> {
}
