package com.datorama.str.models;

import com.datorama.str.CommonConstants;
import com.fasterxml.jackson.annotation.JsonProperty;

//TODO change to package of condition
public class Occurrence {
	@JsonProperty(CommonConstants.OCCURRENCE_COUNT)
	private int occurrenceCount;

	@JsonProperty(CommonConstants.CONDITION)
	private String condition;

	public int getOccurrenceCount() {
		return occurrenceCount;
	}

	public void setOccurrenceCount(int occurrenceCount) {
		this.occurrenceCount = occurrenceCount;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}
}
