package com.datorama.str.controllers.bts;

import com.datorama.str.CommonConstants;
import com.datorama.str.models.bts.CreateIssueModel;
import com.datorama.str.models.bts.IssueModel;
import com.datorama.str.models.bts.UpdateIssueModel;

import retrofit2.Call;
import retrofit2.http.*;

public interface IssueController {
	@GET("/issue") 
	Call<IssueModel> getIssue(@Query(CommonConstants.ISSUE_KEY) String issueId);

	@POST("/issue")
	Call<IssueModel> createIssue(@Body CreateIssueModel createIssueModel);

	@PUT("/issue")
	Call<IssueModel> updateIssue(@Body UpdateIssueModel updateIssueModel);
}
