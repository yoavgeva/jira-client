package com.datorama.str.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LaunchResultModel {

	@JsonProperty("launch")
	LaunchModel launch;

	@JsonProperty("test_items")
	List<TestItemModel> testItems;

	public LaunchModel getLaunch() {
		return launch;
	}

	public void setLaunch(LaunchModel launch) {
		this.launch = launch;
	}

	public List<TestItemModel> getTestItems() {
		return testItems;
	}

	public void setTestItems(List<TestItemModel> testItems) {
		this.testItems = testItems;
	}

	@Override public String toString() {
		final StringBuilder sb = new StringBuilder("LaunchResultSTR{");
		sb.append("launch=").append(launch);
		sb.append(", testItems=").append(testItems);
		sb.append('}');
		return sb.toString();
	}
}
