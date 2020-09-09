package com.datorama.rp.models;

import java.util.List;

import com.epam.ta.reportportal.ws.model.launch.LaunchResource;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LaunchesResponse {
	@JsonProperty(value = "content")
	List<LaunchResource> launchResourceList;

	@JsonProperty(value = "page")
	Page page;

	public List<LaunchResource> getLaunchResourceList() {
		return launchResourceList;
	}

	public Page getPage() {
		return page;
	}

	@Override public String toString() {
		final StringBuilder sb = new StringBuilder("GetListOfProjectLaunchesByFilter{");
		sb.append("testItemsResourceList=").append(launchResourceList);
		sb.append(", page=").append(page);
		sb.append('}');
		return sb.toString();
	}

}
