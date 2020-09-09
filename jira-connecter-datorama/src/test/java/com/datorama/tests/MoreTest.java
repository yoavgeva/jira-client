package com.datorama.tests;

import org.junit.Test;

import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;

public class MoreTest {


	//Need to add the user,password, url to test
	@Test
	public void testBS() throws JiraException {
		BasicCredentials basicCredentials = new BasicCredentials("", "");
		JiraClient client = new JiraClient("", basicCredentials);
		String jql = "project in (Datorama,dLite) and issuetype not in (Epic,\"Test Execution\",\"Test Plan\",\"Test Set\",\"Test Plan\",\"Test Set\") and (createdDate > '2020-05-01 16:40' or updatedDate > '2020-05-01 16:40')";
		client.searchIssues(jql, "", 1, 0);
		System.out.println();
	}
}
