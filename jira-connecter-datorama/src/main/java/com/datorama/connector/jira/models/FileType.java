package com.datorama.connector.jira.models;

public enum FileType {
	JSON(".json"),
	CSV(".csv");

	private String value;

	FileType(final String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return this.getValue();
	}
}
