package com.datorama.connector.jira.models.jira;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JiraDatoramaLogicModel {

	@JsonProperty(value = "jira_name_key",required = true)
	private String jiraNameKey;
	@JsonProperty(value = "datorama_name_key",required = true)
	private String datoramaNameKey;
	@JsonProperty(value = "class_name_value")
	private String classNameValue;
	@JsonProperty(value = "index_value")
	private String indexValue;
	@JsonProperty(value = "method_name_value")
	private String methodNameValue;
	@JsonProperty(value = "type_value",required = true)
	private String typeValue;

	public String getJiraNameKey() {
		return jiraNameKey;
	}

	public String getTypeValue() {
		return typeValue;
	}

	public String getDatoramaNameKey() {
		return datoramaNameKey;
	}

	public String getClassNameValue() {
		return classNameValue;
	}

	public String getIndexValue() {
		return indexValue;
	}

	public String getMethodNameValue() {
		return methodNameValue;
	}

	@Override public String toString() {
		final StringBuilder sb = new StringBuilder("JiraDatoramaLogicModel{");
		sb.append("jiraNameKey='").append(jiraNameKey).append('\'');
		sb.append(", datoramaNameKey='").append(datoramaNameKey).append('\'');
		sb.append(", classNameValue='").append(classNameValue).append('\'');
		sb.append(", indexValue='").append(indexValue).append('\'');
		sb.append(", methodNameValue='").append(methodNameValue).append('\'');
		sb.append(", typeValue='").append(typeValue).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
