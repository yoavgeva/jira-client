package com.datorama.connector.jira.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class ProfilesResponse {
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Profile {

		public String externalIdentifier;
		public String name;
		public Map<String,String> additionalFields = new HashMap<>();
	}

	public List<Profile> profiles = new ArrayList<>();
}
