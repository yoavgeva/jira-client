package com.datorama.connector.jira.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserPassResponse {

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class AdditionalParam {
		public String name;
		public String value;
	}

	public boolean success;
	public String token;
	public List<AdditionalParam> additionalParams = new ArrayList<>();
}
