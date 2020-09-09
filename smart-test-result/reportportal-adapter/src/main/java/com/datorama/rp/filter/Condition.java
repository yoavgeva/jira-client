package com.datorama.rp.filter;

public enum Condition {
	EQUALS("eq"),
	NOT_EQUALS("ne"),
	HAS("has"),
	NOT_IN("!in"),
	IN("in"),
	EXISTS("ex"),
	LEVEL("level"),
	UNDER("under"),
	CONTAINS("cnt"),
	ANY("any"),
	GREATER_THAN("gt"),
	GREATER_THAN_OR_EQUALS("gte"),
	EQUALS_ANY("ea"),
	LOWER_THAN("lt"),
	LOWER_THAN_OR_EQUALS("lte"),
	BETWEEN("btw")
	;
	private String value;
	Condition(String value) {
		this.value = value;
	}

	protected String getValue() {
		return value;
	}
}
