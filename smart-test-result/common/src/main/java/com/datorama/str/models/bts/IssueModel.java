package com.datorama.str.models.bts;

import java.util.Map;

import com.datorama.str.CommonConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class IssueModel {
	@JsonProperty(CommonConstants.ISSUE_KEY) //TODO adjust naming for more unique
	private String issueKey;
	@JsonProperty(CommonConstants.ISSUE_FIELDS)
	private Map<String,String> issueFields;
	private boolean done;
	private BTSModel btsIssueModel;


	public String getIssueKey() {
		return issueKey;
	}

	public void setIssueKey(String issueKey) {
		this.issueKey = issueKey;
	}

	public Map<String, String> getIssueFields() {
		return issueFields;
	}

	public void setIssueFields(Map<String, String> issueFields) {
		this.issueFields = issueFields;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public BTSModel getBtsIssueModel() {
		return btsIssueModel;
	}

	public void setBtsIssueModel(BTSModel btsIssueModel) {
		this.btsIssueModel = btsIssueModel;
	}
}
