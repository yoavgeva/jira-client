package com.datorama.connector.jira.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.datorama.connector.jira.exceptions.BadRequestException;
import com.datorama.connector.jira.exceptions.NotFoundException;

import net.rcarz.jiraclient.*;

@Service
public class JiraDataExtractor {
	@Value("${jira.url}")
	private String JIRA_URL;

	@Value("${number.jira.issues.per.request}")
	private int NUMBER_OF_ISSUES_PER_REQUEST;

	Logger logger = LogManager.getLogger(JiraDataExtractor.class);


	private JiraClient client(BasicCredentials credentials) {
		try {
			return new JiraClient(JIRA_URL, credentials);
		} catch (JiraException e) {
			throw new BadRequestException();
		}
	}

	public List<Issue> searchAllIssuesByJql(String jql,BasicCredentials credentials) {
		JiraClient client = client(credentials);
		int total = getTotalIssues(jql, client);
		Issue.SearchResult searchResult;
		List<Issue> jiraIssues = new ArrayList<>();
		int issuesToFetchPerRequest = NUMBER_OF_ISSUES_PER_REQUEST > total ? total : NUMBER_OF_ISSUES_PER_REQUEST;
		for (int received = 0; received < total; received+=issuesToFetchPerRequest) {
			logger.info("received: {} total: {}",received,total);
			try {
				searchResult = client.searchIssues(jql, "", issuesToFetchPerRequest, received);
				jiraIssues.addAll(searchResult.issues);
			} catch (JiraException e) {
				logger.error("Failed fetching jira issues",e);
				throw new NotFoundException();
			}
		}
		logger.info("issues size downloaded: {}",jiraIssues.size());
		return jiraIssues;
	}

	private int getTotalIssues(String jql, JiraClient client)  {
		try {
			Issue.SearchResult result = client.searchIssues(jql, "", 1, 0);
			return result.total;
		} catch (JiraException e) {
			logger.error("Failed to get total issues",e);
			throw new BadRequestException();
		}
	}

	//	public List<IssueHistory> getIssues(String jql) {
//
//	}

}
