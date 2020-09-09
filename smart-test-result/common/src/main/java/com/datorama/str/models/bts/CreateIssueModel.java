package com.datorama.str.models.bts;

import java.util.Map;

import com.datorama.str.CommonConstants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateIssueModel {
  @JsonProperty(CommonConstants.ISSUE_PROJECT_KEY) // TODO adjust naming for more unique
  private String issueProjectKey;

  @JsonProperty(CommonConstants.ISSUE_SUMMARY)
  private String issueSummary;

  @JsonProperty(CommonConstants.ISSUE_FIELDS)
  private Map<String, String> issueFields;

  public Map<String, String> getIssueFields() {
    return issueFields;
  }

  public void setIssueFields(Map<String, String> issueFields) {
    this.issueFields = issueFields;
  }

  public String getIssueProjectKey() {
    return issueProjectKey;
  }

  public void setIssueProjectKey(String issueProjectKey) {
    this.issueProjectKey = issueProjectKey;
  }

  public String getIssueSummary() {
    return issueSummary;
  }

  public void setIssueSummary(String issueSummary) {
    this.issueSummary = issueSummary;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("CreateIssueModel{");
    sb.append("issueProjectKey='").append(issueProjectKey).append('\'');
    sb.append(", issueSummary='").append(issueSummary).append('\'');
    sb.append(", issueFields=").append(issueFields);
    sb.append('}');
    return sb.toString();
  }
}
