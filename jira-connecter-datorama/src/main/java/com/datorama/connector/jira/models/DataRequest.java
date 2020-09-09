package com.datorama.connector.jira.models;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataRequest {

	public String startDate;
	public String endDate;
	public ProfilesResponse.Profile selectedProfile;
	public Map<String, Object> dsJsonConfig = new HashMap<>();
	public boolean supportsHourlyBreakdown;
}
