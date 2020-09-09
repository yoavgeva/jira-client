package com.datorama.elasticsearch.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.datorama.elasticsearch.indicies.BTSIssueIndex;

@Repository
public interface BTSIssueCRUDRepository extends ElasticsearchRepository<BTSIssueIndex, String> {}
