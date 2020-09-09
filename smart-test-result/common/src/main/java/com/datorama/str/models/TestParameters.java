package com.datorama.str.models;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TestParameters {
	@JsonProperty("key")
	private String key;
	@JsonProperty("value")
	private String value;

	public String getValue() {
		return value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof TestParameters))
			return false;
		TestParameters that = (TestParameters) o;
		return getKey().equals(that.getKey()) &&
				getValue().equals(that.getValue());
	}

	@Override public int hashCode() {
		return Objects.hash(getKey(), getValue());
	}
}
