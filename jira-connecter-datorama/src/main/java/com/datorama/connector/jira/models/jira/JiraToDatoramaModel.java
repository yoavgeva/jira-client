package com.datorama.connector.jira.models.jira;

import java.util.List;

public class JiraToDatoramaModel {

	private List<JiraDatoramaLogicModel> fields;

	public List<JiraDatoramaLogicModel> getFields() {
		return fields;
	}

	@Override public String toString() {
		final StringBuilder sb = new StringBuilder("JiraToDatoramaModel{");
		sb.append("fields=").append(fields);
		sb.append('}');
		return sb.toString();
	}
}
