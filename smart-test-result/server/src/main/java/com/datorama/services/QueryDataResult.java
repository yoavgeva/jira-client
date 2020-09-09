package com.datorama.services;

import java.util.List;

import org.springframework.data.elasticsearch.core.SearchHit;

import com.datorama.elasticsearch.indicies.TestItemIndex;

public class QueryDataResult {
	private String latestLaunchId;
	private List<SearchHit<TestItemIndex>> list;

	public QueryDataResult(String latestLaunchId, List<SearchHit<TestItemIndex>> list) {
		this.latestLaunchId = latestLaunchId;
		this.list = list;
	}

	public String getLatestLaunchId() {
		return latestLaunchId;
	}

	public List<SearchHit<TestItemIndex>> getList() {
		return list;
	}
}
