package com.datorama.connector.jira.models;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class DataRowsResponse {
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class DataRow {

		private Map<String, String> values = new HashMap<>();

		@JsonAnyGetter
		public Map<String, String> get() {
			return values;
		}

		@JsonAnySetter
		public void set(String name, String value) {
			values.put(name, value);
		}
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class UrlBucketsTuple {
		public String url;
		public List<String> buckets = new ArrayList<>();

		public UrlBucketsTuple(String url, Collection collection) {
			this.url = url;
			this.buckets.addAll(collection);
		}

		public UrlBucketsTuple() {
		}
	}

	//		public String type; // keep this for now -  dont know why
	public List<UrlBucketsTuple> urlBucketsTupleList = new ArrayList<>();

	public List<DataRow> data = new ArrayList<>();

	public String jobId;
}
