package com.datorama.str.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultResponseModel {
	List<TestItemModel> testItems;

	public void setTestItems(List<TestItemModel> testItems) {
		this.testItems = testItems;
	}

	public List<TestItemModel> getTestItems() {
		return testItems;
	}
}
