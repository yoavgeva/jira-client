package com.datorama.services;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datorama.str.models.bts.CreateIssueModel;
import com.datorama.str.models.bts.IssueModel;
import com.datorama.str.models.bts.UpdateIssueModel;

import net.rcarz.jiraclient.*;

@Service
public class JiraService {
	final IssueType bug;
	private final JiraClient client;
	Logger logger = LogManager.getLogger(JiraService.class);

	@Autowired JiraService(JiraClient jiraClient, IssueType bug) {
		this.client = jiraClient;
		this.bug = bug;
	}

	public IssueModel createIssue(CreateIssueModel createIssueModel) throws JiraException {
		Issue issue;
		HashMap<String, Object> fieldsOfTemplate = new HashMap<>();
		Issue templateIssue = client.getIssue("DAT-67553");
		createIssueModel
				.getIssueFields()
				.forEach(
						(k, v) -> {
							Object field = templateIssue.getField(k);
							logger.info(field.getClass());
							Object value = v;
							if (field instanceof net.sf.json.JSONArray) {
								ArrayList<String> strings = new ArrayList<>();
								strings.add(v);
								value = strings;
							}
							logger.info(field.toString());
							fieldsOfTemplate.put(k, value);
						});
		Issue.FluentCreate fluentCreate =
				client.createIssue(createIssueModel.getIssueProjectKey(), bug.getName());
		fieldsOfTemplate.forEach(
				(k, v) -> {
					fluentCreate.field(k, v);
				});
		fluentCreate.field(Field.SUMMARY, createIssueModel.getIssueSummary());
		issue = fluentCreate.execute();
		IssueModel issueModel = new IssueModel();
		issueModel.setIssueKey(issue.getKey());
		return issueModel;
	}

	// TODO enhance fetching data
	public IssueModel getIssue(String issueId) throws JiraException {
		Issue issue;
		issue = client.getIssue(issueId);
		IssueModel issueModel = new IssueModel();
		issueModel.setIssueKey(issue.getKey());
		issueModel.setDone(issue.getStatus().getName().toLowerCase().equals("done"));
		return issueModel;
	}

	public IssueModel updateIssue(UpdateIssueModel updateIssueModel) throws JiraException {
		client
				.getIssue(updateIssueModel.getIssueId())
				.addComment(updateIssueModel.getBody())
				.update(updateIssueModel.getBody());
		IssueModel issueModel = new IssueModel();
		issueModel.setIssueKey(updateIssueModel.getIssueId());
		return issueModel;
	}
}
