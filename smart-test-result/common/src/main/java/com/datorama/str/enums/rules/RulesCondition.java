package com.datorama.str.enums.rules;

import java.util.Optional;

public enum RulesCondition {
	CONTAINS("cnt"),
	EQUALS("eq"),
	NOT_EQUALS("ne"),
	GREATER_THAN("gt"),
	LOWER_THAN("lt"),
	LOWER_THAN_OR_EQUALS("lte"),
	GREATER_THAN_OR_EQUALS("gte"),
	BETWEEN("btw"),
	ITEMS_SIZE("its");

	private String value;
	RulesCondition(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}


	public static Optional<RulesCondition> getEnum(String value){
		for (int i = 0; i < RulesCondition.values().length; i++) {
			if (value.equals(RulesCondition.values()[i].getValue())) {
				return Optional.of(RulesCondition.values()[i]);
			}
		}
		return Optional.empty();
	}
}
