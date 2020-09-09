package com.datorama.elasticsearch.indicies;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import com.datorama.str.CommonConstants;

@Document(indexName = "bts_issue")
public class BTSIssueIndex {
	@Id
	private String id;
	@Field(name = CommonConstants.ISSUE_KEY)
	private String issueId;
}
