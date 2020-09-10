package com.datorama.elasticsearch.indicies;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import com.datorama.services.UniqueTestItem;
import com.datorama.str.CommonConstants;
import com.datorama.str.models.rules.RuleFilterModel;

@Document(indexName = "bts_issue")
public class BTSIssueIndex {
	@Id
	private String id;
	@Field(name = CommonConstants.ISSUE_KEY)
	private String issueId;

	private List<UniqueTestItem> uniqueTestItems;

	private List<RuleFilterModel> ruleFilters;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIssueId() {
		return issueId;
	}

	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}

	public List<UniqueTestItem> getUniqueTestItems() {
		return uniqueTestItems;
	}

	public void setUniqueTestItems(List<UniqueTestItem> uniqueTestItems) {
		this.uniqueTestItems = uniqueTestItems;
	}

	public List<RuleFilterModel> getRuleFilters() {
		return ruleFilters;
	}

	public void setRuleFilters(List<RuleFilterModel> ruleFilters) {
		this.ruleFilters = ruleFilters;
	}
}
