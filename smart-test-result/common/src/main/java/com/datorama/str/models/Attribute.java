package com.datorama.str.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Attribute {
	@JsonProperty("key")
	private String key;
	@JsonProperty("value")
	private Object value;

	public Object getValue() {
		return value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}
