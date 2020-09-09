package com.datorama.str.models.bts;

import com.datorama.str.CommonConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateIssueModel {
	@JsonProperty(CommonConstants.ISSUE_KEY) //TODO adjust naming for more unique
	private String issueId;
	@JsonProperty(CommonConstants.ISSUE_BODY)
	private String body;

	public String getIssueId() {
		return issueId;
	}

	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override public String toString() {
		final StringBuilder sb = new StringBuilder("UpdateIssueModel{");
		sb.append("issueId='").append(issueId).append('\'');
		sb.append(", body='").append(body).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
