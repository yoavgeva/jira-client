package com.datorama.rp.models;

import java.util.List;

import com.epam.ta.reportportal.ws.model.TestItemResource;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class TestItemsResponse {

	@JsonProperty(value = "content")
	List<TestItemResource> testItemsResourceList;

	@JsonProperty(value = "page")
	Page page;

	public List<TestItemResource> getTestItemsResourceList() {
		return testItemsResourceList;
	}

	public Page getPage() {
		return page;
	}

	@Override public String toString() {
		final StringBuilder sb = new StringBuilder("GetTestItemsBySpecifiedFilter{");
		sb.append("testItemsResourceList=").append(testItemsResourceList);
		sb.append(", page=").append(page);
		sb.append('}');
		return sb.toString();
	}
}

